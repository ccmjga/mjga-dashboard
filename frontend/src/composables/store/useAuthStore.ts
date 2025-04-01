import { StorageSerializers, useStorage } from "@vueuse/core";
import { defineStore } from "pinia";

const useAuthStore = defineStore("authStore", () => {
	const tokenStore = useStorage<string>("auth-storage", null, localStorage, {
		serializer: StorageSerializers.object,
	});

	const set = (token?: string) => {
		tokenStore.value = token;
	};

	const get = () => {
		return tokenStore.value;
	};

	const remove = () => {
		tokenStore.value = undefined;
	};

	return {
		set,
		get,
		remove,
	};
});

export default useAuthStore;
