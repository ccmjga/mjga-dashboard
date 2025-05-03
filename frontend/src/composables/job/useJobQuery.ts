import client from "@/api/client";
import { ref } from "vue";
import type { components } from "../../api/types/schema";
export const useJobsPaginationQuery = () => {
	const total = ref<number>(0);
	const jobs = ref<components["schemas"]["JobTriggerDto"][]>([]);
	const fetchJobsWith = async (
		param?: {
			name?: string;
		},
		page = 1,
		size = 10,
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
	};

	return {
		total,
		jobs,
		fetchJobsWith,
	};
};
