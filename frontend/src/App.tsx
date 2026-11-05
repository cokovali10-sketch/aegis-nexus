import { useState, lazy, Suspense } from 'react';
import { BrowserRouter, Routes, Route, useNavigate, useLocation } from 'react-router-dom';
import { Sidebar } from './components/Layout/Sidebar';
import { TopBar } from './components/Layout/TopBar';
import { NAVIGATION_ITEMS } from './constants/navigation';

const Overview = lazy(() => import('./pages/Overview').then(m => ({ default: m.Overview })));
const EndpointSecurityPage = lazy(() => import('./pages/edr/EndpointSecurityPage').then(m => ({ default: m.EndpointSecurityPage })));
const ThreatIntelligencePage = lazy(() => import('./pages/intel/ThreatIntelligencePage').then(m => ({ default: m.ThreatIntelligencePage })));
const SiemPage = lazy(() => import('./pages/siem/SiemPage').then(m => ({ default: m.SiemPage })));
const AssetsPage = lazy(() => import('./pages/assets/AssetsPage').then(m => ({ default: m.AssetsPage })));
const ResponsePage = lazy(() => import('./pages/response/ResponsePage').then(m => ({ default: m.ResponsePage })));
const VulnPage = lazy(() => import('./pages/vuln/VulnPage').then(m => ({ default: m.VulnPage })));
const ForensicsPage = lazy(() => import('./pages/forensics/ForensicsPage').then(m => ({ default: m.ForensicsPage })));
const NetworkPage = lazy(() => import('./pages/network/NetworkPage').then(m => ({ default: m.NetworkPage })));
const AutomationPage = lazy(() => import('./pages/automation/AutomationPage').then(m => ({ default: m.AutomationPage })));
const RiskPage = lazy(() => import('./pages/risk/RiskPage').then(m => ({ default: m.RiskPage })));
const ReportsPage = lazy(() => import('./pages/reports/ReportsPage').then(m => ({ default: m.ReportsPage })));
const SettingsPage = lazy(() => import('./pages/settings/SettingsPage').then(m => ({ default: m.SettingsPage })));
const SentinelPage = lazy(() => import('./pages/sentinel/SentinelPage').then(m => ({ default: m.SentinelPage })));

function PageLoader() {
  return (
    <div className="flex items-center justify-center h-64">
      <div className="w-8 h-8 border-2 border-primary/30 border-t-primary rounded-full animate-spin" />
    </div>
  );
}

function AppContent() {
  const [collapsed, setCollapsed] = useState(false);
  const navigate = useNavigate();
  const location = useLocation();

  return (
    <div className="min-h-screen bg-[#080D16] text-[#EDF5FF] flex flex-col overflow-hidden selection:bg-primary/30">
      <TopBar onToggleSidebar={() => setCollapsed((c) => !c)} />
      <div className="flex-1 flex overflow-hidden">
        <Sidebar
          collapsed={collapsed}
          items={NAVIGATION_ITEMS as unknown as { label: string; icon: string; href: string }[]}
          activePath={location.pathname}
          onSelect={(path) => navigate(path)}
        />
        <main className="flex-1 overflow-y-auto p-6 scroll-smooth">
          <Suspense fallback={<PageLoader />}>
            <Routes>
              <Route path="/" element={<Overview />} />
              <Route path="/edr" element={<EndpointSecurityPage />} />
              <Route path="/intel" element={<ThreatIntelligencePage />} />
              <Route path="/siem" element={<SiemPage />} />
              <Route path="/sentinel" element={<SentinelPage />} />
              <Route path="/network" element={<NetworkPage />} />
              <Route path="/forensics" element={<ForensicsPage />} />
              <Route path="/vuln" element={<VulnPage />} />
              <Route path="/response" element={<ResponsePage />} />
              <Route path="/assets" element={<AssetsPage />} />
              <Route path="/automation" element={<AutomationPage />} />
              <Route path="/risk" element={<RiskPage />} />
              <Route path="/reports" element={<ReportsPage />} />
              <Route path="/settings" element={<SettingsPage />} />
              <Route path="*" element={<Overview />} />
            </Routes>
          </Suspense>
        </main>
      </div>
    </div>
  );
}

export default function App() {
  return (
    <BrowserRouter>
      <AppContent />
    </BrowserRouter>
  );
}
