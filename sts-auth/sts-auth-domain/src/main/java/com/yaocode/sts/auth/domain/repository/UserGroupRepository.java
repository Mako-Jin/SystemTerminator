package com.yaocode.sts.auth.domain.repository;

import com.yaocode.sts.auth.domain.entity.UserGroupEntity;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.TenantId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.UserGroupId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.UserId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.UserGroupCode;
import com.yaocode.sts.common.domain.Repository;

import java.util.Optional;

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

    /**
     * 根据id查询数据
     * @param tenantId 租户id
     * @param userGroupId 用户组id
     * @return java.util.Optional<UserGroupEntity>
     */
    Optional<UserGroupEntity> findById(TenantId tenantId, UserGroupId userGroupId);

    /**
     * 根据用户组code查询数据
     * @param tenantId 租户id
     * @param userGroupCode 用户组编码
     * @return java.util.Optional<UserGroupEntity>
     */
    Optional<UserGroupEntity> findByUserGroupCode(TenantId tenantId, UserGroupCode userGroupCode);

    /**
     * 根据用户组名查询数据
     * @param tenantId 租户id
     * @param userGroupName 用户组名
     * @return java.util.Optional<UserGroupEntity>
     */
    Optional<UserGroupEntity> findUserGroupName(TenantId tenantId, String userGroupName);
}
