package com.yaocode.sts.components.flow.core.job;

/**
 * 作业执行器
 * 负责执行具体作业
 *
 * 参考 Camunda: JobHandler
 */
@Slf4j
public class JobExecutor {

    private final ProcessEngine processEngine;
    private final JobRepository jobRepository;
    private final ProcessInstanceRepository processInstanceRepository;

    public JobExecutor(ProcessEngine processEngine) {
        this.processEngine = processEngine;
        this.jobRepository = processEngine.getJobRepository();
        this.processInstanceRepository = processEngine.getProcessInstanceRepository();
    }

    /**
     * 执行作业
     */
    public void execute(Job job) {
        log.info("执行作业: jobId={}, jobType={}, dueDate={}",
                job.getJobId(), job.getJobType(), job.getDueDate());

        try {
            // 根据作业类型执行
            switch (JobType.fromCode(job.getJobType())) {
                case TIMER:
                    executeTimerJob(job);
                    break;
                case ASYNC_CONTINUATION:
                    executeAsyncJob(job);
                    break;
                case ASYNC_MESSAGE:
                    executeMessageJob(job);
                    break;
                case RETRY:
                    executeRetryJob(job);
                    break;
                default:
                    log.warn("未知作业类型: {}", job.getJobType());
                    jobRepository.fail(job.getJobId(), "未知作业类型: " + job.getJobType());
                    return;
            }

            // 执行成功
            jobRepository.success(job.getJobId(), LocalDateTime.now());
            log.info("作业执行成功: jobId={}", job.getJobId());

        } catch (Exception e) {
            log.error("作业执行失败: jobId={}", job.getJobId(), e);
            handleJobFailure(job, e);
        }
    }

    // ==================== 各类型作业执行 ====================

    /**
     * 执行定时器作业
     *
     * 参考 Camunda: TimerJobHandler
     */
    private void executeTimerJob(Job job) {
        Map<String, Object> config = job.getHandlerConfig();

        // 获取定时器配置
        String timerType = (String) config.getOrDefault("timerType", "duration");
        Object timerValue = config.get("timerValue");

        log.debug("执行定时器作业: timerType={}, timerValue={}", timerType, timerValue);

        // 触发流程事件
        // 1. 获取关联的流程实例
        if (job.getProcessInstanceId() != null) {
            ProcessInstance instance = processInstanceRepository.findById(job.getProcessInstanceId());
            if (instance == null) {
                throw new FlowException("流程实例不存在: " + job.getProcessInstanceId());
            }

            // 2. 触发边界事件或中间事件
            // 通过 ProcessExecutor 继续执行流程
            // 这里需要根据 job 关联的节点，触发对应的流程继续
            triggerProcessEvent(job, instance);
        } else {
            // 3. 全局定时器（如定时启动流程）
            // 调用 ProcessDefinitionService 启动流程
            log.debug("全局定时器作业: {}", job.getJobId());
        }
    }

    /**
     * 执行异步延续作业
     *
     * 参考 Camunda: AsyncContinuationJobHandler
     */
    private void executeAsyncJob(Job job) {
        log.debug("执行异步延续作业: jobId={}", job.getJobId());

        // 1. 获取流程实例
        if (job.getProcessInstanceId() == null) {
            throw new FlowException("异步作业缺少流程实例ID");
        }

        ProcessInstance instance = processInstanceRepository.findById(job.getProcessInstanceId());
        if (instance == null) {
            throw new FlowException("流程实例不存在: " + job.getProcessInstanceId());
        }

        // 2. 获取执行路径
        com.yaocode.sts.flow.core.model.Execution execution =
                processEngine.getExecutionRepository().findById(job.getExecutionId());

        if (execution == null) {
            throw new FlowException("执行路径不存在: " + job.getExecutionId());
        }

        // 3. 继续执行流程
        // 获取当前节点，继续执行
        processEngine.getProcessExecutor().executeNextNode(
                instance,
                execution,
                null  // 没有关联的任务
        );
    }

    /**
     * 执行异步消息作业
     */
    private void executeMessageJob(Job job) {
        log.debug("执行异步消息作业: jobId={}", job.getJobId());

        Map<String, Object> config = job.getHandlerConfig();
        String messageName = (String) config.get("messageName");
        String correlationKey = (String) config.get("correlationKey");

        // 触发消息事件
        processEngine.getProcessExecutor().execute((context) -> {
            // 通过 RuntimeService 触发消息
            // runtimeService.messageReceived(messageName, correlationKey, null);
            return null;
        });
    }

    /**
     * 执行重试作业
     */
    private void executeRetryJob(Job job) {
        log.debug("执行重试作业: jobId={}", job.getJobId());

        // 获取原始作业类型
        Map<String, Object> config = job.getHandlerConfig();
        Integer originalJobType = (Integer) config.get("originalJobType");

        if (originalJobType == null) {
            throw new FlowException("重试作业缺少原始作业类型");
        }

        // 重新执行原始作业
        Job originalJob = Job.builder()
                .jobId(job.getJobId())
                .jobType(originalJobType)
                .processInstanceId(job.getProcessInstanceId())
                .executionId(job.getExecutionId())
                .handlerConfig(config)
                .build();

        // 递归执行
        execute(originalJob);
    }

    // ==================== 私有辅助方法 ====================

    /**
     * 触发流程事件
     */
    private void triggerProcessEvent(Job job, ProcessInstance instance) {
        // 根据作业配置触发流程
        // 这里简化处理，实际需要根据节点类型和事件类型分发
        String nodeKey = job.getNodeKey();
        if (nodeKey != null) {
            log.debug("触发流程事件: instanceId={}, nodeKey={}", instance.getProcessInstanceId(), nodeKey);
            // 通过 ProcessExecutor 继续执行
            // processEngine.getProcessExecutor().executeNextNode(instance, null, null);
        }
    }

    /**
     * 处理作业失败
     */
    private void handleJobFailure(Job job, Exception e) {
        String errorMessage = e.getMessage() != null ? e.getMessage() : "未知错误";
        String stackTrace = getStackTrace(e);

        // 检查是否还有重试次数
        if (job.getRetriesRemaining() > 0) {
            // 计算下次重试时间（指数退避）
            LocalDateTime nextRetryTime = calculateNextRetryTime(job);

            jobRepository.fail(job.getJobId(), nextRetryTime, errorMessage, stackTrace);
            log.warn("作业失败，剩余重试次数: {}, 下次重试时间: {}",
                    job.getRetriesRemaining() - 1, nextRetryTime);
        } else {
            // 无重试次数，标记为失败
            jobRepository.fail(job.getJobId(), null, errorMessage, stackTrace);
            log.error("作业永久失败: jobId={}", job.getJobId());

            // 创建异常记录（Incident）
            createIncident(job, errorMessage);
        }
    }

    /**
     * 计算下次重试时间（指数退避）
     */
    private LocalDateTime calculateNextRetryTime(Job job) {
        int retries = job.getRetriesRemaining();
        int backoffMillis = job.getRetryBackoffMillis() != null
                ? job.getRetryBackoffMillis()
                : 1000;

        // 指数退避: 2^(remainingRetries) * backoff
        long delay = (long) (backoffMillis * Math.pow(2, retries));
        return LocalDateTime.now().plusMillis(delay);
    }

    /**
     * 创建异常记录
     */
    private void createIncident(Job job, String errorMessage) {
        // 由 IncidentService 创建
        log.error("创建异常记录: jobId={}, error={}", job.getJobId(), errorMessage);
        // processEngine.getIncidentService().createIncident(job, errorMessage);
    }

    /**
     * 获取堆栈信息
     */
    private String getStackTrace(Exception e) {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement element : e.getStackTrace()) {
            sb.append(element.toString()).append("\n");
            if (sb.length() > 2000) {
                break;
            }
        }
        return sb.toString();
    }
}
