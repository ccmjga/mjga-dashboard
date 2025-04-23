<template>
  <div class="flex flex-col items-center justify-center h-screen bg-gray-100 dark:bg-gray-900">
    <div
      class="w-full max-w-sm p-4 bg-white border border-gray-200 rounded-lg shadow-sm sm:p-6 md:p-8 dark:bg-gray-800 dark:border-gray-700">
      <form class="flex flex-col gap-y-4" action="#">
        <h5 class="text-xl font-medium text-gray-900 dark:text-white">知路管理后台</h5>
        <div>
          <label for="username" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">用户名</label>
          <input type="text" name="email" id="username" v-model="username"
            class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white"
            placeholder="输入任意值" required />
        </div>
        <div>
          <label for="password" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">密码</label>
          <input type="password" name="password" id="password" v-model="password" placeholder="••••••••"
            class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white"
            required />
        </div>
        <button type="submit"
          class="w-full text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
          @click.prevent="handleLogin">登录</button>
      </form>
    </div>
  </div>
</template>

<script setup lang="ts">
import { initFlowbite } from "flowbite";
import { onMounted, ref } from "vue";
import { useRoute, useRouter } from "vue-router";
import { z } from "zod";
import useUserAuth from "../composables/auth/useUserAuth";
import useAlertStore from "../composables/store/useAlertStore";
import { RoutePath } from "../router/constants";

const username = ref("admin");
const password = ref("admin");
const router = useRouter();
const route = useRoute();
const userAuth = useUserAuth();
const alertStore = useAlertStore();

const handleLogin = async () => {
	const userSchema = z.object({
		username: z.string().min(1, "用户名至少1个字符"),
		password: z.string().min(1, "密码至少1个字符"),
	});

	try {
		const validatedData = userSchema.parse({
			username: username.value,
			password: password.value,
		});
		await userAuth.signIn(validatedData.username, validatedData.password);
		alertStore.showAlert({
			level: "success",
			content: "登录成功",
		});
		const redirectPath =
			(route.query.redirect as string) ||
			`${RoutePath.DASHBOARD}/${RoutePath.OVERVIEW}`;
		router.push(redirectPath);
	} catch (e) {
		alertStore.showAlert({
			level: "error",
			content: e instanceof z.ZodError ? e.errors[0].message : "账号或密码错误",
		});
	}
};

onMounted(() => {
	initFlowbite();
});
</script>

<style scoped>
</style>
