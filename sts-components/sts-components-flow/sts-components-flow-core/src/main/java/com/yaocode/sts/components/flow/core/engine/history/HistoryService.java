package com.yaocode.sts.components.flow.core.engine.history;

import java.util.List;
import java.util.Map;

/**
 * 历史服务接口
 * 查询历史数据和生成统计报表
 *
 * @author Process Engine Team
 * @version 1.0.0
 */
public interface HistoryService {

    // ========== 历史流程实例查询 ==========

    /**
     * 查询历史流程实例
     */
    HistoricProcessInstance getHistoricProcessInstance(String processInstanceId);

    /**
     * 查询所有历史流程实例
     */
    List<HistoricProcessInstance> getHistoricProcessInstances();

    /**
     * 查询已完成的历史流程实例
     */
    List<HistoricProcessInstance> getFinishedProcessInstances();

    /**
     * 查询未完成的历史流程实例
     */
    List<HistoricProcessInstance> getUnfinishedProcessInstances();

    /**
     * 根据业务Key查询历史流程实例
     */
    List<HistoricProcessInstance> getHistoricProcessInstancesByBusinessKey(String businessKey);

    // ========== 历史任务查询 ==========

    /**
     * 查询历史任务
     */
    HistoricTaskInstance getHistoricTask(String taskId);

    /**
     * 查询用户的历史任务
     */
    List<HistoricTaskInstance> getHistoricTasksByUser(String userId);

    /**
     * 查询流程实例的所有历史任务
     */
    List<HistoricTaskInstance> getHistoricTasksByProcessInstanceId(String processInstanceId);

    /**
     * 查询已完成的历史任务
     */
    List<HistoricTaskInstance> getFinishedHistoricTasks();

    // ========== 历史活动查询 ==========

    /**
     * 查询流程实例的所有活动
     */
    List<HistoricActivityInstance> getHistoricActivities(String processInstanceId);

    /**
     * 查询流程实例的活动轨迹（按时间排序）
     */
    List<HistoricActivityInstance> getHistoricActivityTrace(String processInstanceId);

    // ========== 统计报表 ==========

    /**
     * 统计流程实例数量
     */
    long countProcessInstances();

    /**
     * 统计已完成流程实例数量
     */
    long countFinishedProcessInstances();

    /**
     * 统计流程实例的平均耗时
     */
    double getAverageProcessInstanceDuration();

    /**
     * 统计流程实例的耗时分布
     */
    Map<String, Long> getProcessInstanceDurationDistribution();

    /**
     * 统计每个流程定义的历史实例数量
     */
    Map<String, Long> countProcessInstancesByDefinitionKey();

    // ========== 历史数据清理 ==========

    /**
     * 删除历史流程实例
     */
    void deleteHistoricProcessInstance(String processInstanceId);

    /**
     * 批量删除历史流程实例（按时间）
     */
    void deleteHistoricProcessInstancesBefore(long timestamp);

    /**
     * 归档历史数据
     */
    void archiveHistoricData(String archiveTableName);
}
