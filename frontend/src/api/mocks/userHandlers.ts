import { faker } from "@faker-js/faker";
import { http, HttpResponse } from "msw";
import { ROLE } from "../../router/constants";

export default [
	http.get("/urp/user", () => {
		const generatePermission = () => ({
			id: faker.number.int({ min: 1, max: 100 }),
			code: `perm_${faker.lorem.word()}`,
			name: faker.lorem.words({ min: 1, max: 3 }),
		});

		const generateRole = () => ({
			id: faker.number.int({ min: 1, max: 100 }),
			code: faker.helpers.arrayElement([
				ROLE.ADMIN,
				"editor",
				"viewer",
				"manager",
			]),
			name: faker.person.jobTitle(),
			permissions: faker.helpers.multiple(generatePermission, {
				count: { min: 1, max: 5 },
			}),
		});

		const generateUser = () => ({
			id: faker.number.int({ min: 1, max: 100 }),
			username: faker.internet.email(),
			password: faker.internet.password(),
			enable: faker.datatype.boolean(),
			roles: faker.helpers.multiple(generateRole, {
				count: { min: 1, max: 3 },
			}),
			createTime: faker.date.recent({ days: 30 }).toISOString(),
			permissions: faker.helpers.multiple(generatePermission, {
				count: { min: 1, max: 5 },
			}),
		});

		return HttpResponse.json(generateUser());
	}),
	http.get("/urp/users", () => {
		const generatePermission = () => ({
			id: faker.number.int({ min: 1, max: 100 }),
			code: `perm_${faker.lorem.word()}`,
			name: faker.lorem.words({ min: 1, max: 3 }),
		});

		const generateRole = () => ({
			id: faker.number.int({ min: 1, max: 100 }),
			code: [ROLE.ADMIN, "editor", "viewer", "manager"],
			name: faker.person.jobTitle(),
			permissions: faker.helpers.multiple(generatePermission, {
				count: { min: 1, max: 5 },
			}),
		});

		const generateUser = () => ({
			id: faker.number.int({ min: 1, max: 100 }),
			username: faker.internet.email(),
			password: faker.internet.password(),
			enable: faker.datatype.boolean(),
			roles: faker.helpers.multiple(generateRole, {
				count: { min: 1, max: 3 },
			}),
			createTime: faker.date.recent({ days: 30 }).toISOString(),
			permissions: faker.helpers.multiple(generatePermission, {
				count: { min: 1, max: 5 },
			}),
		});

		const mockData = {
			data: faker.helpers.multiple(generateUser, { count: 10 }),
			total: 30,
		};
		return HttpResponse.json(mockData);
	}),
	http.post("/urp/user", () => {
		console.log('Captured a "POST /posts" request');
		return HttpResponse.json();
	}),
	http.delete("/urp/user", ({ params }) => {
		console.log(`Captured a "DELETE /posts/${params.id}" request`);
		return HttpResponse.json();
	}),
	http.post("/urp/me", () => {
		console.log('Captured a "POST /posts" request');
		return HttpResponse.json();
	}),
	http.get("/urp/me", () => {
		const generatePermission = () => ({
			id: faker.number.int({ min: 1, max: 1000 }),
			code: `perm_${faker.lorem.word()}`,
			name: faker.lorem.words({ min: 1, max: 3 }),
		});

		const generateRole = () => ({
			id: faker.number.int({ min: 1, max: 100 }),
			code: [ROLE.ADMIN, "editor", "viewer", "manager"],
			name: faker.person.jobTitle(),
			permissions: faker.helpers.multiple(generatePermission, {
				count: { min: 1, max: 5 },
			}),
		});

		const generateUser = () => ({
			id: faker.number.int({ min: 1, max: 100 }),
			username: faker.internet.email(),
			password: faker.internet.password(),
			enable: faker.datatype.boolean(),
			roles: faker.helpers.multiple(generateRole, {
				count: { min: 1, max: 3 },
			}),
			createTime: faker.date.recent({ days: 30 }).toISOString(),
			permissions: faker.helpers.multiple(generatePermission, {
				count: { min: 1, max: 5 },
			}),
		});
		const mockData = generateUser();
		return HttpResponse.json(mockData);
	}),
];
