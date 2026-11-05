package com.aegis.nexus.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthController {

    @GetMapping
    public ApiResponse<String> health() {
        return ApiResponse.ok("operational", "AEGIS NEXUS platform is running");
    }
}
