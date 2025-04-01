export interface UserRolePermission {
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

export type User = UserRolePermission | null;
