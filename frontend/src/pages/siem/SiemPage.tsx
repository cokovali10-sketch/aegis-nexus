import { useState, useEffect } from 'react';
import { BarChart, Activity, AlertTriangle, FileText } from 'lucide-react';
import { request } from '../../services/edr';

interface SiemEvent {
  id: string;
  timestamp: string;
  eventType: string;
  severity: string;
  source: string;
  hostname: string;
  username: string;
  ipAddress: string;
  description: string;
  isAlert: boolean;
}

interface Alert {
  id: string;
  title: string;
  description: string;
  severity: string;
  status: string;
  source: string;
  eventId: string;
  ruleName: string;
  hostname: string;
  createdAt: string;
}

export function SiemPage() {
  const [events, setEvents] = useState<SiemEvent[]>([]);
  const [alerts, setAlerts] = useState<Alert[]>([]);
  const [loading, setLoading] = useState(true);
  const [activeTab, setActiveTab] = useState<'events' | 'alerts'>('events');

  useEffect(() => {
    Promise.all([
      request('/siem/events').then((r: any) => setEvents(r.data)),
      request('/siem/alerts').then((r: any) => setAlerts(r.data)),
    ])
      .catch(() => {})
      .finally(() => setLoading(false));
  }, []);

  const severityColor = (severity: string) => {
    switch (severity) {
      case 'CRITICAL': return 'text-critical bg-critical/10 border-critical/20';
      case 'HIGH': return 'text-warning bg-warning/10 border-warning/20';
      case 'MEDIUM': return 'text-primary bg-primary/10 border-primary/20';
      case 'LOW': return 'text-text-secondary bg-white/5 border-white/10';
      default: return 'text-text-secondary bg-white/5 border-white/10';
    }
  };

  const alertStatusColor = (status: string) => {
    switch (status) {
      case 'NEW': return 'text-critical bg-critical/10 border-critical/20';
      case 'ACKNOWLEDGED': return 'text-warning bg-warning/10 border-warning/20';
      case 'RESOLVED': return 'text-success bg-success/10 border-success/20';
      case 'FALSE_POSITIVE': return 'text-text-secondary bg-white/5 border-white/10';
      default: return 'text-text-secondary bg-white/5 border-white/10';
    }
  };

  if (loading) {
    return (
      <div className="glass-panel p-12 flex flex-col items-center justify-center">
        <div className="w-8 h-8 border-4 border-primary/30 border-t-primary rounded-full animate-spin mb-4" />
        <p className="text-text-secondary">Loading SIEM data...</p>
      </div>
    );
  }

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-2xl font-bold text-text-primary">SIEM — Security Information & Event Management</h1>
        <p className="text-text-secondary mt-1">Centralized event collection, correlation, and alert management</p>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-4 gap-4">
        <div className="glass-card">
          <div className="flex items-center gap-3">
            <div className="w-10 h-10 rounded-lg bg-primary/10 border border-primary/20 flex items-center justify-center">
              <FileText className="w-5 h-5 text-primary" />
            </div>
            <div>
              <p className="text-xs text-text-secondary font-medium">Total Events</p>
              <p className="text-xl font-bold text-text-primary">{events.length}</p>
            </div>
          </div>
        </div>
        <div className="glass-card">
          <div className="flex items-center gap-3">
            <div className="w-10 h-10 rounded-lg bg-critical/10 border border-critical/20 flex items-center justify-center">
              <AlertTriangle className="w-5 h-5 text-critical" />
            </div>
            <div>
              <p className="text-xs text-text-secondary font-medium">New Alerts</p>
              <p className="text-xl font-bold text-text-primary">{alerts.filter(a => a.status === 'NEW').length}</p>
            </div>
          </div>
        </div>
        <div className="glass-card">
          <div className="flex items-center gap-3">
            <div className="w-10 h-10 rounded-lg bg-warning/10 border border-warning/20 flex items-center justify-center">
              <Activity className="w-5 h-5 text-warning" />
            </div>
            <div>
              <p className="text-xs text-text-secondary font-medium">Critical Events</p>
              <p className="text-xl font-bold text-text-primary">{events.filter(e => e.severity === 'CRITICAL').length}</p>
            </div>
          </div>
        </div>
        <div className="glass-card">
          <div className="flex items-center gap-3">
            <div className="w-10 h-10 rounded-lg bg-success/10 border border-success/20 flex items-center justify-center">
              <BarChart className="w-5 h-5 text-success" />
            </div>
            <div>
              <p className="text-xs text-text-secondary font-medium">Resolved Alerts</p>
              <p className="text-xl font-bold text-text-primary">{alerts.filter(a => a.status === 'RESOLVED').length}</p>
            </div>
          </div>
        </div>
      </div>

      <div className="glass-panel p-6">
        <div className="flex gap-2 mb-4">
          <button
            className={`px-4 py-2 rounded-lg text-sm font-medium transition-colors ${activeTab === 'events' ? 'bg-primary/10 text-primary border border-primary/20' : 'text-text-secondary hover:bg-white/5'}`}
            onClick={() => setActiveTab('events')}
          >
            Events ({events.length})
          </button>
          <button
            className={`px-4 py-2 rounded-lg text-sm font-medium transition-colors ${activeTab === 'alerts' ? 'bg-primary/10 text-primary border border-primary/20' : 'text-text-secondary hover:bg-white/5'}`}
            onClick={() => setActiveTab('alerts')}
          >
            Alerts ({alerts.length})
          </button>
        </div>

        {activeTab === 'events' ? (
          <div className="overflow-x-auto">
            <table className="w-full text-left border-collapse">
              <thead>
                <tr className="border-b border-white/10">
                  <th className="pb-3 text-xs font-semibold text-text-secondary uppercase">Timestamp</th>
                  <th className="pb-3 text-xs font-semibold text-text-secondary uppercase">Type</th>
                  <th className="pb-3 text-xs font-semibold text-text-secondary uppercase">Severity</th>
                  <th className="pb-3 text-xs font-semibold text-text-secondary uppercase">Source</th>
                  <th className="pb-3 text-xs font-semibold text-text-secondary uppercase">Hostname</th>
                  <th className="pb-3 text-xs font-semibold text-text-secondary uppercase">Description</th>
                </tr>
              </thead>
              <tbody>
                {events.map((evt) => (
                  <tr key={evt.id} className="border-b border-white/5 hover:bg-white/5 transition-colors">
                    <td className="py-3 text-xs font-mono text-text-secondary">{evt.timestamp}</td>
                    <td className="py-3 text-xs">
                      <span className="px-2 py-1 rounded bg-white/5 text-text-primary font-mono text-[10px]">{evt.eventType}</span>
                    </td>
                    <td className="py-3 text-xs">
                      <span className={`px-2 py-1 rounded-full text-xs font-bold border ${severityColor(evt.severity)}`}>{evt.severity}</span>
                    </td>
                    <td className="py-3 text-xs text-text-secondary">{evt.source}</td>
                    <td className="py-3 text-xs text-text-primary">{evt.hostname}</td>
                    <td className="py-3 text-xs text-text-secondary">{evt.description}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        ) : (
          <div className="overflow-x-auto">
            <table className="w-full text-left border-collapse">
              <thead>
                <tr className="border-b border-white/10">
                  <th className="pb-3 text-xs font-semibold text-text-secondary uppercase">Title</th>
                  <th className="pb-3 text-xs font-semibold text-text-secondary uppercase">Severity</th>
                  <th className="pb-3 text-xs font-semibold text-text-secondary uppercase">Status</th>
                  <th className="pb-3 text-xs font-semibold text-text-secondary uppercase">Rule</th>
                  <th className="pb-3 text-xs font-semibold text-text-secondary uppercase">Hostname</th>
                  <th className="pb-3 text-xs font-semibold text-text-secondary uppercase">Created</th>
                </tr>
              </thead>
              <tbody>
                {alerts.map((alert) => (
                  <tr key={alert.id} className="border-b border-white/5 hover:bg-white/5 transition-colors">
                    <td className="py-3 text-xs text-text-primary">{alert.title}</td>
                    <td className="py-3 text-xs">
                      <span className={`px-2 py-1 rounded-full text-xs font-bold border ${severityColor(alert.severity)}`}>{alert.severity}</span>
                    </td>
                    <td className="py-3 text-xs">
                      <span className={`px-2 py-1 rounded-full text-xs font-bold border ${alertStatusColor(alert.status)}`}>{alert.status}</span>
                    </td>
                    <td className="py-3 text-xs text-text-secondary">{alert.ruleName}</td>
                    <td className="py-3 text-xs text-text-primary">{alert.hostname}</td>
                    <td className="py-3 text-xs font-mono text-text-secondary">{alert.createdAt}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </div>
    </div>
  );
}
