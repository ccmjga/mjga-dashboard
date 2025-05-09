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
        class="flex items-center block gap-x-1 text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800 absolute right-5 bottom-2"
        type="button">
        新增角色
      </button>
    </div>

    <table class="w-full text-sm text-left rtl:text-right shadow-lg rounded-lg text-gray-500 dark:text-gray-400">
      <thead class="text-xs uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
        <tr>
          <th scope="col" class="p-4">
            <div class="flex items-center">
              <input id="checkbox-all-search" disabled type="checkbox"
                class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded-sm focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 dark:focus:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600">
              <label for="checkbox-all-search" class="sr-only">checkbox</label>
            </div>
          </th>
          <th scope="col" class="px-6 py-3">角色名称</th>
          <th scope="col" class="px-6 py-3">角色编码</th>
          <th scope="col" class="px-6 py-3">操作</th>
          <th scope="col" class="px-6 py-3">分配</th>
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
            {{ role.name }}
          </td>
          <td class="px-6 py-4">{{ role.code }}</td>
          <td class="px-6 py-4">
            <div class="flex items-center gap-x-2">
              <button @click="handleUpsertRoleClick(role)"
                class="flex items-center block gap-x-1  text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5  text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800 "
                type="button">
                <svg class="w-4 h-4" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                  <path d="M17.414 2.586a2 2 0 00-2.828 0L7 10.172V13h2.828l7.586-7.586a2 2 0 000-2.828z"></path>
                  <path fill-rule="evenodd"
                    d="M2 6a2 2 0 012-2h4a1 1 0 010 2H4v10h10v-4a1 1 0 112 0v4a2 2 0 01-2 2H4a2 2 0 01-2-2V6z"
                    clip-rule="evenodd"></path>
                </svg>
                编辑
              </button>
              <button
                class="flex items-center block gap-x-1
              bg-red-700 hover:bg-red-800 focus:outline-none dark:bg-red-600 dark:hover:bg-red-700
              focus:ring-red-500 block text-white focus:ring-4 focus:outline-none font-medium rounded-lg text-sm px-5 py-2.5 text-center"
                @click="handleDeleteRoleClick(role)" type="button">
                <svg class="w-4 h-4" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                  <path fill-rule="evenodd"
                    d="M9 2a1 1 0 00-.894.553L7.382 4H4a1 1 0 000 2v10a2 2 0 002 2h8a2 2 0 002-2V6a1 1 0 100-2h-3.382l-.724-1.447A1 1 0 0011 2H9zM7 8a1 1 0 012 0v6a1 1 0 11-2 0V8zm5-1a1 1 0 00-1 1v6a1 1 0 102 0V8a1 1 0 00-1-1z"
                    clip-rule="evenodd"></path>
                </svg>
                删除
              </button>
            </div>
          </td>
          <td class="px-6 py-4">
            <div>
              <button
                class="flex itmes-center text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-gray-100 focus:ring-4 focus:ring-gray-100 font-medium rounded-lg text-sm px-4 py-2.5 text-center dark:bg-gray-800 dark:text-white dark:border-gray-600 dark:hover:bg-gray-700 dark:hover:border-gray-600 dark:focus:ring-gray-700"
                @click="handleBindPermissionClick(role)" type="button">
                分配权限
              </button>
            </div>
          </td>
        </tr>
      </tbody>
    </table>

    <TablePagination :pageChange="handlePageChange" :total="total" />
  </div>

  <RoleDeleteModal :id="'role-delete-modal'" :closeModal="() => {
    roleDeleteModal!.hide();
  }" :onSubmit="handleDeletedModalSubmit" title="确定删除该角色吗" content="删除角色"></RoleDeleteModal>
  <RoleUpsertModal :onSubmit="handleUpsertModalSubmit" :closeModal="() => {
    roleUpsertModal!.hide();
  }" :role="selectedRole">
  </RoleUpsertModal>
</template>

<script setup lang="ts">
import RoleDeleteModal from "@/components/PopupModal.vue";
import RoleUpsertModal from "@/components/RoleUpsertModal.vue";
import TablePagination from "@/components/TablePagination.vue";
import useRoleDelete from "@/composables/role/useRoleDelete";
import { useRolesQuery } from "@/composables/role/useRolesQuery";
import { RouteName } from "@/router/constants";
import type { RoleUpsertModel } from "@/types/role";
import { Modal, type ModalInterface, initFlowbite } from "flowbite";
import { nextTick, onMounted, ref } from "vue";
import { useRouter } from "vue-router";
import type { components } from "../api/types/schema";
import { useRoleUpsert } from "../composables/role/useRoleUpsert";
import useAlertStore from "../composables/store/useAlertStore";

const roleName = ref<string>("");
const selectedRole = ref<components["schemas"]["RoleDto"]>();
const roleUpsertModal = ref<ModalInterface>();
const roleDeleteModal = ref<ModalInterface>();

const { total, roles, fetchRolesWith } = useRolesQuery();

const { deleteRole } = useRoleDelete();
const alertStore = useAlertStore();
const router = useRouter();
const upsertRole = useRoleUpsert();
onMounted(async () => {
	await fetchRolesWith({
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

const handleUpsertModalSubmit = async (data: RoleUpsertModel) => {
	await upsertRole.upsertRole(data);
	await fetchRolesWith({
		name: roleName.value,
	});
	roleUpsertModal.value?.hide();
	alertStore.showAlert({
		content: "操作成功",
		level: "success",
	});
};

const handleUpsertRoleClick = async (
	role?: components["schemas"]["RoleDto"],
) => {
	selectedRole.value = role;
	await nextTick(() => {
		roleUpsertModal.value?.show();
	});
};

const handleDeletedModalSubmit = async () => {
	if (!selectedRole?.value?.id) return;
	await deleteRole(selectedRole.value.id);
	await fetchRolesWith({
		name: roleName.value,
	});
	roleDeleteModal.value?.hide();
	alertStore.showAlert({
		content: "删除成功",
		level: "success",
	});
};

const handleDeleteRoleClick = async (
	role: components["schemas"]["RoleDto"],
) => {
	selectedRole.value = role;
	await nextTick(() => {
		roleDeleteModal.value?.show();
	});
};

const handleBindPermissionClick = async (
	role: components["schemas"]["RoleDto"],
) => {
	router.push({
		name: RouteName.BINDPERMISSIONVIEW,
		params: { roleId: role.id },
	});
};
const handleSearch = async () => {
	await fetchRolesWith({
		name: roleName.value,
	});
};

const handlePageChange = async (page: number, pageSize: number) => {
	await fetchRolesWith(
		{
			name: roleName.value,
		},
		page,
		pageSize,
	);
};
</script>

<style scoped></style>
