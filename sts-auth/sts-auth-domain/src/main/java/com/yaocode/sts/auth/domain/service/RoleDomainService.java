package com.yaocode.sts.auth.domain.service;

import com.yaocode.sts.auth.domain.entity.TenantInfoEntity;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.RoleId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.TenantId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.UserId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.RoleCode;

import java.util.List;

/**
 * 角色领域服务
 * @author: Jin-LiangBo
 * @date: 2025年10月14日 20:14
 */
public interface RoleDomainService {

    /**
     * 验证角色id有效性
     * @param roleId 角色id列表
     * @return boolean
     */
    boolean validateRoleId(RoleId roleId);

    /**
     * 验证角色id有效性
     * @param tenantId 租户id
     * @param roleId 角色id
     * @return boolean
     */
    boolean validateRoleId(TenantId tenantId, RoleId roleId);

    /**
     * 验证角色id有效性
     * @param roleIdList 角色id列表
     * @return boolean
     */
    boolean validateRoleId(List<RoleId> roleIdList);

    /**
     * 给用户分配角色权限
     * @param tenantId 租户id
     * @param userId 用户id
     * @param roleIdList 角色id列表
     */
    void associatedRole(TenantId tenantId, UserId userId, List<RoleId> roleIdList);

    /**
     * 创建租户的默认权限
     * @param tenantInfoEntity 租户信息
     */
    void createDefaultRole(TenantInfoEntity tenantInfoEntity);

    /**
     * 角色编码唯一性校验
     * @param tenantId 租户id
     * @param roleCode 角色编码
     * @return boolean
     */
    boolean uniqueRoleCode(TenantId tenantId, RoleCode roleCode);

    /**
     * 角色名唯一性校验
     * @param tenantId 租户id
     * @param roleName 角色名
     * @return boolean
     */
    boolean uniqueRoleName(TenantId tenantId, String roleName);

}
