import { useState } from 'react';
import { Menu, Search, Bell, User, ChevronDown } from 'lucide-react';

interface TopBarProps {
  onToggleSidebar: () => void;
}

export function TopBar({ onToggleSidebar }: TopBarProps) {
  const [userMenuOpen, setUserMenuOpen] = useState(false);
  const [searchFocused, setSearchFocused] = useState(false);

  return (
    <header className="h-14 border-b border-white/[0.06] sticky top-0 z-30 flex items-center justify-between px-4 backdrop-blur-2xl"
      style={{ background: 'linear-gradient(180deg, rgba(13,20,32,0.9) 0%, rgba(8,13,22,0.7) 100%)' }}>
      <div className="flex items-center gap-3">
        <button className="p-2 rounded-lg hover:bg-white/10 text-text-secondary transition-colors" onClick={onToggleSidebar}>
          <Menu className="w-5 h-5" />
        </button>
        <div className="flex items-center gap-2">
          <div className="w-2.5 h-2.5 bg-primary rounded-full animate-pulse shadow-[0_0_8px_rgba(84,215,255,0.6)]" />
          <span className="font-bold text-sm tracking-tight bg-gradient-to-r from-primary to-secondary bg-clip-text text-transparent hidden sm:block">
            AEGIS NEXUS
          </span>
        </div>
      </div>

      <div className="flex items-center gap-2">
        <div className={`flex items-center gap-2 bg-white/5 border rounded-full px-3 py-1.5 transition-all duration-200 ${
          searchFocused ? 'border-primary/40 bg-primary/5 shadow-[0_0_12px_rgba(84,215,255,0.1)]' : 'border-white/10'
        }`}>
          <Search className="w-3.5 h-3.5 text-text-secondary" />
          <input
            type="text"
            placeholder="Search..."
            className="bg-transparent border-none outline-none text-xs text-text-primary w-40 placeholder-text-tertiary"
            onFocus={() => setSearchFocused(true)}
            onBlur={() => setSearchFocused(false)}
          />
        </div>

        <button className="p-2 rounded-lg hover:bg-white/10 text-text-secondary relative transition-colors">
          <Bell className="w-4.5 h-4.5" />
          <span className="absolute top-1.5 right-1.5 w-1.5 h-1.5 bg-critical rounded-full shadow-[0_0_4px_rgba(255,83,107,0.6)]" />
        </button>

        <div className="relative">
          <button
            className="flex items-center gap-2 px-2 py-1.5 rounded-lg hover:bg-white/10 text-text-secondary transition-colors"
            onClick={() => setUserMenuOpen(!userMenuOpen)}
          >
            <div className="w-7 h-7 rounded-full bg-gradient-to-br from-primary/30 to-secondary/30 border border-primary/20 flex items-center justify-center">
              <User className="w-3.5 h-3.5 text-primary" />
            </div>
            <span className="text-xs font-medium hidden sm:block text-text-primary">Admin</span>
            <ChevronDown className="w-3 h-3 text-text-tertiary" />
          </button>
          {userMenuOpen && (
            <div className="absolute right-0 mt-2 w-48 glass-modal z-50 animate-fade-in">
              <div className="px-4 py-2 border-b border-white/[0.06]">
                <p className="text-xs text-text-tertiary uppercase tracking-wider">Account</p>
              </div>
              <div className="p-2">
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
