package com.saas.module.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.saas.common.core.BaseEntity;

import java.util.List;

/**
 * 系统菜单实体
 *
 * @author saas
 */
@TableName("sys_menu")
public class SysMenu extends BaseEntity {

    /**
     * 父菜单ID
     */
    private Long parentId;

    /**
     * 菜单类型: CATALOG-目录, MENU-菜单, BUTTON-按钮
     */
    private String menuType;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 重定向地址
     */
    private String redirect;

    /**
     * 是否外链
     */
    private Boolean isExternal;

    /**
     * 是否缓存
     */
    private Boolean isCache;

    /**
     * 是否显示
     */
    private Boolean isVisible;

    /**
     * 权限标识
     */
    private String permission;

    /**
     * 排序
     */
    private Integer orderNum;

    /**
     * 状态: NORMAL-正常, DISABLED-禁用
     */
    private String status;

    /**
     * 祖级列表
     */
    private String ancestors;

    /**
     * 子菜单列表（非数据库字段）
     */
    @TableField(exist = false)
    private List<SysMenu> children;

    /**
     * 路由名称（非数据库字段）
     */
    @TableField(exist = false)
    private String routeName;

    /**
     * 重定向地址（非数据库字段）
     */
    @TableField(exist = false)
    private String menuRedirect;

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
    public String getMenuType() { return menuType; }
    public void setMenuType(String menuType) { this.menuType = menuType; }
    public String getMenuName() { return menuName; }
    public void setMenuName(String menuName) { this.menuName = menuName; }
    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }
    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }
    public String getComponent() { return component; }
    public void setComponent(String component) { this.component = component; }
    public String getRedirect() { return redirect; }
    public void setRedirect(String redirect) { this.redirect = redirect; }
    public Boolean getIsExternal() { return isExternal; }
    public void setIsExternal(Boolean isExternal) { this.isExternal = isExternal; }
    public Boolean getIsCache() { return isCache; }
    public void setIsCache(Boolean isCache) { this.isCache = isCache; }
    public Boolean getIsVisible() { return isVisible; }
    public void setIsVisible(Boolean isVisible) { this.isVisible = isVisible; }
    public String getPermission() { return permission; }
    public void setPermission(String permission) { this.permission = permission; }
    public Integer getOrderNum() { return orderNum; }
    public void setOrderNum(Integer orderNum) { this.orderNum = orderNum; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getAncestors() { return ancestors; }
    public void setAncestors(String ancestors) { this.ancestors = ancestors; }
    public List<SysMenu> getChildren() { return children; }
    public void setChildren(List<SysMenu> children) { this.children = children; }
    public String getRouteName() { return routeName; }
    public void setRouteName(String routeName) { this.routeName = routeName; }
    public String getMenuRedirect() { return menuRedirect; }
    public void setMenuRedirect(String menuRedirect) { this.menuRedirect = menuRedirect; }
}
