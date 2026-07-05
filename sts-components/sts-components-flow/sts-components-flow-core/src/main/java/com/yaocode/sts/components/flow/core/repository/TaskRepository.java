package com.yaocode.sts.components.flow.core.repository;

import com.yaocode.sts.components.flow.core.model.Task;

import java.util.List;

/**
 * 任务仓储接口
 */
public interface TaskRepository {

    void save(Task task);

    Task findById(String taskId);

    List<Task> findByProcessInstanceId(String processInstanceId);

    List<Task> findByAssignee(String assignee);

    void updateStatus(String taskId, int status);

    void claim(String taskId, String assignee);

    void delete(String taskId);
}
