/**
 * Auth Guard
 * 路由守卫 - 认证相关
 */

import type { NavigationGuardNext, RouteLocationNormalized } from 'vue-router';
import { useUserStore } from '@/stores/user';
import { useTenantStore } from '@/stores/tenant';

/** 白名单路由 - 不需要登录即可访问 */
const whiteList = ['/auth/login', '/auth/register', '/auth/forgot-password'];

/**
 * 认证守卫
 * 处理登录状态检查和重定向
 */
export async function authGuard(
  to: RouteLocationNormalized,
  from: RouteLocationNormalized,
  next: NavigationGuardNext
): Promise<void> {
  const userStore = useUserStore();
  const tenantStore = useTenantStore();

  // 检查是否已登录
  const hasToken = !!userStore.token;

  // 白名单路由直接放行
  if (whiteList.includes(to.path)) {
    // 已登录则跳转到首页
    if (hasToken) {
      next('/');
    } else {
      next();
    }
    return;
  }

  // 需要登录的路由
  if (to.meta.requiresAuth !== false) {
    if (!hasToken) {
      // 未登录，跳转到登录页
      next({
        path: '/auth/login',
        query: { redirect: to.fullPath },
      });
      return;
    }

    // 已有 Token 但没有用户信息，尝试获取
    if (!userStore.userInfo) {
      try {
        await userStore.fetchUserInfo();
        await tenantStore.loadTenantList();
      } catch {
        // 获取失败，清除 Token 并跳转登录
        userStore.logout();
        next({
          path: '/auth/login',
          query: { redirect: to.fullPath },
        });
        return;
      }
    }

    // 检查租户
    if (!tenantStore.currentTenant && tenantStore.hasMultipleTenants) {
      // 如果有多个租户且未选择，跳转到租户选择页
      if (to.path !== '/auth/select-tenant') {
        next({
          path: '/auth/select-tenant',
          query: { redirect: to.fullPath },
        });
        return;
      }
    }
  }

  next();
}

export default authGuard;
