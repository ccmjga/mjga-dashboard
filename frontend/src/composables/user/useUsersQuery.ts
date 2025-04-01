import client from "@/api/client";
import { ref } from "vue";
import type { UserRolePermission } from "../../types/user";
import { usePagination } from "../page";

export const useUsersPaginationQuery = (page: number, size: number) => {
	const paginationHooks = usePagination({
		initialPage: page,
		initialPageSize: size,
	});

	const total = ref<number>(0);
	const users = ref<UserRolePermission[]>([]);

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
		fetchUsersWith,
	};
};
