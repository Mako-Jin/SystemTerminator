package com.yaocode.sts.components.flow.infrastructure.dao.impl;

import com.yaocode.sts.components.flow.infrastructure.dao.ExecutionDao;
import com.yaocode.sts.components.flow.infrastructure.entity.ExecutionEntity;
import com.yaocode.sts.components.flow.infrastructure.mapper.ExecutionMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

/**
 * 执行路径 DAO 实现
 */
@Repository
public class ExecutionDaoImpl extends BaseFlowDaoImpl<ExecutionMapper, ExecutionEntity> implements ExecutionDao {


    @Resource
    private ExecutionMapper executionMapper;


}
