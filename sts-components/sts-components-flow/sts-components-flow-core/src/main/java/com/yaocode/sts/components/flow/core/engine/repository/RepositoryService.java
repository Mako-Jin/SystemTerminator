package com.yaocode.sts.components.flow.core.engine.repository;

import java.io.InputStream;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * 仓库服务接口
 * <p>
 * 管理流程定义、部署和模型，包括：
 * <ul>
 *   <li>流程部署的创建、查询、删除</li>
 *   <li>流程定义的查询、挂起、激活、删除</li>
 *   <li>部署资源的读取（BPMN、流程图等）</li>
 *   <li>模型的管理（创建、保存、删除、部署）</li>
 *   <li>BPMN/CMMN/DMN模型实例的获取</li>
 *   <li>各类查询构建器的创建</li>
 * </ul>
 *
 * @author Process Engine Team
 * @version 1.0.0
 */
public interface RepositoryService {

    // ========================================================================
    // 一、部署管理
    // ========================================================================

    /**
     * 创建部署构建器
     * <p>
     * 用于构建并执行流程部署操作
     *
     * @return 部署构建器实例
     */
    DeploymentBuilder createDeployment();

    /**
     * 创建关联流程应用的部署构建器
     *
     * @param processApplication 流程应用引用
     * @return 流程应用部署构建器
     */
    ProcessApplicationDeploymentBuilder createDeployment(ProcessApplicationReference processApplication);

    /**
     * 根据部署ID查询部署信息
     *
     * @param deploymentId 部署ID（不能为空）
     * @return 部署对象，不存在时返回null
     */
    Deployment getDeployment(String deploymentId);

    /**
     * 查询所有部署信息
     *
     * @return 部署列表
     */
    List<Deployment> getDeployments();

    /**
     * 删除部署（非级联）
     * <p>
     * 如果部署下存在运行中的流程实例，删除将失败
     *
     * @param deploymentId 部署ID
     */
    void deleteDeployment(String deploymentId);

    /**
     * 删除部署（可级联删除流程实例）
     *
     * @param deploymentId 部署ID
     * @param cascade      是否级联删除（删除所有关联的流程实例和历史数据）
     */
    void deleteDeployment(String deploymentId, boolean cascade);

    /**
     * 删除部署，指定是否跳过自定义监听器
     *
     * @param deploymentId        部署ID
     * @param cascade             是否级联删除
     * @param skipCustomListeners 是否跳过自定义监听器
     */
    void deleteDeployment(String deploymentId, boolean cascade, boolean skipCustomListeners);

    /**
     * 删除部署，完整参数
     *
     * @param deploymentId        部署ID
     * @param cascade             是否级联删除
     * @param skipCustomListeners 是否跳过自定义监听器
     * @param skipIoMappings      是否跳过IO映射
     */
    void deleteDeployment(String deploymentId, boolean cascade, boolean skipCustomListeners, boolean skipIoMappings);

    /**
     * 获取部署中的资源名称列表
     *
     * @param deploymentId 部署ID
     * @return 资源名称列表
     */
    List<String> getDeploymentResourceNames(String deploymentId);

    /**
     * 获取部署中的所有资源
     *
     * @param deploymentId 部署ID
     * @return 资源列表
     */
    List<Resource> getDeploymentResources(String deploymentId);

    /**
     * 创建部署查询构建器
     *
     * @return 部署查询构建器
     */
    DeploymentQuery createDeploymentQuery();

    // ========================================================================
    // 二、流程定义管理
    // ========================================================================

    /**
     * 根据流程定义ID查询流程定义
     *
     * @param processDefinitionId 流程定义ID（不能为空）
     * @return 流程定义对象，不存在时返回null
     */
    ProcessDefinition getProcessDefinition(String processDefinitionId);

    /**
     * 根据流程Key查询最新版本的流程定义
     *
     * @param processDefinitionKey 流程定义Key（不能为空）
     * @return 最新版本的流程定义
     */
    ProcessDefinition getLatestProcessDefinitionByKey(String processDefinitionKey);

    /**
     * 查询流程定义的所有版本
     *
     * @param processDefinitionKey 流程定义Key
     * @return 该Key对应的所有版本的流程定义列表（按版本降序）
     */
    List<ProcessDefinition> getProcessDefinitionsByKey(String processDefinitionKey);

    /**
     * 查询所有流程定义
     *
     * @return 所有流程定义列表
     */
    List<ProcessDefinition> getAllProcessDefinitions();

    /**
     * 挂起流程定义
     * <p>
     * 挂起后，该流程定义无法启动新的流程实例
     *
     * @param processDefinitionId 流程定义ID
     */
    void suspendProcessDefinition(String processDefinitionId);

    /**
     * 挂起流程定义（通过ID）
     *
     * @param processDefinitionId 流程定义ID
     */
    void suspendProcessDefinitionById(String processDefinitionId);

    /**
     * 挂起流程定义（通过ID），可指定是否挂起已有实例及生效时间
     *
     * @param processDefinitionId        流程定义ID
     * @param suspendProcessInstances    是否挂起已有的流程实例
     * @param suspensionDate             挂起生效时间（可为空，立即生效）
     */
    void suspendProcessDefinitionById(String processDefinitionId, boolean suspendProcessInstances, Date suspensionDate);

    /**
     * 挂起流程定义（通过Key）
     * <p>
     * 挂起该Key对应的所有版本
     *
     * @param processDefinitionKey 流程定义Key
     */
    void suspendProcessDefinitionByKey(String processDefinitionKey);

    /**
     * 挂起流程定义（通过Key），可指定是否挂起已有实例及生效时间
     *
     * @param processDefinitionKey       流程定义Key
     * @param suspendProcessInstances    是否挂起已有的流程实例
     * @param suspensionDate             挂起生效时间
     */
    void suspendProcessDefinitionByKey(String processDefinitionKey, boolean suspendProcessInstances, Date suspensionDate);

    /**
     * 激活流程定义
     * <p>
     * 激活后，该流程定义可以启动新的流程实例
     *
     * @param processDefinitionId 流程定义ID
     */
    void activateProcessDefinition(String processDefinitionId);

    /**
     * 激活流程定义（通过ID）
     *
     * @param processDefinitionId 流程定义ID
     */
    void activateProcessDefinitionById(String processDefinitionId);

    /**
     * 激活流程定义（通过ID），可指定是否激活已有实例及生效时间
     *
     * @param processDefinitionId        流程定义ID
     * @param activateProcessInstances   是否激活已有的流程实例
     * @param activationDate             激活生效时间
     */
    void activateProcessDefinitionById(String processDefinitionId, boolean activateProcessInstances, Date activationDate);

    /**
     * 激活流程定义（通过Key）
     *
     * @param processDefinitionKey 流程定义Key
     */
    void activateProcessDefinitionByKey(String processDefinitionKey);

    /**
     * 激活流程定义（通过Key），可指定是否激活已有实例及生效时间
     *
     * @param processDefinitionKey       流程定义Key
     * @param activateProcessInstances   是否激活已有的流程实例
     * @param activationDate             激活生效时间
     */
    void activateProcessDefinitionByKey(String processDefinitionKey, boolean activateProcessInstances, Date activationDate);

    /**
     * 更新流程定义挂起状态的构建器
     * <p>
     * 提供灵活的流程定义挂起/激活操作
     *
     * @return 挂起状态更新构建器
     */
    UpdateProcessDefinitionSuspensionStateSelectBuilder updateProcessDefinitionSuspensionState();

    /**
     * 更新流程定义的历史保留时间
     *
     * @param processDefinitionId  流程定义ID
     * @param historyTimeToLive    历史数据保留时间（天数），null表示使用默认值
     */
    void updateProcessDefinitionHistoryTimeToLive(String processDefinitionId, Integer historyTimeToLive);

    /**
     * 删除流程定义（非级联）
     *
     * @param processDefinitionId 流程定义ID
     */
    void deleteProcessDefinition(String processDefinitionId);

    /**
     * 删除流程定义，指定是否级联删除
     *
     * @param processDefinitionId 流程定义ID
     * @param cascade             是否级联删除关联的流程实例和历史数据
     */
    void deleteProcessDefinition(String processDefinitionId, boolean cascade);

    /**
     * 删除流程定义，指定是否跳过自定义监听器
     *
     * @param processDefinitionId  流程定义ID
     * @param cascade              是否级联删除
     * @param skipCustomListeners  是否跳过自定义监听器
     */
    void deleteProcessDefinition(String processDefinitionId, boolean cascade, boolean skipCustomListeners);

    /**
     * 删除流程定义，完整参数
     *
     * @param processDefinitionId  流程定义ID
     * @param cascade              是否级联删除
     * @param skipCustomListeners  是否跳过自定义监听器
     * @param skipIoMappings       是否跳过IO映射
     */
    void deleteProcessDefinition(String processDefinitionId, boolean cascade, boolean skipCustomListeners, boolean skipIoMappings);

    /**
     * 批量删除流程定义的构建器
     *
     * @return 批量删除构建器
     */
    DeleteProcessDefinitionsSelectBuilder deleteProcessDefinitions();

    /**
     * 创建流程定义查询构建器
     *
     * @return 流程定义查询构建器
     */
    ProcessDefinitionQuery createProcessDefinitionQuery();

    /**
     * 获取静态调用的子流程定义集合
     * <p>
     * 查询流程定义中通过 callActivity 调用的所有子流程定义
     *
     * @param processDefinitionId 流程定义ID
     * @return 被调用的子流程定义集合
     */
    Collection<CalledProcessDefinition> getStaticCalledProcessDefinitions(String processDefinitionId);

    // ========================================================================
    // 三、资源读取
    // ========================================================================

    /**
     * 获取流程定义图片（流程图）
     *
     * @param processDefinitionId 流程定义ID
     * @return 流程图输入流（PNG格式）
     */
    InputStream getProcessDefinitionDiagram(String processDefinitionId);

    /**
     * 获取流程定义BPMN XML
     *
     * @param processDefinitionId 流程定义ID
     * @return BPMN XML输入流
     */
    InputStream getProcessDefinitionBpmn(String processDefinitionId);

    /**
     * 获取部署资源（通过部署ID和资源名称）
     *
     * @param deploymentId  部署ID
     * @param resourceName  资源名称
     * @return 资源输入流
     */
    InputStream getResourceAsStream(String deploymentId, String resourceName);

    /**
     * 获取部署资源（通过部署ID和资源ID）
     *
     * @param deploymentId  部署ID
     * @param resourceId    资源ID
     * @return 资源输入流
     */
    InputStream getResourceAsStreamById(String deploymentId, String resourceId);

    /**
     * 获取流程模型（BPMN XML）
     *
     * @param processDefinitionId 流程定义ID
     * @return 流程模型输入流
     */
    InputStream getProcessModel(String processDefinitionId);

    /**
     * 获取流程图（PNG）
     *
     * @param processDefinitionId 流程定义ID
     * @return 流程图输入流
     */
    InputStream getProcessDiagram(String processDefinitionId);

    /**
     * 获取流程图的布局信息
     *
     * @param processDefinitionId 流程定义ID
     * @return 流程图布局信息
     */
    DiagramLayout getProcessDiagramLayout(String processDefinitionId);

    /**
     * 获取BPMN模型实例
     *
     * @param processDefinitionId 流程定义ID
     * @return BPMN模型实例
     */
    BpmnModelInstance getBpmnModelInstance(String processDefinitionId);

    // ========================================================================
    // 四、模型管理
    // ========================================================================

    /**
     * 创建模型
     *
     * @param model 模型对象
     * @return 创建的模型（包含生成的ID）
     */
    Model createModel(Model model);

    /**
     * 保存模型
     *
     * @param model 模型对象
     */
    void saveModel(Model model);

    /**
     * 删除模型
     *
     * @param modelId 模型ID
     */
    void deleteModel(String modelId);

    /**
     * 查询模型
     *
     * @param modelId 模型ID
     * @return 模型对象
     */
    Model getModel(String modelId);

    /**
     * 部署模型
     * <p>
     * 将模型部署为流程定义
     *
     * @param modelId 模型ID
     * @return 创建的部署对象
     */
    Deployment deployModel(String modelId);

    // ========================================================================
    // 五、CMMN案例定义管理
    // ========================================================================

    /**
     * 查询案例定义
     *
     * @param caseDefinitionId 案例定义ID
     * @return 案例定义对象
     */
    CaseDefinition getCaseDefinition(String caseDefinitionId);

    /**
     * 获取案例模型（CMMN XML）
     *
     * @param caseDefinitionId 案例定义ID
     * @return CMMN模型输入流
     */
    InputStream getCaseModel(String caseDefinitionId);

    /**
     * 获取案例图（PNG）
     *
     * @param caseDefinitionId 案例定义ID
     * @return 案例图输入流
     */
    InputStream getCaseDiagram(String caseDefinitionId);

    /**
     * 获取CMMN模型实例
     *
     * @param caseDefinitionId 案例定义ID
     * @return CMMN模型实例
     */
    CmmnModelInstance getCmmnModelInstance(String caseDefinitionId);

    /**
     * 更新案例定义的历史保留时间
     *
     * @param caseDefinitionId   案例定义ID
     * @param historyTimeToLive  历史数据保留时间（天数）
     */
    void updateCaseDefinitionHistoryTimeToLive(String caseDefinitionId, Integer historyTimeToLive);

    /**
     * 创建案例定义查询构建器
     *
     * @return 案例定义查询构建器
     */
    CaseDefinitionQuery createCaseDefinitionQuery();

    // ========================================================================
    // 六、DMN决策定义管理
    // ========================================================================

    /**
     * 查询决策定义
     *
     * @param decisionDefinitionId 决策定义ID
     * @return 决策定义对象
     */
    DecisionDefinition getDecisionDefinition(String decisionDefinitionId);

    /**
     * 查询决策需求定义
     *
     * @param decisionRequirementsDefinitionId 决策需求定义ID
     * @return 决策需求定义对象
     */
    DecisionRequirementsDefinition getDecisionRequirementsDefinition(String decisionRequirementsDefinitionId);

    /**
     * 获取决策模型（DMN XML）
     *
     * @param decisionDefinitionId 决策定义ID
     * @return DMN模型输入流
     */
    InputStream getDecisionModel(String decisionDefinitionId);

    /**
     * 获取决策需求模型
     *
     * @param decisionRequirementsDefinitionId 决策需求定义ID
     * @return 决策需求模型输入流
     */
    InputStream getDecisionRequirementsModel(String decisionRequirementsDefinitionId);

    /**
     * 获取决策图（PNG）
     *
     * @param decisionDefinitionId 决策定义ID
     * @return 决策图输入流
     */
    InputStream getDecisionDiagram(String decisionDefinitionId);

    /**
     * 获取决策需求图（PNG）
     *
     * @param decisionRequirementsDefinitionId 决策需求定义ID
     * @return 决策需求图输入流
     */
    InputStream getDecisionRequirementsDiagram(String decisionRequirementsDefinitionId);

    /**
     * 获取DMN模型实例
     *
     * @param decisionDefinitionId 决策定义ID
     * @return DMN模型实例
     */
    DmnModelInstance getDmnModelInstance(String decisionDefinitionId);

    /**
     * 更新决策定义的历史保留时间
     *
     * @param decisionDefinitionId  决策定义ID
     * @param historyTimeToLive     历史数据保留时间（天数）
     */
    void updateDecisionDefinitionHistoryTimeToLive(String decisionDefinitionId, Integer historyTimeToLive);

    /**
     * 创建决策定义查询构建器
     *
     * @return 决策定义查询构建器
     */
    DecisionDefinitionQuery createDecisionDefinitionQuery();

    /**
     * 创建决策需求定义查询构建器
     *
     * @return 决策需求定义查询构建器
     */
    DecisionRequirementsDefinitionQuery createDecisionRequirementsDefinitionQuery();
}