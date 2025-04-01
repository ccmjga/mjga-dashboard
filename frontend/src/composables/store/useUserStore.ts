import { StorageSerializers, useStorage } from "@vueuse/core";
import { defineStore } from "pinia";
import { computed } from "vue";
import type { UserRolePermission } from "../../types/user";

const useUserStore = defineStore("userStore", () => {
	const user = useStorage<UserRolePermission>(
		"user-storage",
		null,
		localStorage,
		{
			serializer: StorageSerializers.object,
		},
	);

	const set: (userRolePermission?: UserRolePermission) => void = (
		userRolePermission,
	) => {
		user.value = userRolePermission;
	};

	function remove() {
		user.value = null;
	}

	const roleCodes = computed(() => {
		return user.value?.roles?.flatMap((role) => role.code);
	});

	const permissionCodes = computed(() => {
		return user.value?.roles?.flatMap((role) =>
			role.permissions?.map((permission) => permission.code),
		);
	});

	return {
		user,
		set,
		remove,
		roleCodes,
		permissionCodes,
	};
});

export default useUserStore;
