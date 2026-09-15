/**
 * Dictionary API
 * 字典管理 API
 */

import { http } from '../request';

/** 字典类型 */
export interface DictType {
  id: number;
  name: string;
  code: string;
  status: number;
  remark?: string;
  createdAt?: string;
}

/** 字典数据 */
export interface DictData {
  id: number;
  typeId: number;
  label: string;
  value: string;
  sort: number;
  status: number;
  remark?: string;
}

/** 获取字典类型列表 */
export function getDictTypes(): Promise<DictType[]> {
  return http.get<DictType[]>('/system/dicts/types');
}

/** 获取字典数据 */
export function getDictData(code: string): Promise<DictData[]> {
  return http.get<DictData[]>(`/system/dicts/data/${code}`);
}

/** 获取多个字典数据 */
export function getMultiDictData(codes: string[]): Promise<Record<string, DictData[]>> {
  return http.get<Record<string, DictData[]>>('/system/dicts/data/batch', { codes });
}

/** 获取所有字典数据(缓存) */
export function getAllDictData(): Promise<Record<string, DictData[]>> {
  return http.get<Record<string, DictData[]>>('/system/dicts/all');
}
