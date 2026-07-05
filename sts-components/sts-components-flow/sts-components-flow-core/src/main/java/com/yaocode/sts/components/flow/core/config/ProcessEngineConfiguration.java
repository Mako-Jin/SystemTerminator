package com.yaocode.sts.components.flow.core.config;

import com.yaocode.sts.components.flow.core.repository.ExecutionRepository;
import com.yaocode.sts.components.flow.core.repository.NodeDefinitionRepository;
import com.yaocode.sts.components.flow.core.repository.ProcessDefinitionRepository;
import com.yaocode.sts.components.flow.core.repository.ProcessInstanceRepository;
import com.yaocode.sts.components.flow.core.repository.SequenceDefinitionRepository;
import com.yaocode.sts.components.flow.core.repository.TaskRepository;
import com.yaocode.sts.components.flow.core.repository.VariableRepository;
import lombok.Builder;
import lombok.Getter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 流程引擎配置
 * <p>
 * 参考 Camunda: ProcessEngineConfiguration
 */
@Getter
@Builder
public class ProcessEngineConfiguration {

    // ==================== 仓储（必须注入） ====================

    private final ProcessDefinitionRepository processDefinitionRepository;
    private final ProcessInstanceRepository processInstanceRepository;
    private final ExecutionRepository executionRepository;
    private final TaskRepository taskRepository;
    private final VariableRepository variableRepository;
    private final NodeDefinitionRepository nodeDefinitionRepository;
    private final SequenceDefinitionRepository sequenceDefinitionRepository;

    // ==================== 可选配置 ====================

    /**
     * 作业执行线程池大小，默认 5
     */
    @Builder.Default
    private final int jobExecutorThreadPoolSize = 5;

    /**
     * 是否启用历史记录，默认 true
     */
    @Builder.Default
    private final boolean historyEnabled = true;

    /**
     * 历史记录级别：none, activity, audit, full，默认 full
     */
    @Builder.Default
    private final String historyLevel = "full";

    /**
     * 是否启用表达式缓存，默认 true
     */
    @Builder.Default
    private final boolean expressionCacheEnabled = true;

    /**
     * 表达式缓存大小，默认 1000
     */
    @Builder.Default
    private final int expressionCacheSize = 1000;

    /**
     * 批处理大小，默认 100
     */
    @Builder.Default
    private final int batchSize = 100;

    /**
     * 作业扫描间隔（毫秒），默认 5000
     */
    @Builder.Default
    private final long jobScanInterval = 5000L;

    /**
     * 作业锁超时时间（毫秒），默认 60000
     */
    @Builder.Default
    private final long jobLockTimeout = 60000L;

    /**
     * 自定义表达式解析器
     */
    private final ExpressionResolver expressionResolver;

    /**
     * 自定义线程池
     */
    private final ExecutorService executorService;

    public ExecutorService getExecutorService() {
        if (executorService != null) {
            return executorService;
        }
        return Executors.newFixedThreadPool(jobExecutorThreadPoolSize);
    }

    /**
     * 表达式解析器接口
     */
    public interface ExpressionResolver {
        Object resolve(String expression, Object context);
    }

    /**
     * 最大重试次数，默认 3
     */
    @Builder.Default
    private final int maxRetries = 3;

    /**
     * 重试退避基数（毫秒），默认 1000
     */
    @Builder.Default
    private final int retryBackoffBase = 1000;

    /**
     * 节点ID（用于集群锁标识）
     */
    private final String nodeId;

    // ... 其他 getter/setter ...
}
