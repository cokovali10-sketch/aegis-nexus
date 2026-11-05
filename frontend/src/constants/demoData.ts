export const DEMO_DATA: Record<string, unknown> = {
  securityScore: 87,
  endpoints: [
    { id: 'ep-001', hostname: 'WKS-EXCH-01', ip: '10.0.1.15', os: 'Windows 11', osVersion: '23H2', agentVersion: '1.0.4', status: 'online' as const, lastSeen: '2s ago', riskScore: 12 },
    { id: 'ep-002', hostname: 'SRV-DC-01', ip: '10.0.2.10', os: 'Windows Server', osVersion: '2022', agentVersion: '1.0.4', status: 'online' as const, lastSeen: '1s ago', riskScore: 8 },
    { id: 'ep-003', hostname: 'LPT-SEC-01', ip: '10.0.1.45', os: 'Ubuntu', osVersion: '22.04 LTS', agentVersion: '1.0.2', status: 'warning' as const, lastSeen: '45s ago', riskScore: 34 },
    { id: 'ep-004', hostname: 'WKS-FIN-01', ip: '10.0.1.78', os: 'Windows 11', osVersion: '23H2', agentVersion: '1.0.4', status: 'online' as const, lastSeen: '12s ago', riskScore: 18 },
  ],
  incidents: [
    { id: 'INC-001', title: 'Suspicious Process on WKS-EXCH-01', description: 'Unusual PowerShell execution detected', severity: 'high' as const, status: 'investigating' as const, assignee: 'Analyst 1', createdAt: '2026-11-05T10:00:00Z', updatedAt: '2026-11-05T11:30:00Z' },
    { id: 'INC-002', title: 'Brute Force Attempt on SRV-DC-01', description: 'Multiple failed SSH login attempts', severity: 'critical' as const, status: 'open' as const, assignee: null, createdAt: '2026-11-05T08:15:00Z', updatedAt: '2026-11-05T08:15:00Z' },
  ],
  alerts: [
    { id: 'ALT-001', title: 'PowerShell Execution', severity: 'high' as const, source: 'EDR Agent', timestamp: '2026-11-05T11:45:00Z', status: 'new' as const, deviceId: 'ep-001' },
    { id: 'ALT-002', title: 'Failed SSH Login', severity: 'medium' as const, source: 'SIEM Collector', timestamp: '2026-11-05T11:30:00Z', status: 'new' as const, deviceId: 'ep-003' },
  ],
} as const;
