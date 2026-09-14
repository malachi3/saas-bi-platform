package com.saas.module.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.saas.module.system.entity.SysUser;
import com.saas.common.web.PageDTO;

import java.util.List;

/**
 * 用户服务接口
 *
 * @author saas
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 分页查询用户
     *
     * @param pageDTO 分页参数
     * @param user    查询条件
     * @return 分页结果
     */
    IPage<SysUser> pageUser(PageDTO pageDTO, SysUser user);

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户
     */
    SysUser getByUsername(String username);

    /**
     * 创建用户
     *
     * @param user    用户信息
     * @param roleIds 角色ID列表
     * @return 用户ID
     */
    Long createUser(SysUser user, List<Long> roleIds);

    /**
     * 更新用户
     *
     * @param user    用户信息
     * @param roleIds 角色ID列表
     * @return 是否成功
     */
    boolean updateUser(SysUser user, List<Long> roleIds);

    /**
     * 删除用户
     *
     * @param userId 用户ID
     * @return 是否成功
     */
    boolean deleteUser(Long userId);

    /**
     * 重置密码
     *
     * @param userId      用户ID
     * @param newPassword 新密码
     * @return 是否成功
     */
    boolean resetPassword(Long userId, String newPassword);

    /**
     * 修改密码
     *
     * @param userId      用户ID
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 是否成功
     */
    boolean changePassword(Long userId, String oldPassword, String newPassword);

    /**
     * 更新登录信息
     *
     * @param userId  用户ID
     * @param loginIp 登录IP
     */
    void updateLoginInfo(Long userId, String loginIp);

    /**
     * 检查用户名是否存在
     *
     * @param username 用户名
     * @param excludeId 排除的用户ID
     * @return 是否存在
     */
    boolean checkUsernameExists(String username, Long excludeId);

    /**
     * 检查手机号是否存在
     *
     * @param phone     手机号
     * @param excludeId 排除的用户ID
     * @return 是否存在
     */
    boolean checkPhoneExists(String phone, Long excludeId);

    /**
     * 检查邮箱是否存在
     *
     * @param email     邮箱
     * @param excludeId 排除的用户ID
     * @return 是否存在
     */
    boolean checkEmailExists(String email, Long excludeId);
}
