package com.saas.module.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.saas.common.web.PageDTO;
import com.saas.common.web.R;
import com.saas.module.system.entity.SysDict;
import com.saas.module.system.service.SysDictService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 字典管理 Controller
 *
 * @author saas
 */
@Tag(name = "字典管理", description = "字典管理接口")
@RestController
@RequestMapping("/api/v1/dicts")
@RequiredArgsConstructor
public class SysDictController {

    private final SysDictService sysDictService;

    @Operation(summary = "分页查询字典")
    @GetMapping("/page")
    @PreAuthorize("@ss.hasPermission('system:dict:list')")
    public R<IPage<SysDict>> page(PageDTO pageDTO, SysDict dict) {
        IPage<SysDict> result = sysDictService.pageDict(pageDTO, dict);
        return R.ok(result);
    }

    @Operation(summary = "获取所有字典")
    @GetMapping("/list")
    @PreAuthorize("@ss.hasPermission('system:dict:list')")
    public R<List<SysDict>> list() {
        List<SysDict> list = sysDictService.listAll();
        return R.ok(list);
    }

    @Operation(summary = "获取字典详情")
    @GetMapping("/{id}")
    @PreAuthorize("@ss.hasPermission('system:dict:query')")
    public R<SysDict> getById(@Parameter(description = "字典ID") @PathVariable Long id) {
        SysDict dict = sysDictService.getById(id);
        return dict != null ? R.ok(dict) : R.notFound("字典不存在");
    }

    @Operation(summary = "根据编码获取字典")
    @GetMapping("/code/{code}")
    @PreAuthorize("@ss.hasPermission('system:dict:query')")
    public R<SysDict> getByCode(@Parameter(description = "字典编码") @PathVariable String code) {
        SysDict dict = sysDictService.getByCode(code);
        return dict != null ? R.ok(dict) : R.notFound("字典不存在");
    }

    @Operation(summary = "创建字典")
    @PostMapping
    @PreAuthorize("@ss.hasPermission('system:dict:create')")
    public R<Long> create(@RequestBody SysDict dict) {
        Long dictId = sysDictService.createDict(dict);
        return R.ok(dictId);
    }

    @Operation(summary = "更新字典")
    @PutMapping("/{id}")
    @PreAuthorize("@ss.hasPermission('system:dict:update')")
    public R<Boolean> update(@Parameter(description = "字典ID") @PathVariable Long id, @RequestBody SysDict dict) {
        dict.setId(id);
        boolean result = sysDictService.updateDict(dict);
        return R.ok(result);
    }

    @Operation(summary = "删除字典")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ss.hasPermission('system:dict:delete')")
    public R<Boolean> delete(@Parameter(description = "字典ID") @PathVariable Long id) {
        boolean result = sysDictService.deleteDict(id);
        return R.ok(result);
    }

    @Operation(summary = "刷新字典缓存")
    @PostMapping("/{id}/refresh-cache")
    @PreAuthorize("@ss.hasPermission('system:dict:update')")
    public R<Void> refreshCache(@Parameter(description = "字典ID") @PathVariable Long id) {
        sysDictService.refreshCache(id);
        return R.ok();
    }

    @Operation(summary = "刷新所有字典缓存")
    @PostMapping("/refresh-all-cache")
    @PreAuthorize("@ss.hasPermission('system:dict:update')")
    public R<Void> refreshAllCache() {
        sysDictService.refreshAllCache();
        return R.ok();
    }
}
