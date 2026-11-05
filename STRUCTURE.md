# AEGIS NEXUS Directory Structure

AEGIS-NEXUS/
├── backend/                      # Java 21 + Spring Boot monolith
│   ├── gradle/
│   ├── build.gradle.kts
│   ├── settings.gradle.kts
│   ├── gradlew
│   ├── gradlew.bat
│   ├── src/main/java/com/aegis/nexus/
│   │   ├── core/                 # Core platform (event bus, health, config)
│   │   ├── security/             # Auth, RBAC, audit, JWT
│   │   ├── api/                  # REST controllers, DTOs, OpenAPI
│   │   ├── common/               # Shared models, exceptions, utils
│   │   └── modules/              # 12 subsystem modules
│   │       ├── edr/
│   │       ├── intel/
│   │       ├── siem/
│   │       ├── sentinel/
│   │       ├── forensics/
│   │       ├── network/
│   │       ├── vuln/
│   │       ├── response/
│   │       ├── assets/
│   │       ├── automation/
│   │       ├── risk/
│   │       └── command/
│   └── src/test/java/com/aegis/nexus/
├── frontend/                     # React + TypeScript + Vite + Tailwind
│   ├── package.json
│   ├── vite.config.ts
│   ├── tailwind.config.js
│   ├── tsconfig.json
│   ├── index.html
│   ├── src/
│   │   ├── main.tsx
│   │   ├── App.tsx
│   │   ├── components/
│   │   ├── pages/
│   │   ├── hooks/
│   │   ├── services/
│   │   ├── store/
│   │   ├── types/
│   │   ├── styles/
│   │   └── assets/
│   └── tests/
├── shared/                       # Shared types and contracts
│   └── types/
├── docs/                         # Documentation
│   ├── architecture/
│   ├── modules/
│   ├── api/
│   ├── database/
│   ├── deployment/
│   ├── security/
│   ├── development/
│   └── decisions/
├── rust-tools/                   # Rust system components
│   └── Cargo.toml
├── python-analytics/             # Python analytics service
│   └── pyproject.toml
├── docker-compose.yml
├── .env.example
├── .gitignore
├── LICENSE
├── README.md
├── CONTRIBUTING.md
├── SECURITY.md
├── CODE_OF_CONDUCT.md
├── CHANGELOG.md
├── ARCHITECTURE.md
├── ROADMAP.md
└── TESTING.md
