package com.yaocode.sts.auth.domain.repository;

import com.yaocode.sts.auth.domain.entity.relation.RelRoleUserEntity;
import com.yaocode.sts.common.domain.valueobject.TenantId;
import com.yaocode.sts.common.domain.valueobject.UserId;

import java.util.List;

public interface RelRoleUserRepository{

    /**
     * 根据用户ID和租户ID列表查询用户角色关联实体列表
     * @param userId 用户ID
     * @param tenantIds 租户ID列表
     * @return 用户角色关联实体列表
     */
    List<RelRoleUserEntity> findByUserAndTenants(UserId userId, List<TenantId> tenantIds);

}
