package com.saas.common.core;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * Service 基类接口
 *
 * @param <M> Mapper类型
 * @param <T> 实体类型
 * @author saas
 */
public interface BaseService<M extends BaseMapper<T>, T extends BaseEntity> extends IService<T> {

    /**
     * 根据 ID 查询
     */
    T getById(Long id);

    // 注意: listByIds 方法使用父接口 IService 中的定义
    // 其签名为: List<T> listByIds(Collection<? extends Serializable> ids)

    /**
     * 保存实体
     */
    boolean saveEntity(T entity);

    /**
     * 更新实体
     */
    boolean updateEntity(T entity);

    /**
     * 删除实体
     */
    boolean removeByIdEntity(Long id);

    /**
     * 批量删除实体
     */
    boolean removeByIdsEntity(Collection<Long> ids);
}
