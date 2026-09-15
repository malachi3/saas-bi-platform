<template>
  <div class="error-page">
    <div class="error-content">
      <el-icon :size="80" class="error-icon">
        <WarningFilled />
      </el-icon>
      <h1 class="error-title">404</h1>
      <p class="error-message">抱歉，您访问的页面不存在</p>
      <el-button type="primary" @click="goHome">返回首页</el-button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router';

const router = useRouter();

function goHome(): void {
  router.push('/');
}
</script>

<style scoped>
.error-page {
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
  background-color: var(--color-bg-layout);
}

.error-content {
  text-align: center;
}

.error-icon {
  color: var(--color-warning);
}

.error-title {
  margin: var(--spacing-lg) 0;
  font-size: 80px;
  font-weight: var(--font-weight-bold);
  color: var(--color-text-primary);
}

.error-message {
  margin: 0 0 var(--spacing-xl);
  font-size: var(--font-size-lg);
  color: var(--color-text-secondary);
}
</style>
