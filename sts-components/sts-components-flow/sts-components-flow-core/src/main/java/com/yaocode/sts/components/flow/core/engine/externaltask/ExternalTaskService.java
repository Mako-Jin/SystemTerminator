package com.yaocode.sts.components.flow.core.engine.externaltask;

import java.util.List;
import java.util.Map;

/**
 * 外部任务服务接口
 * <p>
 * 管理外部任务，支持任务拉取、完成、失败等操作，包括：
 * <ul>
 *   <li>任务的获取与锁定（拉取模式）</li>
 *   <li>任务的完成与变量提交</li>
 *   <li>任务的失败处理与重试管理</li>
 *   <li>BPMN错误处理</li>
 *   <li>锁定时间延长与解锁</li>
 *   <li>任务进度报告</li>
 *   <li>任务重试次数的设置（同步/异步）</li>
 *   <li>任务优先级的设置</li>
 *   <li>主题名称查询</li>
 * </ul>
 * <p>
 * 使用示例：
 * <pre>
 * // 获取并锁定任务
 * List&lt;ExternalTask&gt; tasks = externalTaskService
 *     .fetchAndLock(10, "worker1")
 *     .topic("orderProcessing", 60000L)
 *     .execute();
 *
 * // 完成任务
 * externalTaskService.complete(task.getId(), "worker1", variables);
 *
 * // 处理失败
 * externalTaskService.handleFailure(task.getId(), "worker1",
 *     "连接超时", "详细错误信息", 3, 5000L);
 * </pre>
 *
 * @author Process Engine Team
 * @version 1.0.0
 */
public interface ExternalTaskService {

    // ========================================================================
    // 一、任务获取与锁定
    // ========================================================================

    /**
     * 获取并锁定外部任务（拉取模式，不启用优先级）
     *
     * @param maxTasks 最大返回任务数
     * @param workerId 工作者ID
     * @return 外部任务查询构建器
     */
    ExternalTaskQueryBuilder fetchAndLock(int maxTasks, String workerId);

    /**
     * 获取并锁定外部任务（拉取模式，可启用优先级）
     * <p>
     * 返回的任务将被锁定给指定的工作者，直到锁定时间过期。
     * 锁定期间其他工作者无法获取或完成该任务
     *
     * @param maxTasks    最大返回任务数
     * @param workerId    工作者ID
     * @param usePriority 是否启用优先级获取（优先返回高优先级任务）
     * @return 外部任务查询构建器
     */
    ExternalTaskQueryBuilder fetchAndLock(int maxTasks, String workerId, boolean usePriority);

    /**
     * 获取并锁定外部任务（完整流式API）
     * <p>
     * 允许通过流式API配置所有参数，包括排序方式等
     *
     * @return 获取并锁定构建器
     */
    FetchAndLockBuilder fetchAndLock();

    /**
     * 锁定单个外部任务
     * <p>
     * 如果任务已被同一个工作者锁定，将成功并更新锁定时间
     *
     * @param externalTaskId 外部任务ID
     * @param workerId       工作者ID
     * @param lockDuration   锁定持续时间（毫秒）
     */
    void lock(String externalTaskId, String workerId, long lockDuration);

    /**
     * 获取并锁定外部任务（简单模式）
     *
     * @param workerId   工作者ID
     * @param maxTasks   最大返回任务数
     * @return 外部任务列表
     */
    List<ExternalTask> fetchAndLock(String workerId, int maxTasks);

    /**
     * 根据主题获取并锁定任务
     *
     * @param workerId   工作者ID
     * @param topicNames 主题名称列表
     * @param maxTasks   最大返回任务数
     * @return 外部任务列表
     */
    List<ExternalTask> fetchAndLock(String workerId, List<String> topicNames, int maxTasks);

    /**
     * 获取并锁定任务（带超时设置）
     *
     * @param workerId    工作者ID
     * @param topics      主题配置列表
     * @param maxTasks    最大返回任务数
     * @param lockDuration 锁定持续时间（毫秒）
     * @return 外部任务列表
     */
    List<ExternalTask> fetchAndLock(String workerId, List<ExternalTaskTopic> topics, int maxTasks, long lockDuration);

    // ========================================================================
    // 二、任务查询
    // ========================================================================

    /**
     * 查询任务
     *
     * @param taskId 任务ID
     * @return 外部任务对象，不存在时返回null
     */
    ExternalTask getExternalTask(String taskId);

    /**
     * 创建外部任务查询构建器
     *
     * @return 外部任务查询构建器
     */
    ExternalTaskQuery createExternalTaskQuery();

    /**
     * 查询待处理的外部任务
     *
     * @return 外部任务列表
     */
    List<ExternalTask> getPendingExternalTasks();

    /**
     * 查询指定主题的待处理任务
     *
     * @param topicName 主题名称
     * @return 外部任务列表
     */
    List<ExternalTask> getPendingTasksByTopic(String topicName);

    /**
     * 获取所有主题名称
     *
     * @return 主题名称列表
     */
    List<String> getTopicNames();

    /**
     * 获取主题名称（按条件过滤）
     *
     * @param withLockedTasks   是否包含已锁定的任务
     * @param withUnlockedTasks 是否包含未锁定的任务
     * @param withRetriesLeft   是否包含有剩余重试次数的任务
     * @return 主题名称列表
     */
    List<String> getTopicNames(boolean withLockedTasks, boolean withUnlockedTasks, boolean withRetriesLeft);

    /**
     * 获取外部任务的错误详情
     *
     * @param externalTaskId 外部任务ID
     * @return 错误详情，无错误时返回null
     */
    String getExternalTaskErrorDetails(String externalTaskId);

    // ========================================================================
    // 三、任务完成
    // ========================================================================

    /**
     * 完成任务
     * <p>
     * 给定的任务必须分配给该工作者
     *
     * @param externalTaskId 外部任务ID
     * @param workerId       工作者ID
     */
    void complete(String externalTaskId, String workerId);

    /**
     * 完成任务并设置结果变量
     *
     * @param externalTaskId 外部任务ID
     * @param workerId       工作者ID
     * @param variables      设置的全局变量（存储在执行实例上）
     */
    void complete(String externalTaskId, String workerId, Map<String, Object> variables);

    /**
     * 完成任务并设置全局变量和局部变量
     *
     * @param externalTaskId 外部任务ID
     * @param workerId       工作者ID
     * @param variables      全局变量（存储在执行实例上）
     * @param localVariables 局部变量（存储在任务本地）
     */
    void complete(String externalTaskId, String workerId, Map<String, Object> variables, Map<String, Object> localVariables);

    // ========================================================================
    // 四、任务失败处理
    // ========================================================================

    /**
     * 任务失败处理
     *
     * @param externalTaskId 外部任务ID
     * @param workerId       工作者ID
     * @param errorMessage   错误消息
     * @param errorDetails   错误详情
     */
    void handleFailure(String externalTaskId, String workerId, String errorMessage, String errorDetails);

    /**
     * 任务失败处理（带重试配置）
     *
     * @param externalTaskId 外部任务ID
     * @param workerId       工作者ID
     * @param errorMessage   错误消息
     * @param retries        剩余重试次数（必须 >= 0）
     * @param retryTimeout   重试超时时间（毫秒），超时后任务可再次被获取
     */
    void handleFailure(String externalTaskId, String workerId, String errorMessage, int retries, long retryTimeout);

    /**
     * 任务失败处理（带重试配置和错误详情）
     *
     * @param externalTaskId 外部任务ID
     * @param workerId       工作者ID
     * @param errorMessage   错误消息
     * @param errorDetails   错误详情
     * @param retries        剩余重试次数
     * @param retryTimeout   重试超时时间
     */
    void handleFailure(String externalTaskId, String workerId, String errorMessage, String errorDetails, int retries, long retryTimeout);

    /**
     * 任务失败处理（完整参数）
     * <p>
     * 如果 retries = 0，将创建异常事件（Incident），
     * 当重试次数增加时异常事件自动解决
     *
     * @param externalTaskId 外部任务ID
     * @param workerId       工作者ID
     * @param errorMessage   错误消息
     * @param errorDetails   错误详情
     * @param retries        剩余重试次数
     * @param retryDuration  重试超时时间
     * @param variables      全局变量
     * @param localVariables 局部变量
     */
    void handleFailure(
            String externalTaskId,
            String workerId,
            String errorMessage,
            String errorDetails,
            int retries,
            long retryDuration,
            Map<String, Object> variables,
            Map<String, Object> localVariables
    );

    // ========================================================================
    // 五、BPMN错误处理
    // ========================================================================

    /**
     * 处理BPMN错误
     * <p>
     * 向流程引擎发送业务错误信号，触发对应的错误边界事件
     *
     * @param externalTaskId 外部任务ID
     * @param workerId       工作者ID
     * @param errorCode      错误码
     */
    void handleBpmnError(String externalTaskId, String workerId, String errorCode);

    /**
     * 任务失败处理（带业务错误）
     *
     * @param externalTaskId 外部任务ID
     * @param workerId       工作者ID
     * @param errorCode      错误码
     * @param errorMessage   错误消息
     */
    void handleBpmnError(String externalTaskId, String workerId, String errorCode, String errorMessage);

    /**
     * 处理BPMN错误，携带变量
     *
     * @param externalTaskId 外部任务ID
     * @param workerId       工作者ID
     * @param errorCode      错误码
     * @param errorMessage   错误消息
     * @param variables      传递给流程的变量
     */
    void handleBpmnError(
            String externalTaskId,
            String workerId,
            String errorCode,
            String errorMessage,
            Map<String, Object> variables
    );

    // ========================================================================
    // 六、任务扩展操作
    // ========================================================================

    /**
     * 延长任务锁定时间
     * <p>
     * 给定的任务必须分配给该工作者
     *
     * @param externalTaskId  外部任务ID
     * @param workerId        工作者ID
     * @param newLockDuration 新的锁定持续时间（毫秒）
     */
    void extendLock(String externalTaskId, String workerId, long newLockDuration);

    /**
     * 解锁任务（释放锁定）
     *
     * @param externalTaskId 外部任务ID
     */
    void unlock(String externalTaskId);

    /**
     * 报告任务进度
     *
     * @param externalTaskId 外部任务ID
     * @param progress       进度值（0-100）
     * @param message        进度消息
     */
    void reportProgress(String externalTaskId, int progress, String message);

    // ========================================================================
    // 七、任务重试次数管理
    // ========================================================================

    /**
     * 设置任务重试次数
     * <p>
     * 如果新值为0，创建异常事件（Incident）；
     * 如果旧值为0且新值大于0，解决异常事件
     *
     * @param externalTaskId 外部任务ID
     * @param retries        重试次数
     */
    void setRetries(String externalTaskId, int retries);

    /**
     * 批量设置任务重试次数
     *
     * @param externalTaskIds 外部任务ID列表
     * @param retries         重试次数
     */
    void setRetries(List<String> externalTaskIds, int retries);

    /**
     * 异步批量设置任务重试次数
     *
     * @param externalTaskIds    外部任务ID列表
     * @param externalTaskQuery  外部任务查询条件
     * @param retries            重试次数
     * @return 批次操作对象
     */
    Batch setRetriesAsync(
            List<String> externalTaskIds,
            ExternalTaskQuery externalTaskQuery,
            int retries
    );

    /**
     * 更新任务重试次数（流式构建器）
     *
     * @return 更新重试次数构建器
     */
    UpdateExternalTaskRetriesSelectBuilder updateRetries();

    // ========================================================================
    // 八、任务优先级管理
    // ========================================================================

    /**
     * 设置任务优先级
     *
     * @param externalTaskId 外部任务ID
     * @param priority       优先级值
     */
    void setPriority(String externalTaskId, long priority);

    // ========================================================================
    // 九、主题管理
    // ========================================================================

    /**
     * 创建主题
     *
     * @param topicName   主题名称
     * @param description 主题描述
     * @return 创建的主题对象
     */
    ExternalTaskTopic createTopic(String topicName, String description);

    /**
     * 更新主题配置
     *
     * @param topicName   主题名称
     * @param maxRetries  最大重试次数
     * @param lockTimeout 锁定超时时间
     * @param retryDelay  重试延迟时间
     */
    void updateTopicConfiguration(String topicName, int maxRetries, long lockTimeout, long retryDelay);

    /**
     * 查询所有主题
     *
     * @return 主题列表
     */
    List<ExternalTaskTopic> getAllTopics();

    /**
     * 删除主题
     *
     * @param topicName 主题名称
     */
    void deleteTopic(String topicName);
}