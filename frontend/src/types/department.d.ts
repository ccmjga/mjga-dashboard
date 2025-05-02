export type DepartmentUpsertRow = {
	id?: number;
	name: string;
	parentId?: number;
	enable: boolean;
};

export type SelectedDepartmentRow = {
	id: number;
	name: string;
	parentId?: number;
	enable: boolean;
};
