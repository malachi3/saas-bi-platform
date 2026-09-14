package com.saas.module.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.saas.common.core.BaseServiceImpl;
import com.saas.common.enums.ErrorCode;
import com.saas.common.tenant.TenantContextHolder;
import com.saas.common.web.GlobalExceptionHandler.BusinessException;
import com.saas.common.web.PageDTO;
import com.saas.module.system.entity.SysDept;
import com.saas.module.system.mapper.SysDeptMapper;
import com.saas.module.system.service.SysDeptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 部门服务实现类
 *
 * @author saas
 */
@Service
@RequiredArgsConstructor
public class SysDeptServiceImpl extends BaseServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

    private final SysDeptMapper sysDeptMapper;

    @Override
    public IPage<SysDept> pageDept(PageDTO pageDTO, SysDept dept) {
        Page<SysDept> page = new Page<>(pageDTO.getCurrent(), pageDTO.getSize());
        LambdaQueryWrapper<SysDept> queryWrapper = new LambdaQueryWrapper<>();
        if (dept.getDeptName() != null) {
            queryWrapper.like(SysDept::getDeptName, dept.getDeptName());
        }
        if (dept.getStatus() != null) {
            queryWrapper.eq(SysDept::getStatus, dept.getStatus());
        }
        if (dept.getParentId() != null) {
            queryWrapper.eq(SysDept::getParentId, dept.getParentId());
        }
        queryWrapper.orderByAsc(SysDept::getSortOrder);
        IPage<SysDept> result = sysDeptMapper.selectDeptPage(page, queryWrapper);

        // 填充子部门数量
        for (SysDept d : result.getRecords()) {
            d.setChildCount(sysDeptMapper.countChildren(d.getId()));
            d.setHasUser(sysDeptMapper.countUsers(d.getId()) > 0);
        }
        return result;
    }

    @Override
    public List<SysDept> getDeptTree(SysDept dept) {
        LambdaQueryWrapper<SysDept> queryWrapper = new LambdaQueryWrapper<>();
        if (dept.getDeptName() != null) {
            queryWrapper.like(SysDept::getDeptName, dept.getDeptName());
        }
        if (dept.getStatus() != null) {
            queryWrapper.eq(SysDept::getStatus, dept.getStatus());
        }
        queryWrapper.orderByAsc(SysDept::getSortOrder);
        List<SysDept> allDepts = sysDeptMapper.selectDeptTree(queryWrapper);
        return buildDeptTree(allDepts);
    }

    @Override
    public List<SysDept> listAll() {
        LambdaQueryWrapper<SysDept> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(SysDept::getSortOrder);
        return sysDeptMapper.selectDeptTree(queryWrapper);
    }

    @Override
    public SysDept getById(Long deptId) {
        SysDept dept = sysDeptMapper.selectById(deptId);
        if (dept != null) {
            dept.setChildCount(sysDeptMapper.countChildren(deptId));
            dept.setHasUser(sysDeptMapper.countUsers(deptId) > 0);
        }
        return dept;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createDept(SysDept dept) {
        // 校验名称唯一性
        if (!checkDeptNameUnique(dept.getDeptName(), dept.getParentId(), null)) {
            throw new BusinessException(ErrorCode.RESOURCE_CONFLICT);
        }

        // 设置租户ID
        dept.setTenantId(TenantContextHolder.getTenantId());

        // 构建祖级列表
        if (dept.getParentId() != null && dept.getParentId() > 0) {
            SysDept parent = sysDeptMapper.selectById(dept.getParentId());
            if (parent != null) {
                dept.setAncestors(parent.getAncestors() + "," + dept.getParentId());
            }
        } else {
            dept.setAncestors("0");
            dept.setParentId(0L);
        }

        sysDeptMapper.insert(dept);
        return dept.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateDept(SysDept dept) {
        // 校验名称唯一性
        if (!checkDeptNameUnique(dept.getDeptName(), dept.getParentId(), dept.getId())) {
            throw new BusinessException(ErrorCode.RESOURCE_CONFLICT);
        }

        // 获取旧数据
        SysDept oldDept = sysDeptMapper.selectById(dept.getId());
        if (oldDept == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND);
        }

        // 如果父部门变更，需要更新子部门的祖级列表
        if (!oldDept.getParentId().equals(dept.getParentId())) {
            List<Long> childIds = getChildDeptIds(dept.getId());
            for (Long childId : childIds) {
                SysDept child = sysDeptMapper.selectById(childId);
                if (child != null) {
                    String newAncestors = dept.getAncestors() + "," + dept.getId();
                    child.setAncestors(child.getAncestors().replace(oldDept.getAncestors(), newAncestors));
                    sysDeptMapper.updateById(child);
                }
            }
        }

        return sysDeptMapper.updateById(dept) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteDept(Long deptId) {
        if (!canDelete(deptId)) {
            throw new BusinessException(ErrorCode.RESOURCE_CONFLICT);
        }
        return sysDeptMapper.deleteById(deptId) > 0;
    }

    @Override
    public boolean checkDeptNameUnique(String deptName, Long parentId, Long excludeId) {
        LambdaQueryWrapper<SysDept> queryWrapper = new LambdaQueryWrapper<SysDept>()
                .eq(SysDept::getDeptName, deptName)
                .eq(SysDept::getParentId, parentId != null ? parentId : 0)
                .ne(excludeId != null, SysDept::getId, excludeId);
        return sysDeptMapper.selectCount(queryWrapper) == 0;
    }

    @Override
    public boolean canDelete(Long deptId) {
        // 检查是否有子部门
        if (sysDeptMapper.countChildren(deptId) > 0) {
            return false;
        }
        // 检查是否有用户
        if (sysDeptMapper.countUsers(deptId) > 0) {
            return false;
        }
        return true;
    }

    @Override
    public List<Long> getChildDeptIds(Long deptId) {
        return sysDeptMapper.selectChildDeptIds(deptId);
    }

    /**
     * 构建部门树
     */
    private List<SysDept> buildDeptTree(List<SysDept> allDepts) {
        List<SysDept> result = new ArrayList<>();
        // 按父ID分组
        java.util.Map<Long, List<SysDept>> deptMap = allDepts.stream()
                .collect(Collectors.groupingBy(SysDept::getParentId));

        // 递归构建树
        buildDeptTreeRecursive(result, 0L, deptMap);
        return result;
    }

    private void buildDeptTreeRecursive(List<SysDept> result, Long parentId, java.util.Map<Long, List<SysDept>> deptMap) {
        List<SysDept> children = deptMap.get(parentId);
        if (children != null) {
            for (SysDept dept : children) {
                result.add(dept);
                buildDeptTreeRecursive(result, dept.getId(), deptMap);
            }
        }
    }
}
