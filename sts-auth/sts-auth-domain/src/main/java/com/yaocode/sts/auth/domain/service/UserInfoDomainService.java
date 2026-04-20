package com.yaocode.sts.auth.domain.service;

import com.yaocode.sts.auth.domain.command.CreateUserCommand;
import com.yaocode.sts.auth.domain.entity.UserInfoEntity;
import com.yaocode.sts.common.domain.valueobject.TenantId;
import com.yaocode.sts.common.domain.valueobject.UserId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.Username;

import java.util.List;

/**
 * 用户信息领域服务层
 * @author: Jin-LiangBo
 * @date: 2025年10月07日 22:11
 */
public interface UserInfoDomainService {

    /**
     * 创建用户领域逻辑
     * @param command CreateUserCommand
     * @return UserInfoEntity
     */
    UserInfoEntity createUser(CreateUserCommand command);

    /**
     * 验证用户名唯一性
     * @param username 用户名称
     * @param tenantId 租户id
     * @return boolean true 唯一，false 重复
     */
    boolean isUsernameUnique(Username username, TenantId tenantId);

    /**
     * 验证用户名唯一性
     * @param username 用户名称
     * @param tenantIdList 租户id
     * @return boolean true 唯一，false 重复
     */
    boolean isUsernameUnique(Username username, List<TenantId> tenantIdList);

    /**
     * 验证用户状态
     * @param user UserInfoEntity
     */
    void validateUserStatus(UserInfoEntity user);

    /**
     * 验证用户数据
     * @param tenantId 租户id
     * @param userId 用户id
     * @return boolean
     */
    boolean validateUser(TenantId tenantId, UserId userId);

}
