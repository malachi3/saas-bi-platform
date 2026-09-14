package com.saas.module.tenant.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.saas.common.core.BaseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 租户实体
 *
 * @author saas
 */
@TableName("sys_tenant")
public class SysTenant extends BaseEntity {

    /**
     * 租户名称
     */
    @TableField("tenant_name")
    private String tenantName;

    /**
     * 租户编码
     */
    @TableField("tenant_code")
    private String tenantCode;

    /**
     * 租户类型：SHARED共享 Schema/SCHEMA/SCHEMA独立/DATABASE独立数据库
     */
    @TableField("tenant_type")
    private String tenantType;

    /**
     * 套餐ID
     */
    @TableField("package_id")
    private Long packageId;

    /**
     * 到期时间
     */
    @TableField("expire_time")
    private LocalDateTime expireTime;

    /**
     * 用户数量
     */
    @TableField("user_count")
    private Integer userCount;

    /**
     * 状态：NORMAL/FROZEN/EXPIRED
     */
    private String status;

    /**
     * 套餐名称（非数据库字段）
     */
    @TableField(exist = false)
    private String packageName;

    /**
     * 是否超期（非数据库字段）
     */
    @TableField(exist = false)
    private Boolean expired;

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

    public String getTenantName() { return tenantName; }
    public void setTenantName(String tenantName) { this.tenantName = tenantName; }
    public String getTenantCode() { return tenantCode; }
    public void setTenantCode(String tenantCode) { this.tenantCode = tenantCode; }
    public String getTenantType() { return tenantType; }
    public void setTenantType(String tenantType) { this.tenantType = tenantType; }
    public Long getPackageId() { return packageId; }
    public void setPackageId(Long packageId) { this.packageId = packageId; }
    public LocalDateTime getExpireTime() { return expireTime; }
    public void setExpireTime(LocalDateTime expireTime) { this.expireTime = expireTime; }
    public Integer getUserCount() { return userCount; }
    public void setUserCount(Integer userCount) { this.userCount = userCount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getPackageName() { return packageName; }
    public void setPackageName(String packageName) { this.packageName = packageName; }
    public Boolean getExpired() { return expired; }
    public void setExpired(Boolean expired) { this.expired = expired; }
}
