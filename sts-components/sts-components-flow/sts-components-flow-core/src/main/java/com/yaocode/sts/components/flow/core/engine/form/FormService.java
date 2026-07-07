package com.yaocode.sts.components.flow.core.engine.form;


import java.util.List;
import java.util.Map;

/**
 * 表单服务接口
 * 表单定义、渲染和数据管理
 *
 * @author Process Engine Team
 * @version 1.0.0
 */
public interface FormService {

    // ========== 表单定义管理 ==========

    /**
     * 创建表单定义
     */
    FormDefinition createFormDefinition(FormDefinition formDefinition);

    /**
     * 更新表单定义
     */
    void updateFormDefinition(FormDefinition formDefinition);

    /**
     * 删除表单定义
     */
    void deleteFormDefinition(String formDefinitionId);

    /**
     * 查询表单定义
     */
    FormDefinition getFormDefinition(String formDefinitionId);

    /**
     * 根据表单Key查询最新版本
     */
    FormDefinition getLatestFormDefinitionByKey(String formKey);

    /**
     * 查询所有表单定义
     */
    List<FormDefinition> getAllFormDefinitions();

    // ========== 任务表单 ==========

    /**
     * 获取任务表单
     */
    TaskForm getTaskForm(String taskId);

    /**
     * 获取任务表单数据
     */
    FormData getTaskFormData(String taskId);

    /**
     * 提交任务表单
     */
    void submitTaskForm(String taskId, Map<String, Object> formValues);

    /**
     * 渲染任务表单（HTML）
     */
    String renderTaskForm(String taskId);

    // ========== 流程表单 ==========

    /**
     * 获取流程启动表单
     */
    FormData getStartFormData(String processDefinitionId);

    /**
     * 提交启动表单
     */
    void submitStartForm(String processDefinitionId, Map<String, Object> formValues);

    /**
     * 渲染启动表单
     */
    String renderStartForm(String processDefinitionId);

    // ========== 表单数据管理 ==========

    /**
     * 保存表单数据
     */
    void saveFormData(String taskId, Map<String, Object> formData);

    /**
     * 获取表单数据
     */
    Map<String, Object> getFormData(String taskId);

    /**
     * 验证表单数据
     */
    List<FormValidationError> validateFormData(String formDefinitionId,
                                               Map<String, Object> formData);

    // ========== 表单模板 ==========

    /**
     * 获取表单模板
     */
    String getFormTemplate(String templateId);

    /**
     * 创建表单模板
     */
    void createFormTemplate(String templateId, String templateContent);

    /**
     * 更新表单模板
     */
    void updateFormTemplate(String templateId, String templateContent);

    /**
     * 删除表单模板
     */
    void deleteFormTemplate(String templateId);

}
