package com.saas.common.core;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.saas.common.tenant.TenantContextHolder;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * Service 基类实现
 *
 * @param <M> Mapper类型
 * @param <T> 实体类型
 * @author saas
 */
public abstract class BaseServiceImpl<M extends BaseMapper<T>, T extends BaseEntity>
        extends ServiceImpl<M, T> implements BaseService<M, T> {

    @Override
    public T getById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    public List<T> listByIds(Collection<? extends Serializable> ids) {
        return baseMapper.selectBatchIds(ids);
    }

    @Override
    public boolean saveEntity(T entity) {
        // 设置租户ID
        if (entity.getTenantId() == null) {
            entity.setTenantId(TenantContextHolder.getTenantId());
        }
        return save(entity);
    }

    @Override
    public boolean updateEntity(T entity) {
        return updateById(entity);
    }

    @Override
    public boolean removeByIdEntity(Long id) {
        return removeById(id);
    }

    @Override
    public boolean removeByIdsEntity(Collection<Long> ids) {
        return removeByIds(ids);
    }
}
