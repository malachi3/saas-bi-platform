<template>
  <div class="page-container">
    <slot />
  </div>
</template>

<script setup lang="ts">
defineOptions({
  name: 'PageContainer',
});
</script>

<style scoped>
.page-container {
  background-color: var(--color-bg-base);
  border-radius: var(--radius-lg);
  padding: var(--spacing-lg);
  min-height: 100%;
}
</style>
