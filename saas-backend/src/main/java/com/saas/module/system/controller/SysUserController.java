package com.saas.module.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.saas.common.security.LoginUser;
import com.saas.common.web.PageDTO;
import com.saas.common.web.R;
import com.saas.module.system.entity.SysUser;
import com.saas.module.system.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户管理 Controller
 *
 * @author saas
 */
@Tag(name = "用户管理", description = "用户管理接口")
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class SysUserController {

    private final SysUserService sysUserService;

    @Operation(summary = "分页查询用户")
    @GetMapping("/page")
    @PreAuthorize("@ss.hasPermission('system:user:list')")
    public R<IPage<SysUser>> page(PageDTO pageDTO, SysUser user) {
        IPage<SysUser> result = sysUserService.pageUser(pageDTO, user);
        return R.ok(result);
    }

    @Operation(summary = "获取用户详情")
    @GetMapping("/{id}")
    @PreAuthorize("@ss.hasPermission('system:user:query')")
    public R<SysUser> getById(@Parameter(description = "用户ID") @PathVariable Long id) {
        SysUser user = sysUserService.getById(id);
        return user != null ? R.ok(user) : R.fail(40401, "用户不存在");
    }

    @Operation(summary = "获取当前用户信息")
    @GetMapping("/me")
    public R<LoginUser> getCurrentUser(@AuthenticationPrincipal LoginUser loginUser) {
        return R.ok(loginUser);
    }

    @Operation(summary = "创建用户")
    @PostMapping
    @PreAuthorize("@ss.hasPermission('system:user:create')")
    public R<Long> create(@RequestBody SysUser user) {
        List<Long> roleIds = user.getRoleIds();
        Long userId = sysUserService.createUser(user, roleIds);
        return R.ok(userId);
    }

    @Operation(summary = "更新用户")
    @PutMapping("/{id}")
    @PreAuthorize("@ss.hasPermission('system:user:update')")
    public R<Boolean> update(@Parameter(description = "用户ID") @PathVariable Long id, @RequestBody SysUser user) {
        user.setId(id);
        List<Long> roleIds = user.getRoleIds();
        return R.ok(sysUserService.updateUser(user, roleIds));
    }

    @Operation(summary = "删除用户")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ss.hasPermission('system:user:delete')")
    public R<Boolean> delete(@Parameter(description = "用户ID") @PathVariable Long id) {
        return R.ok(sysUserService.deleteUser(id));
    }

    @Operation(summary = "批量删除用户")
    @DeleteMapping("/batch")
    @PreAuthorize("@ss.hasPermission('system:user:delete')")
    public R<Boolean> deleteBatch(@RequestBody List<Long> ids) {
        for (Long id : ids) {
            sysUserService.deleteUser(id);
        }
        return R.ok(true);
    }

    @Operation(summary = "修改密码")
    @PutMapping("/{id}/password")
    @PreAuthorize("@ss.hasPermission('system:user:resetPwd')")
    public R<Boolean> changePassword(
            @Parameter(description = "用户ID") @PathVariable Long id,
            @RequestParam String oldPassword,
            @RequestParam String newPassword) {
        boolean result = sysUserService.changePassword(id, oldPassword, newPassword);
        return R.ok(result);
    }

    @Operation(summary = "重置密码")
    @PutMapping("/{id}/reset-password")
    @PreAuthorize("@ss.hasPermission('system:user:resetPwd')")
    public R<Boolean> resetPassword(
            @Parameter(description = "用户ID") @PathVariable Long id,
            @RequestParam String newPassword) {
        boolean result = sysUserService.resetPassword(id, newPassword);
        return R.ok(result);
    }

    @Operation(summary = "修改用户状态")
    @PutMapping("/{id}/status")
    @PreAuthorize("@ss.hasPermission('system:user:update')")
    public R<Boolean> changeStatus(
            @Parameter(description = "用户ID") @PathVariable Long id,
            @RequestParam String status) {
        SysUser user = new SysUser();
        user.setId(id);
        user.setStatus(status);
        return R.ok(sysUserService.updateById(user));
    }

    @Operation(summary = "导出用户数据")
    @GetMapping("/export")
    @PreAuthorize("@ss.hasPermission('system:user:export')")
    public void export(SysUser user) {
        // TODO: 实现导出功能
    }
}
