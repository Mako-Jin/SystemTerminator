package com.yaocode.sts.components.flow.infrastructure.dao.impl;

import com.yaocode.sts.components.flow.infrastructure.dao.ProcessInstanceDao;
import com.yaocode.sts.components.flow.infrastructure.entity.ProcessInstanceEntity;
import com.yaocode.sts.components.flow.infrastructure.mapper.ProcessInstanceMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

/**
 * 流程实例 DAO 实现
 */
@Repository
public class ProcessInstanceDaoImpl extends BaseFlowDaoImpl<ProcessInstanceMapper, ProcessInstanceEntity> implements ProcessInstanceDao {

    @Resource
    private ProcessInstanceMapper processInstanceMapper;


}
