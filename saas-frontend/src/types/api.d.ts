/**
 * API Response Types
 * API 统一响应类型定义
 */

/**
 * 通用 API 响应结构
 */
export interface ApiResponse<T = unknown> {
  code: number;
  data: T;
  message: string;
  success: boolean;
  timestamp: number;
}

/**
 * 分页响应结构
 */
export interface ApiPageResponse<T = unknown> {
  code: number;
  data: {
    list: T[];
    total: number;
    page: number;
    pageSize: number;
  };
  message: string;
  success: boolean;
  timestamp: number;
}

/**
 * 分页请求参数
 */
export interface PageParams {
  page?: number;
  pageSize?: number;
  /** 其他筛选字段 */
  [key: string]: unknown;
}

/**
 * 通用 ID 请求参数
 */
export interface IdParam {
  id: number | string;
}

/**
 * 通用批量 ID 请求参数
 */
export interface BatchIdParam {
  ids: (number | string)[];
}

/**
 * 批量操作请求
 */
export interface BatchOperationReq {
  ids: (number | string)[];
}

/**
 * 通用状态更新请求
 */
export interface StatusUpdateReq {
  id: number | string;
  status: number | string;
}
