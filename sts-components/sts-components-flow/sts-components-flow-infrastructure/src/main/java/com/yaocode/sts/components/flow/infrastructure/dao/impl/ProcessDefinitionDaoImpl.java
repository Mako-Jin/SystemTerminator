package com.yaocode.sts.components.flow.infrastructure.dao.impl;

import com.yaocode.sts.components.flow.infrastructure.dao.ProcessDefinitionDao;
import com.yaocode.sts.components.flow.infrastructure.entity.ProcessDefinitionEntity;
import com.yaocode.sts.components.flow.infrastructure.mapper.ProcessDefinitionMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

/**
 * 流程定义 DAO 实现
 */
@Repository
public class ProcessDefinitionDaoImpl extends BaseFlowDaoImpl<ProcessDefinitionMapper, ProcessDefinitionEntity> implements ProcessDefinitionDao {

    @Resource
    private ProcessDefinitionMapper processDefinitionMapper;


}
