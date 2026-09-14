package com.saas.module.system.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.saas.common.core.BaseMapper;
import com.saas.module.system.entity.SysDict;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 字典 Mapper 接口
 *
 * @author saas
 */
@Mapper
public interface SysDictMapper extends BaseMapper<SysDict> {

    /**
     * 分页查询字典
     *
     * @param page         分页参数
     * @param queryWrapper 查询条件
     * @return 分页结果
     */
    IPage<SysDict> selectUserPage(Page<SysDict> page, @Param("ew") com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<SysDict> queryWrapper);
}
