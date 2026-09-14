package com.saas.common.tenant;

/**
 * 租户类型枚举
 * 用于区分不同的多租户隔离策略
 *
 * @author saas
 */
public enum TenantType {

    /**
     * 共享 Schema - 普通租户（预计 80%）
     */
    SHARED(1, "共享 Schema"),

    /**
     * 独立 Schema - 中型客户
     */
    SCHEMA(2, "独立 Schema"),

    /**
     * 独立数据库 - 大客户/高安全要求
     */
    DATABASE(3, "独立数据库");

    private final Integer code;
    private final String description;

    TenantType(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    /**
     * 根据 code 获取枚举
     *
     * @param code 类型编码
     * @return 租户类型枚举
     */
    public static TenantType getByCode(Integer code) {
        if (code == null) {
            return SHARED;
        }
        for (TenantType type : values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        return SHARED;
    }
}
