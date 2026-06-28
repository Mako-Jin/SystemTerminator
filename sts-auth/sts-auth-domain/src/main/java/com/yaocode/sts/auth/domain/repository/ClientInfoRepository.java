package com.yaocode.sts.auth.domain.repository;

import com.yaocode.sts.auth.domain.entity.ClientInfoEntity;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.ClientId;
import com.yaocode.sts.common.domain.Repository;

public interface ClientInfoRepository extends Repository<ClientInfoEntity, ClientId> {
}
