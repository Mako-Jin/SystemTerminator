package com.yaocode.sts.auth.domain.service;

import com.yaocode.sts.auth.domain.valueobjects.identifiers.TenantId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.UserGroupId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.UserId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.UserGroupCode;

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
     * @param tenantId 租户id
     * @param userGroupId 用户组id
     * @return boolean
     */
    boolean validateUserGroupId(TenantId tenantId, UserGroupId userGroupId);

    /**
     * 验证用户组id有效性
     * @param userGroupIdList 用户组id列表
     * @return boolean
     */
    boolean validateUserGroupId(List<UserGroupId> userGroupIdList);

    /**
     * 用户组分配用户
     * @param tenantId 租户id
     * @param userGroupId 用户组id
     * @param userId 用户id
     */
    void associatedUserGroupUser(TenantId tenantId, UserGroupId userGroupId, UserId userId);

    /**
     * 用户组编码唯一性校验
     * @param tenantId 租户id
     * @param userGroupCode 用户组编码
     * @return boolean
     */
    boolean uniqueUserGroupCode(TenantId tenantId, UserGroupCode userGroupCode);

    /**
     * 用户组名称唯一性校验
     * @param tenantId 租户id
     * @param userGroupName 用户组名称
     * @return boolean
     */
    boolean uniqueUserGroupName(TenantId tenantId, String userGroupName);

}
