package com.yaocode.sts.components.flow.infrastructure.dao.impl;

import com.yaocode.sts.components.flow.infrastructure.dao.HistIdentityLinkDao;
import com.yaocode.sts.components.flow.infrastructure.entity.HistIdentityLinkEntity;
import com.yaocode.sts.components.flow.infrastructure.mapper.HistIdentityLinkMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

/**
 * 历史身份关联 DAO 实现
 */
@Repository
public class HistIdentityLinkDaoImpl extends BaseFlowDaoImpl<HistIdentityLinkMapper, HistIdentityLinkEntity> implements HistIdentityLinkDao {

    @Resource
    private HistIdentityLinkMapper histIdentityLinkMapper;

}
