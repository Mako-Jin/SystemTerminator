package com.yaocode.sts.auth.domain.service.impl;

import com.yaocode.sts.auth.domain.entity.UserInfoEntity;
import com.yaocode.sts.auth.domain.service.UserInfoDomainService;
import org.springframework.stereotype.Service;

/**
 * 用户信息领域服务
 * @author: Jin-LiangBo
 * @date: 2025年10月07日 23:18
 */
@Service
public class UserInfoDomainServiceImpl implements UserInfoDomainService {
    @Override
    public void createUser(UserInfoEntity user) {

    }

    @Override
    public boolean isUsernameUnique(String username) {
        return false;
    }

    @Override
    public void validateUserStatus(UserInfoEntity user) {

    }
}
