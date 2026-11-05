const { contextBridge, ipcRenderer } = require('electron');

contextBridge.exposeInMainWorld('aegisNexus', {
  platform: process.platform,
  version: process.env.npm_package_version || '1.0.0',
});
