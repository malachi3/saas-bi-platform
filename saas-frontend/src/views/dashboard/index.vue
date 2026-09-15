<template>
  <div class="dashboard-container">
    <!-- 统计卡片 -->
    <div class="stats-grid">
      <div v-for="stat in stats" :key="stat.key" class="stat-card">
        <div class="stat-card__icon" :style="{ backgroundColor: stat.color }">
          <el-icon :size="24">
            <component :is="stat.icon" />
          </el-icon>
        </div>
        <div class="stat-card__content">
          <div class="stat-card__value">{{ stat.value }}</div>
          <div class="stat-card__label">{{ stat.label }}</div>
        </div>
      </div>
    </div>

    <!-- 快捷操作 -->
    <div class="quick-actions">
      <h3 class="section-title">快捷操作</h3>
      <div class="actions-grid">
        <div
          v-for="action in quickActions"
          :key="action.id"
          class="action-card"
          @click="handleAction(action)"
        >
          <el-icon :size="32" :color="action.color">
            <component :is="action.icon" />
          </el-icon>
          <span class="action-card__name">{{ action.name }}</span>
        </div>
      </div>
    </div>

    <!-- 登录趋势 -->
    <div class="chart-section">
      <h3 class="section-title">本周登录趋势</h3>
      <div class="chart-placeholder">
        <el-empty description="图表组件占位" />
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { getDashboardStats, getQuickActions } from '@/api/dashboard';
import type { DashboardStats, QuickAction } from '@/api/dashboard';

const router = useRouter();

const stats = ref([
  { key: 'userCount', label: '用户总数', value: 0, icon: 'User', color: '#1890ff' },
  { key: 'todayLogin', label: '今日登录', value: 0, icon: 'Login', color: '#52c41a' },
  { key: 'todayActive', label: '今日活跃', value: 0, icon: 'UserFilled', color: '#faad14' },
]);

const quickActions = ref<QuickAction[]>([]);

async function loadData(): Promise<void> {
  try {
    const statsData = await getDashboardStats();
    stats.value = [
      { key: 'userCount', label: '用户总数', value: statsData.userCount, icon: 'User', color: '#1890ff' },
      { key: 'todayLogin', label: '今日登录', value: statsData.todayLoginCount, icon: 'Login', color: '#52c41a' },
      { key: 'todayActive', label: '今日活跃', value: statsData.todayActiveCount, icon: 'UserFilled', color: '#faad14' },
    ];

    const actions = await getQuickActions();
    quickActions.value = actions;
  } catch {
    // 加载失败处理
  }
}

function handleAction(action: QuickAction): void {
  router.push(action.path);
}

onMounted(() => {
  loadData();
});
</script>

<style scoped>
.dashboard-container {
  max-width: 1200px;
  margin: 0 auto;
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(240px, 1fr));
  gap: var(--spacing-lg);
  margin-bottom: var(--spacing-xxl);
}

.stat-card {
  display: flex;
  align-items: center;
  gap: var(--spacing-lg);
  padding: var(--spacing-xl);
  background-color: var(--color-bg-base);
  border-radius: var(--radius-lg);
  box-shadow: var(--shadow-sm);
}

.stat-card__icon {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 56px;
  height: 56px;
  border-radius: var(--radius-lg);
  color: var(--color-white);
}

.stat-card__value {
  font-size: var(--font-size-xxl);
  font-weight: var(--font-weight-bold);
  color: var(--color-text-primary);
}

.stat-card__label {
  font-size: var(--font-size-sm);
  color: var(--color-text-secondary);
}

.section-title {
  margin: 0 0 var(--spacing-lg);
  font-size: var(--font-size-lg);
  font-weight: var(--font-weight-semibold);
  color: var(--color-text-primary);
}

.quick-actions {
  margin-bottom: var(--spacing-xxl);
}

.actions-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(120px, 1fr));
  gap: var(--spacing-md);
}

.action-card {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: var(--spacing-sm);
  padding: var(--spacing-xl);
  background-color: var(--color-bg-base);
  border-radius: var(--radius-lg);
  cursor: pointer;
  transition: all var(--transition-normal);
}

.action-card:hover {
  transform: translateY(-4px);
  box-shadow: var(--shadow-md);
}

.action-card__name {
  font-size: var(--font-size-md);
  color: var(--color-text-primary);
}

.chart-section {
  margin-bottom: var(--spacing-xxl);
}

.chart-placeholder {
  padding: var(--spacing-xxl);
  background-color: var(--color-bg-base);
  border-radius: var(--radius-lg);
  text-align: center;
}
</style>
