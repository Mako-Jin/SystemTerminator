package com.yaocode.sts.auth.domain.events.user;

import com.yaocode.sts.auth.domain.aggregate.UserInfoAggregate;
import com.yaocode.sts.auth.domain.enums.AggregateTypeEnums;
import com.yaocode.sts.auth.domain.enums.EventTypeEnums;
import com.yaocode.sts.auth.domain.enums.RegisterSourceEnums;
import com.yaocode.sts.auth.domain.valueobjects.primitives.Username;
import com.yaocode.sts.common.domain.event.AggregateDomainEvent;
import com.yaocode.sts.common.domain.valueobject.UserId;
import lombok.Getter;

/**
 * 用户注册事件
 */
@Getter
public class UserRegisteredEvent extends AggregateDomainEvent<UserId> {

    private final UserId userId;
    private final Username username;
    private final RegisterSourceEnums registerSource;

    public UserRegisteredEvent(UserInfoAggregate user, Username username) {
        super(user, EventTypeEnums.USER_REGISTERED.getCode());
        this.userId = user.getId();
        this.username = username;
        this.registerSource = RegisterSourceEnums.REGISTER;
    }

    public UserRegisteredEvent(UserInfoAggregate user, Username username, RegisterSourceEnums registerSource) {
        super(user, EventTypeEnums.USER_REGISTERED.getCode());
        this.userId = user.getId();
        this.username = username;
        this.registerSource = registerSource;
    }

    public UserRegisteredEvent(UserId userId, Username username, RegisterSourceEnums registerSource) {
        super(userId.getValue(), AggregateTypeEnums.USER.getCode(), EventTypeEnums.USER_REGISTERED.getCode());
        this.userId = userId;
        this.username = username;
        this.registerSource = registerSource;
    }
}