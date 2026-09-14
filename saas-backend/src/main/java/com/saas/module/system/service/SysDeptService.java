package com.saas.module.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.saas.module.system.entity.SysDept;
import com.saas.common.web.PageDTO;

import java.util.List;

/**
 * 部门服务接口
 *
 * @author saas
 */
public interface SysDeptService extends IService<SysDept> {

    /**
     * 分页查询部门
     *
     * @param pageDTO 分页参数
     * @param dept    查询条件
     * @return 分页结果
     */
    IPage<SysDept> pageDept(PageDTO pageDTO, SysDept dept);

    /**
     * 获取部门树
     *
     * @param dept 查询条件
     * @return 部门树
     */
    List<SysDept> getDeptTree(SysDept dept);

    /**
     * 获取所有部门列表
     *
     * @return 部门列表
     */
    List<SysDept> listAll();

    /**
     * 根据ID获取部门
     *
     * @param deptId 部门ID
     * @return 部门
     */
    SysDept getById(Long deptId);

    /**
     * 创建部门
     *
     * @param dept 部门信息
     * @return 部门ID
     */
    Long createDept(SysDept dept);

    /**
     * 更新部门
     *
     * @param dept 部门信息
     * @return 是否成功
     */
    boolean updateDept(SysDept dept);

    /**
     * 删除部门
     *
     * @param deptId 部门ID
     * @return 是否成功
     */
    boolean deleteDept(Long deptId);

    /**
     * 校验部门名称是否唯一
     *
     * @param deptName  部门名称
     * @param parentId  父部门ID
     * @param excludeId 排除的部门ID
     * @return 是否唯一
     */
    boolean checkDeptNameUnique(String deptName, Long parentId, Long excludeId);

    /**
     * 校验部门是否可删除
     *
     * @param deptId 部门ID
     * @return 是否可删除
     */
    boolean canDelete(Long deptId);

    /**
     * 获取所有子部门ID
     *
     * @param deptId 部门ID
     * @return 子部门ID列表
     */
    List<Long> getChildDeptIds(Long deptId);
}
