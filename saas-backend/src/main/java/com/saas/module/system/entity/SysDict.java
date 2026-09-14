package com.saas.module.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.saas.common.core.BaseEntity;

/**
 * 系统字典实体
 *
 * @author saas
 */
@TableName("sys_dict")
public class SysDict extends BaseEntity {

    /**
     * 字典名称
     */
    @TableField("dict_name")
    private String dictName;

    /**
     * 字典编码
     */
    @TableField("dict_code")
    private String dictCode;

    /**
     * 字典类型：0系统字典 1业务字典
     */
    @TableField("dict_type")
    private Integer dictType;

    /**
     * 状态：0禁用 1启用
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 字典项数量（非数据库字段）
     */
    @TableField(exist = false)
    private Integer itemCount;

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

    public String getDictName() { return dictName; }
    public void setDictName(String dictName) { this.dictName = dictName; }
    public String getDictCode() { return dictCode; }
    public void setDictCode(String dictCode) { this.dictCode = dictCode; }
    public Integer getDictType() { return dictType; }
    public void setDictType(Integer dictType) { this.dictType = dictType; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }
    public Integer getItemCount() { return itemCount; }
    public void setItemCount(Integer itemCount) { this.itemCount = itemCount; }
}
