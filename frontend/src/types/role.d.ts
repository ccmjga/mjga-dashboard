export interface RoleModel {
	id?: number;
	name?: string;
	code?: string;
}

export interface RoleUpsertModel {
	id?: number;
	name: string;
	code: string;
}
