/**
 * Header Component
 * 页头组件
 */

import { defineComponent, h } from 'vue';
import { tokens } from '@design-system/tokens';

export const Header = defineComponent({
  name: 'AppHeader',
  setup() {
    return () =>
      h(
        'header',
        {
          class: 'app-header',
          style: {
            height: tokens.layout.headerHeight,
            backgroundColor: tokens.colors.bgBase,
            borderBottom: `1px solid ${tokens.colors.borderLight}`,
            display: 'flex',
            alignItems: 'center',
            padding: `0 ${tokens.spacing.lg}`,
          },
        },
        { default: () => [] }
      );
  },
});

export default Header;
