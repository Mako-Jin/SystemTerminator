package com.yaocode.sts.auth.domain.repository;

import com.yaocode.sts.auth.domain.entity.UserInfoEntity;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.TenantId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.UserId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.Username;
import com.yaocode.sts.common.domain.Repository;

import java.util.List;
import java.util.Optional;

/**
 * db层接口
 * @author: Jin-LiangBo
 * @date: 2025年10月07日 23:24
 */
public interface UserInfoRepository extends Repository<UserInfoEntity, UserId> {

    /**
     * 通过用户名查询用户信息
     * @param userIdList 用户id列表
     * @param username 用户名
     * @return java.util.Optional<UserInfoEntity>
     */
    Optional<UserInfoEntity> findByUsername(List<UserId> userIdList, Username username);

    /**
     * 获取租户下的用户列表
     * @param tenantId 租户id
     * @return java.util.List<UserId>
     */
    List<UserId> getByTenantId(TenantId tenantId);

}
