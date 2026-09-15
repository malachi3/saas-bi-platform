<template>
  <el-config-provider :locale="locale">
    <router-view />
  </el-config-provider>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { ElConfigProvider } from 'element-plus';
import zhCn from 'element-plus/dist/locale/zh-cn.mjs';
import { useUserStore } from '@/stores/user';
import { useTenantStore } from '@/stores/tenant';

const locale = ref(zhCn);

const userStore = useUserStore();
const tenantStore = useTenantStore();

onMounted(async () => {
  // 从本地存储恢复登录状态
  if (userStore.restoreState()) {
    try {
      await userStore.fetchUserInfo();
      await tenantStore.loadTenantList();
    } catch {
      // 恢复失败，清除状态
      userStore.logout();
    }
  }
});
</script>

<style>
@import '@design-system/src/styles/variables.css';

#app {
  font-family: var(--font-family);
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}
</style>
