# ARCHITECTURE.md

# AEGIS NEXUS Architecture

## Overview

AEGIS NEXUS is a unified cybersecurity platform built as a **modular monolith** with clear boundaries between 12 subsystems. All modules share a common database, authentication system, event bus, API layer, and frontend shell.

## High-Level Architecture

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

## Design Principles

1. **Clear Layer Separation**: Controller → Service → Repository → Data
2. **Dependency Injection**: Spring-managed beans, no manual instantiation
3. **Unified Data Models**: Common event and entity types shared across modules
4. **Common Event Bus**: All modules publish and subscribe to platform events
5. **Centralized Error Handling**: Global exception handler with structured error responses
6. **Structured Logging**: JSON logs with correlation IDs
7. **API Versioning**: `/api/v1/` prefix, backward compatibility maintained
8. **Environment Configuration**: All configuration via environment variables
9. **Database Migrations**: Flyway or Liquibase for schema versioning
10. **RBAC**: Role-based access control on all endpoints
11. **Audit Logging**: All state-changing actions logged with actor, action, target, timestamp
12. **Plugin Architecture**: Module system with defined interfaces
13. **Health Checks**: `/actuator/health` for platform and dependency health
14. **No Cyclic Dependencies**: Module dependencies flow one direction only
15. **No Business Logic in UI**: Frontend is a presentation layer only

## Module Boundaries

Each module owns:
- Its domain entities and repository interfaces
- Its service layer and business logic
- Its REST controllers and DTOs
- Its module-specific tests

Modules communicate through:
- The shared event bus (asynchronous)
- The core API (synchronous, via well-defined interfaces)
- Shared database (via their own repositories, no direct cross-module table access)

## Security Architecture

- Authentication: JWT-based stateless authentication
- Authorization: Spring Security method-level security with RBAC
- Password hashing: BCrypt
- Secrets: Environment variables, never hardcoded
- CORS: Configured for allowed origins only
- Rate limiting: Per-endpoint and per-client
- CSRF: Enabled for state-changing browser-originated requests

## Frontend Architecture

- Shell with collapsible sidebar, top bar, main content area, right panel
- Glassmorphism design system (CSS variables, Tailwind, Framer Motion)
- Type-safe API client (generated or hand-written TypeScript types)
- State management via React Context / Zustand
- React Flow for correlation graph visualization
- Recharts for security metrics and analytics

## Infrastructure

- Docker Compose for local development (PostgreSQL, Redis, OpenSearch, backend, frontend)
- GitHub Actions for CI (build, test, lint, security scan)
- Health checks on all services

## AI Analyst Integration

- Separate model provider interface (not locked to one vendor)
- Supports local models and interchangeable providers
- Read-only by default; state-changing actions require authorization and confirmation
- AI outputs cite events and sources used

## Technology Versions

- Java: 21 (LTS)
- Spring Boot: 3.x
- Node.js: 20+
- React: 18
- Python: 3.12+
- PostgreSQL: 15+
- Redis: 7+
