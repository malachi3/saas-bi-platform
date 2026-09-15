/**
 * Tenant Store
 * 租户状态管理
 */

import { defineStore } from 'pinia';
import { ref, computed } from 'vue';
import type { TenantContext, TenantType } from '@/types/tenant.d';
import { getSelectableTenants, switchTenant as switchTenantApi } from '@/api/tenant';
import { getUserInfo } from '@/api/auth';
import router from '@/router';

export const useTenantStore = defineStore('tenant', () => {
  /** 当前租户上下文 */
  const currentTenant = ref<TenantContext | null>(null);

  /** 可选租户列表 */
  const tenantList = ref<TenantContext[]>([]);

  /** 是否有多个租户 */
  const hasMultipleTenants = computed(() => tenantList.value.length > 1);

  /** 设置当前租户 */
  function setTenant(tenant: TenantContext | null): void {
    currentTenant.value = tenant;
    if (tenant) {
      localStorage.setItem('tenant_id', String(tenant.id));
    } else {
      localStorage.removeItem('tenant_id');
    }
  }

  /** 清除租户信息 */
  function clearTenant(): void {
    currentTenant.value = null;
    tenantList.value = [];
    localStorage.removeItem('tenant_id');
  }

  /** 加载可选租户列表 */
  async function loadTenantList(): Promise<void> {
    try {
      const list = await getSelectableTenants();
      tenantList.value = list.map((item) => ({
        id: item.id,
        name: item.name,
        code: item.code,
        type: item.type,
        expireTime: item.expireTime,
      }));

      // 如果只有一个租户，自动设置为当前租户
      if (list.length === 1) {
        setTenant(tenantList.value[0]);
      } else {
        // 从本地存储恢复租户
        const savedTenantId = localStorage.getItem('tenant_id');
        if (savedTenantId) {
          const savedTenant = tenantList.value.find((t) => t.id === Number(savedTenantId));
          if (savedTenant) {
            setTenant(savedTenant);
          }
        }
      }
    } catch (error) {
      console.error('Failed to load tenant list:', error);
    }
  }

  /** 切换租户 */
  async function switchTenant(tenantId: number): Promise<void> {
    try {
      await switchTenantApi(tenantId);
      const tenant = tenantList.value.find((t) => t.id === tenantId);
      if (tenant) {
        setTenant(tenant);
      }
      // 刷新用户信息和权限
      window.location.reload();
    } catch (error) {
      throw error;
    }
  }

  return {
    currentTenant,
    tenantList,
    hasMultipleTenants,
    setTenant,
    clearTenant,
    loadTenantList,
    switchTenant,
  };
});
