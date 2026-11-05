import { ChevronLeft } from 'lucide-react';

interface SidebarProps {
  collapsed: boolean;
  items: readonly { label: string; icon: string; href: string }[];
  activePath: string;
  onSelect: (path: string) => void;
}

export function Sidebar({ collapsed, items, activePath, onSelect }: SidebarProps) {
  return (
    <aside
      className={`fixed left-0 top-16 bottom-0 z-40 border-r border-white/10 transition-all duration-300 ${
        collapsed ? 'w-16' : 'w-64'
      } bg-bg-secondary/80 backdrop-blur-md`}
    >
      <div className="flex items-center justify-between h-14 px-4">
        {!collapsed && (
          <div className="flex items-center gap-2">
            <div className="w-3 h-3 bg-primary rounded-full animate-pulse" />
            <span className="font-bold text-lg tracking-tighter bg-gradient-to-r from-primary to-secondary bg-clip-text text-transparent">
              AEGIS NEXUS
            </span>
          </div>
        )}
        <button className="p-2 rounded-lg hover:bg-white/10 text-text-secondary" onClick={() => {}}>
          <ChevronLeft className={`w-5 h-5 ${collapsed ? 'rotate-180' : ''}`} />
        </button>
      </div>

      <nav className="py-4">
        {items.map((item) => {
          const Icon = (() => {
            switch (item.icon) {
              case 'LayoutDashboard': return () => <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M4 6a2 2 0 012-2h2a2 2 0 012 2v2a2 2 0 01-2 2H6a2 2 0 01-2-2V6zM14 6a2 2 0 012-2h2a2 2 0 012 2v2a2 2 0 01-2 2h-2a2 2 0 01-2-2V6zM4 16a2 2 0 012-2h2a2 2 0 012 2v2a2 2 0 01-2 2H6a2 2 0 01-2-2v-2zM14 16a2 2 0 012-2h2a2 2 0 012 2v2a2 2 0 01-2 2h-2a2 2 0 01-2-2v-2z"></path></svg>;
              case 'Shield': return () => <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"></path></svg>;
              case 'Eye': return () => <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"></path><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z"></path></svg>;
              case 'BarChart': return () => <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M9 19v-6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2a2 2 0 002-2zm0 0V9a2 2 0 012-2h2a2 2 0 012 2v10m-6 0a2 2 0 002 2h2a2 2 0 002-2m0 0V5a2 2 0 012-2h2a2 2 0 012 2v14a2 2 0 01-2 2h-2a2 2 0 01-2-2z"></path></svg>;
              default: return () => <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M4 6a2 2 0 012-2h2a2 2 0 012 2v2a2 2 0 01-2 2H6a2 2 0 01-2-2V6z"></path></svg>;
            }
          })();
          const isActive = activePath === item.href;
          return (
            <button
              key={item.href}
              onClick={() => onSelect(item.href)}
              className={`w-full flex items-center gap-3 px-4 py-3 text-sm font-medium transition-colors duration-200 ${
                isActive
                  ? 'bg-primary/10 border-r-2 border-primary text-primary'
                  : 'text-text-secondary hover:bg-white/10 hover:text-text-primary'
              }`}
            >
              <div className={`w-5 h-5 ${isActive ? 'text-primary' : 'text-text-secondary'}`}>
                <Icon />
              </div>
              {collapsed ? null : item.label}
            </button>
          );
        })}
      </nav>

      <div className="absolute bottom-0 left-0 right-0 p-4 border-t border-white/10">
        <div className="flex items-center gap-2 px-2">
          <div className="w-2 h-2 bg-critical rounded-full animate-pulse" />
          <span className={collapsed ? 'hidden' : 'text-xs text-text-secondary'}>System Degraded</span>
        </div>
      </div>
    </aside>
  );
}
