/**
 * Tenant API
 * 租户管理 API
 */

import { http } from '../request';
import type { TenantInfo, TenantQueryParams, TenantFormData, TenantPackage } from '@/types/tenant.d';
import type { ApiPageResponse } from '@/types/api.d';

/** 获取租户列表 */
export function getTenantList(params: TenantQueryParams): Promise<ApiPageResponse<TenantInfo>> {
  return http.get<ApiPageResponse<TenantInfo>>('/tenant/tenants', params);
}

/** 获取租户详情 */
export function getTenantById(id: number): Promise<TenantInfo> {
  return http.get<TenantInfo>(`/tenant/tenants/${id}`);
}

/** 创建租户 */
export function createTenant(data: TenantFormData): Promise<TenantInfo> {
  return http.post<TenantInfo>('/tenant/tenants', data);
}

/** 更新租户 */
export function updateTenant(id: number, data: TenantFormData): Promise<TenantInfo> {
  return http.put<TenantInfo>(`/tenant/tenants/${id}`, data);
}

/** 删除租户 */
export function deleteTenant(id: number): Promise<void> {
  return http.delete<void>(`/tenant/tenants/${id}`);
}

/** 修改租户状态 */
export function updateTenantStatus(id: number, status: number): Promise<void> {
  return http.patch<void>(`/tenant/tenants/${id}/status`, { status });
}

/** 获取租户套餐列表 */
export function getTenantPackages(): Promise<TenantPackage[]> {
  return http.get<TenantPackage[]>('/tenant/packages');
}

/** 获取可选租户列表(用于切换) */
export function getSelectableTenants(): Promise<TenantInfo[]> {
  return http.get<TenantInfo[]>('/tenant/select');
}

/** 切换当前租户 */
export function switchTenant(tenantId: number): Promise<void> {
  return http.post<void>('/tenant/switch', { tenantId });
}
