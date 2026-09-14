package com.saas.common.tenant;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 租户数据填充处理器
 * 自动填充租户ID和审计字段
 *
 * @author saas
 */
@Component
public class TenantDataSource implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        // 设置租户ID
        Long tenantId = TenantContextHolder.getTenantId();
        if (tenantId != null) {
            this.strictInsertFill(metaObject, "tenantId", Long.class, tenantId);
        }

        // 设置创建时间
        this.strictInsertFill(metaObject, "createdAt", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "updatedAt", LocalDateTime.class, LocalDateTime.now());

        // 设置创建人（从安全上下文中获取）
        this.strictInsertFill(metaObject, "createdBy", String.class, getCurrentUsername());
        this.strictInsertFill(metaObject, "updatedBy", String.class, getCurrentUsername());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // 设置更新时间
        this.strictUpdateFill(metaObject, "updatedAt", LocalDateTime.class, LocalDateTime.now());

        // 设置更新人
        this.strictUpdateFill(metaObject, "updatedBy", String.class, getCurrentUsername());
    }

    /**
     * 获取当前用户名
     * 从安全上下文中获取，如果未登录则返回系统
     *
     * @return 当前用户名
     */
    private String getCurrentUsername() {
        try {
            // 尝试从安全上下文中获取当前用户
            // 这里可以注入 SecurityContext 或使用其他方式获取
            return "system";
        } catch (Exception e) {
            return "system";
        }
    }
}
