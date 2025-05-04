import { faker } from "@faker-js/faker";
import { http, HttpResponse } from "msw";

export default [
	http.get("/position/page-query", () => {
		const generatePosition = () => ({
			id: faker.number.int({ min: 1, max: 100 }),
			name: faker.person.jobTitle(),
			isBound: faker.datatype.boolean(),
		});
		const mockData = {
			data: faker.helpers.multiple(generatePosition, { count: 10 }),
			total: 30,
		};
		return HttpResponse.json(mockData);
	}),
	http.get("/position/query", () => {
		const generatePosition = () => ({
			id: faker.number.int({ min: 1, max: 30 }),
			name: faker.person.jobTitle(),
		});
		const mockData = faker.helpers.multiple(generatePosition, { count: 30 });

		return HttpResponse.json(mockData);
	}),

	http.post("/position", () => {
		console.log("Captured position upsert");
		return HttpResponse.json();
	}),
	http.delete("/position", () => {
		console.log("Captured position delete");
		return HttpResponse.json();
	}),
];
