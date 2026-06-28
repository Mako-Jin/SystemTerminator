package com.yaocode.sts.auth.domain.entity;

import com.yaocode.sts.auth.domain.valueobjects.identifiers.ClientId;
import com.yaocode.sts.common.domain.model.AbstractAggregate;

public class ClientInfoEntity extends AbstractAggregate<ClientId> {
    protected ClientInfoEntity(ClientId clientId) {
        super(clientId);
    }
}
