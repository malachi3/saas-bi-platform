package com.saas.module.system.controller;

import com.saas.common.web.R;
import com.saas.module.system.entity.SysMenu;
import com.saas.module.system.service.SysMenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜单管理 Controller
 *
 * @author saas
 */
@Tag(name = "菜单管理", description = "菜单管理接口")
@RestController
@RequestMapping("/api/v1/menus")
@RequiredArgsConstructor
public class SysMenuController {

    private final SysMenuService sysMenuService;

    @Operation(summary = "获取当前用户菜单")
    @GetMapping("/user")
    public R<List<SysMenu>> getUserMenus() {
        // TODO: 从安全上下文中获取当前用户ID
        Long userId = 1L;
        List<SysMenu> menus = sysMenuService.getUserMenus(userId);
        return R.ok(menus);
    }

    @Operation(summary = "获取所有菜单树")
    @GetMapping("/tree")
    @PreAuthorize("@ss.hasPermission('system:menu:list')")
    public R<List<SysMenu>> getTree() {
        List<SysMenu> tree = sysMenuService.getAllMenus();
        return R.ok(tree);
    }

    @Operation(summary = "根据角色获取菜单")
    @GetMapping("/role/{roleId}")
    @PreAuthorize("@ss.hasPermission('system:menu:list')")
    public R<List<SysMenu>> getMenusByRole(@Parameter(description = "角色ID") @PathVariable Long roleId) {
        List<SysMenu> menus = sysMenuService.getMenusByRoleId(roleId);
        return R.ok(menus);
    }

    @Operation(summary = "获取菜单详情")
    @GetMapping("/{id}")
    @PreAuthorize("@ss.hasPermission('system:menu:query')")
    public R<SysMenu> getById(@Parameter(description = "菜单ID") @PathVariable Long id) {
        SysMenu menu = sysMenuService.getById(id);
        return menu != null ? R.ok(menu) : R.fail(40401, "菜单不存在");
    }

    @Operation(summary = "获取菜单路径")
    @GetMapping("/{id}/path")
    @PreAuthorize("@ss.hasPermission('system:menu:query')")
    public R<List<SysMenu>> getMenuPath(@Parameter(description = "菜单ID") @PathVariable Long id) {
        List<SysMenu> path = sysMenuService.getMenuPath(id);
        return R.ok(path);
    }

    @Operation(summary = "创建菜单")
    @PostMapping
    @PreAuthorize("@ss.hasPermission('system:menu:create')")
    public R<Long> create(@RequestBody SysMenu menu) {
        Long menuId = sysMenuService.createMenu(menu);
        return R.ok(menuId);
    }

    @Operation(summary = "更新菜单")
    @PutMapping("/{id}")
    @PreAuthorize("@ss.hasPermission('system:menu:update')")
    public R<Boolean> update(@Parameter(description = "菜单ID") @PathVariable Long id, @RequestBody SysMenu menu) {
        menu.setId(id);
        boolean result = sysMenuService.updateMenu(menu);
        return R.ok(result);
    }

    @Operation(summary = "删除菜单")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ss.hasPermission('system:menu:delete')")
    public R<Boolean> delete(@Parameter(description = "菜单ID") @PathVariable Long id) {
        boolean result = sysMenuService.deleteMenu(id);
        return R.ok(result);
    }

    @Operation(summary = "修改菜单状态")
    @PutMapping("/{id}/status")
    @PreAuthorize("@ss.hasPermission('system:menu:update')")
    public R<Boolean> changeStatus(
            @Parameter(description = "菜单ID") @PathVariable Long id,
            @RequestParam String status) {
        SysMenu menu = new SysMenu();
        menu.setId(id);
        menu.setStatus(status);
        boolean result = sysMenuService.updateMenu(menu);
        return R.ok(result);
    }
}
