import { expect, test } from "@playwright/test";

// See here how to get started:
// https://playwright.dev/docs/intro
test("visits the app root url", async ({ page }) => {
	await page.goto("/");
	await expect(page.locator("h1")).toHaveText("You did it!");
});

// 登录功能测试
test.describe("登录功能", () => {
	test("成功登录", async ({ page }) => {
		await page.goto("/");

		// 填写登录表单
		await page.fill("[data-test=username]", "testuser");
		await page.fill("[data-test=password]", "password");
		await page.click("[data-test=login-button]");

		// 验证登录成功
		await expect(page.locator("[data-test=welcome-message]")).toHaveText(
			"欢迎回来",
		);
	});
});

// 导航测试
test.describe("导航功能", () => {
	test("可以正确导航到不同页面", async ({ page }) => {
		await page.goto("/");

		// 点击导航链接
		await page.click("[data-test=nav-dashboard]");
		await expect(page).toHaveURL("/dashboard");
	});
});
