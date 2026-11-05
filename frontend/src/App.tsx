import { useState } from 'react';
import { Sidebar } from './components/Layout/Sidebar';
import { TopBar } from './components/Layout/TopBar';
import { Overview } from './pages/Overview';
import { NAVIGATION_ITEMS } from './constants/navigation';

export default function App() {
  const [collapsed, setCollapsed] = useState(false);
  const [activePath, setActivePath] = useState<string>('/');

  return (
    <div className="min-h-screen bg-[#080D16] text-[#EDF5FF] flex flex-col overflow-hidden selection:bg-primary/30">
      <TopBar onToggleSidebar={() => setCollapsed((c) => !c)} />
      <div className="flex-1 flex overflow-hidden">
        <Sidebar collapsed={collapsed} items={NAVIGATION_ITEMS as unknown as { label: string; icon: string; href: string }[]} activePath={activePath} onSelect={(path) => setActivePath(path)} />
        <div className="flex-1 overflow-y-auto p-6">
          <Overview />
        </div>
      </div>
    </div>
  );
}
