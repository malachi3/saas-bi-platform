package com.saas.module.system.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.saas.common.core.BaseMapper;
import com.saas.module.system.entity.SysDept;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 部门 Mapper 接口
 *
 * @author saas
 */
@Mapper
public interface SysDeptMapper extends BaseMapper<SysDept> {

    /**
     * 分页查询部门列表
     *
     * @param page         分页参数
     * @param queryWrapper 查询条件
     * @return 分页结果
     */
    IPage<SysDept> selectDeptPage(Page<SysDept> page, @Param("ew") com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<SysDept> queryWrapper);

    /**
     * 查询所有子部门ID
     *
     * @param parentId 父部门ID
     * @return 子部门ID列表
     */
    java.util.List<Long> selectChildDeptIds(@Param("parentId") Long parentId);

    /**
     * 查询部门树
     *
     * @param queryWrapper 查询条件
     * @return 部门树
     */
    java.util.List<SysDept> selectDeptTree(@Param("ew") com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<SysDept> queryWrapper);

    /**
     * 统计子部门数量
     *
     * @param parentId 父部门ID
     * @return 子部门数量
     */
    int countChildren(@Param("parentId") Long parentId);

    /**
     * 统计部门用户数量
     *
     * @param deptId 部门ID
     * @return 用户数量
     */
    int countUsers(@Param("deptId") Long deptId);
}
