package com.saas.module.system.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.saas.common.core.BaseMapper;
import com.saas.module.system.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色 Mapper 接口
 *
 * @author saas
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 分页查询角色列表
     *
     * @param page         分页参数
     * @param queryWrapper 查询条件
     * @return 分页结果
     */
    IPage<SysRole> selectRolePage(Page<SysRole> page, @Param("ew") com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<SysRole> queryWrapper);

    /**
     * 查询用户角色列表
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    List<SysRole> selectRolesByUserId(@Param("userId") Long userId);

    /**
     * 查询角色编码是否存在
     *
     * @param code    角色编码
     * @param excludeId 排除的角色ID
     * @return 是否存在
     */
    int countByCode(@Param("code") String code, @Param("excludeId") Long excludeId);

    /**
     * 根据角色编码查询
     *
     * @param code 角色编码
     * @return 角色
     */
    SysRole selectByCode(@Param("code") String code);

    /**
     * 查询用户角色ID列表
     *
     * @param userId 用户ID
     * @return 角色ID列表
     */
    List<Long> selectRoleIdsByUserId(@Param("userId") Long userId);
}
