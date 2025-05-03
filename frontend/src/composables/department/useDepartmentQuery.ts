import client from "@/api/client";
import { ref } from "vue";
import { usePagination } from "../page";
import type { components } from "../../api/types/schema";

export const useDepartmentQuery = () => {
	const paginationHooks = usePagination({
		initialPage: 1,
		initialPageSize: 10,
	});

	const total = ref<number>(0);
	const departments = ref<components["schemas"]["Department"][]>([]);
	const allDepartments = ref<components["schemas"]["Department"][]>([]);

	const fetchAllDepartments = async () => {
		const { data } = await client.GET("/department/all", {});
		allDepartments.value = data ?? [];
	};

	const fetchDepartmentsWith = async (
		page: number,
		size: number,
		param: {
			name?: string;
			enable?: boolean;
		},
	) => {
		const { data } = await client.GET("/department/list", {
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
		departments,
		allDepartments,
		fetchDepartmentsWith,
		fetchAllDepartments,
	};
};
