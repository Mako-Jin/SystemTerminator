package com.yaocode.sts.components.flow.core.engine.runtime;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 运行时服务接口
 * <p>
 * 管理流程实例的生命周期和运行时数据，包括：
 * <ul>
 *   <li>流程实例的启动、查询、挂起、激活、终止和删除</li>
 *   <li>流程变量的设置、获取和删除</li>
 *   <li>消息事件和信号事件的触发</li>
 *   <li>执行实例的操作和控制</li>
 *   <li>批量操作（删除、设置变量等）</li>
 *   <li>流程实例修改、迁移和重启</li>
 *   <li>异常事件（Incident）管理</li>
 * </ul>
 *
 * @author Process Engine Team
 * @version 1.0.0
 */
public interface RuntimeService {

    // ========================================================================
    // 一、流程实例启动
    // ========================================================================

    /**
     * 根据流程定义Key启动流程实例
     *
     * @param processDefinitionKey 流程定义Key（不能为空）
     * @return 新创建的流程实例
     * @throws IllegalArgumentException 如果流程定义Key为空或流程定义不存在
     */
    ProcessInstance startProcessInstanceByKey(String processDefinitionKey);

    /**
     * 根据流程定义Key启动流程实例，指定业务Key
     *
     * @param processDefinitionKey 流程定义Key
     * @param businessKey          业务Key（用于关联业务数据，可为空）
     * @return 新创建的流程实例
     */
    ProcessInstance startProcessInstanceByKey(String processDefinitionKey, String businessKey);

    /**
     * 根据流程定义Key启动流程实例，指定业务Key和案例实例ID
     *
     * @param processDefinitionKey 流程定义Key
     * @param businessKey          业务Key
     * @param caseInstanceId       关联的案例实例ID（可为空）
     * @return 新创建的流程实例
     */
    ProcessInstance startProcessInstanceByKey(String processDefinitionKey, String businessKey, String caseInstanceId);

    /**
     * 根据流程定义Key启动流程实例，携带流程变量
     *
     * @param processDefinitionKey 流程定义Key
     * @param variables            启动时携带的流程变量（可为空）
     * @return 新创建的流程实例
     */
    ProcessInstance startProcessInstanceByKey(String processDefinitionKey, Map<String, Object> variables);

    /**
     * 根据流程定义Key启动流程实例，指定业务Key并携带流程变量
     *
     * @param processDefinitionKey 流程定义Key
     * @param businessKey          业务Key
     * @param variables            启动时携带的流程变量
     * @return 新创建的流程实例
     */
    ProcessInstance startProcessInstanceByKey(String processDefinitionKey, String businessKey, Map<String, Object> variables);

    /**
     * 根据流程定义Key启动流程实例，指定业务Key、案例实例ID并携带流程变量
     *
     * @param processDefinitionKey 流程定义Key
     * @param businessKey          业务Key
     * @param caseInstanceId       关联的案例实例ID
     * @param variables            启动时携带的流程变量
     * @return 新创建的流程实例
     */
    ProcessInstance startProcessInstanceByKey(String processDefinitionKey, String businessKey, String caseInstanceId, Map<String, Object> variables);

    /**
     * 根据流程定义ID启动流程实例
     *
     * @param processDefinitionId 流程定义ID（不能为空）
     * @return 新创建的流程实例
     */
    ProcessInstance startProcessInstanceById(String processDefinitionId);

    /**
     * 根据流程定义ID启动流程实例，指定业务Key
     *
     * @param processDefinitionId 流程定义ID
     * @param businessKey         业务Key
     * @return 新创建的流程实例
     */
    ProcessInstance startProcessInstanceById(String processDefinitionId, String businessKey);

    /**
     * 根据流程定义ID启动流程实例，指定业务Key和案例实例ID
     *
     * @param processDefinitionId 流程定义ID
     * @param businessKey         业务Key
     * @param caseInstanceId      关联的案例实例ID
     * @return 新创建的流程实例
     */
    ProcessInstance startProcessInstanceById(String processDefinitionId, String businessKey, String caseInstanceId);

    /**
     * 根据流程定义ID启动流程实例，携带流程变量
     *
     * @param processDefinitionId 流程定义ID
     * @param variables           启动时携带的流程变量
     * @return 新创建的流程实例
     */
    ProcessInstance startProcessInstanceById(String processDefinitionId, Map<String, Object> variables);

    /**
     * 根据流程定义ID启动流程实例，指定业务Key并携带流程变量
     *
     * @param processDefinitionId 流程定义ID
     * @param businessKey         业务Key
     * @param variables           启动时携带的流程变量
     * @return 新创建的流程实例
     */
    ProcessInstance startProcessInstanceById(String processDefinitionId, String businessKey, Map<String, Object> variables);

    /**
     * 根据流程定义ID启动流程实例，指定业务Key、案例实例ID并携带流程变量
     *
     * @param processDefinitionId 流程定义ID
     * @param businessKey         业务Key
     * @param caseInstanceId      关联的案例实例ID
     * @param variables           启动时携带的流程变量
     * @return 新创建的流程实例
     */
    ProcessInstance startProcessInstanceById(String processDefinitionId, String businessKey, String caseInstanceId, Map<String, Object> variables);

    /**
     * 根据消息名称启动流程实例
     * <p>
     * 触发消息启动事件，匹配对应的消息启动流程定义
     *
     * @param messageName 消息名称（不能为空）
     * @return 新创建的流程实例
     */
    ProcessInstance startProcessInstanceByMessage(String messageName);

    /**
     * 根据消息名称启动流程实例，指定业务Key
     *
     * @param messageName 消息名称
     * @param businessKey 业务Key
     * @return 新创建的流程实例
     */
    ProcessInstance startProcessInstanceByMessage(String messageName, String businessKey);

    /**
     * 根据消息名称启动流程实例，携带流程变量
     *
     * @param messageName        消息名称
     * @param processVariables   启动时携带的流程变量
     * @return 新创建的流程实例
     */
    ProcessInstance startProcessInstanceByMessage(String messageName, Map<String, Object> processVariables);

    /**
     * 根据消息名称启动流程实例，指定业务Key并携带流程变量
     *
     * @param messageName        消息名称
     * @param businessKey        业务Key
     * @param processVariables   启动时携带的流程变量
     * @return 新创建的流程实例
     */
    ProcessInstance startProcessInstanceByMessage(String messageName, String businessKey, Map<String, Object> processVariables);

    /**
     * 根据消息名称和流程定义ID启动流程实例
     *
     * @param messageName          消息名称
     * @param processDefinitionId  流程定义ID
     * @return 新创建的流程实例
     */
    ProcessInstance startProcessInstanceByMessageAndProcessDefinitionId(String messageName, String processDefinitionId);

    /**
     * 根据消息名称和流程定义ID启动流程实例，指定业务Key
     *
     * @param messageName          消息名称
     * @param processDefinitionId  流程定义ID
     * @param businessKey          业务Key
     * @return 新创建的流程实例
     */
    ProcessInstance startProcessInstanceByMessageAndProcessDefinitionId(String messageName, String processDefinitionId, String businessKey);

    /**
     * 根据消息名称和流程定义ID启动流程实例，携带流程变量
     *
     * @param messageName          消息名称
     * @param processDefinitionId  流程定义ID
     * @param processVariables     启动时携带的流程变量
     * @return 新创建的流程实例
     */
    ProcessInstance startProcessInstanceByMessageAndProcessDefinitionId(String messageName, String processDefinitionId, Map<String, Object> processVariables);

    /**
     * 根据消息名称和流程定义ID启动流程实例，指定业务Key并携带流程变量
     *
     * @param messageName          消息名称
     * @param processDefinitionId  流程定义ID
     * @param businessKey          业务Key
     * @param processVariables     启动时携带的流程变量
     * @return 新创建的流程实例
     */
    ProcessInstance startProcessInstanceByMessageAndProcessDefinitionId(String messageName, String processDefinitionId, String businessKey, Map<String, Object> processVariables);

    /**
     * 创建流程实例构建器（通过流程定义ID）
     * <p>
     * 用于更灵活的流程实例启动配置
     *
     * @param processDefinitionId 流程定义ID
     * @return 流程实例化构建器
     */
    ProcessInstantiationBuilder createProcessInstanceById(String processDefinitionId);

    /**
     * 创建流程实例构建器（通过流程定义Key）
     *
     * @param processDefinitionKey 流程定义Key
     * @return 流程实例化构建器
     */
    ProcessInstantiationBuilder createProcessInstanceByKey(String processDefinitionKey);

    // ========================================================================
    // 二、流程实例查询
    // ========================================================================

    /**
     * 根据流程实例ID查询流程实例
     *
     * @param processInstanceId 流程实例ID（不能为空）
     * @return 流程实例对象，不存在时返回null
     */
    ProcessInstance getProcessInstance(String processInstanceId);

    /**
     * 获取指定执行实例的当前活动节点ID列表
     *
     * @param executionId 执行实例ID
     * @return 活动节点ID列表
     */
    List<String> getActiveActivityIds(String executionId);

    /**
     * 获取流程实例的活动实例树结构
     *
     * @param processInstanceId 流程实例ID
     * @return 活动实例树根节点
     */
    ActivityInstance getActivityInstance(String processInstanceId);

    /**
     * 查询所有活跃（运行中）的流程实例
     *
     * @return 活跃流程实例列表
     */
    List<ProcessInstance> getActiveProcessInstances();

    /**
     * 根据业务Key查询流程实例列表
     *
     * @param businessKey 业务Key
     * @return 匹配的流程实例列表
     */
    List<ProcessInstance> getProcessInstancesByBusinessKey(String businessKey);

    // ========================================================================
    // 三、流程实例控制（挂起/激活/终止/删除）
    // ========================================================================

    /**
     * 挂起指定流程实例
     * <p>
     * 挂起后，该流程实例将暂停执行，直到被激活
     *
     * @param processInstanceId 流程实例ID
     */
    void suspendProcessInstance(String processInstanceId);

    /**
     * 激活指定流程实例
     * <p>
     * 将已挂起的流程实例恢复为运行状态
     *
     * @param processInstanceId 流程实例ID
     */
    void activateProcessInstance(String processInstanceId);

    /**
     * 终止指定流程实例
     * <p>
     * 强制结束流程实例的执行
     *
     * @param processInstanceId 流程实例ID
     */
    void terminateProcessInstance(String processInstanceId);

    /**
     * 删除流程实例（硬删除）
     * <p>
     * 永久删除流程实例及其所有运行时数据
     *
     * @param processInstanceId 流程实例ID
     * @param deleteReason      删除原因（可为空）
     */
    void deleteProcessInstance(String processInstanceId, String deleteReason);

    /**
     * 删除流程实例，指定是否跳过自定义监听器
     *
     * @param processInstanceId  流程实例ID
     * @param deleteReason       删除原因
     * @param skipCustomListeners 是否跳过自定义监听器
     */
    void deleteProcessInstance(String processInstanceId, String deleteReason, boolean skipCustomListeners);

    /**
     * 删除流程实例，指定是否跳过监听器和外部终止标识
     *
     * @param processInstanceId     流程实例ID
     * @param deleteReason          删除原因
     * @param skipCustomListeners   是否跳过自定义监听器
     * @param externallyTerminated  是否由外部终止
     */
    void deleteProcessInstance(String processInstanceId, String deleteReason, boolean skipCustomListeners, boolean externallyTerminated);

    /**
     * 删除流程实例，完整参数
     *
     * @param processInstanceId     流程实例ID
     * @param deleteReason          删除原因
     * @param skipCustomListeners   是否跳过自定义监听器
     * @param externallyTerminated  是否由外部终止
     * @param skipIoMappings        是否跳过IO映射
     */
    void deleteProcessInstance(String processInstanceId, String deleteReason, boolean skipCustomListeners, boolean externallyTerminated, boolean skipIoMappings);

    /**
     * 删除流程实例，完整参数（包含子流程处理）
     *
     * @param processInstanceId     流程实例ID
     * @param deleteReason          删除原因
     * @param skipCustomListeners   是否跳过自定义监听器
     * @param externallyTerminated  是否由外部终止
     * @param skipIoMappings        是否跳过IO映射
     * @param skipSubprocesses      是否跳过子流程
     */
    void deleteProcessInstance(String processInstanceId, String deleteReason, boolean skipCustomListeners, boolean externallyTerminated, boolean skipIoMappings,
                               boolean skipSubprocesses);

    /**
     * 删除流程实例（如果存在），完整参数
     *
     * @param processInstanceId     流程实例ID
     * @param deleteReason          删除原因
     * @param skipCustomListeners   是否跳过自定义监听器
     * @param externallyTerminated  是否由外部终止
     * @param skipIoMappings        是否跳过IO映射
     * @param skipSubprocesses      是否跳过子流程
     */
    void deleteProcessInstanceIfExists(String processInstanceId, String deleteReason, boolean skipCustomListeners, boolean externallyTerminated, boolean skipIoMappings,
                                       boolean skipSubprocesses);

    /**
     * 批量删除流程实例
     *
     * @param processInstanceIds    流程实例ID列表
     * @param deleteReason          删除原因
     * @param skipCustomListeners   是否跳过自定义监听器
     * @param externallyTerminated  是否由外部终止
     */
    void deleteProcessInstances(List<String> processInstanceIds, String deleteReason, boolean skipCustomListeners, boolean externallyTerminated);

    /**
     * 批量删除流程实例，包含子流程处理
     *
     * @param processInstanceIds    流程实例ID列表
     * @param deleteReason          删除原因
     * @param skipCustomListeners   是否跳过自定义监听器
     * @param externallyTerminated  是否由外部终止
     * @param skipSubprocesses      是否跳过子流程
     */
    void deleteProcessInstances(List<String> processInstanceIds, String deleteReason, boolean skipCustomListeners, boolean externallyTerminated,
                                boolean skipSubprocesses);

    /**
     * 批量删除流程实例，完整参数
     *
     * @param processInstanceIds    流程实例ID列表
     * @param deleteReason          删除原因
     * @param skipCustomListeners   是否跳过自定义监听器
     * @param externallyTerminated  是否由外部终止
     * @param skipSubprocesses      是否跳过子流程
     * @param skipIoMappings        是否跳过IO映射
     */
    void deleteProcessInstances(List<String> processInstanceIds, String deleteReason, boolean skipCustomListeners, boolean externallyTerminated,
                                boolean skipSubprocesses, boolean skipIoMappings);

    /**
     * 批量删除流程实例（如果存在）
     *
     * @param processInstanceIds    流程实例ID列表
     * @param deleteReason          删除原因
     * @param skipCustomListeners   是否跳过自定义监听器
     * @param externallyTerminated  是否由外部终止
     * @param skipSubprocesses      是否跳过子流程
     */
    void deleteProcessInstancesIfExists(List<String> processInstanceIds, String deleteReason, boolean skipCustomListeners, boolean externallyTerminated,
                                        boolean skipSubprocesses);

    /**
     * 异步批量删除流程实例（返回Batch对象用于跟踪）
     *
     * @param processInstanceIds     流程实例ID列表
     * @param processInstanceQuery   流程实例查询条件（与processInstanceIds二选一）
     * @param deleteReason           删除原因
     * @return 批量操作对象
     */
    Batch deleteProcessInstancesAsync(List<String> processInstanceIds, ProcessInstanceQuery processInstanceQuery, String deleteReason);

    /**
     * 异步批量删除流程实例，指定是否跳过自定义监听器
     *
     * @param processInstanceIds     流程实例ID列表
     * @param processInstanceQuery   流程实例查询条件
     * @param deleteReason           删除原因
     * @param skipCustomListeners    是否跳过自定义监听器
     * @return 批量操作对象
     */
    Batch deleteProcessInstancesAsync(List<String> processInstanceIds, ProcessInstanceQuery processInstanceQuery, String deleteReason, boolean skipCustomListeners);

    /**
     * 异步批量删除流程实例，指定是否跳过子流程
     *
     * @param processInstanceIds     流程实例ID列表
     * @param processInstanceQuery   流程实例查询条件
     * @param deleteReason           删除原因
     * @param skipCustomListeners    是否跳过自定义监听器
     * @param skipSubprocesses       是否跳过子流程
     * @return 批量操作对象
     */
    Batch deleteProcessInstancesAsync(List<String> processInstanceIds, ProcessInstanceQuery processInstanceQuery, String deleteReason, boolean skipCustomListeners, boolean skipSubprocesses);

    /**
     * 异步批量删除流程实例，支持历史查询条件
     *
     * @param processInstanceIds              流程实例ID列表
     * @param processInstanceQuery            流程实例查询条件
     * @param historicProcessInstanceQuery    历史流程实例查询条件
     * @param deleteReason                    删除原因
     * @param skipCustomListeners             是否跳过自定义监听器
     * @param skipSubprocesses                是否跳过子流程
     * @return 批量操作对象
     */
    Batch deleteProcessInstancesAsync(List<String> processInstanceIds,
                                      ProcessInstanceQuery processInstanceQuery,
                                      HistoricProcessInstanceQuery historicProcessInstanceQuery,
                                      String deleteReason,
                                      boolean skipCustomListeners,
                                      boolean skipSubprocesses);

    /**
     * 异步批量删除流程实例，完整参数
     *
     * @param processInstanceIds              流程实例ID列表
     * @param processInstanceQuery            流程实例查询条件
     * @param historicProcessInstanceQuery    历史流程实例查询条件
     * @param deleteReason                    删除原因
     * @param skipCustomListeners             是否跳过自定义监听器
     * @param skipSubprocesses                是否跳过子流程
     * @param skipIoMappings                  是否跳过IO映射
     * @return 批量操作对象
     */
    Batch deleteProcessInstancesAsync(List<String> processInstanceIds,
                                      ProcessInstanceQuery processInstanceQuery,
                                      HistoricProcessInstanceQuery historicProcessInstanceQuery,
                                      String deleteReason,
                                      boolean skipCustomListeners,
                                      boolean skipSubprocesses,
                                      boolean skipIoMappings);

    /**
     * 异步批量删除流程实例（通过查询条件）
     *
     * @param processInstanceQuery  流程实例查询条件
     * @param deleteReason          删除原因
     * @return 批量操作对象
     */
    Batch deleteProcessInstancesAsync(ProcessInstanceQuery processInstanceQuery, String deleteReason);

    /**
     * 异步批量删除流程实例（通过ID列表）
     *
     * @param processInstanceIds  流程实例ID列表
     * @param deleteReason        删除原因
     * @return 批量操作对象
     */
    Batch deleteProcessInstancesAsync(List<String> processInstanceIds, String deleteReason);

    /**
     * 更新流程实例挂起状态的构建器
     * <p>
     * 用于灵活的流程实例/流程定义挂起、激活操作
     *
     * @return 挂起状态更新构建器
     */
    UpdateProcessInstanceSuspensionStateSelectBuilder updateProcessInstanceSuspensionState();

    /**
     * 挂起指定ID的流程实例（已废弃，建议使用 suspendProcessInstance）
     *
     * @param processInstanceId 流程实例ID
     * @deprecated 请使用 {@link #suspendProcessInstance(String)}
     */
    @Deprecated
    void suspendProcessInstanceById(String processInstanceId);

    /**
     * 挂起指定流程定义的所有流程实例
     *
     * @param processDefinitionId 流程定义ID
     */
    void suspendProcessInstanceByProcessDefinitionId(String processDefinitionId);

    /**
     * 挂起指定流程定义Key的所有流程实例
     *
     * @param processDefinitionKey 流程定义Key
     */
    void suspendProcessInstanceByProcessDefinitionKey(String processDefinitionKey);

    /**
     * 激活指定ID的流程实例（已废弃，建议使用 activateProcessInstance）
     *
     * @param processInstanceId 流程实例ID
     * @deprecated 请使用 {@link #activateProcessInstance(String)}
     */
    @Deprecated
    void activateProcessInstanceById(String processInstanceId);

    /**
     * 激活指定流程定义的所有流程实例
     *
     * @param processDefinitionId 流程定义ID
     */
    void activateProcessInstanceByProcessDefinitionId(String processDefinitionId);

    /**
     * 激活指定流程定义Key的所有流程实例
     *
     * @param processDefinitionKey 流程定义Key
     */
    void activateProcessInstanceByProcessDefinitionKey(String processDefinitionKey);

    // ========================================================================
    // 四、流程变量管理
    // ========================================================================

    /**
     * 设置流程变量（全局变量）
     * <p>
     * 变量将存储在该执行实例及其所有父作用域中
     *
     * @param executionId   执行实例ID
     * @param variableName  变量名称（不能为空）
     * @param value         变量值
     */
    void setVariable(String executionId, String variableName, Object value);

    /**
     * 设置流程局部变量
     * <p>
     * 变量仅存储在该执行实例中，不影响父作用域
     *
     * @param executionId   执行实例ID
     * @param variableName  变量名称
     * @param value         变量值
     */
    void setVariableLocal(String executionId, String variableName, Object value);

    /**
     * 批量设置流程变量（全局变量）
     *
     * @param executionId  执行实例ID
     * @param variables    变量Map
     */
    void setVariables(String executionId, Map<String, Object> variables);

    /**
     * 批量设置流程局部变量
     *
     * @param executionId  执行实例ID
     * @param variables    变量Map
     */
    void setVariablesLocal(String executionId, Map<String, Object> variables);

    /**
     * 异步批量设置流程变量
     *
     * @param processInstanceIds  流程实例ID列表
     * @param variables           变量Map
     * @return 批量操作对象
     */
    Batch setVariablesAsync(List<String> processInstanceIds, Map<String, ?> variables);

    /**
     * 异步批量设置流程变量（通过查询条件）
     *
     * @param processInstanceQuery  流程实例查询条件
     * @param variables             变量Map
     * @return 批量操作对象
     */
    Batch setVariablesAsync(ProcessInstanceQuery processInstanceQuery, Map<String, ?> variables);

    /**
     * 异步批量设置流程变量（通过历史查询条件）
     *
     * @param historicProcessInstanceQuery  历史流程实例查询条件
     * @param variables                     变量Map
     * @return 批量操作对象
     */
    Batch setVariablesAsync(HistoricProcessInstanceQuery historicProcessInstanceQuery, Map<String, ?> variables);

    /**
     * 异步批量设置流程变量，完整参数
     *
     * @param processInstanceIds              流程实例ID列表
     * @param processInstanceQuery            流程实例查询条件
     * @param historicProcessInstanceQuery    历史流程实例查询条件
     * @param variables                       变量Map
     * @return 批量操作对象
     */
    Batch setVariablesAsync(List<String> processInstanceIds,
                            ProcessInstanceQuery processInstanceQuery,
                            HistoricProcessInstanceQuery historicProcessInstanceQuery,
                            Map<String, ?> variables);

    /**
     * 获取流程变量值（全局变量）
     * <p>
     * 从执行实例及其父作用域中查找变量
     *
     * @param executionId   执行实例ID
     * @param variableName  变量名称
     * @return 变量值，不存在时返回null
     */
    Object getVariable(String executionId, String variableName);

    /**
     * 获取流程局部变量值
     * <p>
     * 仅从当前执行实例中查找变量
     *
     * @param executionId   执行实例ID
     * @param variableName  变量名称
     * @return 变量值，不存在时返回null
     */
    Object getVariableLocal(String executionId, String variableName);

    /**
     * 获取所有流程变量（全局变量）
     *
     * @param processInstanceId  流程实例ID（通常使用执行实例ID）
     * @return 所有变量的Map
     */
    Map<String, Object> getVariables(String processInstanceId);

    /**
     * 获取指定名称的流程变量
     *
     * @param executionId    执行实例ID
     * @param variableNames  需要获取的变量名称集合
     * @return 匹配的变量Map
     */
    Map<String, Object> getVariables(String executionId, Collection<String> variableNames);

    /**
     * 获取所有流程变量（带类型信息）
     *
     * @param executionId  执行实例ID
     * @return 带有类型信息的变量Map
     */
    VariableMap getVariablesTyped(String executionId);

    /**
     * 获取所有流程变量（带类型信息，可控制是否反序列化）
     *
     * @param executionId         执行实例ID
     * @param deserializeValues   是否反序列化复杂对象
     * @return 带有类型信息的变量Map
     */
    VariableMap getVariablesTyped(String executionId, boolean deserializeValues);

    /**
     * 获取指定名称的流程变量（带类型信息）
     *
     * @param executionId         执行实例ID
     * @param variableNames       需要获取的变量名称集合
     * @param deserializeValues   是否反序列化复杂对象
     * @return 带有类型信息的变量Map
     */
    VariableMap getVariablesTyped(String executionId, Collection<String> variableNames, boolean deserializeValues);

    /**
     * 获取流程变量（带类型信息）
     *
     * @param executionId   执行实例ID
     * @param variableName  变量名称
     * @param <T>           变量类型
     * @return 带有类型信息的变量值
     */
    <T extends TypedValue> T getVariableTyped(String executionId, String variableName);

    /**
     * 获取流程变量（带类型信息，可控制是否反序列化）
     *
     * @param executionId         执行实例ID
     * @param variableName        变量名称
     * @param deserializeValue    是否反序列化
     * @param <T>                 变量类型
     * @return 带有类型信息的变量值
     */
    <T extends TypedValue> T getVariableTyped(String executionId, String variableName, boolean deserializeValue);

    /**
     * 获取所有流程局部变量
     *
     * @param executionId  执行实例ID
     * @return 局部变量Map
     */
    Map<String, Object> getVariablesLocal(String executionId);

    /**
     * 获取指定名称的流程局部变量
     *
     * @param executionId    执行实例ID
     * @param variableNames  需要获取的变量名称集合
     * @return 匹配的局部变量Map
     */
    Map<String, Object> getVariablesLocal(String executionId, Collection<String> variableNames);

    /**
     * 获取所有流程局部变量（带类型信息）
     *
     * @param executionId  执行实例ID
     * @return 带有类型信息的局部变量Map
     */
    VariableMap getVariablesLocalTyped(String executionId);

    /**
     * 获取所有流程局部变量（带类型信息，可控制是否反序列化）
     *
     * @param executionId         执行实例ID
     * @param deserializeValues   是否反序列化
     * @return 带有类型信息的局部变量Map
     */
    VariableMap getVariablesLocalTyped(String executionId, boolean deserializeValues);

    /**
     * 获取指定名称的流程局部变量（带类型信息）
     *
     * @param executionId         执行实例ID
     * @param variableNames       需要获取的变量名称集合
     * @param deserializeValues   是否反序列化
     * @return 带有类型信息的局部变量Map
     */
    VariableMap getVariablesLocalTyped(String executionId, Collection<String> variableNames, boolean deserializeValues);

    /**
     * 获取流程局部变量（带类型信息）
     *
     * @param executionId   执行实例ID
     * @param variableName  变量名称
     * @param <T>           变量类型
     * @return 带有类型信息的局部变量值
     */
    <T extends TypedValue> T getVariableLocalTyped(String executionId, String variableName);

    /**
     * 获取流程局部变量（带类型信息，可控制是否反序列化）
     *
     * @param executionId         执行实例ID
     * @param variableName        变量名称
     * @param deserializeValue    是否反序列化
     * @param <T>                 变量类型
     * @return 带有类型信息的局部变量值
     */
    <T extends TypedValue> T getVariableLocalTyped(String executionId, String variableName, boolean deserializeValue);

    /**
     * 删除流程变量（全局变量）
     *
     * @param executionId   执行实例ID
     * @param variableName  变量名称
     */
    void removeVariable(String executionId, String variableName);

    /**
     * 删除流程局部变量
     *
     * @param executionId   执行实例ID
     * @param variableName  变量名称
     */
    void removeVariableLocal(String executionId, String variableName);

    /**
     * 批量删除流程变量
     *
     * @param executionId    执行实例ID
     * @param variableNames  需要删除的变量名称集合
     */
    void removeVariables(String executionId, Collection<String> variableNames);

    /**
     * 批量删除流程局部变量
     *
     * @param executionId    执行实例ID
     * @param variableNames  需要删除的变量名称集合
     */
    void removeVariablesLocal(String executionId, Collection<String> variableNames);

    // ========================================================================
    // 五、消息事件处理
    // ========================================================================

    /**
     * 触发消息事件
     * <p>
     * 向流程引擎发送一个全局消息，匹配的消息订阅将被触发
     *
     * @param messageName  消息名称
     * @param variables    消息携带的变量
     */
    void triggerMessage(String messageName, Map<String, Object> variables);

    /**
     * 向指定执行实例发送消息事件
     *
     * @param messageName  消息名称
     * @param executionId  执行实例ID
     */
    void messageEventReceived(String messageName, String executionId);

    /**
     * 向指定执行实例发送消息事件，携带变量
     *
     * @param messageName        消息名称
     * @param executionId        执行实例ID
     * @param processVariables   消息携带的变量
     */
    void messageEventReceived(String messageName, String executionId, Map<String, Object> processVariables);

    /**
     * 创建消息关联构建器
     * <p>
     * 用于通过消息关联流程实例
     *
     * @param messageName  消息名称
     * @return 消息关联构建器
     */
    MessageCorrelationBuilder createMessageCorrelation(String messageName);

    /**
     * 创建异步消息关联构建器
     *
     * @param messageName  消息名称
     * @return 异步消息关联构建器
     */
    MessageCorrelationAsyncBuilder createMessageCorrelationAsync(String messageName);

    /**
     * 关联消息（简单形式）
     *
     * @param messageName  消息名称
     */
    void correlateMessage(String messageName);

    /**
     * 关联消息，通过业务Key
     *
     * @param messageName  消息名称
     * @param businessKey  业务Key
     */
    void correlateMessage(String messageName, String businessKey);

    /**
     * 关联消息，通过关联Key
     *
     * @param messageName       消息名称
     * @param correlationKeys   关联Key Map
     */
    void correlateMessage(String messageName, Map<String, Object> correlationKeys);

    /**
     * 关联消息，通过业务Key并携带流程变量
     *
     * @param messageName        消息名称
     * @param businessKey        业务Key
     * @param processVariables   流程变量
     */
    void correlateMessage(String messageName, String businessKey, Map<String, Object> processVariables);

    /**
     * 关联消息，通过关联Key并携带流程变量
     *
     * @param messageName        消息名称
     * @param correlationKeys    关联Key Map
     * @param processVariables   流程变量
     */
    void correlateMessage(String messageName, Map<String, Object> correlationKeys, Map<String, Object> processVariables);

    /**
     * 关联消息，完整参数
     *
     * @param messageName        消息名称
     * @param businessKey        业务Key
     * @param correlationKeys    关联Key Map
     * @param processVariables   流程变量
     */
    void correlateMessage(String messageName, String businessKey, Map<String, Object> correlationKeys, Map<String, Object> processVariables);

    // ========================================================================
    // 六、信号事件处理
    // ========================================================================

    /**
     * 触发信号事件（全局信号）
     *
     * @param signalName  信号名称
     */
    void signalEventReceived(String signalName);

    /**
     * 触发信号事件，携带流程变量
     *
     * @param signalName         信号名称
     * @param processVariables   信号携带的变量
     */
    void signalEventReceived(String signalName, Map<String, Object> processVariables);

    /**
     * 向指定执行实例触发信号事件
     *
     * @param signalName    信号名称
     * @param executionId   执行实例ID
     */
    void signalEventReceived(String signalName, String executionId);

    /**
     * 触发信号事件
     *
     * @param signalName  信号名称
     * @param variables   信号携带的变量
     */
    void triggerSignal(String signalName, Map<String, Object> variables);

    /**
     * 触发信号事件（指定流程实例）
     *
     * @param signalName          信号名称
     * @param processInstanceId   流程实例ID
     * @param variables           信号携带的变量
     */
    void triggerSignal(String signalName, String processInstanceId, Map<String, Object> variables);

    /**
     * 创建信号事件构建器
     *
     * @param signalName  信号名称
     * @return 信号事件构建器
     */
    SignalEventReceivedBuilder createSignalEvent(String signalName);

    // ========================================================================
    // 七、执行实例操作
    // ========================================================================

    /**
     * 获取执行实例
     *
     * @param executionId  执行实例ID
     * @return 执行实例对象
     */
    Execution getExecution(String executionId);

    /**
     * 触发执行实例继续执行
     *
     * @param executionId  执行实例ID
     */
    void triggerExecution(String executionId);

    /**
     * 触发执行实例继续执行，携带变量
     *
     * @param executionId  执行实例ID
     * @param variables    传递的变量
     */
    void triggerExecution(String executionId, Map<String, Object> variables);

    /**
     * 向执行实例发送信号（使其继续执行）
     *
     * @param executionId  执行实例ID
     */
    void signal(String executionId);

    /**
     * 向执行实例发送信号，携带信号数据
     *
     * @param executionId         执行实例ID
     * @param signalName          信号名称
     * @param signalData          信号数据
     * @param processVariables    流程变量
     */
    void signal(String executionId, String signalName, Object signalData, Map<String, Object> processVariables);

    /**
     * 向执行实例发送信号，携带变量
     *
     * @param executionId         执行实例ID
     * @param processVariables    流程变量
     */
    void signal(String executionId, Map<String, Object> processVariables);

    // ========================================================================
    // 八、流程实例修改与迁移
    // ========================================================================

    /**
     * 创建流程实例修改构建器
     * <p>
     * 用于动态修改运行中的流程实例（如跳转、取消等）
     *
     * @param processInstanceId  流程实例ID
     * @return 流程实例修改构建器
     */
    ProcessInstanceModificationBuilder createProcessInstanceModification(String processInstanceId);

    /**
     * 创建迁移计划构建器
     * <p>
     * 用于将流程实例从源流程定义迁移到目标流程定义
     *
     * @param sourceProcessDefinitionId  源流程定义ID
     * @param targetProcessDefinitionId  目标流程定义ID
     * @return 迁移计划构建器
     */
    MigrationPlanBuilder createMigrationPlan(String sourceProcessDefinitionId, String targetProcessDefinitionId);

    /**
     * 创建迁移执行构建器
     *
     * @param migrationPlan  迁移计划
     * @return 迁移执行构建器
     */
    MigrationPlanExecutionBuilder newMigration(MigrationPlan migrationPlan);

    /**
     * 创建修改构建器
     *
     * @param processDefinitionId  流程定义ID
     * @return 修改构建器
     */
    ModificationBuilder createModification(String processDefinitionId);

    /**
     * 重启流程实例构建器
     * <p>
     * 用于重新启动已结束的流程实例
     *
     * @param processDefinitionId  流程定义ID
     * @return 重启流程实例构建器
     */
    RestartProcessInstanceBuilder restartProcessInstances(String processDefinitionId);

    // ========================================================================
    // 九、异常事件管理（Incident）
    // ========================================================================

    /**
     * 创建异常事件
     *
     * @param incidentType    异常类型
     * @param executionId     执行实例ID
     * @param configuration   配置信息
     * @return 创建的异常事件
     */
    Incident createIncident(String incidentType, String executionId, String configuration);

    /**
     * 创建异常事件，带消息
     *
     * @param incidentType    异常类型
     * @param executionId     执行实例ID
     * @param configuration   配置信息
     * @param message         异常消息
     * @return 创建的异常事件
     */
    Incident createIncident(String incidentType, String executionId, String configuration, String message);

    /**
     * 解决异常事件
     *
     * @param incidentId  异常事件ID
     */
    void resolveIncident(String incidentId);

    /**
     * 为异常事件设置备注
     *
     * @param incidentId   异常事件ID
     * @param annotation   备注信息
     */
    void setAnnotationForIncidentById(String incidentId, String annotation);

    /**
     * 清除异常事件的备注
     *
     * @param incidentId  异常事件ID
     */
    void clearAnnotationForIncidentById(String incidentId);

    // ========================================================================
    // 十、条件评估
    // ========================================================================

    /**
     * 创建条件评估构建器
     * <p>
     * 用于评估流程中的条件表达式
     *
     * @return 条件评估构建器
     */
    ConditionEvaluationBuilder createConditionEvaluation();
}