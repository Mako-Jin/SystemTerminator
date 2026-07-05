package com.yaocode.sts.components.flow.core.handler;

import com.yaocode.sts.components.flow.core.enums.NodeTypeEnums;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 节点处理器注册表
 *
 * 参考 Camunda: ActivityBehavior 注册机制
 */
public class NodeHandlerRegistry {

    private static final Map<Integer, NodeHandler> HANDLER_MAP = new ConcurrentHashMap<>();

    static {
        // 注册事件处理器
        register(NodeTypeEnums.START_EVENT.getCode(), new StartEventHandler());
        register(NodeTypeEnums.END_EVENT.getCode(), new EndEventHandler());

        // 注册任务处理器
        register(NodeTypeEnums.USER_TASK.getCode(), new UserTaskHandler());
        register(NodeTypeEnums.SERVICE_TASK.getCode(), new ServiceTaskHandler());

        // 注册网关处理器
        register(NodeTypeEnums.EXCLUSIVE_GATEWAY.getCode(), new ExclusiveGatewayHandler());
        register(NodeTypeEnums.PARALLEL_GATEWAY.getCode(), new ParallelGatewayHandler());

        // TODO: 注册更多处理器
    }

    public static void register(int NodeTypeEnums, NodeHandler handler) {
        HANDLER_MAP.put(NodeTypeEnums, handler);
    }

    public static NodeHandler getHandler(NodeTypeEnums NodeTypeEnums) {
        if (NodeTypeEnums == null) {
            return null;
        }
        return HANDLER_MAP.get(NodeTypeEnums.getCode());
    }

    public static NodeHandler getHandler(int NodeTypeEnums) {
        return HANDLER_MAP.get(NodeTypeEnums);
    }

    public static boolean isSupported(int NodeTypeEnums) {
        return HANDLER_MAP.containsKey(NodeTypeEnums);
    }
}
