package com.yaocode.sts.auth.domain.events.client;

import com.yaocode.sts.auth.domain.aggregate.ClientInfoAggregate;
import com.yaocode.sts.auth.domain.enums.AggregateTypeEnums;
import com.yaocode.sts.auth.domain.enums.EventTypeEnums;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.ClientId;
import com.yaocode.sts.common.domain.event.AggregateDomainEvent;
import lombok.Getter;

/**
 * 客户端禁用事件
 */
@Getter
public class ClientDisabledEvent extends AggregateDomainEvent<ClientId> {

    private final ClientId clientId;

    public ClientDisabledEvent(ClientInfoAggregate client) {
        super(client, EventTypeEnums.CLIENT_DISABLED.getCode());
        this.clientId = client.getId();
    }

    public ClientDisabledEvent(ClientId clientId) {
        super(clientId.getValue(), AggregateTypeEnums.CLIENT.getCode(), EventTypeEnums.CLIENT_DISABLED.getCode());
        this.clientId = clientId;
    }
}
