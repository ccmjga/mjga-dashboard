export interface UserRolePermissionModel {
	id?: number;
	username?: string;
	password?: string;
	enable?: boolean;
	roles?: Array<{
		id?: number;
		code?: string;
		name?: string;
		permissions?: Array<{
			id?: number;
			code?: string;
			name?: string;
		}>;
	}>;
	createTime?: string;
}

export interface UserUpsertSubmitModel {
	id?: number;
	username: string;
	password: string;
	enable: boolean;
	confirmPassword?: string;
}

export type User = UserRolePermissionModel | null;
