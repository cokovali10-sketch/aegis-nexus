# README.md

# AEGIS NEXUS

## Unified Cyber Defense & Intelligence Platform

**SEE EVERYTHING. UNDERSTAND THREATS. DEFEND INTELLIGENTLY.**

AEGIS NEXUS is a unified cybersecurity platform combining Endpoint Detection & Response (EDR), Threat Intelligence & OSINT, SIEM, XDR, Digital Forensics, Vulnerability Management, Incident Response, Asset Management, Security Automation, and Risk Intelligence into a single Security Intelligence Core.

### Premium Glassmorphism UI

Modern dark-themed interface with glassmorphism surfaces, smooth animations, and professional SOC-grade design built with React, Tailwind CSS, and Framer Motion.

### Modular Architecture

Java 21 + Spring Boot backend with a modular monolith structure, PostgreSQL database, Redis caching, and clear boundaries between the 12 subsystems. React + TypeScript frontend with TypeScript contracts shared across the stack.

## Technology Stack

| Layer | Technology |
|-------|-----------|
| Backend | Java 21, Spring Boot 3, Spring Security |
| Frontend | React 18, TypeScript, Vite, Tailwind CSS, Framer Motion |
| Database | PostgreSQL, Redis, OpenSearch |
| System Components | Rust, Tokio, Serde |
| Analytics | Python 3.12, FastAPI, Pydantic |
| Infrastructure | Docker, Docker Compose, GitHub Actions |
| Testing | JUnit, Pytest, Vitest, Playwright |

## Getting Started

### Prerequisites

- Java 21+
- Node.js 20+
- Python 3.12+
- PostgreSQL
- Redis

### Backend

```bash
cd backend
./gradlew bootRun
```

### Frontend

```bash
cd frontend
npm install
npm run dev
```

### Docker

```bash
docker-compose up -d
```

## Project Status

Phase 0 — Environment Audit: Complete  
Phase 1 — Foundation: In Progress

See [ROADMAP.md](./ROADMAP.md) for detailed phase planning.

## Documentation

- [ARCHITECTURE.md](./ARCHITECTURE.md) — System architecture and module boundaries
- [TESTING.md](./TESTING.md) — Testing guidelines
- [SECURITY.md](./SECURITY.md) — Security policy
- [CONTRIBUTING.md](./CONTRIBUTING.md) — Contribution guide
- [CHANGELOG.md](./CHANGELOG.md) — Release history
- `docs/` — Detailed module, API, database, and deployment docs

## License

MIT License. See [LICENSE](./LICENSE).
