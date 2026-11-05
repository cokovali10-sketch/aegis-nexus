import { useState, useEffect } from 'react';
import { Bug, Shield, CheckCircle, AlertCircle } from 'lucide-react';
import { PageLayout, StatCard, GlassTable, GlassRow, GlassCell, StatusBadge, EmptyState } from '../../components/ui/PageLayout';
import { request } from '../../services/edr';

interface Vuln {
  id: string; cveId: string; title: string; cvssScore: number; severity: string;
  affectedSoftware: string; isPatched: boolean; discoveredAt: string;
}

export function VulnPage() {
  const [vulns, setVulns] = useState<Vuln[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    request('/vuln')
      .then((r: any) => setVulns(r.data || []))
      .catch(() => setVulns([]))
      .finally(() => setLoading(false));
  }, []);

  return (
    <PageLayout title="Vulnerability Management" subtitle="CVE tracking, CVSS scoring, and patch management" actions={
      <button className="btn-primary">+ Add Vulnerability</button>
    }>
      <div className="grid grid-cols-1 md:grid-cols-4 gap-4">
        <StatCard title="Total" value={vulns.length} icon={<Bug className="w-6 h-6 text-primary" />} color="primary" />
        <StatCard title="Critical" value={vulns.filter(v => v.severity === 'CRITICAL').length} icon={<AlertCircle className="w-6 h-6 text-critical" />} color="critical" />
        <StatCard title="Unpatched" value={vulns.filter(v => !v.isPatched).length} icon={<Shield className="w-6 h-6 text-warning" />} color="warning" />
        <StatCard title="Patched" value={vulns.filter(v => v.isPatched).length} icon={<CheckCircle className="w-6 h-6 text-success" />} color="success" />
      </div>

      {loading ? (
        <div className="glass-panel p-12 flex items-center justify-center">
          <div className="w-8 h-8 border-2 border-primary/30 border-t-primary rounded-full animate-spin" />
        </div>
      ) : vulns.length === 0 ? (
        <EmptyState icon={<Bug className="w-8 h-8 text-primary" />} title="No Vulnerabilities" description="No vulnerabilities have been recorded." />
      ) : (
        <GlassTable headers={['CVE ID', 'Title', 'CVSS', 'Severity', 'Software', 'Status', 'Discovered']}>
          {vulns.map((v) => (
            <GlassRow key={v.id}>
              <GlassCell className="font-mono text-primary text-xs">{v.cveId}</GlassCell>
              <GlassCell className="text-text-primary font-medium">{v.title}</GlassCell>
              <GlassCell className="font-mono text-text-primary">{v.cvssScore.toFixed(1)}</GlassCell>
              <GlassCell><StatusBadge status={v.severity} color={v.severity === 'CRITICAL' ? 'critical' : v.severity === 'HIGH' ? 'warning' : 'info'} /></GlassCell>
              <GlassCell className="text-text-secondary">{v.affectedSoftware}</GlassCell>
              <GlassCell>{v.isPatched ? <StatusBadge status="PATCHED" color="success" /> : <StatusBadge status="UNPATCHED" color="critical" />}</GlassCell>
              <GlassCell className="text-text-secondary text-xs">{v.discoveredAt}</GlassCell>
            </GlassRow>
          ))}
        </GlassTable>
      )}
    </PageLayout>
  );
}
