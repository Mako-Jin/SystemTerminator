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
import com.yaocode.sts.common.tools.id.IdFactory;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

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
        if (!tenantDomainService.validateTenantId(tenantId)) {
            throw new IllegalArgumentException("租户不存在");
        }
        UserGroupCode userGroupCode = UserGroupCode.of(userGroupDto.getUserGroupCode());
        if (userGroupDomainService.uniqueUserGroupCode(tenantId, userGroupCode)) {
            throw new IllegalArgumentException("角色编码已存在");
        }
        if (userGroupDomainService.uniqueUserGroupName(tenantId, userGroupDto.getUserGroupName())) {
            throw new IllegalArgumentException("角色名称已存在");
        }
        if (Objects.nonNull(userGroupDto.getParentId())) {
            UserGroupId parentId = UserGroupId.of(userGroupDto.getParentId());
            if (!userGroupDomainService.validateUserGroupId(tenantId, parentId)) {
                throw new IllegalArgumentException("父角色不存在");
            }
        }
        userGroupDto.setUserGroupId(IdFactory.generate().toString());
        UserGroupEntity entity = userGroupApplicationConverter.toEntity(userGroupDto);
        return userGroupRepository.save(entity).getValue();
    }

}
