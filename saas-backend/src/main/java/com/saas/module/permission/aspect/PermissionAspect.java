package com.saas.module.permission.aspect;

import com.saas.common.security.LoginUser;
import com.saas.common.web.GlobalExceptionHandler.ForbiddenException;
import com.saas.module.permission.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 权限校验切面
 *
 * @author saas
 */
@Aspect
@Component
@RequiredArgsConstructor
public class PermissionAspect {

    private static final Logger log = LoggerFactory.getLogger(PermissionAspect.class);

    private final PermissionService permissionService;

    @Before("@annotation(requirePermission)")
    public void checkPermission(JoinPoint point, RequirePermission requirePermission) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof LoginUser loginUser)) {
            throw new ForbiddenException("未登录或登录已过期");
        }

        String[] permissions = requirePermission.value();
        RequirePermission.Logical logical = requirePermission.logical();

        if (logical == RequirePermission.Logical.AND) {
            // 需要满足所有权限
            for (String permission : permissions) {
                if (!permissionService.hasPermission(loginUser.getUserId(), permission)) {
                    log.warn("用户 {} 缺少权限: {}", loginUser.getUsername(), permission);
                    throw new ForbiddenException("没有访问权限: " + permission);
                }
            }
        } else {
            // 只需要满足任一权限
            List<String> permissionList = Arrays.asList(permissions);
            boolean hasPermission = permissionService.hasAnyPermission(loginUser.getUserId(), permissionList);
            if (!hasPermission) {
                log.warn("用户 {} 缺少权限: {}", loginUser.getUsername(), permissionList);
                throw new ForbiddenException("没有访问权限");
            }
        }
    }
}
