import React from 'react';
import { tokens, colors, spacing, typography, radius, zIndex } from '@/tokens';
import type { CSSProperties } from 'react';

// ============================================
// Select 选择器组件
// ============================================

export interface SelectOption {
  label: string;
  value: string | number;
  disabled?: boolean;
}

interface SelectProps {
  value?: string | number;
  defaultValue?: string | number;
  options: SelectOption[];
  placeholder?: string;
  disabled?: boolean;
  size?: 'sm' | 'md' | 'lg';
  onChange?: (value: string | number) => void;
  className?: string;
}

export function Select({
  value,
  defaultValue,
  options,
  placeholder = '请选择',
  disabled = false,
  size = 'md',
  onChange,
  className = '',
}: SelectProps) {
  const [isOpen, setIsOpen] = React.useState(false);
  const [selectedValue, setSelectedValue] = React.useState(defaultValue);
  const [isHovered, setIsHovered] = React.useState(false);
  const containerRef = React.useRef<HTMLDivElement>(null);

  const currentValue = value !== undefined ? value : selectedValue;
  const selectedOption = options.find(opt => opt.value === currentValue);

  const sizeStyles = {
    sm: { height: 28, fontSize: tokens.typography.fontSize.xs },
    md: { height: 36, fontSize: tokens.typography.fontSize.sm },
    lg: { height: 44, fontSize: tokens.typography.fontSize.base },
  };

  const sizeStyle = sizeStyles[size];

  React.useEffect(() => {
    const handleClickOutside = (e: MouseEvent) => {
      if (containerRef.current && !containerRef.current.contains(e.target as Node)) {
        setIsOpen(false);
      }
    };
    document.addEventListener('mousedown', handleClickOutside);
    return () => document.removeEventListener('mousedown', handleClickOutside);
  }, []);

  const handleSelect = (option: SelectOption) => {
    if (option.disabled) return;
    setSelectedValue(option.value);
    onChange?.(option.value);
    setIsOpen(false);
  };

  const wrapperStyle: CSSProperties = {
    position: 'relative',
    display: 'inline-block',
    minWidth: '200px',
  };

  const triggerStyle: CSSProperties = {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'space-between',
    height: sizeStyle.height,
    padding: `0 ${spacing[3]}px`,
    backgroundColor: disabled ? colors.bg.disabled : colors.bg.base,
    border: `1px solid ${isOpen ? colors.primary[500] : isHovered ? colors.primary[300] : colors.border.primary}`,
    borderRadius: radius.md,
    cursor: disabled ? 'not-allowed' : 'pointer',
    fontSize: sizeStyle.fontSize,
    color: selectedOption ? colors.text.primary : colors.text.tertiary,
    transition: `border-color ${tokens.transitions.fast} ${tokens.transitions.easeInOut}`,
    userSelect: 'none',
  };

  const dropdownStyle: CSSProperties = {
    position: 'absolute',
    top: '100%',
    left: 0,
    right: 0,
    marginTop: spacing[1],
    backgroundColor: colors.bg.base,
    border: `1px solid ${colors.border.secondary}`,
    borderRadius: radius.md,
    boxShadow: tokens.shadows.lg,
    zIndex: zIndex.dropdown,
    maxHeight: '240px',
    overflowY: 'auto',
    animation: 'selectDropDown 150ms ease-out',
  };

  const optionStyle = (isSelected: boolean, isDisabled?: boolean): CSSProperties => ({
    padding: `${spacing[2]} ${spacing[3]}px`,
    cursor: isDisabled ? 'not-allowed' : 'pointer',
    backgroundColor: isSelected ? colors.primary[50] : 'transparent',
    color: isDisabled ? colors.text.disabled : isSelected ? colors.primary[600] : colors.text.primary,
    fontSize: sizeStyle.fontSize,
    transition: `background-color ${tokens.transitions.fast}`,
  });

  return (
    <div
      ref={containerRef}
      style={wrapperStyle}
      className={className}
    >
      <div
        style={triggerStyle}
        onClick={() => !disabled && setIsOpen(!isOpen)}
        onMouseEnter={() => setIsHovered(true)}
        onMouseLeave={() => setIsHovered(false)}
      >
        <span>{selectedOption?.label || placeholder}</span>
        <span style={{ color: colors.text.tertiary, transform: isOpen ? 'rotate(180deg)' : 'rotate(0)', transition: 'transform 150ms' }}>
          ▼
        </span>
      </div>

      {isOpen && (
        <div style={dropdownStyle}>
          {options.map((option) => (
            <div
              key={option.value}
              style={optionStyle(currentValue === option.value, option.disabled)}
              onClick={() => handleSelect(option)}
              onMouseEnter={(e) => {
                if (!option.disabled && currentValue !== option.value) {
                  e.currentTarget.style.backgroundColor = colors.bg.hovered;
                }
              }}
              onMouseLeave={(e) => {
                if (!option.disabled && currentValue !== option.value) {
                  e.currentTarget.style.backgroundColor = 'transparent';
                }
              }}
            >
              {option.label}
            </div>
          ))}
        </div>
      )}

      <style>{`
        @keyframes selectDropDown {
          from { opacity: 0; transform: translateY(-8px); }
          to { opacity: 1; transform: translateY(0); }
        }
      `}</style>
    </div>
  );
}
