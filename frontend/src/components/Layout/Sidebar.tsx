import { ChevronLeft, ChevronRight } from 'lucide-react';

interface SidebarProps {
  collapsed: boolean;
  items: readonly { label: string; icon: string; href: string }[];
  activePath: string;
  onSelect: (path: string) => void;
}

const iconMap: Record<string, string> = {
  LayoutDashboard: '📊', Shield: '🛡️', Eye: '👁️', BarChart: '📈',
  Zap: '⚡', Monitor: '🖥️', Search: '🔍', AlertTriangle: '⚠️',
  Activity: '📋', Server: '🏢', Bot: '🤖', ShieldAlert: '🚨',
  FileText: '📄', Plug: '🔌', Settings: '⚙️',
};

export function Sidebar({ collapsed, items, activePath, onSelect }: SidebarProps) {
  return (
    <aside
      className={`fixed left-0 top-16 bottom-0 z-40 border-r border-white/[0.06] transition-all duration-300 ease-out ${
        collapsed ? 'w-16' : 'w-64'
      } bg-bg-secondary/60 backdrop-blur-2xl`}
      style={{ background: 'linear-gradient(180deg, rgba(13,20,32,0.8) 0%, rgba(8,13,22,0.9) 100%)' }}
    >
      <div className="flex items-center justify-between h-14 px-4 border-b border-white/[0.06]">
        {!collapsed && (
          <div className="flex items-center gap-2">
            <div className="w-2 h-2 bg-primary rounded-full animate-pulse shadow-[0_0_8px_rgba(84,215,255,0.6)]" />
            <span className="font-bold text-sm tracking-tight bg-gradient-to-r from-primary to-secondary bg-clip-text text-transparent">
              AEGIS NEXUS
            </span>
          </div>
        )}
        <button
          className="p-1.5 rounded-lg hover:bg-white/10 text-text-secondary transition-colors"
          onClick={() => {}}
        >
          {collapsed ? <ChevronRight className="w-4 h-4" /> : <ChevronLeft className="w-4 h-4" />}
        </button>
      </div>

      <nav className="py-3 overflow-y-auto h-[calc(100%-7rem)]">
        {items.map((item) => {
          const isActive = activePath === item.href;
          return (
            <button
              key={item.href}
              onClick={() => onSelect(item.href)}
              className={`w-full flex items-center gap-3 px-4 py-2.5 text-sm font-medium transition-all duration-200 relative group ${
                isActive
                  ? 'text-primary'
                  : 'text-text-secondary hover:text-text-primary'
              }`}
            >
              {isActive && (
                <div className="absolute left-0 top-1/2 -translate-y-1/2 w-[3px] h-6 bg-primary rounded-r-full shadow-[0_0_8px_rgba(84,215,255,0.6)]" />
              )}
              <span className={`text-base w-5 text-center transition-transform duration-200 ${isActive ? 'scale-110' : 'group-hover:scale-110'}`}>
                {iconMap[item.icon] || '●'}
              </span>
              {!collapsed && (
                <span className="truncate">{item.label}</span>
              )}
              {isActive && !collapsed && (
                <div className="ml-auto w-1.5 h-1.5 bg-primary rounded-full animate-pulse" />
              )}
            </button>
          );
        })}
      </nav>

      <div className="absolute bottom-0 left-0 right-0 p-3 border-t border-white/[0.06]">
        <div className="flex items-center gap-2 px-2">
          <div className="w-2 h-2 bg-success rounded-full animate-pulse shadow-[0_0_6px_rgba(57,217,160,0.5)]" />
          {!collapsed && <span className="text-xs text-text-secondary">System Operational</span>}
        </div>
      </div>
    </aside>
  );
}
