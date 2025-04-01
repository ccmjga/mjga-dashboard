<script setup lang="ts">
import { RoutePath } from "../router/constants";
</script>

<template>
  <main class="grid min-h-full place-items-center bg-white px-6 py-24 sm:py-32 lg:px-8">
    <div class="text-center">
      <p class="text-base font-semibold text-blue-700">404</p>
      <h1 class="mt-4 text-5xl font-semibold tracking-tight text-balance text-gray-900 sm:text-7xl">Page not found</h1>
      <p class="mt-6 text-lg font-medium text-pretty text-gray-500 sm:text-xl/8">您访问的资源未找到，请点击浏览器后退按钮返回</p>
      <div class="mt-10 flex items-center justify-center gap-x-6">
        <RouterLink :to="`${RoutePath.DASHBOARD}/${RoutePath.OVERVIEW}`" class="rounded-md  px-3.5 py-2.5 text-sm font-semibold bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800 text-white shadow-xs focus-visible:outline-2 focus-visible:outline-offset-2">回到主页</RouterLink>
        <a href="#" class="text-sm font-semibold text-gray-900">联系我们<span aria-hidden="true">&rarr;</span></a>
      </div>
    </div>
  </main>
</template>
