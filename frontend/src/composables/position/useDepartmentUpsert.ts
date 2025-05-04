import client from "../../api/client";
import type { components } from "../../api/types/schema";

export const usePositionUpsert = () => {
	const upsertPosition = async (
		position: components["schemas"]["Position"],
	) => {
		await client.POST("/position", {
			body: position,
		});
	};

	return {
		upsertPosition,
	};
};
