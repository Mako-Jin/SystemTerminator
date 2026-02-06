package com.yaocode.sts.auth.domain.entity;

import com.yaocode.sts.auth.domain.service.TenantDomainService;
import com.yaocode.sts.auth.domain.service.UserGroupDomainService;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.RoleId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.TenantId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.UserGroupId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.UserId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.UserGroupCode;
import com.yaocode.sts.common.domain.model.AbstractAggregate;
import com.yaocode.sts.common.tools.id.IdFactory;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.util.Set;

/**
 * 用户组领域对象
 * @author: Jin-LiangBo
 * @date: 2025年10月13日 22:57
 */
@Getter
public class UserGroupEntity extends AbstractAggregate<UserGroupId> {

    public UserGroupEntity(UserGroupId userGroupId) {
        super(userGroupId);
    }

    /**
     * 用户组编码
     */
    private UserGroupCode userGroupCode;

    /**
     * 用户组名
     */
    private String userGroupName;
    /**
     * 用户组描述
     */
    private String userGroupDesc;
    /**
     * 父id
     */
    private UserGroupId parentId;

    /**
     * 租户id
     */
    private TenantId tenantId;

    /**
     * 用户id列表
     */
    private Set<RoleId> assignedRoles;

    /**
     * 用户id列表
     */
    private Set<UserId> assignedUsers;

    public static UserGroupEntity build (
            TenantDomainService tenantDomainService,
            UserGroupDomainService userGroupDomainService,
            TenantId tenantId,
            UserGroupCode userGroupCode,
            String userGroupName,
            String desc,
            String parentId
    ) {
        if (!tenantDomainService.validateTenantId(tenantId)) {
            throw new IllegalArgumentException("租户不存在");
        }
        if (userGroupDomainService.uniqueUserGroupCode(tenantId, userGroupCode)) {
            throw new IllegalArgumentException("角色编码已存在");
        }
        if (userGroupDomainService.uniqueUserGroupName(tenantId, userGroupName)) {
            throw new IllegalArgumentException("角色名称已存在");
        }
        UserGroupId parentUserGroupId = null;
        if (StringUtils.hasText(parentId)) {
            parentUserGroupId = UserGroupId.of(parentId);
            if (!userGroupDomainService.validateUserGroupId(tenantId, parentUserGroupId)) {
                throw new IllegalArgumentException("父角色不存在");
            }
        }
        UserGroupEntity entity = new UserGroupEntity(UserGroupId.of(IdFactory.generate().toString()));
        entity.tenantId = tenantId;
        entity.userGroupCode = userGroupCode;
        entity.userGroupName = userGroupName;
        entity.userGroupDesc = desc != null ? desc.trim() : "";
        entity.parentId = parentUserGroupId;
        // 6. 发布领域事件
        // entity.registerEvent(new UserGroupCreatedEvent(
        //         entity.getId(),
        //         tenantId,
        //         entity.getUserGroupName()
        // ));
        return entity;
    }

}
