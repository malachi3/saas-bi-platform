package com.saas.module.permission.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.saas.module.system.entity.SysRole;
import com.saas.common.web.PageDTO;

import java.util.List;

/**
 * 角色权限服务接口
 *
 * @author saas
 */
public interface PermRoleService {

    /**
     * 分页查询角色
     *
     * @param pageDTO 分页参数
     * @param role    查询条件
     * @return 分页结果
     */
    IPage<SysRole> pageRole(PageDTO pageDTO, SysRole role);

    /**
     * 获取角色详情
     *
     * @param roleId 角色ID
     * @return 角色信息
     */
    SysRole getById(Long roleId);

    /**
     * 获取所有角色
     *
     * @return 角色列表
     */
    List<SysRole> listAll();

    /**
     * 根据角色编码获取角色
     *
     * @param code 角色编码
     * @return 角色信息
     */
    SysRole getByCode(String code);

    /**
     * 创建角色
     *
     * @param role    角色信息
     * @param menuIds 菜单ID列表
     * @return 角色ID
     */
    Long createRole(SysRole role, List<Long> menuIds);

    /**
     * 更新角色
     *
     * @param role    角色信息
     * @param menuIds 菜单ID列表
     * @return 是否成功
     */
    boolean updateRole(SysRole role, List<Long> menuIds);

    /**
     * 删除角色
     *
     * @param roleId 角色ID
     * @return 是否成功
     */
    boolean deleteRole(Long roleId);

    /**
     * 启用角色
     *
     * @param roleId 角色ID
     * @return 是否成功
     */
    boolean enableRole(Long roleId);

    /**
     * 禁用角色
     *
     * @param roleId 角色ID
     * @return 是否成功
     */
    boolean disableRole(Long roleId);

    /**
     * 校验角色编码唯一性
     *
     * @param code      角色编码
     * @param excludeId 排除的角色ID
     * @return 是否唯一
     */
    boolean checkCodeUnique(String code, Long excludeId);

    /**
     * 分配角色菜单
     *
     * @param roleId  角色ID
     * @param menuIds 菜单ID列表
     * @return 是否成功
     */
    boolean assignMenus(Long roleId, List<Long> menuIds);
}
