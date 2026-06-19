package com.yaocode.sts.components.flow.infrastructure.dao.impl;

import com.yaocode.sts.components.flow.infrastructure.dao.HistIncidentDao;
import com.yaocode.sts.components.flow.infrastructure.entity.HistIncidentEntity;
import com.yaocode.sts.components.flow.infrastructure.mapper.HistIncidentMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

/**
 * 历史异常 DAO 实现
 */
@Repository
public class HistIncidentDaoImpl extends BaseFlowDaoImpl<HistIncidentMapper, HistIncidentEntity> implements HistIncidentDao {

    @Resource
    private HistIncidentMapper histIncidentMapper;

}
