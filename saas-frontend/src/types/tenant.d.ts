/**
 * Tenant Types
 * 租户类型定义
 */

/** 租户信息 */
export interface TenantInfo {
  id: number;
  name: string;
  code: string;
  type: TenantType;
  status: number;
  packageId?: number;
  packageName?: string;
  expireTime?: string;
  userCount?: number;
  userLimit?: number;
  logo?: string;
  contact?: string;
  phone?: string;
  email?: string;
  remark?: string;
  createdAt?: string;
  updatedAt?: string;
}

/** 租户类型 */
export enum TenantType {
  /** 共享 Schema - 普通租户 */
  SHARED = 'SHARED',
  /** 独立 Schema - 中型客户 */
  SCHEMA = 'SCHEMA',
  /** 独立数据库 - 大客户 */
  DATABASE = 'DATABASE',
}

/** 租户状态 */
export enum TenantStatus {
  INACTIVE = 0,
  ACTIVE = 1,
  EXPIRED = 2,
  DISABLED = -1,
}

/** 租户查询参数 */
export interface TenantQueryParams {
  name?: string;
  code?: string;
  type?: TenantType;
  status?: number;
  page?: number;
  pageSize?: number;
}

/** 创建/更新租户参数 */
export interface TenantFormData {
  id?: number | string;
  name: string;
  code: string;
  type: TenantType;
  packageId?: number;
  expireTime?: string;
  logo?: string;
  contact?: string;
  phone?: string;
  email?: string;
  remark?: string;
}

/** 租户套餐信息 */
export interface TenantPackage {
  id: number;
  name: string;
  code: string;
  type: TenantType;
  price: number;
  period: number;
  userLimit: number;
  features: string[];
  status: number;
}

/** 当前租户上下文 */
export interface TenantContext {
  id: number;
  name: string;
  code: string;
  type: TenantType;
  expireTime?: string;
}
