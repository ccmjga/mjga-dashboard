import client from "@/api/client";
import { faker } from "@faker-js/faker";
import { beforeEach, describe, expect, it, vi } from "vitest";
import { useRolesPaginationQuery } from "../useRolesQuery";

vi.mock("@/api/client");

describe("useRolesPaginationQuery", () => {
	beforeEach(() => {
		vi.clearAllMocks();
	});

	it("初始状态应该正确", () => {
		const { roles, total, isLoading, error } = useRolesPaginationQuery(1, 10);
		expect(roles.value).toEqual([]);
		expect(total.value).toBe(0);
		expect(isLoading.value).toBe(false);
		expect(error.value).toBe(null);
	});

	it("fetchRolesWith 获取角色数据后相应状态发生改变", async () => {
		const mockRole = () => ({
			id: faker.number.int({ min: 1, max: 100 }),
			name: faker.person.jobTitle(),
			code: faker.string.alpha(10),
			enable: faker.datatype.boolean(),
			createTime: faker.date.recent({ days: 30 }).toISOString(),
			permissions: [],
		});

		const mockTotal = 20;
		const mockRolesResponse = faker.helpers.multiple(mockRole, {
			count: mockTotal,
		});

		vi.mocked(client.GET).mockResolvedValue({
			data: {
				data: mockRolesResponse,
				total: mockTotal,
			},
		});

		const rolesPaginationQuery = useRolesPaginationQuery(1, 10);
		await rolesPaginationQuery.fetchRolesWith(1, 10, {
			name: "",
		});

		expect(client.GET).toHaveBeenCalledWith("/urp/roles", {
			params: {
				query: {
					pageRequestDto: {
						page: 1,
						size: 10,
					},
					roleQueryDto: {
						name: "",
					},
				},
			},
		});
		expect(rolesPaginationQuery.roles.value).toEqual(mockRolesResponse);
		expect(rolesPaginationQuery.total.value).toBe(mockTotal);
		expect(rolesPaginationQuery.isLoading.value).toBe(false);
		expect(rolesPaginationQuery.error.value).toBeNull;
	});
});
