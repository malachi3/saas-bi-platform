package com.saas.module.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.saas.common.core.BaseServiceImpl;
import com.saas.common.enums.ErrorCode;
import com.saas.common.tenant.TenantContextHolder;
import com.saas.common.web.GlobalExceptionHandler.BusinessException;
import com.saas.common.web.PageDTO;
import com.saas.module.system.entity.SysDept;
import com.saas.module.system.entity.SysMenu;
import com.saas.module.system.entity.SysUser;
import com.saas.module.system.entity.SysUserRole;
import com.saas.module.system.mapper.SysDeptMapper;
import com.saas.module.system.mapper.SysMenuMapper;
import com.saas.module.system.mapper.SysUserMapper;
import com.saas.module.system.mapper.SysUserRoleMapper;
import com.saas.module.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户服务实现类
 *
 * @author saas
 */
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends BaseServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private final SysUserMapper sysUserMapper;
    private final SysUserRoleMapper sysUserRoleMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public IPage<SysUser> pageUser(PageDTO pageDTO, SysUser user) {
        Page<SysUser> page = new Page<>(pageDTO.getCurrent(), pageDTO.getSize());
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        if (user.getUsername() != null) {
            queryWrapper.like(SysUser::getUsername, user.getUsername());
        }
        if (user.getNickname() != null) {
            queryWrapper.like(SysUser::getNickname, user.getNickname());
        }
        if (user.getPhone() != null) {
            queryWrapper.eq(SysUser::getPhone, user.getPhone());
        }
        if (user.getStatus() != null) {
            queryWrapper.eq(SysUser::getStatus, user.getStatus());
        }
        if (user.getDeptId() != null) {
            queryWrapper.eq(SysUser::getDeptId, user.getDeptId());
        }
        queryWrapper.orderByDesc(SysUser::getCreatedAt);
        return sysUserMapper.selectUserPage(page, queryWrapper);
    }

    @Override
    public SysUser getByUsername(String username) {
        return sysUserMapper.selectByUsername(username);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createUser(SysUser user, List<Long> roleIds) {
        // 校验用户名唯一性
        if (checkUsernameExists(user.getUsername(), null)) {
            throw new BusinessException(ErrorCode.USERNAME_EXISTS);
        }
        // 校验手机号唯一性
        if (user.getPhone() != null && checkPhoneExists(user.getPhone(), null)) {
            throw new BusinessException(ErrorCode.PHONE_EXISTS);
        }
        // 校验邮箱唯一性
        if (user.getEmail() != null && checkEmailExists(user.getEmail(), null)) {
            throw new BusinessException(ErrorCode.EMAIL_EXISTS);
        }

        // 设置租户ID
        user.setTenantId(TenantContextHolder.getTenantId());
        // 加密密码
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // 保存用户
        sysUserMapper.insert(user);

        // 保存用户角色关联
        if (roleIds != null && !roleIds.isEmpty()) {
            saveUserRoles(user.getId(), roleIds);
        }

        return user.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateUser(SysUser user, List<Long> roleIds) {
        // 校验用户名唯一性
        if (checkUsernameExists(user.getUsername(), user.getId())) {
            throw new BusinessException(ErrorCode.USERNAME_EXISTS);
        }
        // 校验手机号唯一性
        if (user.getPhone() != null && checkPhoneExists(user.getPhone(), user.getId())) {
            throw new BusinessException(ErrorCode.PHONE_EXISTS);
        }
        // 校验邮箱唯一性
        if (user.getEmail() != null && checkEmailExists(user.getEmail(), user.getId())) {
            throw new BusinessException(ErrorCode.EMAIL_EXISTS);
        }

        // 不更新密码
        user.setPassword(null);
        sysUserMapper.updateById(user);

        // 更新用户角色关联
        sysUserRoleMapper.delete(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, user.getId()));
        if (roleIds != null && !roleIds.isEmpty()) {
            saveUserRoles(user.getId(), roleIds);
        }

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteUser(Long userId) {
        // 删除用户角色关联
        sysUserRoleMapper.delete(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, userId));
        // 逻辑删除用户
        return sysUserMapper.deleteById(userId) > 0;
    }

    @Override
    public boolean resetPassword(Long userId, String newPassword) {
        String encodedPassword = passwordEncoder.encode(newPassword);
        String updatedBy = "system";
        return sysUserMapper.resetPassword(userId, encodedPassword, updatedBy) > 0;
    }

    @Override
    public boolean changePassword(Long userId, String oldPassword, String newPassword) {
        SysUser user = sysUserMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new BusinessException(ErrorCode.USERNAME_PASSWORD_ERROR);
        }
        String encodedPassword = passwordEncoder.encode(newPassword);
        String updatedBy = user.getUsername();
        return sysUserMapper.resetPassword(userId, encodedPassword, updatedBy) > 0;
    }

    @Override
    public void updateLoginInfo(Long userId, String loginIp) {
        sysUserMapper.updateLoginInfo(userId, loginIp, LocalDateTime.now());
    }

    @Override
    public boolean checkUsernameExists(String username, Long excludeId) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUsername, username)
                .ne(excludeId != null, SysUser::getId, excludeId);
        return sysUserMapper.selectCount(queryWrapper) > 0;
    }

    @Override
    public boolean checkPhoneExists(String phone, Long excludeId) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getPhone, phone)
                .ne(excludeId != null, SysUser::getId, excludeId);
        return sysUserMapper.selectCount(queryWrapper) > 0;
    }

    @Override
    public boolean checkEmailExists(String email, Long excludeId) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getEmail, email)
                .ne(excludeId != null, SysUser::getId, excludeId);
        return sysUserMapper.selectCount(queryWrapper) > 0;
    }

    /**
     * 保存用户角色关联
     */
    private void saveUserRoles(Long userId, List<Long> roleIds) {
        Long tenantId = TenantContextHolder.getTenantId();
        for (Long roleId : roleIds) {
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);
            userRole.setTenantId(tenantId);
            sysUserRoleMapper.insert(userRole);
        }
    }
}
