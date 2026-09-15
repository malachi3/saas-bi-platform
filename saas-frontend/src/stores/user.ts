/**
 * User Store
 * 用户状态管理
 */

import { defineStore } from 'pinia';
import { ref, computed } from 'vue';
import type { UserInfo, LoginParams, LoginResult } from '@/types/user.d';
import { login as loginApi, logout as logoutApi, getUserInfo as getUserInfoApi } from '@/api/auth';
import { useTenantStore } from './tenant';
import { usePermissionStore } from './permission';
import router from '@/router';

export const useUserStore = defineStore('user', () => {
  /** 访问令牌 */
  const token = ref<string>('');

  /** 用户信息 */
  const userInfo = ref<UserInfo | null>(null);

  /** 登录状态 */
  const isLoggedIn = computed(() => !!token.value);

  /** 获取用户权限列表 */
  const permissions = computed(() => userInfo.value?.permissions || []);

  /** 获取用户角色列表 */
  const roles = computed(() => userInfo.value?.roles || []);

  /** 是否为超级管理员 */
  const isSuperAdmin = computed(() => roles.value.includes('super_admin') || roles.value.includes('admin'));

  /** 设置 Token */
  function setToken(newToken: string): void {
    token.value = newToken;
    localStorage.setItem('access_token', newToken);
  }

  /** 清除 Token */
  function clearToken(): void {
    token.value = '';
    localStorage.removeItem('access_token');
  }

  /** 设置用户信息 */
  function setUserInfo(info: UserInfo | null): void {
    userInfo.value = info;
    if (info) {
      sessionStorage.setItem('user_info', JSON.stringify(info));
    } else {
      sessionStorage.removeItem('user_info');
    }
  }

  /** 用户登录 */
  async function login(params: LoginParams): Promise<LoginResult> {
    try {
      const result = await loginApi(params);
      setToken(result.accessToken);

      // 获取用户信息
      const info = await getUserInfoApi();
      setUserInfo(info);

      // 加载权限和路由
      const permissionStore = usePermissionStore();
      await permissionStore.loadPermissions();

      return result;
    } catch (error) {
      clearToken();
      throw error;
    }
  }

  /** 用户登出 */
  async function logout(): Promise<void> {
    try {
      await logoutApi();
    } catch {
      // 即使 API 调用失败也清除本地状态
    } finally {
      clearToken();
      setUserInfo(null);
      const tenantStore = useTenantStore();
      tenantStore.clearTenant();
      const permissionStore = usePermissionStore();
      permissionStore.clearPermissions();
      router.push('/auth/login');
    }
  }

  /** 获取用户信息 */
  async function fetchUserInfo(): Promise<UserInfo> {
    try {
      const info = await getUserInfoApi();
      setUserInfo(info);
      return info;
    } catch (error) {
      clearToken();
      setUserInfo(null);
      throw error;
    }
  }

  /** 从本地存储恢复状态 */
  function restoreState(): boolean {
    const savedToken = localStorage.getItem('access_token');
    const savedUserInfo = sessionStorage.getItem('user_info');

    if (savedToken) {
      token.value = savedToken;
    }

    if (savedUserInfo) {
      try {
        userInfo.value = JSON.parse(savedUserInfo);
      } catch {
        sessionStorage.removeItem('user_info');
      }
    }

    return !!savedToken;
  }

  return {
    token,
    userInfo,
    isLoggedIn,
    permissions,
    roles,
    isSuperAdmin,
    setToken,
    setUserInfo,
    login,
    logout,
    fetchUserInfo,
    restoreState,
  };
});
