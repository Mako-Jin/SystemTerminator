package com.yaocode.sts.auth.domain.repository;

import com.yaocode.sts.auth.domain.entity.ResourceInfoEntity;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.ResourceId;
import com.yaocode.sts.common.domain.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 资源信息仓库接口
 * @author: Jin-LiangBo
 * @date: 2025年11月13日 23:00
 */
public interface ResourceRepository extends Repository<ResourceInfoEntity, ResourceId> {

    /**
     * 批量保存资源列表数据
     * @param resourceEntityList 资源实体列表
     * @return java.util.List<java.lang.String>
     */
    List<ResourceId> batchSave(List<ResourceInfoEntity> resourceEntityList);

    /**
     * 根据实体查询实体，组合唯一索引的意思
     * @param resourceEntity 要检查的资源数据
     * @return java.util.Optional<ResourceEntity>
     */
    Optional<ResourceInfoEntity> findByEntity(ResourceInfoEntity resourceEntity);

    /**
     * 检查实体数据存在于数据库
     * @param resourceEntityList 资源数据列表
     * @return List<ResourceEntity>
     */
    List<ResourceInfoEntity> findByEntityList(List<ResourceInfoEntity> resourceEntityList);

    /**
     * 更新资源实体
     * @param resourceEntity 资源实体
     * @return ResourceId
     */
    ResourceId update(ResourceInfoEntity resourceEntity);

    /**
     * 批量更新资源实体
     * @param resourceEntityList 资源实体列表
     * @return List<ResourceId>
     */
    List<ResourceId> batchUpdate(List<ResourceInfoEntity> resourceEntityList);

}