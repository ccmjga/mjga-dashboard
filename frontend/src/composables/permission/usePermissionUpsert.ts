import client from "../../api/client";

const usePermissionUpsert = () => {
	const upsertPermission = async (permission: {
		id?: number;
		name: string;
		code: string;
	}) => {
		await client.POST("/urp/permission", {
			body: permission,
		});
	};

	return {
		upsertPermission,
	};
};
export default usePermissionUpsert;
