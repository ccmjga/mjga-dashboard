import { faker } from "@faker-js/faker";
import { http, HttpResponse } from "msw";

export default [
	http.get("/urp/roles", () => {
		const generatePermission = () => ({
			id: faker.number.int({ min: 1, max: 1000 }),
			code: `perm_${faker.lorem.word()}`,
			name: faker.lorem.words({ min: 1, max: 3 }),
		});

		const generateRole = () => ({
			id: faker.number.int({ min: 1, max: 100 }),
			code: faker.helpers.arrayElement([
				"admin",
				"editor",
				"viewer",
				"manager",
			]),
			name: faker.person.jobTitle(),
			permissions: faker.helpers.multiple(generatePermission, {
				count: { min: 1, max: 5 },
			}),
		});

		const mockData = {
			data: faker.helpers.multiple(generateRole, { count: 20 }),
			total: 20,
		};

		return HttpResponse.json(mockData);
	}),

	http.post("/urp/role", async ({ request }) => {
		console.log('Captured a "POST /urp/role" request');
		return HttpResponse.json();
	}),

	http.post("/urp/users/:userId/bind-role", async ({ request }) => {
		console.log('Captured a "POST /urp/users/:userId/bind-role" request');
		return HttpResponse.json();
	}),

	http.post("/urp/users/:userId/unbind-role", async ({ request }) => {
		console.log('Captured a "POST /urp/users/:userId/unbind-role" request');
		return HttpResponse.json();
	}),

	http.delete("/urp/role", ({ params }) => {
		console.log(`Captured a "DELETE /urp/role ${params.id}" request`);
		return HttpResponse.json();
	}),
];
