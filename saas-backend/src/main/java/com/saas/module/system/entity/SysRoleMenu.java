package com.saas.module.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.saas.common.core.BaseEntity;

/**
 * 角色菜单关联实体
 *
 * @author saas
 */
@TableName("sys_role_menu")
public class SysRoleMenu extends BaseEntity {

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 菜单ID
     */
    private Long menuId;

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

    public Long getRoleId() { return roleId; }
    public void setRoleId(Long roleId) { this.roleId = roleId; }
    public Long getMenuId() { return menuId; }
    public void setMenuId(Long menuId) { this.menuId = menuId; }
}
