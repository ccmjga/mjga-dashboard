import client from "../../api/client";

export const useDepartmentUpsert = () => {
	const upsertDepartment = async (department: {
		id?: number;
		name: string;
		enable: boolean;
		parentId?: number;
	}) => {
		await client.POST("/department", {
			body: department,
		});
	};

	return {
		upsertDepartment,
	};
};
