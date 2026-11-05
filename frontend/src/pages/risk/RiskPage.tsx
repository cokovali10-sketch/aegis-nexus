import { Shield, AlertTriangle, TrendingUp, Target } from 'lucide-react';
import { PageLayout, StatCard } from '../../components/ui/PageLayout';

export function RiskPage() {
  return (
    <PageLayout title="Risk Intelligence" subtitle="Security risk assessment and recommendations">
      <div className="grid grid-cols-1 md:grid-cols-4 gap-4">
        <StatCard title="Security Score" value={87} change="+3.2%" icon={<Shield className="w-6 h-6 text-success" />} color="success" />
        <StatCard title="Critical Risks" value={3} icon={<AlertTriangle className="w-6 h-6 text-critical" />} color="critical" />
        <StatCard title="High Risks" value={7} icon={<TrendingUp className="w-6 h-6 text-warning" />} color="warning" />
        <StatCard title="Mitigated" value={12} icon={<Target className="w-6 h-6 text-primary" />} color="primary" />
      </div>

      <div className="glass-panel p-8 text-center">
        <Shield className="w-16 h-16 text-primary mx-auto mb-4" />
        <h3 className="text-xl font-bold text-text-primary mb-2">Risk Intelligence Dashboard</h3>
        <p className="text-text-secondary">Comprehensive risk assessment with explainable methodology. Risk scores are calculated from asset risk, vulnerability risk, incident risk, and threat intelligence data.</p>
      </div>
    </PageLayout>
  );
}
