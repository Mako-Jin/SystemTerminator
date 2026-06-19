package com.yaocode.sts.components.flow.infrastructure.dao.impl;

import com.yaocode.sts.components.flow.infrastructure.dao.NodeDefinitionDao;
import com.yaocode.sts.components.flow.infrastructure.entity.NodeDefinitionEntity;
import com.yaocode.sts.components.flow.infrastructure.mapper.NodeDefinitionMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

/**
 * 节点定义 DAO 实现
 */
@Repository
public class NodeDefinitionDaoImpl extends BaseFlowDaoImpl<NodeDefinitionMapper, NodeDefinitionEntity> implements NodeDefinitionDao {

    @Resource
    private NodeDefinitionMapper nodeDefinitionMapper;

}
