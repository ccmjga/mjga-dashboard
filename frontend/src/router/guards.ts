import useUserStore from "@/composables/store/useUserStore";
import type { NavigationGuard, Router } from "vue-router";
import type { RouteMeta } from "../types/router";
import { RoutePath } from "./constants";

export const authGuard: NavigationGuard = (to) => {
	const userStore = useUserStore();
	if (to.meta.requiresAuth && !userStore.user) {
		return {
			path: RoutePath.LOGIN,
			query: { redirect: to.fullPath },
		};
	}
	if (to.path === RoutePath.LOGIN && userStore.user) {
		return { path: `${RoutePath.DASHBOARD}/${RoutePath.OVERVIEW}` };
	}
};

export const permissionGuard: NavigationGuard = (to) => {
	const userStore = useUserStore();
	const routeMeta: RouteMeta = to.meta;
	if (routeMeta.hasPermission) {
		const hasPermission = userStore.permissionCodes?.includes(
			routeMeta.hasPermission,
		);
		if (!hasPermission) {
			return false;
		}
	}
};

export const roleGuard: NavigationGuard = (to) => {
	const userStore = useUserStore();
	const routeMeta: RouteMeta = to.meta;
	if (routeMeta.hasRole) {
		const hasRole = userStore.roleCodes?.includes(routeMeta.hasRole);
		if (!hasRole) {
			return false;
		}
	}
};

export const setupGuards = (router: Router) => {
	router.beforeEach(authGuard);
	router.beforeEach(permissionGuard);
	router.beforeEach(roleGuard);
};
