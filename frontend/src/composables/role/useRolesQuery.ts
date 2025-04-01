import client from "@/api/client";
import { computed, ref } from "vue";
import { usePagination } from "../page";
import type { Role } from "../../types/role";

export const useRolesPaginationQuery = (page: number, size: number) => {
	const paginationHooks = usePagination({
		initialPage: page,
		initialPageSize: size,
	});

	const total = ref<number>(0);
	const roles = ref<Role[]>();

	const fetchRolesWith = async (
		page: number,
		size: number,
		param: {
			name?: string;
		},
	) => {
		const { data } = await client.GET("/urp/roles", {
			params: {
				query: {
					pageRequestDto: {
						page,
						size,
					},
					roleQueryDto: {
						roleName: param.name,
					},
				},
			},
		});

		if (!data) {
			throw new Error("获取角色数据失败");
		}

		total.value = data.total ?? 0;
		roles.value = data.data ?? [];

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
		roles,
		fetchRolesWith,
	};
};
