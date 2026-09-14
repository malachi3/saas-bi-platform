package com.saas.module.auth.service;

import com.saas.module.auth.dto.LoginRequest;
import com.saas.module.auth.dto.LoginResponse;
import com.saas.module.auth.dto.RegisterRequest;

/**
 * 认证服务接口
 *
 * @author saas
 */
public interface AuthService {

    /**
     * 用户登录
     *
     * @param request 登录请求
     * @param ip     IP地址
     * @return 登录响应
     */
    LoginResponse login(LoginRequest request, String ip);

    /**
     * 用户注册
     *
     * @param request 注册请求
     * @return 用户ID
     */
    Long register(RegisterRequest request);

    /**
     * 退出登录
     *
     * @param userId 用户ID
     */
    void logout(Long userId);

    /**
     * 刷新Token
     *
     * @param refreshToken 刷新Token
     * @return 新的访问Token
     */
    String refreshToken(String refreshToken);

    /**
     * 验证Token
     *
     * @param token Token
     * @return 是否有效
     */
    boolean validateToken(String token);
}
