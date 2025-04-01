import client from "../../api/client";

export const useRoleUpsert = () => {
	const upsertRole = async (role: {
		id?: number;
		name: string;
		code: string;
	}) => {
		await client.POST("/urp/role", {
			body: role,
		});
	};

	return {
		upsertRole,
	};
};
