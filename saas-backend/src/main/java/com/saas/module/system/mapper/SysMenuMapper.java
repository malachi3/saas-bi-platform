package com.saas.module.system.mapper;

import com.saas.common.core.BaseMapper;
import com.saas.module.system.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜单 Mapper 接口
 *
 * @author saas
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 查询用户菜单树
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    List<SysMenu> selectMenuTreeByUserId(@Param("userId") Long userId);

    /**
     * 查询所有菜单树
     *
     * @param queryWrapper 查询条件
     * @return 菜单列表
     */
    List<SysMenu> selectMenuTree(@Param("ew") com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<SysMenu> queryWrapper);

    /**
     * 查询角色菜单列表
     *
     * @param roleId 角色ID
     * @return 菜单列表
     */
    List<SysMenu> selectMenusByRoleId(@Param("roleId") Long roleId);

    /**
     * 查询用户权限标识列表
     *
     * @param userId 用户ID
     * @return 权限标识列表
     */
    List<String> selectPermsByUserId(@Param("userId") Long userId);

    /**
     * 查询角色权限标识列表
     *
     * @param roleId 角色ID
     * @return 权限标识列表
     */
    List<String> selectPermsByRoleId(@Param("roleId") Long roleId);

    /**
     * 查询菜单下所有按钮权限标识
     *
     * @param parentId 父菜单ID
     * @return 权限标识列表
     */
    List<String> selectButtonPermsByParentId(@Param("parentId") Long parentId);
}
