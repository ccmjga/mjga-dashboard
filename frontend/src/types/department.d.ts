export interface DepartmentUpsertModel {
	id?: number;
	name: string;
	parentId?: number | null;
}
