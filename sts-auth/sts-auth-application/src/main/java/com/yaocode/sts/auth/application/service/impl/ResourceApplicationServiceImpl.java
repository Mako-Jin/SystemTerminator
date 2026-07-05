package com.yaocode.sts.auth.application.service.impl;

import com.yaocode.sts.auth.application.converter.ResourceApplicationConverter;
import com.yaocode.sts.auth.application.dto.ResourceDto;
import com.yaocode.sts.auth.application.enums.AuthErrorCodeEnums;
import com.yaocode.sts.auth.application.exception.AuthServerException;
import com.yaocode.sts.auth.application.service.ResourceApplicationService;
import com.yaocode.sts.auth.domain.entity.ResourceInfoEntity;
import com.yaocode.sts.auth.domain.repository.ResourceRepository;
import com.yaocode.sts.auth.domain.service.ResourceDomainService;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.ResourceId;
import com.yaocode.sts.common.resources.model.ResourcesModel;
import com.yaocode.sts.common.tools.id.IdFactory;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年11月13日 22:55
 */
@Service
public class ResourceApplicationServiceImpl implements ResourceApplicationService {

    @Resource
    private ResourceRepository resourceRepository;

    @Resource
    private ResourceDomainService resourceDomainService;

    @Resource
    private ResourceApplicationConverter resourceApplicationConverter;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String singleAdd(ResourceDto resourceDto) {
        resourceDto.setResourceId(IdFactory.generate().toString());
        ResourceInfoEntity ResourceInfoEntity = resourceApplicationConverter.toEntity(resourceDto);
        if (!resourceDomainService.checkResourceEntity(ResourceInfoEntity)) {
            throw new AuthServerException(AuthErrorCodeEnums.RESOURCE_EXISTS);
        }
        return resourceRepository.save(ResourceInfoEntity).getValue();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<String> batchAdd(List<ResourceDto> resourceDtoList) {
        List<ResourceInfoEntity> ResourceInfoEntityList = resourceApplicationConverter.toEntityList(resourceDtoList);
        List<ResourceId> resourceIdList = batchSave(ResourceInfoEntityList);
        return resourceIdList.stream().map(ResourceId::getValue).toList();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchSaveResources(List<ResourcesModel> resourcesModelList) {
        List<ResourceInfoEntity> ResourceInfoEntityList = resourceApplicationConverter.batchToEntity(resourcesModelList);
        List<ResourceId> resourceIdList = batchSave(ResourceInfoEntityList);
        return !resourceIdList.isEmpty();
    }

    private List<ResourceId> batchSave(List<ResourceInfoEntity> ResourceInfoEntityList) {
        List<ResourceInfoEntity> filterResourceInfoEntityList =
                resourceDomainService.checkResourceEntityList(ResourceInfoEntityList);
        return resourceDomainService.batchSave(filterResourceInfoEntityList);
    }

}