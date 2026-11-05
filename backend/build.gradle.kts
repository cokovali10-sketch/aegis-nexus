plugins {
    id("java")
    id("org.springframework.boot") version "3.3.5"
    id("io.spring.dependency-management") version "1.1.6"
}

group = "com.aegis.nexus"
version = "1.0.0-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.postgresql:postgresql:42.7.4")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("io.jsonwebtoken:jjwt-api:0.12.3")
    implementation("io.jsonwebtoken:jjwt-impl:0.12.3")
    implementation("io.jsonwebtoken:jjwt-jackson:0.12.3")
    implementation("org.opensearch.client:opensearch-rest-client:2.11.1")
    implementation("org.opensearch.client:opensearch-java:2.11.1")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.junit.jupiter:junit-jupiter:5.11.3")
    testRuntimeOnly("com.h2database:h2:2.2.224")
}

tasks.test {
    useJUnitPlatform()
}
