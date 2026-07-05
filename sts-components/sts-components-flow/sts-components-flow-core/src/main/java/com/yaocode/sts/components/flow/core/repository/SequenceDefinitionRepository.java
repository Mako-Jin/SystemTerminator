package com.yaocode.sts.components.flow.core.repository;

import com.yaocode.sts.components.flow.core.model.SequenceDefinition;

import java.util.List;

/**
 * 连线定义仓储接口
 */
public interface SequenceDefinitionRepository {

    List<SequenceDefinition> findByProcessId(String processId);

    List<SequenceDefinition> findOutgoingBySource(String processId, String sourceNodeKey);

    List<SequenceDefinition> findIncomingByTarget(String processId, String targetNodeKey);

    SequenceDefinition findByProcessIdAndSequenceKey(String processId, String sequenceKey);
}

