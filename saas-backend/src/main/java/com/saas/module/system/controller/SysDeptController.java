package com.saas.module.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.saas.common.web.PageDTO;
import com.saas.common.web.R;
import com.saas.module.system.entity.SysDept;
import com.saas.module.system.service.SysDeptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 部门管理 Controller
 *
 * @author saas
 */
@Tag(name = "部门管理", description = "部门管理接口")
@RestController
@RequestMapping("/api/v1/depts")
@RequiredArgsConstructor
public class SysDeptController {

    private final SysDeptService sysDeptService;

    @Operation(summary = "分页查询部门")
    @GetMapping("/page")
    @PreAuthorize("@ss.hasPermission('system:dept:list')")
    public R<IPage<SysDept>> page(PageDTO pageDTO, SysDept dept) {
        IPage<SysDept> result = sysDeptService.pageDept(pageDTO, dept);
        return R.ok(result);
    }

    @Operation(summary = "获取部门树")
    @GetMapping("/tree")
    @PreAuthorize("@ss.hasPermission('system:dept:list')")
    public R<List<SysDept>> getTree(SysDept dept) {
        List<SysDept> tree = sysDeptService.getDeptTree(dept);
        return R.ok(tree);
    }

    @Operation(summary = "获取所有部门列表")
    @GetMapping("/list")
    @PreAuthorize("@ss.hasPermission('system:dept:list')")
    public R<List<SysDept>> list() {
        return R.ok(sysDeptService.listAll());
    }

    @Operation(summary = "获取部门详情")
    @GetMapping("/{id}")
    @PreAuthorize("@ss.hasPermission('system:dept:query')")
    public R<SysDept> getById(@Parameter(description = "部门ID") @PathVariable Long id) {
        SysDept dept = sysDeptService.getById(id);
        return dept != null ? R.ok(dept) : R.fail(40401, "部门不存在");
    }

    @Operation(summary = "创建部门")
    @PostMapping
    @PreAuthorize("@ss.hasPermission('system:dept:create')")
    public R<Long> create(@RequestBody SysDept dept) {
        Long deptId = sysDeptService.createDept(dept);
        return R.ok(deptId);
    }

    @Operation(summary = "更新部门")
    @PutMapping("/{id}")
    @PreAuthorize("@ss.hasPermission('system:dept:update')")
    public R<Boolean> update(@Parameter(description = "部门ID") @PathVariable Long id, @RequestBody SysDept dept) {
        dept.setId(id);
        return R.ok(sysDeptService.updateDept(dept));
    }

    @Operation(summary = "删除部门")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ss.hasPermission('system:dept:delete')")
    public R<Boolean> delete(@Parameter(description = "部门ID") @PathVariable Long id) {
        return R.ok(sysDeptService.deleteDept(id));
    }

    @Operation(summary = "修改部门状态")
    @PutMapping("/{id}/status")
    @PreAuthorize("@ss.hasPermission('system:dept:update')")
    public R<Boolean> changeStatus(
            @Parameter(description = "部门ID") @PathVariable Long id,
            @RequestParam String status) {
        SysDept dept = new SysDept();
        dept.setId(id);
        dept.setStatus(status);
        return R.ok(sysDeptService.updateById(dept));
    }
}
