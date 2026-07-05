package com.yaocode.sts.components.flow.core.executor;

import com.yaocode.sts.flow.core.ProcessEngine;
import com.yaocode.sts.flow.core.model.*;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * 执行上下文
 * 类似 Camunda 的 DelegateExecution
 *
 * 参考 Camunda: org.camunda.bpm.engine.delegate.DelegateExecution
 */
@Data
@Builder
public class ExecutionContext {

    private ProcessEngine processEngine;

    /**
     * 当前流程实例
     */
    private ProcessInstance processInstance;

    /**
     * 当前执行路径
     */
    private Execution execution;

    /**
     * 当前流程定义
     */
    private ProcessDefinition processDefinition;

    /**
     * 当前节点
     */
    private NodeDefinition currentNode;

    /**
     * 流程变量
     */
    private Map<String, Object> variables;

    /**
     * 获取变量
     */
    public Object getVariable(String name) {
        return variables != null ? variables.get(name) : null;
    }

    /**
     * 设置变量
     */
    public void setVariable(String name, Object value) {
        if (variables == null) {
            variables = new java.util.HashMap<>();
        }
        variables.put(name, value);

        // 持久化到数据库
        if (processEngine != null) {
            processEngine.getVariableRepository()
                    .save(processInstance.getProcessInstanceId(), name, value);
        }
    }

    /**
     * 获取变量（带类型转换）
     */
    @SuppressWarnings("unchecked")
    public <T> T getVariable(String name, Class<T> type) {
        Object value = getVariable(name);
        if (value == null) {
            return null;
        }
        if (type.isAssignableFrom(value.getClass())) {
            return (T) value;
        }
        // 尝试类型转换
        return null;
    }

    /**
     * 获取流程实例ID
     */
    public String getProcessInstanceId() {
        return processInstance != null ? processInstance.getProcessInstanceId() : null;
    }

    /**
     * 获取执行路径ID
     */
    public String getExecutionId() {
        return execution != null ? execution.getExecutionId() : null;
    }

    /**
     * 获取业务键
     */
    public String getBusinessKey() {
        return processInstance != null ? processInstance.getBusinessKey() : null;
    }
}
