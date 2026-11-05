# Changelog

All notable changes to AEGIS NEXUS will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Added

- Project foundation: Git repository, directory structure, documentation
- Environment audit: D: drive, Git 2.53, Java 21, Rust 1.99, Python 3.12, Node 24, GitHub CLI
- Forge Hub integration: forge CLI available at C:\Users\Acer\AppData\Local\Programs\Python\Python312\Scripts\forge.exe
- Project structure: backend, frontend, shared, docs, rust-tools, python-analytics
- Backend Core: Spring Boot 3.3.5, Java 21, health checks, event bus, global error handling
- Authentication: JWT-based stateless auth, BCrypt password hashing, login/register/me endpoints
- RBAC: Role enum (SUPER_ADMIN, ADMIN, ANALYST, VIEWER), Spring Security method security
- SecurityConfig: stateless session, JWT authentication filter, permitAll on auth/health
- User entity: username, email, password hash, organization, role, permissions, audit timestamps
- UserRepository: username/email lookups, existence checks
- Frontend shell: glassmorphism layout, collapsible sidebar, top bar, overview dashboard
- Overview dashboard: security score, endpoint health, incidents, alerts, DEMO MODE toggle
- Demo data: predefined endpoints, incidents, alerts marked as DEMO DATA
- Tests: JUnit backend tests (health, auth service, JWT service), Vitest frontend tests
- Docker Compose: PostgreSQL, Redis, backend, frontend services

### Changed

- build.gradle.kts: added Spring Boot starter security, validation, data-jpa, data-redis, jjwt dependencies

## [1.0.0] — Planned

- Phase 1: Foundation (Backend Core, Auth, RBAC, Event Bus, API Gateway, Plugin System, Frontend Shell, Glassmorphism Design, Overview Dashboard)
- Phase 2: EDR (Windows Agent, Process Monitoring, File Integrity, System Events, Endpoint Management)
- Phase 3: Intelligence (OSINT, Threat Intelligence, IOC Database, Domain Analysis, Threat Graph)
- Phase 4: SIEM & Detection (Event Collector, Normalization, Detection Engine, Correlation Engine, Alert Management)
- Phase 5: Advanced Security (Network Intelligence, Digital Forensics, Vulnerability Management, Incident Response, Asset Management)
- Phase 6: Automation & AI (Workflow Builder, AI Analyst, Reports, Advanced Correlation)
- Phase 7: Production (Security Audit, Performance Testing, E2E Testing, Docker, CI/CD, Release 1.0)
