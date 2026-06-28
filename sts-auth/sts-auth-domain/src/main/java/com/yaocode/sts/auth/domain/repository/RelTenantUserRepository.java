package com.yaocode.sts.auth.domain.repository;

import com.yaocode.sts.auth.domain.entity.relation.RelTenantUserEntity;
import com.yaocode.sts.common.domain.valueobject.UserId;

import java.util.List;

public interface RelTenantUserRepository {

    /**
     * 根据用户ID查询所有租户关联
     */
    List<RelTenantUserEntity> findByUserId(UserId userId);

    /**
     * 根据用户ID和租户ID查询
     */
    RelTenantUserEntity findByUserIdAndTenantId(String userId, String tenantId);

    /**
     * 根据标识符（用户名/手机号/邮箱）查询所有租户关联
     * 标识符匹配策略：精确匹配username，或关联的contact_value
     */
    List<RelTenantUserEntity> findByIdentifier(String identifier);

    /**
     * 根据用户名和租户ID查询
     */
    RelTenantUserEntity findByUsernameAndTenantId(String username, String tenantId);

}
