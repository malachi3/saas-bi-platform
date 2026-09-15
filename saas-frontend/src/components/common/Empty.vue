<template>
  <div class="empty-state">
    <el-empty :image-size="imageSize" :description="description">
      <slot />
    </el-empty>
  </div>
</template>

<script setup lang="ts">
defineOptions({
  name: 'EmptyState',
});

interface Props {
  description?: string;
  imageSize?: number;
}

withDefaults(defineProps<Props>(), {
  description: '暂无数据',
  imageSize: 120,
});
</script>

<style scoped>
.empty-state {
  display: flex;
  justify-content: center;
  align-items: center;
  padding: var(--spacing-xxl);
}
</style>
