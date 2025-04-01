import client from "@/api/client";
import { ref } from "vue";
import { usePagination } from "../page";
import type { Permission } from "../../types/permission";

const usePermissionsPaginationQuery = (page: number, size: number) => {
	const paginationHooks = usePagination({
		initialPage: page,
		initialPageSize: size,
	});

	const total = ref<number>(0);
	const permissions = ref<Permission[]>([]);

	const fetchPermissionsWith = async (
		page: number,
		size: number,
		query: {
			name?: string;
		},
	) => {
		const { data } = await client.GET("/urp/permissions", {
			params: {
				query: {
					pageRequestDto: {
						page,
						size,
					},
					permissionQueryDto: {
						permissionName: query.name,
					},
				},
			},
		});

		if (!data) {
			throw new Error("获取权限数据失败");
		}

		total.value = data.total ?? 0;
		permissions.value = data.data ?? [];

		paginationHooks.updatePaginationState({
			currentPage: page,
			pageSize: size,
			total: total.value,
		});
	};

	return {
		pagination: paginationHooks,
		total,
		permissions,
		fetchPermissionsWith,
	};
};

export default usePermissionsPaginationQuery;
