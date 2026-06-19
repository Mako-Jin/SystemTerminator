package com.yaocode.sts.components.flow.infrastructure.dao.impl;

import com.yaocode.sts.components.flow.infrastructure.dao.HistActivityDao;
import com.yaocode.sts.components.flow.infrastructure.entity.HistActivityEntity;
import com.yaocode.sts.components.flow.infrastructure.mapper.HistActivityMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

/**
 * 历史活动 DAO 实现
 */
@Repository
public class HistActivityDaoImpl extends BaseFlowDaoImpl<HistActivityMapper, HistActivityEntity> implements HistActivityDao {

    @Resource
    private HistActivityMapper histActivityMapper;


}
