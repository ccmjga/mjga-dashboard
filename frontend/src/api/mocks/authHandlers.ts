import { faker } from "@faker-js/faker";
import { http, HttpResponse } from "msw";

export default [
	http.post("/auth/sign-in", () => {
		const response = HttpResponse.json();
		response.headers.set("Authorization", faker.string.alpha(16));
		return response;
	}),
];
