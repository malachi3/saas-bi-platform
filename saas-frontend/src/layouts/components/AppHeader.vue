<template>
  <header class="app-header">
    <div class="app-header__left">
      <h1 class="app-header__title">SaaS Platform</h1>
    </div>

    <div class="app-header__center">
      <!-- 搜索框 -->
      <div class="app-header__search">
        <el-input
          v-model="searchKeyword"
          placeholder="搜索菜单、功能..."
          prefix-icon="Search"
          clearable
        />
      </div>
    </div>

    <div class="app-header__right">
      <!-- 租户切换 -->
      <el-dropdown v-if="tenantStore.hasMultipleTenants" @command="handleTenantSwitch">
        <span class="app-header__tenant">
          <el-icon><OfficeBuilding /></el-icon>
          <span>{{ tenantStore.currentTenant?.name || '选择租户' }}</span>
          <el-icon><ArrowDown /></el-icon>
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item
              v-for="tenant in tenantStore.tenantList"
              :key="tenant.id"
              :command="tenant.id"
              :disabled="tenant.id === tenantStore.currentTenant?.id"
            >
              {{ tenant.name }}
            </el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>

      <!-- 全屏切换 -->
      <el-tooltip content="全屏" placement="bottom">
        <el-icon class="app-header__icon" @click="toggleFullscreen">
          <FullScreen />
        </el-icon>
      </el-tooltip>

      <!-- 消息通知 -->
      <el-badge :value="3" :max="99" class="app-header__badge">
        <el-tooltip content="消息通知" placement="bottom">
          <el-icon class="app-header__icon">
            <Bell />
          </el-icon>
        </el-tooltip>
      </el-badge>

      <!-- 用户信息 -->
      <el-dropdown @command="handleUserCommand">
        <span class="app-header__user">
          <el-avatar :src="userStore.userInfo?.avatar" :size="32">
            {{ userStore.userInfo?.nickname?.charAt(0) || 'U' }}
          </el-avatar>
          <span class="app-header__username">{{ userStore.userInfo?.nickname }}</span>
          <el-icon><ArrowDown /></el-icon>
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item command="profile">
              <el-icon><User /></el-icon>
              个人中心
            </el-dropdown-item>
            <el-dropdown-item command="settings">
              <el-icon><Setting /></el-icon>
              账户设置
            </el-dropdown-item>
            <el-dropdown-item divided command="logout">
              <el-icon><SwitchButton /></el-icon>
              退出登录
            </el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </header>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { useUserStore } from '@/stores/user';
import { useTenantStore } from '@/stores/tenant';
import { ElMessage } from 'element-plus';

const router = useRouter();
const userStore = useUserStore();
const tenantStore = useTenantStore();

const searchKeyword = ref('');

function toggleFullscreen(): void {
  if (!document.fullscreenElement) {
    document.documentElement.requestFullscreen();
  } else {
    document.exitFullscreen();
  }
}

async function handleTenantSwitch(tenantId: number): Promise<void> {
  try {
    await tenantStore.switchTenant(tenantId);
    ElMessage.success('租户切换成功');
  } catch {
    ElMessage.error('租户切换失败');
  }
}

function handleUserCommand(command: string): void {
  switch (command) {
    case 'profile':
      router.push('/profile');
      break;
    case 'settings':
      router.push('/settings');
      break;
    case 'logout':
      userStore.logout();
      break;
  }
}
</script>

<style scoped>
.app-header {
  display: flex;
  align-items: center;
  height: var(--header-height);
  padding: 0 var(--spacing-lg);
  background-color: var(--color-bg-base);
  border-bottom: 1px solid var(--color-border-light);
  gap: var(--spacing-lg);
}

.app-header__left {
  flex-shrink: 0;
}

.app-header__title {
  margin: 0;
  font-size: var(--font-size-lg);
  font-weight: var(--font-weight-semibold);
  color: var(--color-text-primary);
}

.app-header__center {
  flex: 1;
  max-width: 400px;
}

.app-header__search {
  width: 100%;
}

.app-header__right {
  display: flex;
  align-items: center;
  gap: var(--spacing-md);
}

.app-header__tenant {
  display: flex;
  align-items: center;
  gap: var(--spacing-xs);
  padding: var(--spacing-xs) var(--spacing-sm);
  border-radius: var(--radius-md);
  cursor: pointer;
  transition: background-color var(--transition-fast);
}

.app-header__tenant:hover {
  background-color: var(--color-bg-layout);
}

.app-header__icon {
  padding: var(--spacing-sm);
  border-radius: var(--radius-md);
  cursor: pointer;
  transition: background-color var(--transition-fast);
}

.app-header__icon:hover {
  background-color: var(--color-bg-layout);
}

.app-header__user {
  display: flex;
  align-items: center;
  gap: var(--spacing-sm);
  padding: var(--spacing-xs);
  border-radius: var(--radius-md);
  cursor: pointer;
  transition: background-color var(--transition-fast);
}

.app-header__user:hover {
  background-color: var(--color-bg-layout);
}

.app-header__username {
  font-size: var(--font-size-md);
  color: var(--color-text-primary);
}

.app-header__badge {
  cursor: pointer;
}
</style>
