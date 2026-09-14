package com.saas.module.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.saas.common.core.BaseEntity;

import java.util.List;

/**
 * 系统部门实体
 *
 * @author saas
 */
@TableName("sys_dept")
public class SysDept extends BaseEntity {

    /**
     * 父部门ID
     */
    private Long parentId;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 部门编码
     */
    private String deptCode;

    /**
     * 负责人
     */
    private String leader;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 排序
     */
    private Integer sortOrder;

    /**
     * 状态: NORMAL-正常, DISABLED-禁用
     */
    private String status;

    /**
     * 祖级列表
     */
    private String ancestors;

    /**
     * 子部门数量（非数据库字段）
     */
    @TableField(exist = false)
    private Integer childCount;

    /**
     * 是否包含用户（非数据库字段）
     */
    @TableField(exist = false)
    private Boolean hasUser;

    /**
     * 子部门列表（非数据库字段）
     */
    @TableField(exist = false)
    private List<SysDept> children;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getTenantId() { return tenantId; }
    public void setTenantId(Long tenantId) { this.tenantId = tenantId; }
    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
    public java.time.LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(java.time.LocalDateTime createdAt) { this.createdAt = createdAt; }
    public String getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(String updatedBy) { this.updatedBy = updatedBy; }
    public java.time.LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(java.time.LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public java.time.LocalDateTime getDeletedAt() { return deletedAt; }
    public void setDeletedAt(java.time.LocalDateTime deletedAt) { this.deletedAt = deletedAt; }
    public Integer getVersion() { return version; }
    public void setVersion(Integer version) { this.version = version; }

    public Long getParentId() { return parentId; }
    public void setParentId(Long parentId) { this.parentId = parentId; }
    public String getDeptName() { return deptName; }
    public void setDeptName(String deptName) { this.deptName = deptName; }
    public String getDeptCode() { return deptCode; }
    public void setDeptCode(String deptCode) { this.deptCode = deptCode; }
    public String getLeader() { return leader; }
    public void setLeader(String leader) { this.leader = leader; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Integer getSortOrder() { return sortOrder; }
    public void setSortOrder(Integer sortOrder) { this.sortOrder = sortOrder; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getAncestors() { return ancestors; }
    public void setAncestors(String ancestors) { this.ancestors = ancestors; }
    public Integer getChildCount() { return childCount; }
    public void setChildCount(Integer childCount) { this.childCount = childCount; }
    public Boolean getHasUser() { return hasUser; }
    public void setHasUser(Boolean hasUser) { this.hasUser = hasUser; }
    public List<SysDept> getChildren() { return children; }
    public void setChildren(List<SysDept> children) { this.children = children; }
}
