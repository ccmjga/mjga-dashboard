import type { RouteRecordRaw } from "vue-router";
import Dashboard from "../../components/Dashboard.vue";
import OverView from "../../views/OverView.vue";
import { RouteName, RoutePath } from "../constants";
import userManagementRoutes from "./user";

const dashboardRoutes: RouteRecordRaw = {
	path: RoutePath.DASHBOARD,
	name: RouteName.DASHBOARD,
	component: Dashboard,
	meta: {
		requiresAuth: true,
	},
	children: [
		{
			path: RoutePath.OVERVIEW,
			name: RouteName.OVERVIEW,
			component: OverView,
			meta: {
				requiresAuth: true,
			},
		},
		{
			path: RoutePath.SETTINGS,
			name: RouteName.SETTINGS,
			component: () => import("../../views/Settings.vue"),
			meta: {
				requiresAuth: true,
			},
		},
		...userManagementRoutes,
		{
			path: RoutePath.NOTFOUND,
			name: RouteName.NOTFOUND,
			component: () => import("../../views/NotFound.vue"),
		},
	],
};

export default dashboardRoutes;
