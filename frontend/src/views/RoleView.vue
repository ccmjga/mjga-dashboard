<template>
  <div class="relative overflow-x-auto px-4 pt-6 xl:grid-cols-3 xl:gap-4 sm:rounded-lg">
    <div class="mb-4 col-span-full">
      <nav class="flex mb-5" aria-label="Breadcrumb">
        <ol class="inline-flex items-center space-x-1 text-sm font-medium md:space-x-2">
          <li class="inline-flex items-center">
            <RouterLink :to="`${RoutePath.DASHBOARD}`"
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
                class="ml-1 text-gray-400 hover:text-primary-600 md:ml-2 dark:text-gray-500 dark:hover:text-white">角色管理</span>
            </div>
          </li>
        </ol>
      </nav>
      <h1 class="text-xl font-semibold text-gray-900 sm:text-2xl dark:text-white">角色管理</h1>
    </div>
    <div class="relative">
      <form class="max-w-sm mb-4">
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
          <input type="search" id="default-search" v-model="roleName"
            class="block w-full p-4 ps-10 text-sm text-gray-900 border border-gray-300 rounded-lg bg-gray-50 focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
            placeholder="角色名" required />
          <button type="submit"
            class="text-white absolute end-2.5 bottom-2.5 bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-4 py-2 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
            @click.prevent="handleSearch">搜索</button>
        </div>
      </form>
      <!-- Create Modal toggle -->
      <button @click="handleUpsertRoleClick(undefined)"
        class="flex items-center block text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800 absolute right-5 bottom-2"
        type="button">
        <svg class="me-1 -ms-1 w-5 h-5" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
          <path fill-rule="evenodd"
            d="M10 5a1 1 0 011 1v3h3a1 1 0 110 2h-3v3a1 1 0 11-2 0v-3H6a1 1 0 110-2h3V6a1 1 0 011-1z"
            clip-rule="evenodd">
          </path>
        </svg>
        新增角色
      </button>
    </div>

    <table class="w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400">
      <thead class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
        <tr>
          <th scope="col" class="p-4">
            <div class="flex items-center">
              <input id="checkbox-all-search" disabled type="checkbox"
                class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded-sm focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 dark:focus:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600">
              <label for="checkbox-all-search" class="sr-only">checkbox</label>
            </div>
          </th>
          <th scope="col" class="px-6 py-3">角色编码</th>
          <th scope="col" class="px-6 py-3">角色名称</th>
          <th scope="col" class="px-6 py-3">操作</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="role in roles" :key="role.id"
          class="bg-white border-b dark:bg-gray-800 dark:border-gray-700 border-gray-200 hover:bg-gray-50 dark:hover:bg-gray-600">
          <td class="w-4 p-4">
            <div class="flex items-center">
              <input :id="'checkbox-table-search-' + role.id" type="checkbox" disabled
                class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded-sm focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 dark:focus:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600">
              <label :for="'checkbox-table-search-' + role.id" class="sr-only">checkbox</label>
            </div>
          </td>
          <td scope="row" class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white">
            {{ role.code }}
          </td>
          <td scope="row" class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white">
            {{ role.name }}
          </td>
          <td class="px-6 py-4 flex items-center">
            <button @click="handleUpsertRoleClick(role)"
              class="flex items-center block text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800 me-2"
              type="button">
              <svg class="me-1 -ms-1 w-5 h-5" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" width="24"
                height="24" fill="none" viewBox="0 0 24 24">
                <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                  d="m14.304 4.844 2.852 2.852M7 7H4a1 1 0 0 0-1 1v10a1 1 0 0 0 1 1h11a1 1 0 0 0 1-1v-4.5m2.409-9.91a2.017 2.017 0 0 1 0 2.853l-6.844 6.844L8 14l.713-3.565 6.844-6.844a2.015 2.015 0 0 1 2.852 0Z" />
              </svg>
              编辑
            </button>
            <button
              class="bg-red-700 hover:bg-red-800 focus:ring-red-300 dark:bg-red-600 dark:hover:bg-red-700 dark:focus:ring-red-900 block text-white focus:ring-4 focus:outline-nonefont-medium rounded-lg text-sm px-5 py-2.5 text-center"
              @click="handleDeleteRoleClick(role)" type="button">
              删除角色
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

  <RoleDeleteModal :id="'role-delete-modal'" :closeModal="() => {
    roleDeleteModal!.hide();
  }" :onSubmit="deleteSelectedRole" title="确定删除该角色吗" content="删除角色"></RoleDeleteModal>
  <RoleUpsertModal :onSubmit="handleUpsertModalSubmit" :closeModal="() => {
    roleUpsertModal!.hide();
  }" mode="edit" :role="selectedRole">
  </RoleUpsertModal>
</template>

<script setup lang="ts">
import RoleDeleteModal from "@/components/PopupModal.vue";
import RoleUpsertModal from "@/components/RoleUpsertModal.vue";
import useRoleDelete from "@/composables/role/useRoleDelete";
import { useRolesPaginationQuery } from "@/composables/role/useRolesQuery";
import { RoutePath } from "@/router/constants";
import type { Role } from "@/types/role";
import { Modal, type ModalInterface, initFlowbite } from "flowbite";
import { nextTick, onMounted, ref } from "vue";

const roleName = ref<string>("");
const selectedRole = ref<Role>();
const roleUpsertModal = ref<ModalInterface>();
const roleDeleteModal = ref<ModalInterface>();

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
	roles,
	fetchRolesWith,
} = useRolesPaginationQuery(1, 10);

const { deleteRole } = useRoleDelete();

onMounted(async () => {
	await fetchRolesWith(currentPage.value, pageSize.value, {
		name: roleName.value,
	});
	initFlowbite();
	const $upsertModalElement: HTMLElement | null =
		document.querySelector("#role-upsert-modal");
	const $deleteModalElement: HTMLElement | null =
		document.querySelector("#role-delete-modal");
	roleUpsertModal.value = new Modal(
		$upsertModalElement,
		{},
		{ id: "role-upsert-modal" },
	);
	roleDeleteModal.value = new Modal(
		$deleteModalElement,
		{},
		{ id: "role-delete-modal" },
	);
});

const handleUpsertModalSubmit = async () => {
	roleUpsertModal.value?.hide();
	await fetchRolesWith(currentPage.value, pageSize.value, {
		name: roleName.value,
	});
};

const handleUpsertRoleClick = async (role?: Role) => {
	selectedRole.value = role;
	await nextTick(() => {
		roleUpsertModal.value?.show();
	});
};

const deleteSelectedRole = async (event: Event) => {
	if (!selectedRole?.value?.id) return;
	await deleteRole(selectedRole.value.id);
	await fetchRolesWith(currentPage.value, pageSize.value, {
		name: roleName.value,
	});
	roleDeleteModal.value?.hide();
};

const handleDeleteRoleClick = async (role: Role) => {
	selectedRole.value = role;
	await nextTick(() => {
		roleDeleteModal.value?.show();
	});
};

const handleSearch = async () => {
	await fetchRolesWith(currentPage.value, pageSize.value, {
		name: roleName.value,
	});
};

const handlePageChange = async (page: number) => {
	if (page < 1 || page > totalPages.value) return;
	await fetchRolesWith(page, pageSize.value, { name: roleName.value });
};
</script>

<style scoped></style>
