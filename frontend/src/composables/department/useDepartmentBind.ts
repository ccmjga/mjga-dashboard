import client from "@/api/client";

export function useDepartmentBind() {
	const bindDepartment = async (userId: number, departmentIds: number[]) => {
		try {
			await client.POST("/urp/department/bind", {
				body: {
					userId,
					departmentIds,
				},
			});
			return true;
		} catch (error) {
			console.error("Error binding departments:", error);
			return false;
		}
	};

	const unbindDepartment = async (userId: number, departmentIds: number[]) => {
		try {
			await client.POST("/urp/department/unbind", {
				body: {
					userId,
					departmentIds,
				},
			});
			return true;
		} catch (error) {
			console.error("Error unbinding departments:", error);
			return false;
		}
	};

	return {
		bindDepartment,
		unbindDepartment,
	};
}
