package com.saas.module.auth.controller;

import com.saas.common.web.R;
import com.saas.module.auth.dto.LoginRequest;
import com.saas.module.auth.dto.LoginResponse;
import com.saas.module.auth.dto.RegisterRequest;
import com.saas.module.auth.service.AuthService;
import com.saas.module.auth.service.CaptchaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 认证 Controller
 *
 * @author saas
 */
@Tag(name = "认证管理", description = "登录、注册、登出等认证接口")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final CaptchaService captchaService;

    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public R<LoginResponse> login(@Valid @RequestBody LoginRequest request, HttpServletRequest httpRequest) {
        String ip = getClientIp(httpRequest);
        LoginResponse response = authService.login(request, ip);
        return R.ok(response);
    }

    @Operation(summary = "用户注册")
    @PostMapping("/register")
    public R<Long> register(@Valid @RequestBody RegisterRequest request) {
        Long userId = authService.register(request);
        return R.ok(userId);
    }

    @Operation(summary = "用户退出")
    @PostMapping("/logout")
    public R<Void> logout(@Parameter(description = "用户ID") @RequestParam(required = false) Long userId) {
        if (userId != null) {
            authService.logout(userId);
        }
        return R.ok();
    }

    @Operation(summary = "刷新Token")
    @PostMapping("/refresh")
    public R<String> refresh(@Parameter(description = "刷新Token") @RequestParam String refreshToken) {
        String newToken = authService.refreshToken(refreshToken);
        return R.ok(newToken);
    }

    @Operation(summary = "获取验证码")
    @GetMapping("/captcha")
    public R<Map<String, String>> getCaptcha() {
        Map<String, String> captcha = captchaService.generateCaptcha();
        return R.ok(captcha);
    }

    @Operation(summary = "验证Token")
    @GetMapping("/validate")
    public R<Boolean> validateToken(@Parameter(description = "Token") @RequestParam String token) {
        boolean valid = authService.validateToken(token);
        return R.ok(valid);
    }

    /**
     * 获取客户端IP
     */
    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 多级代理时取第一个IP
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        return ip;
    }
}
