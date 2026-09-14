package com.saas.module.tenant.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.saas.common.core.BaseEntity;

import java.math.BigDecimal;
import java.util.List;

/**
 * 租户套餐实体
 *
 * @author saas
 */
@TableName("sys_tenant_package")
public class SysTenantPackage extends BaseEntity {

    /**
     * 套餐名称
     */
    @TableField("package_name")
    private String packageName;

    /**
     * 套餐编码
     */
    @TableField("package_code")
    private String packageCode;

    /**
     * 最大用户数
     */
    @TableField("max_users")
    private Integer maxUsers;

    /**
     * 最大存储空间(GB)
     */
    @TableField("max_storage_gb")
    private BigDecimal maxStorageGb;

    /**
     * 最大数据源数量
     */
    @TableField("max_data_sources")
    private Integer maxDataSources;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 排序
     */
    @TableField("sort_order")
    private Integer sortOrder;

    /**
     * 状态
     */
    private String status;

    /**
     * 功能特性JSON
     */
    private String features;

    /**
     * 功能列表（非数据库字段）
     */
    @TableField(exist = false)
    private List<String> featureList;

    /**
     * 使用该套餐的租户数量（非数据库字段）
     */
    @TableField(exist = false)
    private Integer tenantCount;

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

    public String getPackageName() { return packageName; }
    public void setPackageName(String packageName) { this.packageName = packageName; }
    public String getPackageCode() { return packageCode; }
    public void setPackageCode(String packageCode) { this.packageCode = packageCode; }
    public Integer getMaxUsers() { return maxUsers; }
    public void setMaxUsers(Integer maxUsers) { this.maxUsers = maxUsers; }
    public BigDecimal getMaxStorageGb() { return maxStorageGb; }
    public void setMaxStorageGb(BigDecimal maxStorageGb) { this.maxStorageGb = maxStorageGb; }
    public Integer getMaxDataSources() { return maxDataSources; }
    public void setMaxDataSources(Integer maxDataSources) { this.maxDataSources = maxDataSources; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public Integer getSortOrder() { return sortOrder; }
    public void setSortOrder(Integer sortOrder) { this.sortOrder = sortOrder; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getFeatures() { return features; }
    public void setFeatures(String features) { this.features = features; }
    public List<String> getFeatureList() { return featureList; }
    public void setFeatureList(List<String> featureList) { this.featureList = featureList; }
    public Integer getTenantCount() { return tenantCount; }
    public void setTenantCount(Integer tenantCount) { this.tenantCount = tenantCount; }
}
