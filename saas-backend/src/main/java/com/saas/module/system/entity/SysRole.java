package com.saas.module.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.saas.common.core.BaseEntity;

import java.util.List;

/**
 * 系统角色实体
 *
 * @author saas
 */
@TableName("sys_role")
public class SysRole extends BaseEntity {

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色编码
     */
    private String code;

    /**
     * 角色类型：0系统内置 1自定义
     */
    private Integer type;

    /**
     * 状态：0禁用 1启用
     */
    private Integer status;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 数据范围：1全部 2本部门及以下 3本部门 4仅本人 5自定义
     */
    private Integer dataScope;

    /**
     * 备注
     */
    private String remark;

    /**
     * 菜单ID列表（非数据库字段）
     */
    @TableField(exist = false)
    private List<Long> menuIds;

    /**
     * 菜单列表（非数据库字段）
     */
    @TableField(exist = false)
    private List<SysMenu> menus;

    /**
     * 是否有权限（非数据库字段）
     */
    @TableField(exist = false)
    private Boolean hasPermission;

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

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public Integer getType() { return type; }
    public void setType(Integer type) { this.type = type; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
    public Integer getSort() { return sort; }
    public void setSort(Integer sort) { this.sort = sort; }
    public Integer getDataScope() { return dataScope; }
    public void setDataScope(Integer dataScope) { this.dataScope = dataScope; }
    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }
    public List<Long> getMenuIds() { return menuIds; }
    public void setMenuIds(List<Long> menuIds) { this.menuIds = menuIds; }
    public List<SysMenu> getMenus() { return menus; }
    public void setMenus(List<SysMenu> menus) { this.menus = menus; }
    public Boolean getHasPermission() { return hasPermission; }
    public void setHasPermission(Boolean hasPermission) { this.hasPermission = hasPermission; }
}
