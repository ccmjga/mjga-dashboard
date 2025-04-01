import "vue-router";

declare module "vue-router" {
	interface RouteMeta {
		requiresAuth?: boolean;
		hasPermission?: string;
		hasRole?: string;
	}
}

export { RouteMeta };
