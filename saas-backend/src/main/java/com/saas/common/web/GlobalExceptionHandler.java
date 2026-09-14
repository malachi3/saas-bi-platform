package com.saas.common.web;

import com.saas.common.enums.ErrorCode;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.stream.Collectors;

/**
 * 全局异常处理器
 *
 * @author saas
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理业务异常
     *
     * @param e 业务异常
     * @return 响应结果
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R<Void> handleBusinessException(BusinessException e) {
        log.warn("业务异常: {}", e.getMessage());
        return R.fail(e.getCode(), e.getMessage());
    }

    /**
     * 处理参数校验异常
     *
     * @param e 参数校验异常
     * @return 响应结果
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R<Void> handleValidException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        log.warn("参数校验异常: {}", message);
        return R.badRequest(message);
    }

    /**
     * 处理绑定异常
     *
     * @param e 绑定异常
     * @return 响应结果
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R<Void> handleBindException(BindException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        log.warn("绑定异常: {}", message);
        return R.badRequest(message);
    }

    /**
     * 处理约束违反异常
     *
     * @param e 约束违反异常
     * @return 响应结果
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R<Void> handleConstraintViolationException(ConstraintViolationException e) {
        String message = e.getConstraintViolations().stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .collect(Collectors.joining(", "));
        log.warn("约束违反异常: {}", message);
        return R.badRequest(message);
    }

    /**
     * 处理缺少请求参数异常
     *
     * @param e 缺少请求参数异常
     * @return 响应结果
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R<Void> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        String message = "缺少必要参数: " + e.getParameterName();
        log.warn(message);
        return R.badRequest(message);
    }

    /**
     * 处理参数类型不匹配异常
     *
     * @param e 参数类型不匹配异常
     * @return 响应结果
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R<Void> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        String message = "参数类型不匹配: " + e.getName();
        log.warn(message);
        return R.badRequest(message);
    }

    /**
     * 处理请求方法不支持异常
     *
     * @param e 请求方法不支持异常
     * @return 响应结果
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public R<Void> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        String message = "不支持的请求方法: " + e.getMethod();
        log.warn(message);
        return R.fail(40500, message);
    }

    /**
     * 处理资源不存在异常
     *
     * @param e 资源不存在异常
     * @return 响应结果
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public R<Void> handleNoHandlerFoundException(NoHandlerFoundException e) {
        String message = "资源不存在: " + e.getRequestURL();
        log.warn(message);
        return R.notFound(message);
    }

    /**
     * 处理未认证异常
     *
     * @param e 未认证异常
     * @return 响应结果
     */
    @ExceptionHandler(UnAuthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public R<Void> handleUnAuthorizedException(UnAuthorizedException e) {
        log.warn("未认证: {}", e.getMessage());
        return R.unauthorized(e.getMessage());
    }

    /**
     * 处理禁止访问异常
     *
     * @param e 禁止访问异常
     * @return 响应结果
     */
    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public R<Void> handleForbiddenException(ForbiddenException e) {
        log.warn("禁止访问: {}", e.getMessage());
        return R.forbidden(e.getMessage());
    }

    /**
     * 处理其他异常
     *
     * @param e 异常
     * @return 响应结果
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R<Void> handleException(Exception e) {
        log.error("系统异常", e);
        return R.fail("系统繁忙，请稍后重试");
    }

    /**
     * 业务异常
     */
    public static class BusinessException extends RuntimeException {
        private final int code;

        public BusinessException(int code, String message) {
            super(message);
            this.code = code;
        }

        public BusinessException(ErrorCode errorCode) {
            super(errorCode.getMessage());
            this.code = errorCode.getCode();
        }

        public int getCode() {
            return code;
        }
    }

    /**
     * 未认证异常
     */
    public static class UnAuthorizedException extends RuntimeException {
        public UnAuthorizedException(String message) {
            super(message);
        }
    }

    /**
     * 禁止访问异常
     */
    public static class ForbiddenException extends RuntimeException {
        public ForbiddenException(String message) {
            super(message);
        }
    }
}
