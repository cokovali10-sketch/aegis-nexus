import { useState, useEffect } from 'react';
import { AlertTriangle, Shield, Clock, User } from 'lucide-react';
import { PageLayout, StatCard, GlassTable, GlassRow, GlassCell, StatusBadge, EmptyState } from '../../components/ui/PageLayout';
import { request } from '../../services/edr';

interface Incident {
  id: string; title: string; description: string; severity: string;
  status: string; assignee: string | null; hostname: string; createdAt: string;
}

export function ResponsePage() {
  const [incidents, setIncidents] = useState<Incident[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    request('/response/incidents')
      .then((r: any) => setIncidents(r.data || []))
      .catch(() => setIncidents([]))
      .finally(() => setLoading(false));
  }, []);

  return (
    <PageLayout title="Incident Response" subtitle="Investigation and response management" actions={
      <button className="btn-primary">+ New Incident</button>
    }>
      <div className="grid grid-cols-1 md:grid-cols-4 gap-4">
        <StatCard title="Total Incidents" value={incidents.length} icon={<AlertTriangle className="w-6 h-6 text-primary" />} color="primary" />
        <StatCard title="Open" value={incidents.filter(i => i.status === 'OPEN').length} icon={<Shield className="w-6 h-6 text-critical" />} color="critical" />
        <StatCard title="Investigating" value={incidents.filter(i => i.status === 'INVESTIGATING').length} icon={<Clock className="w-6 h-6 text-warning" />} color="warning" />
        <StatCard title="Resolved" value={incidents.filter(i => i.status === 'RESOLVED').length} icon={<User className="w-6 h-6 text-success" />} color="success" />
      </div>

      {loading ? (
        <div className="glass-panel p-12 flex items-center justify-center">
          <div className="w-8 h-8 border-2 border-primary/30 border-t-primary rounded-full animate-spin" />
        </div>
      ) : incidents.length === 0 ? (
        <EmptyState icon={<AlertTriangle className="w-8 h-8 text-primary" />} title="No Incidents" description="No security incidents have been recorded." />
      ) : (
        <GlassTable headers={['ID', 'Title', 'Severity', 'Status', 'Hostname', 'Created']}>
          {incidents.map((inc) => (
            <GlassRow key={inc.id}>
              <GlassCell className="font-mono text-text-secondary text-xs">{inc.id.substring(0, 8)}</GlassCell>
              <GlassCell className="text-text-primary font-medium">{inc.title}</GlassCell>
              <GlassCell><StatusBadge status={inc.severity} color={inc.severity === 'CRITICAL' ? 'critical' : inc.severity === 'HIGH' ? 'warning' : 'info'} /></GlassCell>
              <GlassCell><StatusBadge status={inc.status} color={inc.status === 'RESOLVED' ? 'success' : inc.status === 'OPEN' ? 'critical' : 'warning'} /></GlassCell>
              <GlassCell className="text-text-secondary">{inc.hostname}</GlassCell>
              <GlassCell className="text-text-secondary text-xs">{inc.createdAt}</GlassCell>
            </GlassRow>
          ))}
        </GlassTable>
      )}
    </PageLayout>
  );
}
