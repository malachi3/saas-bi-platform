<template>
  <div v-if="visible" class="loading-overlay" :class="{ 'loading-overlay--absolute': absolute }">
    <el-icon class="loading-spinner" :size="size">
      <Loading />
    </el-icon>
    <p v-if="text" class="loading-text">{{ text }}</p>
  </div>
</template>

<script setup lang="ts">
defineOptions({
  name: 'LoadingSpinner',
});

interface Props {
  visible?: boolean;
  text?: string;
  size?: number;
  absolute?: boolean;
}

withDefaults(defineProps<Props>(), {
  visible: true,
  text: '',
  size: 32,
  absolute: false,
});
</script>

<style scoped>
.loading-overlay {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: var(--spacing-sm);
  background-color: rgba(255, 255, 255, 0.8);
  z-index: var(--z-index-modal);
}

.loading-overlay--absolute {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
}

.loading-spinner {
  color: var(--color-primary);
  animation: spin 1s linear infinite;
}

.loading-text {
  margin: 0;
  font-size: var(--font-size-sm);
  color: var(--color-text-secondary);
}

@keyframes spin {
  from {
    transform: rotate(0deg);
  }
  to {
    transform: rotate(360deg);
  }
}
</style>
