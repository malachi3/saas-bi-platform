package com.saas.module.tenant.service;

import com.saas.module.tenant.entity.SysTenant;
import com.saas.module.tenant.entity.SysTenantPackage;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.saas.common.web.PageDTO;

import java.util.List;

/**
 * 租户服务接口
 *
 * @author saas
 */
public interface TenantService {

    /**
     * 分页查询租户
     *
     * @param pageDTO 分页参数
     * @param tenant  查询条件
     * @return 分页结果
     */
    IPage<SysTenant> pageTenant(PageDTO pageDTO, SysTenant tenant);

    /**
     * 获取租户详情
     *
     * @param tenantId 租户ID
     * @return 租户信息
     */
    SysTenant getById(Long tenantId);

    /**
     * 根据编码获取租户
     *
     * @param code 租户编码
     * @return 租户信息
     */
    SysTenant getByCode(String code);

    /**
     * 获取所有租户
     *
     * @return 租户列表
     */
    List<SysTenant> listAll();

    /**
     * 创建租户
     *
     * @param tenant 租户信息
     * @return 租户ID
     */
    Long createTenant(SysTenant tenant);

    /**
     * 更新租户
     *
     * @param tenant 租户信息
     * @return 是否成功
     */
    boolean updateTenant(SysTenant tenant);

    /**
     * 删除租户
     *
     * @param tenantId 租户ID
     * @return 是否成功
     */
    boolean deleteTenant(Long tenantId);

    /**
     * 启用租户
     *
     * @param tenantId 租户ID
     * @return 是否成功
     */
    boolean enableTenant(Long tenantId);

    /**
     * 禁用租户
     *
     * @param tenantId 租户ID
     * @return 是否成功
     */
    boolean disableTenant(Long tenantId);

    /**
     * 校验租户编码唯一性
     *
     * @param code      租户编码
     * @param excludeId 排除的租户ID
     * @return 是否唯一
     */
    boolean checkCodeUnique(String code, Long excludeId);

    /**
     * 检查租户是否过期
     *
     * @param tenantId 租户ID
     * @return 是否过期
     */
    boolean isExpired(Long tenantId);

    /**
     * 获取套餐列表
     *
     * @return 套餐列表
     */
    List<SysTenantPackage> listPackages();

    /**
     * 获取套餐详情
     *
     * @param packageId 套餐ID
     * @return 套餐信息
     */
    SysTenantPackage getPackageById(Long packageId);

    /**
     * 创建套餐
     *
     * @param pkg 套餐信息
     * @return 套餐ID
     */
    Long createPackage(SysTenantPackage pkg);

    /**
     * 更新套餐
     *
     * @param pkg 套餐信息
     * @return 是否成功
     */
    boolean updatePackage(SysTenantPackage pkg);

    /**
     * 删除套餐
     *
     * @param packageId 套餐ID
     * @return 是否成功
     */
    boolean deletePackage(Long packageId);
}
