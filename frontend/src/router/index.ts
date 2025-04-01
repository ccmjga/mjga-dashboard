import { createRouter, createWebHistory } from "vue-router";
import type { RouteRecordRaw } from "vue-router";
import { setupGuards } from "./guards";

import authRoutes from "./modules/auth";
import dashboardRoutes from "./modules/dashboard";
import errorRoutes from "./modules/error";

const routes: RouteRecordRaw[] = [
	dashboardRoutes,
	...authRoutes,
	...errorRoutes,
];

const router = createRouter({
	history: createWebHistory(import.meta.env.BASE_URL),
	routes,
});

setupGuards(router);

export default router;
