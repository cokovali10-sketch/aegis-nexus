export interface User {
  id: string;
  username: string;
  email: string;
  role: string;
  organization: string;
}

export interface Endpoint {
  id: string;
  hostname: string;
  ip: string;
  os: string;
  osVersion: string;
  agentVersion: string;
  status: 'online' | 'offline' | 'warning' | 'critical';
  lastSeen: string;
  riskScore: number;
}

export interface Incident {
  id: string;
  title: string;
  description: string;
  severity: 'low' | 'medium' | 'high' | 'critical';
  status: 'open' | 'investigating' | 'resolved' | 'closed';
  assignee: string | null;
  createdAt: string;
  updatedAt: string;
}

export interface Alert {
  id: string;
  title: string;
  severity: 'low' | 'medium' | 'high' | 'critical';
  source: string;
  timestamp: string;
  status: 'new' | 'acknowledged' | 'resolved' | 'false_positive';
  deviceId: string;
}

export interface SecurityMetric {
  label: string;
  value: number | string;
  change: number;
  trend: 'up' | 'down' | 'neutral';
  status: 'good' | 'warning' | 'critical';
}

export interface DemoData {
  endpoints: Endpoint[];
  incidents: Incident[];
  alerts: Alert[];
  securityScore: number;
}
