# CONTRIBUTING.md

# Contributing to AEGIS NEXUS

Thank you for considering contributing to AEGIS NEXUS. This is a professional cybersecurity platform, and all contributions must meet engineering-grade standards.

## How to Contribute

1. Fork the repository and create a feature branch from `main`.
2. Implement your change following the code style and architectural principles.
3. Add or update tests for the affected component.
4. Run the full test suite and linting before submitting.
5. Open a pull request with a clear description and link to any related issue.

## Commit Convention

Use [Conventional Commits](https://www.conventionalcommits.org/):

```
feat(edr): add endpoint process inventory
fix(siem): correct event normalization timestamp
docs(architecture): document module boundaries
test(core): add event bus integration tests
```

Allowed types: `feat`, `fix`, `refactor`, `test`, `docs`, `build`, `ci`, `perf`, `security`.

## Code Style

- Java: Google Java Style, 4-space indentation.
- TypeScript: Prettier, 2-space indentation, double quotes.
- Rust: rustfmt default.
- Python: Black, 4-space indentation.
- No comments unless explicitly required (per project rules).
- No TODO placeholders in production code.

## Testing

Every change must be accompanied by tests. Run:

- Java: `./gradlew test`
- Python: `pytest`
- Frontend: `npm run test` and `npm run test:e2e`

## Security

Do not commit secrets, tokens, passwords, or private keys. See [SECURITY.md](./SECURITY.md) for vulnerability reporting.

## License

By contributing, you agree that your contributions will be licensed under the MIT License.
