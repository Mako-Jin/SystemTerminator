package com.yaocode.sts.components.flow.core.engine;

import com.yaocode.sts.components.flow.core.config.ProcessEngineConfiguration;
import com.yaocode.sts.components.flow.core.executor.CommandExecutor;
import com.yaocode.sts.components.flow.core.executor.ProcessExecutor;
import com.yaocode.sts.components.flow.core.job.JobExecutor;
import com.yaocode.sts.components.flow.core.job.JobScheduler;
import com.yaocode.sts.components.flow.core.repository.ExecutionRepository;
import com.yaocode.sts.components.flow.core.repository.NodeDefinitionRepository;
import com.yaocode.sts.components.flow.core.repository.ProcessDefinitionRepository;
import com.yaocode.sts.components.flow.core.repository.ProcessInstanceRepository;
import com.yaocode.sts.components.flow.core.repository.SequenceDefinitionRepository;
import com.yaocode.sts.components.flow.core.repository.TaskRepository;
import com.yaocode.sts.components.flow.core.repository.VariableRepository;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * 流程引擎
 * 类似 Camunda 的 ProcessEngine
 * <p>
 * 参考 Camunda: org.camunda.bpm.engine.ProcessEngine
 */
@Slf4j
@Getter
public class ProcessEngine {

    private final ProcessEngineConfiguration configuration;
    /**
     * -- GETTER --
     *  获取命令执行器
     */
    private final CommandExecutor commandExecutor;
    /**
     * -- GETTER --
     *  获取流程执行器
     */
    private final ProcessExecutor processExecutor;
    private final JobExecutor jobExecutor;
    /**
     * -- GETTER --
     *  获取作业调度器
     */
    private final JobScheduler jobScheduler;

    // 仓储
    private final ProcessDefinitionRepository processDefinitionRepository;
    private final ProcessInstanceRepository processInstanceRepository;
    private final ExecutionRepository executionRepository;
    private final TaskRepository taskRepository;
    private final VariableRepository variableRepository;
    private final NodeDefinitionRepository nodeDefinitionRepository;
    private final SequenceDefinitionRepository sequenceDefinitionRepository;

    public ProcessEngine(ProcessEngineConfiguration configuration) {
        this.configuration = configuration;
        this.commandExecutor = new CommandExecutor();
        this.processExecutor = new ProcessExecutor(this);
        this.jobExecutor = new JobExecutor(this);
        this.jobScheduler = new JobScheduler(this);

        // 初始化仓储
        this.processDefinitionRepository = configuration.getProcessDefinitionRepository();
        this.processInstanceRepository = configuration.getProcessInstanceRepository();
        this.executionRepository = configuration.getExecutionRepository();
        this.taskRepository = configuration.getTaskRepository();
        this.variableRepository = configuration.getVariableRepository();
        this.nodeDefinitionRepository = configuration.getNodeDefinitionRepository();
        this.sequenceDefinitionRepository = configuration.getSequenceDefinitionRepository();

        log.info("ProcessEngine 初始化完成");
    }

    public ProcessEngine start() {
        log.info("ProcessEngine 启动");
        jobScheduler.start();
        return this;
    }

    public void stop() {
        log.info("ProcessEngine 停止");
        jobScheduler.stop();
    }

}
