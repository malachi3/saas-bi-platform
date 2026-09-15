import React from 'react';
import { tokens, colors, spacing, typography, radius, shadows, transitions } from '@/tokens';
import type { CSSProperties } from 'react';

// ============================================
// Button 按钮组件
// ============================================

export type ButtonVariant = 'primary' | 'secondary' | 'ghost' | 'danger';
export type ButtonSize = 'sm' | 'md' | 'lg';

interface ButtonProps {
  variant?: ButtonVariant;
  size?: ButtonSize;
  disabled?: boolean;
  loading?: boolean;
  fullWidth?: boolean;
  leftIcon?: React.ReactNode;
  rightIcon?: React.ReactNode;
  children: React.ReactNode;
  onClick?: (e: React.MouseEvent<HTMLButtonElement>) => void;
  type?: 'button' | 'submit' | 'reset';
  className?: string;
}

const variantStyles: Record<ButtonVariant, { bg: string; color: string; border: string; hoverBg: string }> = {
  primary: {
    bg: colors.primary[500],
    color: '#fff',
    border: 'transparent',
    hoverBg: colors.primary[600],
  },
  secondary: {
    bg: colors.bg.base,
    color: colors.text.primary,
    border: colors.border.primary,
    hoverBg: colors.bg.hovered,
  },
  ghost: {
    bg: 'transparent',
    color: colors.text.primary,
    border: 'transparent',
    hoverBg: colors.bg.hovered,
  },
  danger: {
    bg: colors.error[500],
    color: '#fff',
    border: 'transparent',
    hoverBg: colors.error[600],
  },
};

const sizeStyles: Record<ButtonSize, { height: number; paddingH: number; fontSize: string }> = {
  sm: { height: 28, paddingH: 12, fontSize: typography.fontSize.xs },
  md: { height: 36, paddingH: 16, fontSize: typography.fontSize.sm },
  lg: { height: 44, paddingH: 20, fontSize: typography.fontSize.base },
};

export function Button({
  variant = 'primary',
  size = 'md',
  disabled = false,
  loading = false,
  fullWidth = false,
  leftIcon,
  rightIcon,
  children,
  onClick,
  type = 'button',
  className = '',
}: ButtonProps) {
  const variantStyle = variantStyles[variant];
  const sizeStyle = sizeStyles[size];

  const style: CSSProperties = {
    display: 'inline-flex',
    alignItems: 'center',
    justifyContent: 'center',
    gap: spacing[2],
    height: sizeStyle.height,
    padding: `0 ${sizeStyle.paddingH}px`,
    fontSize: sizeStyle.fontSize,
    fontWeight: typography.fontWeight.medium,
    borderRadius: radius.md,
    border: `1px solid ${variantStyle.border}`,
    backgroundColor: disabled ? colors.bg.disabled : variantStyle.bg,
    color: disabled ? colors.text.disabled : variantStyle.color,
    cursor: disabled || loading ? 'not-allowed' : 'pointer',
    opacity: loading ? 0.7 : 1,
    transition: `all ${transitions.fast} ${transitions.easeInOut}`,
    width: fullWidth ? '100%' : 'auto',
    outline: 'none',
    userSelect: 'none',
  };

  return (
    <button
      type={type}
      style={style}
      disabled={disabled || loading}
      onClick={onClick}
      className={className}
      onMouseEnter={(e) => {
        if (!disabled && !loading) {
          e.currentTarget.style.backgroundColor = variantStyle.hoverBg;
        }
      }}
      onMouseLeave={(e) => {
        if (!disabled && !loading) {
          e.currentTarget.style.backgroundColor = variantStyle.bg;
        }
      }}
    >
      {loading && <LoadingSpinner size={size === 'sm' ? 12 : 16} />}
      {!loading && leftIcon}
      {children}
      {!loading && rightIcon}
    </button>
  );
}

// Loading 指示器
function LoadingSpinner({ size = 16 }: { size?: number }) {
  return (
    <svg
      width={size}
      height={size}
      viewBox="0 0 24 24"
      fill="none"
      style={{ animation: 'spin 1s linear infinite' }}
    >
      <style>{`@keyframes spin { from { transform: rotate(0deg); } to { transform: rotate(360deg); } }`}</style>
      <circle cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="3" strokeLinecap="round" strokeDasharray="31.4 31.4" />
    </svg>
  );
}
