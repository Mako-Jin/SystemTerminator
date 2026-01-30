package com.yaocode.sts.auth.domain.service;

import com.yaocode.sts.auth.domain.entity.ResourceEntity;

import java.util.List;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年11月13日 23:01
 */
public interface ResourceDomainService {

    /**
     * 检查资源数据
     * @param resourceEntity 资源数据
     * @return boolean true 检查通过， false: 检查不通过
     */
    boolean checkResourceEntity(ResourceEntity resourceEntity);

    /**
     * 检查资源数据是否满足保存条件
     * @param resourceEntityList 资源列表
     * @return java.util.List<ResourceEntity>
     */
    List<ResourceEntity> checkResourceEntityList(List<ResourceEntity> resourceEntityList);

}
