package com.yaocode.sts.flow.infrastructure.dao.impl;

import com.yaocode.sts.flow.infrastructure.dao.VariableDao;
import com.yaocode.sts.flow.infrastructure.entity.VariableEntity;
import com.yaocode.sts.flow.infrastructure.mapper.VariableMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

/**
 * 运行时变量 DAO 实现
 */
@Repository
public class VariableDaoImpl extends BaseFlowDaoImpl<VariableMapper, VariableEntity> implements VariableDao {

    @Resource
    private VariableMapper variableMapper;

}
