package com.saas.common.tenant;

import com.saas.common.security.LoginUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 租户拦截器
 * 从请求头中提取租户ID并设置到上下文中
 *
 * @author saas
 */
@Component
public class TenantInterceptor implements HandlerInterceptor {

    private static final String TENANT_HEADER = "X-Tenant-ID";
    private static final Long DEFAULT_TENANT_ID = 1L;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String tenantIdStr = request.getHeader(TENANT_HEADER);
        Long tenantId = DEFAULT_TENANT_ID;

        if (tenantIdStr != null && !tenantIdStr.isEmpty()) {
            try {
                tenantId = Long.parseLong(tenantIdStr);
            } catch (NumberFormatException e) {
                // 使用默认租户ID
            }
        }

        TenantContextHolder.setTenantId(tenantId);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        TenantContextHolder.clear();
    }
}
