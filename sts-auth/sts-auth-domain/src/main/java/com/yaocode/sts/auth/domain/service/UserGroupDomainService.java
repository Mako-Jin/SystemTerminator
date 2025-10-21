package com.yaocode.sts.auth.domain.service;

import com.yaocode.sts.auth.domain.valueobjects.identifiers.UserGroupId;

import java.util.List;

/**
 * 用户组领域服务
 * @author: Jin-LiangBo
 * @date: 2025年10月14日 20:15
 */
public interface UserGroupDomainService {

    /**
     * 验证用户组id有效性
     * @param userGroupId 用户组id
     * @return boolean
     */
    boolean validateUserGroupId(UserGroupId userGroupId);

    /**
     * 验证用户组id有效性
     * @param userGroupIdList 用户组id列表
     * @return boolean
     */
    boolean validateUserGroupId(List<UserGroupId> userGroupIdList);

}
