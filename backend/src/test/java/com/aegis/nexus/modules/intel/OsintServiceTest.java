package com.aegis.nexus.modules.intel;

import com.aegis.nexus.modules.intel.service.OsintService;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class OsintServiceTest {

    private final OsintService osintService = new OsintService();

    @Test
    void analyzeValidIp() {
        Map<String, Object> result = osintService.analyzeIp("192.168.1.100");
        assertThat(result.get("valid")).isEqualTo(true);
        assertThat(result.get("is_private")).isEqualTo(true);
        assertThat(result.get("risk_level")).isEqualTo("low");
    }

    @Test
    void analyzePublicIp() {
        Map<String, Object> result = osintService.analyzeIp("8.8.8.8");
        assertThat(result.get("valid")).isEqualTo(true);
        assertThat(result.get("is_private")).isEqualTo(false);
    }

    @Test
    void analyzeInvalidIp() {
        Map<String, Object> result = osintService.analyzeIp("999.999.999.999");
        assertThat(result.get("valid")).isEqualTo(false);
    }

    @Test
    void analyzeValidDomain() {
        Map<String, Object> result = osintService.analyzeDomain("example.com");
        assertThat(result.get("valid")).isEqualTo(true);
        assertThat(result.get("tld")).isEqualTo("com");
        assertThat(result.get("risk_level")).isEqualTo("low");
    }

    @Test
    void analyzeSuspiciousDomain() {
        Map<String, Object> result = osintService.analyzeDomain("malware.tk");
        assertThat(result.get("valid")).isEqualTo(true);
        assertThat(result.get("risk_level")).isEqualTo("high");
    }

    @Test
    void analyzeInvalidDomain() {
        Map<String, Object> result = osintService.analyzeDomain("not a domain");
        assertThat(result.get("valid")).isEqualTo(false);
    }

    @Test
    void analyzeSha256Hash() {
        Map<String, Object> result = osintService.analyzeHash("a".repeat(64));
        assertThat(result.get("hash_type")).isEqualTo("SHA-256");
        assertThat(result.get("valid")).isEqualTo(true);
    }

    @Test
    void analyzeMd5Hash() {
        Map<String, Object> result = osintService.analyzeHash("a".repeat(32));
        assertThat(result.get("hash_type")).isEqualTo("MD5");
        assertThat(result.get("valid")).isEqualTo(true);
    }

    @Test
    void analyzeInvalidHash() {
        Map<String, Object> result = osintService.analyzeHash("not-a-hash");
        assertThat(result.get("valid")).isEqualTo(false);
    }
}
