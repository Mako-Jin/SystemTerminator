package com.yaocode.sts.components.flow.infrastructure.dao.impl;

import com.yaocode.sts.components.flow.infrastructure.dao.IdentityLinkDao;
import com.yaocode.sts.components.flow.infrastructure.entity.IdentityLinkEntity;
import com.yaocode.sts.components.flow.infrastructure.mapper.IdentityLinkMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

/**
 * 身份关联 DAO 实现
 */
@Repository
public class IdentityLinkDaoImpl extends BaseFlowDaoImpl<IdentityLinkMapper, IdentityLinkEntity> implements IdentityLinkDao {

    @Resource
    private IdentityLinkMapper identityLinkMapper;

}
