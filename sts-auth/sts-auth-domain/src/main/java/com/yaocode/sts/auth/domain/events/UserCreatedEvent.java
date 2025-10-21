package com.yaocode.sts.auth.domain.events;

import com.yaocode.sts.auth.domain.valueobjects.identifiers.OrganizationId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.RoleId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.TenantId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.UserGroupId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.UserId;
import com.yaocode.sts.common.domain.model.AbstractDomainEvent;
import lombok.Builder;
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
@Builder
@EqualsAndHashCode(callSuper = true)
public class UserCreatedEvent extends AbstractDomainEvent {

    private final UserId userId;
    private final TenantId tenantId;
    private final OrganizationId organizationId;
    private final UserGroupId userGroupId;
    private final List<RoleId> roleIds;
    private final LocalDateTime createTime;
    private final String username;
    private final String email;

}
