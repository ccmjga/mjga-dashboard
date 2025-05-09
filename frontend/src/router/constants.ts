export enum RoutePath {
	HOME = "/",
	LOGIN = "/login",
	DASHBOARD = "/dashboard",
	GLOBAL_NOTFOUND = "/:pathMatch(.*)*",
	NOTFOUND = ":pathMatch(.*)*",
	OVERVIEW = "overview",
	USERVIEW = "users",
	ROLEVIEW = "roles",
	PERMISSIONVIEW = "permissions",
	CREATEUSERVIEW = "create-user",
	UPSERTUSERVIEW = "upsert-user",
	UPSERTROLEVIEW = "upsert-role",
	UPSERTPERMISSIONVIEW = "upsert-permission",
	SETTINGS = "settings",
}

export enum RouteName {
	HOME = "home",
	LOGIN = "login",
	DASHBOARD = "dashboard",
	OVERVIEW = "overview",
	USERVIEW = "users",
	ROLEVIEW = "roles",
	PERMISSIONVIEW = "permissions",
	CREATEUSERVIEW = "create-user",
	UPSERTUSERVIEW = "upsert-user",
	UPSERTROLEVIEW = "upsert-role",
	UPSERTPERMISSIONVIEW = "upsert-permission",
	SETTINGS = "settings",
	NOTFOUND = "notfound",
	GLOBAL_NOTFOUND = "global-notfound",
}

export enum ROLE {
	ADMIN = "ADMIN",
	USER = "USER",
}

export enum PERMISSION {
	USER_VIEW = "user:view",
	USER_ADD = "user:add",
	USER_EDIT = "user:edit",
	USER_DELETE = "user:delete",
}
