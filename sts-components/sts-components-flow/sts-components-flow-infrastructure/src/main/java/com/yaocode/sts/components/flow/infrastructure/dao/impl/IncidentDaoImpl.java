package com.yaocode.sts.components.flow.infrastructure.dao.impl;

import com.yaocode.sts.components.flow.infrastructure.dao.IncidentDao;
import com.yaocode.sts.components.flow.infrastructure.entity.IncidentEntity;
import com.yaocode.sts.components.flow.infrastructure.mapper.IncidentMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

/**
 * 运行时异常 DAO 实现
 */
@Repository
public class IncidentDaoImpl extends BaseFlowDaoImpl<IncidentMapper, IncidentEntity> implements IncidentDao {

    @Resource
    private IncidentMapper incidentMapper;

}
