package com.yaocode.sts.flow.infrastructure.dao.impl;

import com.yaocode.sts.flow.infrastructure.dao.RuntimeEventSubscriptionDao;
import com.yaocode.sts.flow.infrastructure.entity.RuntimeEventSubscriptionEntity;
import com.yaocode.sts.flow.infrastructure.mapper.RuntimeEventSubscriptionMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

/**
 * 运行时事件订阅 DAO 实现
 */
@Repository
public class RuntimeEventSubscriptionDaoImpl extends BaseFlowDaoImpl<RuntimeEventSubscriptionMapper, RuntimeEventSubscriptionEntity> implements RuntimeEventSubscriptionDao {

    @Resource
    private RuntimeEventSubscriptionMapper runtimeEventSubscriptionMapper;

}
