import client from "@/api/client";
export const usePermissionBind = () => {
	const bindPermission = async (roleId: number, permissionIds: number[]) => {
		// biome-ignore lint/style/noUnusedTemplateLiteral: <explanation>
		await client.POST("/urp/roles/{roleId}/bind-permission", {
			params: {
				path: {
					roleId,
				},
			},
			body: permissionIds,
		});
	};

	const unbindPermission = async (roleId: number, permissionIds: number[]) => {
		// biome-ignore lint/style/noUnusedTemplateLiteral: <explanation>
		await client.POST("/urp/roles/{roleId}/unbind-permission", {
			params: {
				path: {
					roleId,
				},
			},
			body: permissionIds,
		});
	};
	return {
		bindPermission,
		unbindPermission,
	};
};
