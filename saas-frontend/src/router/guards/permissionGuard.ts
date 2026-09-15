/**
 * Permission Guard
 * 路由守卫 - 权限相关
 */

import type { NavigationGuardNext, RouteLocationNormalized } from 'vue-router';
import { usePermissionStore } from '@/stores/permission';
import { useUserStore } from '@/stores/user';

/**
 * 权限守卫
 * 处理动态路由加载和权限检查
 */
export async function permissionGuard(
  to: RouteLocationNormalized,
  from: RouteLocationNormalized,
  next: NavigationGuardNext
): Promise<void> {
  const permissionStore = usePermissionStore();
  const userStore = useUserStore();

  // 需要权限的路由
  if (to.meta.requiresAuth !== false) {
    // 加载权限和路由（如果尚未加载）
    if (!permissionStore.isLoaded) {
      try {
        await permissionStore.loadPermissions();
      } catch {
        // 加载失败，已在 authGuard 中处理
        return;
      }
    }

    // 检查权限
    const requiredPermission = to.meta.permission as string | string[] | undefined;
    if (requiredPermission) {
      const hasPermission = permissionStore.hasPermission(requiredPermission);
      if (!hasPermission) {
        // 无权限，跳转到 403 页面
        next({ path: '/403' });
        return;
      }
    }

    // 检查角色
    const requiredRole = to.meta.role as string | string[] | undefined;
    if (requiredRole) {
      const hasRole = permissionStore.hasRole(requiredRole);
      if (!hasRole) {
        next({ path: '/403' });
        return;
      }
    }
  }

  // 动态添加路由后直接访问的情况
  if (from.name === undefined && to.name === undefined) {
    // 首次加载，等待路由准备好
    next();
    return;
  }

  next();
}

export default permissionGuard;
