package com.saas.module.permission.service.impl;

import com.saas.common.security.LoginUser;
import com.saas.module.permission.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * 权限表达式服务
 * 用于 @PreAuthorize 注解中
 *
 * @author saas
 */
@Component("ss")
@RequiredArgsConstructor
public class SecurityExpressionService {

    private final PermissionService permissionService;

    /**
     * 检查当前用户是否拥有指定权限
     */
    public boolean hasPermission(String permission) {
        LoginUser loginUser = getCurrentLoginUser();
        if (loginUser == null) {
            return false;
        }
        return permissionService.hasPermission(loginUser.getUserId(), permission);
    }

    /**
     * 检查当前用户是否拥有所有指定权限
     */
    public boolean hasAllPermissions(String... permissions) {
        LoginUser loginUser = getCurrentLoginUser();
        if (loginUser == null) {
            return false;
        }
        return permissionService.hasAllPermissions(loginUser.getUserId(), java.util.Arrays.asList(permissions));
    }

    /**
     * 检查当前用户是否拥有任一指定权限
     */
    public boolean hasAnyPermission(String... permissions) {
        LoginUser loginUser = getCurrentLoginUser();
        if (loginUser == null) {
            return false;
        }
        return permissionService.hasAnyPermission(loginUser.getUserId(), java.util.Arrays.asList(permissions));
    }

    /**
     * 检查当前用户是否拥有指定角色
     */
    public boolean hasRole(String role) {
        LoginUser loginUser = getCurrentLoginUser();
        if (loginUser == null) {
            return false;
        }
        return permissionService.hasRole(loginUser.getUserId(), role);
    }

    /**
     * 检查当前用户是否拥有所有指定角色
     */
    public boolean hasAllRoles(String... roles) {
        LoginUser loginUser = getCurrentLoginUser();
        if (loginUser == null) {
            return false;
        }
        return permissionService.hasAllRoles(loginUser.getUserId(), java.util.Arrays.asList(roles));
    }

    /**
     * 检查当前用户是否拥有任一指定角色
     */
    public boolean hasAnyRole(String... roles) {
        LoginUser loginUser = getCurrentLoginUser();
        if (loginUser == null) {
            return false;
        }
        return permissionService.hasAnyRole(loginUser.getUserId(), java.util.Arrays.asList(roles));
    }

    /**
     * 检查当前用户是否为超管
     */
    public boolean isSuperAdmin() {
        LoginUser loginUser = getCurrentLoginUser();
        return loginUser != null && loginUser.isSuperAdmin();
    }

    /**
     * 获取当前登录用户
     */
    private LoginUser getCurrentLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof LoginUser loginUser) {
            return loginUser;
        }
        return null;
    }
}
