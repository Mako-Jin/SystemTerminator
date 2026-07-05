package com.yaocode.sts.components.flow.core.handler;

import com.yaocode.sts.components.flow.core.executor.ExecutionContext;
import com.yaocode.sts.components.flow.core.model.NodeDefinition;
import com.yaocode.sts.components.flow.core.model.SequenceDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 并行网关处理器
 *
 * 参考 Camunda: ParallelGatewayActivityBehavior
 * 注意：并行网关有分支和汇聚两种行为
 */
public class ParallelGatewayHandler implements NodeHandler {

    private static final Logger logger = LoggerFactory.getLogger(ParallelGatewayHandler.class);

    @Override
    public void execute(ExecutionContext context) {
        NodeDefinition node = context.getCurrentNode();
        logger.info("执行并行网关: nodeKey={}, nodeName={}", node.getNodeKey(), node.getNodeName());

        // 判断是分支还是汇聚
        // 分支：出边 > 1，创建多个并发执行路径
        // 汇聚：入边 > 1，等待所有分支完成

        List<SequenceDefinition> outgoing = context.getProcessEngine()
                .getSequenceDefinitionRepository()
                .findOutgoingBySource(
                        context.getProcessDefinition().getProcessId(),
                        node.getNodeKey()
                );

        if (outgoing.size() > 1) {
            // 分支行为
            fork(context, outgoing);
        } else {
            // 汇聚行为
            join(context);
        }
    }

    /**
     * 分支：创建多个并发执行路径
     */
    private void fork(ExecutionContext context, List<SequenceDefinition> outgoing) {
        // 为每个出边创建独立的执行路径
        for (SequenceDefinition sequence : outgoing) {
            // 创建新的执行路径
            // 使用 ExecutionRepository 创建
            // 每个分支独立执行
        }
    }

    /**
     * 汇聚：等待所有分支完成
     */
    private void join(ExecutionContext context) {
        // 检查所有并发分支是否都到达了汇聚点
        // 如果都到达，继续执行
        // 否则，等待
    }

    @Override
    public boolean isWaitState() {
        // 汇聚时是等待状态
        return true;
    }

    @Override
    public int getNodeType() {
        return NodeType.PARALLEL_GATEWAY.getCode();
    }
}
