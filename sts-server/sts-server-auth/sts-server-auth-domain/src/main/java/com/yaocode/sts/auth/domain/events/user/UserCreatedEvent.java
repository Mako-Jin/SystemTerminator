package com.yaocode.sts.auth.domain.events.user;

import com.yaocode.sts.auth.domain.valueobjects.identifiers.OrganizationId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.RoleId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.UserGroupId;
import com.yaocode.sts.common.domain.event.AggregateDomainEvent;
import com.yaocode.sts.common.domain.model.AbstractAggregate;
import com.yaocode.sts.common.domain.valueobject.TenantId;
import com.yaocode.sts.common.domain.valueobject.UserId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 创建用户事件
 * @author: Jin-LiangBo
 * @date: 2025年10月12日 14:03
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class UserCreatedEvent extends AggregateDomainEvent<UserId> {

    private final UserId userId;
    private final TenantId tenantId;
    private final OrganizationId organizationId;
    private final UserGroupId userGroupId;
    private final List<RoleId> roleIds;
    private final LocalDateTime createTime;
    private final String username;
    private final String email;

    public UserCreatedEvent(
            AbstractAggregate<UserId> aggregate, UserId userId, TenantId tenantId,
            OrganizationId organizationId, UserGroupId userGroupId, List<RoleId> roleIds,
            LocalDateTime createTime, String username, String email
    ) {
        super(aggregate);
        this.userId = userId;
        this.tenantId = tenantId;
        this.organizationId = organizationId;
        this.userGroupId = userGroupId;
        this.roleIds = roleIds;
        this.createTime = createTime;
        this.username = username;
        this.email = email;
    }

    public UserCreatedEvent(
            AbstractAggregate<UserId> aggregate, String eventType, UserId userId,
            TenantId tenantId, OrganizationId organizationId, UserGroupId userGroupId,
            List<RoleId> roleIds, LocalDateTime createTime, String username, String email
    ) {
        super(aggregate, eventType);
        this.userId = userId;
        this.tenantId = tenantId;
        this.organizationId = organizationId;
        this.userGroupId = userGroupId;
        this.roleIds = roleIds;
        this.createTime = createTime;
        this.username = username;
        this.email = email;
    }

    public UserCreatedEvent(
            String aggregateId, String aggregateType, UserId userId, TenantId tenantId,
            OrganizationId organizationId, UserGroupId userGroupId, List<RoleId> roleIds,
            LocalDateTime createTime, String username, String email
    ) {
        super(aggregateId, aggregateType);
        this.userId = userId;
        this.tenantId = tenantId;
        this.organizationId = organizationId;
        this.userGroupId = userGroupId;
        this.roleIds = roleIds;
        this.createTime = createTime;
        this.username = username;
        this.email = email;
    }

    public UserCreatedEvent(
            String aggregateId, String aggregateType, String eventType, UserId userId,
            TenantId tenantId, OrganizationId organizationId, UserGroupId userGroupId,
            List<RoleId> roleIds, LocalDateTime createTime, String username, String email
    ) {
        super(aggregateId, aggregateType, eventType);
        this.userId = userId;
        this.tenantId = tenantId;
        this.organizationId = organizationId;
        this.userGroupId = userGroupId;
        this.roleIds = roleIds;
        this.createTime = createTime;
        this.username = username;
        this.email = email;
    }
}
