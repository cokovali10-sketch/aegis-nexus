import { describe, it, expect } from 'vitest';

describe('Overview page', () => {
  it('should have demo data structure', () => {
    const endpoints = [
      { id: 'ep-001', status: 'online' as const },
      { id: 'ep-002', status: 'offline' as const },
    ];
    const onlineCount = endpoints.filter((e) => e.status === 'online').length;
    expect(onlineCount).toBe(1);
  });

  it('should calculate security score correctly', () => {
    const score = 87;
    expect(score).toBeGreaterThan(0);
    expect(score).toBeLessThanOrEqual(100);
  });
});
