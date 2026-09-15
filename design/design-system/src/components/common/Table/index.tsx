import React from 'react';
import { tokens, colors, spacing, typography, radius } from '@/tokens';
import type { CSSProperties } from 'react';

// ============================================
// Table 表格组件
// ============================================

export interface TableColumn<T = any> {
  title: string;
  dataIndex: keyof T | string;
  key: string;
  width?: number | string;
  align?: 'left' | 'center' | 'right';
  render?: (value: any, record: T, index: number) => React.ReactNode;
}

interface TableProps<T = any> {
  columns: TableColumn<T>[];
  dataSource: T[];
  rowKey?: keyof T | ((record: T) => string | number);
  loading?: boolean;
  emptyText?: string;
  onRow?: (record: T, index: number) => {
    onClick?: () => void;
    style?: CSSProperties;
  };
  className?: string;
}

export function Table<T extends Record<string, any>>({
  columns,
  dataSource,
  rowKey = 'id' as keyof T,
  loading = false,
  emptyText = '暂无数据',
  onRow,
  className = '',
}: TableProps<T>) {
  const getRowKey = (record: T, index: number): string => {
    if (typeof rowKey === 'function') {
      return String(rowKey(record));
    }
    return String(record[rowKey] ?? index);
  };

  const containerStyle: CSSProperties = {
    backgroundColor: colors.bg.base,
    borderRadius: radius.lg,
    border: `1px solid ${colors.border.secondary}`,
    overflow: 'hidden',
  };

  const tableStyle: CSSProperties = {
    width: '100%',
    borderCollapse: 'collapse',
  };

  const thStyle: CSSProperties = {
    padding: `${spacing[3]} ${spacing[4]}`,
    textAlign: 'left',
    fontSize: typography.fontSize.sm,
    fontWeight: typography.fontWeight.semibold,
    color: colors.text.secondary,
    backgroundColor: colors.bg.page,
    borderBottom: `1px solid ${colors.border.secondary}`,
  };

  const tdStyle: CSSProperties = {
    padding: `${spacing[3]} ${spacing[4]}`,
    fontSize: typography.fontSize.sm,
    color: colors.text.primary,
    borderBottom: `1px solid ${colors.border.secondary}`,
  };

  if (loading) {
    return (
      <div style={containerStyle} className={className}>
        <div style={{ padding: spacing[8], textAlign: 'center', color: colors.text.tertiary }}>
          加载中...
        </div>
      </div>
    );
  }

  if (dataSource.length === 0) {
    return (
      <div style={containerStyle} className={className}>
        <div style={{ padding: spacing[8], textAlign: 'center', color: colors.text.tertiary }}>
          {emptyText}
        </div>
      </div>
    );
  }

  return (
    <div style={containerStyle} className={className}>
      <table style={tableStyle}>
        <thead>
          <tr>
            {columns.map((col) => (
              <th
                key={col.key}
                style={{
                  ...thStyle,
                  width: col.width,
                  textAlign: col.align || 'left',
                }}
              >
                {col.title}
              </th>
            ))}
          </tr>
        </thead>
        <tbody>
          {dataSource.map((record, index) => {
            const rowProps = onRow?.(record, index);
            return (
              <tr
                key={getRowKey(record, index)}
                style={{
                  cursor: rowProps?.onClick ? 'pointer' : 'default',
                  transition: `background-color ${tokens.transitions.fast}`,
                  ...rowProps?.style,
                }}
                onClick={rowProps?.onClick}
                onMouseEnter={(e) => {
                  e.currentTarget.style.backgroundColor = colors.bg.hovered;
                }}
                onMouseLeave={(e) => {
                  e.currentTarget.style.backgroundColor = 'transparent';
                }}
              >
                {columns.map((col) => {
                  const value = record[col.dataIndex as keyof T];
                  return (
                    <td
                      key={col.key}
                      style={{
                        ...tdStyle,
                        textAlign: col.align || 'left',
                      }}
                    >
                      {col.render ? col.render(value, record, index) : value}
                    </td>
                  );
                })}
              </tr>
            );
          })}
        </tbody>
      </table>
    </div>
  );
}
