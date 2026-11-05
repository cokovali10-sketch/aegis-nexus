package com.aegis.nexus.core;

import com.aegis.nexus.api.HealthController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class HealthControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

@Test
void healthEndpointReturnsOk() {
    ResponseEntity<String> response = restTemplate.getForEntity("/api/v1/health", String.class);
    assertThat(response).isNotNull();
}
}
