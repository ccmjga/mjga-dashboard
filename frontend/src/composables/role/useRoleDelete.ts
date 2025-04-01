import client from "../../api/client";

const useRoleDelete = () => {
	const deleteRole = async (roleId: number) => {
		await client.DELETE("/urp/role", {
			params: {
				query: {
					roleId,
				},
			},
		});
	};

	return {
		deleteRole,
	};
};

export default useRoleDelete;
