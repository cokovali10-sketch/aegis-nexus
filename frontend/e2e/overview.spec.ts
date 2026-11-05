import { test, expect } from '@playwright/test';

test.describe('AEGIS NEXUS - Overview', () => {
  test('should load the overview page', async ({ page }) => {
    await page.goto('/');
    await expect(page).toHaveTitle(/AEGIS NEXUS/);
    await expect(page.locator('h1')).toContainText('Security Overview');
  });

  test('should show demo mode toggle', async ({ page }) => {
    await page.goto('/');
    await expect(page.locator('button:has-text("DEMO MODE")')).toBeVisible();
  });

  test('should display security score', async ({ page }) => {
    await page.goto('/');
    await expect(page.locator('text=Security Score')).toBeVisible();
  });

  test('should navigate to EDR page', async ({ page }) => {
    await page.goto('/');
    await page.click('button:has-text("Endpoint Security")');
    await expect(page.locator('h1')).toContainText('Endpoint Security');
  });

  test('should navigate to SIEM page', async ({ page }) => {
    await page.goto('/');
    await page.click('button:has-text("SIEM")');
    await expect(page.locator('h1')).toContainText('SIEM');
  });
});
