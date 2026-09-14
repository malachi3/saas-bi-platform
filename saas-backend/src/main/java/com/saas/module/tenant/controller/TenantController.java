package com.saas.module.tenant.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.saas.common.web.PageDTO;
import com.saas.common.web.R;
import com.saas.module.tenant.entity.SysTenant;
import com.saas.module.tenant.entity.SysTenantPackage;
import com.saas.module.tenant.service.TenantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 租户管理 Controller
 *
 * @author saas
 */
@Tag(name = "租户管理", description = "租户管理接口")
@RestController
@RequestMapping("/api/v1/tenants")
@RequiredArgsConstructor
public class TenantController {

    private final TenantService tenantService;

    @Operation(summary = "分页查询租户")
    @GetMapping("/page")
    @PreAuthorize("@ss.hasPermission('tenant:tenant:list')")
    public R<IPage<SysTenant>> page(PageDTO pageDTO, SysTenant tenant) {
        IPage<SysTenant> result = tenantService.pageTenant(pageDTO, tenant);
        return R.ok(result);
    }

    @Operation(summary = "获取租户详情")
    @GetMapping("/{id}")
    @PreAuthorize("@ss.hasPermission('tenant:tenant:query')")
    public R<SysTenant> getById(@Parameter(description = "租户ID") @PathVariable Long id) {
        SysTenant tenant = tenantService.getById(id);
        return tenant != null ? R.ok(tenant) : R.notFound("租户不存在");
    }

    @Operation(summary = "获取所有租户")
    @GetMapping("/list")
    @PreAuthorize("@ss.hasPermission('tenant:tenant:list')")
    public R<List<SysTenant>> list() {
        List<SysTenant> list = tenantService.listAll();
        return R.ok(list);
    }

    @Operation(summary = "创建租户")
    @PostMapping
    @PreAuthorize("@ss.hasPermission('tenant:tenant:create')")
    public R<Long> create(@RequestBody SysTenant tenant) {
        Long tenantId = tenantService.createTenant(tenant);
        return R.ok(tenantId);
    }

    @Operation(summary = "更新租户")
    @PutMapping("/{id}")
    @PreAuthorize("@ss.hasPermission('tenant:tenant:update')")
    public R<Boolean> update(
            @Parameter(description = "租户ID") @PathVariable Long id,
            @RequestBody SysTenant tenant) {
        tenant.setId(id);
        boolean result = tenantService.updateTenant(tenant);
        return R.ok(result);
    }

    @Operation(summary = "删除租户")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ss.hasPermission('tenant:tenant:delete')")
    public R<Boolean> delete(@Parameter(description = "租户ID") @PathVariable Long id) {
        boolean result = tenantService.deleteTenant(id);
        return R.ok(result);
    }

    @Operation(summary = "启用租户")
    @PutMapping("/{id}/enable")
    @PreAuthorize("@ss.hasPermission('tenant:tenant:update')")
    public R<Boolean> enable(@Parameter(description = "租户ID") @PathVariable Long id) {
        boolean result = tenantService.enableTenant(id);
        return R.ok(result);
    }

    @Operation(summary = "禁用租户")
    @PutMapping("/{id}/disable")
    @PreAuthorize("@ss.hasPermission('tenant:tenant:update')")
    public R<Boolean> disable(@Parameter(description = "租户ID") @PathVariable Long id) {
        boolean result = tenantService.disableTenant(id);
        return R.ok(result);
    }

    @Operation(summary = "获取套餐列表")
    @GetMapping("/packages")
    @PreAuthorize("@ss.hasPermission('tenant:package:list')")
    public R<List<SysTenantPackage>> listPackages() {
        List<SysTenantPackage> list = tenantService.listPackages();
        return R.ok(list);
    }

    @Operation(summary = "获取套餐详情")
    @GetMapping("/packages/{id}")
    @PreAuthorize("@ss.hasPermission('tenant:package:query')")
    public R<SysTenantPackage> getPackage(@Parameter(description = "套餐ID") @PathVariable Long id) {
        SysTenantPackage pkg = tenantService.getPackageById(id);
        return pkg != null ? R.ok(pkg) : R.notFound("套餐不存在");
    }

    @Operation(summary = "创建套餐")
    @PostMapping("/packages")
    @PreAuthorize("@ss.hasPermission('tenant:package:create')")
    public R<Long> createPackage(@RequestBody SysTenantPackage pkg) {
        Long packageId = tenantService.createPackage(pkg);
        return R.ok(packageId);
    }

    @Operation(summary = "更新套餐")
    @PutMapping("/packages/{id}")
    @PreAuthorize("@ss.hasPermission('tenant:package:update')")
    public R<Boolean> updatePackage(
            @Parameter(description = "套餐ID") @PathVariable Long id,
            @RequestBody SysTenantPackage pkg) {
        pkg.setId(id);
        boolean result = tenantService.updatePackage(pkg);
        return R.ok(result);
    }

    @Operation(summary = "删除套餐")
    @DeleteMapping("/packages/{id}")
    @PreAuthorize("@ss.hasPermission('tenant:package:delete')")
    public R<Boolean> deletePackage(@Parameter(description = "套餐ID") @PathVariable Long id) {
        boolean result = tenantService.deletePackage(id);
        return R.ok(result);
    }
}
