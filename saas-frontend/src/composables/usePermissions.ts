/**
 * usePermissions
 * 权限检查 Hook
 */

import { computed } from 'vue';
import { usePermissionStore } from '@/stores/permission';
import { useUserStore } from '@/stores/user';

export interface UsePermissionsResult {
  /** 权限列表 */
  permissions: ReturnType<typeof usePermissionStore>['permissions'];
  /** 角色列表 */
  roles: ReturnType<typeof useUserStore>['roles'];
  /** 是否为超级管理员 */
  isSuperAdmin: ReturnType<typeof useUserStore>['isSuperAdmin'];
  /** 检查权限 */
  hasPermission: (permission: string | string[]) => boolean;
  /** 检查角色 */
  hasRole: (role: string | string[]) => boolean;
  /** 检查多个权限(全部满足) */
  hasAllPermissions: (permissions: string[]) => boolean;
  /** 检查多个权限(任一满足) */
  hasAnyPermission: (permissions: string[]) => boolean;
}

export function usePermissions(): UsePermissionsResult {
  const permissionStore = usePermissionStore();
  const userStore = useUserStore();

  function hasPermission(permission: string | string[]): boolean {
    return permissionStore.hasPermission(permission);
  }

  function hasRole(role: string | string[]): boolean {
    return permissionStore.hasRole(role);
  }

  function hasAllPermissions(permissions: string[]): boolean {
    return permissions.every((p) => permissionStore.hasPermission(p));
  }

  function hasAnyPermission(permissions: string[]): boolean {
    return permissions.some((p) => permissionStore.hasPermission(p));
  }

  return {
    permissions: permissionStore.permissions,
    roles: userStore.roles,
    isSuperAdmin: userStore.isSuperAdmin,
    hasPermission,
    hasRole,
    hasAllPermissions,
    hasAnyPermission,
  };
}

export default usePermissions;
