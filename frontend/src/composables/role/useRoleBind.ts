import client from "@/api/client";

export const useRoleBind = () => {
	const bindRole = async ({
		userId,
		roleIds,
	}: { userId: number; roleIds: number[] }) => {
		await client.POST("/urp/role/bind", {
			body: {
				userId,
				roleIds,
			},
		});
	};

	const unbindRole = async (userId: number, roleIds: number[]) => {
		await client.POST("/urp/role/unbind", {
			body: {
				userId,
				roleIds,
			},
		});
	};
	return {
		bindRole,
		unbindRole,
	};
};
