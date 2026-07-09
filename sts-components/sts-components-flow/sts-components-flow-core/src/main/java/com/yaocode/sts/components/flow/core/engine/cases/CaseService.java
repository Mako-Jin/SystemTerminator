package com.yaocode.sts.components.flow.core.engine.cases;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 案例服务接口（CMMN）
 * <p>
 * 管理CMMN（案例管理模型和符号）案例实例和案例执行，包括：
 * <ul>
 *   <li>案例定义的查询、挂起、激活</li>
 *   <li>案例实例的创建、查询、挂起、激活、终止</li>
 *   <li>案例执行的手动启动、禁用、重新启用、完成、终止</li>
 *   <li>案例变量的设置、获取、删除</li>
 *   <li>案例实例和案例执行的查询构建器</li>
 *   <li>流式API（构建器模式）</li>
 * </ul>
 * <p>
 * CMMN状态说明：
 * <ul>
 *   <li><b>ENABLED</b>：已启用，等待手动启动</li>
 *   <li><b>ACTIVE</b>：激活中，正在执行</li>
 *   <li><b>DISABLED</b>：已禁用，不执行</li>
 *   <li><b>COMPLETED</b>：已完成</li>
 *   <li><b>TERMINATED</b>：已终止</li>
 *   <li><b>CLOSED</b>：已关闭（案例实例最终状态）</li>
 * </ul>
 *
 * @author Process Engine Team
 * @version 1.0.0
 */
public interface CaseService {

    // ========================================================================
    // 一、案例定义管理
    // ========================================================================

    /**
     * 查询案例定义
     *
     * @param caseDefinitionId 案例定义ID
     * @return 案例定义对象，不存在时返回null
     */
    CaseDefinition getCaseDefinition(String caseDefinitionId);

    /**
     * 根据Key查询最新版本的案例定义
     *
     * @param caseDefinitionKey 案例定义Key
     * @return 最新版本的案例定义
     */
    CaseDefinition getLatestCaseDefinitionByKey(String caseDefinitionKey);

    /**
     * 查询所有案例定义
     *
     * @return 案例定义列表
     */
    List<CaseDefinition> getAllCaseDefinitions();

    /**
     * 挂起案例定义
     * <p>
     * 挂起后，该案例定义无法启动新的案例实例
     *
     * @param caseDefinitionId 案例定义ID
     */
    void suspendCaseDefinition(String caseDefinitionId);

    /**
     * 激活案例定义
     * <p>
     * 激活后，该案例定义可以启动新的案例实例
     *
     * @param caseDefinitionId 案例定义ID
     */
    void activateCaseDefinition(String caseDefinitionId);

    // ========================================================================
    // 二、案例实例管理
    // ========================================================================

    /**
     * 根据Key启动案例实例（使用最新版本）
     *
     * @param caseDefinitionKey 案例定义Key
     * @return 新创建的案例实例
     */
    CaseInstance startCaseInstance(String caseDefinitionKey);

    /**
     * 根据Key启动案例实例（使用最新版本），携带变量
     *
     * @param caseDefinitionKey 案例定义Key
     * @param variables         案例变量
     * @return 新创建的案例实例
     */
    CaseInstance startCaseInstance(String caseDefinitionKey, Map<String, Object> variables);

    /**
     * 根据Key启动案例实例（使用最新版本），指定业务Key和变量
     *
     * @param caseDefinitionKey 案例定义Key
     * @param businessKey       业务Key（用于关联业务数据）
     * @param variables         案例变量
     * @return 新创建的案例实例
     */
    CaseInstance startCaseInstance(
            String caseDefinitionKey,
            String businessKey,
            Map<String, Object> variables
    );

    /**
     * 根据Key启动案例实例（使用最新版本）
     *
     * @param caseDefinitionKey 案例定义Key
     * @return 新创建的案例实例
     */
    CaseInstance createCaseInstanceByKey(String caseDefinitionKey);

    /**
     * 根据Key启动案例实例（使用最新版本），指定业务Key
     *
     * @param caseDefinitionKey 案例定义Key
     * @param businessKey       业务Key
     * @return 新创建的案例实例
     */
    CaseInstance createCaseInstanceByKey(String caseDefinitionKey, String businessKey);

    /**
     * 根据Key启动案例实例（使用最新版本），携带变量
     *
     * @param caseDefinitionKey 案例定义Key
     * @param variables         案例变量
     * @return 新创建的案例实例
     */
    CaseInstance createCaseInstanceByKey(String caseDefinitionKey, Map<String, Object> variables);

    /**
     * 根据Key启动案例实例（使用最新版本），指定业务Key和变量
     *
     * @param caseDefinitionKey 案例定义Key
     * @param businessKey       业务Key
     * @param variables         案例变量
     * @return 新创建的案例实例
     */
    CaseInstance createCaseInstanceByKey(
            String caseDefinitionKey,
            String businessKey,
            Map<String, Object> variables
    );

    /**
     * 根据案例定义ID启动案例实例（指定版本）
     *
     * @param caseDefinitionId 案例定义ID
     * @return 新创建的案例实例
     */
    CaseInstance createCaseInstanceById(String caseDefinitionId);

    /**
     * 根据案例定义ID启动案例实例（指定版本），指定业务Key
     *
     * @param caseDefinitionId 案例定义ID
     * @param businessKey      业务Key
     * @return 新创建的案例实例
     */
    CaseInstance createCaseInstanceById(String caseDefinitionId, String businessKey);

    /**
     * 根据案例定义ID启动案例实例（指定版本），携带变量
     *
     * @param caseDefinitionId 案例定义ID
     * @param variables        案例变量
     * @return 新创建的案例实例
     */
    CaseInstance createCaseInstanceById(String caseDefinitionId, Map<String, Object> variables);

    /**
     * 根据案例定义ID启动案例实例（指定版本），指定业务Key和变量
     *
     * @param caseDefinitionId 案例定义ID
     * @param businessKey      业务Key
     * @param variables        案例变量
     * @return 新创建的案例实例
     */
    CaseInstance createCaseInstanceById(String caseDefinitionId, String businessKey, Map<String, Object> variables);

    /**
     * 查询案例实例
     *
     * @param caseInstanceId 案例实例ID
     * @return 案例实例对象，不存在时返回null
     */
    CaseInstance getCaseInstance(String caseInstanceId);

    /**
     * 查询所有活跃的案例实例
     *
     * @return 活跃案例实例列表
     */
    List<CaseInstance> getActiveCaseInstances();

    /**
     * 挂起案例实例
     *
     * @param caseInstanceId 案例实例ID
     */
    void suspendCaseInstance(String caseInstanceId);

    /**
     * 激活案例实例
     *
     * @param caseInstanceId 案例实例ID
     */
    void activateCaseInstance(String caseInstanceId);

    /**
     * 终止案例实例
     *
     * @param caseInstanceId 案例实例ID
     */
    void terminateCaseInstance(String caseInstanceId);

    /**
     * 关闭案例实例
     * <p>
     * 将已完成的案例实例从 COMPLETED 状态转换为 CLOSED 状态。
     * 关闭后，不允许对该案例实例进行任何进一步的工作或修改
     *
     * @param caseExecutionId 案例执行ID（属于该案例实例）
     */
    void closeCaseInstance(String caseExecutionId);

    // ========================================================================
    // 三、案例执行操作（CMMN状态转换）
    // ========================================================================

    /**
     * 手动启动案例执行
     * <p>
     * 将案例执行从 ENABLED 状态转换为 ACTIVE 状态。
     * 根据CMMN规范：
     * <ul>
     *   <li>Task：任务立即完成</li>
     *   <li>HumanTask：创建新的用户任务</li>
     *   <li>ProcessTask：创建新的流程实例</li>
     *   <li>CaseTask：创建新的案例实例</li>
     * </ul>
     *
     * @param caseExecutionId 案例执行ID
     */
    void manuallyStartCaseExecution(String caseExecutionId);

    /**
     * 手动启动案例执行，携带变量
     *
     * @param caseExecutionId 案例执行ID
     * @param variables       传递给案例执行的变量
     */
    void manuallyStartCaseExecution(String caseExecutionId, Map<String, Object> variables);

    /**
     * 禁用案例执行
     * <p>
     * 将案例执行从 ENABLED 状态转换为 DISABLED 状态。
     * 如果案例执行有父执行，父执行会被通知，可能导致父执行完成
     *
     * @param caseExecutionId 案例执行ID
     */
    void disableCaseExecution(String caseExecutionId);

    /**
     * 禁用案例执行，携带变量
     *
     * @param caseExecutionId 案例执行ID
     * @param variables       传递给案例执行的变量
     */
    void disableCaseExecution(String caseExecutionId, Map<String, Object> variables);

    /**
     * 重新启用案例执行
     * <p>
     * 将案例执行从 DISABLED 状态转换为 ENABLED 状态
     *
     * @param caseExecutionId 案例执行ID
     */
    void reenableCaseExecution(String caseExecutionId);

    /**
     * 重新启用案例执行，携带变量
     *
     * @param caseExecutionId 案例执行ID
     * @param variables       传递给案例执行的变量
     */
    void reenableCaseExecution(String caseExecutionId, Map<String, Object> variables);

    /**
     * 完成案例执行
     * <p>
     * 将案例执行从 ACTIVE 状态转换为 COMPLETED 状态。
     * 仅当满足完成条件时才可完成：
     * <ul>
     *   <li>Stage：没有 ACTIVE 状态的子项</li>
     *   <li>Task：任务目标已达成</li>
     * </ul>
     *
     * @param caseExecutionId 案例执行ID
     */
    void completeCaseExecution(String caseExecutionId);

    /**
     * 完成案例执行，携带变量
     *
     * @param caseExecutionId 案例执行ID
     * @param variables       传递给案例执行的变量
     */
    void completeCaseExecution(String caseExecutionId, Map<String, Object> variables);

    /**
     * 终止案例执行
     * <p>
     * 将案例执行从 ACTIVE/AVAILABLE 状态转换为 TERMINATED 状态
     *
     * @param caseExecutionId 案例执行ID
     */
    void terminateCaseExecution(String caseExecutionId);

    /**
     * 终止案例执行，携带变量
     *
     * @param caseExecutionId 案例执行ID
     * @param variables       传递给案例执行的变量
     */
    void terminateCaseExecution(String caseExecutionId, Map<String, Object> variables);

    // ========================================================================
    // 四、流式API（构建器模式）
    // ========================================================================

    /**
     * 使用流式构建器定义案例实例（通过Key）
     * <p>
     * 使用最新版本的案例定义创建新案例实例
     *
     * @param caseDefinitionKey 案例定义Key
     * @return 案例实例构建器
     */
    CaseInstanceBuilder withCaseDefinitionByKey(String caseDefinitionKey);

    /**
     * 使用流式构建器定义案例实例（通过ID）
     * <p>
     * 使用指定版本的案例定义创建新案例实例
     *
     * @param caseDefinitionId 案例定义ID
     * @return 案例实例构建器
     */
    CaseInstanceBuilder withCaseDefinition(String caseDefinitionId);

    /**
     * 使用流式构建器定义案例执行命令
     *
     * @param caseExecutionId 案例执行ID
     * @return 案例执行命令构建器
     */
    CaseExecutionCommandBuilder withCaseExecution(String caseExecutionId);

    // ========================================================================
    // 五、查询API
    // ========================================================================

    /**
     * 创建案例实例查询构建器
     *
     * @return 案例实例查询构建器
     */
    CaseInstanceQuery createCaseInstanceQuery();

    /**
     * 创建案例执行查询构建器
     *
     * @return 案例执行查询构建器
     */
    CaseExecutionQuery createCaseExecutionQuery();

    // ========================================================================
    // 六、案例变量管理
    // ========================================================================

    /**
     * 获取案例变量值（从当前及父作用域查找）
     *
     * @param caseExecutionId 案例执行ID
     * @param variableName    变量名称
     * @return 变量值，不存在时返回null
     */
    Object getVariable(String caseExecutionId, String variableName);

    /**
     * 获取案例变量值（带类型信息）
     *
     * @param caseExecutionId 案例执行ID
     * @param variableName    变量名称
     * @param <T>             变量类型
     * @return 带类型信息的变量值
     */
    <T extends TypedValue> T getVariableTyped(String caseExecutionId, String variableName);

    /**
     * 获取案例变量值（带类型信息，可控制是否反序列化）
     *
     * @param caseExecutionId  案例执行ID
     * @param variableName     变量名称
     * @param deserializeValue 是否反序列化
     * @param <T>              变量类型
     * @return 带类型信息的变量值
     */
    <T extends TypedValue> T getVariableTyped(String caseExecutionId, String variableName, boolean deserializeValue);

    /**
     * 获取案例局部变量值（仅从当前作用域查找）
     *
     * @param caseExecutionId 案例执行ID
     * @param variableName    变量名称
     * @return 变量值，不存在时返回null
     */
    Object getVariableLocal(String caseExecutionId, String variableName);

    /**
     * 获取案例局部变量值（带类型信息）
     *
     * @param caseExecutionId 案例执行ID
     * @param variableName    变量名称
     * @param <T>             变量类型
     * @return 带类型信息的局部变量值
     */
    <T extends TypedValue> T getVariableLocalTyped(String caseExecutionId, String variableName);

    /**
     * 获取案例局部变量值（带类型信息，可控制是否反序列化）
     *
     * @param caseExecutionId  案例执行ID
     * @param variableName     变量名称
     * @param deserializeValue 是否反序列化
     * @param <T>              变量类型
     * @return 带类型信息的局部变量值
     */
    <T extends TypedValue> T getVariableLocalTyped(String caseExecutionId, String variableName, boolean deserializeValue);

    /**
     * 获取所有案例变量（从当前及父作用域查找）
     *
     * @param caseExecutionId 案例执行ID
     * @return 所有变量Map
     */
    Map<String, Object> getVariables(String caseExecutionId);

    /**
     * 获取所有案例变量（带类型信息）
     *
     * @param caseExecutionId 案例执行ID
     * @return 带类型信息的变量Map
     */
    VariableMap getVariablesTyped(String caseExecutionId);

    /**
     * 获取所有案例变量（带类型信息，可控制是否反序列化）
     *
     * @param caseExecutionId   案例执行ID
     * @param deserializeValues 是否反序列化
     * @return 带类型信息的变量Map
     */
    VariableMap getVariablesTyped(String caseExecutionId, boolean deserializeValues);

    /**
     * 获取指定名称的案例变量（从当前及父作用域查找）
     *
     * @param caseExecutionId 案例执行ID
     * @param variableNames   变量名称集合
     * @return 匹配的变量Map
     */
    Map<String, Object> getVariables(String caseExecutionId, Collection<String> variableNames);

    /**
     * 获取指定名称的案例变量（带类型信息）
     *
     * @param caseExecutionId   案例执行ID
     * @param variableNames     变量名称集合
     * @param deserializeValues 是否反序列化
     * @return 带类型信息的变量Map
     */
    VariableMap getVariablesTyped(String caseExecutionId, Collection<String> variableNames, boolean deserializeValues);

    /**
     * 获取所有案例局部变量（仅从当前作用域查找）
     *
     * @param caseExecutionId 案例执行ID
     * @return 所有局部变量Map
     */
    Map<String, Object> getVariablesLocal(String caseExecutionId);

    /**
     * 获取所有案例局部变量（带类型信息）
     *
     * @param caseExecutionId 案例执行ID
     * @return 带类型信息的局部变量Map
     */
    VariableMap getVariablesLocalTyped(String caseExecutionId);

    /**
     * 获取所有案例局部变量（带类型信息，可控制是否反序列化）
     *
     * @param caseExecutionId   案例执行ID
     * @param deserializeValues 是否反序列化
     * @return 带类型信息的局部变量Map
     */
    VariableMap getVariablesLocalTyped(String caseExecutionId, boolean deserializeValues);

    /**
     * 获取指定名称的案例局部变量（仅从当前作用域查找）
     *
     * @param caseExecutionId 案例执行ID
     * @param variableNames   变量名称集合
     * @return 匹配的局部变量Map
     */
    Map<String, Object> getVariablesLocal(String caseExecutionId, Collection<String> variableNames);

    /**
     * 获取指定名称的案例局部变量（带类型信息）
     *
     * @param caseExecutionId   案例执行ID
     * @param variableNames     变量名称集合
     * @param deserializeValues 是否反序列化
     * @return 带类型信息的局部变量Map
     */
    VariableMap getVariablesLocalTyped(String caseExecutionId, Collection<String> variableNames, boolean deserializeValues);

    /**
     * 设置案例变量（在当前及父作用域）
     *
     * @param caseExecutionId 案例执行ID
     * @param variableName    变量名称
     * @param value           变量值
     */
    void setVariable(String caseExecutionId, String variableName, Object value);

    /**
     * 设置案例局部变量（仅当前作用域）
     *
     * @param caseExecutionId 案例执行ID
     * @param variableName    变量名称
     * @param value           变量值
     */
    void setVariableLocal(String caseExecutionId, String variableName, Object value);

    /**
     * 批量设置案例变量
     *
     * @param caseExecutionId 案例执行ID
     * @param variables       变量Map
     */
    void setVariables(String caseExecutionId, Map<String, Object> variables);

    /**
     * 批量设置案例局部变量
     *
     * @param caseExecutionId 案例执行ID
     * @param variables       变量Map
     */
    void setVariablesLocal(String caseExecutionId, Map<String, Object> variables);

    /**
     * 删除案例变量
     *
     * @param caseExecutionId 案例执行ID
     * @param variableName    变量名称
     */
    void removeVariable(String caseExecutionId, String variableName);

    /**
     * 删除案例局部变量
     *
     * @param caseExecutionId 案例执行ID
     * @param variableName    变量名称
     */
    void removeVariableLocal(String caseExecutionId, String variableName);

    /**
     * 批量删除案例变量
     *
     * @param caseExecutionId 案例执行ID
     * @param variableNames   变量名称集合
     */
    void removeVariables(String caseExecutionId, Collection<String> variableNames);

    /**
     * 批量删除案例局部变量
     *
     * @param caseExecutionId 案例执行ID
     * @param variableNames   变量名称集合
     */
    void removeVariablesLocal(String caseExecutionId, Collection<String> variableNames);

    // ========================================================================
    // 七、案例查询（便捷方法）
    // ========================================================================

    /**
     * 根据业务Key查询案例实例列表
     *
     * @param businessKey 业务Key
     * @return 案例实例列表
     */
    List<CaseInstance> getCaseInstancesByBusinessKey(String businessKey);

    /**
     * 查询用户参与的案例实例
     *
     * @param userId 用户ID
     * @return 案例实例列表
     */
    List<CaseInstance> getCaseInstancesByUser(String userId);
}