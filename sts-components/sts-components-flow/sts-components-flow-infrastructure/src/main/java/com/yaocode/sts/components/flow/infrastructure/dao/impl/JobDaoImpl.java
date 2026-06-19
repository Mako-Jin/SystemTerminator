package com.yaocode.sts.components.flow.infrastructure.dao.impl;

import com.yaocode.sts.components.flow.infrastructure.dao.JobDao;
import com.yaocode.sts.components.flow.infrastructure.entity.JobEntity;
import com.yaocode.sts.components.flow.infrastructure.mapper.JobMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

/**
 * 异步作业 DAO 实现
 */
@Repository
public class JobDaoImpl extends BaseFlowDaoImpl<JobMapper, JobEntity> implements JobDao {

    @Resource
    private JobMapper jobMapper;

}
