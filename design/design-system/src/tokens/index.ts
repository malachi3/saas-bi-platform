// ============================================
// Design Tokens - 设计变量中心
// 所有设计变量必须从这里导出，禁止硬编码
// ============================================

export const colors = {
  // 主色调
  primary: {
    50: '#e6f4ff',
    100: '#bae0ff',
    200: '#91caff',
    300: '#69b1ff',
    400: '#4096ff',
    500: '#1677ff',
    600: '#0958d9',
    700: '#003eb3',
    800: '#002c8c',
    900: '#001d66',
  },
  
  // 成功色
  success: {
    50: '#f6ffed',
    100: '#d9f7be',
    200: '#b7eb8f',
    300: '#95de64',
    400: '#73d13d',
    500: '#52c41a',
    600: '#389e0d',
    700: '#237807',
    800: '#135200',
    900: '#092b00',
  },
  
  // 警告色
  warning: {
    50: '#fffbe6',
    100: '#fff1b8',
    200: '#ffe58f',
    300: '#ffd666',
    400: '#ffc53d',
    500: '#faad14',
    600: '#d48806',
    700: '#ad6800',
    800: '#874d00',
    900: '#5c2d00',
  },
  
  // 错误色
  error: {
    50: '#fff2f0',
    100: '#ffccc7',
    200: '#ffa39e',
    300: '#ff7875',
    400: '#ff4d4f',
    500: '#f5222d',
    600: '#cf1322',
    700: '#a8071a',
    800: '#820014',
    900: '#5c0000',
  },
  
  // 中性色
  neutral: {
    50: '#fafafa',
    100: '#f5f5f5',
    200: '#e8e8e8',
    300: '#d9d9d9',
    400: '#bfbfbf',
    500: '#8c8c8c',
    600: '#595959',
    700: '#434343',
    800: '#262626',
    900: '#1f1f1f',
    1000: '#000000',
  },
  
  // 文字色
  text: {
    primary: '#1f1f1f',
    secondary: '#595959',
    tertiary: '#8c8c8c',
    disabled: '#bfbfbf',
    inverse: '#ffffff',
  },
  
  // 背景色
  bg: {
    base: '#ffffff',
    page: '#f5f5f5',
    elevated: '#ffffff',
    hovered: '#f5f5f5',
    pressed: '#e8e8e8',
    disabled: '#f5f5f5',
  },
  
  // 边框色
  border: {
    primary: '#d9d9d9',
    secondary: '#e8e8e8',
    disabled: '#bfbfbf',
  },
} as const;

// 间距系统（基于 4px 网格）
export const spacing = {
  0: '0px',
  1: '4px',
  2: '8px',
  3: '12px',
  4: '16px',
  5: '20px',
  6: '24px',
  8: '32px',
  10: '40px',
  12: '48px',
  16: '64px',
  20: '80px',
  24: '96px',
} as const;

// 字体系统
export const typography = {
  fontFamily: {
    sans: '-apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif',
    mono: '"SF Mono", Monaco, "Cascadia Code", "Roboto Mono", Consolas, monospace',
  },
  fontSize: {
    xs: '12px',
    sm: '14px',
    base: '16px',
    lg: '18px',
    xl: '20px',
    '2xl': '24px',
    '3xl': '30px',
    '4xl': '36px',
  },
  fontWeight: {
    normal: 400,
    medium: 500,
    semibold: 600,
    bold: 700,
  },
  lineHeight: {
    tight: 1.25,
    normal: 1.5,
    relaxed: 1.75,
  },
} as const;

// 圆角系统
export const radius = {
  none: '0px',
  sm: '4px',
  md: '8px',
  lg: '12px',
  xl: '16px',
  '2xl': '24px',
  full: '9999px',
} as const;

// 阴影系统
export const shadows = {
  none: 'none',
  sm: '0 1px 2px 0 rgba(0, 0, 0, 0.05)',
  md: '0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -2px rgba(0, 0, 0, 0.1)',
  lg: '0 10px 15px -3px rgba(0, 0, 0, 0.1), 0 4px 6px -4px rgba(0, 0, 0, 0.1)',
  xl: '0 20px 25px -5px rgba(0, 0, 0, 0.1), 0 8px 10px -6px rgba(0, 0, 0, 0.1)',
  '2xl': '0 25px 50px -12px rgba(0, 0, 0, 0.25)',
} as const;

// 断点系统
export const breakpoints = {
  xs: '480px',
  sm: '576px',
  md: '768px',
  lg: '992px',
  xl: '1200px',
  '2xl': '1400px',
} as const;

// 动画系统
export const transitions = {
  fast: '100ms',
  normal: '200ms',
  slow: '300ms',
  easeIn: 'cubic-bezier(0.4, 0, 1, 1)',
  easeOut: 'cubic-bezier(0, 0, 0.2, 1)',
  easeInOut: 'cubic-bezier(0.4, 0, 0.2, 1)',
} as const;

// z-index 层级
export const zIndex = {
  dropdown: 1000,
  sticky: 1100,
  fixed: 1200,
  modalBackdrop: 1300,
  modal: 1400,
  popover: 1500,
  tooltip: 1600,
} as const;

// 合并为完整 tokens
export const tokens = {
  colors,
  spacing,
  typography,
  radius,
  shadows,
  breakpoints,
  transitions,
  zIndex,
} as const;

export type Tokens = typeof tokens;
