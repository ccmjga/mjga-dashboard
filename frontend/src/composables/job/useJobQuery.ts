import client from "@/api/client";
import { ref } from "vue";
import type { components } from "../../api/types/schema";
import type { JobTriggerDto } from "../../types/jobs";
import type { UserRolePermission } from "../../types/user";
import { usePagination } from "../page";

export const useJobsPaginationQuery = (page: number, size: number) => {
	const paginationHooks = usePagination({
		initialPage: page,
		initialPageSize: size,
	});

	const total = ref<number>(0);
	const jobs = ref<JobTriggerDto[]>();

	const fetchJobsWith = async (
		page: number,
		size: number,
		param: {
			jobName?: string;
		},
	) => {
		const { data } = await client.GET("/scheduler/jobs", {
			params: {
				query: {
					pageRequestDto: {
						page: page,
						size: size,
					},
				},
			},
		});
		total.value = !data || !data.total ? 0 : data.total;
		jobs.value = data?.data ?? [];
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
		jobs,
		fetchJobsWith,
	};
};
