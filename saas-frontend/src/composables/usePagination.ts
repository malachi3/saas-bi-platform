/**
 * usePagination
 * 分页逻辑 Hook
 */

import { ref, computed, type Ref } from 'vue';

export interface PaginationOptions {
  /** 当前页码 */
  page?: number;
  /** 每页条数 */
  pageSize?: number;
  /** 总数 */
  total?: number;
  /** 变化回调 */
  onChange?: (page: number, pageSize: number) => void;
}

export interface PaginationResult {
  /** 当前页码 */
  current: Ref<number>;
  /** 每页条数 */
  pageSize: Ref<number>;
  /** 总数 */
  total: Ref<number>;
  /** 总页数 */
  totalPages: ComputedRef<number>;
  /** 是否为第一页 */
  isFirstPage: ComputedRef<boolean>;
  /** 是否为最后一页 */
  isLastPage: ComputedRef<boolean>;
  /** 分页配置 */
  pagination: ComputedRef<PaginationOptions>;
  /** 切换页码 */
  setPage: (page: number) => void;
  /** 切换每页条数 */
  setPageSize: (size: number) => void;
  /** 重置分页 */
  reset: () => void;
}

export function usePagination(options: PaginationOptions = {}): PaginationResult {
  const { page = 1, pageSize = 10, total = 0, onChange } = options;

  const current = ref(page);
  const pageSizeRef = ref(pageSize);
  const totalRef = ref(total);

  const totalPages = computed(() => Math.ceil(totalRef.value / pageSizeRef.value));

  const isFirstPage = computed(() => current.value === 1);

  const isLastPage = computed(() => current.value >= totalPages.value);

  const pagination = computed(() => ({
    current: current.value,
    pageSize: pageSizeRef.value,
    total: totalRef.value,
    onChange: (page: number, size: number) => {
      current.value = page;
      pageSizeRef.value = size;
      onChange?.(page, size);
    },
  }));

  function setPage(page: number): void {
    if (page < 1) page = 1;
    if (page > totalPages.value) page = totalPages.value;
    current.value = page;
    onChange?.(current.value, pageSizeRef.value);
  }

  function setPageSize(size: number): void {
    pageSizeRef.value = size;
    current.value = 1;
    onChange?.(current.value, size);
  }

  function reset(): void {
    current.value = 1;
    pageSizeRef.value = pageSize;
    totalRef.value = 0;
  }

  return {
    current,
    pageSize: pageSizeRef,
    total: totalRef,
    totalPages,
    isFirstPage,
    isLastPage,
    pagination,
    setPage,
    setPageSize,
    reset,
  };
}

export default usePagination;
