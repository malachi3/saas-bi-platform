package com.saas.module.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 登录响应
 *
 * @author saas
 */
@Schema(description = "登录响应")
public class LoginResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "访问Token")
    private String accessToken;

    @Schema(description = "刷新Token")
    private String refreshToken;

    @Schema(description = "Token类型", example = "Bearer")
    private String tokenType;

    @Schema(description = "过期时间(秒)", example = "86400")
    private Long expiresIn;

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "昵称")
    private String nickname;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "租户ID")
    private Long tenantId;

    @Schema(description = "角色列表")
    private List<String> roles;

    @Schema(description = "权限列表")
    private List<String> permissions;

    // Builder pattern
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final LoginResponse response = new LoginResponse();

        public Builder accessToken(String accessToken) {
            response.accessToken = accessToken;
            return this;
        }

        public Builder refreshToken(String refreshToken) {
            response.refreshToken = refreshToken;
            return this;
        }

        public Builder tokenType(String tokenType) {
            response.tokenType = tokenType;
            return this;
        }

        public Builder expiresIn(Long expiresIn) {
            response.expiresIn = expiresIn;
            return this;
        }

        public Builder userId(Long userId) {
            response.userId = userId;
            return this;
        }

        public Builder username(String username) {
            response.username = username;
            return this;
        }

        public Builder nickname(String nickname) {
            response.nickname = nickname;
            return this;
        }

        public Builder avatar(String avatar) {
            response.avatar = avatar;
            return this;
        }

        public Builder tenantId(Long tenantId) {
            response.tenantId = tenantId;
            return this;
        }

        public Builder roles(List<String> roles) {
            response.roles = roles;
            return this;
        }

        public Builder permissions(List<String> permissions) {
            response.permissions = permissions;
            return this;
        }

        public LoginResponse build() {
            return response;
        }
    }

    // Getters and Setters
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }
}
