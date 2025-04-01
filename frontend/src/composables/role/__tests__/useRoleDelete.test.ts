import client from "@/api/client";
import { faker } from "@faker-js/faker";
import { beforeEach, describe, expect, it, vi } from "vitest";
import useRoleDelete from "../useRoleDelete";

vi.mock("@/api/client");

describe("useRoleDelete", () => {
	beforeEach(() => {
		vi.clearAllMocks();
	});

	it("初始状态应该正确", () => {
		const { isLoading, error } = useRoleDelete();
		expect(isLoading.value).toBe(false);
		expect(error.value).toBe(null);
	});

	it("deleteRole 成功后状态应该正确", async () => {
		vi.mocked(client.DELETE).mockResolvedValue({ data: {} });

		const roleDelete = useRoleDelete();
		const roleId = faker.number.int({ min: 1, max: 100 });

		await roleDelete.deleteRole(roleId);

		expect(client.DELETE).toHaveBeenCalledWith("/urp/role", {
			params: {
				query: { roleId },
			},
		});
		expect(roleDelete.isLoading.value).toBe(false);
		expect(roleDelete.error.value).toBe(null);
	});

	it("deleteRole 失败后应该设置 error", async () => {
		const mockError = new Error("Failed to delete role");
		vi.mocked(client.DELETE).mockRejectedValue(mockError);

		const roleDelete = useRoleDelete();
		await roleDelete.deleteRole(1);

		expect(roleDelete.error.value).toBe(mockError);
		expect(roleDelete.isLoading.value).toBe(false);
	});
});
