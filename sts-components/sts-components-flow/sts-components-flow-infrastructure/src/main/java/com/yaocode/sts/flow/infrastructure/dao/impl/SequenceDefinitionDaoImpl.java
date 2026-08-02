package com.yaocode.sts.flow.infrastructure.dao.impl;

import com.yaocode.sts.flow.infrastructure.dao.SequenceDefinitionDao;
import com.yaocode.sts.flow.infrastructure.entity.SequenceDefinitionEntity;
import com.yaocode.sts.flow.infrastructure.mapper.SequenceDefinitionMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

/**
 * 连线定义 DAO 实现
 */
@Repository
public class SequenceDefinitionDaoImpl extends BaseFlowDaoImpl<SequenceDefinitionMapper, SequenceDefinitionEntity> implements SequenceDefinitionDao {

    @Resource
    private SequenceDefinitionMapper sequenceDefinitionMapper;

}
