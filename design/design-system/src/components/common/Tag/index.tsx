import React from 'react';
import { tokens, colors, spacing, radius } from '@/tokens';
import type { CSSProperties } from 'react';

// ============================================
// Tag 标签组件
// ============================================

export type TagVariant = 'default' | 'primary' | 'success' | 'warning' | 'error';

interface TagProps {
  variant?: TagVariant;
  closable?: boolean;
  onClose?: () => void;
  children: React.ReactNode;
  onClick?: () => void;
  className?: string;
}

const variantStyles: Record<TagVariant, { bg: string; color: string; border: string }> = {
  default: {
    bg: colors.bg.page,
    color: colors.text.secondary,
    border: colors.border.primary,
  },
  primary: {
    bg: colors.primary[50],
    color: colors.primary[600],
    border: colors.primary[200],
  },
  success: {
    bg: colors.success[50],
    color: colors.success[600],
    border: colors.success[200],
  },
  warning: {
    bg: colors.warning[50],
    color: colors.warning[600],
    border: colors.warning[200],
  },
  error: {
    bg: colors.error[50],
    color: colors.error[600],
    border: colors.error[200],
  },
};

export function Tag({
  variant = 'default',
  closable = false,
  onClose,
  children,
  onClick,
  className = '',
}: TagProps) {
  const [isHovered, setIsHovered] = React.useState(false);
  const style = variantStyles[variant];

  const tagStyle: CSSProperties = {
    display: 'inline-flex',
    alignItems: 'center',
    gap: spacing[1],
    padding: `${spacing[1]} ${spacing[2]}`,
    fontSize: tokens.typography.fontSize.xs,
    fontWeight: tokens.typography.fontWeight.medium,
    backgroundColor: isHovered ? style.bg : style.bg,
    color: style.color,
    border: `1px solid ${style.border}`,
    borderRadius: radius.sm,
    cursor: onClick ? 'pointer' : 'default',
    transition: `all ${tokens.transitions.fast} ${tokens.transitions.easeInOut}`,
    userSelect: 'none',
  };

  return (
    <span
      style={tagStyle}
      className={className}
      onClick={onClick}
      onMouseEnter={() => setIsHovered(true)}
      onMouseLeave={() => setIsHovered(false)}
    >
      {children}
      {closable && (
        <span
          onClick={(e) => {
            e.stopPropagation();
            onClose?.();
          }}
          style={{
            cursor: 'pointer',
            marginLeft: spacing[1],
            opacity: 0.6,
          }}
        >
          ✕
        </span>
      )}
    </span>
  );
}
