import client from "@/api/client";
import { faker } from "@faker-js/faker";
import { beforeEach, describe, expect, it, vi } from "vitest";
import { useUserUpsert } from "../useUserUpsert";

vi.mock("@/api/client");

describe("useUserUpsert", () => {
	beforeEach(() => {
		vi.clearAllMocks();
	});

	it("初始状态应该正确", () => {
		const { error } = useUserUpsert();
		expect(error.value).toBe(null);
	});

	it("upsertUser 成功后状态应该正确", async () => {
		vi.mocked(client.POST).mockResolvedValue({ data: {} });

		const userUpsert = useUserUpsert();
		const mockUser = {
			username: faker.internet.userName(),
			password: faker.internet.password(),
			enable: true,
		};

		await userUpsert.upsertUser(mockUser);

		expect(client.POST).toHaveBeenCalledWith("/urp/user", {
			body: mockUser,
		});
		expect(userUpsert.error.value).toBe(null);
	});

	it("upsertUser 失败后应该设置 error", async () => {
		const mockError = new Error("Failed to upsert user");
		vi.mocked(client.POST).mockRejectedValue(mockError);

		const userUpsert = useUserUpsert();
		await userUpsert.upsertUser({
			username: "test",
			password: "test",
			enable: true,
		});

		expect(userUpsert.error.value).toBe(mockError);
	});
});
