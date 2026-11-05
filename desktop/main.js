const { app, BrowserWindow } = require('electron');
const path = require('path');

let mainWindow;

function createWindow() {
  mainWindow = new BrowserWindow({
    width: 1400,
    height: 900,
    minWidth: 1024,
    minHeight: 768,
    title: 'AEGIS NEXUS',
    backgroundColor: '#080D16',
    show: true,
    webPreferences: {
      nodeIntegration: false,
      contextIsolation: true,
    },
  });

  const exeDir = path.dirname(process.execPath);
  const frontendPath = path.join(exeDir, 'frontend', 'dist', 'index.html');
  console.log('LOADING FROM:', frontendPath);
  console.log('EXISTS:', require('fs').existsSync(frontendPath));

  mainWindow.loadFile(frontendPath).then(() => {
    console.log('LOADED OK');
  }).catch((err) => {
    console.error('LOAD FAILED:', err.message);
  });

  mainWindow.webContents.on('did-fail-load', (e, code, desc, url) => {
    console.error('DID FAIL LOAD:', code, desc, url);
  });

  mainWindow.webContents.on('did-finish-load', () => {
    console.log('DID FINISH LOAD');
  });

  mainWindow.webContents.openDevTools();

  mainWindow.on('closed', () => {
    mainWindow = null;
  });
}

app.whenReady().then(() => {
  createWindow();
  app.on('activate', () => {
    if (BrowserWindow.getAllWindows().length === 0) createWindow();
  });
});

app.on('window-all-closed', () => {
  if (process.platform !== 'darwin') app.quit();
});
