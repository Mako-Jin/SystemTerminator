package com.yaocode.sts.components.flow.core.repository;

import com.yaocode.sts.components.flow.core.model.NodeDefinition;

import java.util.List;

/**
 * 节点定义仓储接口
 */
public interface NodeDefinitionRepository {

    List<NodeDefinition> findByProcessId(String processId);

    NodeDefinition findByProcessIdAndNodeKey(String processId, String nodeKey);

    NodeDefinition findStartNode(String processId);

    List<NodeDefinition> findByType(String processId, int nodeType);
}
