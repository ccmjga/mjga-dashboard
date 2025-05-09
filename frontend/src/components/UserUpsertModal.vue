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
            {{ mode === 'edit' ? '编辑用户' : '新增用户' }}
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
                placeholder="Type product name" required="true">
            </div>
            <div class="col-span-2">
              <label for="password"
                class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">密码</label>
              <input type="password" id="password" v-model="formData.password"
                class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                placeholder="•••••••••" required />
            </div>
            <div class="col-span-2">
              <label for="confirm_password" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">确认密码</label>
              <input type="password" id="confirm_password" v-model="formData.confirmPassword"
                class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                placeholder="•••••••••" required />
            </div>
            <div class="col-span-2 sm:col-span-1">
              <label for="category" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">状态</label>
              <select id="category" v-model="formData.enable"
                class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-500 focus:border-primary-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500">
                <option :value=true>启用</option>
                <option :value=false >关闭</option>
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
import { useUserUpsert } from "@/composables/user/useUserUpsert";
import type { UserRolePermission } from "@/types/user";
import { initFlowbite } from "flowbite";
import { onMounted, ref, watch } from "vue";
import { z } from "zod";

const userUpsert = useUserUpsert();

const { mode, user, onSubmit } = defineProps<{
	mode: "edit" | "create";
	user?: UserRolePermission;
	closeModal: () => void;
	onSubmit: (event: Event) => void;
}>();

const formData = ref<UserRolePermission & { confirmPassword?: string }>({
	...user,
	confirmPassword: user?.password,
});

watch(
	() => user,
	(newUser) => {
		formData.value = {
			...newUser,
			confirmPassword: user?.password,
		};
	},
);

const handleSubmit = (event: Event) => {
	const userSchema = z
		.object({
			username: z.string().min(1, "用户名至少1个字符"),
			password: z.string().min(1, "密码至少1个字符"),
			confirmPassword: z.string(),
			enable: z.boolean(),
		})
		.refine((data) => data.password === data.confirmPassword, {
			message: "您的密码输入不一致，请重新输入。",
		});

	try {
		const validatedData = userSchema.parse(formData.value);
		userUpsert.upsertUser(validatedData);
		onSubmit(event);
	} catch (error) {
		if (error instanceof z.ZodError) {
			console.error("表单验证错误:", error.errors);
		}
	}
};

onMounted(() => {
	initFlowbite();
});
</script>
