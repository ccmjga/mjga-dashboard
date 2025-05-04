import client from "@/api/client";

export function usePositionBind() {
	const bindPosition = async (userId: number, positionIds: number[]) => {
		try {
			await client.POST("/urp/position/bind", {
				body: {
					userId,
					positionIds,
				},
			});
			return true;
		} catch (error) {
			console.error("Error binding positions:", error);
			return false;
		}
	};

	const unbindPosition = async (userId: number, positionIds: number[]) => {
		try {
			await client.POST("/urp/position/unbind", {
				body: {
					userId,
					positionIds,
				},
			});
			return true;
		} catch (error) {
			console.error("Error unbinding positions:", error);
			return false;
		}
	};

	return {
		bindPosition,
		unbindPosition,
	};
}
