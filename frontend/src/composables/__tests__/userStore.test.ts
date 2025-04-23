import useUserStore from "@/composables/auth/useUserStore";
import { createPinia, setActivePinia } from "pinia";
import { beforeEach, describe, expect, it } from "vitest";

describe("User Store", () => {
	beforeEach(() => {
		setActivePinia(createPinia());
		const store = useUserStore();
		store.logout();
	});
	it("初始状态应为 null", () => {
		const store = useUserStore();
		expect(store.user).toBe(null);
	});

	it("login 应该正确设置用户信息", () => {
		const store = useUserStore();
		const mockUser = {
			name: "测试用户",
			roles: [
				{
					name: "admin",
					code: "ADMIN",
					permissions: [
						{
							name: "查看仪表板",
							code: "VIEW_DASHBOARD",
						},
					],
				},
			],
		};

		store.login(mockUser);
		expect(store.user).toEqual(mockUser);
	});

	it("logout 应该清除用户信息", () => {
		const store = useUserStore();
		const mockUser = {
			name: "测试用户",
			roles: [
				{
					name: "admin",
					code: "ADMIN",
					permissions: [
						{
							name: "查看仪表板",
							code: "VIEW_DASHBOARD",
						},
					],
				},
			],
		};

		store.login(mockUser);
		expect(store.user).not.toBe(null);

		store.logout();
		expect(store.user).toBe(null);
	});

	it("roleCodes 应该正确返回用户角色列表", () => {
		const store = useUserStore();
		const mockUser = {
			name: "测试用户",
			roles: [
				{
					name: "admin",
					code: "ADMIN",
					permissions: [],
				},
				{
					name: "user",
					code: "USER",
					permissions: [],
				},
			],
		};

		store.login(mockUser);
		expect(store.roleCodes).toEqual(["admin", "user"]);
	});

	it("permissionCodes 应该正确返回用户权限列表", () => {
		const store = useUserStore();
		const mockUser = {
			name: "测试用户",
			roles: [
				{
					name: "admin",
					code: "ADMIN",
					permissions: [
						{
							name: "查看仪表板",
							code: "VIEW_DASHBOARD",
						},
						{
							name: "编辑用户",
							code: "EDIT_USER",
						},
					],
				},
			],
		};

		store.login(mockUser);
		expect(store.permissionCodes).toEqual(["VIEW_DASHBOARD", "EDIT_USER"]);
	});

	it("未登录状态下计算属性应该返回 undefined", () => {
		const store = useUserStore();
		expect(store.roleCodes).toBe(undefined);
		expect(store.permissionCodes).toBe(undefined);
	});
});
