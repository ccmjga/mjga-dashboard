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
      <h1 class="text-xl font-semibold text-gray-900 sm:text-2xl dark:text-white">部门管理</h1>
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
          <input type="search" id="default-search" v-model="name"
            class="block w-full p-4 ps-10 text-sm text-gray-900 border border-gray-300 rounded-lg bg-gray-50 focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
            placeholder="部门名称" required />
          <button type="submit"
            class="text-white absolute end-2.5 bottom-2.5 bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-4 py-2 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
            @click.prevent="handleSearch">搜索</button>
        </div>
      </form>
      <!-- Create Modal toggle -->
      <button @click="handleUpsertDepartmentClick()"
        class="flex items-center block text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800 absolute right-5 bottom-2"
        type="button">
        新增部门
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
          <th scope="col" class="px-6 py-3">部门编号</th>
          <th scope="col" class="px-6 py-3">上级部门编号</th>
          <th scope="col" class="px-6 py-3">部门名称</th>
          <th scope="col" class="px-6 py-3">状态</th>
          <th scope="col" class="px-6 py-3">操作</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="department in departments" :key="department.id"
          class="bg-white border-b dark:bg-gray-800 dark:border-gray-700 border-gray-200 hover:bg-gray-50 dark:hover:bg-gray-600">
          <td class="w-4 p-4">
            <div class="flex items-center">
              <input :id="'checkbox-table-search-' + department.id" type="checkbox" disabled
                class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded-sm focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 dark:focus:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600">
              <label :for="'checkbox-table-search-' + department.id" class="sr-only">checkbox</label>
            </div>
          </td>
          <td scope="row" class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white">
            {{ department.id }}
          </td>
          <td class="px-6 py-4">
            {{ department.parentId }}
          </td>
          <td class="px-6 py-4">
            {{ department.name }}
          </td>
          <td class="px-6 py-4">
            <div class="flex items-center">
              <div class="h-2.5 w-2.5 rounded-full me-2" :class="department.enable ? 'bg-green-500' : 'bg-red-500'">
              </div> {{
              department.enable === true ? "启用" : "关闭" }}
            </div>

          </td>
          <td class="px-6 py-4 flex items-center gap-x-2">
            <!-- Edit Modal toggle -->
            <button @click="handleUpsertDepartmentClick(department as components['schemas']['DepartmentUpsertDto'])"
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
              bg-red-700 hover:bg-red-800 focus:outline-none focus:ring-red-300 dark:bg-red-600 dark:hover:bg-red-700
              dark:focus:ring-red-900 block text-white focus:ring-4 focus:outline-nonefont-medium rounded-lg text-sm px-5 py-2.5 text-center"
              @click="handleDeleteDepartmentClick(department as components['schemas']['DepartmentUpsertDto'])"
              type="button">
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
    <TablePagination :total="total" :pageChange="handlePageChange" />
  </div>

  <DepartmentDeleteModal :id="'department-delete-modal'" :closeModal="() => {
    departmentDeleteModal!.hide();
  }" :onSubmit="handleDeleteDepartmentSubmit" title="确定删除该部门吗" content="删除部门"></DepartmentDeleteModal>
  <DepartmentUpsertModal :id="'department-upsert-modal'" :onSubmit="handleUpsertDepartmentSubmit" :closeModal="() => {
    departmentUpsertModal!.hide();
  }" :department="selectedDepartment" :allDepartments="allDepartments">
  </DepartmentUpsertModal>
</template>

<script setup lang="ts">
import DepartmentDeleteModal from "@/components/PopupModal.vue";
import DepartmentUpsertModal from "@/components/UpsertDepartmentModal.vue";
import { RouteName } from "@/router/constants";
import { Modal, type ModalInterface, initFlowbite } from "flowbite";
import { nextTick, onMounted, ref } from "vue";
import type { components } from "../api/types/schema";
import useDepartmentDelete from "../composables/department/useDepartmentDelete";
import { useDepartmentQuery } from "../composables/department/useDepartmentQuery";
import { useDepartmentUpsert } from "../composables/department/useDepartmentUpsert";
import useAlertStore from "../composables/store/useAlertStore";
import TablePagination from "@/components/TablePagination.vue";

const name = ref<string>("");
const selectedDepartment = ref<components["schemas"]["Department"]>();
const departmentUpsertModal = ref<ModalInterface>();
const departmentDeleteModal = ref<ModalInterface>();

const {
	departments,
	allDepartments,
	fetchDepartmentWith,
	fetchAllDepartments,
	total,
} = useDepartmentQuery();

const { deleteDepartment } = useDepartmentDelete();
const { upsertDepartment } = useDepartmentUpsert();

const alertStore = useAlertStore();

onMounted(async () => {
	await fetchAllDepartments();
	await fetchDepartmentWith({
		name: name.value,
	});
	initFlowbite();
	const $upsertModalElement: HTMLElement | null = document.querySelector(
		"#department-upsert-modal",
	);
	const $deleteModalElement: HTMLElement | null = document.querySelector(
		"#department-delete-modal",
	);
	departmentUpsertModal.value = new Modal(
		$upsertModalElement,
		{},
		{
			id: "department-upsert-modal",
		},
	);
	departmentDeleteModal.value = new Modal(
		$deleteModalElement,
		{},
		{
			id: "department-delete-modal",
		},
	);
});

const handleUpsertDepartmentSubmit = async (
	department: components["schemas"]["DepartmentUpsertDto"],
) => {
	departmentUpsertModal.value?.hide();
	await upsertDepartment({
		id: department.id,
		name: department.name,
		parentId: department.parentId,
		enable: department.enable,
	});
	fetchAllDepartments({});
	alertStore.showAlert({
		content: "操作成功",
		level: "success",
	});
};

const handleUpsertDepartmentClick = async (
	department?: components["schemas"]["DepartmentUpsertDto"],
) => {
	selectedDepartment.value = department;
	await nextTick(() => {
		departmentUpsertModal.value?.show();
	});
};

const handleDeleteDepartmentSubmit = async () => {
	if (!selectedDepartment?.value?.id) return;
	await deleteDepartment(selectedDepartment.value.id);
	fetchAllDepartments();
	departmentDeleteModal.value?.hide();
	alertStore.showAlert({
		content: "删除成功",
		level: "success",
	});
};

const handleDeleteDepartmentClick = async (
	department: components["schemas"]["DepartmentUpsertDto"],
) => {
	selectedDepartment.value = department;
	await nextTick(() => {
		departmentDeleteModal.value?.show();
	});
};

const handleSearch = async () => {
	await fetchDepartmentWith({
		name: name.value,
	});
};

const handlePageChange = async (page: number, size: number) => {
	await fetchDepartmentWith(
		{
			name: name.value,
		},
		page,
		size,
	);
};
</script>

<style scoped></style>
