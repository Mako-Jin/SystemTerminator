package com.yaocode.sts.components.flow.core.engine.externaltask;


import java.util.List;
import java.util.Map;

/**
 * 外部任务服务接口
 * 管理外部任务，支持任务拉取、完成、失败等操作
 *
 * @author Process Engine Team
 * @version 1.0.0
 */
public interface ExternalTaskService {

    // ========== 任务获取 ==========

    /**
     * 获取并锁定外部任务（拉取模式）
     */
    List<ExternalTask> fetchAndLock(String workerId, int maxTasks);

    /**
     * 根据主题获取并锁定任务
     */
    List<ExternalTask> fetchAndLock(String workerId, List<String> topicNames, int maxTasks);

    /**
     * 获取并锁定任务（带超时设置）
     */
    List<ExternalTask> fetchAndLock(String workerId, List<ExternalTaskTopic> topics,
                                    int maxTasks, long lockDuration);

    /**
     * 查询任务
     */
    ExternalTask getExternalTask(String taskId);

    /**
     * 查询待处理的外部任务
     */
    List<ExternalTask> getPendingExternalTasks();

    /**
     * 查询指定主题的待处理任务
     */
    List<ExternalTask> getPendingTasksByTopic(String topicName);

    // ========== 任务完成 ==========

    /**
     * 完成任务
     */
    void complete(String taskId, String workerId);

    /**
     * 完成任务并设置结果变量
     */
    void complete(String taskId, String workerId, Map<String, Object> variables);

    /**
     * 完成任务并设置局部变量
     */
    void complete(String taskId, String workerId, Map<String, Object> variables,
                  Map<String, Object> localVariables);

    // ========== 任务失败 ==========

    /**
     * 任务失败处理
     */
    void handleFailure(String taskId, String workerId,
                       String errorMessage, String errorDetails);

    /**
     * 任务失败处理（带重试配置）
     */
    void handleFailure(String taskId, String workerId, String errorMessage,
                       String errorDetails, int retries, long retryTimeout);

    /**
     * 任务失败处理（带业务错误）
     */
    void handleBpmnError(String taskId, String workerId, String errorCode,
                         String errorMessage);

    // ========== 任务扩展 ==========

    /**
     * 延长任务锁定时间
     */
    void extendLock(String taskId, String workerId, long newLockDuration);

    /**
     * 解锁任务（释放锁定）
     */
    void unlock(String taskId);

    /**
     * 报告任务进度
     */
    void reportProgress(String taskId, int progress, String message);

    // ========== 主题管理 ==========

    /**
     * 创建主题
     */
    ExternalTaskTopic createTopic(String topicName, String description);

    /**
     * 更新主题配置
     */
    void updateTopicConfiguration(String topicName, int maxRetries,
                                  long lockTimeout, long retryDelay);

    /**
     * 查询所有主题
     */
    List<ExternalTaskTopic> getAllTopics();

    /**
     * 删除主题
     */
    void deleteTopic(String topicName);
}