import { useState, useEffect } from 'react';
import { Activity, Shield, Server, Cpu } from 'lucide-react';
import { PageLayout, StatCard, GlassTable, GlassRow, GlassCell, StatusBadge, EmptyState } from '../../components/ui/PageLayout';
import { request } from '../../services/edr';

interface Asset {
  id: string; name: string; type: string; ipAddress: string; hostname: string;
  os: string; owner: string; riskScore: number; isCritical: boolean;
}

export function AssetsPage() {
  const [assets, setAssets] = useState<Asset[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    request('/assets')
      .then((r: any) => setAssets(r.data || []))
      .catch(() => setAssets([]))
      .finally(() => setLoading(false));
  }, []);

  return (
    <PageLayout title="Asset Management" subtitle="Infrastructure and asset inventory across your organization" actions={
      <button className="btn-primary">+ Add Asset</button>
    }>
      <div className="grid grid-cols-1 md:grid-cols-4 gap-4">
        <StatCard title="Total Assets" value={assets.length} icon={<Server className="w-6 h-6 text-primary" />} color="primary" />
        <StatCard title="Critical" value={assets.filter(a => a.isCritical).length} icon={<Shield className="w-6 h-6 text-critical" />} color="critical" />
        <StatCard title="High Risk" value={assets.filter(a => a.riskScore > 50).length} icon={<Activity className="w-6 h-6 text-warning" />} color="warning" />
        <StatCard title="Servers" value={assets.filter(a => a.type === 'SERVER').length} icon={<Cpu className="w-6 h-6 text-success" />} color="success" />
      </div>

      {loading ? (
        <div className="glass-panel p-12 flex items-center justify-center">
          <div className="w-8 h-8 border-2 border-primary/30 border-t-primary rounded-full animate-spin" />
        </div>
      ) : assets.length === 0 ? (
        <EmptyState icon={<Server className="w-8 h-8 text-primary" />} title="No Assets Found" description="Start by adding your first asset to the inventory." />
      ) : (
        <GlassTable headers={['Name', 'Type', 'IP Address', 'Hostname', 'OS', 'Risk', 'Critical']}>
          {assets.map((a) => (
            <GlassRow key={a.id}>
              <GlassCell className="text-text-primary font-medium">{a.name}</GlassCell>
              <GlassCell><StatusBadge status={a.type} color="info" /></GlassCell>
              <GlassCell className="font-mono text-text-secondary">{a.ipAddress}</GlassCell>
              <GlassCell className="text-text-secondary">{a.hostname}</GlassCell>
              <GlassCell className="text-text-secondary">{a.os}</GlassCell>
              <GlassCell className="font-mono text-text-primary">{a.riskScore}</GlassCell>
              <GlassCell>{a.isCritical ? <StatusBadge status="YES" color="critical" /> : <StatusBadge status="NO" color="neutral" />}</GlassCell>
            </GlassRow>
          ))}
        </GlassTable>
      )}
    </PageLayout>
  );
}
