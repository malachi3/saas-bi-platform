package com.saas.common.enums;

/**
 * 状态枚举
 *
 * @author saas
 */
public enum StatusEnum {

    DISABLE(0, "禁用"),
    ENABLE(1, "启用");

    private final Integer code;
    private final String description;

    StatusEnum(Integer code, String description) {
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
     * @param code 状态码
     * @return 状态枚举
     */
    public static StatusEnum getByCode(Integer code) {
        if (code == null) {
            return DISABLE;
        }
        for (StatusEnum status : values()) {
            if (status.code.equals(code)) {
                return status;
            }
        }
        return DISABLE;
    }

    /**
     * 判断是否启用
     *
     * @param code 状态码
     * @return 是否启用
     */
    public static boolean isEnabled(Integer code) {
        return ENABLE.code.equals(code);
    }

    /**
     * 判断是否禁用
     *
     * @param code 状态码
     * @return 是否禁用
     */
    public static boolean isDisabled(Integer code) {
        return DISABLE.code.equals(code);
    }
}
