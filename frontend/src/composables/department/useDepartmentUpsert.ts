import client from "../../api/client";
import type { components } from "../../api/types/schema";

export const useDepartmentUpsert = () => {
	const upsertDepartment = async (
		department: components["schemas"]["Department"],
	) => {
		await client.POST("/department", {
			body: department,
		});
	};

	return {
		upsertDepartment,
	};
};
