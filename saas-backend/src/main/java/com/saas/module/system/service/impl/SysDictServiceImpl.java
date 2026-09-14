package com.saas.module.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.saas.common.core.BaseServiceImpl;
import com.saas.common.enums.ErrorCode;
import com.saas.common.tenant.TenantContextHolder;
import com.saas.common.web.GlobalExceptionHandler.BusinessException;
import com.saas.common.web.PageDTO;
import com.saas.module.system.entity.SysDict;
import com.saas.module.system.mapper.SysDictMapper;
import com.saas.module.system.service.SysDictService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * 字典服务实现类
 *
 * @author saas
 */
@Service
@RequiredArgsConstructor
public class SysDictServiceImpl extends BaseServiceImpl<SysDictMapper, SysDict> implements SysDictService {

    private static final Logger log = LoggerFactory.getLogger(SysDictServiceImpl.class);

    private final SysDictMapper sysDictMapper;

    // Redis 为可选依赖
    @Autowired(required = false)
    private RedisTemplate<String, Object> redisTemplate;

    // 内存缓存作为无 Redis 时的替代
    private final ConcurrentHashMap<String, CachedDict> memoryCache = new ConcurrentHashMap<>();

    private static final String DICT_CACHE_PREFIX = "saas:dict:";
    private static final long CACHE_EXPIRE_HOURS = 24;

    @Override
    public IPage<SysDict> pageDict(PageDTO pageDTO, SysDict dict) {
        Page<SysDict> page = new Page<>(pageDTO.getCurrent(), pageDTO.getSize());
        LambdaQueryWrapper<SysDict> queryWrapper = new LambdaQueryWrapper<>();
        if (dict.getDictName() != null) {
            queryWrapper.like(SysDict::getDictName, dict.getDictName());
        }
        if (dict.getDictCode() != null) {
            queryWrapper.eq(SysDict::getDictCode, dict.getDictCode());
        }
        if (dict.getStatus() != null) {
            queryWrapper.eq(SysDict::getStatus, dict.getStatus());
        }
        queryWrapper.orderByDesc(SysDict::getCreatedAt);
        return sysDictMapper.selectUserPage(page, queryWrapper);
    }

    @Override
    public List<SysDict> listAll() {
        LambdaQueryWrapper<SysDict> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(SysDict::getDictCode);
        return sysDictMapper.selectList(queryWrapper);
    }

    @Override
    public SysDict getById(Long dictId) {
        return sysDictMapper.selectById(dictId);
    }

    @Override
    public SysDict getByCode(String code) {
        // 尝试从 Redis 获取
        if (redisTemplate != null) {
            try {
                String cacheKey = DICT_CACHE_PREFIX + code;
                SysDict cachedDict = (SysDict) redisTemplate.opsForValue().get(cacheKey);
                if (cachedDict != null) {
                    return cachedDict;
                }
            } catch (Exception e) {
                log.warn("Redis缓存读取失败，使用数据库查询", e);
            }
        }

        // 从内存缓存获取
        CachedDict memoryCached = memoryCache.get(code);
        if (memoryCached != null && !memoryCached.isExpired()) {
            return memoryCached.dict;
        }

        // 从数据库获取
        LambdaQueryWrapper<SysDict> queryWrapper = new LambdaQueryWrapper<SysDict>()
                .eq(SysDict::getDictCode, code);
        SysDict dict = sysDictMapper.selectOne(queryWrapper);

        // 缓存
        if (dict != null) {
            if (redisTemplate != null) {
                try {
                    redisTemplate.opsForValue().set(DICT_CACHE_PREFIX + code, dict, CACHE_EXPIRE_HOURS, TimeUnit.HOURS);
                } catch (Exception e) {
                    log.warn("Redis缓存写入失败", e);
                }
            }
            memoryCache.put(code, new CachedDict(dict));
        }

        return dict;
    }

    @Override
    public Long createDict(SysDict dict) {
        // 校验编码唯一性
        if (!checkCodeUnique(dict.getDictCode(), null)) {
            throw new BusinessException(ErrorCode.RESOURCE_CONFLICT);
        }

        // 设置租户ID
        dict.setTenantId(TenantContextHolder.getTenantId());
        sysDictMapper.insert(dict);
        return dict.getId();
    }

    @Override
    public boolean updateDict(SysDict dict) {
        // 校验编码唯一性
        if (!checkCodeUnique(dict.getDictCode(), dict.getId())) {
            throw new BusinessException(ErrorCode.RESOURCE_CONFLICT);
        }

        // 刷新缓存
        refreshCache(dict.getId());
        return sysDictMapper.updateById(dict) > 0;
    }

    @Override
    public boolean deleteDict(Long dictId) {
        // 刷新缓存
        refreshCache(dictId);
        return sysDictMapper.deleteById(dictId) > 0;
    }

    @Override
    public boolean checkCodeUnique(String code, Long excludeId) {
        LambdaQueryWrapper<SysDict> queryWrapper = new LambdaQueryWrapper<SysDict>()
                .eq(SysDict::getDictCode, code)
                .ne(excludeId != null, SysDict::getId, excludeId);
        return sysDictMapper.selectCount(queryWrapper) == 0;
    }

    @Override
    public void refreshCache(Long dictId) {
        SysDict dict = sysDictMapper.selectById(dictId);
        if (dict != null) {
            // 清除 Redis 缓存
            if (redisTemplate != null) {
                try {
                    redisTemplate.delete(DICT_CACHE_PREFIX + dict.getDictCode());
                } catch (Exception e) {
                    log.warn("Redis缓存删除失败", e);
                }
            }
            // 清除内存缓存
            memoryCache.remove(dict.getDictCode());
        }
    }

    @Override
    public void refreshAllCache() {
        // 清除所有缓存
        if (redisTemplate != null) {
            try {
                redisTemplate.delete(redisTemplate.keys(DICT_CACHE_PREFIX + "*"));
            } catch (Exception e) {
                log.warn("Redis缓存清除失败", e);
            }
        }
        memoryCache.clear();
    }

    /**
     * 内存缓存数据
     */
    private static class CachedDict {
        SysDict dict;
        long createTime;

        CachedDict(SysDict dict) {
            this.dict = dict;
            this.createTime = System.currentTimeMillis();
        }

        boolean isExpired() {
            return System.currentTimeMillis() - createTime > CACHE_EXPIRE_HOURS * 60 * 60 * 1000;
        }
    }
}
