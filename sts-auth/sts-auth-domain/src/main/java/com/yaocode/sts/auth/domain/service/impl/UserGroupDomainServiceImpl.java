package com.yaocode.sts.auth.domain.service.impl;

import com.yaocode.sts.auth.domain.service.UserGroupDomainService;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.UserGroupId;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户组领域服务实现
 * @author: Jin-LiangBo
 * @date: 2025年10月14日 20:17
 */
@Service
public class UserGroupDomainServiceImpl implements UserGroupDomainService {
    @Override
    public boolean validateUserGroupId(UserGroupId userGroupId) {
        return false;
    }

    @Override
    public boolean validateUserGroupId(List<UserGroupId> userGroupIdList) {
        return false;
    }
}
