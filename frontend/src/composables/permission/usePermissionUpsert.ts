import client from "../../api/client";
import type { PermissionUpsertModel } from "../../types/permission";

const usePermissionUpsert = () => {
	const upsertPermission = async (permission: PermissionUpsertModel) => {
		await client.POST("/urp/permission", {
			body: {
				id: permission.id,
				name: permission.name,
				code: permission.code,
			},
		});
	};

	return {
		upsertPermission,
	};
};
export default usePermissionUpsert;
