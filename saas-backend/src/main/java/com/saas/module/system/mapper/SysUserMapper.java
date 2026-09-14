package com.saas.module.system.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.saas.common.core.BaseMapper;
import com.saas.module.system.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 用户 Mapper 接口
 *
 * @author saas
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 分页查询用户列表
     *
     * @param page        分页参数
     * @param queryWrapper 查询条件
     * @return 分页结果
     */
    IPage<SysUser> selectUserPage(Page<SysUser> page, @Param(Constants.WRAPPER) com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<SysUser> queryWrapper);

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户
     */
    @Select("SELECT * FROM sys_user WHERE username = #{username} AND deleted_at IS NULL")
    SysUser selectByUsername(@Param("username") String username);

    /**
     * 根据手机号查询用户
     *
     * @param phone 手机号
     * @return 用户
     */
    @Select("SELECT * FROM sys_user WHERE phone = #{phone} AND deleted_at IS NULL")
    SysUser selectByPhone(@Param("phone") String phone);

    /**
     * 根据邮箱查询用户
     *
     * @param email 邮箱
     * @return 用户
     */
    @Select("SELECT * FROM sys_user WHERE email = #{email} AND deleted_at IS NULL")
    SysUser selectByEmail(@Param("email") String email);

    /**
     * 重置密码
     *
     * @param userId      用户ID
     * @param password    新密码
     * @param updatedBy   更新人
     * @return 影响行数
     */
    @Update("UPDATE sys_user SET password = #{password}, updated_by = #{updatedBy}, updated_at = NOW() WHERE id = #{userId}")
    int resetPassword(@Param("userId") Long userId, @Param("password") String password, @Param("updatedBy") String updatedBy);

    /**
     * 更新登录信息
     *
     * @param userId   用户ID
     * @param loginIp  登录IP
     * @param loginAt  登录时间
     * @return 影响行数
     */
    @Update("UPDATE sys_user SET last_login_ip = #{loginIp}, last_login_time = #{loginAt} WHERE id = #{userId}")
    int updateLoginInfo(@Param("userId") Long userId, @Param("loginIp") String loginIp, @Param("loginAt") java.time.LocalDateTime loginAt);
}
