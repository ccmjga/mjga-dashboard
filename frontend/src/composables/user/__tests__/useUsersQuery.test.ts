import client from "@/api/client";
import { faker } from "@faker-js/faker";
import { beforeEach, describe, expect, it, vi } from "vitest";
import { useUsersPaginationQuery } from "../useUsersQuery";

vi.mock("@/api/client");

describe("useUsersPaginationQuery", () => {
	beforeEach(() => {
		vi.clearAllMocks();
	});

	it("初始状态应该正确", () => {
		const { users, total, isLoading, error } = useUsersPaginationQuery(1, 10);
		expect(users.value).toEqual([]);
		expect(total.value).toBe(0);
		expect(isLoading.value).toBe(false);
		expect(error.value).toBe(null);
	});

	it("fetchUsersWith 获取用户数据后相应状态发生改变", async () => {
		const mockUser = () => ({
			id: faker.number.int({ min: 1, max: 100 }),
			username: faker.internet.email(),
			enable: faker.datatype.boolean(),
			roles: [],
			createTime: faker.date.recent({ days: 30 }).toISOString(),
			permissions: [],
		});
		const mockTotal = 20;
		const mockUsersResponse = faker.helpers.multiple(mockUser, {
			count: mockTotal,
		});
		vi.mocked(client.GET).mockResolvedValue({
			data: {
				data: mockUsersResponse,
				total: mockTotal,
			},
		});

		const usersPaginationQuery = useUsersPaginationQuery(1, 10);
		await usersPaginationQuery.fetchUsersWith(1, 10, {
			username: "",
		});

		expect(client.GET).toHaveBeenCalledWith("/urp/users", {
			params: {
				query: {
					pageRequestDto: {
						page: 1,
						size: 10,
					},
					userQueryDto: {
						username: "",
					},
				},
			},
		});
		expect(usersPaginationQuery.users.value).toEqual(mockUsersResponse);
		expect(usersPaginationQuery.total.value).toBe(mockTotal);
		expect(usersPaginationQuery.isLoading.value).toBe(false);
		expect(usersPaginationQuery.error.value).toBeNull;
		expect(usersPaginationQuery.pagination.totalPages.value).toBe(2);
		expect(usersPaginationQuery.pagination.pageNumbers.value).toEqual([1, 2]);
		expect(usersPaginationQuery.pagination.displayRange.value).toEqual({
			start: 1,
			end: 10,
		});
	});
});
