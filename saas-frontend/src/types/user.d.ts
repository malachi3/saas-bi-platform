/**
 * User Types
 * 用户类型定义
 */

/** 用户基本信息 */
export interface UserInfo {
  id: number;
  username: string;
  nickname: string;
  email: string;
  phone: string;
  avatar?: string;
  gender?: number;
  status?: number;
  deptId?: number;
  deptName?: string;
  roles?: string[];
  permissions?: string[];
  remark?: string;
  createdAt?: string;
  updatedAt?: string;
}

/** 登录请求参数 */
export interface LoginParams {
  username: string;
  password: string;
  captcha?: string;
  captchaId?: string;
  /** 租户 ID */
  tenantId?: number | string;
}

/** 登录响应 */
export interface LoginResult {
  accessToken: string;
  refreshToken: string;
  expiresIn: number;
  tokenType: string;
}

/** 修改密码参数 */
export interface UpdatePasswordParams {
  oldPassword: string;
  newPassword: string;
  confirmPassword?: string;
}

/** 重置密码参数 */
export interface ResetPasswordParams {
  userId: number | string;
  newPassword: string;
}

/** 用户查询参数 */
export interface UserQueryParams {
  username?: string;
  nickname?: string;
  phone?: string;
  email?: string;
  status?: number;
  deptId?: number;
  roleId?: number;
  page?: number;
  pageSize?: number;
}

/** 创建/更新用户参数 */
export interface UserFormData {
  id?: number | string;
  username?: string;
  nickname: string;
  password?: string;
  email?: string;
  phone?: string;
  gender?: number;
  status?: number;
  deptId?: number;
  roleIds?: number[];
  remark?: string;
}

/** 用户状态枚举 */
export enum UserStatus {
  DISABLED = 0,
  ENABLED = 1,
}
