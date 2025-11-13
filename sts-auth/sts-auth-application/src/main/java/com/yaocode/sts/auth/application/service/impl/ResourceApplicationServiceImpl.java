package com.yaocode.sts.auth.application.service.impl;

import com.yaocode.sts.auth.application.converter.ResourceApplicationConverter;
import com.yaocode.sts.auth.application.dto.ResourceDto;
import com.yaocode.sts.auth.application.service.ResourceApplicationService;
import com.yaocode.sts.auth.domain.entity.ResourceEntity;
import com.yaocode.sts.auth.domain.repository.ResourceRepository;
import com.yaocode.sts.auth.domain.service.ResourceDomainService;
import com.yaocode.sts.common.tools.id.IdFactory;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        ResourceEntity resourceEntity = resourceApplicationConverter.toEntity(resourceDto);
        return resourceRepository.save(resourceEntity).getValue();
    }
}
