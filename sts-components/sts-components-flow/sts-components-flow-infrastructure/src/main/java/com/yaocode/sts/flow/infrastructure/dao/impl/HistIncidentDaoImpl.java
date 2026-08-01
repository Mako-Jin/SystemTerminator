package com.yaocode.sts.flow.infrastructure.dao.impl;

import com.yaocode.sts.flow.infrastructure.dao.HistIncidentDao;
import com.yaocode.sts.flow.infrastructure.entity.HistIncidentEntity;
import com.yaocode.sts.flow.infrastructure.mapper.HistIncidentMapper;
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
