import { faker } from "@faker-js/faker";
import { http, HttpResponse } from "msw";

export default [
	http.get("/urp/permissions", () => {
		const generatePermission = () => ({
			id: faker.number.int({ min: 1, max: 1000 }),
			code: `perm_${faker.lorem.word()}`,
			name: faker.lorem.words({ min: 1, max: 3 }),
		});

		const mockData = {
			data: faker.helpers.multiple(generatePermission, { count: 20 }),
			total: 20,
		};
		return HttpResponse.json(mockData);
	}),

	http.post("/urp/permission", async ({ request }) => {
		console.log('Captured a "POST /posts" request');
		return HttpResponse.json();
	}),

	http.delete("/urp/permission", ({ params }) => {
		console.log(`Captured a "DELETE /posts/${params.id}" request`);
		return HttpResponse.json();
	}),
];
