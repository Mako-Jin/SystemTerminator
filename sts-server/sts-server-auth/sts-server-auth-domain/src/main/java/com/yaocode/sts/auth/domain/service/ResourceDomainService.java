package com.yaocode.sts.auth.domain.service;

import com.yaocode.sts.auth.domain.entity.ResourceInfoEntity;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.ResourceId;

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
    boolean checkResourceEntity(ResourceInfoEntity resourceEntity);

    /**
     * 检查资源数据是否满足保存条件
     * @param resourceEntityList 资源列表
     * @return java.util.List<ResourceEntity>
     */
    List<ResourceInfoEntity> checkResourceEntityList(List<ResourceInfoEntity> resourceEntityList);

    /**
     * 批量保存资源数据
     * @param resourceEntityList 资源数据列表
     * @return java.util.List<ResourceId>
     */
    List<ResourceId> batchSave(List<ResourceInfoEntity> resourceEntityList);

    /**
     * 批量保存或更新资源数据（存在则更新，不存在则新增）
     * @param resourceEntityList 资源数据列表
     * @return java.util.List<ResourceId>
     */
    List<ResourceId> batchSaveOrUpdate(List<ResourceInfoEntity> resourceEntityList);

}