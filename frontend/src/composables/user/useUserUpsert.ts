import { ref } from "vue";
import client from "../../api/client";

export const useUserUpsert = () => {
	const upsertUser = async (user: {
		id?: number;
		username: string;
		password: string;
		enable: boolean;
	}) => {
		await client.POST("/urp/user", {
			body: user,
		});
	};

	return {
		upsertUser,
	};
};
