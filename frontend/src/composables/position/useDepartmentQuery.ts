import client from "@/api/client";
import { ref } from "vue";
import type { components } from "../../api/types/schema";

export const usePositionQuery = () => {
	const total = ref<number>(0);
	const positions = ref<components["schemas"]["PositionRespDto"][]>([]);
	const allPositions = ref<components["schemas"]["Position"][]>([]);

	const fetchAllPositions = async () => {
		const { data } = await client.GET("/position/query");
		allPositions.value = data ?? [];
	};
	const fetchPositionWith = async (
		param: {
			name?: string;
			enable?: boolean;
			userId?: number;
			bindState?: "ALL" | "BIND" | "UNBIND";
		},
		page = 1,
		size = 10,
	) => {
		const { data } = await client.GET("/position/page-query", {
			params: {
				query: {
					pageRequestDto: {
						page,
						size,
					},
					positionQueryDto: param,
				},
			},
		});
		total.value = !data || !data.total ? 0 : data.total;
		positions.value = data?.data ?? [];
	};
	return {
		total,
		positions,
		allPositions,
		fetchPositionWith,
		fetchAllPositions,
	};
};
