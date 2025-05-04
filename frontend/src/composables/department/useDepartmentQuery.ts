import client from "@/api/client";
import { ref } from "vue";
import type { components } from "../../api/types/schema";

export const useDepartmentQuery = () => {
	const total = ref<number>(0);
	const departments = ref<components["schemas"]["DepartmentRespDto"][]>([]);
	const allDepartments = ref<components["schemas"]["Department"][]>([]);

	const fetchAllDepartments = async () => {
		const { data } = await client.GET("/department/query");
		allDepartments.value = data ?? [];
	};
	const fetchDepartmentWith = async (
		param: {
			name?: string;
			enable?: boolean;
			userId?: number;
			bindState?: "ALL" | "BIND" | "UNBIND";
		},
		page = 1,
		size = 10,
	) => {
		const { data } = await client.GET("/department/page-query", {
			params: {
				query: {
					pageRequestDto: {
						page,
						size,
					},
					departmentQueryDto: param,
				},
			},
		});
		total.value = !data || !data.total ? 0 : data.total;
		departments.value = data?.data ?? [];
	};
	return {
		total,
		departments,
		allDepartments,
		fetchDepartmentWith,
		fetchAllDepartments,
	};
};
