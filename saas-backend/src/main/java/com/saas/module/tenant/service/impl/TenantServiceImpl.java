package com.saas.module.tenant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.saas.common.core.BaseServiceImpl;
import com.saas.common.enums.ErrorCode;
import com.saas.common.tenant.TenantContextHolder;
import com.saas.common.web.GlobalExceptionHandler.BusinessException;
import com.saas.common.web.PageDTO;
import com.saas.module.tenant.entity.SysTenant;
import com.saas.module.tenant.entity.SysTenantPackage;
import com.saas.module.tenant.mapper.SysTenantMapper;
import com.saas.module.tenant.mapper.SysTenantPackageMapper;
import com.saas.module.tenant.service.TenantService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 租户服务实现类
 *
 * @author saas
 */
@Service
public class TenantServiceImpl extends BaseServiceImpl<SysTenantMapper, SysTenant> implements TenantService {

    private final SysTenantMapper tenantMapper;
    private final SysTenantPackageMapper packageMapper;

    public TenantServiceImpl(SysTenantMapper tenantMapper, SysTenantPackageMapper packageMapper) {
        this.tenantMapper = tenantMapper;
        this.packageMapper = packageMapper;
    }

    @Override
    public IPage<SysTenant> pageTenant(PageDTO pageDTO, SysTenant tenant) {
        Page<SysTenant> page = new Page<>(pageDTO.getCurrent(), pageDTO.getSize());
        LambdaQueryWrapper<SysTenant> queryWrapper = new LambdaQueryWrapper<>();
        if (tenant.getTenantName() != null) {
            queryWrapper.like(SysTenant::getTenantName, tenant.getTenantName());
        }
        if (tenant.getTenantCode() != null) {
            queryWrapper.eq(SysTenant::getTenantCode, tenant.getTenantCode());
        }
        if (tenant.getStatus() != null) {
            queryWrapper.eq(SysTenant::getStatus, tenant.getStatus());
        }
        queryWrapper.orderByDesc(SysTenant::getCreatedAt);
        return tenantMapper.selectUserPage(page, queryWrapper);
    }

    @Override
    public SysTenant getById(Long tenantId) {
        return tenantMapper.selectById(tenantId);
    }

    @Override
    public SysTenant getByCode(String code) {
        LambdaQueryWrapper<SysTenant> queryWrapper = new LambdaQueryWrapper<SysTenant>()
                .eq(SysTenant::getTenantCode, code);
        return tenantMapper.selectOne(queryWrapper);
    }

    @Override
    public List<SysTenant> listAll() {
        LambdaQueryWrapper<SysTenant> queryWrapper = new LambdaQueryWrapper<SysTenant>()
                .orderByDesc(SysTenant::getCreatedAt);
        return tenantMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional
    public Long createTenant(SysTenant tenant) {
        // 校验编码唯一性
        if (!checkCodeUnique(tenant.getTenantCode(), null)) {
            throw new BusinessException(ErrorCode.RESOURCE_CONFLICT);
        }
        tenantMapper.insert(tenant);
        return tenant.getId();
    }

    @Override
    public boolean updateTenant(SysTenant tenant) {
        // 校验编码唯一性
        if (!checkCodeUnique(tenant.getTenantCode(), tenant.getId())) {
            throw new BusinessException(ErrorCode.RESOURCE_CONFLICT);
        }
        return tenantMapper.updateById(tenant) > 0;
    }

    @Override
    @Transactional
    public boolean deleteTenant(Long tenantId) {
        return tenantMapper.deleteById(tenantId) > 0;
    }

    @Override
    public boolean enableTenant(Long tenantId) {
        SysTenant tenant = new SysTenant();
        tenant.setId(tenantId);
        tenant.setStatus("NORMAL");
        return tenantMapper.updateById(tenant) > 0;
    }

    @Override
    public boolean disableTenant(Long tenantId) {
        SysTenant tenant = new SysTenant();
        tenant.setId(tenantId);
        tenant.setStatus("FROZEN");
        return tenantMapper.updateById(tenant) > 0;
    }

    @Override
    public boolean checkCodeUnique(String code, Long excludeId) {
        LambdaQueryWrapper<SysTenant> queryWrapper = new LambdaQueryWrapper<SysTenant>()
                .eq(SysTenant::getTenantCode, code)
                .ne(excludeId != null, SysTenant::getId, excludeId);
        return tenantMapper.selectCount(queryWrapper) == 0;
    }

    @Override
    public boolean isExpired(Long tenantId) {
        SysTenant tenant = tenantMapper.selectById(tenantId);
        if (tenant == null || tenant.getExpireTime() == null) {
            return false;
        }
        return tenant.getExpireTime().isBefore(LocalDateTime.now());
    }

    @Override
    public List<SysTenantPackage> listPackages() {
        LambdaQueryWrapper<SysTenantPackage> queryWrapper = new LambdaQueryWrapper<SysTenantPackage>()
                .orderByAsc(SysTenantPackage::getSortOrder);
        return packageMapper.selectList(queryWrapper);
    }

    @Override
    public SysTenantPackage getPackageById(Long packageId) {
        return packageMapper.selectById(packageId);
    }

    @Override
    @Transactional
    public Long createPackage(SysTenantPackage pkg) {
        packageMapper.insert(pkg);
        return pkg.getId();
    }

    @Override
    public boolean updatePackage(SysTenantPackage pkg) {
        return packageMapper.updateById(pkg) > 0;
    }

    @Override
    @Transactional
    public boolean deletePackage(Long packageId) {
        return packageMapper.deleteById(packageId) > 0;
    }
}
