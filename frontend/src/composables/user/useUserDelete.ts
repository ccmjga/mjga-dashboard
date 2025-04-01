import { ref } from "vue";
import client from "../../api/client";

const useUserDelete = () => {
	const deleteUser = async (userId: number) => {
		await client.DELETE("/urp/user", {
			params: {
				query: {
					userId,
				},
			},
		});
	};
	return {
		deleteUser,
	};
};

export default useUserDelete;
