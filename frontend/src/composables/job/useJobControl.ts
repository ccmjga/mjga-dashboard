import client from "@/api/client";
import { ref } from "vue";

export const useJobControl = () => {
	const resumeTrigger = async (trigger: {
		triggerName: string;
		triggerGroup: string;
		jobQueryParam?: {
			name?: string;
		};
	}) => {
		await client.POST("/scheduler/trigger/resume", {
			body: {
				name: trigger.triggerName,
				group: trigger.triggerGroup,
			},
		});
	};

	const pauseTrigger = async (trigger: {
		triggerName: string;
		triggerGroup: string;
		jobQueryParam?: {
			name?: string;
		};
	}) => {
		await client.POST("/scheduler/trigger/pause", {
			body: {
				name: trigger.triggerName,
				group: trigger.triggerGroup,
			},
		});
	};

	return {
		pauseTrigger,
		resumeTrigger,
	};
};
