/**
 * Auth Routes
 * 认证相关路由配置
 */

import type { RouteRecordRaw } from 'vue-router';

export const authRoutes: RouteRecordRaw[] = [
  {
    path: '/auth',
    name: 'Auth',
    component: () => import('@/layouts/BlankLayout.vue'),
    redirect: '/auth/login',
    meta: { requiresAuth: false },
    children: [
      {
        path: 'login',
        name: 'Login',
        component: () => import('@/views/auth/Login.vue'),
        meta: { title: '登录', requiresAuth: false },
      },
      {
        path: 'register',
        name: 'Register',
        component: () => import('@/views/auth/Register.vue'),
        meta: { title: '注册', requiresAuth: false },
      },
      {
        path: 'forgot-password',
        name: 'ForgotPassword',
        component: () => import('@/views/auth/ForgotPassword.vue'),
        meta: { title: '忘记密码', requiresAuth: false },
      },
    ],
  },
  {
    path: '/auth/select-tenant',
    name: 'SelectTenant',
    component: () => import('@/views/auth/SelectTenant.vue'),
    meta: { title: '选择租户', requiresAuth: true },
  },
];

export default authRoutes;
