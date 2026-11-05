import { useState, useEffect } from 'react';
import { Search } from 'lucide-react';
import { request } from '../../services/edr';

interface Ioc {
  id: string;
  type: string;
  value: string;
  source: string;
  confidence: number;
  threatType: string;
  description: string;
  firstSeen: string;
  lastSeen: string | null;
  isActive: boolean;
}

export function ThreatIntelligencePage() {
  const [iocs, setIocs] = useState<Ioc[]>([]);
  const [loading, setLoading] = useState(true);
  const [osintResult, setOsintResult] = useState<any>(null);
  const [osintQuery, setOsintQuery] = useState('');
  const [osintType, setOsintType] = useState<'ip' | 'domain' | 'hash'>('ip');

  useEffect(() => {
    request('/intel/iocs')
      .then((r: any) => setIocs(r.data))
      .catch(() => {})
      .finally(() => setLoading(false));
  }, []);

  const handleOsintAnalyze = async () => {
    try {
      const res = await request(`/intel/osint/${osintType}/${encodeURIComponent(osintQuery)}`);
      setOsintResult(res.data);
    } catch (err: any) {
      setOsintResult({ error: err.message });
    }
  };

  const threatColor = (threat: string) => {
    switch (threat) {
      case 'MALWARE': case 'RANSOMWARE': return 'text-critical bg-critical/10 border-critical/20';
      case 'PHISHING': case 'C2': return 'text-warning bg-warning/10 border-warning/20';
      default: return 'text-text-secondary bg-white/5 border-white/10';
    }
  };

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-2xl font-bold text-text-primary">Threat Intelligence</h1>
        <p className="text-text-secondary mt-1">IOC database, OSINT analysis, and threat feeds</p>
      </div>

      <div className="glass-panel p-6">
        <h2 className="text-lg font-bold text-text-primary mb-4">OSINT Analysis</h2>
        <div className="flex gap-3 mb-4">
          <select
            className="bg-panel border border-glass-border text-text-primary px-3 py-2 rounded-lg focus:border-primary focus:outline-none"
            value={osintType}
            onChange={(e) => setOsintType(e.target.value as 'ip' | 'domain' | 'hash')}
          >
            <option value="ip">IP Address</option>
            <option value="domain">Domain</option>
            <option value="hash">Hash</option>
          </select>
          <input
            type="text"
            placeholder={osintType === 'ip' ? 'Enter IP address...' : osintType === 'domain' ? 'Enter domain...' : 'Enter hash...'}
            className="flex-1 bg-panel border border-glass-border text-text-primary px-4 py-2 rounded-lg focus:border-primary focus:outline-none"
            value={osintQuery}
            onChange={(e) => setOsintQuery(e.target.value)}
            onKeyDown={(e) => e.key === 'Enter' && handleOsintAnalyze()}
          />
          <button className="btn-primary flex items-center gap-2" onClick={handleOsintAnalyze}>
            <Search className="w-4 h-4" />
            Analyze
          </button>
        </div>
        {osintResult && (
          <div className="bg-white/5 border border-white/10 rounded-lg p-4">
            <pre className="text-xs text-text-primary font-mono whitespace-pre-wrap">
              {JSON.stringify(osintResult, null, 2)}
            </pre>
          </div>
        )}
      </div>

      <div className="glass-panel p-6">
        <div className="flex items-center justify-between mb-4">
          <h2 className="text-lg font-bold text-text-primary">IOC Database</h2>
          <span className="text-xs text-text-secondary">{iocs.length} indicators</span>
        </div>
        {loading ? (
          <div className="flex items-center justify-center py-8">
            <div className="w-6 h-6 border-2 border-primary/30 border-t-primary rounded-full animate-spin" />
          </div>
        ) : (
          <div className="overflow-x-auto">
            <table className="w-full text-left border-collapse">
              <thead>
                <tr className="border-b border-white/10">
                  <th className="pb-3 text-xs font-semibold text-text-secondary uppercase">Type</th>
                  <th className="pb-3 text-xs font-semibold text-text-secondary uppercase">Value</th>
                  <th className="pb-3 text-xs font-semibold text-text-secondary uppercase">Source</th>
                  <th className="pb-3 text-xs font-semibold text-text-secondary uppercase">Confidence</th>
                  <th className="pb-3 text-xs font-semibold text-text-secondary uppercase">Threat</th>
                  <th className="pb-3 text-xs font-semibold text-text-secondary uppercase">Status</th>
                </tr>
              </thead>
              <tbody>
                {iocs.map((ioc) => (
                  <tr key={ioc.id} className="border-b border-white/5 hover:bg-white/5 transition-colors">
                    <td className="py-3 text-xs">
                      <span className="px-2 py-1 rounded bg-primary/10 text-primary border border-primary/20 font-mono text-[10px]">
                        {ioc.type}
                      </span>
                    </td>
                    <td className="py-3 text-xs font-mono text-text-primary">{ioc.value}</td>
                    <td className="py-3 text-xs text-text-secondary">{ioc.source}</td>
                    <td className="py-3 text-xs">
                      <div className="flex items-center gap-2">
                        <div className="w-16 h-1.5 bg-white/10 rounded-full overflow-hidden">
                          <div className="h-full bg-primary rounded-full" style={{ width: `${ioc.confidence}%` }} />
                        </div>
                        <span className="text-text-secondary font-mono">{ioc.confidence}%</span>
                      </div>
                    </td>
                    <td className="py-3 text-xs">
                      <span className={`px-2 py-1 rounded-full text-xs font-bold border ${threatColor(ioc.threatType)}`}>
                        {ioc.threatType}
                      </span>
                    </td>
                    <td className="py-3 text-xs">
                      <span className={`px-2 py-1 rounded-full text-xs font-bold border ${ioc.isActive ? 'bg-success/10 text-success border-success/20' : 'bg-text-secondary/10 text-text-secondary border-white/10'}`}>
                        {ioc.isActive ? 'ACTIVE' : 'INACTIVE'}
                      </span>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </div>
    </div>
  );
}
