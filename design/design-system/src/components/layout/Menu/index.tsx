import React from 'react';
import { tokens, colors, spacing, radius, zIndex } from '@/tokens';
import type { CSSProperties } from 'react';

// ============================================
// Menu 菜单组件
// ============================================

export interface MenuItem {
  key: string;
  label: string;
  icon?: React.ReactNode;
  disabled?: boolean;
  children?: MenuItem[];
}

interface MenuProps {
  items: MenuItem[];
  selectedKey?: string;
  collapsed?: boolean;
  onSelect?: (key: string) => void;
  style?: CSSProperties;
}

export function Menu({ items, selectedKey, collapsed = false, onSelect, style }: MenuProps) {
  const [hoveredKey, setHoveredKey] = React.useState<string | null>(null);

  const renderMenuItem = (item: MenuItem, level = 0) => {
    const isSelected = selectedKey === item.key;
    const isHovered = hoveredKey === item.key;

    const itemStyle: CSSProperties = {
      display: 'flex',
      alignItems: 'center',
      gap: spacing[2],
      padding: `${spacing[2]} ${collapsed ? spacing[3] : spacing[4]}`,
      paddingLeft: collapsed ? spacing[3] : `${spacing[4] + level * spacing[4]}`,
      fontSize: tokens.typography.fontSize.sm,
      color: isSelected ? colors.primary[600] : isHovered ? colors.text.primary : colors.text.secondary,
      backgroundColor: isSelected ? colors.primary[50] : isHovered ? colors.bg.hovered : 'transparent',
      cursor: item.disabled ? 'not-allowed' : 'pointer',
      opacity: item.disabled ? 0.5 : 1,
      transition: `all ${tokens.transitions.fast}`,
      borderRadius: collapsed ? radius.md : 0,
      margin: collapsed ? `${spacing[1]} ${spacing[1]}` : 0,
    };

    return (
      <div
        key={item.key}
        style={itemStyle}
        onClick={() => !item.disabled && onSelect?.(item.key)}
        onMouseEnter={() => setHoveredKey(item.key)}
        onMouseLeave={() => setHoveredKey(null)}
      >
        {item.icon && (
          <span style={{ fontSize: '16px', display: 'flex', alignItems: 'center' }}>
            {item.icon}
          </span>
        )}
        {!collapsed && <span>{item.label}</span>}
      </div>
    );
  };

  const containerStyle: CSSProperties = {
    padding: spacing[2],
    ...style,
  };

  return (
    <nav style={containerStyle}>
      {items.map(item => renderMenuItem(item))}
    </nav>
  );
}
