import { useState } from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { Sidebar } from './components/Layout/Sidebar';
import { TopBar } from './components/Layout/TopBar';
import { Overview } from './pages/Overview';
import { EndpointSecurityPage } from './pages/edr/EndpointSecurityPage';
import { ThreatIntelligencePage } from './pages/intel/ThreatIntelligencePage';
import { SiemPage } from './pages/siem/SiemPage';
import { NAVIGATION_ITEMS } from './constants/navigation';

export default function App() {
  const [collapsed, setCollapsed] = useState(false);

  return (
    <BrowserRouter>
      <div className="min-h-screen bg-[#080D16] text-[#EDF5FF] flex flex-col overflow-hidden selection:bg-primary/30">
        <TopBar onToggleSidebar={() => setCollapsed((c) => !c)} />
        <div className="flex-1 flex overflow-hidden">
          <Sidebar collapsed={collapsed} items={NAVIGATION_ITEMS as unknown as { label: string; icon: string; href: string }[]} activePath="/" onSelect={() => {}} />
          <div className="flex-1 overflow-y-auto p-6">
            <Routes>
              <Route path="/" element={<Overview />} />
              <Route path="/edr" element={<EndpointSecurityPage />} />
              <Route path="/intel" element={<ThreatIntelligencePage />} />
              <Route path="/siem" element={<SiemPage />} />
            </Routes>
          </div>
        </div>
      </div>
    </BrowserRouter>
  );
}
