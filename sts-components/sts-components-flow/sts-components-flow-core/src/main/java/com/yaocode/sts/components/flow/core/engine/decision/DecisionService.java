package com.yaocode.sts.components.flow.core.engine.decision;


import java.util.List;
import java.util.Map;

/**
 * 决策服务接口（DMN）
 * 管理决策定义和执行决策
 *
 * @author Process Engine Team
 * @version 1.0.0
 */
public interface DecisionService {

    // ========== 决策定义管理 ==========

    /**
     * 创建决策定义
     */
    DecisionDefinition createDecisionDefinition(DecisionDefinition definition);

    /**
     * 更新决策定义
     */
    void updateDecisionDefinition(DecisionDefinition definition);

    /**
     * 删除决策定义
     */
    void deleteDecisionDefinition(String decisionDefinitionId);

    /**
     * 查询决策定义
     */
    DecisionDefinition getDecisionDefinition(String decisionDefinitionId);

    /**
     * 根据Key查询最新版本
     */
    DecisionDefinition getLatestDecisionDefinitionByKey(String decisionKey);

    /**
     * 查询所有决策定义
     */
    List<DecisionDefinition> getAllDecisionDefinitions();

    // ========== 决策执行 ==========

    /**
     * 执行决策（同步）
     */
    DecisionResult evaluateDecision(String decisionKey, Map<String, Object> input);

    /**
     * 执行决策（指定版本）
     */
    DecisionResult evaluateDecision(String decisionKey, int version, Map<String, Object> input);

    /**
     * 执行决策（异步）
     */
    void evaluateDecisionAsync(String decisionKey, Map<String, Object> input,
                               DecisionCallback callback);

    /**
     * 执行批量决策
     */
    List<DecisionResult> evaluateBatchDecision(String decisionKey,
                                               List<Map<String, Object>> inputs);

    // ========== 决策历史 ==========

    /**
     * 查询决策执行历史
     */
    DecisionInstance getDecisionInstance(String decisionInstanceId);

    /**
     * 查询决策的所有执行历史
     */
    List<DecisionInstance> getDecisionInstances(String decisionKey);

    /**
     * 查询决策的执行历史（按时间范围）
     */
    List<DecisionInstance> getDecisionInstancesByTime(String decisionKey,
                                                      long startTime, long endTime);

    /**
     * 删除决策历史
     */
    void deleteDecisionHistory(String decisionInstanceId);

    // ========== 决策统计 ==========

    /**
     * 统计决策执行次数
     */
    long countDecisionExecutions(String decisionKey);

    /**
     * 统计决策执行结果分布
     */
    Map<String, Long> getDecisionResultDistribution(String decisionKey);

    /**
     * 获取决策执行的平均耗时
     */
    double getAverageDecisionExecutionTime(String decisionKey);

    /**
     * 获取决策执行的性能报表
     */
    DecisionPerformanceReport getDecisionPerformanceReport(String decisionKey);

    // ========== 决策测试 ==========

    /**
     * 测试决策（不保存历史）
     */
    DecisionResult testDecision(String decisionDefinitionId, Map<String, Object> testInput);

    /**
     * 验证决策定义
     */
    List<DecisionValidationError> validateDecisionDefinition(String decisionDefinitionId);

}
