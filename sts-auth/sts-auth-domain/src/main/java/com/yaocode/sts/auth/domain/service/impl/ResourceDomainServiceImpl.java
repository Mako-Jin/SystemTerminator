package com.yaocode.sts.auth.domain.service.impl;

import com.yaocode.sts.auth.domain.entity.ResourceEntity;
import com.yaocode.sts.auth.domain.repository.ResourceRepository;
import com.yaocode.sts.auth.domain.service.ResourceDomainService;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.ResourceId;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
    public boolean checkResourceEntity(ResourceEntity resourceEntity) {
        if (Objects.isNull(resourceEntity)) {
            return false;
        }
        Optional<ResourceEntity> optionalResourceEntity = resourceRepository.findByEntity(resourceEntity);
        return optionalResourceEntity.isEmpty();
    }

    @Override
    public List<ResourceEntity> checkResourceEntityList(List<ResourceEntity> resourceEntityList) {
        if (resourceEntityList == null || resourceEntityList.isEmpty()) {
            return Collections.emptyList();
        }
        List<ResourceEntity> dbEntityList = resourceRepository.findByEntityList(resourceEntityList);
        return resourceEntityList.stream().filter(resourceEntity -> dbEntityList.stream().noneMatch(dbEntity ->
            Objects.equals(resourceEntity.getResourceValue(), dbEntity.getResourceValue())
                    && Objects.equals(resourceEntity.getResourceName(), dbEntity.getResourceName())
                    && Objects.equals(resourceEntity.getResourceType(), dbEntity.getResourceType())
                    && Objects.equals(resourceEntity.getIsEnabled(), dbEntity.getIsEnabled())
                    && Objects.equals(resourceEntity.getRequestMethod(), dbEntity.getRequestMethod())
                    && Objects.equals(resourceEntity.getVersion(), dbEntity.getVersion())
                    && Objects.equals(resourceEntity.getRequestUrl(), dbEntity.getRequestUrl())
        )).toList();
    }

    @Override
    public List<ResourceId> batchSave(List<ResourceEntity> resourceEntityList) {
        return resourceRepository.batchSave(resourceEntityList);
    }

}
