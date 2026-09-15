/**
 * Sidebar Component
 * 侧边栏组件
 */

import { defineComponent, h } from 'vue';
import { tokens } from '@design-system/tokens';

export const Sidebar = defineComponent({
  name: 'AppSidebar',
  setup() {
    return () =>
      h(
        'aside',
        {
          class: 'app-sidebar',
          style: {
            width: tokens.layout.siderWidth,
            height: '100%',
            backgroundColor: tokens.colors.bgBase,
            borderRight: `1px solid ${tokens.colors.borderLight}`,
          },
        },
        { default: () => [] }
      );
  },
});

export default Sidebar;
