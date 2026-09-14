package com.saas.common.core;

import com.saas.common.web.R;
import com.saas.common.web.PageDTO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * Controller 基类
 *
 * @param <S> Service类型
 * @param <T> 实体类型
 * @author saas
 */
public abstract class BaseController<S extends BaseService<?, T>, T extends BaseEntity> {

    /**
     * 获取 Service
     *
     * @return Service实例
     */
    protected abstract S getService();

    /**
     * 分页查询
     *
     * @param pageDTO 分页参数
     * @return 分页结果
     */
    protected R<IPage<T>> page(PageDTO pageDTO) {
        Page<T> page = new Page<>(pageDTO.getCurrent(), pageDTO.getSize());
        IPage<T> result = getService().page(page);
        return R.ok(result);
    }

    /**
     * 分页查询（带条件）
     *
     * @param pageDTO 分页参数
     * @param queryWrapper 查询条件
     * @return 分页结果
     */
    protected R<IPage<T>> page(PageDTO pageDTO, com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<T> queryWrapper) {
        Page<T> page = new Page<>(pageDTO.getCurrent(), pageDTO.getSize());
        IPage<T> result = getService().page(page, queryWrapper);
        return R.ok(result);
    }

    /**
     * 查询列表
     *
     * @return 列表结果
     */
    protected R<List<T>> list() {
        List<T> list = getService().list();
        return R.ok(list);
    }

    /**
     * 根据ID查询
     *
     * @param id 主键ID
     * @return 实体
     */
    protected R<T> getById(Long id) {
        T entity = getService().getById(id);
        return entity != null ? R.ok(entity) : R.fail(40401, "记录不存在");
    }

    /**
     * 保存
     *
     * @param entity 实体
     * @return 是否成功
     */
    protected R<Boolean> save(T entity) {
        boolean result = getService().saveEntity(entity);
        return result ? R.ok(true) : R.fail(50001, "保存失败");
    }

    /**
     * 更新
     *
     * @param entity 实体
     * @return 是否成功
     */
    protected R<Boolean> update(T entity) {
        boolean result = getService().updateEntity(entity);
        return result ? R.ok(true) : R.fail(50001, "更新失败");
    }

    /**
     * 删除
     *
     * @param id 主键ID
     * @return 是否成功
     */
    protected R<Boolean> removeById(Long id) {
        boolean result = getService().removeByIdEntity(id);
        return result ? R.ok(true) : R.fail(50001, "删除失败");
    }

    /**
     * 批量删除
     *
     * @param ids ID集合
     * @return 是否成功
     */
    protected R<Boolean> removeByIds(List<Long> ids) {
        boolean result = getService().removeByIdsEntity(ids);
        return result ? R.ok(true) : R.fail(50001, "批量删除失败");
    }
}
