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
                class="ml-1 text-gray-400 hover:text-primary-600 md:ml-2 dark:text-gray-500 dark:hover:text-white">岗位分配</span>
            </div>
          </li>
        </ol>
      </nav>
      <h1 class="text-xl mb-2 font-semibold text-gray-900 sm:text-2xl dark:text-white">岗位分配</h1>
    </div>
    <div class="relative">
      <form class="max-w-sm mb-4 grid grid-cols-5 gap-y-4">
        <div class="col-span-3">
          <label for="default-search"
            class="mb-2 text-sm font-medium text-gray-900 sr-only dark:text-white">Search</label>
          <div class="relative">
            <div class="absolute inset-y-0 start-0 flex items-center ps-3 pointer-events-none">
              <svg class="w-4 h-4 text-gray-500 dark:text-gray-400" aria-hidden="true"
                xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 20 20">
                <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                  d="m19 19-4-4m0-7A7 7 0 1 1 1 8a7 7 0 0 1 14 0Z" />
              </svg>
            </div>
            <input type="search" id="default-search" v-model="positionName"
              class="block p-3 ps-10 text-sm text-gray-900 border border-gray-300 rounded-lg bg-gray-50 focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
              placeholder="岗位名称" required />
          </div>
        </div>
        <select id="countries" v-model="bindState"
          class="col-span-2 block bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500">
          <option value="BIND">已绑定</option>
          <option value="UNBIND">未绑定</option>
          <option value="ALL">全部</option>
        </select>
        <button type="submit"
          class="text-white  bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-4 py-2 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
          @click.prevent="handleSearch">搜索</button>
      </form>
      <div class="flex items-center justify-end gap-2 absolute right-5 bottom-2">
        <button @click="() => {
          if (checkedPositionIds.length === 0) {
            alertStore.showAlert({
              content: '没有选择岗位',
              level: 'error',
            });
          } else {
            positionBindModal?.show();
          }
        }"
          class="flex items-center block text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center"
          type="button">
          绑定
        </button>
        <button @click="() => {
          if (checkedPositionIds.length === 0) {
            alertStore.showAlert({
              content: '没有选择岗位',
              level: 'error',
            });
          } else {
            positionUnbindModal?.show();
          }
        }"
          class="flex items-center block text-white bg-red-700 hover:bg-red-800 focus:ring-4 focus:outline-none focus:ring-red-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center"
          type="button">
          解绑
        </button>
      </div>
    </div>

    <table class="w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400">
      <thead class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
        <tr>
          <th scope="col" class="p-4">
            <div class="flex items-center">
              <input id="checkbox-all-search" type="checkbox" v-model="allChecked"
                class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded-sm focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 dark:focus:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600">
              <label for="checkbox-all-search" class="sr-only">checkbox</label>
            </div>
          </th>
          <th scope="col" class="px-6 py-3">岗位名称</th>
          <th scope="col" class="px-6 py-3">绑定状态</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="position in positions" :key="position.id"
          class="bg-white border-b dark:bg-gray-800 dark:border-gray-700 border-gray-200 hover:bg-gray-50 dark:hover:bg-gray-600">
          <td class="w-4 p-4">
            <div class="flex items-center">
              <input :id="'checkbox-table-search-' + position.id" :value="position.id" type="checkbox"
                v-model="checkedPositionIds"
                class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded-sm focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 dark:focus:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600">
              <label :for="'checkbox-table-search-' + position.id" class="sr-only">checkbox</label>
            </div>
          </td>

          <td scope="row" class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white">
            {{ position.name }}
          </td>
          <td class="px-6 py-4">
            <div class="flex items-center">
              <div class="h-2.5 w-2.5 rounded-full me-2" :class="position.isBound ? 'bg-green-500' : 'bg-red-500'">
              </div> {{
              position.isBound === true ? "已绑定" : "未绑定" }}
            </div>
          </td>
        </tr>
      </tbody>
    </table>

    <TablePagination :pageChange="handlePageChange" :total="total" />
  </div>

  <BindModal :id="'position-bind-modal'" :closeModal="() => {
    positionBindModal!.hide();
  }" :onSubmit="handleBindPositionSubmit" title="绑定选中的岗位吗"></BindModal>
  <UnModal :id="'position-unbind-modal'" :closeModal="() => {
    positionUnbindModal!.hide();
  }" :onSubmit="handleUnbindPositionSubmit" title="解绑选中的岗位吗"></UnModal>
</template>

<script setup lang="ts">
import BindModal from "@/components/PopupModal.vue";
import UnModal from "@/components/PopupModal.vue";
import TablePagination from "@/components/TablePagination.vue";
import { useDepartmentQuery } from "@/composables/department/useDepartmentQuery";
import { usePositionBind } from "@/composables/position/useDepartmentBind";
import { usePositionQuery } from "@/composables/position/useDepartmentQuery";
import { RouteName } from "@/router/constants";
import { Modal, type ModalInterface, initFlowbite } from "flowbite";
import { onMounted, ref, watch } from "vue";
import { useRoute } from "vue-router";
import { useDepartmentBind } from "../composables/department/useDepartmentBind";
import useAlertStore from "../composables/store/useAlertStore";

const positionName = ref<string>("");
const checkedPositionIds = ref<number[]>([]);
const positionBindModal = ref<ModalInterface>();
const positionUnbindModal = ref<ModalInterface>();
const allChecked = ref<boolean>(false);
const $route = useRoute();
const bindState = ref<"BIND" | "ALL" | "UNBIND">("BIND");

const alertStore = useAlertStore();

const { total, positions, fetchPositionWith } = usePositionQuery();

const { bindPosition, unbindPosition } = usePositionBind();

const handleBindPositionSubmit = async () => {
	await bindPosition(Number($route.params.userId), checkedPositionIds.value);
	positionBindModal.value?.hide();
	alertStore.showAlert({
		content: "操作成功",
		level: "success",
	});
	await fetchPositionWith({
		name: positionName.value,
		userId: Number($route.params.userId),
		bindState: bindState.value,
	});
};

const handleUnbindPositionSubmit = async () => {
	await unbindPosition(Number($route.params.userId), checkedPositionIds.value);
	positionUnbindModal.value?.hide();
	alertStore.showAlert({
		content: "操作成功",
		level: "success",
	});
	await fetchPositionWith({
		name: positionName.value,
		userId: Number($route.params.userId),
		bindState: bindState.value,
	});
};

onMounted(async () => {
	await fetchPositionWith({
		name: positionName.value,
		userId: Number($route.params.userId),
		bindState: bindState.value,
	});
	initFlowbite();
	const $bindModalElement: HTMLElement | null = document.querySelector(
		"#position-bind-modal",
	);
	positionBindModal.value = new Modal(
		$bindModalElement,
		{},
		{ id: "position-bind-modal" },
	);
	const $unbindModalElement: HTMLElement | null = document.querySelector(
		"#department-unbind-modal",
	);
	positionUnbindModal.value = new Modal(
		$unbindModalElement,
		{},
		{ id: "position-unbind-modal" },
	);
});

const handleSearch = async () => {
	await fetchPositionWith({
		name: positionName.value,
		userId: Number($route.params.userId),
		bindState: bindState.value,
	});
};

const handlePageChange = async (page: number, pageSize: number) => {
	await fetchPositionWith(
		{
			name: positionName.value,
			userId: Number($route.params.userId),
			bindState: bindState.value,
		},
		page,
		pageSize,
	);
};

watch(allChecked, async () => {
	if (allChecked.value) {
		checkedPositionIds.value = positions.value?.map((d) => d.id!) ?? [];
	} else {
		checkedPositionIds.value = [];
	}
});
</script>

<style scoped></style>
