package com.yaocode.sts.auth.domain.service.impl;

import com.yaocode.sts.auth.domain.entity.UserInfoEntity;
import com.yaocode.sts.auth.domain.repository.UserInfoRepository;
import com.yaocode.sts.auth.domain.service.UserInfoDomainService;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.TenantId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.UserId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.Username;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户信息领域服务
 * @author: Jin-LiangBo
 * @date: 2025年10月07日 23:18
 */
@Service
public class UserInfoDomainServiceImpl implements UserInfoDomainService {

    @Resource
    private UserInfoRepository userInfoRepository;

    @Override
    public void createUser(UserInfoEntity user) {

    }

    @Override
    public boolean isUsernameUnique(Username username, TenantId tenantId) {
        List<UserId> userIdList = userInfoRepository.getByTenantId(tenantId);
        return userInfoRepository.findByUsername(userIdList, username).isEmpty();
    }

    @Override
    public void validateUserStatus(UserInfoEntity user) {

    }
}
