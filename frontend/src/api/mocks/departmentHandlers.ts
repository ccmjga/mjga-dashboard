import { faker } from "@faker-js/faker";
import { http, HttpResponse } from "msw";

export default [
	http.get("/department/list", () => {
		const generateDepartment = () => ({
			id: faker.number.int({ min: 1, max: 100 }),
			name: faker.company.name(),
			parentId: faker.number.int({ min: 1, max: 100 }),
			enable: faker.datatype.boolean(),
		});
		const mockData = {
			data: faker.helpers.multiple(generateDepartment, { count: 10 }),
			total: 30,
		};
		return HttpResponse.json(mockData);
	}),
	http.get("/department/all", () => {
		const generateDepartment = () => ({
			id: faker.number.int({ min: 1, max: 30 }),
			name: faker.company.name(),
			parentId: faker.number.int({ min: 1, max: 30 }),
			enable: faker.datatype.boolean(),
		});
		const mockData = faker.helpers.multiple(generateDepartment, { count: 30 });

		return HttpResponse.json(mockData);
	}),

	http.post("/department", () => {
		console.log("Captured department upsert");
		return HttpResponse.json();
	}),
	http.delete("/department", () => {
		console.log("Captured department delete");
		return HttpResponse.json();
	}),
];
