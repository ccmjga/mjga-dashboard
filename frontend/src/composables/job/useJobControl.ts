import client from "@/api/client";
import { ref } from "vue";

export const useJobControl = () => {
	const isLoading = ref(false);
	const resumeTrigger = async (trigger: {
		triggerName: string;
		triggerGroup: string;
		jobQueryParam?: {
			name?: string;
		};
	}) => {
		isLoading.value = true;
		await client.POST("/scheduler/trigger/resume", {
			body: {
				name: trigger.triggerName,
				group: trigger.triggerGroup,
			},
		});
		isLoading.value = false;
	};

	const pauseTrigger = async (trigger: {
		triggerName: string;
		triggerGroup: string;
		jobQueryParam?: {
			name?: string;
		};
	}) => {
		isLoading.value = true;
		await client.POST("/scheduler/trigger/pause", {
			body: {
				name: trigger.triggerName,
				group: trigger.triggerGroup,
			},
		});
		isLoading.value = false;
	};

	return {
		pauseTrigger,
		resumeTrigger,
		isLoading,
	};
};
