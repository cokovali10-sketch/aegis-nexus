import { useState } from 'react';
import { BarChart, PieChart } from 'lucide-react';
import { PageLayout } from '../../components/ui/PageLayout';
import { request } from '../../services/edr';

export function ReportsPage() {
  const [loading, setLoading] = useState(false);

  const generateReport = async (type: string) => {
    setLoading(true);
    try {
      const res = await request(`/reports/${type}`);
      console.log('Report generated:', res);
    } catch (err) {
      console.error('Failed to generate report:', err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <PageLayout title="Reports" subtitle="Executive and technical security reports" actions={
      <button className="btn-primary" onClick={() => generateReport('executive-summary')} disabled={loading}>
        {loading ? 'Generating...' : 'Generate Report'}
      </button>
    }>
      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
        <div className="glass-panel p-6 hover:scale-[1.01] transition-transform cursor-pointer" onClick={() => generateReport('executive-summary')}>
          <div className="flex items-center gap-4 mb-4">
            <div className="w-12 h-12 rounded-xl bg-primary/15 border border-primary/30 flex items-center justify-center">
              <BarChart className="w-6 h-6 text-primary" />
            </div>
            <div>
              <h3 className="text-lg font-bold text-text-primary">Executive Summary</h3>
              <p className="text-text-secondary text-sm">High-level security posture overview</p>
            </div>
          </div>
          <p className="text-text-secondary text-sm">Includes security score, endpoint health, incident status, vulnerability management, and recommendations.</p>
        </div>

        <div className="glass-panel p-6 hover:scale-[1.01] transition-transform cursor-pointer" onClick={() => generateReport('technical')}>
          <div className="flex items-center gap-4 mb-4">
            <div className="w-12 h-12 rounded-xl bg-secondary/15 border border-secondary/30 flex items-center justify-center">
              <PieChart className="w-6 h-6 text-secondary" />
            </div>
            <div>
              <h3 className="text-lg font-bold text-text-primary">Technical Report</h3>
              <p className="text-text-secondary text-sm">Detailed technical analysis</p>
            </div>
          </div>
          <p className="text-text-secondary text-sm">Includes event analysis, detection results, endpoint telemetry, network intelligence, and risk assessment.</p>
        </div>
      </div>
    </PageLayout>
  );
}
