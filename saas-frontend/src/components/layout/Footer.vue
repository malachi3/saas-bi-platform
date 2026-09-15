/**
 * Footer Component
 * 页脚组件
 */

import { defineComponent, h } from 'vue';
import { tokens } from '@design-system/tokens';

export const Footer = defineComponent({
  name: 'AppFooter',
  setup() {
    return () =>
      h(
        'footer',
        {
          class: 'app-footer',
          style: {
            height: tokens.layout.footerHeight,
            backgroundColor: tokens.colors.bgBase,
            borderTop: `1px solid ${tokens.colors.borderLight}`,
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'center',
          },
        },
        { default: () => [] }
      );
  },
});

export default Footer;
