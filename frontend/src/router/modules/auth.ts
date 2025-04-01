import type { RouteRecordRaw } from "vue-router";
import { RouteName, RoutePath } from "../constants";

const authRoutes: RouteRecordRaw[] = [
	{
		path: RoutePath.HOME,
		name: RouteName.HOME,
		redirect: {
			name: RouteName.LOGIN,
		},
	},
	{
		path: RoutePath.LOGIN,
		name: RouteName.LOGIN,
		component: () => import("../../views/LoginView.vue"),
	},
];

export default authRoutes;
