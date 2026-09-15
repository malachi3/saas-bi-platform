/**
 * Department API
 * 部门管理 API
 */

import { http } from '../request';
import type { MenuInfo } from '@/types/menu.d';

/** 部门树结构 */
export interface DeptTree {
  id: number;
  parentId: number;
  name: string;
  sort: number;
  status: number;
  children?: DeptTree[];
}

/** 部门查询参数 */
export interface DeptQueryParams {
  name?: string;
  status?: number;
}

/** 部门表单数据 */
export interface DeptFormData {
  id?: number;
  parentId: number;
  name: string;
  sort?: number;
  leader?: string;
  phone?: string;
  email?: string;
  status?: number;
  remark?: string;
}

/** 获取部门树 */
export function getDeptTree(params?: DeptQueryParams): Promise<DeptTree[]> {
  return http.get<DeptTree[]>('/system/depts/tree', params);
}

/** 获取部门详情 */
export function getDeptById(id: number): Promise<DeptTree> {
  return http.get<DeptTree>(`/system/depts/${id}`);
}

/** 创建部门 */
export function createDept(data: DeptFormData): Promise<DeptTree> {
  return http.post<DeptTree>('/system/depts', data);
}

/** 更新部门 */
export function updateDept(id: number, data: DeptFormData): Promise<DeptTree> {
  return http.put<DeptTree>(`/system/depts/${id}`, data);
}

/** 删除部门 */
export function deleteDept(id: number): Promise<void> {
  return http.delete<void>(`/system/depts/${id}`);
}
