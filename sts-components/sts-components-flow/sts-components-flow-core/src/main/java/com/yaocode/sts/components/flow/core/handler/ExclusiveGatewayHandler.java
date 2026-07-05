package com.yaocode.sts.components.flow.core.handler;

import com.yaocode.sts.components.flow.core.executor.ExecutionContext;
import com.yaocode.sts.components.flow.core.model.NodeDefinition;
import com.yaocode.sts.components.flow.core.model.SequenceDefinition;

import java.util.List;

/**
 * 排他网关处理器
 *
 * 参考 Camunda: ExclusiveGatewayActivityBehavior
 */
public class ExclusiveGatewayHandler implements NodeHandler {

    @Override
    public void execute(ExecutionContext context) {
        NodeDefinition node = context.getCurrentNode();
        log.info("执行排他网关: nodeKey={}, nodeName={}", node.getNodeKey(), node.getNodeName());

        // 1. 获取所有出边
        List<SequenceDefinition> outgoing = context.getProcessEngine()
                .getSequenceDefinitionRepository()
                .findOutgoingBySource(
                        context.getProcessDefinition().getProcessId(),
                        node.getNodeKey()
                );

        // 2. 评估条件，选择第一个满足条件的路径
        for (SequenceDefinition sequence : outgoing) {
            if (sequence.getConditionExpression() == null
                    || sequence.getConditionExpression().trim().isEmpty()) {
                // 默认路径
                context.getProcessEngine().getProcessExecutor()
                        .executeSequence(context, sequence);
                return;
            }

            // 评估条件
            boolean conditionMet = evaluateCondition(sequence.getConditionExpression(), context);
            if (conditionMet) {
                context.getProcessEngine().getProcessExecutor()
                        .executeSequence(context, sequence);
                return;
            }
        }

        // 3. 没有满足条件的路径，抛出异常
        throw new RuntimeException("排他网关没有匹配的路径: " + node.getNodeKey());
    }

    @Override
    public boolean isWaitState() {
        return false;
    }

    @Override
    public int getNodeType() {
        return NodeType.EXCLUSIVE_GATEWAY.getCode();
    }

    private boolean evaluateCondition(String expression, ExecutionContext context) {
        // 由表达式引擎评估
        return context.getProcessEngine().getConfiguration().getExpressionResolver() != null
                ? (Boolean) context.getProcessEngine().getConfiguration()
                .getExpressionResolver().resolve(expression, context)
                : true;
    }
}
