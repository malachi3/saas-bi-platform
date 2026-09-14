package com.saas.common.tenant;

/**
 * 租户上下文持有者
 * 使用 ThreadLocal 存储当前请求的租户ID
 *
 * @author saas
 */
public class TenantContextHolder {

    private static final ThreadLocal<Long> TENANT_ID = new ThreadLocal<>();

    /**
     * 设置租户ID
     *
     * @param tenantId 租户ID
     */
    public static void setTenantId(Long tenantId) {
        TENANT_ID.set(tenantId);
    }

    /**
     * 获取租户ID
     *
     * @return 租户ID
     */
    public static Long getTenantId() {
        return TENANT_ID.get();
    }

    /**
     * 清除租户上下文
     * 在请求结束时必须调用
     */
    public static void clear() {
        TENANT_ID.remove();
    }

    /**
     * 检查是否设置了租户ID
     *
     * @return 是否设置了租户ID
     */
    public static boolean hasTenant() {
        return TENANT_ID.get() != null;
    }
}
