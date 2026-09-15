import React, { useState } from 'react';
import { tokens, colors, spacing, typography, radius, transitions } from '@/tokens';
import type { CSSProperties } from 'react';

// ============================================
// Input 输入框组件
// ============================================

export type InputSize = 'sm' | 'md' | 'lg';
export type InputStatus = 'default' | 'error' | 'success';

interface InputProps {
  size?: InputSize;
  status?: InputStatus;
  disabled?: boolean;
  placeholder?: string;
  value?: string;
  defaultValue?: string;
  prefix?: React.ReactNode;
  suffix?: React.ReactNode;
  allowClear?: boolean;
  onChange?: (e: React.ChangeEvent<HTMLInputElement>) => void;
  onPressEnter?: () => void;
  className?: string;
}

interface InputRef {
  focus: () => void;
  blur: () => void;
}

const sizeStyles: Record<InputSize, { height: number; paddingH: number; fontSize: string }> = {
  sm: { height: 28, paddingH: 8, fontSize: typography.fontSize.xs },
  md: { height: 36, paddingH: 12, fontSize: typography.fontSize.sm },
  lg: { height: 44, paddingH: 16, fontSize: typography.fontSize.base },
};

const statusColors: Record<InputStatus, string> = {
  default: colors.border.primary,
  error: colors.error[500],
  success: colors.success[500],
};

export const Input = React.forwardRef<InputRef, InputProps>(({
  size = 'md',
  status = 'default',
  disabled = false,
  placeholder,
  value,
  defaultValue,
  prefix,
  suffix,
  allowClear = false,
  onChange,
  onPressEnter,
  className = '',
}, ref) => {
  const [inputValue, setInputValue] = useState(defaultValue || '');
  const [isFocused, setIsFocused] = useState(false);
  const [isHovered, setIsHovered] = useState(false);
  
  const currentValue = value !== undefined ? value : inputValue;
  const sizeStyle = sizeStyles[size];
  const borderColor = isFocused 
    ? colors.primary[500] 
    : status !== 'default' 
      ? statusColors[status]
      : isHovered 
        ? colors.primary[300]
        : colors.border.primary;

  const wrapperStyle: CSSProperties = {
    display: 'inline-flex',
    alignItems: 'center',
    height: sizeStyle.height,
    padding: `0 ${sizeStyle.paddingH}px`,
    backgroundColor: disabled ? colors.bg.disabled : colors.bg.base,
    border: `1px solid ${disabled ? colors.border.disabled : borderColor}`,
    borderRadius: radius.md,
    transition: `all ${transitions.fast} ${transitions.easeInOut}`,
    cursor: disabled ? 'not-allowed' : 'text',
    minWidth: '200px',
  };

  const inputStyle: CSSProperties = {
    flex: 1,
    height: '100%',
    border: 'none',
    outline: 'none',
    background: 'transparent',
    fontSize: sizeStyle.fontSize,
    color: disabled ? colors.text.disabled : colors.text.primary,
    padding: 0,
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (disabled) return;
    setInputValue(e.target.value);
    onChange?.(e);
  };

  const handleClear = () => {
    setInputValue('');
    onChange?.({ target: { value: '' } } as React.ChangeEvent<HTMLInputElement>);
  };

  React.useImperativeHandle(ref, () => ({
    focus: () => document.querySelector<HTMLInputElement>('[data-input]')?.focus(),
    blur: () => document.querySelector<HTMLInputElement>('[data-input]')?.blur(),
  }));

  return (
    <div
      style={wrapperStyle}
      className={className}
      onMouseEnter={() => setIsHovered(true)}
      onMouseLeave={() => setIsHovered(false)}
    >
      {prefix && <span style={{ color: colors.text.tertiary, marginRight: spacing[1] }}>{prefix}</span>}
      <input
        data-input
        type="text"
        style={inputStyle}
        placeholder={placeholder}
        value={currentValue}
        disabled={disabled}
        onChange={handleChange}
        onFocus={() => setIsFocused(true)}
        onBlur={() => setIsFocused(false)}
        onKeyDown={(e) => {
          if (e.key === 'Enter' && onPressEnter) {
            onPressEnter();
          }
        }}
      />
      {allowClear && currentValue && (
        <span
          onClick={handleClear}
          style={{ 
            color: colors.text.tertiary, 
            cursor: 'pointer',
            marginLeft: spacing[1],
            display: 'flex',
            alignItems: 'center',
          }}
        >
          ✕
        </span>
      )}
      {suffix && <span style={{ color: colors.text.tertiary, marginLeft: spacing[1] }}>{suffix}</span>}
    </div>
  );
});

Input.displayName = 'Input';
