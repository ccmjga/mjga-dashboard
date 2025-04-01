import { ref } from "vue";
import client from "@/api/client";
import useUserStore from "../store/useUserStore";
import useAuthStore from "../store/useAuthStore";

const useUserAuth = () => {
	const isAuthenticated = ref(false);
	const authStore = useAuthStore();
	const userStore = useUserStore();

	const queryCurrentUser = async () => {
		const { data } = await client.GET("/urp/me");
		return data;
	};

	const refreshCurrentUser = async () => {
		const currentUser = await queryCurrentUser();
		if (currentUser) {
			userStore.set(currentUser);
			isAuthenticated.value = true;
		}
	};

	const upsertCurrentUser = async (user: {
		username: string;
		password: string;
		enable: boolean;
	}) => {
		await client.POST("/urp/me", {
			body: user,
		});
		await refreshCurrentUser();
	};

	const signIn = async (username: string, password: string) => {
		const signInResponse = await client.POST("/auth/sign-in", {
			body: {
				username,
				password,
			},
		});
		authStore.set(
			signInResponse.response.headers.get("authorization") ?? undefined,
		);
		await refreshCurrentUser();
	};

	const signOut = () => {
		authStore.remove();
		isAuthenticated.value = false;
		userStore.remove();
	};

	return {
		isAuthenticated,
		signIn,
		signOut,
		queryCurrentUser,
		upsertCurrentUser,
	};
};

export default useUserAuth;
