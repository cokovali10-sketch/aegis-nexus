import { useState, useEffect } from 'react';
import { Network, Server, Wifi, AlertTriangle } from 'lucide-react';
import { PageLayout, StatCard, GlassTable, GlassRow, GlassCell, StatusBadge, EmptyState } from '../../components/ui/PageLayout';
import { request } from '../../services/edr';

interface Device {
  id: string; ipAddress: string; macAddress: string; hostname: string;
  deviceType: string; vendor: string; status: string; isManaged: boolean; riskScore: number;
}

export function NetworkPage() {
  const [devices, setDevices] = useState<Device[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    request('/network/devices')
      .then((r: any) => setDevices(r.data || []))
      .catch(() => setDevices([]))
      .finally(() => setLoading(false));
  }, []);

  return (
    <PageLayout title="Network Intelligence" subtitle="Device discovery and network monitoring">
      <div className="grid grid-cols-1 md:grid-cols-4 gap-4">
        <StatCard title="Total Devices" value={devices.length} icon={<Network className="w-6 h-6 text-primary" />} color="primary" />
        <StatCard title="Online" value={devices.filter(d => d.status === 'ONLINE').length} icon={<Wifi className="w-6 h-6 text-success" />} color="success" />
        <StatCard title="Unmanaged" value={devices.filter(d => !d.isManaged).length} icon={<AlertTriangle className="w-6 h-6 text-warning" />} color="warning" />
        <StatCard title="High Risk" value={devices.filter(d => d.riskScore > 50).length} icon={<Server className="w-6 h-6 text-critical" />} color="critical" />
      </div>

      {loading ? (
        <div className="glass-panel p-12 flex items-center justify-center">
          <div className="w-8 h-8 border-2 border-primary/30 border-t-primary rounded-full animate-spin" />
        </div>
      ) : devices.length === 0 ? (
        <EmptyState icon={<Network className="w-8 h-8 text-primary" />} title="No Devices" description="No network devices have been discovered yet." />
      ) : (
        <GlassTable headers={['IP Address', 'MAC Address', 'Hostname', 'Type', 'Vendor', 'Status', 'Managed', 'Risk']}>
          {devices.map((d) => (
            <GlassRow key={d.id}>
              <GlassCell className="font-mono text-primary">{d.ipAddress}</GlassCell>
              <GlassCell className="font-mono text-text-secondary text-xs">{d.macAddress}</GlassCell>
              <GlassCell className="text-text-primary font-medium">{d.hostname}</GlassCell>
              <GlassCell><StatusBadge status={d.deviceType} color="info" /></GlassCell>
              <GlassCell className="text-text-secondary">{d.vendor}</GlassCell>
              <GlassCell><StatusBadge status={d.status} color={d.status === 'ONLINE' ? 'success' : 'neutral'} /></GlassCell>
              <GlassCell>{d.isManaged ? <StatusBadge status="YES" color="success" /> : <StatusBadge status="NO" color="warning" />}</GlassCell>
              <GlassCell className="font-mono text-text-primary">{d.riskScore}</GlassCell>
            </GlassRow>
          ))}
        </GlassTable>
      )}
    </PageLayout>
  );
}
