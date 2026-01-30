package com.yaocode.sts.auth.infrastructure.mybatis.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaocode.sts.auth.infrastructure.mybatis.dao.ResourceDao;
import com.yaocode.sts.auth.infrastructure.mybatis.mapper.ResourceMapper;
import com.yaocode.sts.auth.infrastructure.po.ResourcePo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年11月13日 23:26
 */
@Service
public class ResourceDaoImpl extends ServiceImpl<ResourceMapper, ResourcePo> implements ResourceDao {

    @Resource
    private ResourceMapper resourceMapper;

    @Override
    public ResourcePo getByPo(ResourcePo resourcePo) {
        LambdaQueryWrapper<ResourcePo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ResourcePo::getResourceValue, resourcePo.getResourceValue());
        wrapper.eq(ResourcePo::getResourceName, resourcePo.getResourceName());
        wrapper.eq(ResourcePo::getResourceType, resourcePo.getResourceType());
        wrapper.eq(ResourcePo::getRequestMethod, resourcePo.getRequestMethod());
        wrapper.eq(ResourcePo::getRequestUrl, resourcePo.getRequestUrl());
        wrapper.eq(ResourcePo::getVersion, resourcePo.getVersion());
        wrapper.eq(ResourcePo::getIsEnabled, resourcePo.getIsEnabled());
        return resourceMapper.selectOne(wrapper);
    }

    @Override
    public List<ResourcePo> getByPoList(List<ResourcePo> resourcePoList) {
        LambdaQueryWrapper<ResourcePo> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(ResourcePo::getResourceValue, resourcePoList.stream().map(ResourcePo::getResourceValue).filter(Objects::nonNull).toList());
        wrapper.in(ResourcePo::getResourceName, resourcePoList.stream().map(ResourcePo::getResourceName).filter(Objects::nonNull).toList());
        wrapper.in(ResourcePo::getResourceType, resourcePoList.stream().map(ResourcePo::getResourceType).filter(Objects::nonNull).toList());
        // TODO 这块有个问题，这种列表转字符串的，如果其他都一样，但是逗号分隔的字符串只少一个值，或者多一个值，岂不是两条数据了。
        wrapper.in(ResourcePo::getRequestMethod, resourcePoList.stream().map(ResourcePo::getRequestMethod).filter(Objects::nonNull).toList());
        wrapper.in(ResourcePo::getRequestUrl, resourcePoList.stream().map(ResourcePo::getRequestUrl).filter(Objects::nonNull).toList());
        wrapper.in(ResourcePo::getVersion, resourcePoList.stream().map(ResourcePo::getVersion).filter(Objects::nonNull).toList());
        wrapper.in(ResourcePo::getIsEnabled, resourcePoList.stream().map(ResourcePo::getIsEnabled).filter(Objects::nonNull).toList());
        return resourceMapper.selectList(wrapper);
    }
}
