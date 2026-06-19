package com.yaocode.sts.components.flow.infrastructure.dao.impl;

import com.yaocode.sts.components.flow.infrastructure.dao.ExternalTaskDao;
import com.yaocode.sts.components.flow.infrastructure.entity.ExternalTaskEntity;
import com.yaocode.sts.components.flow.infrastructure.mapper.ExternalTaskMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

/**
 * 外部任务 DAO 实现
 */
@Repository
public class ExternalTaskDaoImpl extends BaseFlowDaoImpl<ExternalTaskMapper, ExternalTaskEntity> implements ExternalTaskDao {

    @Resource
    private ExternalTaskMapper externalTaskMapper;

}
