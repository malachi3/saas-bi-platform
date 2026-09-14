package com.saas.module.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.io.Serial;
import java.io.Serializable;

/**
 * 登录请求
 *
 * @author saas
 */
@Schema(description = "登录请求")
public class LoginRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "用户名", example = "admin")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @Schema(description = "密码", example = "admin123")
    @NotBlank(message = "密码不能为空")
    private String password;

    @Schema(description = "验证码")
    private String captcha;

    @Schema(description = "验证码Key")
    private String captchaKey;

    @Schema(description = "记住我", example = "false")
    private Boolean rememberMe;

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getCaptchaKey() {
        return captchaKey;
    }

    public void setCaptchaKey(String captchaKey) {
        this.captchaKey = captchaKey;
    }

    public Boolean getRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(Boolean rememberMe) {
        this.rememberMe = rememberMe;
    }
}
