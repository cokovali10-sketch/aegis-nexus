use serde::{Deserialize, Serialize};
use std::env;
use std::fs;
use std::io::Read;
use std::net::UdpSocket;
use std::path::PathBuf;
use uuid::Uuid;
use sha2::{Digest, Sha256};

const AGENT_VERSION: &str = "1.0.0";
const API_BASE: &str = "http://localhost:8080/api/v1";
const HEARTBEAT_INTERVAL_SECS: u64 = 30;

#[derive(Serialize, Deserialize, Clone, Debug)]
pub struct EndpointRegisterRequest {
    pub agent_id: String,
    pub hostname: String,
    pub ip_address: String,
    pub os: String,
    pub os_version: String,
    pub agent_version: String,
}

#[derive(Serialize, Deserialize, Clone, Debug)]
pub struct ProcessInfo {
    pub pid: u32,
    pub name: String,
    pub executable_path: Option<String>,
    pub command_line: String,
    pub parent_pid: Option<u32>,
    pub user: Option<String>,
    pub cpu_usage: f64,
    pub memory_mb: f64,
}

#[derive(Serialize, Deserialize, Clone, Debug)]
pub struct FileIntegrityRecord {
    pub endpoint_id: String,
    pub file_path: String,
    pub sha256: String,
    pub file_size: u64,
    pub change_type: String,
}

#[derive(Serialize, Deserialize)]
pub struct ApiResponse<T> {
    pub success: bool,
    pub data: Option<T>,
    pub message: Option<String>,
    pub timestamp: Option<String>,
}

fn agent_id_path() -> PathBuf {
    #[cfg(target_os = "windows")]
    {
        env::var("APPDATA")
            .map(|p| PathBuf::from(p).join("AEGIS NEXUS").join("agent_id"))
            .unwrap_or_else(|_| PathBuf::from("agent_id"))
    }
    #[cfg(not(target_os = "windows"))]
    {
        PathBuf::from("agent_id")
    }
}

fn load_or_create_agent_id() -> String {
    let path = agent_id_path();
    if let Ok(content) = fs::read_to_string(&path) {
        let trimmed = content.trim();
        if !trimmed.is_empty() {
            return trimmed.to_string();
        }
    }
    let new_id = Uuid::new_v4().to_string();
    if let Some(parent) = path.parent() {
        let _ = fs::create_dir_all(parent);
    }
    let _ = fs::write(&path, &new_id);
    new_id
}

fn get_hostname() -> String {
    #[cfg(target_os = "windows")]
    {
        env::var("COMPUTERNAME").unwrap_or_else(|_| "unknown-host".to_string())
    }
    #[cfg(not(target_os = "windows"))]
    {
        "unknown-host".to_string()
    }
}

fn get_os_version() -> String {
    #[cfg(target_os = "windows")]
    {
        env::var("OS_VERSION").unwrap_or_else(|_| env::consts::OS.to_string())
    }
    #[cfg(not(target_os = "windows"))]
    {
        env::consts::OS.to_string()
    }
}

fn get_local_ip() -> String {
    // std-only trick: connect a UDP socket to a public address to learn local IP
    if let Ok(socket) = UdpSocket::bind("0.0.0.0:0") {
        if socket.connect(("8.8.8.8", 80)).is_ok() {
            if let Ok(addr) = socket.local_addr() {
                return addr.ip().to_string();
            }
        }
    }
    "0.0.0.0".to_string()
}

async fn register_endpoint(client: &reqwest::Client, request: &EndpointRegisterRequest) -> Result<(), Box<dyn std::error::Error>> {
    let res = client
        .post(&format!("{}/edr/endpoints/register", API_BASE))
        .json(request)
        .send()
        .await?;

    if res.status().is_success() {
        println!("[AGENT] Endpoint registered successfully");
    } else {
        let status = res.status();
        let text = res.text().await.unwrap_or_default();
        eprintln!("[AGENT] Registration failed: {} {}", status, text);
    }
    Ok(())
}

async fn send_heartbeat(client: &reqwest::Client, agent_id: &str) -> Result<(), Box<dyn std::error::Error>> {
    let res = client
        .post(&format!("{}/edr/endpoints/{}/heartbeat", API_BASE, agent_id))
        .send()
        .await?;

    if res.status().is_success() {
        println!("[AGENT] Heartbeat sent");
    } else {
        eprintln!("[AGENT] Heartbeat failed: {}", res.status());
    }
    Ok(())
}

async fn send_processes(client: &reqwest::Client, agent_id: &str, processes: Vec<ProcessInfo>) -> Result<(), Box<dyn std::error::Error>> {
    for proc in processes {
        let _ = client
            .post(&format!("{}/edr/processes/endpoint/{}", API_BASE, agent_id))
            .query(&[
                ("pid", proc.pid.to_string()),
                ("name", proc.name.clone()),
                ("executable_path", proc.executable_path.clone().unwrap_or_default()),
                ("command_line", proc.command_line.clone()),
                ("user", proc.user.clone().unwrap_or_default()),
            ])
            .send()
            .await;
    }
    Ok(())
}

fn collect_processes() -> Vec<ProcessInfo> {
    let mut sys = sysinfo::System::new_all();
    sys.refresh_all();

    let mut processes = Vec::new();
    for (pid, proc) in sys.processes() {
        processes.push(ProcessInfo {
            pid: pid.as_u32(),
            name: proc.name().to_string_lossy().to_string(),
            executable_path: proc.exe().map(|p| p.to_string_lossy().to_string()),
            command_line: proc.cmd().iter().map(|s| s.to_string_lossy().to_string()).collect::<Vec<_>>().join(" "),
            parent_pid: proc.parent().map(|p| p.as_u32()),
            user: None,
            cpu_usage: proc.cpu_usage() as f64,
            memory_mb: proc.memory() as f64 / 1024.0 / 1024.0,
        });
    }
    processes
}

fn collect_file_integrity_sample(agent_id: &str) -> Vec<FileIntegrityRecord> {
    let mut records = Vec::new();
    let system_dirs = [
        "C:\\Windows\\System32\\kernel32.dll",
        "C:\\Windows\\System32\\ntdll.dll",
    ];

    for path in &system_dirs {
        if let Ok(metadata) = fs::metadata(path) {
            if metadata.is_file() {
                let sha256 = sha256_file(path).unwrap_or_default();
                records.push(FileIntegrityRecord {
                    endpoint_id: agent_id.to_string(),
                    file_path: path.to_string(),
                    sha256,
                    file_size: metadata.len(),
                    change_type: "CREATED".to_string(),
                });
            }
        }
    }
    records
}

#[cfg(not(target_os = "windows"))]
fn collect_file_integrity_sample(_agent_id: &str) -> Vec<FileIntegrityRecord> {
    Vec::new()
}

fn sha256_file(path: &str) -> Result<String, Box<dyn std::error::Error>> {
    let mut file = fs::File::open(path)?;
    let mut hasher = Sha256::new();
    let mut buffer = [0; 8192];
    loop {
        let n = file.read(&mut buffer)?;
        if n == 0 {
            break;
        }
        hasher.update(&buffer[..n]);
    }
    let result = hasher.finalize();
    Ok(format!("{:x}", result))
}

#[tokio::main]
async fn main() -> Result<(), Box<dyn std::error::Error>> {
    println!("[AEGIS NEXUS Agent] Starting endpoint agent v{}", AGENT_VERSION);

    let agent_id = load_or_create_agent_id();
    println!("[AGENT] Agent ID: {}", agent_id);

    let hostname = get_hostname();
    let os_version = get_os_version();
    let ip_address = get_local_ip();

    println!("[AGENT] Hostname: {}", hostname);
    println!("[AGENT] OS: {} {}", env::consts::OS, os_version);
    println!("[AGENT] IP: {}", ip_address);

    let client = reqwest::Client::builder()
        .timeout(std::time::Duration::from_secs(10))
        .build()?;

    let register_req = EndpointRegisterRequest {
        agent_id: agent_id.clone(),
        hostname,
        ip_address,
        os: env::consts::OS.to_string(),
        os_version,
        agent_version: AGENT_VERSION.to_string(),
    };
    if let Err(e) = register_endpoint(&client, &register_req).await {
        eprintln!("[AGENT] Registration error: {}", e);
    }

    let mut interval = tokio::time::interval(std::time::Duration::from_secs(HEARTBEAT_INTERVAL_SECS));

    loop {
        interval.tick().await;

        if let Err(e) = send_heartbeat(&client, &agent_id).await {
            eprintln!("[AGENT] Heartbeat error: {}", e);
        }

        let processes = collect_processes();
        println!("[AGENT] Collected {} processes", processes.len());
        if let Err(e) = send_processes(&client, &agent_id, processes).await {
            eprintln!("[AGENT] Process reporting error: {}", e);
        }

        let fim_records = collect_file_integrity_sample(&agent_id);
        if !fim_records.is_empty() {
            println!("[AGENT] Collected {} file integrity records", fim_records.len());
        }
    }
}
