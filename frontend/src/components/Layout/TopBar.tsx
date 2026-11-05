import { useState } from 'react';
import { Menu, Search, Bell, User } from 'lucide-react';

interface TopBarProps {
  onToggleSidebar: () => void;
}

export function TopBar({ onToggleSidebar }: TopBarProps) {
  const [userMenuOpen, setUserMenuOpen] = useState(false);

  return (
    <header className="h-16 border-b border-white/10 bg-bg-secondary/80 backdrop-blur-md sticky top-0 z-30 flex items-center justify-between px-6">
      <div className="flex items-center gap-4">
        <button className="p-2 rounded-lg hover:bg-white/10 text-text-secondary" onClick={onToggleSidebar}>
          <Menu className="w-5 h-5" />
        </button>
        <div className="flex items-center gap-2 ml-2">
          <div className="w-3 h-3 bg-primary rounded-full animate-pulse" />
          <span className="font-bold text-lg tracking-tighter bg-gradient-to-r from-primary to-secondary bg-clip-text text-transparent hidden sm:block">
            AEGIS NEXUS
          </span>
        </div>
      </div>

      <div className="flex items-center gap-1">
        <div className="flex items-center gap-2 bg-panel border border-glass-border rounded-full px-3 py-1.5">
          <Search className="w-4 h-4 text-text-secondary" />
          <input type="text" placeholder="Global search..." className="bg-transparent border-none outline-none text-xs text-text-primary w-32 placeholder-text-secondary" />
        </div>

        <button className="p-2 ml-2 rounded-lg hover:bg-white/10 text-text-secondary relative">
          <Bell className="w-5 h-5" />
          <span className="absolute top-1 right-1 w-2 h-2 bg-critical rounded-full" />
        </button>

        <div className="relative ml-2">
          <button className="flex items-center gap-2 px-3 py-1.5 rounded-lg hover:bg-white/10 text-text-secondary" onClick={() => setUserMenuOpen(!userMenuOpen)}>
            <div className="w-8 h-8 rounded-full bg-primary/20 border border-primary/30 overflow-hidden">
              <User className="w-5 h-5 text-primary" />
            </div>
            <span className="text-sm font-medium hidden sm:block text-text-primary">Administrator</span>
          </button>
          {userMenuOpen && (
            <div className="absolute right-0 mt-2 w-48 bg-panel border border-glass-border rounded-lg shadow-xl backdrop-blur-md z-50">
              <div className="px-4 py-2 border-b border-white/10">
                <p className="text-xs text-text-secondary uppercase tracking-wider">Account</p>
              </div>
              <div className="px-4 py-2">
                <button className="w-full flex items-center gap-2 px-3 py-2 text-sm text-text-primary hover:bg-white/5 rounded-lg transition-colors">Profile</button>
                <button className="w-full flex items-center gap-2 px-3 py-2 text-sm text-text-primary hover:bg-white/5 rounded-lg transition-colors">Settings</button>
                <button className="w-full flex items-center gap-2 px-3 py-2 text-sm text-critical hover:bg-white/5 rounded-lg transition-colors">Sign out</button>
              </div>
            </div>
          )}
        </div>
      </div>
    </header>
  );
}
