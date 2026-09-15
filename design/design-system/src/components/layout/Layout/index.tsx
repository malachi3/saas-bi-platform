import React from 'react';
import { tokens, colors, spacing, radius, shadows, zIndex } from '@/tokens';
import type { CSSProperties } from 'react';

// ============================================
// Layout 布局组件
// ============================================

interface LayoutProps {
  children: React.ReactNode;
  style?: CSSProperties;
  className?: string;
}

export function Layout({ children, style, className = '' }: LayoutProps) {
  return (
    <div
      style={{
        minHeight: '100vh',
        display: 'flex',
        flexDirection: 'column',
        backgroundColor: colors.bg.page,
        ...style,
      }}
      className={className}
    >
      {children}
    </div>
  );
}

interface HeaderProps {
  children?: React.ReactNode;
  logo?: React.ReactNode;
  actions?: React.ReactNode;
  style?: CSSProperties;
  fixed?: boolean;
}

export function Header({ children, logo, actions, style, fixed = false }: HeaderProps) {
  const headerStyle: CSSProperties = {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'space-between',
    height: 64,
    padding: `0 ${spacing[6]}`,
    backgroundColor: colors.bg.base,
    borderBottom: `1px solid ${colors.border.secondary}`,
    boxShadow: shadows.sm,
    position: fixed ? 'fixed' : 'relative',
    top: 0,
    left: 0,
    right: 0,
    zIndex: zIndex.sticky,
  };

  return (
    <header style={{ ...headerStyle, ...style }}>
      <div style={{ display: 'flex', alignItems: 'center', gap: spacing[4] }}>
        {logo}
        {children}
      </div>
      {actions && <div style={{ display: 'flex', alignItems: 'center', gap: spacing[2] }}>{actions}</div>}
    </header>
  );
}

interface SiderProps {
  children: React.ReactNode;
  width?: number | string;
  collapsed?: boolean;
  style?: CSSProperties;
}

export function Sider({ children, width = 200, collapsed = false, style }: SiderProps) {
  const siderStyle: CSSProperties = {
    width: collapsed ? 64 : width,
    backgroundColor: colors.bg.base,
    borderRight: `1px solid ${colors.border.secondary}`,
    transition: `width ${tokens.transitions.normal} ${tokens.transitions.easeInOut}`,
    flexShrink: 0,
    overflow: 'hidden',
  };

  return (
    <aside style={{ ...siderStyle, ...style }}>
      {children}
    </aside>
  );
}

interface ContentProps {
  children: React.ReactNode;
  style?: CSSProperties;
  className?: string;
}

export function Content({ children, style, className = '' }: ContentProps) {
  return (
    <main
      style={{
        flex: 1,
        padding: spacing[6],
        ...style,
      }}
      className={className}
    >
      {children}
    </main>
  );
}

interface FooterProps {
  children?: React.ReactNode;
  style?: CSSProperties;
}

export function Footer({ children, style }: FooterProps) {
  return (
    <footer
      style={{
        padding: `${spacing[4]} ${spacing[6]}`,
        backgroundColor: colors.bg.base,
        borderTop: `1px solid ${colors.border.secondary}`,
        textAlign: 'center',
        color: colors.text.tertiary,
        fontSize: tokens.typography.fontSize.sm,
        ...style,
      }}
    >
      {children}
    </footer>
  );
}
