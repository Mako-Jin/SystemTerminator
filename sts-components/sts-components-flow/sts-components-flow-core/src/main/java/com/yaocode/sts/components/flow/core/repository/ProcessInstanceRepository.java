package com.yaocode.sts.components.flow.core.repository;

import com.yaocode.sts.components.flow.core.model.ProcessInstance;

import java.util.List;

/**
 * 流程实例仓储接口
 */
public interface ProcessInstanceRepository {

    ProcessInstance findById(String processInstanceId);

    List<ProcessInstance> findByBusinessKey(String businessKey);

    List<ProcessInstance> findByProcessKey(String processKey);

    void save(ProcessInstance instance);

    void updateStatus(ProcessInstance instance);

    void delete(String processInstanceId);
}
