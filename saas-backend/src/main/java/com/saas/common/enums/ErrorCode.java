package com.saas.common.enums;

/**
 * 错误码枚举
 * <p>
 * 错误码体系：
 * - 0: 成功
 * - 400xx: 参数/校验错误
 * - 401xx: 认证错误
 * - 403xx: 权限错误
 * - 404xx: 资源错误
 * - 500xx: 服务端错误
 *
 * @author saas
 */
public enum ErrorCode {

    // 成功
    SUCCESS(0, "操作成功"),

    // 参数错误 (40000-40099)
    PARAM_INVALID(40000, "参数无效"),
    PARAM_MISSING(40001, "缺少必要参数"),
    PARAM_TYPE_ERROR(40002, "参数类型错误"),
    VALIDATION_ERROR(40003, "数据校验失败"),

    // 认证错误 (40100-40199)
    UNAUTHORIZED(40100, "未登录或登录已过期"),
    TOKEN_INVALID(40101, "Token无效"),
    TOKEN_EXPIRED(40102, "Token已过期"),
    USERNAME_PASSWORD_ERROR(40103, "用户名或密码错误"),
    ACCOUNT_DISABLED(40104, "账号已被禁用"),
    ACCOUNT_LOCKED(40105, "账号已被锁定"),

    // 权限错误 (40300-40399)
    FORBIDDEN(40300, "没有访问权限"),
    PERMISSION_DENIED(40301, "权限不足"),

    // 资源错误 (40400-40499)
    NOT_FOUND(40400, "资源不存在"),
    USER_NOT_FOUND(40401, "用户不存在"),
    TENANT_NOT_FOUND(40402, "租户不存在"),
    MENU_NOT_FOUND(40403, "菜单不存在"),
    ROLE_NOT_FOUND(40404, "角色不存在"),

    // 业务错误 (40900-40999)
    RESOURCE_CONFLICT(40900, "资源冲突"),
    USERNAME_EXISTS(40901, "用户名已存在"),
    EMAIL_EXISTS(40902, "邮箱已被使用"),
    PHONE_EXISTS(40903, "手机号已被使用"),
    TENANT_EXPIRED(40904, "租户已过期"),

    // 服务端错误 (50000-50099)
    INTERNAL_ERROR(50000, "系统繁忙，请稍后重试"),
    SAVE_ERROR(50001, "保存失败"),
    UPDATE_ERROR(50002, "更新失败"),
    DELETE_ERROR(50003, "删除失败"),
    QUERY_ERROR(50004, "查询失败"),
    OPERATION_ERROR(50005, "操作失败"),

    // 验证码错误 (60000-60099)
    CAPTCHA_ERROR(60000, "验证码错误"),
    CAPTCHA_EXPIRED(60001, "验证码已过期"),
    CAPTCHA_REQUIRED(60002, "请输入验证码");

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    /**
     * 根据 code 获取枚举
     *
     * @param code 错误码
     * @return 错误码枚举
     */
    public static ErrorCode getByCode(int code) {
        for (ErrorCode errorCode : values()) {
            if (errorCode.code == code) {
                return errorCode;
            }
        }
        return INTERNAL_ERROR;
    }
}
