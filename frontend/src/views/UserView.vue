<template>
  <div class="relative overflow-x-auto px-4 pt-6 xl:grid-cols-3 xl:gap-4 sm:rounded-lg">
    <div class="mb-4 col-span-full">
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
                class="ml-1 text-gray-400 hover:text-primary-600 md:ml-2 dark:text-gray-500 dark:hover:text-white">用户管理</span>
            </div>
          </li>
        </ol>
      </nav>
      <h1 class="text-xl font-semibold text-gray-900 sm:text-2xl dark:text-white">用户管理</h1>
    </div>
    <div class="relative">
      <form class="max-w-sm mb-4 ">
        <label for="default-search"
          class="mb-2 text-sm font-medium text-gray-900 sr-only dark:text-white">Search</label>
        <div class="relative">
          <div class="absolute inset-y-0 start-0 flex items-center ps-3 pointer-events-none">
            <svg class="w-4 h-4 text-gray-500 dark:text-gray-400" aria-hidden="true" xmlns="http://www.w3.org/2000/svg"
              fill="none" viewBox="0 0 20 20">
              <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                d="m19 19-4-4m0-7A7 7 0 1 1 1 8a7 7 0 0 1 14 0Z" />
            </svg>
          </div>
          <input type="search" id="default-search" v-model="username"
            class="block w-full p-4 ps-10 text-sm text-gray-900 border border-gray-300 rounded-lg bg-gray-50 focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
            placeholder="用户名" required />
          <button type="submit"
            class="text-white absolute end-2.5 bottom-2.5 bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-4 py-2 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
            @click.prevent="handleSearch">搜索</button>
        </div>
      </form>
      <!-- Create Modal toggle -->
      <button @click="handleUpsertUserClick(undefined)"
        class="flex items-center block text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800 absolute right-5 bottom-2"
        type="button">
        新增用户
      </button>
    </div>

    <table class="w-full text-sm text-left rtl:text-right shadow-lg rounded-lg text-gray-500 dark:text-gray-400">
      <thead class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
        <tr>
          <th scope="col" class="p-4">
            <div class="flex items-center">
              <input id="checkbox-all-search" disabled type="checkbox"
                class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded-sm focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 dark:focus:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600">
              <label for="checkbox-all-search" class="sr-only">checkbox</label>
            </div>
          </th>
          <th scope="col" class="px-6 py-3">用户名</th>
          <th scope="col" class="px-6 py-3">邮箱</th>
          <th scope="col" class="px-6 py-3">创建时间</th>
          <th scope="col" class="px-6 py-3">状态</th>
          <th scope="col" class="px-6 py-3">操作</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="user in users" :key="user.id"
          class="bg-white border-b dark:bg-gray-800 dark:border-gray-700 border-gray-200 hover:bg-gray-50 dark:hover:bg-gray-600">
          <td class="w-4 p-4">
            <div class="flex items-center">
              <input :id="'checkbox-table-search-' + user.id" type="checkbox" disabled
                class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded-sm focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 dark:focus:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600">
              <label :for="'checkbox-table-search-' + user.id" class="sr-only">checkbox</label>
            </div>
          </td>
          <td scope="row" class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white">
            {{ user.username }}
          </td>
          <td class="px-6 py-4">
            {{ user.username }}
          </td>
          <td class="px-6 py-4">
            {{ user.createTime }}
          </td>
          <td class="px-6 py-4">
            <div class="flex items-center">
              <div class="h-2.5 w-2.5 rounded-full me-2" :class="user.enable ? 'bg-green-500' : 'bg-red-500'"></div> {{
              user.enable === true ? "启用" : "关闭" }}
            </div>

          </td>
          <td class="px-6 py-4 flex items-center gap-x-2">
            <!-- Edit Modal toggle -->
            <button @click="handleUpsertUserClick(user)"
              class="flex items-center block gap-x-1 text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
              type="button">
              <svg class="w-5 h-5" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" width="24" height="24"
                fill="none" viewBox="0 0 24 24">
                <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                  d="m14.304 4.844 2.852 2.852M7 7H4a1 1 0 0 0-1 1v10a1 1 0 0 0 1 1h11a1 1 0 0 0 1-1v-4.5m2.409-9.91a2.017 2.017 0 0 1 0 2.853l-6.844 6.844L8 14l.713-3.565 6.844-6.844a2.015 2.015 0 0 1 2.852 0Z" />
              </svg>
              编辑
            </button>
            <button
              class="flex items-center block gap-x-1
              bg-yellow-600 hover:bg-yellow-700 focus:outline-none dark:bg-yellow-600 dark:hover:bg-yellow-700
              focus:ring-yellow-500 block text-white focus:ring-4 focus:outline-none font-medium rounded-lg text-sm px-5 py-2.5 text-center"
              @click="handleBindRoleClick(user)" type="button">
              <svg class="w-5 h-5 text-white" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" width="24"
                height="24" fill="none" viewBox="0 0 24 24">
                <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                  d="M16 12h4m-2 2v-4M4 18v-1a3 3 0 0 1 3-3h4a3 3 0 0 1 3 3v1a1 1 0 0 1-1 1H5a1 1 0 0 1-1-1Zm8-10a3 3 0 1 1-6 0 3 3 0 0 1 6 0Z" />
              </svg>
              绑定角色
            </button>
            <button
              class="flex items-center block gap-x-1
              bg-green-600 hover:bg-green-700 focus:outline-none dark:bg-green-600 dark:hover:bg-green-700
              focus:ring-green-500 block text-white focus:ring-4 focus:outline-none font-medium rounded-lg text-sm px-5 py-2.5 text-center"
              @click="handleBindDepartmentClick(user)" type="button">
              <svg class="w-5 h-5 text-white" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none"
                viewBox="0 0 24 24">
                <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                  d="M14.7141 15h4.268c.4043 0 .732-.3838.732-.8571V3.85714c0-.47338-.3277-.85714-.732-.85714H6.71411c-.55228 0-1 .44772-1 1v4m10.99999 7v-3h3v3h-3Zm-3 6H6.71411c-.55228 0-1-.4477-1-1 0-1.6569 1.34315-3 3-3h2.99999c1.6569 0 3 1.3431 3 3 0 .5523-.4477 1-1 1Zm-1-9.5c0 1.3807-1.1193 2.5-2.5 2.5s-2.49999-1.1193-2.49999-2.5S8.8334 9 10.2141 9s2.5 1.1193 2.5 2.5Z" />
              </svg>
              分配部门
            </button>
            <button
              class="flex items-center block gap-x-1
              bg-red-700 hover:bg-red-800 focus:outline-none focus:ring-red-300 dark:bg-red-600 dark:hover:bg-red-700
              dark:focus:ring-red-900 block text-white focus:ring-4 focus:outline-nonefont-medium rounded-lg text-sm px-5 py-2.5 text-center"
              @click="handleDeleteUserClick(user)" type="button">
              <svg class="w-5 h-5 text-white" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" width="24"
                height="24" fill="none" viewBox="0 0 24 24">
                <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                  d="M5 12h14" />
              </svg>
              删除
            </button>
          </td>
        </tr>
      </tbody>
    </table>
    <nav class="flex items-center flex-column flex-wrap md:flex-row justify-between pt-4 px-5 pb-5"
      aria-label="Table navigation">
      <span class="text-sm font-normal text-gray-500 dark:text-gray-400 mb-4 md:mb-0 block w-full md:inline md:w-auto">
        显示
        <span class="font-semibold text-gray-900 dark:text-white">
          {{ displayRange.start }}-{{ displayRange.end }}
        </span>
        共
        <span class="font-semibold text-gray-900 dark:text-white">{{ total }}</span> 条
      </span>

      <ul class="inline-flex -space-x-px rtl:space-x-reverse text-sm h-8">
        <li>
          <a href="#" @click.prevent="handlePageChange(currentPage - 1)" :class="[
              'flex items-center justify-center px-3 h-8 ms-0 leading-tight text-gray-500 bg-white border border-gray-300 rounded-s-lg hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white',
              { 'opacity-50 cursor-not-allowed': isFirstPage }
            ]">上一页</a>
        </li>

        <li v-for="page in pageNumbers" :key="page">
          <a href="#" @click.prevent="handlePageChange(page)" :class="[
              'flex items-center justify-center px-3 h-8 leading-tight border border-gray-300 hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:hover:bg-gray-700 dark:hover:text-white',
              currentPage === page 
                ? 'text-blue-600 bg-blue-50 hover:bg-blue-100 hover:text-blue-700 dark:border-gray-700 dark:bg-gray-700 dark:text-white'
                : 'text-gray-500 bg-white dark:text-gray-400'
            ]">{{ page }}</a>
        </li>

        <li>
          <a href="#" @click.prevent="handlePageChange(currentPage + 1)" :class="[
              'flex items-center justify-center px-3 h-8 leading-tight text-gray-500 bg-white border border-gray-300 rounded-e-lg hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white',
              { 'opacity-50 cursor-not-allowed': isLastPage }
            ]">下一页</a>
        </li>
      </ul>
    </nav>
  </div>

  <UserDeleteModal :id="'user-delete-modal'" :closeModal="() => {
    userDeleteModal!.hide();
  }" :onSubmit="handleDeleteUserSubmit" title="确定删除该用户吗" content="删除用户"></UserDeleteModal>
  <UserUpsertModal :id="'user-upsert-modal'" :onSubmit="handleUpsertUserSubmit" :closeModal="() => {
    userUpsertModal!.hide();
  }" :user="selectedUser">
  </UserUpsertModal>
</template>

<script setup lang="ts">
import UserDeleteModal from "@/components/PopupModal.vue";
import UserUpsertModal from "@/components/UserUpsertModal.vue";
import useUserDelete from "@/composables/user/useUserDelete";
import { useUserQuery } from "@/composables/user/useUserQuery";
import { RouteName } from "@/router/constants";
import type { UserUpsertSubmitModel } from "@/types/user";
import { Modal, type ModalInterface, initFlowbite } from "flowbite";
import { nextTick, onMounted, ref } from "vue";
import { useRouter } from "vue-router";
import type { components } from "../api/types/schema";
import useAlertStore from "../composables/store/useAlertStore";
import { useUserUpsert } from "../composables/user/useUserUpsert";

const username = ref<string>("");
const selectedUser = ref<components["schemas"]["UserRolePermissionDto"]>();
const userUpsertModal = ref<ModalInterface>();
const userDeleteModal = ref<ModalInterface>();
const router = useRouter();

const {
	pagination: {
		pageSize,
		currentPage,
		totalPages,
		pageNumbers,
		displayRange,
		isFirstPage,
		isLastPage,
	},
	total,
	users,
	fetchUsersWith,
} = useUserQuery();

const { deleteUser } = useUserDelete();
const userUpsert = useUserUpsert();

const alertStore = useAlertStore();

onMounted(async () => {
	await fetchUsersWith(currentPage.value, pageSize.value, {
		username: username.value,
	});
	initFlowbite();
	const $upsertModalElement: HTMLElement | null =
		document.querySelector("#user-upsert-modal");
	const $deleteModalElement: HTMLElement | null =
		document.querySelector("#user-delete-modal");
	userUpsertModal.value = new Modal(
		$upsertModalElement,
		{},
		{
			id: "user-upsert-modal",
		},
	);
	userDeleteModal.value = new Modal(
		$deleteModalElement,
		{},
		{
			id: "user-delete-modal",
		},
	);
});

const handleUpsertUserSubmit = async (data: UserUpsertSubmitModel) => {
	await userUpsert.upsertUser(data);
	await fetchUsersWith(currentPage.value, pageSize.value, {
		username: username.value,
	});
	userUpsertModal.value?.hide();
	alertStore.showAlert({
		content: "操作成功",
		level: "success",
	});
};

const handleUpsertUserClick = async (
	user?: components["schemas"]["UserRolePermissionDto"],
) => {
	selectedUser.value = user;
	await nextTick(() => {
		userUpsertModal.value?.show();
	});
};

const handleBindRoleClick = async (
	user: components["schemas"]["UserRolePermissionDto"],
) => {
	router.push({
		name: RouteName.BINDROLEVIEW,
		params: {
			userId: user.id,
		},
	});
};

const handleBindDepartmentClick = async (
	user: components["schemas"]["UserRolePermissionDto"],
) => {
	router.push({
		name: RouteName.BINDDEPARTMENTVIEW,
		params: {
			userId: user.id,
		},
	});
};

const handleDeleteUserSubmit = async () => {
	if (!selectedUser?.value?.id) return;
	await deleteUser(selectedUser.value.id);
	await fetchUsersWith(currentPage.value, pageSize.value, {
		username: username.value,
	});
	userDeleteModal.value?.hide();
	alertStore.showAlert({
		content: "删除成功",
		level: "success",
	});
};

const handleDeleteUserClick = async (
	user: components["schemas"]["UserRolePermissionDto"],
) => {
	selectedUser.value = user;
	await nextTick(() => {
		userDeleteModal.value?.show();
	});
};

const handleSearch = async () => {
	await fetchUsersWith(currentPage.value, pageSize.value, {
		username: username.value,
	});
};

const handlePageChange = async (page: number) => {
	if (page < 1 || page > totalPages.value) return;
	await fetchUsersWith(page, pageSize.value, { username: username.value });
};
</script>

<style scoped></style>
