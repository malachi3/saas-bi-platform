/**
 * Dashboard Routes
 * 仪表盘路由配置
 */

import type { RouteRecordRaw } from 'vue-router';

export const dashboardRoutes: RouteRecordRaw[] = [
  {
    path: '/',
    name: 'Dashboard',
    component: () => import('@/layouts/DefaultLayout.vue'),
    redirect: '/dashboard',
    meta: { title: '首页', icon: 'home', requiresAuth: true },
    children: [
      {
        path: 'dashboard',
        name: 'DashboardIndex',
        component: () => import('@/views/dashboard/index.vue'),
        meta: { title: '工作台', icon: 'dashboard', requiresAuth: true },
      },
    ],
  },
];

export default dashboardRoutes;
