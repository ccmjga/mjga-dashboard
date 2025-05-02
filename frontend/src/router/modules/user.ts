import type { RouteRecordRaw } from "vue-router";
import { ROLE, RouteName, RoutePath } from "../constants";

const userManagementRoutes: RouteRecordRaw[] = [
	{
		path: RoutePath.USERVIEW,
		name: RouteName.USERVIEW,
		component: () => import("@/views/UserView.vue"),
		meta: {
			requiresAuth: true,
			hasRole: ROLE.ADMIN,
		},
	},
	{
		path: RoutePath.ROLEVIEW,
		name: RouteName.ROLEVIEW,
		component: () => import("@/views/RoleView.vue"),
		meta: {
			requiresAuth: true,
			hasRole: ROLE.ADMIN,
		},
	},
	{
		path: RoutePath.BINDROLEVIEW,
		name: RouteName.BINDROLEVIEW,
		component: () => import("@/views/BindRoleView.vue"),
		meta: {
			requiresAuth: true,
			hasRole: ROLE.ADMIN,
		},
	},
	{
		path: RoutePath.BINDPERMISSIONVIEW,
		name: RouteName.BINDPERMISSIONVIEW,
		component: () => import("@/views/BindPermissionView.vue"),
		meta: {
			requiresAuth: true,
			hasRole: ROLE.ADMIN,
		},
	},
	{
		path: RoutePath.PERMISSIONVIEW,
		name: RouteName.PERMISSIONVIEW,
		component: () => import("@/views/PermissionView.vue"),
		meta: {
			requiresAuth: true,
			hasRole: ROLE.ADMIN,
		},
	},
];

export default userManagementRoutes;
