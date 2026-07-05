package com.yaocode.sts.components.flow.core.event;

import com.yaocode.sts.components.flow.core.executor.ExecutionContext;
import com.yaocode.sts.components.flow.core.handler.NodeHandler;
import com.yaocode.sts.components.flow.core.model.NodeDefinition;
import lombok.extern.slf4j.Slf4j;

/**
 * 开始事件处理器
 *
 * 参考 Camunda: StartEventActivityBehavior
 */
@Slf4j
public class StartEventHandler implements NodeHandler {

    @Override
    public void execute(ExecutionContext context) {
        NodeDefinition node = context.getCurrentNode();
        log.info("执行开始事件: nodeKey={}, nodeName={}", node.getNodeKey(), node.getNodeName());

        // 开始事件执行逻辑：
        // 1. 记录开始时间
        // 2. 执行开始事件脚本（如果有）
        // 3. 触发出边

        // 开始事件不是等待状态，继续执行
    }

    @Override
    public boolean isWaitState() {
        return false;
    }

    @Override
    public int getNodeType() {
        return NodeType.START_EVENT.getCode();
    }
}
