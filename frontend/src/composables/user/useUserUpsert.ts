import { ref } from "vue";
import client from "../../api/client";
import type { UserUpsertSubmitModel } from "../../types/user";

export const useUserUpsert = () => {
	const upsertUser = async (user: UserUpsertSubmitModel) => {
		const { data } = await client.POST("/urp/user", {
			body: {
				id: user.id,
				username: user.username,
				password: user.password,
				enable: user.enable,
			},
		});
	};

	return {
		upsertUser,
	};
};
