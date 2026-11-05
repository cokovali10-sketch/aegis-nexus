package com.aegis.nexus.modules.intel.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Service
public class OsintService {

    private static final Pattern IPV4_PATTERN = Pattern.compile(
        "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$"
    );

    private static final Pattern DOMAIN_PATTERN = Pattern.compile(
        "^(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,}$"
    );

    public Map<String, Object> analyzeIp(String ipAddress) {
        Map<String, Object> result = new HashMap<>();
        result.put("ip", ipAddress);
        result.put("type", "IPv4");
        result.put("valid", IPV4_PATTERN.matcher(ipAddress).matches());

        if (IPV4_PATTERN.matcher(ipAddress).matches()) {
            result.put("is_private", isPrivateIp(ipAddress));
            result.put("is_reserved", isReservedIp(ipAddress));
            result.put("risk_level", assessIpRisk(ipAddress));
        } else {
            result.put("valid", false);
            result.put("error", "Invalid IPv4 address");
        }

        return result;
    }

    public Map<String, Object> analyzeDomain(String domain) {
        Map<String, Object> result = new HashMap<>();
        result.put("domain", domain);
        result.put("valid", DOMAIN_PATTERN.matcher(domain).matches());

        if (DOMAIN_PATTERN.matcher(domain).matches()) {
            result.put("tld", extractTld(domain));
            result.put("subdomain_count", countSubdomains(domain));
            result.put("length", domain.length());
            result.put("risk_level", assessDomainRisk(domain));
        } else {
            result.put("valid", false);
            result.put("error", "Invalid domain format");
        }

        return result;
    }

    public Map<String, Object> analyzeHash(String hash) {
        Map<String, Object> result = new HashMap<>();
        result.put("hash", hash);
        result.put("length", hash.length());

        String hashType = "unknown";
        if (hash.length() == 32) hashType = "MD5";
        else if (hash.length() == 40) hashType = "SHA-1";
        else if (hash.length() == 64) hashType = "SHA-256";

        result.put("hash_type", hashType);
        result.put("valid", hash.matches("^[a-fA-F0-9]+$"));
        result.put("risk_level", "unknown");

        return result;
    }

    private boolean isPrivateIp(String ip) {
        String[] parts = ip.split("\\.");
        int first = Integer.parseInt(parts[0]);
        int second = Integer.parseInt(parts[1]);

        if (first == 10) return true;
        if (first == 172 && second >= 16 && second <= 31) return true;
        if (first == 192 && second == 168) return true;
        if (first == 127) return true;
        return false;
    }

    private boolean isReservedIp(String ip) {
        String[] parts = ip.split("\\.");
        int first = Integer.parseInt(parts[0]);
        if (first == 0 || first == 127 || first == 169) return true;
        return false;
    }

    private String assessIpRisk(String ip) {
        if (isPrivateIp(ip)) return "low";
        return "medium";
    }

    private String extractTld(String domain) {
        int lastDot = domain.lastIndexOf('.');
        return lastDot >= 0 ? domain.substring(lastDot + 1) : "";
    }

    private int countSubdomains(String domain) {
        return domain.split("\\.").length - 2;
    }

    private String assessDomainRisk(String domain) {
        String lower = domain.toLowerCase();
        String[] suspiciousTlds = {".tk", ".ml", ".ga", ".cf", ".gq", ".xyz", ".top"};
        for (String tld : suspiciousTlds) {
            if (lower.endsWith(tld)) return "high";
        }
        if (domain.length() > 50) return "medium";
        return "low";
    }
}


