/**
 * Routes Index
 * 路由配置汇总
 */

import type { RouteRecordRaw } from 'vue-router';
import { authRoutes } from './auth';
import { dashboardRoutes } from './dashboard';
import { systemRoutes } from './system';

/** 静态路由 */
export const staticRoutes: RouteRecordRaw[] = [
  ...authRoutes,
  ...dashboardRoutes,
  ...systemRoutes,

  // 错误页面
  {
    path: '/403',
    name: 'Forbidden',
    component: () => import('@/views/error/403.vue'),
    meta: { title: '无权限', requiresAuth: false },
  },
  {
    path: '/404',
    name: 'NotFound',
    component: () => import('@/views/error/404.vue'),
    meta: { title: '页面不存在', requiresAuth: false },
  },
  {
    path: '/:pathMatch(.*)*',
    redirect: '/404',
  },
];

export { authRoutes, dashboardRoutes, systemRoutes };
