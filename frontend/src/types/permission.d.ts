export interface PermissionModel {
	id?: number;
	name?: string;
	code?: string;
}

export interface PermissionUpsertModel {
	id: number | undefined;
	name: string | undefined;
	code: string | undefined;
}
