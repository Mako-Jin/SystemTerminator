package com.yaocode.sts.components.flow.core.job;

import com.yaocode.sts.components.flow.core.model.Job;

import java.util.Map;

/**
 * 作业工厂
 * 负责创建各类作业
 */
public class JobFactory {

    /**
     * 创建定时器作业
     */
    public static Job createTimerJob(String processInstanceId,
                                     String executionId,
                                     String definitionId,
                                     String nodeKey,
                                     NodeDefinition node,
                                     Map<String, Object> eventConfig,
                                     LocalDateTime dueDate) {

        Map<String, Object> handlerConfig = new HashMap<>();
        handlerConfig.put("timerType", eventConfig.get("timerType"));
        handlerConfig.put("timerValue", eventConfig.get("timerValue"));
        handlerConfig.put("calendarName", eventConfig.get("calendarName"));
        handlerConfig.put("nodeKey", nodeKey);

        return Job.builder()
                .jobId(UUID.randomUUID().toString().replace("-", ""))
                .jobType(JobType.TIMER.getCode())
                .processInstanceId(processInstanceId)
                .executionId(executionId)
                .definitionId(definitionId)
                .nodeKey(nodeKey)
                .handlerType("timer")
                .handlerConfig(handlerConfig)
                .retriesRemaining(3)
                .maxRetries(3)
                .retryBackoffMillis(5000)
                .dueDate(dueDate)
                .status(1)  // 等待
                .suspensionState(1) // 激活
                .priority(50)
                .exclusive(true)
                .build();
    }

    /**
     * 创建异步延续作业
     */
    public static Job createAsyncJob(String processInstanceId,
                                     String executionId,
                                     String definitionId,
                                     String nodeKey,
                                     boolean isAsyncBefore) {

        Map<String, Object> handlerConfig = new HashMap<>();
        handlerConfig.put("asyncType", isAsyncBefore ? "before" : "after");
        handlerConfig.put("nodeKey", nodeKey);

        return Job.builder()
                .jobId(UUID.randomUUID().toString().replace("-", ""))
                .jobType(JobType.ASYNC_CONTINUATION.getCode())
                .processInstanceId(processInstanceId)
                .executionId(executionId)
                .definitionId(definitionId)
                .nodeKey(nodeKey)
                .handlerType("async-continuation")
                .handlerConfig(handlerConfig)
                .retriesRemaining(3)
                .maxRetries(3)
                .retryBackoffMillis(5000)
                .dueDate(LocalDateTime.now())
                .status(1)
                .suspensionState(1)
                .priority(50)
                .exclusive(true)
                .build();
    }

    /**
     * 创建重试作业
     */
    public static Job createRetryJob(Job originalJob) {
        Map<String, Object> handlerConfig = new HashMap<>(originalJob.getHandlerConfig());
        handlerConfig.put("originalJobType", originalJob.getJobType());

        return Job.builder()
                .jobId(UUID.randomUUID().toString().replace("-", ""))
                .jobType(JobType.RETRY.getCode())
                .processInstanceId(originalJob.getProcessInstanceId())
                .executionId(originalJob.getExecutionId())
                .definitionId(originalJob.getDefinitionId())
                .nodeKey(originalJob.getNodeKey())
                .handlerType("retry")
                .handlerConfig(handlerConfig)
                .retriesRemaining(3)
                .maxRetries(3)
                .retryBackoffMillis(5000)
                .dueDate(LocalDateTime.now())
                .status(1)
                .suspensionState(1)
                .priority(50)
                .exclusive(true)
                .build();
    }
}
