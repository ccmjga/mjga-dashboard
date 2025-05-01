import client from "@/api/client";

export const useRoleBind = () => {
	const bindRole = async (userId: number, roleIds: number[]) => {
		// biome-ignore lint/style/noUnusedTemplateLiteral: <explanation>
		await client.POST("/urp/users/{userId}/bind-role", {
			params: {
				path: {
					userId,
				},
			},
			body: roleIds,
		});
	};

	const unbindRole = async (userId: number, roleIds: number[]) => {
		// biome-ignore lint/style/noUnusedTemplateLiteral: <explanation>
		await client.POST("/urp/users/{userId}/unbind-role", {
			params: {
				path: {
					userId,
				},
			},
			body: roleIds,
		});
	};
	return {
		bindRole,
		unbindRole,
	};
};
