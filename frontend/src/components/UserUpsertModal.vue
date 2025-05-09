<template>

  <!-- Main modal -->
  <div id="user-upsert-modal" tabindex="-1" aria-hidden="true"
    class="bg-gray-900/50 dark:bg-gray-900/80 hidden overflow-y-auto overflow-x-hidden fixed top-0 right-0 left-0 z-50 justify-center items-center w-full md:inset-0 h-[calc(100%-1rem)] max-h-full">
    <div class="relative p-4 w-full max-w-md max-h-full">
      <!-- Modal content -->
      <div class="relative bg-white rounded-lg shadow-sm dark:bg-gray-700">
        <!-- Modal header -->
        <div
          class="flex items-center justify-between p-4 md:p-5 border-b rounded-t dark:border-gray-600 border-gray-200">
          <h3 class="text-lg font-semibold text-gray-900 dark:text-white">
            用户管理
          </h3>
          <button type="button" @click="closeModal"
            class="text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm w-8 h-8 ms-auto inline-flex justify-center items-center dark:hover:bg-gray-600 dark:hover:text-white">
            <svg class="w-3 h-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 14">
              <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6" />
            </svg>
            <span class="sr-only">Close modal</span>
          </button>
        </div>
        <!-- Modal body -->
        <div class="p-4 md:p-5">
          <div class="grid gap-4 mb-4 grid-cols-2">
            <div class="col-span-2">
              <label for="name" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">用户名</label>
              <input type="text" name="用户名" id="name" v-model="formData.username"
                class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500"
                required="true">
            </div>
            <div class="col-span-2">
              <label for="password" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">密码</label>
              <input type="password" id="password" v-model="formData.password"
                class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                required />
            </div>
            <div class="col-span-2">
              <label for="confirm_password"
                class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">确认密码</label>
              <input type="password" id="confirm_password" v-model="formData.confirmPassword"
                class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                required />
            </div>
            <div class="col-span-2 sm:col-span-1">
              <label for="category" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">状态</label>
              <select id="category" v-model="formData.enable"
                class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-500 focus:border-primary-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500">
                <option :value=true>启用</option>
                <option :value=false>禁用</option>
              </select>
            </div>
          </div>
          <button type="submit" @click="handleSubmit"
            class="text-white flex items-center bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800 self-start mt-5">
            保存
          </button>
        </div>
      </div>
    </div>
  </div>

</template>
<script setup lang="ts">
import useAlertStore from "@/composables/store/useAlertStore";
import type { UserUpsertSubmitModel } from "@/types/user";
import { initFlowbite } from "flowbite";
import { onMounted, ref, watch } from "vue";
import { z } from "zod";
import type { components } from "../api/types/schema";

const alertStore = useAlertStore();

const { user, onSubmit } = defineProps<{
	user?: components["schemas"]["UserRolePermissionDto"];
	closeModal: () => void;
	onSubmit: (data: UserUpsertSubmitModel) => Promise<void>;
}>();

const formData = ref();

watch(
	() => user,
	(newUser) => {
		formData.value = {
			id: newUser?.id,
			username: newUser?.username,
			password: undefined,
			enable: newUser?.enable,
			confirmPassword: undefined,
		};
	},
	{
		immediate: true,
	},
);

const handleSubmit = async () => {
	const userSchema = z
		.object({
			id: z.number().optional(),
			username: z.string({
        message: "用户名不能为空",
      }).min(4, "用户名至少4个字符"),
			enable: z.boolean(),
			password: z.string({
        message: "密码不能为空",
      }).min(5, "密码至少5个字符").optional(),
			confirmPassword: z.string({
        message: "密码不能为空",
      }).min(5, "密码至少5个字符").optional(),
		})
		.refine(
			(data) => {
				if (data.password) return true;
				return data.password === data.confirmPassword;
			},
			{
				message: "您的密码输入不一致，请重新输入。",
			},
		);

	try {
		const validatedData = userSchema.parse(formData.value);
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

onMounted(() => {
	initFlowbite();
});
</script>
