package com.yaocode.sts.auth.domain.entity;

import com.yaocode.sts.auth.domain.service.RoleDomainService;
import com.yaocode.sts.auth.domain.service.TenantDomainService;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.RoleId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.TenantId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.RoleCode;
import com.yaocode.sts.common.domain.model.AbstractAggregate;
import lombok.Getter;

import java.util.Objects;

/**
 * 角色领域对象
 * @author: Jin-LiangBo
 * @date: 2025年10月12日 13:49
 */
@Getter
public class RoleInfoEntity extends AbstractAggregate<RoleId> {

    /**
     * 租户id
     */
    private TenantId tenantId;
    /**
     * 角色编码
     */
    private RoleCode roleCode;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 角色描述
     */
    private String roleDesc;
    /**
     * 是否默认角色
     */
    private Integer isDefault;
    /**
     * 父角色id
     */
    private RoleId parentId;

    private RoleInfoEntity(RoleId roleId) {
        super(roleId);
    }

    public static RoleInfoEntity build (
            TenantDomainService tenantDomainService,
            RoleDomainService roleDomainService,
            TenantId tenantId,
            RoleCode roleCode,
            String roleName,
            String roleDesc,
            Integer isDefault,
            RoleId roleParentId
    ) {
        if (!tenantDomainService.validateTenantId(tenantId)) {
            throw new IllegalArgumentException("租户不存在");
        }
        if (!roleDomainService.uniqueRoleCode(tenantId, roleCode)) {
            throw new IllegalArgumentException("角色编码已存在");
        }
        if (!roleDomainService.uniqueRoleName(tenantId, roleName)) {
            throw new IllegalArgumentException("角色名称已存在");
        }
        if (Objects.nonNull(roleParentId) && !roleDomainService.uniqueRoleCode(tenantId, roleCode)) {
            throw new IllegalArgumentException("角色父编码不存在");
        }
        RoleInfoEntity roleInfoEntity = new RoleInfoEntity(RoleId.nextId());
        roleInfoEntity.roleCode = roleCode;
        roleInfoEntity.tenantId = tenantId;
        roleInfoEntity.roleName = roleName;
        roleInfoEntity.roleDesc = roleDesc != null ? roleDesc.trim() : "";
        roleInfoEntity.isDefault = isDefault;
        roleInfoEntity.parentId = roleParentId;
        return roleInfoEntity;
    }

    public static RoleInfoEntity build (
            RoleId roleId,
            TenantId tenantId,
            RoleCode roleCode,
            String roleName,
            String roleDesc,
            Integer isDefault,
            RoleId roleParentId
    ) {
        RoleInfoEntity roleInfoEntity = new RoleInfoEntity(roleId);
        roleInfoEntity.roleCode = roleCode;
        roleInfoEntity.tenantId = tenantId;
        roleInfoEntity.roleName = roleName;
        roleInfoEntity.roleDesc = roleDesc;
        roleInfoEntity.isDefault = isDefault;
        roleInfoEntity.parentId = roleParentId;
        return roleInfoEntity;
    }

    // private Set<Permission> permissions; // 值对象集合

    // public void assignPermission(Permission permission) {
    //     // 业务规则验证
    //     if (permissions.contains(permission)) {
    //         throw new BusinessException("权限已存在");
    //     }
    //
    //     permissions.add(permission);
    //     registerEvent(new PermissionAssignedEvent(this.id, permission));
    // }
    //
    // public void revokePermission(Permission permission) {
    //     boolean removed = permissions.remove(permission);
    //     if (removed) {
    //         registerEvent(new PermissionRevokedEvent(this.id, permission));
    //     }
    // }
    //
    // public boolean hasPermission(String resource, String action) {
    //     return permissions.stream()
    //             .anyMatch(p -> p.matches(resource, action));
    // }

}
