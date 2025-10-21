package com.yaocode.sts.auth.domain.entity;

import com.yaocode.sts.auth.domain.valueobjects.identifiers.RoleId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.TenantId;
import com.yaocode.sts.common.domain.model.AbstractAggregate;
import lombok.Getter;
import lombok.Setter;

/**
 * 角色领域对象
 * @author: Jin-LiangBo
 * @date: 2025年10月12日 13:49
 */
@Getter
@Setter
public class RoleInfoEntity extends AbstractAggregate<RoleId> {

    private TenantId tenantId;
    private String name;
    private String code;

    protected RoleInfoEntity(RoleId roleId) {
        super(roleId);
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
