import { useState, useEffect } from 'react';
import { Zap, Shield, AlertTriangle, Eye } from 'lucide-react';
import { PageLayout, StatCard, GlassTable, GlassRow, GlassCell, StatusBadge, EmptyState } from '../../components/ui/PageLayout';
import { request } from '../../services/edr';

interface Detection {
  id: string; ruleName: string; description: string; severity: string;
  source: string; timestamp: string; status: string;
}

export function SentinelPage() {
  const [detections, setDetections] = useState<Detection[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    request('/siem/alerts')
      .then((r: any) => setDetections(r.data || []))
      .catch(() => setDetections([]))
      .finally(() => setLoading(false));
  }, []);

  return (
    <PageLayout title="Detection Engine" subtitle="Behavioral analysis and rule-based detection">
      <div className="grid grid-cols-1 md:grid-cols-4 gap-4">
        <StatCard title="Total Detections" value={detections.length} icon={<Zap className="w-6 h-6 text-primary" />} color="primary" />
        <StatCard title="Critical" value={detections.filter(d => d.severity === 'CRITICAL').length} icon={<AlertTriangle className="w-6 h-6 text-critical" />} color="critical" />
        <StatCard title="High" value={detections.filter(d => d.severity === 'HIGH').length} icon={<Shield className="w-6 h-6 text-warning" />} color="warning" />
        <StatCard title="Active Rules" value={5} icon={<Eye className="w-6 h-6 text-success" />} color="success" />
      </div>

      {loading ? (
        <div className="glass-panel p-12 flex items-center justify-center">
          <div className="w-8 h-8 border-2 border-primary/30 border-t-primary rounded-full animate-spin" />
        </div>
      ) : detections.length === 0 ? (
        <EmptyState icon={<Zap className="w-8 h-8 text-primary" />} title="No Detections" description="No detection alerts have been triggered." />
      ) : (
        <GlassTable headers={['Rule', 'Description', 'Severity', 'Source', 'Status', 'Time']}>
          {detections.map((d) => (
            <GlassRow key={d.id}>
              <GlassCell className="text-text-primary font-medium">{d.ruleName || 'Detection'}</GlassCell>
              <GlassCell className="text-text-secondary">{d.description}</GlassCell>
              <GlassCell><StatusBadge status={d.severity} color={d.severity === 'CRITICAL' ? 'critical' : d.severity === 'HIGH' ? 'warning' : 'info'} /></GlassCell>
              <GlassCell className="text-text-secondary">{d.source}</GlassCell>
              <GlassCell><StatusBadge status={d.status} color={d.status === 'NEW' ? 'critical' : 'success'} /></GlassCell>
              <GlassCell className="text-text-secondary text-xs">{d.timestamp}</GlassCell>
            </GlassRow>
          ))}
        </GlassTable>
      )}
    </PageLayout>
  );
}
