package com.saas.module.permission.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.saas.common.web.PageDTO;
import com.saas.common.web.R;
import com.saas.module.system.entity.SysMenu;
import com.saas.module.system.entity.SysRole;
import com.saas.module.permission.service.PermRoleService;
import com.saas.module.system.service.SysMenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色权限管理 Controller
 *
 * @author saas
 */
@Tag(name = "角色权限管理", description = "角色和权限管理接口")
@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class PermRoleController {

    private final PermRoleService permRoleService;
    private final SysMenuService sysMenuService;

    @Operation(summary = "分页查询角色")
    @GetMapping("/page")
    @PreAuthorize("@ss.hasPermission('permission:role:list')")
    public R<IPage<SysRole>> page(PageDTO pageDTO, SysRole role) {
        IPage<SysRole> result = permRoleService.pageRole(pageDTO, role);
        return R.ok(result);
    }

    @Operation(summary = "获取角色详情")
    @GetMapping("/{id}")
    @PreAuthorize("@ss.hasPermission('permission:role:query')")
    public R<SysRole> getById(@Parameter(description = "角色ID") @PathVariable Long id) {
        SysRole role = permRoleService.getById(id);
        return role != null ? R.ok(role) : R.notFound("角色不存在");
    }

    @Operation(summary = "获取所有角色")
    @GetMapping("/list")
    @PreAuthorize("@ss.hasPermission('permission:role:list')")
    public R<List<SysRole>> list() {
        List<SysRole> list = permRoleService.listAll();
        return R.ok(list);
    }

    @Operation(summary = "创建角色")
    @PostMapping
    @PreAuthorize("@ss.hasPermission('permission:role:create')")
    public R<Long> create(@RequestBody SysRole role) {
        Long roleId = permRoleService.createRole(role, role.getMenuIds());
        return R.ok(roleId);
    }

    @Operation(summary = "更新角色")
    @PutMapping("/{id}")
    @PreAuthorize("@ss.hasPermission('permission:role:update')")
    public R<Boolean> update(
            @Parameter(description = "角色ID") @PathVariable Long id,
            @RequestBody SysRole role) {
        role.setId(id);
        boolean result = permRoleService.updateRole(role, role.getMenuIds());
        return R.ok(result);
    }

    @Operation(summary = "删除角色")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ss.hasPermission('permission:role:delete')")
    public R<Boolean> delete(@Parameter(description = "角色ID") @PathVariable Long id) {
        boolean result = permRoleService.deleteRole(id);
        return R.ok(result);
    }

    @Operation(summary = "启用角色")
    @PutMapping("/{id}/enable")
    @PreAuthorize("@ss.hasPermission('permission:role:update')")
    public R<Boolean> enable(@Parameter(description = "角色ID") @PathVariable Long id) {
        boolean result = permRoleService.enableRole(id);
        return R.ok(result);
    }

    @Operation(summary = "禁用角色")
    @PutMapping("/{id}/disable")
    @PreAuthorize("@ss.hasPermission('permission:role:update')")
    public R<Boolean> disable(@Parameter(description = "角色ID") @PathVariable Long id) {
        boolean result = permRoleService.disableRole(id);
        return R.ok(result);
    }

    @Operation(summary = "获取角色菜单")
    @GetMapping("/{id}/menus")
    @PreAuthorize("@ss.hasPermission('permission:role:query')")
    public R<List<SysMenu>> getRoleMenus(@Parameter(description = "角色ID") @PathVariable Long id) {
        List<SysMenu> menus = sysMenuService.getMenusByRoleId(id);
        return R.ok(menus);
    }

    @Operation(summary = "分配角色菜单")
    @PutMapping("/{id}/menus")
    @PreAuthorize("@ss.hasPermission('permission:role:assignMenu')")
    public R<Boolean> assignMenus(
            @Parameter(description = "角色ID") @PathVariable Long id,
            @RequestBody List<Long> menuIds) {
        boolean result = permRoleService.assignMenus(id, menuIds);
        return R.ok(result);
    }
}
