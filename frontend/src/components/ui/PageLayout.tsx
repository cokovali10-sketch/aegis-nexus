import { ReactNode } from 'react';

interface PageLayoutProps {
  title: string;
  subtitle?: string;
  children: ReactNode;
  actions?: ReactNode;
}

export function PageLayout({ title, subtitle, children, actions }: PageLayoutProps) {
  return (
    <div className="space-y-6 relative">
      {/* Background orbs */}
      <div className="fixed top-0 left-1/4 w-96 h-96 bg-primary/5 rounded-full blur-3xl pointer-events-none -z-10" />
      <div className="fixed bottom-0 right-1/4 w-96 h-96 bg-secondary/5 rounded-full blur-3xl pointer-events-none -z-10" />

      <header className="flex items-start justify-between gap-4 relative z-10">
        <div>
          <h1 className="text-3xl font-bold text-text-primary tracking-tight">
            {title}
          </h1>
          {subtitle && <p className="text-text-secondary mt-1">{subtitle}</p>}
        </div>
        {actions && <div className="flex items-center gap-3">{actions}</div>}
      </header>

      <div className="relative z-10">
        {children}
      </div>
    </div>
  );
}

export function StatCard({ title, value, change, icon, color = 'primary' }: {
  title: string;
  value: string | number;
  change?: string;
  icon: ReactNode;
  color?: 'primary' | 'success' | 'warning' | 'critical';
}) {
  const colors = {
    primary: { bg: 'bg-primary/15', text: 'text-primary', border: 'border-primary/30', glow: 'shadow-[0_0_16px_rgba(84,215,255,0.15)]' },
    success: { bg: 'bg-success/15', text: 'text-success', border: 'border-success/30', glow: 'shadow-[0_0_16px_rgba(57,217,160,0.15)]' },
    warning: { bg: 'bg-warning/15', text: 'text-warning', border: 'border-warning/30', glow: 'shadow-[0_0_16px_rgba(244,184,96,0.15)]' },
    critical: { bg: 'bg-critical/15', text: 'text-critical', border: 'border-critical/30', glow: 'shadow-[0_0_16px_rgba(255,83,107,0.15)]' },
  };
  const c = colors[color];

  return (
    <div className={`glass-card ${c.glow} p-5 hover:scale-[1.02] transition-transform duration-200`}>
      <div className="flex items-start justify-between">
        <div>
          <p className="text-xs text-text-secondary font-medium uppercase tracking-wider">{title}</p>
          <div className="flex items-baseline gap-2 mt-2">
            <span className="text-3xl font-bold text-text-primary">{value}</span>
            {change && <span className="text-sm font-medium text-text-secondary">{change}</span>}
          </div>
        </div>
        <div className={`w-12 h-12 rounded-xl ${c.bg} ${c.border} border flex items-center justify-center ${c.glow}`}>
          {icon}
        </div>
      </div>
    </div>
  );
}

export function GlassTable({ headers, children }: { headers: string[]; children: ReactNode }) {
  return (
    <div className="glass-panel overflow-hidden">
      <div className="overflow-x-auto">
        <table className="w-full text-left border-collapse">
          <thead>
            <tr className="border-b border-white/10">
              {headers.map((h) => (
                <th key={h} className="px-4 py-3 text-xs font-semibold text-text-secondary uppercase tracking-wider">{h}</th>
              ))}
            </tr>
          </thead>
          <tbody>
            {children}
          </tbody>
        </table>
      </div>
    </div>
  );
}

export function GlassRow({ children }: { children: ReactNode }) {
  return (
    <tr className="border-b border-white/5 hover:bg-white/5 transition-colors duration-150">
      {children}
    </tr>
  );
}

export function GlassCell({ children, className = '' }: { children: ReactNode; className?: string }) {
  return (
    <td className={`px-4 py-3 text-sm ${className}`}>{children}</td>
  );
}

export function StatusBadge({ status, color }: { status: string; color?: 'success' | 'warning' | 'critical' | 'info' | 'neutral' }) {
  const colors = {
    success: 'bg-success/15 text-success border-success/30',
    warning: 'bg-warning/15 text-warning border-warning/30',
    critical: 'bg-critical/15 text-critical border-critical/30',
    info: 'bg-primary/15 text-primary border-primary/30',
    neutral: 'bg-white/5 text-text-secondary border-white/10',
  };
  return (
    <span className={`px-2.5 py-1 rounded-full text-xs font-bold border ${colors[color || 'neutral']}`}>
      {status}
    </span>
  );
}

export function EmptyState({ icon, title, description }: { icon: ReactNode; title: string; description: string }) {
  return (
    <div className="glass-panel p-12 flex flex-col items-center justify-center text-center">
      <div className="w-16 h-16 rounded-2xl bg-primary/10 border border-primary/20 flex items-center justify-center mb-4">
        {icon}
      </div>
      <h3 className="text-lg font-semibold text-text-primary mb-2">{title}</h3>
      <p className="text-text-secondary text-sm max-w-md">{description}</p>
    </div>
  );
}
