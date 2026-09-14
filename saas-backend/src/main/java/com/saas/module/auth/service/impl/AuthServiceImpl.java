package com.saas.module.auth.service.impl;

import com.saas.common.security.LoginUser;
import com.saas.common.security.JwtUtils;
import com.saas.common.web.GlobalExceptionHandler.BusinessException;
import com.saas.common.enums.ErrorCode;
import com.saas.module.auth.dto.LoginRequest;
import com.saas.module.auth.dto.LoginResponse;
import com.saas.module.auth.dto.RegisterRequest;
import com.saas.module.auth.service.AuthService;
import com.saas.module.system.entity.SysMenu;
import com.saas.module.system.entity.SysUser;
import com.saas.module.system.mapper.SysMenuMapper;
import com.saas.module.system.mapper.SysUserMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 认证服务实现类
 *
 * @author saas
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final SysUserMapper sysUserMapper;
    private final SysMenuMapper sysMenuMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    // Redis 为可选依赖
    @Autowired(required = false)
    private RedisTemplate<String, Object> redisTemplate;

    @Value("${jwt.expiration}")
    private Long expiration;

    private static final String TOKEN_BLACKLIST_PREFIX = "saas:token:blacklist:";

    @Override
    public LoginResponse login(LoginRequest request, String ip) {
        // 查询用户
        SysUser user = sysUserMapper.selectByUsername(request.getUsername());
        if (user == null) {
            throw new BusinessException(ErrorCode.USERNAME_PASSWORD_ERROR);
        }

        // 验证密码
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BusinessException(ErrorCode.USERNAME_PASSWORD_ERROR);
        }

        // 检查账号状态
        if (!"NORMAL".equals(user.getStatus())) {
            throw new BusinessException(ErrorCode.ACCOUNT_DISABLED);
        }

        // 查询用户角色
        List<String> roles = sysMenuMapper.selectPermsByUserId(user.getId()).stream()
                .filter(p -> p != null && !p.isEmpty())
                .collect(Collectors.toList());

        // 查询用户权限
        List<String> permissions = sysMenuMapper.selectPermsByUserId(user.getId());

        // 生成Token
        String accessToken = jwtUtils.generateToken(user.getId(), user.getTenantId());
        String refreshToken = jwtUtils.generateRefreshToken(user.getId(), user.getTenantId());

        // 更新登录信息
        sysUserMapper.updateLoginInfo(user.getId(), ip, java.time.LocalDateTime.now());

        // 构建登录用户信息
        LoginUser loginUser = LoginUser.builder()
                .userId(user.getId())
                .tenantId(user.getTenantId())
                .username(user.getUsername())
                .password(user.getPassword())
                .nickname(user.getNickname())
                .avatar(user.getAvatar())
                .roles(roles)
                .permissions(permissions)
                .status(user.getStatus())
                .build();

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(expiration / 1000)
                .userId(user.getId())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .avatar(user.getAvatar())
                .tenantId(user.getTenantId())
                .roles(roles)
                .permissions(permissions)
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long register(RegisterRequest request) {
        // 验证密码确认
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new BusinessException(ErrorCode.VALIDATION_ERROR);
        }

        // 检查用户名是否存在
        if (sysUserMapper.selectByUsername(request.getUsername()) != null) {
            throw new BusinessException(ErrorCode.USERNAME_EXISTS);
        }

        // 创建用户
        SysUser user = new SysUser();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setNickname(request.getNickname());
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        user.setStatus("1");
        user.setTenantId(1L); // 默认租户

        sysUserMapper.insert(user);
        return user.getId();
    }

    @Override
    public void logout(Long userId) {
        // 将Token加入黑名单
        // 在实际使用中，应该将Token的JTI加入黑名单
        log.info("用户 {} 退出登录", userId);
    }

    @Override
    public String refreshToken(String refreshToken) {
        // 验证刷新Token
        if (!jwtUtils.validateToken(refreshToken)) {
            throw new BusinessException(ErrorCode.TOKEN_INVALID);
        }

        // 检查Token类型 - 获取用户信息并验证
        Long userId = jwtUtils.getUserIdFromToken(refreshToken);
        Long tenantId = jwtUtils.getTenantIdFromToken(refreshToken);

        // 生成新的访问Token
        return jwtUtils.generateToken(userId, tenantId);
    }

    @Override
    public boolean validateToken(String token) {
        return jwtUtils.validateToken(token);
    }
}
