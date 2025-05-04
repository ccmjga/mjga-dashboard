import client from "@/api/client";

export const usePositionDelete = () => {
	const deletePosition = async (positionId: number) => {
		await client.DELETE("/position", {
			params: {
				query: {
					id: positionId,
				},
			},
		});
	};
	return {
		deletePosition,
	};
};

export default usePositionDelete;
