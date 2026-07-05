package com.yaocode.sts.components.flow.core.executor;

import com.yaocode.sts.components.flow.core.engine.ProcessEngine;
import com.yaocode.sts.components.flow.core.exception.FlowException;
import com.yaocode.sts.components.flow.core.model.Execution;
import com.yaocode.sts.components.flow.core.model.NodeDefinition;
import com.yaocode.sts.components.flow.core.model.ProcessDefinition;
import com.yaocode.sts.components.flow.core.model.ProcessInstance;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.*;

/**
 * 流程执行器
 * 类似 Camunda 的 PvmExecution，负责执行流程的核心逻辑
 *
 * 参考 Camunda: org.camunda.bpm.engine.impl.pvm.runtime.ExecutionImpl
 *           org.camunda.bpm.engine.impl.pvm.process.ActivityImpl
 */
@Slf4j
@RequiredArgsConstructor
public class ProcessExecutor {

    private final ProcessEngine processEngine;

    /**
     * 执行流程（命令入口）
     */
    public void execute(CommandExecutor.Command<Object> command) {
        processEngine.getCommandExecutor().execute(command);
    }

    /**
     * 启动流程
     */
    public void startProcess(ProcessDefinition definition, Map<String, Object> variables, String startUserId) {
        log.info("启动流程: processKey={}, version={}", definition.getProcessKey(), definition.getVersion());

        // 1. 查找开始节点
        NodeDefinition startNode = findStartNode(definition);
        if (startNode == null) {
            throw new FlowException("流程定义缺少开始节点");
        }

        // 2. 创建流程实例
        ProcessInstance instance = createProcessInstance(definition, variables, startUserId);

        // 3. 创建执行路径
        Execution execution = createExecution(instance);

        // 4. 保存执行上下文
        ExecutionContext context = ExecutionContext.builder()
                .processEngine(processEngine)
                .processInstance(instance)
                .execution(execution)
                .currentNode(startNode)
                .variables(variables != null ? new HashMap<>(variables) : new HashMap<>())
                .build();

        // 5. 执行开始节点
        executeNode(context);

        log.info("流程启动成功: processInstanceId={}", instance.getProcessInstanceId());
    }

    /**
     * 执行下一个节点（任务完成后调用）
     */
    public void executeNextNode(ProcessInstance instance, Execution execution, Task completedTask) {
        log.info("执行下一个节点: processInstanceId={}, executionId={}, nodeKey={}",
                instance.getProcessInstanceId(), execution.getExecutionId(), execution.getNodeKey());

        // 1. 获取流程定义
        ProcessDefinition definition = processEngine.getProcessDefinitionRepository()
                .findById(instance.getProcessId());

        // 2. 获取当前节点定义
        NodeDefinition currentNode = findNodeByKey(definition, execution.getNodeKey());

        // 3. 获取出边
        List<SequenceDefinition> outgoing = findOutgoingSequences(definition, currentNode.getNodeKey());

        if (outgoing.isEmpty()) {
            // 没有出边，流程结束
            completeProcessInstance(instance);
            return;
        }

        // 4. 构建执行上下文
        Map<String, Object> variables = processEngine.getVariableRepository()
                .findByProcessInstanceId(instance.getProcessInstanceId());

        ExecutionContext context = ExecutionContext.builder()
                .processEngine(processEngine)
                .processInstance(instance)
                .execution(execution)
                .currentNode(currentNode)
                .variables(variables)
                .build();

        // 5. 执行出边
        for (SequenceDefinition sequence : outgoing) {
            // 如果是网关，需要评估条件
            if (isGatewayNode(currentNode)) {
                // 评估条件表达式
                if (!evaluateCondition(sequence.getConditionExpression(), context)) {
                    continue;
                }
            }
            executeSequence(context, sequence);
        }
    }

    /**
     * 执行节点
     */
    public void executeNode(ExecutionContext context) {
        NodeDefinition node = context.getCurrentNode();
        log.info("执行节点: nodeKey={}, nodeType={}, nodeName={}",
                node.getNodeKey(), node.getNodeType(), node.getNodeName());

        // 1. 获取节点处理器
        NodeHandler handler = NodeHandlerRegistry.getHandler(NodeType.fromCode(node.getNodeType()));
        if (handler == null) {
            throw new FlowException("未找到节点处理器: " + node.getNodeType());
        }

        // 2. 记录活动开始（用于历史）
        recordActivityStart(context);

        // 3. 执行节点（由具体处理器实现）
        handler.execute(context);

        // 4. 记录活动结束（用于历史）
        recordActivityEnd(context);

        // 5. 如果节点不是等待型（用户任务），继续执行下一个节点
        if (!handler.isWaitState()) {
            continueExecution(context);
        }
    }

    /**
     * 执行连线
     */
    public void executeSequence(ExecutionContext context, SequenceDefinition sequence) {
        log.info("执行连线: from={} -> to={}",
                sequence.getSourceNodeKey(), sequence.getTargetNodeKey());

        // 1. 更新执行路径的当前节点
        Execution execution = context.getExecution();
        execution.setNodeKey(sequence.getTargetNodeKey());

        // 2. 获取目标节点定义
        NodeDefinition targetNode = findNodeByKey(
                context.getProcessDefinition(),
                sequence.getTargetNodeKey()
        );

        // 3. 更新执行上下文
        context.setCurrentNode(targetNode);

        // 4. 执行目标节点
        executeNode(context);
    }

    /**
     * 继续执行（非等待型节点完成后）
     */
    private void continueExecution(ExecutionContext context) {
        // 获取当前节点的出边
        List<SequenceDefinition> outgoing = findOutgoingSequences(
                context.getProcessDefinition(),
                context.getCurrentNode().getNodeKey()
        );

        for (SequenceDefinition sequence : outgoing) {
            executeSequence(context, sequence);
        }
    }

    // ==================== 私有辅助方法 ====================

    /**
     * 查找开始节点
     */
    private NodeDefinition findStartNode(ProcessDefinition definition) {
        return definition.getNodes().stream()
                .filter(n -> n.getNodeType().equals(NodeType.START_EVENT.getCode())
                        || n.getNodeType().equals(NodeType.START_EVENT_TIMER.getCode())
                        || n.getNodeType().equals(NodeType.START_EVENT_MESSAGE.getCode())
                        || n.getNodeType().equals(NodeType.START_EVENT_SIGNAL.getCode()))
                .findFirst()
                .orElse(null);
    }

    /**
     * 根据节点KEY查找节点
     */
    private NodeDefinition findNodeByKey(ProcessDefinition definition, String nodeKey) {
        return definition.getNodes().stream()
                .filter(n -> n.getNodeKey().equals(nodeKey))
                .findFirst()
                .orElse(null);
    }

    /**
     * 查找出边
     */
    private List<SequenceDefinition> findOutgoingSequences(ProcessDefinition definition, String sourceNodeKey) {
        return definition.getSequences().stream()
                .filter(s -> s.getSourceNodeKey().equals(sourceNodeKey))
                .toList();
    }

    /**
     * 判断是否是网关节点
     */
    private boolean isGatewayNode(NodeDefinition node) {
        int type = node.getNodeType();
        return type >= NodeType.EXCLUSIVE_GATEWAY.getCode()
                && type <= NodeType.EVENT_BASED_GATEWAY.getCode();
    }

    /**
     * 评估条件表达式
     */
    private boolean evaluateCondition(String conditionExpression, ExecutionContext context) {
        if (conditionExpression == null || conditionExpression.trim().isEmpty()) {
            return true;
        }

        // 使用表达式引擎评估
        // 参考 Camunda: DelegateExecution
        return processEngine.getConfiguration().getExpressionResolver() != null
                ? (Boolean) processEngine.getConfiguration().getExpressionResolver()
                .resolve(conditionExpression, context)
                : true;
    }

    /**
     * 创建流程实例
     */
    private ProcessInstance createProcessInstance(ProcessDefinition definition,
                                                  Map<String, Object> variables,
                                                  String startUserId) {
        String instanceId = UUID.randomUUID().toString().replace("-", "");
        ProcessInstance instance = ProcessInstance.builder()
                .processInstanceId(instanceId)
                .businessKey((String) variables.getOrDefault("businessKey", null))
                .processId(definition.getProcessId())
                .processKey(definition.getProcessKey())
                .processVersion(definition.getVersion())
                .processName(definition.getName())
                .status(ProcessStatus.RUNNING.getCode())
                .suspensionState(1)
                .startTime(LocalDateTime.now())
                .startUserId(startUserId)
                .variables(variables)
                .build();

        // 保存流程实例（通过仓储）
        processEngine.getProcessInstanceRepository().save(instance);

        // 保存变量
        if (variables != null && !variables.isEmpty()) {
            processEngine.getVariableRepository().saveAll(
                    instanceId, variables
            );
        }

        return instance;
    }

    /**
     * 创建执行路径
     */
    private Execution createExecution(ProcessInstance instance) {
        String executionId = UUID.randomUUID().toString().replace("-", "");
        Execution execution = Execution.builder()
                .executionId(executionId)
                .processInstanceId(instance.getProcessInstanceId())
                .parentExecutionId(null)
                .superExecutionId(null)
                .nodeKey(null)
                .isActive(true)
                .isConcurrent(false)
                .isScope(false)
                .isEventScope(false)
                .suspensionState(1)
                .build();

        processEngine.getExecutionRepository().save(execution);
        return execution;
    }

    /**
     * 完成流程实例
     */
    private void completeProcessInstance(ProcessInstance instance) {
        instance.setStatus(ProcessStatus.COMPLETED.getCode());
        instance.setEndTime(LocalDateTime.now());
        instance.setDurationMilliseconds(
                System.currentTimeMillis() - instance.getStartTime()
                        .atZone(java.time.ZoneId.systemDefault())
                        .toInstant().toEpochMilli()
        );

        processEngine.getProcessInstanceRepository().updateStatus(instance);

        // 归档历史
        if (processEngine.getConfiguration().isHistoryEnabled()) {
            // 调用历史服务归档
            // processEngine.getHistoryService().archive(instance);
        }

        log.info("流程实例完成: processInstanceId={}", instance.getProcessInstanceId());
    }

    /**
     * 记录活动开始（用于历史）
     */
    private void recordActivityStart(ExecutionContext context) {
        if (!processEngine.getConfiguration().isHistoryEnabled()) {
            return;
        }
        // 由 HistoryService 实现
    }

    /**
     * 记录活动结束（用于历史）
     */
    private void recordActivityEnd(ExecutionContext context) {
        if (!processEngine.getConfiguration().isHistoryEnabled()) {
            return;
        }
        // 由 HistoryService 实现
    }
}
