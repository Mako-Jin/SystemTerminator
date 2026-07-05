package com.yaocode.sts.components.flow.core.repository;

import com.yaocode.sts.components.flow.core.model.Execution;

import java.util.List;

/**
 * 执行路径仓储接口
 */
public interface ExecutionRepository {

    void save(Execution execution);

    Execution findById(String executionId);

    List<Execution> findByProcessInstanceId(String processInstanceId);

    List<Execution> findActiveByProcessInstanceId(String processInstanceId);

    void updateNode(String executionId, String nodeKey);

    void delete(String executionId);
}
