/**
 * Design System Tokens
 * 设计系统变量 - 所有颜色、间距、字体等必须从这里引用
 */

// 颜色系统
export const colors = {
  // 主色
  primary: '#1890ff',
  primaryHover: '#40a9ff',
  primaryActive: '#096dd9',
  primaryLight: '#e6f7ff',

  // 成功色
  success: '#52c41a',
  successHover: '#73d13d',
  successActive: '#389e0d',
  successLight: '#f6ffed',

  // 警告色
  warning: '#faad14',
  warningHover: '#ffc53d',
  warningActive: '#d48806',
  warningLight: '#fffbe6',

  // 错误色
  error: '#ff4d4f',
  errorHover: '#ff7875',
  errorActive: '#d4380d',
  errorLight: '#fff2f0',

  // 信息色
  info: '#1890ff',
  infoLight: '#e6f7ff',

  // 中性色
  white: '#ffffff',
  black: '#000000',
  transparent: 'transparent',

  // 文字色
  textPrimary: 'rgba(0, 0, 0, 0.88)',
  textSecondary: 'rgba(0, 0, 0, 0.65)',
  textTertiary: 'rgba(0, 0, 0, 0.45)',
  textDisabled: 'rgba(0, 0, 0, 0.25)',

  // 边框色
  border: '#d9d9d9',
  borderLight: '#f0f0f0',
  borderPrimary: '#1890ff',

  // 背景色
  bgBase: '#ffffff',
  bgLayout: '#f5f5f5',
  bgContainer: '#ffffff',
  bgElevated: '#ffffff',
  bgMask: 'rgba(0, 0, 0, 0.45)',
} as const;

// 间距系统
export const spacing = {
  xs: '4px',
  sm: '8px',
  md: '12px',
  lg: '16px',
  xl: '24px',
  xxl: '32px',
  xxxl: '48px',
} as const;

// 圆角系统
export const borderRadius = {
  sm: '4px',
  md: '6px',
  lg: '8px',
  xl: '12px',
  full: '9999px',
} as const;

// 字体系统
export const typography = {
  fontFamily: "-apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif",
  fontFamilyMono: "'SFMono-Regular', Consolas, 'Liberation Mono', Menlo, monospace",

  fontSize: {
    xs: '12px',
    sm: '13px',
    md: '14px',
    lg: '16px',
    xl: '20px',
    xxl: '24px',
    xxxl: '30px',
  },

  fontWeight: {
    normal: 400,
    medium: 500,
    semibold: 600,
    bold: 700,
  },

  lineHeight: {
    tight: 1.25,
    default: 1.5,
    loose: 1.75,
  },
} as const;

// 阴影系统
export const shadows = {
  sm: '0 1px 2px 0 rgba(0, 0, 0, 0.05)',
  md: '0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06)',
  lg: '0 10px 15px -3px rgba(0, 0, 0, 0.1), 0 4px 6px -2px rgba(0, 0, 0, 0.05)',
  xl: '0 20px 25px -5px rgba(0, 0, 0, 0.1), 0 10px 10px -5px rgba(0, 0, 0, 0.04)',
} as const;

// 动画系统
export const transitions = {
  duration: {
    fast: '100ms',
    normal: '200ms',
    slow: '300ms',
  },
  easing: {
    default: 'cubic-bezier(0.4, 0, 0.2, 1)',
    easeIn: 'cubic-bezier(0.4, 0, 1, 1)',
    easeOut: 'cubic-bezier(0, 0, 0.2, 1)',
    easeInOut: 'cubic-bezier(0.4, 0, 0.2, 1)',
  },
} as const;

// Z-Index 层级
export const zIndex = {
  base: 0,
  dropdown: 1000,
  sticky: 1100,
  fixed: 1200,
  modal: 1300,
  popover: 1400,
  tooltip: 1500,
  notification: 1600,
} as const;

// 断点系统
export const breakpoints = {
  xs: '480px',
  sm: '576px',
  md: '768px',
  lg: '992px',
  xl: '1200px',
  xxl: '1600px',
} as const;

// 布局系统
export const layout = {
  headerHeight: '64px',
  siderWidth: '208px',
  siderCollapsedWidth: '48px',
  footerHeight: '48px',
} as const;

// 合并为完整 tokens 对象
export const tokens = {
  colors,
  spacing,
  borderRadius,
  typography,
  shadows,
  transitions,
  zIndex,
  breakpoints,
  layout,
} as const;

export type DesignTokens = typeof tokens;

export default tokens;
