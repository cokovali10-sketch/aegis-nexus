import { useState, useEffect } from 'react';
import { FileSearch, Shield, Hash, Clock } from 'lucide-react';
import { PageLayout, StatCard, GlassTable, GlassRow, GlassCell, StatusBadge, EmptyState } from '../../components/ui/PageLayout';
import { request } from '../../services/edr';

interface Evidence {
  id: string; caseId: string; fileName: string; sha256: string; fileSize: number;
  fileType: string; collectedBy: string; integrityVerified: boolean; collectedAt: string;
}

export function ForensicsPage() {
  const [evidence, setEvidence] = useState<Evidence[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    request('/forensics/evidence')
      .then((r: any) => setEvidence(r.data || []))
      .catch(() => setEvidence([]))
      .finally(() => setLoading(false));
  }, []);

  return (
    <PageLayout title="Digital Forensics" subtitle="Evidence collection and integrity verification">
      <div className="grid grid-cols-1 md:grid-cols-4 gap-4">
        <StatCard title="Evidence Items" value={evidence.length} icon={<FileSearch className="w-6 h-6 text-primary" />} color="primary" />
        <StatCard title="Cases" value={new Set(evidence.map(e => e.caseId)).size} icon={<Shield className="w-6 h-6 text-success" />} color="success" />
        <StatCard title="Verified" value={evidence.filter(e => e.integrityVerified).length} icon={<Hash className="w-6 h-6 text-warning" />} color="warning" />
        <StatCard title="Pending" value={evidence.filter(e => !e.integrityVerified).length} icon={<Clock className="w-6 h-6 text-critical" />} color="critical" />
      </div>

      {loading ? (
        <div className="glass-panel p-12 flex items-center justify-center">
          <div className="w-8 h-8 border-2 border-primary/30 border-t-primary rounded-full animate-spin" />
        </div>
      ) : evidence.length === 0 ? (
        <EmptyState icon={<FileSearch className="w-8 h-8 text-primary" />} title="No Evidence" description="No forensic evidence has been collected yet." />
      ) : (
        <GlassTable headers={['Case ID', 'File Name', 'SHA-256', 'Size', 'Type', 'Verified', 'Collected']}>
          {evidence.map((e) => (
            <GlassRow key={e.id}>
              <GlassCell className="font-mono text-primary text-xs">{e.caseId}</GlassCell>
              <GlassCell className="text-text-primary font-medium">{e.fileName}</GlassCell>
              <GlassCell className="font-mono text-text-secondary text-xs">{e.sha256.substring(0, 16)}...</GlassCell>
              <GlassCell className="text-text-secondary">{(e.fileSize / 1024).toFixed(1)} KB</GlassCell>
              <GlassCell className="text-text-secondary">{e.fileType}</GlassCell>
              <GlassCell>{e.integrityVerified ? <StatusBadge status="VERIFIED" color="success" /> : <StatusBadge status="PENDING" color="warning" />}</GlassCell>
              <GlassCell className="text-text-secondary text-xs">{e.collectedAt}</GlassCell>
            </GlassRow>
          ))}
        </GlassTable>
      )}
    </PageLayout>
  );
}
