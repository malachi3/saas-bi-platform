<template>
  <main class="app-main">
    <!-- 面包屑导航 -->
    <div v-if="showBreadcrumb" class="app-main__breadcrumb">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item v-for="item in breadcrumbs" :key="item.path">
          {{ item.meta?.title || item.name }}
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <!-- 页面内容 -->
    <div class="app-main__content">
      <router-view v-slot="{ Component }">
        <transition name="fade" mode="out-in">
          <keep-alive :include="keepAliveRoutes">
            <component :is="Component" />
          </keep-alive>
        </transition>
      </router-view>
    </div>
  </main>
</template>

<script setup lang="ts">
import { computed } from 'vue';
import { useRoute } from 'vue-router';

const route = useRoute();

// 是否显示面包屑
const showBreadcrumb = computed(() => {
  return route.meta.breadcrumb !== false && route.path !== '/';
});

// 面包屑数据
const breadcrumbs = computed(() => {
  return route.matched.filter((item) => item.meta?.title);
});

// 需要缓存的路由名称
const keepAliveRoutes = computed(() => {
  const routes = route.matched.filter((item) => item.meta?.keepAlive);
  return routes.map((item) => item.name as string);
});
</script>

<style scoped>
.app-main {
  display: flex;
  flex-direction: column;
  height: 100%;
}

.app-main__breadcrumb {
  margin-bottom: var(--spacing-lg);
}

.app-main__content {
  flex: 1;
  overflow: auto;
}

/* 页面切换动画 */
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.2s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>
