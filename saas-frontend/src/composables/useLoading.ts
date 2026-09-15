/**
 * useLoading
 * 加载状态管理 Hook
 */

import { ref, type Ref } from 'vue';

export interface LoadingOptions {
  /** 初始状态 */
  initial?: boolean;
  /** 防抖延迟(ms) */
  debounce?: number;
}

export interface LoadingResult {
  /** 加载状态 */
  loading: Ref<boolean>;
  /** 开始加载 */
  start: () => void;
  /** 结束加载 */
  finish: () => void;
  /** 切换加载状态 */
  toggle: () => void;
  /** 带回调的执行函数 */
  wrap: <T>(fn: Promise<T>) => Promise<T>;
}

export function useLoading(options: LoadingOptions = {}): LoadingResult {
  const { initial = false } = options;

  const loading = ref(initial);
  let debounceTimer: ReturnType<typeof setTimeout> | null = null;

  function start(): void {
    if (debounceTimer) {
      clearTimeout(debounceTimer);
      debounceTimer = null;
    }
    loading.value = true;
  }

  function finish(): void {
    if (debounceTimer) {
      clearTimeout(debounceTimer);
      debounceTimer = null;
    }
    loading.value = false;
  }

  function toggle(): void {
    loading.value = !loading.value;
  }

  async function wrap<T>(fn: Promise<T>): Promise<T> {
    start();
    try {
      return await fn;
    } finally {
      finish();
    }
  }

  return {
    loading,
    start,
    finish,
    toggle,
    wrap,
  };
}

export default useLoading;
