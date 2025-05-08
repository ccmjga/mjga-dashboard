<template>
  <div class="grid grid-cols-1 px-4 pt-6 xl:grid-cols-3 xl:gap-4 dark:bg-gray-900 ">
    <div class="mb-4 col-span-full xl:mb-2">
      <nav class="flex mb-5" aria-label="Breadcrumb">
        <ol class="inline-flex items-center space-x-1 text-sm font-medium md:space-x-2">
          <li class="inline-flex items-center">
            <RouterLink :to="{name: RouteName.USERVIEW}"
              class="inline-flex items-center text-gray-700 hover:text-primary-600 dark:text-gray-300 dark:hover:text-white">
              <svg class="w-5 h-5 mr-2.5" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                <path
                  d="M10.707 2.293a1 1 0 00-1.414 0l-7 7a1 1 0 001.414 1.414L4 10.414V17a1 1 0 001 1h2a1 1 0 001-1v-2a1 1 0 011-1h2a1 1 0 011 1v2a1 1 0 001 1h2a1 1 0 001-1v-6.586l.293.293a1 1 0 001.414-1.414l-7-7z">
                </path>
              </svg>
              首页
            </RouterLink>
          </li>
          <li>
            <div class="flex items-center">
              <svg class="w-6 h-6 text-gray-400" fill="currentColor" viewBox="0 0 20 20"
                xmlns="http://www.w3.org/2000/svg">
                <path fill-rule="evenodd"
                  d="M7.293 14.707a1 1 0 010-1.414L10.586 10 7.293 6.707a1 1 0 011.414-1.414l4 4a1 1 0 010 1.414l-4 4a1 1 0 01-1.414 0z"
                  clip-rule="evenodd"></path>
              </svg>
              <span
                class="ml-1 text-gray-400 hover:text-primary-600 md:ml-2 dark:text-gray-500 dark:hover:text-white">用户设置</span>
            </div>
          </li>
        </ol>
      </nav>
      <h1 class="text-xl font-semibold text-gray-900 sm:text-2xl dark:text-white">用户设置</h1>
    </div>
    <!-- Right Content -->
    <div class="col-span-full xl:col-auto">
      <div
        class="p-4 mb-4 bg-white border border-gray-200 rounded-lg shadow-sm 2xl:col-span-2 dark:border-gray-700 sm:p-6 dark:bg-gray-800">
        <div class="items-center sm:flex xl:block 2xl:flex sm:space-x-4 xl:space-x-0 2xl:space-x-4">
          <img class="mb-4 rounded-lg w-28 h-28 sm:mb-0 xl:mb-4 2xl:mb-0" src="/trump.jpg" alt="Jese picture">
          <div>
            <h3 class="mb-1 text-xl font-bold text-gray-900 dark:text-white">个人资料</h3>
            <div class="mb-4 text-sm text-gray-500 dark:text-gray-400">
              JPG, GIF or PNG. Max size of 800K
            </div>
            <div class="flex items-center space-x-4">
              <button type="button" disabled
                class="cursor-not-allowed inline-flex items-center px-3 py-2 text-sm font-medium text-center text-white rounded-lg bg-blue-400 dark:bg-blue-500  ">
                <svg class="w-4 h-4 mr-2 -ml-1" fill="currentColor" viewBox="0 0 20 20"
                  xmlns="http://www.w3.org/2000/svg">
                  <path
                    d="M5.5 13a3.5 3.5 0 01-.369-6.98 4 4 0 117.753-1.977A4.5 4.5 0 1113.5 13H11V9.413l1.293 1.293a1 1 0 001.414-1.414l-3-3a1 1 0 00-1.414 0l-3 3a1 1 0 001.414 1.414L9 9.414V13H5.5z">
                  </path>
                  <path d="M9 13h2v5a1 1 0 11-2 0v-5z"></path>
                </svg>
                Upload picture
              </button>
            </div>
          </div>
        </div>
      </div>


    </div>
    <div class="col-span-1 row-start-3">
      <div
        class="p-4 mb-4 bg-white border border-gray-200 rounded-lg shadow-sm 2xl:col-span-2 dark:border-gray-700 sm:p-6 dark:bg-gray-800">
        <h3 class="mb-4 text-xl font-semibold dark:text-white">个人信息</h3>
        <form action="#">
          <div class="grid grid-cols-6 gap-6">
            <div class="col-span-6 ">
              <label for="current-username"
                class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">用户名</label>
              <input type="text" name="current-username" id="current-username" v-model="userForm.username"
                class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-500 focus:border-primary-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500"
                required>
            </div>
            <div class="col-span-6 ">
              <label for="current-password"
                class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">密码</label>
              <input type="password" name="current-password" id="current-password" v-model="userForm.password"
                class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-500 focus:border-primary-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500"
                placeholder="••••••••" required>
            </div>
            <div class="col-span-6 ">
              <label for="password" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">确认密码</label>
              <input type="password" id="password" v-model="userForm.confirmPassword"
                class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                placeholder="••••••••" required>
            </div>
            <div class="col-span-6 ">
              <label for="category" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">状态</label>
              <select id="category" v-model="userForm.enable"
                class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-500 focus:border-primary-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500">
                <option :value=true>启用</option>
                <option :value=false>禁用</option>
              </select>
            </div>
            <div class="col-span-6 sm:col-full">
              <button
                class="text-white bg-primary-700 hover:bg-primary-800 focus:ring-4 focus:ring-primary-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-primary-600 dark:hover:bg-primary-700 dark:focus:ring-primary-800"
                @click.prevent="handleUpdateClick" type="submit">保存</button>
            </div>
          </div>
        </form>
      </div>
    </div>

  </div>
</template>

<script setup lang="ts">
import useUserAuth from "@/composables/auth/useUserAuth";
import useUserStore from "@/composables/store/useUserStore";
import { initFlowbite } from "flowbite";
import { onMounted, ref } from "vue";
import { z } from "zod";
import useAlertStore from "../composables/store/useAlertStore";
import { RouteName } from "../router/constants";

const { user } = useUserStore();
const { upsertCurrentUser } = useUserAuth();
const alertStore = useAlertStore();

const userForm = ref({
	username: user.username,
	password: user.password,
	enable: user.enable,
	confirmPassword: user.password,
});

onMounted(() => {
	initFlowbite();
});

const handleUpdateClick = async () => {
	let validatedData = undefined;
	try {
		validatedData = z
			.object({
				username: z.string().min(4, "用户名长度不能小于4个字符"),
				password: z.string().min(5, "密码长度不能小于5个字符"),
				confirmPassword: z.string(),
				enable: z.boolean(),
			})
			.refine((data) => data.password === data.confirmPassword, {
				message: "密码输入不一致。",
			})
			.parse(userForm.value);
		await upsertCurrentUser(validatedData);
		alertStore.showAlert({
			content: "操作成功",
			level: "success",
		});
	} catch (error) {
		if (error instanceof z.ZodError) {
			alertStore.showAlert({
				level: "error",
				content: error.errors[0].message,
			});
		}
	}
};
</script>
