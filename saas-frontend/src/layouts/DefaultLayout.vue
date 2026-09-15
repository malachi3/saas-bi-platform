<template>
  <div class="default-layout">
    <AppHeader />
    <div class="default-layout__body">
      <AppSidebar />
      <div class="default-layout__main">
        <AppMain />
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import AppHeader from './components/AppHeader.vue';
import AppSidebar from './components/AppSidebar.vue';
import AppMain from './components/AppMain.vue';
</script>

<style scoped>
.default-layout {
  display: flex;
  flex-direction: column;
  height: 100vh;
  overflow: hidden;
}

.default-layout__body {
  display: flex;
  flex: 1;
  overflow: hidden;
}

.default-layout__main {
  flex: 1;
  overflow: auto;
  padding: var(--spacing-lg);
  background-color: var(--color-bg-layout);
}
</style>
