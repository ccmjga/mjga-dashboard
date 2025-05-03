import client from "@/api/client";

export const useDepartmentDelete = () => {
	const deleteDepartment = async (departmentId: number) => {
		await client.DELETE("/department", {
			params: {
				query: {
					id: departmentId,
				},
			},
		});
	};
	return {
		deleteDepartment,
	};
};

export default useDepartmentDelete;
