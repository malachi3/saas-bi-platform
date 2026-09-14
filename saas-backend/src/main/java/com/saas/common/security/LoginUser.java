package com.saas.common.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 登录用户信息
 * 实现 UserDetails 接口用于 Spring Security
 *
 * @author saas
 */
public class LoginUser implements UserDetails, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 头像URL
     */
    private String avatar;

    /**
     * 角色编码列表
     */
    private List<String> roles;

    /**
     * 权限标识列表
     */
    private List<String> permissions;

    /**
     * 状态：0禁用 1启用
     */
    private String status;

    // Builder pattern
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long userId;
        private Long tenantId;
        private String username;
        private String password;
        private String nickname;
        private String email;
        private String phone;
        private String avatar;
        private List<String> roles;
        private List<String> permissions;
        private String status;

        public Builder userId(Long userId) { this.userId = userId; return this; }
        public Builder tenantId(Long tenantId) { this.tenantId = tenantId; return this; }
        public Builder username(String username) { this.username = username; return this; }
        public Builder password(String password) { this.password = password; return this; }
        public Builder nickname(String nickname) { this.nickname = nickname; return this; }
        public Builder email(String email) { this.email = email; return this; }
        public Builder phone(String phone) { this.phone = phone; return this; }
        public Builder avatar(String avatar) { this.avatar = avatar; return this; }
        public Builder roles(List<String> roles) { this.roles = roles; return this; }
        public Builder permissions(List<String> permissions) { this.permissions = permissions; return this; }
        public Builder status(String status) { this.status = status; return this; }

        public LoginUser build() {
            LoginUser user = new LoginUser();
            user.userId = this.userId;
            user.tenantId = this.tenantId;
            user.username = this.username;
            user.password = this.password;
            user.nickname = this.nickname;
            user.email = this.email;
            user.phone = this.phone;
            user.avatar = this.avatar;
            user.roles = this.roles;
            user.permissions = this.permissions;
            user.status = this.status;
            return user;
        }
    }

    // Getters and Setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Long getTenantId() { return tenantId; }
    public void setTenantId(Long tenantId) { this.tenantId = tenantId; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }
    public List<String> getRoles() { return roles; }
    public void setRoles(List<String> roles) { this.roles = roles; }
    public List<String> getPermissions() { return permissions; }
    public void setPermissions(List<String> permissions) { this.permissions = permissions; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (roles != null) {
            return roles.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                    .collect(Collectors.toList());
        }
        return List.of();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return status == null || "1".equals(status);
    }

    /**
     * 判断是否超管
     *
     * @return 是否超管
     */
    public boolean isSuperAdmin() {
        return roles != null && roles.contains("superadmin");
    }
}
