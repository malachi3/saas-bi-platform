/**
 * User API
 * 用户管理 API
 */

import { http } from '../request';
import type { UserQueryParams, UserFormData, UserInfo } from '@/types/user.d';
import type { ApiPageResponse } from '@/types/api.d';

/** 获取用户列表 */
export function getUserList(params: UserQueryParams): Promise<ApiPageResponse<UserInfo>> {
  return http.get<ApiPageResponse<UserInfo>>('/system/users', params);
}

/** 获取用户详情 */
export function getUserById(id: number): Promise<UserInfo> {
  return http.get<UserInfo>(`/system/users/${id}`);
}

/** 创建用户 */
export function createUser(data: UserFormData): Promise<UserInfo> {
  return http.post<UserInfo>('/system/users', data);
}

/** 更新用户 */
export function updateUser(id: number, data: UserFormData): Promise<UserInfo> {
  return http.put<UserInfo>(`/system/users/${id}`, data);
}

/** 删除用户 */
export function deleteUser(id: number): Promise<void> {
  return http.delete<void>(`/system/users/${id}`);
}

/** 批量删除用户 */
export function batchDeleteUsers(ids: number[]): Promise<void> {
  return http.delete<void>('/system/users/batch', { ids });
}

/** 修改用户状态 */
export function updateUserStatus(id: number, status: number): Promise<void> {
  return http.patch<void>(`/system/users/${id}/status`, { status });
}

/** 重置用户密码 */
export function resetUserPassword(id: number, newPassword: string): Promise<void> {
  return http.post<void>(`/system/users/${id}/password/reset`, { newPassword });
}

/** 导出用户 */
export function exportUsers(params: UserQueryParams): Promise<Blob> {
  return http.request({
    url: '/system/users/export',
    method: 'get',
    params,
    responseType: 'blob',
  });
}
