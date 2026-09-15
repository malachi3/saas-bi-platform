/**
 * Axios Request Wrapper
 * Axios 请求封装 - 包含请求/响应拦截器
 */

import axios, { type AxiosInstance, type AxiosRequestConfig, type AxiosResponse } from 'axios';
import type { ApiResponse } from '@/types/api.d';
import { useUserStore } from '@/stores/user';
import { useTenantStore } from '@/stores/tenant';
import { ElMessage } from 'element-plus';

// 创建 axios 实例
const request: AxiosInstance = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || '/api',
  timeout: 30000,
  headers: {
    'Content-Type': 'application/json',
  },
});

// 请求计数器 - 用于 loading 状态管理
let loadingRequestCount = 0;

/**
 * 显示全局 loading
 */
function showLoading(): void {
  loadingRequestCount++;
  // 可以在这里触发全局 loading
}

/**
 * 隐藏全局 loading
 */
function hideLoading(): void {
  loadingRequestCount--;
  if (loadingRequestCount <= 0) {
    loadingRequestCount = 0;
    // 可以在这里隐藏全局 loading
  }
}

/**
 * 请求拦截器
 */
request.interceptors.request.use(
  (config: AxiosRequestConfig) => {
    showLoading();

    // 携带 Token
    const userStore = useUserStore();
    if (userStore.token) {
      config.headers = config.headers || {};
      config.headers.Authorization = `Bearer ${userStore.token}`;
    }

    // 携带租户标识
    const tenantStore = useTenantStore();
    if (tenantStore.currentTenant) {
      config.headers = config.headers || {};
      config.headers['X-Tenant-ID'] = tenantStore.currentTenant.id;
    }

    // 附加时间戳防止缓存
    if (config.method === 'get') {
      config.params = {
        ...config.params,
        _t: Date.now(),
      };
    }

    return config;
  },
  (error) => {
    hideLoading();
    return Promise.reject(error);
  }
);

/**
 * 响应拦截器
 */
request.interceptors.response.use(
  (response: AxiosResponse<ApiResponse>) => {
    hideLoading();

    const { code, message, success } = response.data;

    // 业务错误处理
    if (success && code === 0) {
      return response;
    }

    // Token 过期
    if (code === 40101 || code === 40102) {
      ElMessage.error('登录已过期，请重新登录');
      useUserStore().logout();
      window.location.href = '/auth/login';
      return Promise.reject(new Error(message || '登录已过期'));
    }

    // 其他业务错误
    ElMessage.error(message || '请求失败');
    return Promise.reject(new Error(message || '请求失败'));
  },
  (error) => {
    hideLoading();

    // 网络错误处理
    if (!error.response) {
      if (error.code === 'ECONNABORTED') {
        ElMessage.error('请求超时，请稍后重试');
      } else {
        ElMessage.error('网络错误，请检查网络连接');
      }
      return Promise.reject(error);
    }

    const { status, data } = error.response;

    switch (status) {
      case 400:
        ElMessage.error(data?.message || '参数错误');
        break;
      case 401:
        ElMessage.error('未授权，请重新登录');
        useUserStore().logout();
        window.location.href = '/auth/login';
        break;
      case 403:
        ElMessage.error('拒绝访问，权限不足');
        break;
      case 404:
        ElMessage.error('请求资源不存在');
        break;
      case 500:
        ElMessage.error('服务器错误，请联系管理员');
        break;
      default:
        ElMessage.error(data?.message || '请求失败');
    }

    return Promise.reject(error);
  }
);

/**
 * 通用请求方法
 */
export const http = {
  get<T>(url: string, params?: Record<string, unknown>, config?: AxiosRequestConfig): Promise<T> {
    return request.get<ApiResponse<T>>(url, { params, ...config }).then((res) => res.data.data);
  },

  post<T>(url: string, data?: unknown, config?: AxiosRequestConfig): Promise<T> {
    return request.post<ApiResponse<T>>(url, data, config).then((res) => res.data.data);
  },

  put<T>(url: string, data?: unknown, config?: AxiosRequestConfig): Promise<T> {
    return request.put<ApiResponse<T>>(url, data, config).then((res) => res.data.data);
  },

  delete<T>(url: string, params?: Record<string, unknown>, config?: AxiosRequestConfig): Promise<T> {
    return request.delete<ApiResponse<T>>(url, { params, ...config }).then((res) => res.data.data);
  },

  patch<T>(url: string, data?: unknown, config?: AxiosRequestConfig): Promise<T> {
    return request.patch<ApiResponse<T>>(url, data, config).then((res) => res.data.data);
  },

  request<T>(config: AxiosRequestConfig): Promise<T> {
    return request<ApiResponse<T>>(config).then((res) => res.data.data);
  },
};

export default request;
