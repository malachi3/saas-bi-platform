/**
 * System Routes
 * 系统管理路由配置
 */

import type { RouteRecordRaw } from 'vue-router';

export const systemRoutes: RouteRecordRaw[] = [
  {
    path: '/system',
    name: 'System',
    component: () => import('@/layouts/DefaultLayout.vue'),
    redirect: '/system/users',
    meta: { title: '系统管理', icon: 'setting', requiresAuth: true },
    children: [
      {
        path: 'users',
        name: 'UserManagement',
        component: () => import('@/views/system/user/index.vue'),
        meta: {
          title: '用户管理',
          icon: 'user',
          requiresAuth: true,
          permission: 'system:user:list',
        },
      },
      {
        path: 'depts',
        name: 'DeptManagement',
        component: () => import('@/views/system/dept/index.vue'),
        meta: {
          title: '部门管理',
          icon: 'department',
          requiresAuth: true,
          permission: 'system:dept:list',
        },
      },
      {
        path: 'menus',
        name: 'MenuManagement',
        component: () => import('@/views/system/menu/index.vue'),
        meta: {
          title: '菜单管理',
          icon: 'menu',
          requiresAuth: true,
          permission: 'system:menu:list',
        },
      },
      {
        path: 'roles',
        name: 'RoleManagement',
        component: () => import('@/views/permission/role/index.vue'),
        meta: {
          title: '角色管理',
          icon: 'role',
          requiresAuth: true,
          permission: 'system:role:list',
        },
      },
    ],
  },
];

export default systemRoutes;
