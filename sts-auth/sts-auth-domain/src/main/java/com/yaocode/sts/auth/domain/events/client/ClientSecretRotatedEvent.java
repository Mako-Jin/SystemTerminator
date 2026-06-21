package com.yaocode.sts.auth.domain.events.client;

import com.yaocode.sts.auth.domain.aggregate.ClientInfoAggregate;
import com.yaocode.sts.auth.domain.enums.AggregateTypeEnums;
import com.yaocode.sts.auth.domain.enums.EventTypeEnums;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.ClientId;
import com.yaocode.sts.common.domain.event.AggregateDomainEvent;
import lombok.Getter;

/**
 * 客户端密钥轮换事件
 */
@Getter
public class ClientSecretRotatedEvent extends AggregateDomainEvent<ClientId> {

    private final ClientId clientId;

    public ClientSecretRotatedEvent(ClientInfoAggregate client) {
        super(client, EventTypeEnums.CLIENT_SECRET_ROTATED.getCode());
        this.clientId = client.getId();
    }

    public ClientSecretRotatedEvent(ClientId clientId) {
        super(clientId.getValue(), AggregateTypeEnums.CLIENT.getCode(), EventTypeEnums.CLIENT_SECRET_ROTATED.getCode());
        this.clientId = clientId;
    }
}
