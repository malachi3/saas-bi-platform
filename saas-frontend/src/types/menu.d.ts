/**
 * Menu Types
 * 菜单类型定义
 */

/** 菜单信息 */
export interface MenuInfo {
  id: number;
  parentId: number;
  name: string;
  path: string;
  component?: string;
  redirect?: string;
  type: MenuType;
  icon?: string;
  sort: number;
  status: number;
  visible: number;
  perms?: string;
  cacheable: number;
  affix: number;
  breadcrumb: number;
  keepAlive?: boolean;
  children?: MenuInfo[];
  meta?: MenuMeta;
  createdAt?: string;
  updatedAt?: string;
}

/** 菜单类型 */
export enum MenuType {
  /** 目录 */
  CATALOG = 0,
  /** 菜单 */
  MENU = 1,
  /** 按钮 */
  BUTTON = 2,
}

/** 菜单可见性 */
export enum MenuVisible {
  /** 隐藏 */
  HIDDEN = 0,
  /** 显示 */
  VISIBLE = 1,
}

/** 菜单缓存 */
export enum MenuCacheable {
  /** 不缓存 */
  NO_CACHE = 0,
  /** 缓存 */
  CACHE = 1,
}

/** 菜单元信息 */
export interface MenuMeta {
  title: string;
  icon?: string;
  hidden?: boolean;
  fixed?: boolean;
  keepAlive?: boolean;
  permission?: string | string[];
  requiresAuth?: boolean;
}

/** 菜单查询参数 */
export interface MenuQueryParams {
  name?: string;
  type?: MenuType;
  status?: number;
  visible?: number;
}

/** 创建/更新菜单参数 */
export interface MenuFormData {
  id?: number | string;
  parentId: number;
  name: string;
  path: string;
  component?: string;
  redirect?: string;
  type: MenuType;
  icon?: string;
  sort?: number;
  status?: number;
  visible?: number;
  perms?: string;
  cacheable?: number;
}

/** 角色信息 */
export interface RoleInfo {
  id: number;
  name: string;
  code: string;
  sort: number;
  dataScope: DataScope;
  status: number;
  remark?: string;
  createdAt?: string;
  updatedAt?: string;
}

/** 数据权限范围 */
export enum DataScope {
  /** 全部数据权限 */
  ALL = 1,
  /** 自定义数据权限 */
  CUSTOM = 2,
  /** 本部门数据权限 */
  DEPT = 3,
  /** 本部门及以下数据权限 */
  DEPT_AND_CHILD = 4,
  /** 仅本人数据权限 */
  SELF = 5,
}

/** 角色查询参数 */
export interface RoleQueryParams {
  name?: string;
  code?: string;
  status?: number;
  page?: number;
  pageSize?: number;
}

/** 创建/更新角色参数 */
export interface RoleFormData {
  id?: number | string;
  name: string;
  code: string;
  sort?: number;
  dataScope?: DataScope;
  status?: number;
  roleIds?: number[];
  menuIds?: number[];
  remark?: string;
}

/** 角色状态 */
export enum RoleStatus {
  DISABLED = 0,
  ENABLED = 1,
}
