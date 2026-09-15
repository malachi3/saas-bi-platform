import React from 'react';
import { tokens, colors, spacing, radius, zIndex } from '@/tokens';
import type { CSSProperties } from 'react';

// ============================================
// Badge 徽标组件
// ============================================

export type BadgeStatus = 'success' | 'warning' | 'error' | 'default' | 'processing';
export type BadgeSize = 'small' | 'default';

interface BadgeProps {
  count?: number;
  status?: BadgeStatus;
  dot?: boolean;
  size?: BadgeSize;
  overflowCount?: number;
  showZero?: boolean;
  children?: React.ReactNode;
}

const statusColors: Record<BadgeStatus, string> = {
  success: colors.success[500],
  warning: colors.warning[500],
  error: colors.error[500],
  default: colors.neutral[500],
  processing: colors.primary[500],
};

export function Badge({
  count,
  status,
  dot = false,
  size = 'default',
  overflowCount = 99,
  showZero = false,
  children,
}: BadgeProps) {
  const displayCount = count !== undefined && count > overflowCount ? `${overflowCount}+` : count;
  const showBadge = status || dot || (count !== undefined && (showZero || count > 0));

  const badgeSize = size === 'small' ? 16 : 18;
  const dotSize = size === 'small' ? 6 : 8;

  if (!showBadge) {
    return <>{children}</>;
  }

  if (status || dot) {
    return (
      <span style={{ position: 'relative', display: 'inline-flex' }}>
        {children}
        <span
          style={{
            position: 'absolute',
            top: status ? '-2px' : 0,
            right: status ? '-2px' : 0,
            width: dot ? dotSize : badgeSize,
            height: dot ? dotSize : badgeSize,
            borderRadius: '50%',
            backgroundColor: status ? statusColors[status] : colors.error[500],
            animation: status === 'processing' ? 'badgePulse 1.2s infinite' : 'none',
          }}
        />
        <style>{`
          @keyframes badgePulse {
            0% { transform: scale(1); opacity: 1; }
            50% { transform: scale(1.2); opacity: 0.8; }
            100% { transform: scale(1); opacity: 1; }
          }
        `}</style>
      </span>
    );
  }

  const badgeStyle: CSSProperties = {
    display: 'inline-flex',
    alignItems: 'center',
    justifyContent: 'center',
    minWidth: badgeSize,
    height: badgeSize,
    padding: '0 6px',
    fontSize: size === 'small' ? tokens.typography.fontSize.xs : tokens.typography.fontSize.sm,
    fontWeight: tokens.typography.fontWeight.medium,
    color: '#fff',
    backgroundColor: colors.error[500],
    borderRadius: badgeSize / 2,
    zIndex: zIndex.dropdown,
  };

  if (!children) {
    return <span style={badgeStyle}>{displayCount}</span>;
  }

  return (
    <span style={{ position: 'relative', display: 'inline-flex' }}>
      {children}
      <span style={{ ...badgeStyle, position: 'absolute', top: '-6px', right: '-10px' }}>
        {displayCount}
      </span>
    </span>
  );
}
