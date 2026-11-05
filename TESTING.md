# TESTING.md

# Testing Guidelines

AEGIS NEXUS follows a test-driven, quality-first approach. Every change must be accompanied by tests.

## Test Categories

| Category | Tool | Scope |
|----------|------|-------|
| Unit Tests | JUnit 5, Pytest, Vitest | Individual classes/functions |
| Integration Tests | JUnit, Pytest | Module interactions, DB, API |
| API Tests | REST Assured / Spring MockMvc | Endpoint contracts |
| Type Checking | tsc | Frontend TypeScript |
| Linting | Checkstyle, ESLint, ruff, clippy | Code style |
| Build Tests | ./gradlew build | Full compilation |
| Frontend Tests | Vitest | React components |
| E2E Tests | Playwright | Critical user flows |
| Security Checks | Dependency scan, SAST | Vulnerabilities |

## Running Tests

### Backend (Java)

```bash
cd backend
./gradlew test          # Unit + integration tests
./gradlew check         # All checks including linting
```

### Frontend (TypeScript/React)

```bash
cd frontend
npm run test            # Vitest unit tests
npm run test:e2e        # Playwright E2E tests
npm run lint            # ESLint
npm run typecheck       # TypeScript compiler
```

### Python Analytics

```bash
cd python-analytics
pytest
```

### Rust Tools

```bash
cd rust-tools
cargo test
```

## Test Standards

- No fake tests that always pass
- No TODO placeholders in test code
- Tests must verify real behavior
- Demo data must be marked as DEMO DATA
- Tests run before every commit
- Failed tests block commits

## Coverage Goals

- Backend: 80%+ line coverage on core modules
- Frontend: 70%+ component coverage
- Critical paths (auth, RBAC, audit): 95%+
