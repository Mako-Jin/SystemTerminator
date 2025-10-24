package com.yaocode.sts.auth.domain.repository;

import com.yaocode.sts.auth.domain.entity.RoleInfoEntity;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.RoleId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.TenantId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.UserId;
import com.yaocode.sts.common.domain.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 角色信息仓库接口
 * @author: Jin-LiangBo
 * @date: 2025年10月13日 22:55
 */
public interface RoleInfoRepository extends Repository<RoleInfoEntity, RoleId> {

    /**
     * 通过角色id列表查询角色信息
     * @param tenantId 租户id
     * @param roleIdList 角色id列表
     * @return List<RoleInfoEntity>
     */
    List<RoleInfoEntity> findByIdList(TenantId tenantId, List<RoleId> roleIdList);

    /**
     * 保存用户角色关联关系
     * @param tenantId 租户id
     * @param userId 用户id
     * @param roleIdList 角色id列表
     */
    void saveRelRoleUser(TenantId tenantId, UserId userId, List<RoleId> roleIdList);

    /**
     * 获取租户下的默认权限id
     * @param tenantId 租户id
     * @return RoleId
     */
    Optional<RoleId> getDefaultRole(TenantId tenantId);

}
