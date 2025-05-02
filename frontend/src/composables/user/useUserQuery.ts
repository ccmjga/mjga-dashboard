import client from "@/api/client";
import { ref } from "vue";
import type { UserRolePermission } from "../../types/user";
import { usePagination } from "../page";

export const useUserQuery = () => {
	const paginationHooks = usePagination({
		initialPage: 1,
		initialPageSize: 10,
	});

	const total = ref<number>(0);
	const users = ref<UserRolePermission[]>([]);
	const user = ref<UserRolePermission>();

	const getUserWithDetail = async (userId: number) => {
		const { data } = await client.GET("/urp/user", {
			params: {
				query: {
					userId: userId,
				},
			},
		});
		user.value = data;
	};

	const fetchUsersWith = async (
		page: number,
		size: number,
		param: {
			username?: string;
		},
	) => {
		const { data } = await client.GET("/urp/users", {
			params: {
				query: {
					pageRequestDto: {
						page,
						size,
					},
					userQueryDto: param,
				},
			},
		});
		total.value = !data || !data.total ? 0 : data.total;
		users.value = data?.data ?? [];

		paginationHooks.updatePaginationState({
			currentPage: page,
			pageSize: size,
			total: total.value,
		});
	};

	return {
		pagination: {
			pageSize: paginationHooks.pageSize,
			currentPage: paginationHooks.currentPage,
			totalPages: paginationHooks.totalPages,
			pageNumbers: paginationHooks.pageNumbers,
			displayRange: paginationHooks.displayRange,
			isFirstPage: paginationHooks.isFirstPage,
			isLastPage: paginationHooks.isLastPage,
		},
		total,
		users,
		user,
		fetchUsersWith,
		getUserWithDetail,
	};
};
