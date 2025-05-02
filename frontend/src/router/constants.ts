export enum RoutePath {
	HOME = "/",
	LOGIN = "/login",
	DASHBOARD = "/dashboard",
	GLOBAL_NOTFOUND = "/:pathMatch(.*)*",
	NOTFOUND = ":pathMatch(.*)*",
	OVERVIEW = "overview",
	USERVIEW = "users",
	ROLEVIEW = "roles",
	BINDROLEVIEW = "bind-roles/:userId",
	BINDPERMISSIONVIEW = "bind-permissions/:roleId",
	PERMISSIONVIEW = "permissions",
	CREATEUSERVIEW = "create-user",
	SCHEDULERVIEW = "scheduler",
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
	BINDROLEVIEW = "bind-roles",
	BINDPERMISSIONVIEW = "bind-permissions",
	PERMISSIONVIEW = "permissions",
	CREATEUSERVIEW = "create-user",
	SCHEDULERVIEW = "scheduler",
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
