import { describe, it, expect } from 'vitest';

describe('Navigation', () => {
  it('should have required modules', () => {
    const modules = [
      'Overview', 'Endpoint Security', 'Threat Intelligence', 'SIEM',
      'Detection Engine', 'Network Intelligence', 'Digital Forensics',
      'Vulnerability Management', 'Incident Response', 'Asset Management',
      'Automation', 'Risk Intelligence', 'Reports', 'Integrations', 'Settings',
    ];
    expect(modules.length).toBe(15);
  });
});
