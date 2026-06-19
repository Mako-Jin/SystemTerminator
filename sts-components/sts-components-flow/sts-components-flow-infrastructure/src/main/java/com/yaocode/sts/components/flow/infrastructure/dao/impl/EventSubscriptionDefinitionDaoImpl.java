package com.yaocode.sts.components.flow.infrastructure.dao.impl;

import com.yaocode.sts.components.flow.infrastructure.dao.EventSubscriptionDefinitionDao;
import com.yaocode.sts.components.flow.infrastructure.entity.EventSubscriptionDefinitionEntity;
import com.yaocode.sts.components.flow.infrastructure.mapper.EventSubscriptionDefinitionMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

/**
 * 事件订阅定义 DAO 实现
 */
@Repository
public class EventSubscriptionDefinitionDaoImpl extends BaseFlowDaoImpl<EventSubscriptionDefinitionMapper, EventSubscriptionDefinitionEntity> implements EventSubscriptionDefinitionDao {

    @Resource
    private EventSubscriptionDefinitionMapper eventSubscriptionDefinitionMapper;

}

