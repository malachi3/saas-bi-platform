package com.saas.common.core;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 实体基类
 * 包含通用字段：ID、租户ID、创建人、创建时间、更新人、更新时间、删除时间、版本号
 *
 * @author saas
 */
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    protected Long id;

    /**
     * 租户ID
     */
    protected Long tenantId;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    protected String createdBy;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    protected LocalDateTime createdAt;

    /**
     * 更新人
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    protected String updatedBy;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    protected LocalDateTime updatedAt;

    /**
     * 删除时间（逻辑删除）
     */
    @TableLogic
    protected LocalDateTime deletedAt;

    /**
     * 乐观锁版本号
     */
    @Version
    protected Integer version;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getTenantId() { return tenantId; }
    public void setTenantId(Long tenantId) { this.tenantId = tenantId; }
    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public String getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(String updatedBy) { this.updatedBy = updatedBy; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public LocalDateTime getDeletedAt() { return deletedAt; }
    public void setDeletedAt(LocalDateTime deletedAt) { this.deletedAt = deletedAt; }
    public Integer getVersion() { return version; }
    public void setVersion(Integer version) { this.version = version; }
}
