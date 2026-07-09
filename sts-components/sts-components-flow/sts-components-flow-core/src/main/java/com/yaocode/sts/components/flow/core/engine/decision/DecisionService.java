package com.yaocode.sts.components.flow.core.engine.decision;

import java.util.List;
import java.util.Map;

/**
 * 决策服务接口（DMN）
 * <p>
 * 管理决策定义和执行决策，包括：
 * <ul>
 *   <li>决策定义的创建、更新、删除、查询</li>
 *   <li>决策的同步执行（按Key/ID/版本）</li>
 *   <li>决策的异步执行（回调模式）</li>
 *   <li>批量决策执行</li>
 *   <li>决策执行历史查询与删除</li>
 *   <li>决策统计（执行次数、结果分布、平均耗时、性能报表）</li>
 *   <li>决策测试（不保存历史）</li>
 *   <li>决策定义验证</li>
 *   <li>流式API（构建器模式）</li>
 * </ul>
 * <p>
 * 使用示例：
 * <pre>
 * // 同步执行决策
 * DmnDecisionTableResult result = decisionService
 *     .evaluateDecisionTableByKey("orderDecision")
 *     .variables(variables)
 *     .evaluate();
 *
 * // 异步执行决策
 * decisionService.evaluateDecisionAsync("orderDecision", variables,
 *     new DecisionCallback() {
 *         public void onSuccess(DecisionResult result) { ... }
 *         public void onFailure(Exception e) { ... }
 *     });
 * </pre>
 *
 * @author Process Engine Team
 * @version 1.0.0
 */
public interface DecisionService {

    // ========================================================================
    // 一、决策定义管理
    // ========================================================================

    /**
     * 创建决策定义
     *
     * @param definition 决策定义对象
     * @return 创建后的决策定义
     */
    DecisionDefinition createDecisionDefinition(DecisionDefinition definition);

    /**
     * 更新决策定义
     *
     * @param definition 决策定义对象
     */
    void updateDecisionDefinition(DecisionDefinition definition);

    /**
     * 删除决策定义
     *
     * @param decisionDefinitionId 决策定义ID
     */
    void deleteDecisionDefinition(String decisionDefinitionId);

    /**
     * 查询决策定义
     *
     * @param decisionDefinitionId 决策定义ID
     * @return 决策定义对象，不存在时返回null
     */
    DecisionDefinition getDecisionDefinition(String decisionDefinitionId);

    /**
     * 根据Key查询最新版本的决策定义
     *
     * @param decisionKey 决策Key
     * @return 最新版本的决策定义
     */
    DecisionDefinition getLatestDecisionDefinitionByKey(String decisionKey);

    /**
     * 查询所有决策定义
     *
     * @return 决策定义列表
     */
    List<DecisionDefinition> getAllDecisionDefinitions();

    // ========================================================================
    // 二、决策同步执行
    // ========================================================================

    /**
     * 执行决策（按Key，使用最新版本）
     *
     * @param decisionKey 决策Key
     * @param input       输入参数
     * @return 决策执行结果
     */
    DecisionResult evaluateDecision(String decisionKey, Map<String, Object> input);

    /**
     * 执行决策（按Key，指定版本）
     *
     * @param decisionKey 决策Key
     * @param version     版本号
     * @param input       输入参数
     * @return 决策执行结果
     */
    DecisionResult evaluateDecision(String decisionKey, int version, Map<String, Object> input);

    /**
     * 执行决策表（按Key，使用最新版本）
     *
     * @param decisionDefinitionKey 决策定义Key
     * @param variables             输入变量
     * @return 决策表执行结果
     */
    DmnDecisionTableResult evaluateDecisionTableByKey(String decisionDefinitionKey, Map<String, Object> variables);

    /**
     * 执行决策表（按ID）
     *
     * @param decisionDefinitionId 决策定义ID
     * @param variables            输入变量
     * @return 决策表执行结果
     */
    DmnDecisionTableResult evaluateDecisionTableById(String decisionDefinitionId, Map<String, Object> variables);

    /**
     * 执行决策表（按Key，指定版本）
     *
     * @param decisionDefinitionKey 决策定义Key
     * @param version               版本号（为null时使用最新版本）
     * @param variables             输入变量
     * @return 决策表执行结果
     */
    DmnDecisionTableResult evaluateDecisionTableByKeyAndVersion(
            String decisionDefinitionKey,
            Integer version,
            Map<String, Object> variables
    );

    // ========================================================================
    // 三、决策异步执行
    // ========================================================================

    /**
     * 异步执行决策
     *
     * @param decisionKey 决策Key
     * @param input       输入参数
     * @param callback    执行结果回调
     */
    void evaluateDecisionAsync(String decisionKey, Map<String, Object> input, DecisionCallback callback);

    // ========================================================================
    // 四、批量决策执行
    // ========================================================================

    /**
     * 批量执行决策
     *
     * @param decisionKey 决策Key
     * @param inputs      多个输入参数列表
     * @return 决策执行结果列表
     */
    List<DecisionResult> evaluateBatchDecision(String decisionKey, List<Map<String, Object>> inputs);

    // ========================================================================
    // 五、流式API（构建器模式）
    // ========================================================================

    /**
     * 返回流式构建器执行决策表（按Key）
     *
     * @param decisionDefinitionKey 决策定义Key
     * @return 决策表执行构建器
     */
    DecisionEvaluationBuilder evaluateDecisionTableByKey(String decisionDefinitionKey);

    /**
     * 返回流式构建器执行决策表（按ID）
     *
     * @param decisionDefinitionId 决策定义ID
     * @return 决策表执行构建器
     */
    DecisionEvaluationBuilder evaluateDecisionTableById(String decisionDefinitionId);

    /**
     * 返回流式构建器执行决策（按Key）
     *
     * @param decisionDefinitionKey 决策定义Key
     * @return 决策执行构建器
     */
    DecisionsEvaluationBuilder evaluateDecisionByKey(String decisionDefinitionKey);

    /**
     * 返回流式构建器执行决策（按ID）
     *
     * @param decisionDefinitionId 决策定义ID
     * @return 决策执行构建器
     */
    DecisionsEvaluationBuilder evaluateDecisionById(String decisionDefinitionId);

    // ========================================================================
    // 六、决策历史查询
    // ========================================================================

    /**
     * 查询决策执行历史（按实例ID）
     *
     * @param decisionInstanceId 决策实例ID
     * @return 决策实例对象，不存在时返回null
     */
    DecisionInstance getDecisionInstance(String decisionInstanceId);

    /**
     * 查询决策的所有执行历史（按Key）
     *
     * @param decisionKey 决策Key
     * @return 决策实例列表
     */
    List<DecisionInstance> getDecisionInstances(String decisionKey);

    /**
     * 按时间范围查询决策执行历史
     *
     * @param decisionKey 决策Key
     * @param startTime   开始时间（毫秒）
     * @param endTime     结束时间（毫秒）
     * @return 决策实例列表
     */
    List<DecisionInstance> getDecisionInstancesByTime(String decisionKey, long startTime, long endTime);

    /**
     * 删除决策历史
     *
     * @param decisionInstanceId 决策实例ID
     */
    void deleteDecisionHistory(String decisionInstanceId);

    // ========================================================================
    // 七、决策统计
    // ========================================================================

    /**
     * 统计决策执行次数
     *
     * @param decisionKey 决策Key
     * @return 执行次数
     */
    long countDecisionExecutions(String decisionKey);

    /**
     * 统计决策执行结果分布
     *
     * @param decisionKey 决策Key
     * @return 结果名称 -> 次数
     */
    Map<String, Long> getDecisionResultDistribution(String decisionKey);

    /**
     * 获取决策执行的平均耗时
     *
     * @param decisionKey 决策Key
     * @return 平均耗时（毫秒）
     */
    double getAverageDecisionExecutionTime(String decisionKey);

    /**
     * 获取决策执行的性能报表
     *
     * @param decisionKey 决策Key
     * @return 性能报表
     */
    DecisionPerformanceReport getDecisionPerformanceReport(String decisionKey);

    // ========================================================================
    // 八、决策测试与验证
    // ========================================================================

    /**
     * 测试决策（不保存历史记录）
     *
     * @param decisionDefinitionId 决策定义ID
     * @param testInput            测试输入参数
     * @return 测试执行结果
     */
    DecisionResult testDecision(String decisionDefinitionId, Map<String, Object> testInput);

    /**
     * 验证决策定义
     *
     * @param decisionDefinitionId 决策定义ID
     * @return 验证错误列表
     */
    List<DecisionValidationError> validateDecisionDefinition(String decisionDefinitionId);
}