package com.yaocode.sts.components.flow.core.engine.repository;

/**
 * 仓库服务接口
 * 管理流程定义、部署和模型
 *
 * @author Process Engine Team
 * @version 1.0.0
 */
public interface RepositoryService {

    // ========== 部署管理 ==========

    /**
     * 创建部署构建器
     */
    DeploymentBuilder createDeployment();

    /**
     * 根据部署ID查询部署
     */
    Deployment getDeployment(String deploymentId);

    /**
     * 查询所有部署
     */
    List<Deployment> getDeployments();

    /**
     * 删除部署（级联删除流程实例）
     */
    void deleteDeployment(String deploymentId, boolean cascade);

    // ========== 流程定义管理 ==========

    /**
     * 根据流程定义ID查询
     */
    ProcessDefinition getProcessDefinition(String processDefinitionId);

    /**
     * 根据流程Key查询最新版本
     */
    ProcessDefinition getLatestProcessDefinitionByKey(String processDefinitionKey);

    /**
     * 查询流程定义的所有版本
     */
    List<ProcessDefinition> getProcessDefinitionsByKey(String processDefinitionKey);

    /**
     * 查询所有流程定义
     */
    List<ProcessDefinition> getAllProcessDefinitions();

    /**
     * 挂起流程定义（新实例不能启动）
     */
    void suspendProcessDefinition(String processDefinitionId);

    /**
     * 激活流程定义
     */
    void activateProcessDefinition(String processDefinitionId);

    /**
     * 删除流程定义
     */
    void deleteProcessDefinition(String processDefinitionId);

    // ========== 资源读取 ==========

    /**
     * 获取流程定义图片（流程图）
     */
    InputStream getProcessDefinitionDiagram(String processDefinitionId);

    /**
     * 获取流程定义BPMN XML
     */
    InputStream getProcessDefinitionBpmn(String processDefinitionId);

    /**
     * 获取部署资源
     */
    InputStream getResourceAsStream(String deploymentId, String resourceName);

    // ========== 模型管理 ==========

    /**
     * 创建模型
     */
    Model createModel(Model model);

    /**
     * 保存模型
     */
    void saveModel(Model model);

    /**
     * 删除模型
     */
    void deleteModel(String modelId);

    /**
     * 查询模型
     */
    Model getModel(String modelId);

    /**
     * 部署模型
     */
    Deployment deployModel(String modelId);

}
