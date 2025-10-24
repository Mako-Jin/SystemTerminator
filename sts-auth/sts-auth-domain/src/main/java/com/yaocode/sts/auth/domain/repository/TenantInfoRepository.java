package com.yaocode.sts.auth.domain.repository;

import com.yaocode.sts.auth.domain.entity.TenantInfoEntity;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.TenantId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.UserId;
import com.yaocode.sts.common.domain.Repository;

import java.util.Optional;

/**
 * 租户信息DB接口
 * @author: Jin-LiangBo
 * @date: 2025年10月13日 22:55
 */
public interface TenantInfoRepository extends Repository<TenantInfoEntity, TenantId> {

    /**
     * 根据租户编码查询租户对象
     * @param tenantCode 租户编码
     * @return Optional<TenantInfoEntity>
     */
    Optional<TenantInfoEntity> getByTenantCode(String tenantCode);

    /**
     * 根据租户名称查询租户对象
     * @param tenantName 租户名
     * @return Optional<TenantInfoEntity>
     */
    Optional<TenantInfoEntity> getByTenantName(String tenantName);

    /**
     * 保存租户用户关联关系
     * @param tenantId 租户id
     * @param userId 用户id
     */
    void saveRelTenantUser(TenantId tenantId, UserId userId);

}
