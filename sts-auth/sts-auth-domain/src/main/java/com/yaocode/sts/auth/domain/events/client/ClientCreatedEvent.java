package com.yaocode.sts.auth.domain.events.client;

import com.yaocode.sts.auth.domain.aggregate.ClientInfoAggregate;
import com.yaocode.sts.auth.domain.enums.AggregateTypeEnums;
import com.yaocode.sts.auth.domain.enums.EventTypeEnums;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.ClientId;
import com.yaocode.sts.common.domain.event.AggregateDomainEvent;
import lombok.Getter;

/**
 * 客户端创建事件
 */
@Getter
public class ClientCreatedEvent extends AggregateDomainEvent<ClientId> {

    private final ClientId clientId;
    private final String clientName;

    public ClientCreatedEvent(ClientInfoAggregate client) {
        super(client, EventTypeEnums.CLIENT_CREATED.getCode());
        this.clientId = client.getId();
        this.clientName = client.getClientName();
    }

    public ClientCreatedEvent(ClientId clientId, String clientName) {
        super(clientId.getValue(), AggregateTypeEnums.CLIENT.getCode(), EventTypeEnums.CLIENT_CREATED.getCode());
        this.clientId = clientId;
        this.clientName = clientName;
    }
}