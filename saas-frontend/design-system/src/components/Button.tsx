import type { ExtractPropTypes, PropType } from 'vue';
import { tokens } from '../tokens';

export interface ButtonProps {
  /** 按钮类型 */
  type?: 'primary' | 'default' | 'dashed' | 'text' | 'link';
  /** 按钮尺寸 */
  size?: 'small' | 'middle' | 'large';
  /** 是否禁用 */
  disabled?: boolean;
  /** 是否加载中 */
  loading?: boolean;
  /** 图标 */
  icon?: string;
  /** 原生类型 */
  htmlType?: 'button' | 'submit' | 'reset';
  /** 危险按钮 */
  danger?: boolean;
  /** 块级按钮 */
  block?: boolean;
  /** 点击回调 */
  onClick?: (e: MouseEvent) => void;
}

export type ButtonEmits = {
  (e: 'click', event: MouseEvent): void;
};

export const buttonProps = {
  type: {
    type: String as PropType<ButtonProps['type']>,
    default: 'default',
  },
  size: {
    type: String as PropType<ButtonProps['size']>,
    default: 'middle',
  },
  disabled: Boolean,
  loading: Boolean,
  icon: String,
  htmlType: {
    type: String as PropType<ButtonProps['htmlType']>,
    default: 'button',
  },
  danger: Boolean,
  block: Boolean,
};

export type ButtonExpose = {
  ref: HTMLButtonElement | undefined;
};

export function useButton(props: ExtractPropTypes<typeof buttonProps>) {
  const classNames = computed(() => {
    const classes: string[] = ['ds-button', `ds-button-${props.type}`, `ds-button-${props.size}`];

    if (props.disabled || props.loading) {
      classes.push('ds-button-disabled');
    }
    if (props.loading) {
      classes.push('ds-button-loading');
    }
    if (props.danger) {
      classes.push('ds-button-danger');
    }
    if (props.block) {
      classes.push('ds-button-block');
    }

    return classes.join(' ');
  });

  const styles = computed(() => ({
    color: props.type === 'primary' ? tokens.colors.white : undefined,
    backgroundColor: props.type === 'primary' ? tokens.colors.primary : undefined,
    borderColor:
      props.type !== 'text' && props.type !== 'link'
        ? tokens.colors.border
        : undefined,
    borderRadius: tokens.borderRadius.md,
  }));

  return {
    classNames,
    styles,
  };
}

import { computed } from 'vue';
