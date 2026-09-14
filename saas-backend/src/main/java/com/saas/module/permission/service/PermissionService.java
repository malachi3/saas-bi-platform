package com.saas.module.permission.service;

import java.util.List;

/**
 * 权限检查服务接口
 *
 * @author saas
 */
public interface PermissionService {

    /**
     * 检查用户是否拥有指定权限
     *
     * @param userId      用户ID
     * @param permission  权限标识
     * @return 是否有权限
     */
    boolean hasPermission(Long userId, String permission);

    /**
     * 检查用户是否拥有所有指定权限
     *
     * @param userId      用户ID
     * @param permissions 权限标识列表
     * @return 是否拥有所有权限
     */
    boolean hasAllPermissions(Long userId, List<String> permissions);

    /**
     * 检查用户是否拥有任一指定权限
     *
     * @param userId      用户ID
     * @param permissions 权限标识列表
     * @return 是否拥有任一权限
     */
    boolean hasAnyPermission(Long userId, List<String> permissions);

    /**
     * 检查用户是否拥有指定角色
     *
     * @param userId 用户ID
     * @param role   角色编码
     * @return 是否有角色
     */
    boolean hasRole(Long userId, String role);

    /**
     * 检查用户是否拥有所有指定角色
     *
     * @param userId 用户ID
     * @param roles  角色编码列表
     * @return 是否拥有所有角色
     */
    boolean hasAllRoles(Long userId, List<String> roles);

    /**
     * 检查用户是否拥有任一指定角色
     *
     * @param userId 用户ID
     * @param roles  角色编码列表
     * @return 是否拥有任一角色
     */
    boolean hasAnyRole(Long userId, List<String> roles);

    /**
     * 获取用户的所有权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    List<String> getUserPermissions(Long userId);

    /**
     * 获取用户的角色列表
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    List<String> getUserRoles(Long userId);
}
