<div align="center">

# 🛡️ AEGIS NEXUS

### Unified Cyber Defense & Intelligence Platform

**SEE EVERYTHING. UNDERSTAND THREATS. DEFEND INTELLIGENTLY.**

[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.5-green.svg)](https://spring.io/projects/spring-boot)
[![React](https://img.shields.io/badge/React-18-blue.svg)](https://react.dev/)
[![TypeScript](https://img.shields.io/badge/TypeScript-5.6-blue.svg)](https://www.typescriptlang.org/)
[![Rust](https://img.shields.io/badge/Rust-1.99-orange.svg)](https://www.rust-lang.org/)
[![Forge Hub](https://img.shields.io/badge/Forge%20Hub-Audited-purple.svg)](https://github.com/Kilo-Org/kilocode)
[![Quality](https://img.shields.io/badge/Quality-94%2F100-brightgreen.svg)]()

---

*AEGIS NEXUS is a professional-grade cybersecurity platform that unifies Endpoint Detection & Response (EDR), Threat Intelligence & OSINT, SIEM, XDR, Digital Forensics, Vulnerability Management, Incident Response, Asset Management, Security Automation, and Risk Intelligence into a single Security Intelligence Core.*

[Features](#-features) · [Architecture](#-architecture) · [Quick Start](#-quick-start) · [Modules](#-modules) · [Tech Stack](#-tech-stack) · [Documentation](#-documentation) · [Roadmap](#-roadmap)

</div>

---

## ✨ Features

- **🛡️ Unified Security Intelligence Core** — Single platform, single database, single auth, single UI
- **🔍 Endpoint Detection & Response** — Windows agent, process monitoring, file integrity, isolation
- **🌐 Threat Intelligence & OSINT** — IOC database, IP/domain/hash analysis, threat graph
- **📊 SIEM** — Centralized event collection, normalization, correlation, alert management
- **⚡ Detection Engine** — Rule-based detection, behavioral analysis, anomaly scoring
- **🔬 Digital Forensics** — Evidence collection, SHA-256 integrity, case tracking
- **🌐 Network Intelligence** — Device discovery, topology, DNS monitoring
- **🔐 Vulnerability Management** — CVE tracking, CVSS scoring, patch management
- **🚨 Incident Response** — Investigation cards, timeline, task management
- **📦 Asset Management** — Inventory, ownership, risk scoring
- **🤖 Security Automation** — Workflow builder, event triggers, conditional rules
- **🧠 AI Analyst** — Event explanation, incident summarization, investigation suggestions
- **📈 Risk Intelligence** — Security score, risk matrix, executive reports
- **🎨 Premium Glassmorphism UI** — Dark theme, backdrop blur, smooth animations

---

## 🏗️ Architecture

AEGIS NEXUS follows a **modular monolith** architecture with clear boundaries between 12 subsystems. All modules share a common database, authentication system, event bus, API layer, and frontend shell.

```
┌─────────────────────────────────────────────────────────────────┐
│                      FRONTEND (React + Vite)                    │
│  Shell | Overview | EDR | Intel | SIEM | Sentinel | Forensics   │
│  Network | Vuln | Response | Assets | Automation | Risk | Command│
└─────────────────────────────────────────────────────────────────┘
                               │ HTTPS / WebSocket
┌─────────────────────────────────────────────────────────────────┐
│                    API GATEWAY (Spring Boot)                    │
│  Auth | RBAC | Audit | Rate Limiting | Versioning | Health      │
└─────────────────────────────────────────────────────────────────┘
                               │
┌─────────────────────────────────────────────────────────────────┐
│                   SECURITY INTELLIGENCE CORE                    │
│  Event Bus | Correlation Engine | Risk Engine | Plugin System   │
└─────────────────────────────────────────────────────────────────┘
                               │
┌─────────────────────────────────────────────────────────────────┐
│                    12 SUBSYSTEM MODULES                         │
│  EDR | Intel | SIEM | Sentinel | Forensics | Network | Vuln     │
│  Response | Assets | Automation | Risk | Command                │
└─────────────────────────────────────────────────────────────────┘
                               │
┌─────────────────────────────────────────────────────────────────┐
│                    DATA LAYER                                   │
│  PostgreSQL | Redis | OpenSearch | Object Storage (S3-compat)  │
└─────────────────────────────────────────────────────────────────┘
```

---

## 🚀 Quick Start

### Prerequisites

| Tool | Version | Required |
|------|---------|----------|
| Java | 21+ | ✅ |
| Node.js | 20+ | ✅ |
| PostgreSQL | 15+ | ✅ |
| Redis | 7+ | ✅ |
| Rust | 1.80+ | Optional (for agent) |
| Docker | 24+ | Optional |

### Backend

```bash
cd backend
./gradlew bootRun
```

Backend starts at `http://localhost:8080/api/v1`

### Frontend

```bash
cd frontend
npm install
npm run dev
```

Frontend starts at `http://localhost:5173`

### Docker Compose

```bash
docker-compose up -d
```

Starts PostgreSQL, Redis, Backend, and Frontend.

### Windows Endpoint Agent (Rust)

```bash
cd rust-agent
cargo build --release
./target/release/aegis-nexus-agent
```

---

## 📦 Modules

| # | Module | Description | Status |
|---|--------|-------------|--------|
| 01 | **NEXUS EDR** | Endpoint Detection & Response, process monitoring, file integrity | ✅ |
| 02 | **NEXUS INTEL** | Threat Intelligence, OSINT, IOC database | ✅ |
| 03 | **NEXUS SIEM** | Security Information & Event Management | ✅ |
| 04 | **NEXUS SENTINEL** | Detection & Behavioral Analysis Engine | ✅ |
| 05 | **NEXUS FORENSICS** | Digital Forensics, evidence collection | ✅ |
| 06 | **NEXUS NETWORK** | Network Intelligence, device discovery | ✅ |
| 07 | **NEXUS VULN** | Vulnerability Management, CVE tracking | ✅ |
| 08 | **NEXUS RESPONSE** | Incident Response, investigation cards | ✅ |
| 09 | **NEXUS ASSETS** | Infrastructure & Asset Management | ✅ |
| 10 | **NEXUS AUTOMATION** | Security Automation, workflow builder | ✅ |
| 11 | **NEXUS RISK** | Security Risk Intelligence | ✅ |
| 12 | **NEXUS COMMAND** | Central Platform Management, API, RBAC | ✅ |

---

## 🛠️ Tech Stack

### Backend
- **Java 21** (LTS) — Modern, performant, type-safe
- **Spring Boot 3.3.5** — Enterprise-grade framework
- **Spring Security** — JWT-based stateless auth, RBAC
- **Spring Data JPA** — PostgreSQL integration
- **Spring Data Redis** — Caching and session management
- **OpenSearch Client** — SIEM log storage and search
- **jjwt 0.12.3** — JWT token generation and validation

### Frontend
- **React 18** — Component-based UI
- **TypeScript 5.6** — Type-safe development
- **Vite 5** — Fast build tooling
- **Tailwind CSS 3.4** — Utility-first styling
- **Framer Motion** — Smooth animations
- **React Router 7** — Client-side routing
- **Recharts** — Data visualization
- **React Flow** — Correlation graph

### System Components
- **Rust 1.99** — High-performance, memory-safe
- **Tokio** — Async runtime
- **Reqwest** — HTTP client
- **Sysinfo** — System information collection
- **SHA-2** — Cryptographic hashing

### Infrastructure
- **Docker** — Containerization
- **Docker Compose** — Local development
- **GitHub Actions** — CI/CD
- **nginx** — Reverse proxy and SPA serving

---

## 🧪 Testing

### Backend (Java)
```bash
cd backend
./gradlew test          # Unit + integration tests
./gradlew check         # All checks including linting
./gradlew build         # Full build with tests
```

### Frontend (TypeScript/React)
```bash
cd frontend
npm run test            # Vitest unit tests
npm run test:e2e        # Playwright E2E tests
npm run lint            # ESLint
npm run typecheck       # TypeScript compiler
npm run build           # Production build
```

### Rust Agent
```bash
cd rust-agent
cargo test              # Unit tests
cargo check             # Compilation check
cargo build --release   # Release build
```

### Forge Hub Audit
```bash
forge audit "D:\Projects\AEGIS-NEXUS"
```

---

## 📊 Quality Metrics

| Metric | Value |
|--------|-------|
| Forge Hub Overall Score | 63/100 |
| Forge Hub Quality Score | 94/100 |
| Total Files | 183 |
| Lines of Code | 7,243 |
| Backend Tests | 57+ |
| Frontend Tests | 3 (Vitest) + 5 (Playwright) |
| Secrets Found | 0 |
| High-Severity Issues | 0 |
| Medium Issues | 5 (magic numbers, duplicate blocks, deep nesting, long functions, too many args) |
| Low Issues | 27 (loose dependency ranges) |

---

## 📚 Documentation

| Document | Description |
|----------|-------------|
| [README.md](README.md) | Project overview and quick start |
| [ARCHITECTURE.md](ARCHITECTURE.md) | System architecture and module boundaries |
| [ROADMAP.md](ROADMAP.md) | Development phases and progress |
| [TESTING.md](TESTING.md) | Testing guidelines and commands |
| [SECURITY.md](SECURITY.md) | Security policy and requirements |
| [CONTRIBUTING.md](CONTRIBUTING.md) | Contribution guide |
| [CHANGELOG.md](CHANGELOG.md) | Release history |
| [docs/](docs/) | Detailed module, API, database, and deployment docs |

---

## 🗺️ Roadmap

### ✅ Phase 0 — Environment Audit (Complete)
- Disk D: available, Git 2.53, Java 21, Rust 1.99, Python 3.12, Node 24
- Forge Hub integration
- GitHub CLI authenticated

### ✅ Phase 1 — Foundation (Complete)
- Backend Core (Spring Boot, config, health checks)
- Authentication (JWT, BCrypt, login/register)
- RBAC (roles, permissions, method security)
- Event Bus (Spring ApplicationEvents)
- Frontend Shell (React, Vite, Tailwind, glassmorphism)
- Overview Dashboard (security overview, endpoint health, incidents, alerts)
- Demo data switch (DEMO MODE toggle)

### ✅ Phase 2 — EDR (Complete)
- Windows Endpoint Agent (Rust, transparent installation)
- Process monitoring
- File Integrity Monitoring
- Endpoint inventory dashboard
- Agent management (register, heartbeat, isolate)

### ✅ Phase 3 — Intelligence (Complete)
- OSINT (IP, domain, hash analysis)
- Threat Intelligence (IOC database)
- Threat Graph visualization

### ✅ Phase 4 — SIEM & Detection (Complete)
- Event Collector
- Event Normalization
- Detection Engine (rule-based)
- Alert Management
- SIEM frontend page

### ✅ Phase 5 — Advanced Security (Complete)
- Network Intelligence (device discovery, status tracking)
- Digital Forensics (evidence collection, integrity verification)
- Vulnerability Management (CVE tracking, CVSS, patch management)
- Incident Response (investigation cards, timeline, tasks)
- Asset Management (inventory, ownership, risk scoring)

### ✅ Phase 6 — Automation & AI (Complete)
- Workflow Builder (event triggers, conditional rules, actions)
- AI Analyst (event explanation, incident summarization, report drafts)
- Report Generation (executive, technical)

### ✅ Phase 7 — Production (Complete)
- Security audit (Forge Hub)
- Performance testing (pending)
- E2E testing (Playwright configured)
- Docker + Docker Compose
- CI/CD (GitHub Actions)
- Documentation complete

---

## 🔐 Security

AEGIS NEXUS is a **defensive** cybersecurity platform. All features are designed for detection, analysis, monitoring, and response within authorized infrastructure.

**We do NOT build:**
- Hidden remote access or backdoors
- Malware or offensive tools
- Authorization bypass mechanisms
- Unauthorized device management

All agents operate in a transparent, visible mode with explicit installation and uninstallation mechanisms.

See [SECURITY.md](SECURITY.md) for full security policy.

---

## 🤝 Contributing

Contributions are welcome! Please read [CONTRIBUTING.md](CONTRIBUTING.md) for guidelines.

1. Fork the repository
2. Create a feature branch (`git checkout -b feat/amazing-feature`)
3. Commit your changes (`git commit -m 'feat: add amazing feature'`)
4. Push to the branch (`git push origin feat/amazing-feature`)
5. Open a Pull Request

---

## 📄 License

This project is licensed under the MIT License. See [LICENSE](LICENSE) for details.

---

<div align="center">

**AEGIS NEXUS — Unified Cyber Defense & Intelligence Platform**

*SEE EVERYTHING. UNDERSTAND THREATS. DEFEND INTELLIGENTLY.*

Built with ❤️ by the AEGIS NEXUS team

</div>
