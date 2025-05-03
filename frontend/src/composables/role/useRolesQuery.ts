import client from "@/api/client";
import { ref } from "vue";
import type { components } from "../../api/types/schema";

export const useRolesQuery = () => {
	const total = ref<number>(0);
	const roles = ref<components["schemas"]["RoleDto"][]>();
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
		param: {
			name?: string;
			userId?: number;
			bindState?: "BIND" | "ALL" | "UNBIND";
		},
		page = 1,
		size = 10,
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
	};

	return {
		total,
		roles,
		roleWithDetail,
		getRoleWithDetail,
		fetchRolesWith,
	};
};
