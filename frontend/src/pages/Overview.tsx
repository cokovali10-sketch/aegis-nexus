import { useState } from 'react';
import { motion, AnimatePresence } from 'framer-motion';
import {
  AlertCircle, ShieldCheck, Monitor, Layers
} from 'lucide-react';
import { DEMO_DATA } from '../constants/demoData';

const statColors: Record<string, { bg: string; text: string; border: string; glow: string }> = {
  primary: { bg: 'bg-primary/15', text: 'text-primary', border: 'border-primary/30', glow: 'shadow-[0_0_20px_rgba(84,215,255,0.2)]' },
  success: { bg: 'bg-success/15', text: 'text-success', border: 'border-success/30', glow: 'shadow-[0_0_20px_rgba(57,217,160,0.2)]' },
  warning: { bg: 'bg-warning/15', text: 'text-warning', border: 'border-warning/30', glow: 'shadow-[0_0_20px_rgba(244,184,96,0.2)]' },
  critical: { bg: 'bg-critical/15', text: 'text-critical', border: 'border-critical/30', glow: 'shadow-[0_0_20px_rgba(255,83,107,0.2)]' },
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
  const trendIcon = trend === 'up' ? '↑' : trend === 'down' ? '↓' : '→';
  const trendColor = trend === 'up' ? 'text-success' : trend === 'down' ? 'text-critical' : 'text-text-secondary';

  return (
    <motion.div
      initial={{ opacity: 0, y: 20 }}
      animate={{ opacity: 1, y: 0 }}
      transition={{ duration: 0.4, ease: 'easeOut' }}
      className={`glass-card ${style.glow} p-5`}
    >
      <div className="flex items-start justify-between">
        <div>
          <p className="text-xs text-text-secondary font-medium uppercase tracking-wider">{title}</p>
          <div className="flex items-baseline gap-2 mt-2">
            <span className="text-3xl font-bold text-text-primary">{value}</span>
            <span className={`text-sm font-medium ${trendColor}`}>{trendIcon} {change}</span>
          </div>
        </div>
        <div className={`w-12 h-12 rounded-xl ${style.bg} ${style.border} border flex items-center justify-center ${style.glow}`}>
          <IconComponent className={`w-6 h-6 ${style.text}`} />
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
    <div className="space-y-6 relative">
      {/* Animated background orbs */}
      <div className="fixed top-20 left-20 w-72 h-72 bg-primary/5 rounded-full blur-3xl animate-pulse pointer-events-none" />
      <div className="fixed bottom-20 right-20 w-96 h-96 bg-secondary/5 rounded-full blur-3xl animate-pulse pointer-events-none" style={{ animationDelay: '1s' }} />

      <header className="flex items-start justify-between gap-4 relative z-10">
        <div>
          <h1 className="text-3xl font-bold text-text-primary tracking-tight">
            Security <span className="text-gradient">Overview</span>
          </h1>
          <p className="text-text-secondary mt-1">Unified view of your security posture across all subsystems</p>
        </div>
        <div className="flex items-center gap-3">
          <button
            className={`px-4 py-2 rounded-xl text-sm font-medium border transition-all duration-200 ${
              demoMode
                ? 'bg-primary/15 border-primary/30 text-primary shadow-[0_0_12px_rgba(84,215,255,0.2)]'
                : 'bg-white/5 border-white/10 text-text-secondary hover:bg-white/10'
            }`}
            onClick={() => setDemoMode(!demoMode)}
          >
            {demoMode ? '● DEMO MODE' : '○ DEMO MODE'}
          </button>
          <select className="input-glass" value={timeRange} onChange={(e) => setTimeRange(e.target.value)}>
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

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4 relative z-10">
        <StatCard title="Security Score" value={87} change="+3.2%" trend="up" icon={ShieldCheck} color="success" />
        <StatCard title="Active Endpoints" value={endpoints.filter((e) => e.status === 'online').length} change="4 total" trend="neutral" icon={Monitor} color="primary" />
        <StatCard title="Active Incidents" value={incidents.filter((i) => i.status !== 'resolved').length} change="2 open" trend="up" icon={AlertCircle} color="warning" />
        <StatCard title="Critical Alerts" value={alerts.filter((a) => a.severity === 'critical').length} change="1 new" trend="neutral" icon={Layers} color="critical" />
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-3 gap-6 relative z-10">
        <div className="lg:col-span-2 glass-panel p-6">
          <div className="flex items-center justify-between mb-4">
            <h2 className="text-lg font-bold text-text-primary">Recent Incidents</h2>
            <button className="text-sm text-primary hover:text-primary/80 underline-offset-4 hover:underline">View all →</button>
          </div>
          <div className="overflow-x-auto">
            <table className="w-full text-left border-collapse">
              <thead>
                <tr className="border-b border-white/10">
                  <th className="pb-3 text-xs font-semibold text-text-secondary uppercase tracking-wider">ID</th>
                  <th className="pb-3 text-xs font-semibold text-text-secondary uppercase tracking-wider">Title</th>
                  <th className="pb-3 text-xs font-semibold text-text-secondary uppercase tracking-wider">Severity</th>
                  <th className="pb-3 text-xs font-semibold text-text-secondary uppercase tracking-wider">Status</th>
                  <th className="pb-3 text-xs font-semibold text-text-secondary uppercase tracking-wider">Assignee</th>
                  <th className="pb-3 text-xs font-semibold text-text-secondary uppercase tracking-wider">Updated</th>
                </tr>
              </thead>
              <tbody>
                {incidents.map((inc) => (
                  <tr key={inc.id} className="border-b border-white/5 hover:bg-white/5 transition-colors">
                    <td className="py-3 text-xs font-mono text-text-primary">{inc.id}</td>
                    <td className="py-3 text-xs text-text-primary">{inc.title}</td>
                    <td className="py-3 text-xs">
                      <span className={`px-2 py-1 rounded-full text-xs font-bold border ${
                        inc.severity === 'critical' ? 'bg-critical/15 text-critical border-critical/30' :
                        inc.severity === 'high' ? 'bg-warning/15 text-warning border-warning/30' :
                        'bg-text-secondary/10 text-text-secondary border-white/10'
                      }`}>{inc.severity}</span>
                    </td>
                    <td className="py-3 text-xs">
                      <span className={`px-2 py-1 rounded-full text-xs font-bold border ${
                        inc.status === 'open' ? 'bg-critical/15 text-critical border-critical/30' :
                        inc.status === 'investigating' ? 'bg-warning/15 text-warning border-warning/30' :
                        'bg-success/15 text-success border-success/30'
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
              <div key={ep.id} className="flex items-center justify-between p-3 rounded-xl bg-white/5 border border-white/5 hover:bg-white/8 transition-colors">
                <div className="flex items-center gap-3">
                  <div className={`w-2.5 h-2.5 rounded-full ${ep.status === 'online' ? 'bg-success shadow-[0_0_6px_rgba(57,217,160,0.5)]' : ep.status === 'warning' ? 'bg-warning shadow-[0_0_6px_rgba(244,184,96,0.5)]' : 'bg-critical shadow-[0_0_6px_rgba(255,83,107,0.5)]'}`} />
                  <div>
                    <p className="text-sm font-medium text-text-primary">{ep.hostname}</p>
                    <p className="text-xs text-text-secondary font-mono">{ep.ip}</p>
                  </div>
                </div>
                <div className="flex items-center gap-3 text-right">
                  <div>
                    <p className="text-xs font-mono text-text-secondary">{ep.lastSeen}</p>
                    <p className="text-xs font-bold text-text-primary">Risk: {ep.riskScore}</p>
                  </div>
                </div>
              </div>
            ))}
          </div>
        </div>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6 relative z-10">
        <div className="glass-panel p-6">
          <h2 className="text-lg font-bold text-text-primary mb-4">Threat Activity</h2>
          <div className="h-64 bg-white/5 rounded-xl border border-white/5 flex items-center justify-center">
            <span className="text-text-secondary text-sm">Threat activity timeline (Recharts integration)</span>
          </div>
        </div>
        <div className="glass-panel p-6">
          <h2 className="text-lg font-bold text-text-primary mb-4">Network Activity</h2>
          <div className="h-64 bg-white/5 rounded-xl border border-white/5 flex items-center justify-center">
            <span className="text-text-secondary text-sm">Network connections graph</span>
          </div>
        </div>
      </div>
    </div>
  );
}
