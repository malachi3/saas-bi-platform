import React from 'react';
import { tokens, colors, spacing, radius, shadows, zIndex, transitions } from '@/tokens';
import { Button } from '@/components/common/Button';
import type { CSSProperties } from 'react';

// ============================================
// Modal 模态框组件
// ============================================

interface ModalProps {
  open: boolean;
  title?: React.ReactNode;
  width?: number | string;
  footer?: React.ReactNode;
  closable?: boolean;
  maskClosable?: boolean;
  onClose: () => void;
  onOk?: () => void;
  okText?: string;
  cancelText?: string;
  children: React.ReactNode;
}

export function Modal({
  open,
  title,
  width = 520,
  footer,
  closable = true,
  maskClosable = true,
  onClose,
  onOk,
  okText = '确定',
  cancelText = '取消',
  children,
}: ModalProps) {
  if (!open) return null;

  const handleMaskClick = (e: React.MouseEvent) => {
    if (e.target === e.currentTarget && maskClosable) {
      onClose();
    }
  };

  return (
    <div style={overlayStyle} onClick={handleMaskClick}>
      <div style={{ ...dialogStyle, width }}>
        {/* Header */}
        {title && (
          <div style={headerStyle}>
            <span style={titleStyle}>{title}</span>
            {closable && (
              <button style={closeBtnStyle} onClick={onClose}>
                ✕
              </button>
            )}
          </div>
        )}

        {/* Body */}
        <div style={bodyStyle}>{children}</div>

        {/* Footer */}
        {footer !== undefined ? (
          footer
        ) : onOk ? (
          <div style={footerStyle}>
            <Button variant="secondary" onClick={onClose}>
              {cancelText}
            </Button>
            <Button variant="primary" onClick={onOk}>
              {okText}
            </Button>
          </div>
        ) : null}
      </div>
    </div>
  );
}

const overlayStyle: CSSProperties = {
  position: 'fixed',
  top: 0,
  left: 0,
  right: 0,
  bottom: 0,
  backgroundColor: 'rgba(0, 0, 0, 0.45)',
  display: 'flex',
  alignItems: 'center',
  justifyContent: 'center',
  zIndex: zIndex.modalBackdrop,
  animation: 'fadeIn 200ms ease-out',
};

const dialogStyle: CSSProperties = {
  backgroundColor: colors.bg.base,
  borderRadius: radius.lg,
  boxShadow: shadows['2xl'],
  maxHeight: '85vh',
  display: 'flex',
  flexDirection: 'column',
  animation: 'slideUp 200ms ease-out',
};

const headerStyle: CSSProperties = {
  display: 'flex',
  alignItems: 'center',
  justifyContent: 'space-between',
  padding: `${spacing[4]} ${spacing[6]}`,
  borderBottom: `1px solid ${colors.border.secondary}`,
};

const titleStyle: CSSProperties = {
  fontSize: tokens.typography.fontSize.lg,
  fontWeight: tokens.typography.fontWeight.semibold,
  color: colors.text.primary,
};

const closeBtnStyle: CSSProperties = {
  width: '32px',
  height: '32px',
  display: 'flex',
  alignItems: 'center',
  justifyContent: 'center',
  border: 'none',
  background: 'transparent',
  cursor: 'pointer',
  borderRadius: radius.sm,
  color: colors.text.tertiary,
  fontSize: '16px',
};

const bodyStyle: CSSProperties = {
  padding: spacing[6],
  overflowY: 'auto',
  flex: 1,
};

const footerStyle: CSSProperties = {
  display: 'flex',
  alignItems: 'center',
  justifyContent: 'flex-end',
  gap: spacing[2],
  padding: `${spacing[4]} ${spacing[6]}`,
  borderTop: `1px solid ${colors.border.secondary}`,
};

// 全局动画样式
const globalStyles = `
  @keyframes fadeIn {
    from { opacity: 0; }
    to { opacity: 1; }
  }
  @keyframes slideUp {
    from { opacity: 0; transform: translateY(20px); }
    to { opacity: 1; transform: translateY(0); }
  }
`;

// 导出动画样式（需在入口文件引入）
export const modalStyles = globalStyles;
