export interface UserUpsertSubmitModel {
	id?: number;
	username: string;
	password: string;
	enable: boolean;
	confirmPassword?: string;
}

export type User = UserRolePermissionModel | null;
