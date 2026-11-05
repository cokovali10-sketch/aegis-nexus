import { useState, useEffect } from 'react';
import { Shield, Monitor, AlertCircle, RefreshCw, StopCircle } from 'lucide-react';
import { edrApi } from '../../services/edr';

interface Endpoint {
  id: string;
  agentId: string;
  hostname: string;
  ipAddress: string;
  os: string;
  osVersion: string;
  agentVersion: string;
  status: string;
  lastSeenAt: string | null;
  riskScore: number;
  isIsolated: boolean;
}

export function EndpointSecurityPage() {
  const [endpoints, setEndpoints] = useState<Endpoint[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    edrApi.listEndpoints()
      .then(setEndpoints)
      .catch((err: any) => setError(err.message))
      .finally(() => setLoading(false));
  }, []);

  const handleHeartbeat = async (agentId: string) => {
    try {
      await edrApi.heartbeat(agentId);
      setEndpoints(prev => prev.map(e => e.agentId === agentId ? { ...e, status: 'ONLINE', lastSeenAt: new Date().toISOString() } : e));
    } catch (err: any) {
      alert(err.message);
    }
  };

  const handleIsolate = async (id: string, currentlyIsolated: boolean) => {
    try {
      await edrApi.isolateEndpoint(id, !currentlyIsolated);
      setEndpoints(prev => prev.map(e => e.id === id ? { ...e, isIsolated: !currentlyIsolated } : e));
    } catch (err: any) {
      alert(err.message);
    }
  };

  const statusColor = (status: string) => {
    switch (status) {
      case 'ONLINE': return 'text-success bg-success/10 border-success/20';
      case 'OFFLINE': return 'text-critical bg-critical/10 border-critical/20';
      case 'WARNING': return 'text-warning bg-warning/10 border-warning/20';
      case 'CRITICAL': return 'text-critical bg-critical/10 border-critical/20';
      case 'ISOLATED': return 'text-primary bg-primary/10 border-primary/20';
      default: return 'text-text-secondary bg-white/5 border-white/10';
    }
  };

  if (loading) {
    return (
      <div className="glass-panel p-12 flex flex-col items-center justify-center">
        <div className="w-8 h-8 border-4 border-primary/30 border-t-primary rounded-full animate-spin mb-4" />
        <p className="text-text-secondary">Loading endpoints...</p>
      </div>
    );
  }

  if (error) {
    return (
      <div className="glass-panel p-6">
        <div className="flex items-center gap-3">
          <AlertCircle className="w-6 h-6 text-critical" />
          <span className="text-text-primary">Error: {error}</span>
        </div>
      </div>
    );
  }

  const onlineCount = endpoints.filter(e => e.status === 'ONLINE').length;
  const isolatedCount = endpoints.filter(e => e.isIsolated).length;

  return (
    <div className="space-y-6">
      <div className="flex items-start justify-between gap-4">
        <div>
          <h1 className="text-2xl font-bold text-text-primary">Endpoint Security</h1>
          <p className="text-text-secondary mt-1">Manage and monitor endpoint agents across your infrastructure</p>
        </div>
        <button className="btn-primary flex items-center gap-2">
          <RefreshCw className="w-4 h-4" />
          Refresh
        </button>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
        <div className="glass-card">
          <div className="flex items-center gap-3">
            <div className="w-10 h-10 rounded-lg bg-primary/10 border border-primary/20 flex items-center justify-center">
              <Monitor className="w-5 h-5 text-primary" />
            </div>
            <div>
              <p className="text-xs text-text-secondary font-medium">Total Endpoints</p>
              <p className="text-xl font-bold text-text-primary">{endpoints.length}</p>
            </div>
          </div>
        </div>
        <div className="glass-card">
          <div className="flex items-center gap-3">
            <div className="w-10 h-10 rounded-lg bg-success/10 border border-success/20 flex items-center justify-center">
              <Shield className="w-5 h-5 text-success" />
            </div>
            <div>
              <p className="text-xs text-text-secondary font-medium">Online</p>
              <p className="text-xl font-bold text-text-primary">{onlineCount}</p>
            </div>
          </div>
        </div>
        <div className="glass-card">
          <div className="flex items-center gap-3">
            <div className="w-10 h-10 rounded-lg bg-warning/10 border border-warning/20 flex items-center justify-center">
              <StopCircle className="w-5 h-5 text-warning" />
            </div>
            <div>
              <p className="text-xs text-text-secondary font-medium">Isolated</p>
              <p className="text-xl font-bold text-text-primary">{isolatedCount}</p>
            </div>
          </div>
        </div>
      </div>

      <div className="glass-panel p-6">
        <div className="flex items-center justify-between mb-4">
          <h2 className="text-lg font-bold text-text-primary">Endpoint Inventory</h2>
          <button className="btn-glass flex items-center gap-2 text-sm">
            <Shield className="w-4 h-4" />
            Register Agent
          </button>
        </div>
        <div className="overflow-x-auto">
          <table className="w-full text-left border-collapse">
            <thead>
              <tr className="border-b border-white/10">
                <th className="pb-3 text-xs font-semibold text-text-secondary uppercase">Hostname</th>
                <th className="pb-3 text-xs font-semibold text-text-secondary uppercase">Agent ID</th>
                <th className="pb-3 text-xs font-semibold text-text-secondary uppercase">IP</th>
                <th className="pb-3 text-xs font-semibold text-text-secondary uppercase">OS</th>
                <th className="pb-3 text-xs font-semibold text-text-secondary uppercase">Status</th>
                <th className="pb-3 text-xs font-semibold text-text-secondary uppercase">Risk</th>
                <th className="pb-3 text-xs font-semibold text-text-secondary uppercase">Isolated</th>
                <th className="pb-3 text-xs font-semibold text-text-secondary uppercase">Actions</th>
              </tr>
            </thead>
            <tbody>
              {endpoints.map((ep) => (
                <tr key={ep.id} className="border-b border-white/5 hover:bg-white/5 transition-colors">
                  <td className="py-3 text-xs text-text-primary">{ep.hostname}</td>
                  <td className="py-3 text-xs font-mono text-text-secondary">{ep.agentId}</td>
                  <td className="py-3 text-xs text-text-secondary">{ep.ipAddress}</td>
                  <td className="py-3 text-xs text-text-secondary">{ep.os} {ep.osVersion}</td>
                  <td className="py-3 text-xs">
                    <span className={`px-2 py-1 rounded-full text-xs font-bold border ${statusColor(ep.status)}`}>
                      {ep.status}
                    </span>
                  </td>
                  <td className="py-3 text-xs font-mono text-text-primary">{ep.riskScore}</td>
                  <td className="py-3 text-xs">
                    <span className={`px-2 py-1 rounded-full text-xs font-bold border ${ep.isIsolated ? 'bg-critical/10 text-critical border-critical/20' : 'bg-text-secondary/10 text-text-secondary border-white/10'}`}>
                      {ep.isIsolated ? 'YES' : 'NO'}
                    </span>
                  </td>
                  <td className="py-3 text-xs flex gap-2">
                    <button className="px-2 py-1 text-xs text-primary hover:text-primary/80" onClick={() => handleHeartbeat(ep.agentId)}>
                      Heartbeat
                    </button>
                    <button className="px-2 py-1 text-xs text-critical hover:text-critical/80" onClick={() => handleIsolate(ep.id, ep.isIsolated)}>
                      {ep.isIsolated ? 'Restore' : 'Isolate'}
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}
