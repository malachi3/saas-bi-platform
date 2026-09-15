/**
 * Menu API
 * 菜单管理 API
 */

import { http } from '../request';
import type { MenuInfo, MenuQueryParams, MenuFormData } from '@/types/menu.d';

/** 获取菜单列表 */
export function getMenuList(params?: MenuQueryParams): Promise<MenuInfo[]> {
  return http.get<MenuInfo[]>('/system/menus', params);
}

/** 获取菜单树 */
export function getMenuTree(): Promise<MenuInfo[]> {
  return http.get<MenuInfo[]>('/system/menus/tree');
}

/** 获取菜单详情 */
export function getMenuById(id: number): Promise<MenuInfo> {
  return http.get<MenuInfo>(`/system/menus/${id}`);
}

/** 创建菜单 */
export function createMenu(data: MenuFormData): Promise<MenuInfo> {
  return http.post<MenuInfo>('/system/menus', data);
}

/** 更新菜单 */
export function updateMenu(id: number, data: MenuFormData): Promise<MenuInfo> {
  return http.put<MenuInfo>(`/system/menus/${id}`, data);
}

/** 删除菜单 */
export function deleteMenu(id: number): Promise<void> {
  return http.delete<void>(`/system/menus/${id}`);
}

/** 获取角色菜单权限 */
export function getRoleMenus(roleId: number): Promise<number[]> {
  return http.get<number[]>(`/system/roles/${roleId}/menus`);
}

/** 分配角色菜单权限 */
export function assignRoleMenus(roleId: number, menuIds: number[]): Promise<void> {
  return http.post<void>(`/system/roles/${roleId}/menus`, { menuIds });
}
