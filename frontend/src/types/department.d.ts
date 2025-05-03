export interface DepartmentUpsertModel {
	id?: number;
	name: string;
	parentId?: number;
	enable: boolean;
}

export interface Department {
	id: number;
	name: string;
	code: string;
	parentId?: number;
	enable: boolean;
}
