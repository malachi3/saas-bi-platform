package com.saas.common.core;

/**
 * Mapper 基类接口
 *
 * @param <T> 实体类型
 * @author saas
 */
public interface BaseMapper<T> extends com.baomidou.mybatisplus.core.mapper.BaseMapper<T> {

    // MyBatis-Plus BaseMapper 已经提供了 deleteById 和 deleteBatchIds 方法
    // 这里直接继承父接口即可，无需重新定义

}
