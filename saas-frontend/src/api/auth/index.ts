/**
 * Auth API
 * 认证相关 API
 */

import { http } from '../request';
import type { LoginParams, LoginResult } from '@/types/user.d';
import type { UserInfo } from '@/types/user.d';

/** 用户登录 */
export function login(data: LoginParams): Promise<LoginResult> {
  return http.post<LoginResult>('/auth/login', data);
}

/** 获取刷新 Token */
export function refreshToken(refreshToken: string): Promise<LoginResult> {
  return http.post<LoginResult>('/auth/refresh', { refreshToken });
}

/** 用户登出 */
export function logout(): Promise<void> {
  return http.post<void>('/auth/logout');
}

/** 获取当前用户信息 */
export function getUserInfo(): Promise<UserInfo> {
  return http.get<UserInfo>('/auth/userinfo');
}

/** 获取图形验证码 */
export function getCaptcha(): Promise<{ id: string; image: string }> {
  return http.get<{ id: string; image: string }>('/auth/captcha');
}

/** 修改密码 */
export function updatePassword(data: {
  oldPassword: string;
  newPassword: string;
}): Promise<void> {
  return http.post<void>('/auth/password', data);
}

/** 重置密码 */
export function resetPassword(data: { userId: number; newPassword: string }): Promise<void> {
  return http.post<void>('/auth/password/reset', data);
}
