package com.saas.module.permission.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限校验注解
 * 标注在方法上，指定需要的权限
 *
 * @author saas
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequirePermission {

    /**
     * 权限标识
     */
    String[] value();

    /**
     * 逻辑：AND/OR
     */
    Logical logical() default Logical.AND;

    enum Logical {
        /**
         * 需要满足所有权限
         */
        AND,
        /**
         * 只需要满足任一权限
         */
        OR
    }
}
