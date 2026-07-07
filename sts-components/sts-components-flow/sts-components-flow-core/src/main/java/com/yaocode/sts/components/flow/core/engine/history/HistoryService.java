package com.yaocode.sts.components.flow.core.engine.history;

import java.util.List;
import java.util.Map;

/**
 * 历史服务接口
 * <p>
 * 提供关于运行中和已结束流程实例的历史信息查询和管理功能，包括：
 * <ul>
 *   <li>历史流程实例的查询、删除、批量清理</li>
 *   <li>历史活动实例的查询和统计</li>
 *   <li>历史任务实例的查询和删除</li>
 *   <li>历史变量实例的查询和删除</li>
 *   <li>历史决策实例的查询和删除</li>
 *   <li>历史案例实例的查询和删除</li>
 *   <li>用户操作日志查询</li>
 *   <li>历史作业日志和外部任务日志查询</li>
 *   <li>历史报表统计（流程实例、任务实例）</li>
 *   <li>历史数据清理和归档</li>
 *   <li>移除时间设置</li>
 * </ul>
 *
 * @author Process Engine Team
 * @version 1.0.0
 */
public interface HistoryService {

    // ========================================================================
    // 一、查询构建器
    // ========================================================================

    /**
     * 创建历史流程实例查询构建器
     *
     * @return 历史流程实例查询构建器
     */
    HistoricProcessInstanceQuery createHistoricProcessInstanceQuery();

    /**
     * 创建历史活动实例查询构建器
     *
     * @return 历史活动实例查询构建器
     */
    HistoricActivityInstanceQuery createHistoricActivityInstanceQuery();

    /**
     * 创建历史任务实例查询构建器
     *
     * @return 历史任务实例查询构建器
     */
    HistoricTaskInstanceQuery createHistoricTaskInstanceQuery();

    /**
     * 创建历史详情查询构建器
     *
     * @return 历史详情查询构建器
     */
    HistoricDetailQuery createHistoricDetailQuery();

    /**
     * 创建历史变量实例查询构建器
     *
     * @return 历史变量实例查询构建器
     */
    HistoricVariableInstanceQuery createHistoricVariableInstanceQuery();

    /**
     * 创建用户操作日志查询构建器
     *
     * @return 用户操作日志查询构建器
     */
    UserOperationLogQuery createUserOperationLogQuery();

    /**
     * 创建历史异常事件查询构建器
     *
     * @return 历史异常事件查询构建器
     */
    HistoricIncidentQuery createHistoricIncidentQuery();

    /**
     * 创建历史身份关联日志查询构建器
     *
     * @return 历史身份关联日志查询构建器
     */
    HistoricIdentityLinkLogQuery createHistoricIdentityLinkLogQuery();

    /**
     * 创建历史作业日志查询构建器
     *
     * @return 历史作业日志查询构建器
     */
    HistoricJobLogQuery createHistoricJobLogQuery();

    /**
     * 创建历史外部任务日志查询构建器
     *
     * @return 历史外部任务日志查询构建器
     */
    HistoricExternalTaskLogQuery createHistoricExternalTaskLogQuery();

    /**
     * 创建历史案例实例查询构建器
     *
     * @return 历史案例实例查询构建器
     */
    HistoricCaseInstanceQuery createHistoricCaseInstanceQuery();

    /**
     * 创建历史案例活动实例查询构建器
     *
     * @return 历史案例活动实例查询构建器
     */
    HistoricCaseActivityInstanceQuery createHistoricCaseActivityInstanceQuery();

    /**
     * 创建历史决策实例查询构建器
     *
     * @return 历史决策实例查询构建器
     */
    HistoricDecisionInstanceQuery createHistoricDecisionInstanceQuery();

    /**
     * 创建历史批次查询构建器
     *
     * @return 历史批次查询构建器
     */
    HistoricBatchQuery createHistoricBatchQuery();

    // ========================================================================
    // 二、原生SQL查询构建器
    // ========================================================================

    /**
     * 创建原生SQL历史流程实例查询构建器
     *
     * @return 原生SQL历史流程实例查询构建器
     */
    NativeHistoricProcessInstanceQuery createNativeHistoricProcessInstanceQuery();

    /**
     * 创建原生SQL历史任务实例查询构建器
     *
     * @return 原生SQL历史任务实例查询构建器
     */
    NativeHistoricTaskInstanceQuery createNativeHistoricTaskInstanceQuery();

    /**
     * 创建原生SQL历史活动实例查询构建器
     *
     * @return 原生SQL历史活动实例查询构建器
     */
    NativeHistoricActivityInstanceQuery createNativeHistoricActivityInstanceQuery();

    /**
     * 创建原生SQL历史案例实例查询构建器
     *
     * @return 原生SQL历史案例实例查询构建器
     */
    NativeHistoricCaseInstanceQuery createNativeHistoricCaseInstanceQuery();

    /**
     * 创建原生SQL历史案例活动实例查询构建器
     *
     * @return 原生SQL历史案例活动实例查询构建器
     */
    NativeHistoricCaseActivityInstanceQuery createNativeHistoricCaseActivityInstanceQuery();

    /**
     * 创建原生SQL历史决策实例查询构建器
     *
     * @return 原生SQL历史决策实例查询构建器
     */
    NativeHistoricDecisionInstanceQuery createNativeHistoricDecisionInstanceQuery();

    /**
     * 创建原生SQL历史变量实例查询构建器
     *
     * @return 原生SQL历史变量实例查询构建器
     */
    NativeHistoricVariableInstanceQuery createNativeHistoricVariableInstanceQuery();

    // ========================================================================
    // 三、历史流程实例查询
    // ========================================================================

    /**
     * 根据流程实例ID查询历史流程实例
     *
     * @param processInstanceId 流程实例ID
     * @return 历史流程实例对象，不存在时返回null
     */
    HistoricProcessInstance getHistoricProcessInstance(String processInstanceId);

    /**
     * 查询所有历史流程实例
     *
     * @return 历史流程实例列表
     */
    List<HistoricProcessInstance> getHistoricProcessInstances();

    /**
     * 查询已完成的历史流程实例
     *
     * @return 已完成的流程实例列表
     */
    List<HistoricProcessInstance> getFinishedProcessInstances();

    /**
     * 查询未完成的历史流程实例
     *
     * @return 未完成的流程实例列表
     */
    List<HistoricProcessInstance> getUnfinishedProcessInstances();

    /**
     * 根据业务Key查询历史流程实例
     *
     * @param businessKey 业务Key
     * @return 匹配的历史流程实例列表
     */
    List<HistoricProcessInstance> getHistoricProcessInstancesByBusinessKey(String businessKey);

    // ========================================================================
    // 四、历史任务实例查询
    // ========================================================================

    /**
     * 根据任务ID查询历史任务
     *
     * @param taskId 任务ID
     * @return 历史任务对象，不存在时返回null
     */
    HistoricTaskInstance getHistoricTask(String taskId);

    /**
     * 查询用户的历史任务
     *
     * @param userId 用户ID
     * @return 历史任务列表
     */
    List<HistoricTaskInstance> getHistoricTasksByUser(String userId);

    /**
     * 查询流程实例的所有历史任务
     *
     * @param processInstanceId 流程实例ID
     * @return 历史任务列表
     */
    List<HistoricTaskInstance> getHistoricTasksByProcessInstanceId(String processInstanceId);

    /**
     * 查询所有已完成的历史任务
     *
     * @return 已完成的历史任务列表
     */
    List<HistoricTaskInstance> getFinishedHistoricTasks();

    // ========================================================================
    // 五、历史活动实例查询
    // ========================================================================

    /**
     * 查询流程实例的所有历史活动
     *
     * @param processInstanceId 流程实例ID
     * @return 历史活动实例列表
     */
    List<HistoricActivityInstance> getHistoricActivities(String processInstanceId);

    /**
     * 查询流程实例的活动轨迹（按时间排序）
     *
     * @param processInstanceId 流程实例ID
     * @return 按时间排序的历史活动实例列表
     */
    List<HistoricActivityInstance> getHistoricActivityTrace(String processInstanceId);

    /**
     * 创建历史活动统计查询
     * <p>
     * 按流程定义的活动聚合统计活动实例数量
     *
     * @param processDefinitionId 流程定义ID
     * @return 历史活动统计查询构建器
     */
    HistoricActivityStatisticsQuery createHistoricActivityStatisticsQuery(String processDefinitionId);

    /**
     * 创建历史案例活动统计查询
     *
     * @param caseDefinitionId 案例定义ID
     * @return 历史案例活动统计查询构建器
     */
    HistoricCaseActivityStatisticsQuery createHistoricCaseActivityStatisticsQuery(String caseDefinitionId);

    // ========================================================================
    // 六、统计报表
    // ========================================================================

    /**
     * 统计历史流程实例总数
     *
     * @return 流程实例总数
     */
    long countProcessInstances();

    /**
     * 统计已完成的历史流程实例数量
     *
     * @return 已完成流程实例数量
     */
    long countFinishedProcessInstances();

    /**
     * 统计流程实例的平均耗时
     *
     * @return 平均耗时（毫秒）
     */
    double getAverageProcessInstanceDuration();

    /**
     * 统计流程实例的耗时分布
     *
     * @return 耗时分布Map（时间段 -> 数量）
     */
    Map<String, Long> getProcessInstanceDurationDistribution();

    /**
     * 按流程定义Key统计历史实例数量
     *
     * @return 流程定义Key -> 实例数量
     */
    Map<String, Long> countProcessInstancesByDefinitionKey();

    /**
     * 创建历史流程实例报表
     *
     * @return 历史流程实例报表构建器
     */
    HistoricProcessInstanceReport createHistoricProcessInstanceReport();

    /**
     * 创建历史任务实例报表
     *
     * @return 历史任务实例报表构建器
     */
    HistoricTaskInstanceReport createHistoricTaskInstanceReport();

    /**
     * 创建可清理的历史流程实例报表
     * <p>
     * 用于查询哪些流程实例可以按TTL策略清理
     *
     * @return 可清理历史流程实例报表构建器
     */
    CleanableHistoricProcessInstanceReport createCleanableHistoricProcessInstanceReport();

    /**
     * 创建可清理的历史决策实例报表
     *
     * @return 可清理历史决策实例报表构建器
     */
    CleanableHistoricDecisionInstanceReport createCleanableHistoricDecisionInstanceReport();

    /**
     * 创建可清理的历史案例实例报表
     *
     * @return 可清理历史案例实例报表构建器
     */
    CleanableHistoricCaseInstanceReport createCleanableHistoricCaseInstanceReport();

    /**
     * 创建可清理的历史批次报表
     *
     * @return 可清理历史批次报表构建器
     */
    CleanableHistoricBatchReport createCleanableHistoricBatchReport();

    /**
     * 创建历史决策实例统计查询
     *
     * @param decisionRequirementsDefinitionId 决策需求定义ID
     * @return 历史决策实例统计查询构建器
     */
    HistoricDecisionInstanceStatisticsQuery createHistoricDecisionInstanceStatisticsQuery(String decisionRequirementsDefinitionId);

    // ========================================================================
    // 七、历史数据删除（同步）
    // ========================================================================

    /**
     * 删除历史任务实例
     * <p>
     * 如果历史任务实例不存在，不抛出异常
     *
     * @param taskId 任务ID
     */
    void deleteHistoricTaskInstance(String taskId);

    /**
     * 删除历史流程实例
     * <p>
     * 同时删除所有关联的历史活动、历史任务和历史详情
     *
     * @param processInstanceId 流程实例ID
     */
    void deleteHistoricProcessInstance(String processInstanceId);

    /**
     * 删除历史流程实例（如果存在）
     * <p>
     * 如果流程实例不存在，不抛出异常
     *
     * @param processInstanceId 流程实例ID
     */
    void deleteHistoricProcessInstanceIfExists(String processInstanceId);

    /**
     * 批量删除历史流程实例
     *
     * @param processInstanceIds 流程实例ID列表
     */
    void deleteHistoricProcessInstances(List<String> processInstanceIds);

    /**
     * 批量删除历史流程实例（如果存在）
     *
     * @param processInstanceIds 流程实例ID列表
     */
    void deleteHistoricProcessInstancesIfExists(List<String> processInstanceIds);

    /**
     * 批量删除历史流程实例（使用IN子句）
     * <p>
     * 注意：需注意数据库IN子句的数量限制
     *
     * @param processInstanceIds 流程实例ID列表
     */
    void deleteHistoricProcessInstancesBulk(List<String> processInstanceIds);

    /**
     * 删除历史案例实例
     *
     * @param caseInstanceId 案例实例ID
     */
    void deleteHistoricCaseInstance(String caseInstanceId);

    /**
     * 批量删除历史案例实例
     *
     * @param caseInstanceIds 案例实例ID列表
     */
    void deleteHistoricCaseInstancesBulk(List<String> caseInstanceIds);

    /**
     * 按定义ID删除历史决策实例
     *
     * @param decisionDefinitionId 决策定义ID
     */
    void deleteHistoricDecisionInstanceByDefinitionId(String decisionDefinitionId);

    /**
     * 按实例ID删除历史决策实例
     *
     * @param historicDecisionInstanceId 历史决策实例ID
     */
    void deleteHistoricDecisionInstanceByInstanceId(String historicDecisionInstanceId);

    /**
     * 批量删除历史决策实例
     *
     * @param decisionInstanceIds 决策实例ID列表
     */
    void deleteHistoricDecisionInstancesBulk(List<String> decisionInstanceIds);

    /**
     * 删除历史变量实例
     *
     * @param variableInstanceId 变量实例ID
     */
    void deleteHistoricVariableInstance(String variableInstanceId);

    /**
     * 删除流程实例的所有历史变量和详情
     *
     * @param processInstanceId 流程实例ID
     */
    void deleteHistoricVariableInstancesByProcessInstanceId(String processInstanceId);

    /**
     * 删除历史批次
     *
     * @param id 批次ID
     */
    void deleteHistoricBatch(String id);

    /**
     * 删除用户操作日志条目
     *
     * @param entryId 日志条目ID
     */
    void deleteUserOperationLogEntry(String entryId);

    // ========================================================================
    // 八、历史数据删除（异步批量）
    // ========================================================================

    /**
     * 异步批量删除历史流程实例（通过ID列表）
     *
     * @param processInstanceIds 流程实例ID列表
     * @param deleteReason       删除原因
     * @return 批次操作对象
     */
    Batch deleteHistoricProcessInstancesAsync(List<String> processInstanceIds, String deleteReason);

    /**
     * 异步批量删除历史流程实例（通过查询条件）
     *
     * @param query         历史流程实例查询条件
     * @param deleteReason  删除原因
     * @return 批次操作对象
     */
    Batch deleteHistoricProcessInstancesAsync(HistoricProcessInstanceQuery query, String deleteReason);

    /**
     * 异步批量删除历史流程实例（合并ID列表和查询条件）
     *
     * @param processInstanceIds 流程实例ID列表
     * @param query              历史流程实例查询条件
     * @param deleteReason       删除原因
     * @return 批次操作对象
     */
    Batch deleteHistoricProcessInstancesAsync(List<String> processInstanceIds,
                                              HistoricProcessInstanceQuery query,
                                              String deleteReason);

    /**
     * 异步批量删除历史决策实例（通过ID列表）
     *
     * @param decisionInstanceIds 决策实例ID列表
     * @param deleteReason        删除原因
     * @return 批次操作对象
     */
    Batch deleteHistoricDecisionInstancesAsync(List<String> decisionInstanceIds, String deleteReason);

    /**
     * 异步批量删除历史决策实例（通过查询条件）
     *
     * @param query         历史决策实例查询条件
     * @param deleteReason  删除原因
     * @return 批次操作对象
     */
    Batch deleteHistoricDecisionInstancesAsync(HistoricDecisionInstanceQuery query, String deleteReason);

    /**
     * 异步批量删除历史决策实例（合并ID列表和查询条件）
     *
     * @param decisionInstanceIds 决策实例ID列表
     * @param query               历史决策实例查询条件
     * @param deleteReason        删除原因
     * @return 批次操作对象
     */
    Batch deleteHistoricDecisionInstancesAsync(List<String> decisionInstanceIds,
                                               HistoricDecisionInstanceQuery query,
                                               String deleteReason);

    // ========================================================================
    // 九、历史数据清理
    // ========================================================================

    /**
     * 异步清理历史数据
     * <p>
     * 根据流程定义、决策定义、案例定义的TTL配置，清理已完成的历史数据
     *
     * @return 清理作业对象
     */
    Job cleanUpHistoryAsync();

    /**
     * 异步清理历史数据
     *
     * @param immediatelyDue 是否立即执行（true立即执行，false按批次窗口调度）
     * @return 清理作业对象
     */
    Job cleanUpHistoryAsync(boolean immediatelyDue);

    /**
     * 查找历史清理作业列表
     *
     * @return 历史清理作业列表
     */
    List<Job> findHistoryCleanupJobs();

    /**
     * 获取历史作业日志的异常堆栈信息
     *
     * @param historicJobLogId 历史作业日志ID
     * @return 异常堆栈信息
     */
    String getHistoricJobLogExceptionStacktrace(String historicJobLogId);

    /**
     * 获取历史外部任务日志的错误详情
     *
     * @param historicExternalTaskLogId 历史外部任务日志ID
     * @return 错误详情信息
     */
    String getHistoricExternalTaskLogErrorDetails(String historicExternalTaskLogId);

    // ========================================================================
    // 十、移除时间管理
    // ========================================================================

    /**
     * 设置历史流程实例的移除时间（构建器模式）
     * <p>
     * 可指定绝对时间或基于TTL计算
     *
     * @return 移除时间设置构建器
     */
    SetRemovalTimeSelectModeForHistoricProcessInstancesBuilder setRemovalTimeToHistoricProcessInstances();

    /**
     * 设置历史决策实例的移除时间（构建器模式）
     *
     * @return 移除时间设置构建器
     */
    SetRemovalTimeSelectModeForHistoricDecisionInstancesBuilder setRemovalTimeToHistoricDecisionInstances();

    /**
     * 设置历史批次的移除时间（构建器模式）
     *
     * @return 移除时间设置构建器
     */
    SetRemovalTimeSelectModeForHistoricBatchesBuilder setRemovalTimeToHistoricBatches();

    // ========================================================================
    // 十一、用户操作日志注释管理
    // ========================================================================

    /**
     * 为用户操作日志条目设置注释
     *
     * @param operationId 操作ID
     * @param annotation  注释内容
     */
    void setAnnotationForOperationLogById(String operationId, String annotation);

    /**
     * 清除用户操作日志条目的注释
     *
     * @param operationId 操作ID
     */
    void clearAnnotationForOperationLogById(String operationId);
}