import type { PropType, CSSProperties } from 'vue';
import { tokens } from '../tokens';

export interface TableColumn<T = Record<string, unknown>> {
  /** 列字段名 */
  dataIndex: string;
  /** 列标题 */
  title: string;
  /** 列宽度 */
  width?: string | number;
  /** 对齐方式 */
  align?: 'left' | 'center' | 'right';
  /** 是否可排序 */
  sorter?: boolean;
  /** 是否可调整宽度 */
  resizable?: boolean;
  /** 自定义单元格渲染 */
  customRender?: (opts: { text: unknown; record: T; index: number }) => unknown;
  /** 自定义标题渲染 */
  titleRender?: (opts: { text: string; column: TableColumn<T> }) => unknown;
}

export interface TableProps<T = Record<string, unknown>> {
  /** 表格数据 */
  dataSource: T[];
  /** 列配置 */
  columns: TableColumn<T>[];
  /** 是否加载中 */
  loading?: boolean;
  /** 是否带边框 */
  bordered?: boolean;
  /** 是否显示斑马纹 */
  stripe?: boolean;
  /** 是否显示表头 */
  showHeader?: boolean;
  /** 行唯一标识字段 */
  rowKey?: string | ((record: T) => string | number);
  /** 分页配置 */
  pagination?: false | TablePagination;
  /** 行类名 */
  rowClassName?: string | ((record: T, index: number) => string);
  /** 点击行回调 */
  onRow?: (record: T, index: number) => { onClick?: (e: MouseEvent) => void };
  /** 选择配置 */
  rowSelection?: TableRowSelection<T>;
}

export interface TablePagination {
  current?: number;
  pageSize?: number;
  total?: number;
  pageSizeOptions?: string[];
  showSizeChanger?: boolean;
  showQuickJumper?: boolean;
  showTotal?: (total: number) => string;
  onChange?: (page: number, pageSize: number) => void;
}

export interface TableRowSelection<T> {
  selectedRowKeys?: (string | number)[];
  onChange?: (selectedRowKeys: (string | number)[], selectedRows: T[]) => void;
  onSelect?: (record: T, selected: boolean) => void;
  onSelectAll?: (selected: boolean, selectedRows: T[]) => void;
}

export const tableProps = {
  dataSource: {
    type: Array as PropType<TableProps['dataSource']>,
    default: () => [],
  },
  columns: {
    type: Array as PropType<TableProps['columns']>,
    default: () => [],
  },
  loading: Boolean,
  bordered: Boolean,
  stripe: Boolean,
  showHeader: {
    type: Boolean,
    default: true,
  },
  rowKey: {
    type: [String, Function] as PropType<TableProps['rowKey']>,
    default: 'id',
  },
  pagination: {
    type: [Boolean, Object] as PropType<TableProps['pagination']>,
    default: false,
  },
  rowSelection: Object as PropType<TableProps['rowSelection']>,
};

export function useTable<T>(props: TableProps<T>) {
  const tableStyles = computed<CSSProperties>(() => ({
    borderRadius: tokens.borderRadius.lg,
    overflow: 'hidden',
  }));

  const headerCellStyles = computed<CSSProperties>(() => ({
    backgroundColor: tokens.colors.bgLayout,
    color: tokens.colors.textSecondary,
    fontWeight: tokens.typography.fontWeight.medium,
    padding: tokens.spacing.md,
  }));

  const bodyCellStyles = computed<CSSProperties>(() => ({
    padding: tokens.spacing.md,
    borderTop: `1px solid ${tokens.colors.borderLight}`,
  }));

  return {
    tableStyles,
    headerCellStyles,
    bodyCellStyles,
  };
}

import { computed } from 'vue';
