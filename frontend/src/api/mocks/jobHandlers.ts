import { faker } from "@faker-js/faker";
import { http, HttpResponse } from "msw";

export default [
	http.get("/scheduler/jobs", () => {
		const generateJobs = () => ({
			name: faker.word.sample(),
			group: faker.helpers.arrayElement(["default", "system", "custom"]),
			className: `com.example.jobs.${faker.word.sample()}Job`,
			jobDataMap: {
				dirty: faker.datatype.boolean(),
				allowsTransientData: faker.datatype.boolean(),
				keys: faker.helpers.multiple(() => faker.word.sample(), { count: 3 }),
				empty: false,
				wrappedMap: {},
			},
			triggerName: faker.word.sample(),
			triggerGroup: faker.helpers.arrayElement(["DEFAULT", "SYSTEM"]),
			schedulerType: "CRON",
			cronExpression: "0 0/30 * * * ?",
			startTime: faker.date.past().getTime(),
			endTime: faker.date.future().getTime(),
			nextFireTime: faker.date.soon().getTime(),
			previousFireTime: faker.date.recent().getTime(),
			triggerJobDataMap: {
				dirty: faker.datatype.boolean(),
				allowsTransientData: true,
				keys: [],
				empty: true,
				wrappedMap: {},
			},
		});

		const mockData = {
			data: faker.helpers.multiple(generateJobs, { count: 20 }),
			total: 20,
		};
		return HttpResponse.json(mockData);
	}),
];
