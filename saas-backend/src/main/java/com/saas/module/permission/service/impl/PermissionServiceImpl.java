package com.saas.module.permission.service.impl;

import com.saas.module.permission.service.PermissionService;
import com.saas.module.system.mapper.SysMenuMapper;
import com.saas.module.system.mapper.SysRoleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 权限检查服务实现
 *
 * @author saas
 */
@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final SysMenuMapper sysMenuMapper;
    private final SysRoleMapper sysRoleMapper;

    @Override
    public boolean hasPermission(Long userId, String permission) {
        List<String> permissions = getUserPermissions(userId);
        return permissions.contains(permission);
    }

    @Override
    public boolean hasAllPermissions(Long userId, List<String> permissions) {
        List<String> userPermissions = getUserPermissions(userId);
        return new HashSet<>(userPermissions).containsAll(permissions);
    }

    @Override
    public boolean hasAnyPermission(Long userId, List<String> permissions) {
        List<String> userPermissions = getUserPermissions(userId);
        Set<String> permissionSet = new HashSet<>(userPermissions);
        return permissions.stream().anyMatch(permissionSet::contains);
    }

    @Override
    public boolean hasRole(Long userId, String role) {
        List<String> roles = getUserRoles(userId);
        return roles.contains(role);
    }

    @Override
    public boolean hasAllRoles(Long userId, List<String> roles) {
        List<String> userRoles = getUserRoles(userId);
        return new HashSet<>(userRoles).containsAll(roles);
    }

    @Override
    public boolean hasAnyRole(Long userId, List<String> roles) {
        List<String> userRoles = getUserRoles(userId);
        Set<String> roleSet = new HashSet<>(userRoles);
        return roles.stream().anyMatch(roleSet::contains);
    }

    @Override
    public List<String> getUserPermissions(Long userId) {
        List<String> permissions = sysMenuMapper.selectPermsByUserId(userId);
        return permissions.stream()
                .filter(p -> p != null && !p.isEmpty())
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getUserRoles(Long userId) {
        List<String> roles = sysRoleMapper.selectRolesByUserId(userId).stream()
                .map(r -> r.getCode())
                .filter(c -> c != null && !c.isEmpty())
                .collect(Collectors.toList());
        return roles;
    }
}
