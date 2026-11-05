package com.aegis.nexus.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.aegis.nexus")
@EnableJpaRepositories(basePackages = "com.aegis.nexus")
@EntityScan(basePackages = "com.aegis.nexus")
public class AegisNexusApplication {

    public static void main(String[] args) {
        SpringApplication.run(AegisNexusApplication.class, args);
    }
}
