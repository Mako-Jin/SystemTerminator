package com.yaocode.sts.auth.domain.events.client;

import com.yaocode.sts.auth.domain.aggregate.ClientInfoAggregate;
import com.yaocode.sts.auth.domain.enums.AggregateTypeEnums;
import com.yaocode.sts.auth.domain.enums.EventTypeEnums;
import com.yaocode.sts.auth.domain.enums.GrantTypeEnums;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.ClientId;
import com.yaocode.sts.common.domain.event.AggregateDomainEvent;
import lombok.Getter;

/**
 * 授权类型移除事件
 */
@Getter
public class GrantTypeRemovedEvent extends AggregateDomainEvent<ClientId> {

    private final ClientId clientId;
    private final GrantTypeEnums grantType;

    public GrantTypeRemovedEvent(ClientInfoAggregate client, GrantTypeEnums grantType) {
        super(client, EventTypeEnums.GRANT_TYPE_REMOVED.getCode());
        this.clientId = client.getId();
        this.grantType = grantType;
    }

    public GrantTypeRemovedEvent(ClientId clientId, GrantTypeEnums grantType) {
        super(clientId.getValue(), AggregateTypeEnums.CLIENT.getCode(), EventTypeEnums.GRANT_TYPE_REMOVED.getCode());
        this.clientId = clientId;
        this.grantType = grantType;
    }
}
