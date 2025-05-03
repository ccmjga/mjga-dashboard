<template>
  <div id="permission-upsert-modal" tabindex="-1" aria-hidden="true"
    class="bg-gray-900/50 dark:bg-gray-900/80 hidden overflow-y-auto overflow-x-hidden fixed top-0 right-0 left-0 z-50 justify-center items-center w-full md:inset-0 h-[calc(100%-1rem)] max-h-full">
    <div class="relative p-4 w-full max-w-md max-h-full">
      <div class="relative bg-white rounded-lg shadow dark:bg-gray-700">
        <div
          class="flex items-center justify-between p-4 md:p-5 border-b rounded-t dark:border-gray-600 border-gray-200">
          <h3 class="text-lg font-semibold text-gray-900 dark:text-white">
            权限管理
          </h3>
          <button @click="closeModal" type="button"
            class="text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm w-8 h-8 ms-auto inline-flex justify-center items-center dark:hover:bg-gray-600 dark:hover:text-white">
            <svg class="w-3 h-3" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 14">
              <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6" />
            </svg>
          </button>
        </div>
        <form @submit.prevent="handleSubmit" class="p-4 md:p-5">
          <div class="grid gap-4 mb-4">
            <div>
              <label for="name" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">权限名称</label>
              <input type="text" v-model="formData.name" name="name" id="name"
                class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
                required>
            </div>
            <div>
              <label for="code" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">权限编码</label>
              <input type="text" v-model="formData.code" name="code" id="code"
                class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
                required>
            </div>
          </div>
          <button type="submit"
            class="text-white inline-flex items-center bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center">
            提交
          </button>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import useAlertStore from "@/composables/store/useAlertStore";
import type { PermissionUpsertModel } from "@/types/permission";
import { computed, ref, watch } from "vue";
import { z } from "zod";
import type { components } from "../api/types/schema";

const { permission, onSubmit, closeModal } = defineProps<{
	permission?: components["schemas"]["PermissionDto"];
	closeModal: () => void;
	onSubmit: (data: PermissionUpsertModel) => void;
}>();

const alertStore = useAlertStore();

const formData = ref({
	id: permission?.id,
	name: permission?.name,
	code: permission?.code,
});

watch(
	() => permission,
	(newVal) => {
		if (newVal) {
			formData.value.id = newVal.id;
			formData.value.name = newVal.name;
			formData.value.code = newVal.code;
		}
	},
	{ immediate: true },
);

const handleSubmit = () => {
	const permissionSchema = z.object({
		name: z.string().min(3, "权限名称至少3个字符"),
		code: z.string().min(3, "权限代码至少3个字符"),
	});

	try {
		const validatedData = permissionSchema.parse(formData.value);
		onSubmit(validatedData);
	} catch (error) {
		if (error instanceof z.ZodError) {
			alertStore.showAlert({
				level: "error",
				content: `请检查您填写的字段：${error.errors[0].path}`,
			});
		}
	}
};
</script>
