package com.yaocode.sts.components.flow.core.engine.task;

import java.util.List;
import java.util.Map;

/**
 * 任务服务接口
 * 管理人工任务的生命周期
 *
 * @author Process Engine Team
 * @version 1.0.0
 */
public interface TaskService {

    // ========== 任务查询 ==========

    /**
     * 根据任务ID查询
     */
    Task getTask(String taskId);

    /**
     * 查询待办任务（根据用户ID）
     */
    List<Task> getTasksByAssignee(String assignee);

    /**
     * 查询候选任务（用户或组）
     */
    List<Task> getTasksByCandidate(String userId);

    /**
     * 查询待认领任务（未分配）
     */
    List<Task> getUnassignedTasks();

    /**
     * 查询用户相关的所有任务（待办 + 候选）
     */
    List<Task> getTasksByUser(String userId);

    /**
     * 根据流程实例ID查询任务
     */
    List<Task> getTasksByProcessInstanceId(String processInstanceId);

    // ========== 任务操作 ==========

    /**
     * 认领任务
     */
    void claim(String taskId, String userId);

    /**
     * 取消认领
     */
    void unclaim(String taskId);

    /**
     * 完成任务
     */
    void complete(String taskId);

    /**
     * 完成任务并设置变量
     */
    void complete(String taskId, Map<String, Object> variables);

    /**
     * 委派任务（给其他人处理）
     */
    void delegateTask(String taskId, String delegateUserId);

    /**
     * 解决任务（委派后提交结果）
     */
    void resolveTask(String taskId);

    /**
     * 转交任务
     */
    void assignTask(String taskId, String assignee);

    /**
     * 挂起任务
     */
    void suspendTask(String taskId);

    /**
     * 激活任务
     */
    void activateTask(String taskId);

    // ========== 任务变量 ==========

    /**
     * 设置任务局部变量
     */
    void setTaskVariable(String taskId, String variableName, Object value);

    /**
     * 获取任务局部变量
     */
    Object getTaskVariable(String taskId, String variableName);

    /**
     * 获取任务所有局部变量
     */
    Map<String, Object> getTaskVariables(String taskId);

    // ========== 任务评论 ==========

    /**
     * 添加评论
     */
    Comment addComment(String taskId, String processInstanceId, String message);

    /**
     * 获取任务评论
     */
    List<Comment> getTaskComments(String taskId);

    /**
     * 删除评论
     */
    void deleteComment(String commentId);

    // ========== 任务附件 ==========

    /**
     * 添加附件
     */
    Attachment createAttachment(String taskId, String processInstanceId,
                                String attachmentName, String description,
                                byte[] content);

    /**
     * 获取任务附件
     */
    List<Attachment> getTaskAttachments(String taskId);

    // ========== 任务事件 ==========

    /**
     * 添加任务事件（记录操作日志）
     */
    void addTaskEvent(String taskId, String eventType, String message);

    /**
     * 获取任务事件
     */
    List<TaskEvent> getTaskEvents(String taskId);
}
