package com.saas.module.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.io.Serial;
import java.io.Serializable;

/**
 * 注册请求
 *
 * @author saas
 */
@Schema(description = "注册请求")
public class RegisterRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "用户名", example = "zhangsan")
    @NotBlank(message = "用户名不能为空")
    @Size(min = 4, max = 20, message = "用户名长度在4-20之间")
    private String username;

    @Schema(description = "密码", example = "Admin123!")
    @NotBlank(message = "密码不能为空")
    @Size(min = 8, max = 20, message = "密码长度在8-20之间")
    private String password;

    @Schema(description = "确认密码", example = "Admin123!")
    @NotBlank(message = "确认密码不能为空")
    private String confirmPassword;

    @Schema(description = "昵称", example = "张三")
    private String nickname;

    @Schema(description = "手机号", example = "13800138000")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;

    @Schema(description = "邮箱", example = "zhangsan@example.com")
    @Email(message = "邮箱格式不正确")
    private String email;

    @Schema(description = "验证码")
    private String captcha;

    @Schema(description = "验证码Key")
    private String captchaKey;

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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
}
