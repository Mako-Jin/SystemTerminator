package com.yaocode.sts.components.flow.infrastructure.dao.impl;

import com.yaocode.sts.components.flow.infrastructure.dao.HistExternalTaskLogDao;
import com.yaocode.sts.components.flow.infrastructure.entity.HistExternalTaskLogEntity;
import com.yaocode.sts.components.flow.infrastructure.mapper.HistExternalTaskLogMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

/**
 * 历史外部任务日志 DAO 实现
 */
@Repository
public class HistExternalTaskLogDaoImpl extends BaseFlowDaoImpl<HistExternalTaskLogMapper, HistExternalTaskLogEntity> implements HistExternalTaskLogDao {

    @Resource
    private HistExternalTaskLogMapper histExternalTaskLogMapper;

}
