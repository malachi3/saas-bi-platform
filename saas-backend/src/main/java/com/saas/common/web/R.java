package com.saas.common.web;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 统一响应结果
 *
 * @param <T> 数据类型
 * @author saas
 */
@Data
@Schema(description = "统一响应结果")
public class R<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "状态码: 0成功, 其他失败")
    private int code;

    @Schema(description = "数据")
    private T data;

    @Schema(description = "消息")
    private String message;

    @Schema(description = "是否成功")
    private boolean success;

    @Schema(description = "时间戳")
    private long timestamp;

    public R() {
        this.timestamp = System.currentTimeMillis();
    }

    public R(int code, T data, String message, boolean success) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.success = success;
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * 成功响应
     *
     * @param data 数据
     * @param <T>  数据类型
     * @return 响应结果
     */
    public static <T> R<T> ok(T data) {
        return new R<>(0, data, "success", true);
    }

    /**
     * 成功响应（无数据）
     *
     * @param <T> 数据类型
     * @return 响应结果
     */
    public static <T> R<T> ok() {
        return new R<>(0, null, "success", true);
    }

    /**
     * 失败响应
     *
     * @param code    错误码
     * @param message 错误消息
     * @param <T>     数据类型
     * @return 响应结果
     */
    public static <T> R<T> fail(int code, String message) {
        return new R<>(code, null, message, false);
    }

    /**
     * 失败响应（使用默认错误码）
     *
     * @param message 错误消息
     * @param <T>     数据类型
     * @return 响应结果
     */
    public static <T> R<T> fail(String message) {
        return new R<>(50000, null, message, false);
    }

    /**
     * 参数错误
     *
     * @param message 错误消息
     * @param <T>    数据类型
     * @return 响应结果
     */
    public static <T> R<T> badRequest(String message) {
        return new R<>(40000, null, message, false);
    }

    /**
     * 未授权
     *
     * @param message 错误消息
     * @param <T>    数据类型
     * @return 响应结果
     */
    public static <T> R<T> unauthorized(String message) {
        return new R<>(40100, null, message, false);
    }

    /**
     * 禁止访问
     *
     * @param message 错误消息
     * @param <T>    数据类型
     * @return 响应结果
     */
    public static <T> R<T> forbidden(String message) {
        return new R<>(40300, null, message, false);
    }

    /**
     * 资源不存在
     *
     * @param message 错误消息
     * @param <T>    数据类型
     * @return 响应结果
     */
    public static <T> R<T> notFound(String message) {
        return new R<>(40400, null, message, false);
    }
}
