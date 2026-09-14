package com.saas.module.permission.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.saas.common.core.BaseServiceImpl;
import com.saas.common.enums.ErrorCode;
import com.saas.common.tenant.TenantContextHolder;
import com.saas.common.web.GlobalExceptionHandler.BusinessException;
import com.saas.common.web.PageDTO;
import com.saas.module.permission.service.PermRoleService;
import com.saas.module.system.entity.SysMenu;
import com.saas.module.system.entity.SysRole;
import com.saas.module.system.entity.SysRoleMenu;
import com.saas.module.system.mapper.SysMenuMapper;
import com.saas.module.system.mapper.SysRoleMapper;
import com.saas.module.system.mapper.SysRoleMenuMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色权限服务实现
 *
 * @author saas
 */
@Service
@RequiredArgsConstructor
public class PermRoleServiceImpl extends BaseServiceImpl<SysRoleMapper, SysRole> implements PermRoleService {

    private final SysRoleMapper sysRoleMapper;
    private final SysRoleMenuMapper sysRoleMenuMapper;
    private final SysMenuMapper sysMenuMapper;

    @Override
    public IPage<SysRole> pageRole(PageDTO pageDTO, SysRole role) {
        Page<SysRole> page = new Page<>(pageDTO.getCurrent(), pageDTO.getSize());
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        if (role.getName() != null) {
            queryWrapper.like(SysRole::getName, role.getName());
        }
        if (role.getCode() != null) {
            queryWrapper.eq(SysRole::getCode, role.getCode());
        }
        if (role.getStatus() != null) {
            queryWrapper.eq(SysRole::getStatus, role.getStatus());
        }
        queryWrapper.orderByAsc(SysRole::getSort);
        return sysRoleMapper.selectRolePage(page, queryWrapper);
    }

    @Override
    public SysRole getById(Long roleId) {
        return sysRoleMapper.selectById(roleId);
    }

    @Override
    public List<SysRole> listAll() {
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(SysRole::getSort);
        return sysRoleMapper.selectList(queryWrapper);
    }

    @Override
    public SysRole getByCode(String code) {
        return sysRoleMapper.selectByCode(code);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createRole(SysRole role, List<Long> menuIds) {
        // 校验编码唯一性
        if (!checkCodeUnique(role.getCode(), null)) {
            throw new BusinessException(ErrorCode.RESOURCE_CONFLICT);
        }

        // 设置租户ID
        role.setTenantId(TenantContextHolder.getTenantId());
        sysRoleMapper.insert(role);

        // 保存角色菜单关联
        if (menuIds != null && !menuIds.isEmpty()) {
            saveRoleMenus(role.getId(), menuIds);
        }

        return role.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateRole(SysRole role, List<Long> menuIds) {
        // 校验编码唯一性
        if (!checkCodeUnique(role.getCode(), role.getId())) {
            throw new BusinessException(ErrorCode.RESOURCE_CONFLICT);
        }

        // 不允许修改系统内置角色
        if (role.getType() == 0) {
            throw new BusinessException(ErrorCode.FORBIDDEN);
        }

        sysRoleMapper.updateById(role);

        // 更新角色菜单关联
        sysRoleMenuMapper.delete(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getRoleId, role.getId()));
        if (menuIds != null && !menuIds.isEmpty()) {
            saveRoleMenus(role.getId(), menuIds);
        }

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteRole(Long roleId) {
        SysRole role = sysRoleMapper.selectById(roleId);
        if (role != null && role.getType() == 0) {
            throw new BusinessException(ErrorCode.FORBIDDEN);
        }
        // 删除角色菜单关联
        sysRoleMenuMapper.delete(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getRoleId, roleId));
        return sysRoleMapper.deleteById(roleId) > 0;
    }

    @Override
    public boolean enableRole(Long roleId) {
        SysRole role = new SysRole();
        role.setId(roleId);
        role.setStatus(1);
        return sysRoleMapper.updateById(role) > 0;
    }

    @Override
    public boolean disableRole(Long roleId) {
        SysRole role = new SysRole();
        role.setId(roleId);
        role.setStatus(0);
        return sysRoleMapper.updateById(role) > 0;
    }

    @Override
    public boolean checkCodeUnique(String code, Long excludeId) {
        return sysRoleMapper.countByCode(code, excludeId) == 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean assignMenus(Long roleId, List<Long> menuIds) {
        // 删除旧的菜单关联
        sysRoleMenuMapper.delete(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getRoleId, roleId));

        // 保存新的菜单关联
        if (menuIds != null && !menuIds.isEmpty()) {
            saveRoleMenus(roleId, menuIds);
        }

        return true;
    }

    /**
     * 保存角色菜单关联
     */
    private void saveRoleMenus(Long roleId, List<Long> menuIds) {
        Long tenantId = TenantContextHolder.getTenantId();
        for (Long menuId : menuIds) {
            SysRoleMenu roleMenu = new SysRoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            roleMenu.setTenantId(tenantId);
            sysRoleMenuMapper.insert(roleMenu);
        }
    }
}
