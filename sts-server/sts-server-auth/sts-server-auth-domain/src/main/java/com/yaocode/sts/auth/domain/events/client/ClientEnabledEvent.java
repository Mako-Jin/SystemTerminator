package com.yaocode.sts.auth.domain.events.client;

import com.yaocode.sts.auth.domain.aggregate.ClientInfoAggregate;
import com.yaocode.sts.auth.domain.enums.AggregateTypeEnums;
import com.yaocode.sts.auth.domain.enums.EventTypeEnums;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.ClientId;
import com.yaocode.sts.common.domain.event.AggregateDomainEvent;
import lombok.Getter;

/**
 * 客户端启用事件
 */
@Getter
public class ClientEnabledEvent extends AggregateDomainEvent<ClientId> {

    private final ClientId clientId;

    public ClientEnabledEvent(ClientInfoAggregate client) {
        super(client, EventTypeEnums.CLIENT_ENABLED.getCode());
        this.clientId = client.getId();
    }

    public ClientEnabledEvent(ClientId clientId) {
        super(clientId.getValue(), AggregateTypeEnums.CLIENT.getCode(), EventTypeEnums.CLIENT_ENABLED.getCode());
        this.clientId = clientId;
    }
}
