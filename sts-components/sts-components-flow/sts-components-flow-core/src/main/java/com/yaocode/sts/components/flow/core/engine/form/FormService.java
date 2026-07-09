package com.yaocode.sts.components.flow.core.engine.form;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 表单服务接口
 * <p>
 * 管理流程表单的定义、渲染和数据，包括：
 * <ul>
 *   <li>表单定义的创建、更新、删除、查询</li>
 *   <li>启动表单的获取、渲染、提交</li>
 *   <li>任务表单的获取、渲染、提交</li>
 *   <li>表单变量的获取（启动表单变量、任务表单变量）</li>
 *   <li>部署表单的获取</li>
 *   <li>表单模板管理</li>
 * </ul>
 * <p>
 * 使用示例：
 * <pre>
 * // 获取启动表单数据
 * StartFormData startForm = formService.getStartFormData(processDefinitionId);
 * // 提交启动表单
 * ProcessInstance instance = formService.submitStartForm(processDefinitionId, formValues);
 * // 获取任务表单数据
 * TaskFormData taskForm = formService.getTaskFormData(taskId);
 * // 提交任务表单
 * formService.submitTaskForm(taskId, formValues);
 * </pre>
 *
 * @author Process Engine Team
 * @version 1.0.0
 */
public interface FormService {

    // ========================================================================
    // 一、表单定义管理
    // ========================================================================

    /**
     * 创建表单定义
     *
     * @param formDefinition 表单定义对象
     * @return 创建后的表单定义
     */
    FormDefinition createFormDefinition(FormDefinition formDefinition);

    /**
     * 更新表单定义
     *
     * @param formDefinition 表单定义对象
     */
    void updateFormDefinition(FormDefinition formDefinition);

    /**
     * 删除表单定义
     *
     * @param formDefinitionId 表单定义ID
     */
    void deleteFormDefinition(String formDefinitionId);

    /**
     * 查询表单定义
     *
     * @param formDefinitionId 表单定义ID
     * @return 表单定义对象，不存在时返回null
     */
    FormDefinition getFormDefinition(String formDefinitionId);

    /**
     * 根据表单Key查询最新版本的表单定义
     *
     * @param formKey 表单Key
     * @return 最新版本的表单定义
     */
    FormDefinition getLatestFormDefinitionByKey(String formKey);

    /**
     * 查询所有表单定义
     *
     * @return 表单定义列表
     */
    List<FormDefinition> getAllFormDefinitions();

    // ========================================================================
    // 二、启动表单
    // ========================================================================

    /**
     * 获取流程启动表单数据
     *
     * @param processDefinitionId 流程定义ID
     * @return 表单数据对象
     */
    FormData getStartFormData(String processDefinitionId);

    /**
     * 渲染启动表单（使用默认表单引擎）
     *
     * @param processDefinitionId 流程定义ID
     * @return 渲染后的表单HTML
     */
    String renderStartForm(String processDefinitionId);

    /**
     * 渲染启动表单（使用指定的表单引擎）
     *
     * @param processDefinitionId 流程定义ID
     * @param formEngineName      表单引擎名称
     * @return 渲染后的表单
     */
    Object getRenderedStartForm(String processDefinitionId, String formEngineName);

    /**
     * 获取启动表单的渲染结果
     *
     * @param processDefinitionId 流程定义ID
     * @return 渲染后的表单
     */
    Object getRenderedStartForm(String processDefinitionId);

    /**
     * 提交启动表单
     * <p>
     * 使用用户在启动表单中输入的数据启动新的流程实例
     *
     * @param processDefinitionId 流程定义ID
     * @param formValues          表单字段值
     * @return 启动的流程实例
     */
    ProcessInstance submitStartForm(String processDefinitionId, Map<String, Object> formValues);

    /**
     * 提交启动表单（指定业务Key）
     *
     * @param processDefinitionId 流程定义ID
     * @param businessKey         业务Key（用于关联业务数据）
     * @param formValues          表单字段值
     * @return 启动的流程实例
     */
    ProcessInstance submitStartForm(String processDefinitionId, String businessKey, Map<String, Object> formValues);

    /**
     * 获取启动表单的Key
     *
     * @param processDefinitionId 流程定义ID
     * @return 表单Key
     */
    String getStartFormKey(String processDefinitionId);

    /**
     * 获取已部署的启动表单资源
     *
     * @param processDefinitionId 流程定义ID
     * @return 表单内容输入流
     */
    InputStream getDeployedStartForm(String processDefinitionId);

    // ========================================================================
    // 三、任务表单
    // ========================================================================

    /**
     * 获取任务表单
     *
     * @param taskId 任务ID
     * @return 任务表单对象
     */
    TaskForm getTaskForm(String taskId);

    /**
     * 获取任务表单数据
     *
     * @param taskId 任务ID
     * @return 表单数据对象
     */
    FormData getTaskFormData(String taskId);

    /**
     * 提交任务表单
     * <p>
     * 使用用户在任务表单中输入的数据完成任务
     *
     * @param taskId     任务ID
     * @param formValues 表单字段值
     */
    void submitTaskForm(String taskId, Map<String, Object> formValues);

    /**
     * 提交任务表单并返回变量
     *
     * @param taskId             任务ID
     * @param formValues         表单字段值
     * @param deserializeValues  是否反序列化变量值
     * @return 流程变量Map
     */
    VariableMap submitTaskFormWithVariablesInReturn(
            String taskId,
            Map<String, Object> formValues,
            boolean deserializeValues
    );

    /**
     * 渲染任务表单（使用默认表单引擎）
     *
     * @param taskId 任务ID
     * @return 渲染后的表单HTML
     */
    String renderTaskForm(String taskId);

    /**
     * 渲染任务表单（使用指定表单引擎）
     *
     * @param taskId          任务ID
     * @param formEngineName  表单引擎名称
     * @return 渲染后的表单
     */
    Object getRenderedTaskForm(String taskId, String formEngineName);

    /**
     * 获取任务表单的渲染结果
     *
     * @param taskId 任务ID
     * @return 渲染后的表单
     */
    Object getRenderedTaskForm(String taskId);

    /**
     * 获取任务表单的Key
     *
     * @param processDefinitionId 流程定义ID
     * @param taskDefinitionKey   任务定义Key
     * @return 表单Key
     */
    String getTaskFormKey(String processDefinitionId, String taskDefinitionKey);

    /**
     * 获取已部署的任务表单资源
     *
     * @param taskId 任务ID
     * @return 表单内容输入流
     */
    InputStream getDeployedTaskForm(String taskId);

    // ========================================================================
    // 四、表单变量管理
    // ========================================================================

    /**
     * 获取启动表单的所有变量
     * <p>
     * 考虑启动事件中指定的FormData，允许定义表单字段的默认值
     *
     * @param processDefinitionId 流程定义ID
     * @return 变量Map
     */
    VariableMap getStartFormVariables(String processDefinitionId);

    /**
     * 获取启动表单的指定变量
     *
     * @param processDefinitionId    流程定义ID
     * @param formVariables          需要获取的变量名称集合
     * @param deserializeObjectValues 是否反序列化对象值
     * @return 变量Map
     */
    VariableMap getStartFormVariables(
            String processDefinitionId,
            Collection<String> formVariables,
            boolean deserializeObjectValues
    );

    /**
     * 获取任务表单的所有变量
     * <p>
     * 变量解析顺序：
     * <ol>
     *   <li>收集所有表单字段并创建变量实例</li>
     *   <li>收集任务变量</li>
     *   <li>收集父作用域的流程变量，直到流程实例作用域</li>
     * </ol>
     *
     * @param taskId 任务ID
     * @return 变量Map
     */
    VariableMap getTaskFormVariables(String taskId);

    /**
     * 获取任务表单的指定变量
     *
     * @param taskId                 任务ID
     * @param formVariables          需要获取的变量名称集合
     * @param deserializeObjectValues 是否反序列化对象值
     * @return 变量Map
     */
    VariableMap getTaskFormVariables(
            String taskId,
            Collection<String> formVariables,
            boolean deserializeObjectValues
    );

    /**
     * 保存表单数据
     *
     * @param taskId   任务ID
     * @param formData 表单数据
     */
    void saveFormData(String taskId, Map<String, Object> formData);

    /**
     * 获取表单数据
     *
     * @param taskId 任务ID
     * @return 表单数据Map
     */
    Map<String, Object> getFormData(String taskId);

    /**
     * 验证表单数据
     *
     * @param formDefinitionId 表单定义ID
     * @param formData         表单数据
     * @return 验证错误列表
     */
    List<FormValidationError> validateFormData(String formDefinitionId, Map<String, Object> formData);

    // ========================================================================
    // 五、表单模板管理
    // ========================================================================

    /**
     * 获取表单模板
     *
     * @param templateId 模板ID
     * @return 模板内容
     */
    String getFormTemplate(String templateId);

    /**
     * 创建表单模板
     *
     * @param templateId      模板ID
     * @param templateContent 模板内容
     */
    void createFormTemplate(String templateId, String templateContent);

    /**
     * 更新表单模板
     *
     * @param templateId      模板ID
     * @param templateContent 模板内容
     */
    void updateFormTemplate(String templateId, String templateContent);

    /**
     * 删除表单模板
     *
     * @param templateId 模板ID
     */
    void deleteFormTemplate(String templateId);
}