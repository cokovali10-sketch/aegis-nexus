# ROADMAP.md

# AEGIS NEXUS Roadmap

## Phase 0 — Environment Audit ✅ Complete

- [x] Disk D: available (376.84 GB total, drive healthy)
- [x] Git 2.53.0 installed
- [x] Forge Hub found at D:\Forge-Factority
- [x] Java 21, Rust 1.99, Python 3.12, Node 24 installed
- [x] GitHub CLI authenticated (cokovali10-sketch)
- [x] Docker NOT installed (will be added later)
- [x] Project directory created: D:\Projects\AEGIS-NEXUS
- [x] Git repository initialized

## Phase 1 — Foundation (Current)

- [x] Project directory structure
- [x] .gitignore, README, LICENSE, CONTRIBUTING, SECURITY, CODE_OF_CONDUCT, CHANGELOG
- [x] ARCHITECTURE.md, ROADMAP.md
- [ ] Backend Core (Spring Boot project, config, health checks)
- [ ] Database (PostgreSQL schema, Flyway migrations)
- [ ] Authentication (JWT, BCrypt, login/register)
- [ ] RBAC (roles, permissions, method security)
- [ ] Event Bus (Spring ApplicationEvents or custom)
- [ ] API Gateway (versioned REST, global error handling)
- [ ] Plugin System (module interface)
- [ ] Frontend Shell (React, Vite, Tailwind, glassmorphism design system)
- [ ] Overview Dashboard (security overview, endpoint health, incidents, alerts)
- [ ] Demo data switch (DEMO MODE toggle)
- [ ] Unit tests (JUnit, Vitest)
- [ ] First Git commit

## Phase 2 — EDR (NEXUS EDR)

- [ ] Windows Endpoint Agent (Rust, transparent installation)
- [ ] Process monitoring
- [ ] File Integrity Monitoring
- [ ] System events collection
- [ ] Service monitoring
- [ ] Endpoint inventory dashboard
- [ ] Agent management (install, uninstall, configure)
- [ ] Detection integration with Sentinel
- [ ] EDR unit and integration tests

## Phase 3 — Intelligence (NEXUS INTEL)

- [ ] Public IP analysis
- [ ] Domain analysis
- [ ] DNS information
- [ ] WHOIS information
- [ ] Certificate Transparency
- [ ] IOC Database
- [ ] IOC Search
- [ ] Threat Feeds
- [ ] Threat Graph visualization
- [ ] Intelligence reports
- [ ] Documented public API integrations

## Phase 4 — SIEM & Detection (NEXUS SIEM + SENTINEL)

- [ ] Centralized event collector
- [ ] Log normalization (structured event format)
- [ ] Event Explorer (search, filter, sort)
- [ ] Saved queries
- [ ] Correlation rules
- [ ] Detection Rule Builder
- [ ] Live Event Stream (WebSocket)
- [ ] Alert Management
- [ ] Event Timeline
- [ ] Event export
- [ ] Detection metrics

## Phase 5 — Advanced Security

- [ ] Network Intelligence (device discovery, topology, DNS monitoring)
- [ ] Digital Forensics (file analysis, SHA-256, timeline, evidence registry)
- [ ] Vulnerability Management (CVE integration, CVSS scoring, SBOM)
- [ ] Incident Response (incident cards, investigation timeline, tasks)
- [ ] Asset Management (inventory, OS info, software, groups)

## Phase 6 — Automation & AI

- [ ] Workflow Builder (event triggers, conditional rules, actions)
- [ ] AI Analyst (event explanation, incident summarization, report drafts)
- [ ] Report generation (executive, technical)
- [ ] Advanced correlation
- [ ] Approval gates for dangerous actions

## Phase 7 — Production

- [ ] Security audit
- [ ] Performance testing
- [ ] E2E testing (Playwright)
- [ ] Docker + Docker Compose
- [ ] CI/CD (GitHub Actions)
- [ ] Documentation completion
- [ ] Release 1.0

## Success Criteria per Phase

Each phase is complete when:
- All planned features implemented
- Unit and integration tests pass
- Linting and type checking pass
- Build succeeds
- Documentation updated
- Changes committed with conventional commit messages
