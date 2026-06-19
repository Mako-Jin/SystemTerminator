package com.yaocode.sts.components.flow.infrastructure.dao.impl;

import com.yaocode.sts.components.flow.infrastructure.dao.HistTaskDao;
import com.yaocode.sts.components.flow.infrastructure.entity.HistTaskEntity;
import com.yaocode.sts.components.flow.infrastructure.mapper.HistTaskMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

/**
 * 历史任务 DAO 实现
 */
@Repository
public class HistTaskDaoImpl extends BaseFlowDaoImpl<HistTaskMapper, HistTaskEntity> implements HistTaskDao {

    @Resource
    private HistTaskMapper histTaskMapper;

}
