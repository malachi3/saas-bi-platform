<template>
  <div class="select-tenant-container">
    <div class="select-tenant-card">
      <div class="select-tenant-header">
        <h1 class="select-tenant-title">选择租户</h1>
        <p class="select-tenant-subtitle">您有多个租户，请选择要访问的租户</p>
      </div>

      <div class="tenant-list">
        <div
          v-for="tenant in tenantList"
          :key="tenant.id"
          class="tenant-item"
          :class="{ 'tenant-item--active': selectedTenant === tenant.id }"
          @click="handleSelect(tenant.id)"
        >
          <el-icon :size="32" class="tenant-icon">
            <OfficeBuilding />
          </el-icon>
          <div class="tenant-info">
            <div class="tenant-name">{{ tenant.name }}</div>
            <div class="tenant-code">{{ tenant.code }}</div>
          </div>
          <el-icon v-if="selectedTenant === tenant.id" class="tenant-check" :size="20">
            <Check />
          </el-icon>
        </div>
      </div>

      <el-button type="primary" size="large" class="confirm-button" :loading="loading" :disabled="!selectedTenant" @click="handleConfirm">
        确 定
      </el-button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { useTenantStore } from '@/stores/tenant';
import { ElMessage } from 'element-plus';

const router = useRouter();
const route = useRoute();
const tenantStore = useTenantStore();

const loading = ref(false);
const tenantList = ref<{ id: number; name: string; code: string }[]>([]);
const selectedTenant = ref<number | null>(null);

function handleSelect(id: number): void {
  selectedTenant.value = id;
}

async function handleConfirm(): Promise<void> {
  if (!selectedTenant.value) return;

  try {
    loading.value = true;
    await tenantStore.switchTenant(selectedTenant.value);
    ElMessage.success('租户切换成功');

    const redirect = route.query.redirect as string;
    router.push(redirect || '/');
  } catch {
    ElMessage.error('租户切换失败');
  } finally {
    loading.value = false;
  }
}

onMounted(() => {
  tenantList.value = tenantStore.tenantList;
  if (tenantStore.currentTenant) {
    selectedTenant.value = tenantStore.currentTenant.id;
  }
});
</script>

<style scoped>
.select-tenant-container {
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.select-tenant-card {
  width: 480px;
  padding: var(--spacing-xxl);
  background-color: var(--color-bg-base);
  border-radius: var(--radius-lg);
  box-shadow: var(--shadow-lg);
}

.select-tenant-header {
  text-align: center;
  margin-bottom: var(--spacing-xxl);
}

.select-tenant-title {
  margin: 0 0 var(--spacing-sm);
  font-size: var(--font-size-xxl);
  font-weight: var(--font-weight-bold);
  color: var(--color-text-primary);
}

.select-tenant-subtitle {
  margin: 0;
  font-size: var(--font-size-md);
  color: var(--color-text-secondary);
}

.tenant-list {
  margin-bottom: var(--spacing-xl);
}

.tenant-item {
  display: flex;
  align-items: center;
  gap: var(--spacing-md);
  padding: var(--spacing-lg);
  margin-bottom: var(--spacing-sm);
  border: 2px solid var(--color-border);
  border-radius: var(--radius-lg);
  cursor: pointer;
  transition: all var(--transition-normal);
}

.tenant-item:hover {
  border-color: var(--color-primary);
  background-color: var(--color-primary-light);
}

.tenant-item--active {
  border-color: var(--color-primary);
  background-color: var(--color-primary-light);
}

.tenant-icon {
  color: var(--color-primary);
}

.tenant-info {
  flex: 1;
}

.tenant-name {
  font-size: var(--font-size-lg);
  font-weight: var(--font-weight-semibold);
  color: var(--color-text-primary);
}

.tenant-code {
  font-size: var(--font-size-sm);
  color: var(--color-text-secondary);
}

.tenant-check {
  color: var(--color-primary);
}

.confirm-button {
  width: 100%;
}
</style>
