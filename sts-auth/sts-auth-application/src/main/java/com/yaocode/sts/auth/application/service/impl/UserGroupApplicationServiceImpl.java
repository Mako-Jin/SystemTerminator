package com.yaocode.sts.auth.application.service.impl;

import com.yaocode.sts.auth.application.converter.UserGroupApplicationConverter;
import com.yaocode.sts.auth.application.dto.UserGroupDto;
import com.yaocode.sts.auth.application.service.UserGroupApplicationService;
import com.yaocode.sts.auth.domain.entity.UserGroupEntity;
import com.yaocode.sts.auth.domain.repository.UserGroupRepository;
import com.yaocode.sts.auth.domain.service.TenantDomainService;
import com.yaocode.sts.auth.domain.service.UserGroupDomainService;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.TenantId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.UserGroupId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.UserGroupCode;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年10月28日 21:47
 */
@Service
public class UserGroupApplicationServiceImpl implements UserGroupApplicationService {

    @Resource
    private UserGroupDomainService userGroupDomainService;

    @Resource
    private UserGroupRepository userGroupRepository;

    @Resource
    private UserGroupApplicationConverter userGroupApplicationConverter;

    @Resource
    private TenantDomainService tenantDomainService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String singleAdd(UserGroupDto userGroupDto) {
        TenantId tenantId = TenantId.of(userGroupDto.getTenantId());
        UserGroupCode userGroupCode = UserGroupCode.of(userGroupDto.getUserGroupCode());
        UserGroupId parentId = UserGroupId.of(userGroupDto.getUserGroupId());
        UserGroupEntity entity = UserGroupEntity.build(
                tenantDomainService,
                userGroupDomainService,
                tenantId,
                userGroupCode,
                userGroupDto.getUserGroupName(),
                userGroupDto.getUserGroupDesc(),
                parentId
        );
        return userGroupRepository.save(entity).getValue();
    }

}
