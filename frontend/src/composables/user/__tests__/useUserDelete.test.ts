import client from "@/api/client";
import { faker } from "@faker-js/faker";
import { beforeEach, describe, expect, it, vi } from "vitest";
import useUserDelete from "../useUserDelete";

vi.mock("@/api/client");

describe("useUserDelete", () => {
	beforeEach(() => {
		vi.clearAllMocks();
	});

	it("初始状态应该正确", () => {
		const { error } = useUserDelete();
		expect(error.value).toBe(null);
	});

	it("deleteUser 成功后状态应该正确", async () => {
		vi.mocked(client.DELETE).mockResolvedValue({ data: {} });

		const userDelete = useUserDelete();
		const userId = faker.number.int({ min: 1, max: 100 });

		await userDelete.deleteUser(userId);

		expect(client.DELETE).toHaveBeenCalledWith("/urp/user", {
			params: {
				query: { userId },
			},
		});
		expect(userDelete.error.value).toBe(null);
	});

	it("deleteUser 失败后应该设置 error", async () => {
		const mockError = new Error("Failed to delete user");
		vi.mocked(client.DELETE).mockRejectedValue(mockError);

		const userDelete = useUserDelete();
		await userDelete.deleteUser(1);

		expect(userDelete.error.value).toBe(mockError);
	});
});
