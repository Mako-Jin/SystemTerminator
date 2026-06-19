package com.yaocode.sts.components.flow.infrastructure.dao.impl;

import com.yaocode.sts.components.flow.infrastructure.dao.BoundaryEventDefinitionDao;
import com.yaocode.sts.components.flow.infrastructure.entity.BoundaryEventDefinitionEntity;
import com.yaocode.sts.components.flow.infrastructure.mapper.BoundaryEventDefinitionMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

/**
 * 边界事件定义 DAO 实现
 */
@Repository
public class BoundaryEventDefinitionDaoImpl extends BaseFlowDaoImpl<BoundaryEventDefinitionMapper, BoundaryEventDefinitionEntity> implements BoundaryEventDefinitionDao {

    @Resource
    private BoundaryEventDefinitionMapper boundaryEventDefinitionMapper;

}
