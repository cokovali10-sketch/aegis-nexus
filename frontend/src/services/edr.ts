const API_BASE = '/api/v1';

export async function request(path: string, options: RequestInit = {}) {
  const res = await fetch(`${API_BASE}${path}`, {
    headers: {
      'Content-Type': 'application/json',
      ...options.headers,
    },
    ...options,
  });
  if (!res.ok) {
    const text = await res.text().catch(() => '');
    throw new Error(text || `Request failed: ${res.status}`);
  }
  return res.json();
}

export const edrApi = {
  listEndpoints: () => request('/edr/endpoints').then((r: any) => r.data),
  getEndpoint: (agentId: string) => request(`/edr/endpoints/${agentId}`).then((r: any) => r.data),
  registerEndpoint: (body: any) => request('/edr/endpoints/register', {
    method: 'POST',
    body: JSON.stringify(body),
  }).then((r: any) => r.data),
  heartbeat: (agentId: string) => request(`/edr/endpoints/${agentId}/heartbeat`, {
    method: 'POST',
  }).then((r: any) => r.data),
  isolateEndpoint: (id: string, isolate: boolean) => request(`/edr/endpoints/${id}/isolate?isolate=${isolate}`, {
    method: 'POST',
  }).then((r: any) => r.data),
  listProcesses: (endpointId: string) => request(`/edr/processes/endpoint/${endpointId}`).then((r: any) => r.data),
  listSuspiciousProcesses: (endpointId: string) => request(`/edr/processes/endpoint/${endpointId}/suspicious`).then((r: any) => r.data),
  listFileIntegrity: (endpointId: string) => request(`/edr/file-integrity/endpoint/${endpointId}`).then((r: any) => r.data),
  listUnauthorizedFileIntegrity: () => request('/edr/file-integrity/unauthorized').then((r: any) => r.data),
};
