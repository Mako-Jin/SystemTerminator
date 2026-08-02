package com.yaocode.sts.flow.infrastructure.dao.impl;

import com.yaocode.sts.flow.infrastructure.dao.JobDefinitionDao;
import com.yaocode.sts.flow.infrastructure.entity.JobDefinitionEntity;
import com.yaocode.sts.flow.infrastructure.mapper.JobDefinitionMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

/**
 * 作业定义 DAO 实现
 */
@Repository
public class JobDefinitionDaoImpl extends BaseFlowDaoImpl<JobDefinitionMapper, JobDefinitionEntity> implements JobDefinitionDao {

    @Resource
    private JobDefinitionMapper jobDefinitionMapper;


}
