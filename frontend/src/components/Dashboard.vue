<script setup lang="ts">
import { RouterView } from "vue-router";
import Sidebar from "./Sidebar.vue";
import Headbar from "./Headbar.vue";
</script>

<template>
  <Headbar></Headbar>
  <Sidebar>
  </Sidebar>
  <article class="ml-64 mt-14">
    <RouterView></RouterView>
  </article>
</template>
