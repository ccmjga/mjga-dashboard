import client from "@/api/client";
export const usePermissionBind = () => {
	const bindPermission = async (roleId: number, permissionIds: number[]) => {
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
