import client from "@/api/client";
import { ref } from "vue";

export const useJobUpdate = () => {
	const updateCron = async (trigger: {
		triggerName: string;
		triggerGroup: string;
		cron: string;
	}) => {
		await client.POST("/scheduler/job/update", {
			params: {
				query: {
					cron: trigger.cron,
				},
			},
			body: {
				name: trigger.triggerName,
				group: trigger.triggerGroup,
			},
		});
	};
	return {
		updateCron,
	};
};
