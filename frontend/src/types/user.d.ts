export interface UserUpsertSubmitModel {
	id?: number;
	username: string;
	password?: string;
	enable: boolean;
}

export type User = UserRolePermissionModel | null;
