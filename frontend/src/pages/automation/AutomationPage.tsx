import { useState, useEffect } from 'react';
import { Bot, Play, Pause, Zap } from 'lucide-react';
import { PageLayout, StatCard, GlassTable, GlassRow, GlassCell, StatusBadge, EmptyState } from '../../components/ui/PageLayout';
import { request } from '../../services/edr';

interface Workflow {
  id: string; name: string; description: string; triggerType: string;
  isEnabled: boolean; executionCount: number; lastExecutedAt: string | null;
}

export function AutomationPage() {
  const [workflows, setWorkflows] = useState<Workflow[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    request('/automation/workflows')
      .then((r: any) => setWorkflows(r.data || []))
      .catch(() => setWorkflows([]))
      .finally(() => setLoading(false));
  }, []);

  return (
    <PageLayout title="Security Automation" subtitle="Workflow builder and automation rules" actions={
      <button className="btn-primary">+ New Workflow</button>
    }>
      <div className="grid grid-cols-1 md:grid-cols-4 gap-4">
        <StatCard title="Total Workflows" value={workflows.length} icon={<Bot className="w-6 h-6 text-primary" />} color="primary" />
        <StatCard title="Enabled" value={workflows.filter(w => w.isEnabled).length} icon={<Play className="w-6 h-6 text-success" />} color="success" />
        <StatCard title="Disabled" value={workflows.filter(w => !w.isEnabled).length} icon={<Pause className="w-6 h-6 text-warning" />} color="warning" />
        <StatCard title="Total Executions" value={workflows.reduce((sum, w) => sum + w.executionCount, 0)} icon={<Zap className="w-6 h-6 text-critical" />} color="critical" />
      </div>

      {loading ? (
        <div className="glass-panel p-12 flex items-center justify-center">
          <div className="w-8 h-8 border-2 border-primary/30 border-t-primary rounded-full animate-spin" />
        </div>
      ) : workflows.length === 0 ? (
        <EmptyState icon={<Bot className="w-8 h-8 text-primary" />} title="No Workflows" description="Create your first automation workflow to get started." />
      ) : (
        <GlassTable headers={['Name', 'Trigger', 'Status', 'Executions', 'Last Run']}>
          {workflows.map((w) => (
            <GlassRow key={w.id}>
              <GlassCell className="text-text-primary font-medium">{w.name}</GlassCell>
              <GlassCell><StatusBadge status={w.triggerType} color="info" /></GlassCell>
              <GlassCell>{w.isEnabled ? <StatusBadge status="ENABLED" color="success" /> : <StatusBadge status="DISABLED" color="neutral" />}</GlassCell>
              <GlassCell className="font-mono text-text-primary">{w.executionCount}</GlassCell>
              <GlassCell className="text-text-secondary text-xs">{w.lastExecutedAt || 'Never'}</GlassCell>
            </GlassRow>
          ))}
        </GlassTable>
      )}
    </PageLayout>
  );
}
