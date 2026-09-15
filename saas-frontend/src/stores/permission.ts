/**
 * Permission Store
 * 权限状态管理
 */

import { defineStore } from 'pinia';
import { ref, computed } from 'vue';
import type { RouteRecordRaw } from 'vue-router';
import type { MenuInfo } from '@/types/menu.d';
import { getMenuList } from '@/api/system/menu';
import { useUserStore } from './user';

export const usePermissionStore = defineStore('permission', () => {
  /** 动态路由列表 */
  const routes = ref<RouteRecordRaw[]>([]);

  /** 菜单树 */
  const menus = ref<MenuInfo[]>([]);

  /** 按钮权限列表 */
  const buttonPermissions = ref<string[]>([]);

  /** 是否已加载权限 */
  const isLoaded = ref(false);

  /** 获取用户按钮权限 */
  const permissions = computed(() => {
    const userStore = useUserStore();
    return userStore.permissions || buttonPermissions.value;
  });

  /**
   * 加载权限和菜单
   */
  async function loadPermissions(): Promise<void> {
    try {
      const menuList = await getMenuList();

      // 处理菜单数据
      menus.value = menuList;

      // 提取按钮权限
      const btnPerms: string[] = [];
      const filterPerms = (list: MenuInfo[]) => {
        list.forEach((item) => {
          if (item.perms && item.type === 2) {
            btnPerms.push(item.perms);
          }
          if (item.children?.length) {
            filterPerms(item.children);
          }
        });
      };
      filterPerms(menuList);
      buttonPermissions.value = btnPerms;

      // 生成路由
      routes.value = generateRoutes(menuList);

      isLoaded.value = true;
    } catch (error) {
      console.error('Failed to load permissions:', error);
      throw error;
    }
  }

  /**
   * 生成路由配置
   */
  function generateRoutes(menuList: MenuInfo[]): RouteRecordRaw[] {
    const result: RouteRecordRaw[] = [];

    const convertMenuToRoute = (menu: MenuInfo): RouteRecordRaw | null => {
      // 按钮类型不生成路由
      if (menu.type === 2) {
        return null;
      }

      const route: RouteRecordRaw = {
        path: menu.path,
        name: menu.name,
        meta: {
          title: menu.meta?.title || menu.name,
          icon: menu.meta?.icon || menu.icon,
          hidden: menu.meta?.hidden || menu.visible === 0,
          keepAlive: menu.meta?.keepAlive || menu.cacheable === 1,
          permission: menu.perms,
        },
      };

      // 设置组件
      if (menu.component) {
        // 布局组件
        if (menu.component === 'Layout') {
          route.component = () => import('@/layouts/DefaultLayout.vue');
        } else {
          route.component = () => import(`@/views/${menu.component}.vue`);
        }
      }

      // 处理子路由
      if (menu.children?.length) {
        const children = menu.children
          .filter((child) => child.type !== 2)
          .map((child) => convertMenuToRoute(child))
          .filter(Boolean) as RouteRecordRaw[];

        if (children.length > 0) {
          route.children = children;
        }

        // 如果有重定向
        if (menu.redirect && children.length > 0) {
          route.redirect = menu.redirect;
        }
      }

      return route;
    };

    // 过滤顶级目录和菜单
    menuList
      .filter((menu) => menu.type !== 2)
      .forEach((menu) => {
        const route = convertMenuToRoute(menu);
        if (route) {
          result.push(route);
        }
      });

    return result;
  }

  /**
   * 检查是否有指定权限
   */
  function hasPermission(permission: string | string[]): boolean {
    const userStore = useUserStore();

    // 超级管理员拥有所有权限
    if (userStore.isSuperAdmin) {
      return true;
    }

    if (Array.isArray(permission)) {
      return permission.some((p) => permissions.value.includes(p));
    }

    return permissions.value.includes(permission);
  }

  /**
   * 检查是否有指定角色
   */
  function hasRole(role: string | string[]): boolean {
    const userStore = useUserStore();

    if (userStore.isSuperAdmin) {
      return true;
    }

    if (Array.isArray(role)) {
      return role.some((r) => userStore.roles.includes(r));
    }

    return userStore.roles.includes(role);
  }

  /**
   * 清除权限信息
   */
  function clearPermissions(): void {
    routes.value = [];
    menus.value = [];
    buttonPermissions.value = [];
    isLoaded.value = false;
  }

  return {
    routes,
    menus,
    buttonPermissions,
    isLoaded,
    permissions,
    loadPermissions,
    hasPermission,
    hasRole,
    clearPermissions,
  };
});
