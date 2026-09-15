import type { ExtractPropTypes, PropType } from 'vue';
import { tokens } from '../tokens';

export interface InputProps {
  /** 输入框类型 */
  type?: 'text' | 'password' | 'email' | 'number' | 'tel' | 'url';
  /** 输入框尺寸 */
  size?: 'small' | 'middle' | 'large';
  /** 占位文本 */
  placeholder?: string;
  /** 是否禁用 */
  disabled?: boolean;
  /** 是否只读 */
  readonly?: boolean;
  /** 最大长度 */
  maxLength?: number;
  /** 前缀图标 */
  prefix?: string;
  /** 后缀图标 */
  suffix?: string;
  /** 是否显示清除按钮 */
  allowClear?: boolean;
  /** v-model 值 */
  value?: string;
  /** 默认值 */
  defaultValue?: string;
}

export type InputEmits = {
  (e: 'update:value', value: string): void;
  (e: 'change', value: string): void;
  (e: 'input', value: string): void;
  (e: 'focus', event: FocusEvent): void;
  (e: 'blur', event: FocusEvent): void;
  (e: 'pressEnter', event: KeyboardEvent): void;
};

export const inputProps = {
  type: {
    type: String as PropType<InputProps['type']>,
    default: 'text',
  },
  size: {
    type: String as PropType<InputProps['size']>,
    default: 'middle',
  },
  placeholder: String,
  disabled: Boolean,
  readonly: Boolean,
  maxLength: Number,
  prefix: String,
  suffix: String,
  allowClear: Boolean,
  value: String,
  defaultValue: String,
};

export type InputExpose = {
  focus: () => void;
  blur: () => void;
  select: () => void;
};

export function useInput(props: ExtractPropTypes<typeof inputProps>) {
  const inputHeight = computed(() => {
    const heights = {
      small: '24px',
      middle: '32px',
      large: '40px',
    };
    return heights[props.size || 'middle'];
  });

  const inputStyles = computed(() => ({
    height: inputHeight.value,
    fontSize:
      props.size === 'small'
        ? tokens.typography.fontSize.sm
        : props.size === 'large'
          ? tokens.typography.fontSize.lg
          : tokens.typography.fontSize.md,
    borderRadius: tokens.borderRadius.md,
    paddingLeft: tokens.spacing.lg,
    paddingRight: props.allowClear || props.suffix ? '32px' : tokens.spacing.lg,
    borderColor: props.disabled ? tokens.colors.borderLight : tokens.colors.border,
    backgroundColor: props.disabled ? tokens.colors.bgLayout : tokens.colors.bgBase,
    color: props.disabled ? tokens.colors.textDisabled : tokens.colors.textPrimary,
  }));

  return {
    inputStyles,
    inputHeight,
  };
}

import { computed } from 'vue';
