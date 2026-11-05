import { useState, lazy, Suspense } from 'react';
import { BrowserRouter, Routes, Route, useNavigate, useLocation } from 'react-router-dom';
import { Sidebar } from './components/Layout/Sidebar';
import { TopBar } from './components/Layout/TopBar';
import { NAVIGATION_ITEMS } from './constants/navigation';

const Overview = lazy(() => import('./pages/Overview').then(m => ({ default: m.Overview })));
const EndpointSecurityPage = lazy(() => import('./pages/edr/EndpointSecurityPage').then(m => ({ default: m.EndpointSecurityPage })));
const ThreatIntelligencePage = lazy(() => import('./pages/intel/ThreatIntelligencePage').then(m => ({ default: m.ThreatIntelligencePage })));
const SiemPage = lazy(() => import('./pages/siem/SiemPage').then(m => ({ default: m.SiemPage })));

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
