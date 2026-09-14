package com.saas.module.tenant.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.saas.module.tenant.entity.SysTenant;
import org.apache.ibatis.annotations.Mapper;

/**
 * 租户 Mapper
 *
 * @author saas
 */
@Mapper
public interface SysTenantMapper extends BaseMapper<SysTenant> {

    /**
     * 分页查询
     */
    default IPage<SysTenant> selectUserPage(Page<SysTenant> page, LambdaQueryWrapper<SysTenant> queryWrapper) {
        return selectPage(page, queryWrapper);
    }
}
