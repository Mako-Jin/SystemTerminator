package com.yaocode.sts.flow.infrastructure.dao.impl;

import com.yaocode.sts.flow.infrastructure.dao.HistVariableDao;
import com.yaocode.sts.flow.infrastructure.entity.HistVariableEntity;
import com.yaocode.sts.flow.infrastructure.mapper.HistVariableMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

/**
 * 历史变量 DAO 实现
 */
@Repository
public class HistVariableDaoImpl extends BaseFlowDaoImpl<HistVariableMapper, HistVariableEntity> implements HistVariableDao {

    @Resource
    private HistVariableMapper histVariableMapper;

}
