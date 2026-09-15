<template>
  <aside class="app-sidebar" :class="{ 'app-sidebar--collapsed': collapsed }">
    <div class="app-sidebar__header">
      <img v-if="!collapsed" src="/logo.svg" alt="Logo" class="app-sidebar__logo" />
      <span v-if="!collapsed" class="app-sidebar__title">后台管理系统</span>
      <el-icon v-else class="app-sidebar__icon"><Box /></el-icon>
    </div>

    <el-menu
      :default-active="activeMenu"
      :collapse="collapsed"
      :collapse-transition="false"
      class="app-sidebar__menu"
      @select="handleMenuSelect"
    >
      <template v-for="menu in menus" :key="menu.path">
        <el-menu-item v-if="!menu.children?.length" :index="menu.path">
          <el-icon v-if="menu.icon"><component :is="menu.icon" /></el-icon>
          <template #title>{{ menu.meta?.title || menu.name }}</template>
        </el-menu-item>

        <el-sub-menu v-else :index="menu.path">
          <template #title>
            <el-icon v-if="menu.icon"><component :is="menu.icon" /></el-icon>
            <span>{{ menu.meta?.title || menu.name }}</span>
          </template>
          <el-menu-item
            v-for="child in menu.children"
            :key="child.path"
            :index="child.path"
          >
            <el-icon v-if="child.icon"><component :is="child.icon" /></el-icon>
            <template #title>{{ child.meta?.title || child.name }}</template>
          </el-menu-item>
        </el-sub-menu>
      </template>
    </el-menu>

    <div class="app-sidebar__footer">
      <el-button text @click="collapsed = !collapsed">
        <el-icon>
          <Expand v-if="collapsed" />
          <Fold v-else />
        </el-icon>
      </el-button>
    </div>
  </aside>
</template>

<script setup lang="ts">
import { ref, computed, watch } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { usePermissionStore } from '@/stores/permission';

const route = useRoute();
const router = useRouter();
const permissionStore = usePermissionStore();

const collapsed = ref(false);

// 过滤可见菜单
const menus = computed(() => {
  return permissionStore.menus.filter((menu) => menu.visible !== 0);
});

// 当前激活的菜单
const activeMenu = computed(() => route.path);

function handleMenuSelect(index: string): void {
  router.push(index);
}

// 监听路由变化，更新菜单激活状态
watch(
  () => route.path,
  () => {
    // 菜单选择逻辑
  }
);
</script>

<style scoped>
.app-sidebar {
  display: flex;
  flex-direction: column;
  width: var(--sider-width);
  height: 100%;
  background-color: var(--color-bg-base);
  border-right: 1px solid var(--color-border-light);
  transition: width var(--transition-normal);
}

.app-sidebar--collapsed {
  width: var(--sider-collapsed-width);
}

.app-sidebar__header {
  display: flex;
  align-items: center;
  height: var(--header-height);
  padding: 0 var(--spacing-md);
  gap: var(--spacing-sm);
}

.app-sidebar__logo {
  width: 32px;
  height: 32px;
}

.app-sidebar__title {
  font-size: var(--font-size-lg);
  font-weight: var(--font-weight-semibold);
  color: var(--color-text-primary);
  white-space: nowrap;
}

.app-sidebar__icon {
  font-size: 20px;
}

.app-sidebar__menu {
  flex: 1;
  overflow-y: auto;
  border-right: none;
}

.app-sidebar__footer {
  padding: var(--spacing-sm);
  border-top: 1px solid var(--color-border-light);
  text-align: center;
}
</style>
