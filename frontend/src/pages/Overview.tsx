import { useState } from 'react';
import { motion, AnimatePresence } from 'framer-motion';
import {
  Activity, AlertCircle, ShieldCheck, Monitor, Layers, BarChart3, TrendingUp
} from 'lucide-react';
import { DEMO_DATA } from '../constants/demoData';

const statColors: Record<string, { bg: string; text: string; border: string }> = {
  primary: { bg: 'bg-primary/10', text: 'text-primary', border: 'border-primary/20' },
  success: { bg: 'bg-success/10', text: 'text-success', border: 'border-success/20' },
  warning: { bg: 'bg-warning/10', text: 'text-warning', border: 'border-warning/20' },
  critical: { bg: 'bg-critical/10', text: 'text-critical', border: 'border-critical/20' },
};

interface StatCardProps {
  title: string;
  value: string | number;
  change: string;
  trend: 'up' | 'down' | 'neutral';
  icon: React.ComponentType<{ className?: string }>;
  color: keyof typeof statColors;
}

function StatCard({ title, value, change, trend, icon: IconComponent, color }: StatCardProps) {
  const style = statColors[color];
  const trendIcon = trend === 'up' ? <TrendingUp className="w-4 h-4" /> : trend === 'down' ? <Activity className="w-4 h-4" /> : <BarChart3 className="w-4 h-4" />;
  const trendColor = trend === 'up' ? 'text-success' : trend === 'down' ? 'text-critical' : 'text-text-secondary';

  return (
    <motion.div
      initial={{ opacity: 0, y: 10 }}
      animate={{ opacity: 1, y: 0 }}
      transition={{ duration: 0.3 }}
      className="glass-card"
    >
      <div className="flex items-start justify-between">
        <div>
          <p className="text-sm text-text-secondary font-medium">{title}</p>
          <div className="flex items-baseline gap-1 mt-1">
            <span className="text-2xl font-bold text-text-primary">{value}</span>
            <span className={`text-sm font-medium ${trendColor}`}>{trendIcon} {change}</span>
          </div>
        </div>
        <div className={`w-10 h-10 rounded-lg ${style.bg} ${style.border} flex items-center justify-center`}>
          <IconComponent />
        </div>
      </div>
    </motion.div>
  );
}

export function Overview() {
  const [demoMode, setDemoMode] = useState(true);
  const [timeRange, setTimeRange] = useState('24h');

  const endpoints = DEMO_DATA.endpoints as Array<{
    id: string; hostname: string; ip: string; os: string; osVersion: string;
    agentVersion: string; status: string; lastSeen: string; riskScore: number;
  }>;

  const incidents = DEMO_DATA.incidents as Array<{
    id: string; title: string; description: string; severity: string;
    status: string; assignee: string | null; createdAt: string; updatedAt: string;
  }>;

  const alerts = DEMO_DATA.alerts as Array<{
    id: string; title: string; severity: string; source: string;
    timestamp: string; status: string; deviceId: string;
  }>;

  return (
    <div className="space-y-6">
      <header className="flex items-start justify-between gap-4">
        <div>
          <h1 className="text-2xl font-bold text-text-primary">Security Overview</h1>
          <p className="text-text-secondary mt-1">Unified view of your security posture across all subsystems</p>
        </div>
        <div className="flex items-center gap-3">
          <button className={`px-3 py-2 rounded-lg text-sm font-medium border transition-colors ${
            demoMode ? 'bg-primary/10 border-primary/20 text-primary' : 'bg-white/5 border-white/10 text-text-secondary hover:bg-white/10'
          }`} onClick={() => setDemoMode(!demoMode)}>
            {demoMode ? 'DEMO MODE ON' : 'DEMO MODE OFF'}
          </button>
          <select className="bg-panel border border-glass-border text-text-primary px-3 py-2 rounded-lg focus:border-primary focus:outline-none" value={timeRange} onChange={(e) => setTimeRange(e.target.value)}>
            <option value="24h">Last 24 Hours</option>
            <option value="7d">Last 7 Days</option>
            <option value="30d">Last 30 Days</option>
          </select>
        </div>
      </header>

      <AnimatePresence>
        {demoMode && (
    <motion.div
      initial={{ opacity: 0, y: -10 }}
      animate={{ opacity: 1, y: 0 }}
      exit={{ opacity: 0, y: -10 }}
      className="glass-panel p-4 border border-warning/30"
    >
            <div className="flex items-center gap-2">
              <AlertCircle className="w-4 h-4 text-warning" />
              <span className="text-sm text-warning font-medium">DEMO DATA — This is demonstration data for first-run preview</span>
            </div>
          </motion.div>
        )}
      </AnimatePresence>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">
        <StatCard title="Security Score" value={87} change="+3.2%" trend="up" icon={ShieldCheck} color="success" />
        <StatCard title="Active Endpoints" value={endpoints.filter((e) => e.status === 'online').length} change="4 total" trend="neutral" icon={Monitor} color="primary" />
        <StatCard title="Active Incidents" value={incidents.filter((i) => i.status !== 'resolved').length} change="2 open" trend="up" icon={AlertCircle} color="warning" />
        <StatCard title="Critical Alerts" value={alerts.filter((a) => a.severity === 'critical').length} change="1 new" trend="neutral" icon={Layers} color="critical" />
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
        <div className="lg:col-span-2 glass-panel p-6">
          <div className="flex items-center justify-between mb-4">
            <h2 className="text-lg font-bold text-text-primary">Recent Incidents</h2>
            <button className="text-sm text-primary hover:text-primary/80 underline-offset-4 hover:underline">View all</button>
          </div>
          <div className="overflow-x-auto">
            <table className="w-full text-left border-collapse">
              <thead>
                <tr className="border-b border-white/10">
                  <th className="pb-3 text-xs font-semibold text-text-secondary uppercase">ID</th>
                  <th className="pb-3 text-xs font-semibold text-text-secondary uppercase">Title</th>
                  <th className="pb-3 text-xs font-semibold text-text-secondary uppercase">Severity</th>
                  <th className="pb-3 text-xs font-semibold text-text-secondary uppercase">Status</th>
                  <th className="pb-3 text-xs font-semibold text-text-secondary uppercase">Assignee</th>
                  <th className="pb-3 text-xs font-semibold text-text-secondary uppercase">Updated</th>
                </tr>
              </thead>
              <tbody>
                {incidents.map((inc) => (
                  <tr key={inc.id} className="border-b border-white/5 hover:bg-white/5 transition-colors">
                    <td className="py-3 text-xs font-mono text-text-primary">{inc.id}</td>
                    <td className="py-3 text-xs text-text-primary">{inc.title}</td>
                    <td className="py-3 text-xs">
                      <span className={`px-2 py-1 rounded-full text-xs font-bold ${
                        inc.severity === 'critical' ? 'bg-critical/10 text-critical border border-critical/20' :
                        inc.severity === 'high' ? 'bg-warning/10 text-warning border border-warning/20' :
                        'bg-text-secondary/10 text-text-secondary border border-white/10'
                      }`}>{inc.severity}</span>
                    </td>
                    <td className="py-3 text-xs">
                      <span className={`px-2 py-1 rounded-full text-xs font-bold ${
                        inc.status === 'open' ? 'bg-critical/10 text-critical border border-critical/20' :
                        inc.status === 'investigating' ? 'bg-warning/10 text-warning border border-warning/20' :
                        'bg-success/10 text-success border border-success/20'
                      }`}>{inc.status}</span>
                    </td>
                    <td className="py-3 text-xs text-text-secondary">{inc.assignee ?? 'Unassigned'}</td>
                    <td className="py-3 text-xs font-mono text-text-secondary">{inc.updatedAt}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>

        <div className="glass-panel p-6">
          <div className="flex items-center justify-between mb-4">
            <h2 className="text-lg font-bold text-text-primary">Endpoint Health</h2>
            <span className="text-xs text-text-secondary">Agent status</span>
          </div>
          <div className="space-y-3">
            {endpoints.map((ep) => (
              <div key={ep.id} className="flex items-center justify-between p-3 rounded-lg bg-white/5 border border-white/5">
                <div className="flex items-center gap-3">
                  <div className={`w-2 h-2 rounded-full ${ep.status === 'online' ? 'bg-success' : ep.status === 'warning' ? 'bg-warning' : 'bg-critical'}`} />
                  <div>
                    <p className="text-xs font-medium text-text-primary">{ep.hostname}</p>
                    <p className="text-[10px] text-text-secondary font-mono">{ep.ip}</p>
                  </div>
                </div>
                <div className="flex items-center gap-3 text-right">
                  <div>
                    <p className="text-xs font-mono text-text-secondary">{ep.lastSeen}</p>
                    <p className="text-xs font-bold text-text-primary">{ep.riskScore}</p>
                  </div>
                </div>
              </div>
            ))}
          </div>
        </div>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <div className="glass-panel p-6">
          <h2 className="text-lg font-bold text-text-primary mb-4">Threat Activity</h2>
          <div className="h-64 bg-white/5 rounded-lg border border-white/5 flex items-center justify-center">
            <span className="text-text-secondary text-sm">Threat activity timeline (Recharts integration)</span>
          </div>
        </div>
        <div className="glass-panel p-6">
          <h2 className="text-lg font-bold text-text-primary mb-4">Network Activity</h2>
          <div className="h-64 bg-white/5 rounded-lg border border-white/5 flex items-center justify-center">
            <span className="text-text-secondary text-sm">Network connections graph</span>
          </div>
        </div>
      </div>
    </div>
  );
}
