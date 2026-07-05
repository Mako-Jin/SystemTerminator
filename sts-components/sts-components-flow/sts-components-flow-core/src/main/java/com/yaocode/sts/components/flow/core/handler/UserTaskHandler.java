package com.yaocode.sts.components.flow.core.handler;


import com.yaocode.sts.components.flow.core.executor.ExecutionContext;
import com.yaocode.sts.components.flow.core.model.NodeDefinition;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

/**
 * 用户任务处理器
 *
 * 参考 Camunda: UserTaskActivityBehavior
 */
@Slf4j
public class UserTaskHandler implements NodeHandler {

    @Override
    public void execute(ExecutionContext context) {
        NodeDefinition node = context.getCurrentNode();
        log.info("创建用户任务: nodeKey={}, nodeName={}", node.getNodeKey(), node.getNodeName());

        // 1. 解析任务配置
        Map<String, Object> config = node.getConfiguration();

        // 2. 解析处理人（从变量中获取）
        String assignee = resolveAssignee(config, context);
        String candidateUsers = resolveCandidateUsers(config, context);
        String candidateGroups = resolveCandidateGroups(config, context);

        // 3. 创建任务
        Task task = Task.builder()
                .taskId(UUID.randomUUID().toString().replace("-", ""))
                .processInstanceId(context.getProcessInstanceId())
                .executionId(context.getExecutionId())
                .nodeKey(node.getNodeKey())
                .nodeName(node.getNodeName())
                .taskName((String) config.getOrDefault("taskName", node.getNodeName()))
                .assignee(assignee)
                .status(TaskStatus.CREATED.getCode())
                .createTime(LocalDateTime.now())
                .build();

        // 4. 保存任务
        context.getProcessEngine().getTaskRepository().save(task);

        // 5. 保存候选人信息（如果有）
        // 通过 IdentityLink 存储

        log.info("用户任务创建成功: taskId={}, assignee={}", task.getTaskId(), assignee);
    }

    /**
     * 用户任务是等待状态，需要外部触发完成
     */
    @Override
    public boolean isWaitState() {
        return true;
    }

    @Override
    public int getNodeType() {
        return NodeType.USER_TASK.getCode();
    }

    // ==================== 私有辅助方法 ====================

    private String resolveAssignee(Map<String, Object> config, ExecutionContext context) {
        // 支持表达式: ${assignee}
        Object assignee = config.get("assignee");
        if (assignee instanceof String && ((String) assignee).startsWith("${")) {
            String expr = ((String) assignee).substring(2, ((String) assignee).length() - 1);
            Object value = context.getVariable(expr);
            return value != null ? value.toString() : null;
        }
        return assignee != null ? assignee.toString() : null;
    }

    private String resolveCandidateUsers(Map<String, Object> config, ExecutionContext context) {
        // 类似处理候选人
        return null;
    }

    private String resolveCandidateGroups(Map<String, Object> config, ExecutionContext context) {
        // 类似处理候选组
        return null;
    }
}
