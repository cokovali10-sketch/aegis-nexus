# SECURITY.md

# Security Policy

AEGIS NEXUS is a defensive cybersecurity platform. Security of the platform itself is paramount.

## Supported Versions

| Version | Supported |
|---------|-----------|
| 1.0.x   | Yes       |

## Reporting a Vulnerability

Please disclose security vulnerabilities responsibly. Do not create public issues for security bugs.

Contact the maintainers through the repository security advisory feature or email security@aegis.local.

Include:
- Description of the vulnerability
- Steps to reproduce
- Affected components and versions
- Potential impact
- Suggested remediation (optional)

## Platform Security Requirements

- Secure authentication with password hashing (bcrypt/argon2)
- Role-Based Access Control (RBAC) with least privilege
- Audit logging for all state-changing actions
- Input validation on all API endpoints
- API rate limiting
- CSRF protection where applicable
- SQL injection protection via parameterized queries
- XSS protection via output encoding
- Secure file handling
- TLS for network connections
- Authenticated agent communication
- Secrets management (no hardcoded credentials)

## What We Do Not Build

- Hidden remote access or backdoors
- Malware or offensive tools
- Authorization bypass mechanisms
- Unauthorized device management
- Unsigned or opaque agent binaries

All agents operate in a transparent, visible mode with explicit installation and uninstallation mechanisms.
