package com.saas.module.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.saas.module.system.entity.SysDict;
import com.saas.common.web.PageDTO;

import java.util.List;

/**
 * 字典服务接口
 *
 * @author saas
 */
public interface SysDictService extends IService<SysDict> {

    /**
     * 分页查询字典
     *
     * @param pageDTO 分页参数
     * @param dict    查询条件
     * @return 分页结果
     */
    IPage<SysDict> pageDict(PageDTO pageDTO, SysDict dict);

    /**
     * 获取所有字典
     *
     * @return 字典列表
     */
    List<SysDict> listAll();

    /**
     * 根据ID获取字典
     *
     * @param dictId 字典ID
     * @return 字典
     */
    SysDict getById(Long dictId);

    /**
     * 根据编码获取字典
     *
     * @param code 字典编码
     * @return 字典
     */
    SysDict getByCode(String code);

    /**
     * 创建字典
     *
     * @param dict 字典信息
     * @return 字典ID
     */
    Long createDict(SysDict dict);

    /**
     * 更新字典
     *
     * @param dict 字典信息
     * @return 是否成功
     */
    boolean updateDict(SysDict dict);

    /**
     * 删除字典
     *
     * @param dictId 字典ID
     * @return 是否成功
     */
    boolean deleteDict(Long dictId);

    /**
     * 校验字典编码是否唯一
     *
     * @param code     字典编码
     * @param excludeId 排除的字典ID
     * @return 是否唯一
     */
    boolean checkCodeUnique(String code, Long excludeId);

    /**
     * 刷新字典缓存
     *
     * @param dictId 字典ID
     */
    void refreshCache(Long dictId);

    /**
     * 刷新所有字典缓存
     */
    void refreshAllCache();
}
