package com.yaocode.sts.auth.domain.service.impl;

import com.yaocode.sts.auth.domain.entity.TenantInfoEntity;
import com.yaocode.sts.auth.domain.entity.UserGroupEntity;
import com.yaocode.sts.auth.domain.entity.UserInfoEntity;
import com.yaocode.sts.auth.domain.repository.TenantInfoRepository;
import com.yaocode.sts.auth.domain.repository.UserGroupRepository;
import com.yaocode.sts.auth.domain.repository.UserInfoRepository;
import com.yaocode.sts.auth.domain.service.UserGroupDomainService;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.TenantId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.UserGroupId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.UserId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.UserGroupCode;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 用户组领域服务实现
 * @author: Jin-LiangBo
 * @date: 2025年10月14日 20:17
 */
@Service
public class UserGroupDomainServiceImpl implements UserGroupDomainService {

    @Resource
    private TenantInfoRepository tenantInfoRepository;

    @Resource
    private UserGroupRepository userGroupRepository;

    @Resource
    private UserInfoRepository userInfoRepository;

    @Override
    public boolean validateUserGroupId(UserGroupId userGroupId) {
        return false;
    }

    @Override
    public boolean validateUserGroupId(TenantId tenantId, UserGroupId userGroupId) {
        return userGroupRepository.findById(tenantId, userGroupId).isPresent();
    }

    @Override
    public boolean validateUserGroupId(List<UserGroupId> userGroupIdList) {
        return false;
    }

    @Override
    public void associatedUserGroupUser(TenantId tenantId, UserGroupId userGroupId, UserId userId) {
        // 验证当前租户存在不存在
        Optional<TenantInfoEntity> tenantInfoEntity = tenantInfoRepository.findById(tenantId);
        if (tenantInfoEntity.isEmpty()) {
            throw new IllegalArgumentException("auth.params.data.not.exists");
        }
        // 检查用户存在不存在
        Optional<UserGroupEntity> userGroupEntity = userGroupRepository.findById(userGroupId);
        if (userGroupEntity.isEmpty()) {
            throw new IllegalArgumentException("auth.params.data.not.exists");
        }
        // 检查用户存在不存在
        Optional<UserInfoEntity> userInfoEntity = userInfoRepository.findById(userId);
        if (userInfoEntity.isEmpty()) {
            throw new IllegalArgumentException("auth.params.data.not.exists");
        }
        userGroupRepository.saveRelUserGroupUser(tenantId, userGroupId, userId);
    }

    @Override
    public boolean uniqueUserGroupCode(TenantId tenantId, UserGroupCode userGroupCode) {
        return userGroupRepository.findByUserGroupCode(tenantId, userGroupCode).isPresent();
    }

    @Override
    public boolean uniqueUserGroupName(TenantId tenantId, String userGroupName) {
        return userGroupRepository.findUserGroupName(tenantId, userGroupName).isPresent();
    }
}
