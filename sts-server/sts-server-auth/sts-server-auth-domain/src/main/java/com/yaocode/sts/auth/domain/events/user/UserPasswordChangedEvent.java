package com.yaocode.sts.auth.domain.events.user;

import com.yaocode.sts.auth.domain.aggregate.UserInfoAggregate;
import com.yaocode.sts.auth.domain.enums.AggregateTypeEnums;
import com.yaocode.sts.auth.domain.enums.EventTypeEnums;
import com.yaocode.sts.common.domain.event.AggregateDomainEvent;
import com.yaocode.sts.common.domain.valueobject.TenantId;
import com.yaocode.sts.common.domain.valueobject.UserId;
import lombok.Getter;

/**
 * 修改密码事件
 * @author: Jin-LiangBo
 * @date: 2025年10月12日 14:04
 */
@Getter
public class UserPasswordChangedEvent extends AggregateDomainEvent<UserId> {

    private final UserId userId;
    private final TenantId tenantId;
    private final boolean isInitial;

    public UserPasswordChangedEvent(UserInfoAggregate user, TenantId tenantId, boolean isInitial) {
        super(user, EventTypeEnums.USER_PASSWORD_CHANGED.getCode());
        this.userId = user.getId();
        this.tenantId = tenantId;
        this.isInitial = isInitial;
    }

    public UserPasswordChangedEvent(UserId userId, TenantId tenantId, boolean isInitial) {
        super(userId.getValue(), AggregateTypeEnums.USER.getCode(), EventTypeEnums.USER_PASSWORD_CHANGED.getCode());
        this.userId = userId;
        this.tenantId = tenantId;
        this.isInitial = isInitial;
    }
}
