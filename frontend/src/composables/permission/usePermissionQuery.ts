import client from "@/api/client";
import { ref } from "vue";
import type { components } from "../../api/types/schema";

const usePermissionsQuery = () => {
	const total = ref<number>(0);
	const permissions = ref<components["schemas"]["PermissionDto"][]>([]);
	const fetchPermissionsWith = async (
		query: {
			name?: string;
			roleId?: number;
			bindState?: "BIND" | "ALL" | "UNBIND";
		},
		page = 1,
		size = 10,
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
						roleId: query.roleId,
						bindState: query.bindState,
					},
				},
			},
		});

		if (!data) {
			throw new Error("获取权限数据失败");
		}

		total.value = data.total ?? 0;
		permissions.value = data.data ?? [];
	};

	return {
		total,
		permissions,
		fetchPermissionsWith,
	};
};

export default usePermissionsQuery;
