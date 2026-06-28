package com.yaocode.sts.auth.infrastructure.mybatis.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaocode.sts.auth.infrastructure.mybatis.dao.ResourceDao;
import com.yaocode.sts.auth.infrastructure.mybatis.mapper.ResourceMapper;
import com.yaocode.sts.auth.infrastructure.po.ResourceInfoPo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年11月13日 23:26
 */
@Repository
public class ResourceDaoImpl extends ServiceImpl<ResourceMapper, ResourceInfoPo> implements ResourceDao {

    @Resource
    private ResourceMapper resourceMapper;

    @Override
    public ResourceInfoPo getByPo(ResourceInfoPo resourcePo) {
        LambdaQueryWrapper<ResourceInfoPo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ResourceInfoPo::getResourceValue, resourcePo.getResourceValue());
        wrapper.eq(ResourceInfoPo::getResourceName, resourcePo.getResourceName());
        wrapper.eq(ResourceInfoPo::getResourceType, resourcePo.getResourceType());
        wrapper.eq(ResourceInfoPo::getRequestMethod, resourcePo.getRequestMethod());
        wrapper.eq(ResourceInfoPo::getRequestUrl, resourcePo.getRequestUrl());
        wrapper.eq(ResourceInfoPo::getVersion, resourcePo.getVersion());
        wrapper.eq(ResourceInfoPo::getIsEnabled, resourcePo.getIsEnabled());
        return resourceMapper.selectOne(wrapper);
    }

    @Override
    public List<ResourceInfoPo> getByPoList(List<ResourceInfoPo> resourcePoList) {
        LambdaQueryWrapper<ResourceInfoPo> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(ResourceInfoPo::getResourceValue, resourcePoList.stream().map(ResourceInfoPo::getResourceValue).filter(Objects::nonNull).collect(Collectors.toSet()));
        // wrapper.in(ResourcePo::getResourceName, resourcePoList.stream().map(ResourcePo::getResourceName).filter(Objects::nonNull).collect(Collectors.toSet()));
        // wrapper.in(ResourcePo::getResourceType, resourcePoList.stream().map(ResourcePo::getResourceType).filter(Objects::nonNull).collect(Collectors.toSet()));
        // // TODO 这块有个问题，这种列表转字符串的，如果其他都一样，但是逗号分隔的字符串只少一个值，或者多一个值，岂不是两条数据了。
        // wrapper.in(ResourcePo::getRequestMethod, resourcePoList.stream().map(ResourcePo::getRequestMethod).filter(Objects::nonNull).collect(Collectors.toSet()));
        // wrapper.in(ResourcePo::getRequestUrl, resourcePoList.stream().map(ResourcePo::getRequestUrl).filter(Objects::nonNull).collect(Collectors.toSet()));
        // wrapper.in(ResourcePo::getVersion, resourcePoList.stream().map(ResourcePo::getVersion).filter(Objects::nonNull).collect(Collectors.toSet()));
        // wrapper.in(ResourcePo::getIsEnabled, resourcePoList.stream().map(ResourcePo::getIsEnabled).filter(Objects::nonNull).collect(Collectors.toSet()));
        return resourceMapper.selectList(wrapper);
    }
}
