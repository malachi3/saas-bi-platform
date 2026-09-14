package com.saas.module.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.saas.common.core.BaseServiceImpl;
import com.saas.common.enums.ErrorCode;
import com.saas.common.tenant.TenantContextHolder;
import com.saas.common.web.GlobalExceptionHandler.BusinessException;
import com.saas.module.system.entity.SysMenu;
import com.saas.module.system.entity.SysRoleMenu;
import com.saas.module.system.mapper.SysMenuMapper;
import com.saas.module.system.mapper.SysRoleMenuMapper;
import com.saas.module.system.service.SysMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 菜单服务实现类
 *
 * @author saas
 */
@Service
@RequiredArgsConstructor
public class SysMenuServiceImpl extends BaseServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    private final SysMenuMapper sysMenuMapper;
    private final SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public List<SysMenu> getUserMenus(Long userId) {
        List<SysMenu> menus = sysMenuMapper.selectMenuTreeByUserId(userId);
        return buildMenuTree(menus);
    }

    @Override
    public List<SysMenu> getAllMenus() {
        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(SysMenu::getOrderNum);
        List<SysMenu> menus = sysMenuMapper.selectMenuTree(queryWrapper);
        return buildMenuTree(menus);
    }

    @Override
    public List<SysMenu> getMenusByRoleId(Long roleId) {
        return sysMenuMapper.selectMenusByRoleId(roleId);
    }

    @Override
    public List<String> getUserPermissions(Long userId) {
        return sysMenuMapper.selectPermsByUserId(userId);
    }

    @Override
    public List<String> getRolePermissions(Long roleId) {
        return sysMenuMapper.selectPermsByRoleId(roleId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createMenu(SysMenu menu) {
        // 校验名称唯一性
        if (!checkMenuNameUnique(menu.getMenuName(), menu.getParentId(), null)) {
            throw new BusinessException(ErrorCode.RESOURCE_CONFLICT);
        }

        // 设置租户ID
        menu.setTenantId(TenantContextHolder.getTenantId());

        // 构建祖级列表
        if (menu.getParentId() != null && menu.getParentId() > 0) {
            SysMenu parent = sysMenuMapper.selectById(menu.getParentId());
            if (parent != null) {
                menu.setAncestors(parent.getAncestors() + "," + menu.getParentId());
            }
        } else {
            menu.setAncestors("0");
            menu.setParentId(0L);
        }

        sysMenuMapper.insert(menu);
        return menu.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateMenu(SysMenu menu) {
        // 校验名称唯一性
        if (!checkMenuNameUnique(menu.getMenuName(), menu.getParentId(), menu.getId())) {
            throw new BusinessException(ErrorCode.RESOURCE_CONFLICT);
        }

        // 获取旧数据
        SysMenu oldMenu = sysMenuMapper.selectById(menu.getId());
        if (oldMenu == null) {
            throw new BusinessException(ErrorCode.MENU_NOT_FOUND);
        }

        // 如果父菜单变更，需要更新子菜单的祖级列表
        if (!oldMenu.getParentId().equals(menu.getParentId())) {
            List<SysMenu> allMenus = sysMenuMapper.selectList(null);
            List<Long> childIds = allMenus.stream()
                    .filter(m -> m.getAncestors() != null && m.getAncestors().contains("," + menu.getId() + ","))
                    .map(SysMenu::getId)
                    .collect(Collectors.toList());

            for (Long childId : childIds) {
                SysMenu child = sysMenuMapper.selectById(childId);
                if (child != null) {
                    String newAncestors = menu.getAncestors() + "," + menu.getId();
                    child.setAncestors(child.getAncestors().replace(oldMenu.getAncestors(), newAncestors));
                    sysMenuMapper.updateById(child);
                }
            }
        }

        return sysMenuMapper.updateById(menu) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteMenu(Long menuId) {
        if (!canDelete(menuId)) {
            throw new BusinessException(ErrorCode.RESOURCE_CONFLICT);
        }
        // 删除角色菜单关联
        sysRoleMenuMapper.delete(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getMenuId, menuId));
        return sysMenuMapper.deleteById(menuId) > 0;
    }

    @Override
    public boolean checkMenuNameUnique(String menuName, Long parentId, Long excludeId) {
        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<SysMenu>()
                .eq(SysMenu::getMenuName, menuName)
                .eq(SysMenu::getParentId, parentId != null ? parentId : 0)
                .ne(excludeId != null, SysMenu::getId, excludeId);
        return sysMenuMapper.selectCount(queryWrapper) == 0;
    }

    @Override
    public boolean canDelete(Long menuId) {
        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<SysMenu>()
                .like(SysMenu::getAncestors, menuId);
        return sysMenuMapper.selectCount(queryWrapper) == 0;
    }

    @Override
    public List<SysMenu> buildMenuTree(List<SysMenu> menus) {
        List<SysMenu> result = new ArrayList<>();
        Map<Long, List<SysMenu>> menuMap = menus.stream()
                .collect(Collectors.groupingBy(SysMenu::getParentId));

        buildMenuTreeRecursive(result, 0L, menuMap);
        return result;
    }

    private void buildMenuTreeRecursive(List<SysMenu> result, Long parentId, Map<Long, List<SysMenu>> menuMap) {
        List<SysMenu> children = menuMap.get(parentId);
        if (children != null) {
            for (SysMenu menu : children) {
                result.add(menu);
                buildMenuTreeRecursive(result, menu.getId(), menuMap);
            }
        }
    }

    @Override
    public List<SysMenu> getMenuPath(Long menuId) {
        List<SysMenu> path = new ArrayList<>();
        SysMenu menu = sysMenuMapper.selectById(menuId);
        if (menu != null && menu.getAncestors() != null) {
            String[] ancestorIds = menu.getAncestors().split(",");
            for (String ancestorId : ancestorIds) {
                if (!"0".equals(ancestorId)) {
                    SysMenu ancestor = sysMenuMapper.selectById(Long.parseLong(ancestorId));
                    if (ancestor != null) {
                        path.add(ancestor);
                    }
                }
            }
            path.add(menu);
        }
        return path;
    }
}
