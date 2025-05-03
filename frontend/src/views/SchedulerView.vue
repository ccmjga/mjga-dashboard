<template>
  <div class="relative overflow-x-scroll px-4 pt-6 xl:grid-cols-3 xl:gap-4 sm:rounded-lg">
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
                class="ml-1 text-gray-400 hover:text-primary-600 md:ml-2 dark:text-gray-500 dark:hover:text-white">定时任务管理</span>
            </div>
          </li>
        </ol>
      </nav>
      <h1 class="text-xl font-semibold text-gray-900 sm:text-2xl dark:text-white">定时任务管理</h1>
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
          <input type="search" id="default-search" v-model="jobName"
            class="block w-full p-4 ps-10 text-sm text-gray-900 border border-gray-300 rounded-lg bg-gray-50 focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
            placeholder="定时任务名称" required />
          <button type="submit"
            class="text-white absolute end-2.5 bottom-2.5 bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-4 py-2 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
            @click.prevent="handleSearch">搜索</button>
        </div>
      </form>
      <!-- Create Modal toggle -->
    </div>

    <table
      class="w-full text-sm text-left rtl:text-right shadow-lg rounded-lg text-gray-500 dark:text-gray-400 overflow-x-auto">
      <thead class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
        <tr>
          <th scope="col" class="p-4">
            <div class="flex items-center">
              <input id="checkbox-all-search" disabled type="checkbox"
                class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded-sm focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 dark:focus:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600">
              <label for="checkbox-all-search" class="sr-only">checkbox</label>
            </div>
          </th>
          <th scope="col" class="px-6 py-3">任务</th>
          <th scope="col" class="px-6 py-3">触发器</th>
          <th scope="col" class="px-6 py-3">开始</th>
          <th scope="col" class="px-6 py-3">结束</th>
          <th scope="col" class="px-6 py-3">下次执行</th>
          <th scope="col" class="px-6 py-3">上次执行</th>
          <th scope="col" class="px-6 py-3">类型</th>
          <th scope="col" class="px-6 py-3">Cron</th>
          <th scope="col" class="px-6 py-3">编辑</th>
          <th scope="col" class="px-6 py-3">操作</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="job in jobs" :key="job.triggerName"
          class="bg-white border-b dark:bg-gray-800 dark:border-gray-700 border-gray-200 hover:bg-gray-50 dark:hover:bg-gray-600">
          <td class="w-4 p-4">
            <div class="flex items-center">
              <input :id="'checkbox-table-search-' + job.name" type="checkbox" disabled
                class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded-sm focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 dark:focus:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600">
              <label :for="'checkbox-table-search-' + job.name" class="sr-only">checkbox</label>
            </div>
          </td>
          <td class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white">{{
            `${job.name}:${job.group}` }}</td>
          <td class="px-6 py-4">{{ `${job.triggerName}:${job.triggerGroup}` }}</td>
          <td class="px-6 py-4">{{ new Date(job.startTime!).toLocaleString() }}</td>
          <td class="px-6 py-4">{{ job.endTime ? new Date(job.endTime).toLocaleString() : undefined }}</td>
          <td class="px-6 py-4">{{ job.nextFireTime ? new Date(job.nextFireTime).toLocaleString() : undefined}}</td>
          <td class="px-6 py-4">{{ job.previousFireTime ? new Date(job.previousFireTime).toLocaleString() : undefined
            }}
          </td>
          <td class="px-6 py-4">{{ job.schedulerType }}</td>
          <td class="px-6 py-4">{{ job.cronExpression }}</td>
          <td class="px-6 py-4 whitespace-nowrap">
            <button @click="handleCronUpdateClick(job)" :disabled="job.schedulerType !== 'CRON'"
              :class="['flex items-center block text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800 me-2' , { 'opacity-50 cursor-not-allowed': job.schedulerType !== 'CRON' }]"
              type="button">
              <svg class="me-1 -ms-1 w-5 h-5" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" width="24"
                height="24" fill="none" viewBox="0 0 24 24">
                <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                  d="m14.304 4.844 2.852 2.852M7 7H4a1 1 0 0 0-1 1v10a1 1 0 0 0 1 1h11a1 1 0 0 0 1-1v-4.5m2.409-9.91a2.017 2.017 0 0 1 0 2.853l-6.844 6.844L8 14l.713-3.565 6.844-6.844a2.015 2.015 0 0 1 2.852 0Z" />
              </svg>
              更新
            </button>
          </td>
          <td class="px-6 py-4 whitespace-nowrap">
            <button :disabled="job.triggerState !== 'PAUSE'" :class="['inline-flex items-center text-white bg-green-700 hover:bg-green-800 focus:ring-green-300 dark:bg-green-600 dark:hover:bg-green-700 dark:focus:ring-green-900  text-white focus:ring-4 focus:outline-nonefont-medium rounded-lg text-sm px-5 py-2.5 text-center mr-2',
                { 'opacity-50 cursor-not-allowed': job.triggerState !== 'PAUSE' }]" @click="handleResumeJobClick(job)"
              type="button">
              <svg class="me-1 -ms-1  w-5 h-5 text-white" aria-hidden="true" xmlns="http://www.w3.org/2000/svg"
                width="24" height="24" fill="none" viewBox="0 0 24 24">
                <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                  d="M8 18V6l8 6-8 6Z" />
              </svg>
              恢复
            </button>
            <button :disabled="job.triggerState === 'PAUSE'" :class="['inline-flex items-center mr-2 text-white bg-red-700 hover:bg-red-800 focus:ring-red-300 dark:bg-red-600 dark:hover:bg-red-700 dark:focus:ring-red-900 block text-white focus:ring-4 focus:outline-nonefont-medium rounded-lg text-sm px-5 py-2.5 text-center',
              { 'opacity-50 cursor-not-allowed': job.triggerState === 'PAUSE' }]" @click="handlePauseJobClick(job)"
              type="button">
              <svg class="me-1 -ms-1 w-5 h-5 text-white" aria-hidden="true" xmlns="http://www.w3.org/2000/svg"
                width="24" height="24" fill="none" viewBox="0 0 24 24">
                <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                  d="M10 9v6m4-6v6m7-3a9 9 0 1 1-18 0 9 9 0 0 1 18 0Z" />
              </svg>
              暂停
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

  <PopupModal :id="'job-resume-modal'" :closeModal="() => {
    jobResumeModal!.hide();
  }" :onSubmit="handleResumeModalConfirmClick" title="确定恢复该任务吗？" content="恢复任务"></PopupModal>
  <PopupModal :id="'job-pause-modal'" :closeModal="() => {
    jobPauseModal!.hide();
  }" :onSubmit="handlePauseModalSubmit" title="确定暂停该任务吗" content="暂停任务"></PopupModal>
  <JobUpdateModal :job="selectedJob" :id="'job-update-modal'" :closeModal="() => {
    jobUpdateModal!.hide();
  }" :onSubmit="handleJobUpdateModalSubmit"></JobUpdateModal>
</template>

<script setup lang="ts">
import JobUpdateModal from "@/components/JobUpdateModal.vue";
import PopupModal from "@/components/PopupModal.vue";
import { useJobControl } from "@/composables/job/useJobControl";
import { useJobsPaginationQuery } from "@/composables/job/useJobQuery";
import { useJobUpdate } from "@/composables/job/useJobUpdate";
import useAlertStore from "@/composables/store/useAlertStore";
import { Modal, type ModalInterface, initFlowbite } from "flowbite";
import { nextTick, onMounted, ref } from "vue";
import type { components } from "../api/types/schema";
import { RouteName } from "@/router/constants";

const jobName = ref<string>("");
const jobResumeModal = ref<ModalInterface>();
const jobPauseModal = ref<ModalInterface>();
const jobUpdateModal = ref<ModalInterface>();
const selectedJob = ref<components["schemas"]["JobTriggerDto"]>();

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
	jobs,
	total,
	fetchJobsWith,
} = useJobsPaginationQuery(1, 10);

const alertStore = useAlertStore();

const { resumeTrigger, pauseTrigger } = useJobControl();

const { updateCron } = useJobUpdate();

const handleResumeJobClick = async (
	currentJob: components["schemas"]["JobTriggerDto"],
) => {
	selectedJob.value = currentJob;
	await nextTick(() => {
		jobResumeModal.value?.show();
	});
};

const handleCronUpdateClick = async (
	currentJob: components["schemas"]["JobTriggerDto"],
) => {
	selectedJob.value = currentJob;
	await nextTick(() => {
		jobUpdateModal.value?.show();
	});
};

const handlePauseJobClick = async (
	currentJob: components["schemas"]["JobTriggerDto"],
) => {
	selectedJob.value = currentJob;
	await nextTick(() => {
		jobPauseModal.value?.show();
	});
};

const handleResumeModalConfirmClick = async () => {
	if (selectedJob.value?.triggerState !== "PAUSE") {
		return;
	}
	await resumeTrigger({
		triggerName: selectedJob.value.triggerName!,
		triggerGroup: selectedJob.value.group!,
	});
	await fetchJobsWith(currentPage.value, pageSize.value, {
		name: jobName.value,
	});
	jobResumeModal.value?.hide();
	alertStore.showAlert({
		level: "success",
		content: "操作成功",
	});
};

const handleJobUpdateModalSubmit = async () => {
	await updateCron({
		triggerName: selectedJob.value!.triggerName!,
		triggerGroup: selectedJob.value!.group!,
		cron: selectedJob.value!.cronExpression!,
	});
	await fetchJobsWith(currentPage.value, pageSize.value, {
		name: jobName.value,
	});
	jobUpdateModal.value?.hide();
	alertStore.showAlert({
		level: "success",
		content: "操作成功",
	});
};

const handlePauseModalSubmit = async () => {
	if (selectedJob.value?.triggerState === "PAUSE" || controlLoading.value) {
		return;
	}
	await pauseTrigger({
		triggerName: selectedJob!.value!.triggerName!,
		triggerGroup: selectedJob!.value!.group!,
	});
	await fetchJobsWith(currentPage.value, pageSize.value, {
		name: jobName.value,
	});
	jobPauseModal.value?.hide();
	alertStore.showAlert({
		level: "success",
		content: "操作成功",
	});
};

const handleSearch = async () => {
	await fetchJobsWith(currentPage.value, pageSize.value, {
		name: jobName.value,
	});
};

const handlePageChange = async (page: number) => {
	if (page < 1 || page > totalPages.value) return;
	await fetchJobsWith(page, pageSize.value, {
		name: jobName.value,
	});
};

onMounted(async () => {
	await fetchJobsWith(currentPage.value, pageSize.value, {
		name: jobName.value,
	});
	initFlowbite();
	const $jobResumeModalElement: HTMLElement | null =
		document.querySelector("#job-resume-modal");
	const $jobPauseModalElement: HTMLElement | null =
		document.querySelector("#job-pause-modal");
	const $jobUpdateModalElement: HTMLElement | null =
		document.querySelector("#job-update-modal");

	jobResumeModal.value = new Modal(
		$jobResumeModalElement,
		{},
		{
			id: "job-resume-modal",
		},
	);
	jobPauseModal.value = new Modal(
		$jobPauseModalElement,
		{},
		{
			id: "job-pause-modal",
		},
	);
	jobUpdateModal.value = new Modal(
		$jobUpdateModalElement,
		{},
		{
			id: "job-update-modal",
		},
	);
});
</script>

<style scoped></style>
