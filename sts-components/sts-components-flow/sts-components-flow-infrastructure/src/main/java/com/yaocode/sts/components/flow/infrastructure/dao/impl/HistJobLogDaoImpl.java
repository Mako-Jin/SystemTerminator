package com.yaocode.sts.components.flow.infrastructure.dao.impl;

import com.yaocode.sts.components.flow.infrastructure.dao.HistJobLogDao;
import com.yaocode.sts.components.flow.infrastructure.entity.HistJobLogEntity;
import com.yaocode.sts.components.flow.infrastructure.mapper.HistJobLogMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

/**
 * 历史作业日志 DAO 实现
 */
@Repository
public class HistJobLogDaoImpl extends BaseFlowDaoImpl<HistJobLogMapper, HistJobLogEntity> implements HistJobLogDao {

    @Resource
    private HistJobLogMapper histJobLogMapper;

}
