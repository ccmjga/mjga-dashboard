import type { RouteRecordRaw } from "vue-router";
import { RouteName, RoutePath } from "../constants";

const errorRoutes: RouteRecordRaw[] = [
	{
		path: RoutePath.GLOBAL_NOTFOUND,
		name: RouteName.GLOBAL_NOTFOUND,
		component: () => import("../../views/NotFound.vue"),
	},
];

export default errorRoutes;
