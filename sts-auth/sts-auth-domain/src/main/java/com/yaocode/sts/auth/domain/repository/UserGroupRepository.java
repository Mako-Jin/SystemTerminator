package com.yaocode.sts.auth.domain.repository;

import com.yaocode.sts.auth.domain.entity.UserGroupEntity;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.TenantId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.UserGroupId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.UserId;
import com.yaocode.sts.common.domain.Repository;

/**
 * 用户组仓库接口
 * @author: Jin-LiangBo
 * @date: 2025年10月13日 22:56
 */
public interface UserGroupRepository extends Repository<UserGroupEntity, UserGroupId> {

    /**
     * 保存用户组用户关联关系
     * @param tenantId 租户id
     * @param userGroupId 用户组id
     * @param userId 用户id
     */
    void saveRelUserGroupUser(TenantId tenantId, UserGroupId userGroupId, UserId userId);

}
