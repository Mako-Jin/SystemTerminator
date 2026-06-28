package com.yaocode.sts.auth.domain.repository;

import com.yaocode.sts.auth.domain.entity.relation.RelUserGroupUserEntity;
import com.yaocode.sts.common.domain.valueobject.TenantId;
import com.yaocode.sts.common.domain.valueobject.UserId;

import java.util.List;

public interface RelUserGroupUserRepository {

    /**
     * 根据用户id和用户组id列表查询关联对象列表
     * @param userId 用户id
     * @param tenantIds 租户id列表
     * @return List<RelUserGroupUserEntity>
     */
    List<RelUserGroupUserEntity> findByUserAndTenants(UserId userId, List<TenantId> tenantIds);

}
