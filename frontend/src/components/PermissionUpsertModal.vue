<template>
  <div :id tabindex="-1" aria-hidden="true"
    class="bg-gray-900/50 dark:bg-gray-900/80 hidden overflow-y-auto overflow-x-hidden fixed top-0 right-0 left-0 z-50 justify-center items-center w-full md:inset-0 h-[calc(100%-1rem)] max-h-full">
    <div class="relative p-4 w-full max-w-md max-h-full">
      <div class="relative bg-white rounded-lg shadow-sm dark:bg-gray-700">
        <div
          class="flex items-center justify-between p-4 md:p-5 border-b rounded-t dark:border-gray-600 border-gray-200">
          <h3 class="text-lg font-semibold text-gray-900 dark:text-white">
            权限管理
          </h3>
          <button type="button" @click="closeModal"
            class="text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm w-8 h-8 ms-auto inline-flex justify-center items-center">
            <svg class="w-3 h-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 14">
              <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6" />
            </svg>
            <span class="sr-only">Close modal</span>
          </button>
        </div>
        <div class="p-4 md:p-5">
          <div class="grid gap-4 mb-4 grid-cols-2">
            <div class="col-span-2">
              <label for="name" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">权限名称</label>
              <input type="text" name="权限名称" id="name" v-model="formData.name"
                class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5"
                required="true">
            </div>
            <div class="col-span-2">
              <label for="code" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">权限编码</label>
              <input type="text" id="code" v-model="formData.code"
                class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
                required />
            </div>
          </div>
          <button type="submit" @click.prevent="handleSubmit"
            class="text-white flex items-center bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center self-start mt-5">
            保存
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import useAlertStore from "@/composables/store/useAlertStore";
import type { PermissionUpsertModel } from "@/types/permission";
import { ref, watch } from "vue";
import { z } from "zod";
import type { components } from "../api/types/schema";

const { permission, onSubmit, closeModal } = defineProps<{
  id: string;
	permission?: components["schemas"]["PermissionRespDto"];
	closeModal: () => void;
	onSubmit: (data: PermissionUpsertModel) => Promise<void>;
}>();

const alertStore = useAlertStore();

const formData = ref({
  
});

watch(
	() => permission,
	(newPermission) => {
			formData.value = {
			id: newPermission?.id,
			name: newPermission?.name,
			code: newPermission?.code,
		};
	},
	{ immediate: true },
);

const handleSubmit = async () => {
	const permissionSchema = z.object({
		id: z.number().optional(),
		name: z.string({
      message: "权限名称不能为空",
    }).min(2, "权限名称至少2个字符"),
		code: z.string({
      message: "权限代码不能为空",
    }).min(2, "权限代码至少2个字符"),
	});

	try {
		const validatedData = permissionSchema.parse(formData.value);
		await onSubmit(validatedData);
	} catch (error) {
		if (error instanceof z.ZodError) {
			alertStore.showAlert({
				level: "error",
				content: error.errors[0].message,
			});
		} else {
      throw error;
    }
	}
};
</script>
