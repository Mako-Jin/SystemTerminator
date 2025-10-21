package com.yaocode.sts.auth.domain.service;

import com.yaocode.sts.auth.domain.valueobjects.identifiers.RoleId;

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
     * @param roleIdList 角色id列表
     * @return boolean
     */
    boolean validateRoleId(List<RoleId> roleIdList);

}
