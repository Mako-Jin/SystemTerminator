package com.yaocode.sts.components.flow.core.repository;

import com.yaocode.sts.components.flow.core.model.ProcessDefinition;

import java.util.List;

/**
 * 流程定义仓储接口（Core 定义，Infrastructure 实现）
 */
public interface ProcessDefinitionRepository {

    ProcessDefinition findById(String processId);

    ProcessDefinition findByKey(String processKey);

    ProcessDefinition findLatestByKey(String processKey);

    ProcessDefinition findByKeyAndVersion(String processKey, Integer version);

    List<ProcessDefinition> findAllActive();

    void save(ProcessDefinition definition);
}

