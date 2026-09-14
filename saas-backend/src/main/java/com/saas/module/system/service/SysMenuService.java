package com.saas.module.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.saas.module.system.entity.SysMenu;

import java.util.List;

/**
 * 菜单服务接口
 *
 * @author saas
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 获取用户菜单树
     *
     * @param userId 用户ID
     * @return 菜单树
     */
    List<SysMenu> getUserMenus(Long userId);

    /**
     * 获取所有菜单树
     *
     * @return 菜单树
     */
    List<SysMenu> getAllMenus();

    /**
     * 根据角色ID获取菜单列表
     *
     * @param roleId 角色ID
     * @return 菜单列表
     */
    List<SysMenu> getMenusByRoleId(Long roleId);

    /**
     * 获取用户权限标识
     *
     * @param userId 用户ID
     * @return 权限标识列表
     */
    List<String> getUserPermissions(Long userId);

    /**
     * 获取角色权限标识
     *
     * @param roleId 角色ID
     * @return 权限标识列表
     */
    List<String> getRolePermissions(Long roleId);

    /**
     * 创建菜单
     *
     * @param menu 菜单信息
     * @return 菜单ID
     */
    Long createMenu(SysMenu menu);

    /**
     * 更新菜单
     *
     * @param menu 菜单信息
     * @return 是否成功
     */
    boolean updateMenu(SysMenu menu);

    /**
     * 删除菜单
     *
     * @param menuId 菜单ID
     * @return 是否成功
     */
    boolean deleteMenu(Long menuId);

    /**
     * 校验菜单名称是否唯一
     *
     * @param menuName  菜单名称
     * @param parentId  父菜单ID
     * @param excludeId 排除的菜单ID
     * @return 是否唯一
     */
    boolean checkMenuNameUnique(String menuName, Long parentId, Long excludeId);

    /**
     * 校验菜单是否可删除
     *
     * @param menuId 菜单ID
     * @return 是否可删除
     */
    boolean canDelete(Long menuId);

    /**
     * 构建菜单树
     *
     * @param menus 菜单列表
     * @return 菜单树
     */
    List<SysMenu> buildMenuTree(List<SysMenu> menus);

    /**
     * 获取菜单路径
     *
     * @param menuId 菜单ID
     * @return 菜单路径
     */
    List<SysMenu> getMenuPath(Long menuId);
}
