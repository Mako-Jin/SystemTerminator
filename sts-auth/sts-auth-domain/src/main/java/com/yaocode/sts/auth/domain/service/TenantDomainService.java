package com.yaocode.sts.auth.domain.service;

import com.yaocode.sts.auth.domain.enums.UserAddTypeEnums;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.TenantId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.UserId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.TenantCode;

import java.util.List;

/**
 * 租户领域服务
 * @author: Jin-LiangBo
 * @date: 2025年10月13日 20:28
 */
public interface TenantDomainService {

    /**
     * 验证租户id有效性
     * @param tenantIdList 租户id列表
     * @return boolean
     */
    boolean validateTenantId(List<TenantId> tenantIdList);

    /**
     * 验证租户id有效性
     * @param tenantId 租户id
     * @return boolean
     */
    boolean validateTenantId(TenantId tenantId);

    /**
     * 验证租户Code是否存在
     * @param tenantCode 租户编码
     * @return boolean
     */
    boolean existsByTenantCode(TenantCode tenantCode);

    /**
     * 验证租户名是否存在
     * @param tenantName 租户名
     * @return boolean
     */
    boolean existsByTenantName(String tenantName);

    /**
     * 绑定租户和用户关系
     * @param tenantId 租户id
     * @param userId 用户id
     * @param userAddType 用户新增类型
     */
    void associatedTenantUser(TenantId tenantId, UserId userId, UserAddTypeEnums userAddType);

}
