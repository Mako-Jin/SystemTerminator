package com.yaocode.sts.components.flow.infrastructure.dao.impl;

import com.yaocode.sts.components.flow.infrastructure.dao.HistProcessInstanceDao;
import com.yaocode.sts.components.flow.infrastructure.entity.HistProcessInstanceEntity;
import com.yaocode.sts.components.flow.infrastructure.mapper.HistProcessInstanceMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

/**
 * 历史流程实例 DAO 实现
 */
@Repository
public class HistProcessInstanceDaoImpl extends BaseFlowDaoImpl<HistProcessInstanceMapper, HistProcessInstanceEntity> implements HistProcessInstanceDao {

    @Resource
    private HistProcessInstanceMapper histProcessInstanceMapper;

}
