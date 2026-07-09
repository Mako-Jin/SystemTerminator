package com.yaocode.sts.auth.domain.service.impl;

import com.yaocode.sts.auth.domain.entity.ResourceInfoEntity;
import com.yaocode.sts.auth.domain.repository.ResourceRepository;
import com.yaocode.sts.auth.domain.service.ResourceDomainService;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.ResourceId;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年11月13日 23:45
 */
@Service
public class ResourceDomainServiceImpl implements ResourceDomainService {

    @Resource
    private ResourceRepository resourceRepository;

    @Override
    public boolean checkResourceEntity(ResourceInfoEntity resourceEntity) {
        if (Objects.isNull(resourceEntity)) {
            return false;
        }
        Optional<ResourceInfoEntity> optionalResourceEntity = resourceRepository.findByEntity(resourceEntity);
        return optionalResourceEntity.isEmpty();
    }

    @Override
    public List<ResourceInfoEntity> checkResourceEntityList(List<ResourceInfoEntity> resourceEntityList) {
        if (resourceEntityList == null || resourceEntityList.isEmpty()) {
            return Collections.emptyList();
        }
        List<ResourceInfoEntity> dbEntityList = resourceRepository.findByEntityList(resourceEntityList);
        return resourceEntityList.stream().filter(resourceEntity -> dbEntityList.stream().noneMatch(dbEntity ->
            Objects.equals(resourceEntity, dbEntity))).toList();
    }

    @Override
    public List<ResourceId> batchSave(List<ResourceInfoEntity> resourceEntityList) {
        return resourceRepository.batchSave(resourceEntityList);
    }

    @Override
    public List<ResourceId> batchSaveOrUpdate(List<ResourceInfoEntity> resourceEntityList) {
        if (resourceEntityList == null || resourceEntityList.isEmpty()) {
            return Collections.emptyList();
        }
        
        // 查询数据库中已存在的资源
        List<ResourceInfoEntity> dbEntityList = resourceRepository.findByEntityList(resourceEntityList);
        
        // 分离需要新增和需要更新的资源
        List<ResourceInfoEntity> toAddList = resourceEntityList.stream()
            .filter(newEntity -> dbEntityList.stream().noneMatch(newEntity::isSameAs))
            .toList();
        
        List<ResourceInfoEntity> toUpdateList = resourceEntityList.stream()
            .filter(newEntity -> dbEntityList.stream()
                .anyMatch(newEntity::isSameAs))
            .map(newEntity -> {
                // 找到对应的数据库实体
                ResourceInfoEntity dbEntity = dbEntityList.stream()
                    .filter(e -> e.isSameAs(newEntity))
                    .findFirst()
                    .orElseThrow();
                // 使用 upgrade 方法更新，保持原有的 resourceId
                return dbEntity.upgrade(newEntity);
            })
            .toList();
        
        // 执行批量新增
        List<ResourceId> savedIds = resourceRepository.batchSave(toAddList);
        
        // 执行批量更新
        List<ResourceId> updatedIds = resourceRepository.batchUpdate(toUpdateList);
        
        // 合并结果（使用流式合并，避免不可变集合问题）
        return Stream.concat(savedIds.stream(), updatedIds.stream()).toList();
    }

}