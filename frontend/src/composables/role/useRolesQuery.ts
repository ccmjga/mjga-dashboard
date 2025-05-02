import client from "@/api/client";
import { computed, ref } from "vue";
import type { components } from "../../api/types/schema";
import type { Role } from "../../types/role";
import { usePagination } from "../page";

export const useRolesQuery = (page = 1, size = 10) => {
	const paginationHooks = usePagination({
		initialPage: page,
		initialPageSize: size,
	});

	const total = ref<number>(0);
	const roles = ref<Role[]>();
	const roleWithDetail = ref<components["schemas"]["RoleDto"]>();

	const getRoleWithDetail = async (roleId: number) => {
		const { data } = await client.GET("/urp/role", {
			params: {
				query: {
					roleId,
				},
			},
		});
		roleWithDetail.value = data;
	};

	const fetchRolesWith = async (
		page: number,
		size: number,
		param: {
			name?: string;
			userId?: number;
			bindState?: "BIND" | "ALL" | "UNBIND";
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
						userId: param.userId,
						bindState: param.bindState,
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
		roleWithDetail,
		getRoleWithDetail,
		fetchRolesWith,
	};
};
