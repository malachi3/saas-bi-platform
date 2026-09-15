/**
 * Vue Router Configuration
 * 路由配置
 */

import { createRouter, createWebHistory, type RouteRecordRaw } from 'vue-router';
import { staticRoutes } from './routes';
import { usePermissionStore } from '@/stores/permission';
import authGuard from './guards/authGuard';
import permissionGuard from './guards/permissionGuard';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: staticRoutes as RouteRecordRaw[],
  scrollBehavior(_to, _from, savedPosition) {
    if (savedPosition) {
      return savedPosition;
    }
    return { top: 0 };
  },
});

// 注册路由守卫
router.beforeEach(authGuard);
router.beforeEach(permissionGuard);

/**
 * 动态添加路由
 */
export function addRoutes(routes: RouteRecordRaw[]): void {
  routes.forEach((route) => {
    if (!router.hasRoute(route.name as string)) {
      router.addRoute(route as RouteRecordRaw);
    }
  });
}

/**
 * 重置路由
 */
export function resetRouter(): void {
  router.getRoutes().forEach((route) => {
    const name = route.name as string;
    if (!['Login', 'Register', 'ForgotPassword', 'Forbidden', 'NotFound'].includes(name)) {
      router.removeRoute(name);
    }
  });
}

export default router;
