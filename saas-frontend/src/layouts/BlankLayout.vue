<template>
  <div class="blank-layout">
    <router-view />
  </div>
</template>

<script setup lang="ts">
// 空白布局 - 仅包含路由视图
</script>

<style scoped>
.blank-layout {
  min-height: 100vh;
  background-color: var(--color-bg-layout);
}
</style>
