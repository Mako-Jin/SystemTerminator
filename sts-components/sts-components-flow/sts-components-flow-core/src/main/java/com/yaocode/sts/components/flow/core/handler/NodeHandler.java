package com.yaocode.sts.components.flow.core.handler;


import com.yaocode.sts.components.flow.core.executor.ExecutionContext;

/**
 * 节点处理器接口
 * 类似 Camunda 的 ActivityBehavior
 *
 * 参考 Camunda: org.camunda.bpm.engine.impl.pvm.delegate.ActivityBehavior
 */
public interface NodeHandler {

    /**
     * 执行节点
     */
    void execute(ExecutionContext context);

    /**
     * 是否是等待状态（用户任务、接收任务等）
     * 等待状态的节点会暂停执行，等待外部触发
     */
    default boolean isWaitState() {
        return false;
    }

    /**
     * 节点类型
     */
    int getNodeType();
}
