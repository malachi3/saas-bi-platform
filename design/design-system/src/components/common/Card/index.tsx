import React from 'react';
import { tokens, colors, spacing, typography, radius, shadows } from '@/tokens';
import type { CSSProperties } from 'react';

// ============================================
// Card 卡片组件
// ============================================

interface CardProps {
  title?: React.ReactNode;
  extra?: React.ReactNode;
  bordered?: boolean;
  hoverable?: boolean;
  loading?: boolean;
  children: React.ReactNode;
  className?: string;
  onClick?: () => void;
  style?: CSSProperties;
}

export function Card({
  title,
  extra,
  bordered = true,
  hoverable = false,
  loading = false,
  children,
  className = '',
  onClick,
  style = {},
}: CardProps) {
  const [isHovered, setIsHovered] = React.useState(false);

  const cardStyle: CSSProperties = {
    backgroundColor: colors.bg.base,
    border: bordered ? `1px solid ${colors.border.secondary}` : 'none',
    borderRadius: radius.lg,
    boxShadow: hoverable && isHovered ? shadows.md : shadows.sm,
    transition: 'all 200ms ease-in-out',
    cursor: onClick ? 'pointer' : 'default',
    overflow: 'hidden',
    ...style,
  };

  return (
    <div
      style={cardStyle}
      className={className}
      onClick={onClick}
      onMouseEnter={() => setIsHovered(true)}
      onMouseLeave={() => setIsHovered(false)}
    >
      {title && (
        <div
          style={{
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'space-between',
            padding: `${spacing[4]} ${spacing[6]}`,
            borderBottom: bordered ? `1px solid ${colors.border.secondary}` : 'none',
          }}
        >
          <div style={{ fontSize: typography.fontSize.lg, fontWeight: typography.fontWeight.semibold, color: colors.text.primary }}>
            {title}
          </div>
          {extra && <div>{extra}</div>}
        </div>
      )}
      <div style={{ padding: spacing[6] }}>
        {loading ? (
          <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'center', minHeight: '100px' }}>
            <Loading />
          </div>
        ) : (
          children
        )}
      </div>
    </div>
  );
}

// Card 内部组件
Card.Header = function CardHeader({ children, style }: { children: React.ReactNode; style?: CSSProperties }) {
  return (
    <div
      style={{
        padding: `${spacing[4]} ${spacing[6]}`,
        borderBottom: `1px solid ${colors.border.secondary}`,
        ...style,
      }}
    >
      {children}
    </div>
  );
};

Card.Body = function CardBody({ children, style }: { children: React.ReactNode; style?: CSSProperties }) {
  return (
    <div style={{ padding: spacing[6], ...style }}>
      {children}
    </div>
  );
};

Card.Footer = function CardFooter({ children, style }: { children: React.ReactNode; style?: CSSProperties }) {
  return (
    <div
      style={{
        padding: `${spacing[4]} ${spacing[6]}`,
        borderTop: `1px solid ${colors.border.secondary}`,
        backgroundColor: colors.bg.page,
        ...style,
      }}
    >
      {children}
    </div>
  );
};

function Loading() {
  return (
    <svg
      width="24"
      height="24"
      viewBox="0 0 24 24"
      fill="none"
      style={{ animation: 'spin 1s linear infinite', color: colors.primary[500] }}
    >
      <style>{`@keyframes spin { from { transform: rotate(0deg); } to { transform: rotate(360deg); } }`}</style>
      <circle cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="3" strokeLinecap="round" strokeDasharray="31.4 31.4" />
    </svg>
  );
}
