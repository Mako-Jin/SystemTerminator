package com.yaocode.sts.auth.domain.service;

import com.yaocode.sts.auth.domain.entity.UserInfoEntity;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.TenantId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.Username;

/**
 * 用户信息领域服务层
 * @author: Jin-LiangBo
 * @date: 2025年10月07日 22:11
 */
public interface UserInfoDomainService {

    /**
     * 创建用户领域逻辑
     * @param user UserInfoEntity
     */
    void createUser(UserInfoEntity user);

    /**
     * 验证用户名唯一性
     * @param username 用户名称
     * @param tenantId 租户id
     * @return boolean true 唯一，false 重复
     */
    boolean isUsernameUnique(Username username, TenantId tenantId);

    /**
     * 验证用户状态
     * @param user UserInfoEntity
     */
    void validateUserStatus(UserInfoEntity user);

}
