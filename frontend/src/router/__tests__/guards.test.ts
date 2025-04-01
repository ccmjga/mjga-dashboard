import { afterEach, beforeEach, describe, expect, it, vi } from "vitest";
import type {
	NavigationGuardNext,
	RouteLocationNormalized,
	RouteLocationNormalizedLoaded,
} from "vue-router";
import { RoutePath } from "../constants";

describe.skip("Router Guards", () => {
	let mockTo: RouteLocationNormalized;
	let mockNext: NavigationGuardNext;
	let mockFrom: RouteLocationNormalizedLoaded;

	beforeEach(() => {
		mockTo = {
			meta: {
				requiresAuth: true,
			},
			path: "/test",
			fullPath: "/test",
		} as RouteLocationNormalized;
		mockNext = vi.fn();
		mockFrom = {
			meta: {},
			path: "/test",
			fullPath: "/test",
		} as RouteLocationNormalizedLoaded;
		vi.resetModules();
		vi.doUnmock("../../stores/userStore");
	});

	describe("authGuard", () => {
		it("未登录用户访问需要认证的页面时应该重定向到登录页面", async () => {
			vi.doMock("../../stores/userStore", () => ({
				useUserStore: () => ({
					user: undefined,
					login: vi.fn(),
					logout: vi.fn(),
					roleCodes: undefined,
					permissionCodes: undefined,
				}),
			}));

			const { authGuard } = await import("../guards");
			const result = authGuard(mockTo, mockFrom, mockNext);
			expect(result).toEqual({
				path: RoutePath.LOGIN,
				query: { redirect: mockTo.fullPath },
			});
			const useUserStore1 = await import("@/composables/auth/useUserStore");
			console.log(useUserStore1.useUserStore());
		});

		it("已登录用户访问登录页面时应该重定向到后台页面", async () => {
			const userInfo = {
				id: 1,
				name: "testUser",
			};
			vi.doMock("../../stores/userStore", () => ({
				useUserStore: () => ({
					user: userInfo,
					login: vi.fn(),
					logout: vi.fn(),
					roleCodes: ["admin"],
					permissionCodes: ["read"],
				}),
			}));
			const { authGuard } = await import("../guards");
			const useUserStore2 = await import("@/composables/auth/useUserStore");
			console.log(useUserStore2.useUserStore());
			mockTo.path = RoutePath.LOGIN;
			mockTo.meta.requiresAuth = false;

			const result = authGuard(mockTo, mockFrom, mockNext);
			expect(result).toEqual({
				path: RoutePath.DASHBOARD,
			});
		});
	});
});
