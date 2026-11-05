import { User, Shield, Bell, Database } from 'lucide-react';
import { PageLayout } from '../../components/ui/PageLayout';

export function SettingsPage() {
  return (
    <PageLayout title="Settings" subtitle="Platform configuration and preferences">
      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
        <div className="glass-panel p-6">
          <div className="flex items-center gap-3 mb-4">
            <User className="w-5 h-5 text-primary" />
            <h3 className="text-lg font-bold text-text-primary">Profile</h3>
          </div>
          <div className="space-y-3">
            <div>
              <label className="text-xs text-text-secondary uppercase tracking-wider">Username</label>
              <input className="input-glass w-full mt-1" value="admin" readOnly />
            </div>
            <div>
              <label className="text-xs text-text-secondary uppercase tracking-wider">Role</label>
              <input className="input-glass w-full mt-1" value="ADMIN" readOnly />
            </div>
          </div>
        </div>

        <div className="glass-panel p-6">
          <div className="flex items-center gap-3 mb-4">
            <Shield className="w-5 h-5 text-primary" />
            <h3 className="text-lg font-bold text-text-primary">Security</h3>
          </div>
          <div className="space-y-3">
            <div className="flex items-center justify-between p-3 rounded-xl bg-white/5">
              <span className="text-text-primary text-sm">Two-Factor Authentication</span>
              <span className="text-text-secondary text-xs">Disabled</span>
            </div>
            <div className="flex items-center justify-between p-3 rounded-xl bg-white/5">
              <span className="text-text-primary text-sm">Session Timeout</span>
              <span className="text-text-secondary text-xs">24 hours</span>
            </div>
          </div>
        </div>

        <div className="glass-panel p-6">
          <div className="flex items-center gap-3 mb-4">
            <Bell className="w-5 h-5 text-primary" />
            <h3 className="text-lg font-bold text-text-primary">Notifications</h3>
          </div>
          <div className="space-y-3">
            <div className="flex items-center justify-between p-3 rounded-xl bg-white/5">
              <span className="text-text-primary text-sm">Email Alerts</span>
              <span className="text-success text-xs">Enabled</span>
            </div>
            <div className="flex items-center justify-between p-3 rounded-xl bg-white/5">
              <span className="text-text-primary text-sm">Critical Only</span>
              <span className="text-text-secondary text-xs">Disabled</span>
            </div>
          </div>
        </div>

        <div className="glass-panel p-6">
          <div className="flex items-center gap-3 mb-4">
            <Database className="w-5 h-5 text-primary" />
            <h3 className="text-lg font-bold text-text-primary">System</h3>
          </div>
          <div className="space-y-3">
            <div className="flex items-center justify-between p-3 rounded-xl bg-white/5">
              <span className="text-text-primary text-sm">Backend Status</span>
              <span className="text-success text-xs">Online</span>
            </div>
            <div className="flex items-center justify-between p-3 rounded-xl bg-white/5">
              <span className="text-text-primary text-sm">Database</span>
              <span className="text-success text-xs">H2 (In-Memory)</span>
            </div>
          </div>
        </div>
      </div>
    </PageLayout>
  );
}
