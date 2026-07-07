package com.yaocode.sts.components.flow.core.engine.task;

import java.io.InputStream;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 任务服务接口
 * <p>
 * 管理人工任务的生命周期，包括：
 * <ul>
 *   <li>任务的创建、保存、删除</li>
 *   <li>任务的认领、取消认领、完成、委派、解决</li>
 *   <li>任务的分配、挂起、激活</li>
 *   <li>任务查询（按办理人、候选人、流程实例等）</li>
 *   <li>任务变量的设置、获取和删除</li>
 *   <li>任务候选人管理（用户/组）</li>
 *   <li>任务评论和附件管理</li>
 *   <li>BPMN错误和 escalation 处理</li>
 * </ul>
 *
 * @author Process Engine Team
 * @version 1.0.0
 */
public interface TaskService {

    // ========================================================================
    // 一、任务创建与保存
    // ========================================================================

    /**
     * 创建一个新的独立任务（不关联任何流程实例）
     * <p>
     * 返回的任务是瞬态的，必须通过 {@link #saveTask(Task)} 手动保存
     *
     * @return 新创建的任务实例
     */
    Task newTask();

    /**
     * 创建一个带有自定义ID的新任务
     *
     * @param taskId 自定义任务ID（不能为空）
     * @return 新创建的任务实例
     */
    Task newTask(String taskId);

    /**
     * 保存任务到持久化存储
     * <p>
     * 如果任务已存在则更新，否则创建新任务
     *
     * @param task 任务对象（不能为空）
     */
    void saveTask(Task task);

    // ========================================================================
    // 二、任务删除
    // ========================================================================

    /**
     * 删除指定任务（不删除历史信息）
     *
     * @param taskId 任务ID（不能为空）
     */
    void deleteTask(String taskId);

    /**
     * 删除指定任务
     *
     * @param taskId   任务ID（不能为空）
     * @param cascade  是否级联删除历史信息
     */
    void deleteTask(String taskId, boolean cascade);

    /**
     * 删除指定任务，记录删除原因
     *
     * @param taskId       任务ID（不能为空）
     * @param deleteReason 删除原因（记录到历史中）
     */
    void deleteTask(String taskId, String deleteReason);

    /**
     * 批量删除任务（不删除历史信息）
     *
     * @param taskIds 任务ID集合（不能为空）
     */
    void deleteTasks(Collection<String> taskIds);

    /**
     * 批量删除任务
     *
     * @param taskIds  任务ID集合（不能为空）
     * @param cascade  是否级联删除历史信息
     */
    void deleteTasks(Collection<String> taskIds, boolean cascade);

    /**
     * 批量删除任务，记录删除原因
     *
     * @param taskIds      任务ID集合（不能为空）
     * @param deleteReason 删除原因
     */
    void deleteTasks(Collection<String> taskIds, String deleteReason);

    // ========================================================================
    // 三、任务认领与分配
    // ========================================================================

    /**
     * 认领任务
     * <p>
     * 将当前用户设为任务的办理人。如果任务已被其他人认领，会抛出异常。
     * 当 userId 为 null 时，取消任务认领（即取消分配）
     *
     * @param taskId 任务ID（不能为空）
     * @param userId 认领任务的用户ID（为null时取消认领）
     */
    void claim(String taskId, String userId);

    /**
     * 取消认领任务
     * <p>
     * 清除任务的办理人，使任务变为未分配状态
     *
     * @param taskId 任务ID（不能为空）
     */
    void unclaim(String taskId);

    /**
     * 设置任务的办理人
     * <p>
     * 直接指定任务的办理人，不检查任务是否已被认领
     *
     * @param taskId 任务ID（不能为空）
     * @param userId 办理人用户ID（不能为空）
     */
    void setAssignee(String taskId, String userId);

    /**
     * 设置任务的所有者
     * <p>
     * 任务所有者拥有对任务的高级权限，如可以完成被委派的任务
     *
     * @param taskId 任务ID（不能为空）
     * @param userId 所有者用户ID（不能为空）
     */
    void setOwner(String taskId, String userId);

    /**
     * 委派任务给其他用户
     * <p>
     * 将任务委派给指定用户处理，任务的办理人变为被委派人，
     * 委派状态变为 {@code PENDING}
     *
     * @param taskId         任务ID（不能为空）
     * @param delegateUserId 被委派用户ID（不能为空）
     */
    void delegateTask(String taskId, String delegateUserId);

    /**
     * 解决委派任务
     * <p>
     * 被委派人完成任务处理后，将任务返回给所有者。
     * 调用后任务委派状态变为 {@code RESOLVED}
     *
     * @param taskId 任务ID（不能为空）
     */
    void resolveTask(String taskId);

    /**
     * 解决委派任务并携带变量
     *
     * @param taskId    任务ID（不能为空）
     * @param variables 返回时携带的变量
     */
    void resolveTask(String taskId, Map<String, Object> variables);

    // ========================================================================
    // 四、任务完成
    // ========================================================================

    /**
     * 完成任务
     * <p>
     * 标记任务为已完成，继续流程执行
     *
     * @param taskId 任务ID（不能为空）
     */
    void complete(String taskId);

    /**
     * 完成任务并携带变量
     *
     * @param taskId    任务ID（不能为空）
     * @param variables 完成时携带的流程变量
     */
    void complete(String taskId, Map<String, Object> variables);

    /**
     * 完成任务并返回任务变量
     * <p>
     * 完成任务的同是返回任务的所有变量
     *
     * @param taskId             任务ID（不能为空）
     * @param variables          完成时携带的流程变量
     * @param deserializeValues  是否反序列化变量值
     * @return 任务的所有变量
     */
    VariableMap completeWithVariablesInReturn(String taskId, Map<String, Object> variables, boolean deserializeValues);

    // ========================================================================
    // 五、任务查询
    // ========================================================================

    /**
     * 根据任务ID查询任务
     *
     * @param taskId 任务ID（不能为空）
     * @return 任务对象，不存在时返回null
     */
    Task getTask(String taskId);

    /**
     * 查询指定办理人的待办任务
     *
     * @param assignee 办理人用户ID
     * @return 任务列表
     */
    List<Task> getTasksByAssignee(String assignee);

    /**
     * 查询用户作为候选人的任务
     *
     * @param userId 用户ID
     * @return 任务列表
     */
    List<Task> getTasksByCandidate(String userId);

    /**
     * 查询所有未分配的任务
     *
     * @return 未分配任务列表
     */
    List<Task> getUnassignedTasks();

    /**
     * 查询用户相关的所有任务（待办 + 候选）
     *
     * @param userId 用户ID
     * @return 任务列表
     */
    List<Task> getTasksByUser(String userId);

    /**
     * 根据流程实例ID查询任务
     *
     * @param processInstanceId 流程实例ID
     * @return 任务列表
     */
    List<Task> getTasksByProcessInstanceId(String processInstanceId);

    /**
     * 查询父任务的所有子任务
     *
     * @param parentTaskId 父任务ID
     * @return 子任务列表
     */
    List<Task> getSubTasks(String parentTaskId);

    /**
     * 创建任务查询构建器
     *
     * @return 任务查询构建器
     */
    TaskQuery createTaskQuery();

    /**
     * 创建原生SQL任务查询构建器
     *
     * @return 原生SQL任务查询构建器
     */
    NativeTaskQuery createNativeTaskQuery();

    /**
     * 创建任务报表构建器
     *
     * @return 任务报表构建器
     */
    TaskReport createTaskReport();

    // ========================================================================
    // 六、任务属性修改
    // ========================================================================

    /**
     * 设置任务优先级
     *
     * @param taskId   任务ID（不能为空）
     * @param priority 优先级值
     */
    void setPriority(String taskId, int priority);

    /**
     * 设置任务名称
     *
     * @param taskId 任务ID（不能为空）
     * @param name   任务名称（不能为空）
     */
    void setName(String taskId, String name);

    /**
     * 设置任务描述
     *
     * @param taskId      任务ID（不能为空）
     * @param description 任务描述
     */
    void setDescription(String taskId, String description);

    /**
     * 设置任务截止日期
     *
     * @param taskId  任务ID（不能为空）
     * @param dueDate 截止日期
     */
    void setDueDate(String taskId, Date dueDate);

    /**
     * 设置任务跟进日期
     *
     * @param taskId       任务ID（不能为空）
     * @param followUpDate 跟进日期
     */
    void setFollowUpDate(String taskId, Date followUpDate);

    /**
     * 挂起任务
     *
     * @param taskId 任务ID
     */
    void suspendTask(String taskId);

    /**
     * 激活任务
     *
     * @param taskId 任务ID
     */
    void activateTask(String taskId);

    // ========================================================================
    // 七、候选人管理（身份关联）
    // ========================================================================

    /**
     * 获取任务的所有身份关联
     *
     * @param taskId 任务ID（不能为空）
     * @return 身份关联列表
     */
    List<IdentityLink> getIdentityLinksForTask(String taskId);

    /**
     * 添加候选用户
     *
     * @param taskId 任务ID（不能为空）
     * @param userId 用户ID（不能为空）
     */
    void addCandidateUser(String taskId, String userId);

    /**
     * 添加候选组
     *
     * @param taskId  任务ID（不能为空）
     * @param groupId 组ID（不能为空）
     */
    void addCandidateGroup(String taskId, String groupId);

    /**
     * 添加用户身份关联
     *
     * @param taskId           任务ID（不能为空）
     * @param userId           用户ID（不能为空）
     * @param identityLinkType 身份关联类型（不能为空）
     */
    void addUserIdentityLink(String taskId, String userId, String identityLinkType);

    /**
     * 添加组身份关联
     *
     * @param taskId           任务ID（不能为空）
     * @param groupId          组ID（不能为空）
     * @param identityLinkType 身份关联类型（不能为空）
     */
    void addGroupIdentityLink(String taskId, String groupId, String identityLinkType);

    /**
     * 删除候选用户
     *
     * @param taskId 任务ID（不能为空）
     * @param userId 用户ID（不能为空）
     */
    void deleteCandidateUser(String taskId, String userId);

    /**
     * 删除候选组
     *
     * @param taskId  任务ID（不能为空）
     * @param groupId 组ID（不能为空）
     */
    void deleteCandidateGroup(String taskId, String groupId);

    /**
     * 删除用户身份关联
     *
     * @param taskId           任务ID（不能为空）
     * @param userId           用户ID（不能为空）
     * @param identityLinkType 身份关联类型（不能为空）
     */
    void deleteUserIdentityLink(String taskId, String userId, String identityLinkType);

    /**
     * 删除组身份关联
     *
     * @param taskId           任务ID（不能为空）
     * @param groupId          组ID（不能为空）
     * @param identityLinkType 身份关联类型（不能为空）
     */
    void deleteGroupIdentityLink(String taskId, String groupId, String identityLinkType);

    // ========================================================================
    // 八、任务变量管理
    // ========================================================================

    /**
     * 设置任务变量（全局作用域）
     * <p>
     * 变量存储在任务的最外层作用域（即流程实例作用域）
     *
     * @param taskId       任务ID（不能为空）
     * @param variableName 变量名称（不能为空）
     * @param value        变量值
     */
    void setVariable(String taskId, String variableName, Object value);

    /**
     * 设置任务局部变量
     * <p>
     * 变量仅存储在任务作用域中，不影响父作用域
     *
     * @param taskId       任务ID（不能为空）
     * @param variableName 变量名称（不能为空）
     * @param value        变量值
     */
    void setVariableLocal(String taskId, String variableName, Object value);

    /**
     * 批量设置任务变量（全局作用域）
     *
     * @param taskId    任务ID（不能为空）
     * @param variables 变量Map
     */
    void setVariables(String taskId, Map<String, ? extends Object> variables);

    /**
     * 批量设置任务局部变量
     *
     * @param taskId    任务ID（不能为空）
     * @param variables 变量Map
     */
    void setVariablesLocal(String taskId, Map<String, ? extends Object> variables);

    /**
     * 获取任务变量值（全局作用域）
     *
     * @param taskId       任务ID（不能为空）
     * @param variableName 变量名称（不能为空）
     * @return 变量值，不存在时返回null
     */
    Object getVariable(String taskId, String variableName);

    /**
     * 获取任务变量值（带类型信息）
     *
     * @param taskId       任务ID
     * @param variableName 变量名称
     * @param <T>          变量类型
     * @return 带类型信息的变量值
     */
    <T extends TypedValue> T getVariableTyped(String taskId, String variableName);

    /**
     * 获取任务变量值（带类型信息，可控制是否反序列化）
     *
     * @param taskId           任务ID
     * @param variableName     变量名称
     * @param deserializeValue 是否反序列化
     * @param <T>              变量类型
     * @return 带类型信息的变量值
     */
    <T extends TypedValue> T getVariableTyped(String taskId, String variableName, boolean deserializeValue);

    /**
     * 获取任务局部变量值
     *
     * @param taskId       任务ID（不能为空）
     * @param variableName 变量名称（不能为空）
     * @return 变量值，不存在时返回null
     */
    Object getVariableLocal(String taskId, String variableName);

    /**
     * 获取任务局部变量值（带类型信息）
     *
     * @param taskId       任务ID
     * @param variableName 变量名称
     * @param <T>          变量类型
     * @return 带类型信息的局部变量值
     */
    <T extends TypedValue> T getVariableLocalTyped(String taskId, String variableName);

    /**
     * 获取任务局部变量值（带类型信息，可控制是否反序列化）
     *
     * @param taskId           任务ID
     * @param variableName     变量名称
     * @param deserializeValue 是否反序列化
     * @param <T>              变量类型
     * @return 带类型信息的局部变量值
     */
    <T extends TypedValue> T getVariableLocalTyped(String taskId, String variableName, boolean deserializeValue);

    /**
     * 获取所有任务变量（全局作用域）
     *
     * @param taskId 任务ID（不能为空）
     * @return 所有变量Map
     */
    Map<String, Object> getVariables(String taskId);

    /**
     * 获取所有任务变量（带类型信息）
     *
     * @param taskId 任务ID
     * @return 带类型信息的变量Map
     */
    VariableMap getVariablesTyped(String taskId);

    /**
     * 获取所有任务变量（带类型信息，可控制是否反序列化）
     *
     * @param taskId            任务ID
     * @param deserializeValues 是否反序列化
     * @return 带类型信息的变量Map
     */
    VariableMap getVariablesTyped(String taskId, boolean deserializeValues);

    /**
     * 获取指定名称的任务变量（全局作用域）
     *
     * @param taskId        任务ID（不能为空）
     * @param variableNames 变量名称集合
     * @return 匹配的变量Map
     */
    Map<String, Object> getVariables(String taskId, Collection<String> variableNames);

    /**
     * 获取指定名称的任务变量（带类型信息）
     *
     * @param taskId            任务ID
     * @param variableNames     变量名称集合
     * @param deserializeValues 是否反序列化
     * @return 带类型信息的变量Map
     */
    VariableMap getVariablesTyped(String taskId, Collection<String> variableNames, boolean deserializeValues);

    /**
     * 获取所有任务局部变量
     *
     * @param taskId 任务ID（不能为空）
     * @return 所有局部变量Map
     */
    Map<String, Object> getVariablesLocal(String taskId);

    /**
     * 获取所有任务局部变量（带类型信息）
     *
     * @param taskId 任务ID
     * @return 带类型信息的局部变量Map
     */
    VariableMap getVariablesLocalTyped(String taskId);

    /**
     * 获取所有任务局部变量（带类型信息，可控制是否反序列化）
     *
     * @param taskId            任务ID
     * @param deserializeValues 是否反序列化
     * @return 带类型信息的局部变量Map
     */
    VariableMap getVariablesLocalTyped(String taskId, boolean deserializeValues);

    /**
     * 获取指定名称的任务局部变量
     *
     * @param taskId        任务ID（不能为空）
     * @param variableNames 变量名称集合
     * @return 匹配的局部变量Map
     */
    Map<String, Object> getVariablesLocal(String taskId, Collection<String> variableNames);

    /**
     * 获取指定名称的任务局部变量（带类型信息）
     *
     * @param taskId            任务ID
     * @param variableNames     变量名称集合
     * @param deserializeValues 是否反序列化
     * @return 带类型信息的局部变量Map
     */
    VariableMap getVariablesLocalTyped(String taskId, Collection<String> variableNames, boolean deserializeValues);

    /**
     * 删除任务变量（全局作用域）
     *
     * @param taskId       任务ID（不能为空）
     * @param variableName 变量名称（不能为空）
     */
    void removeVariable(String taskId, String variableName);

    /**
     * 删除任务局部变量
     *
     * @param taskId       任务ID（不能为空）
     * @param variableName 变量名称（不能为空）
     */
    void removeVariableLocal(String taskId, String variableName);

    /**
     * 批量删除任务变量（全局作用域）
     *
     * @param taskId        任务ID（不能为空）
     * @param variableNames 变量名称集合
     */
    void removeVariables(String taskId, Collection<String> variableNames);

    /**
     * 批量删除任务局部变量
     *
     * @param taskId        任务ID（不能为空）
     * @param variableNames 变量名称集合
     */
    void removeVariablesLocal(String taskId, Collection<String> variableNames);

    // ========================================================================
    // 九、任务评论管理
    // ========================================================================

    /**
     * 创建任务评论
     *
     * @param taskId            任务ID（可为空）
     * @param processInstanceId 流程实例ID（可为空）
     * @param message           评论内容（不能为空）
     * @return 创建的评论对象
     */
    Comment createComment(String taskId, String processInstanceId, String message);

    /**
     * 获取任务的所有评论
     *
     * @param taskId 任务ID（不能为空）
     * @return 评论列表
     */
    List<Comment> getTaskComments(String taskId);

    /**
     * 获取指定的任务评论
     *
     * @param taskId    任务ID（不能为空）
     * @param commentId 评论ID（不能为空）
     * @return 评论对象
     */
    Comment getTaskComment(String taskId, String commentId);

    /**
     * 获取流程实例的所有评论
     *
     * @param processInstanceId 流程实例ID（不能为空）
     * @return 评论列表
     */
    List<Comment> getProcessInstanceComments(String processInstanceId);

    /**
     * 删除任务评论
     *
     * @param taskId    任务ID（不能为空）
     * @param commentId 评论ID（不能为空）
     */
    void deleteTaskComment(String taskId, String commentId);

    /**
     * 删除流程实例评论
     *
     * @param processInstanceId 流程实例ID（不能为空）
     * @param commentId         评论ID（不能为空）
     */
    void deleteProcessInstanceComment(String processInstanceId, String commentId);

    /**
     * 删除任务的所有评论
     *
     * @param taskId 任务ID（不能为空）
     */
    void deleteTaskComments(String taskId);

    /**
     * 删除流程实例的所有评论
     *
     * @param processInstanceId 流程实例ID（不能为空）
     */
    void deleteProcessInstanceComments(String processInstanceId);

    /**
     * 更新任务评论
     *
     * @param taskId    任务ID（不能为空）
     * @param commentId 评论ID（不能为空）
     * @param message   新的评论内容（不能为空）
     */
    void updateTaskComment(String taskId, String commentId, String message);

    /**
     * 更新流程实例评论
     *
     * @param processInstanceId 流程实例ID（不能为空）
     * @param commentId         评论ID（不能为空）
     * @param message           新的评论内容（不能为空）
     */
    void updateProcessInstanceComment(String processInstanceId, String commentId, String message);

    // ========================================================================
    // 十、任务附件管理
    // ========================================================================

    /**
     * 创建附件（使用输入流提供内容）
     *
     * @param attachmentType        附件类型
     * @param taskId                任务ID（与processInstanceId至少提供一个）
     * @param processInstanceId     流程实例ID（与taskId至少提供一个）
     * @param attachmentName        附件名称
     * @param attachmentDescription 附件描述
     * @param content               附件内容输入流
     * @return 创建的附件对象
     */
    Attachment createAttachment(String attachmentType, String taskId, String processInstanceId,
                                String attachmentName, String attachmentDescription, InputStream content);

    /**
     * 创建附件（使用URL作为内容）
     *
     * @param attachmentType        附件类型
     * @param taskId                任务ID（与processInstanceId至少提供一个）
     * @param processInstanceId     流程实例ID（与taskId至少提供一个）
     * @param attachmentName        附件名称
     * @param attachmentDescription 附件描述
     * @param url                   附件URL
     * @return 创建的附件对象
     */
    Attachment createAttachment(String attachmentType, String taskId, String processInstanceId,
                                String attachmentName, String attachmentDescription, String url);

    /**
     * 保存附件（更新附件信息）
     *
     * @param attachment 附件对象（不能为空）
     */
    void saveAttachment(Attachment attachment);

    /**
     * 获取附件
     *
     * @param attachmentId 附件ID（不能为空）
     * @return 附件对象
     */
    Attachment getAttachment(String attachmentId);

    /**
     * 获取任务的指定附件
     *
     * @param taskId       任务ID（不能为空）
     * @param attachmentId 附件ID（不能为空）
     * @return 附件对象
     */
    Attachment getTaskAttachment(String taskId, String attachmentId);

    /**
     * 获取附件内容
     *
     * @param attachmentId 附件ID（不能为空）
     * @return 附件内容输入流
     */
    InputStream getAttachmentContent(String attachmentId);

    /**
     * 获取任务的指定附件内容
     *
     * @param taskId       任务ID（不能为空）
     * @param attachmentId 附件ID（不能为空）
     * @return 附件内容输入流
     */
    InputStream getTaskAttachmentContent(String taskId, String attachmentId);

    /**
     * 获取任务的所有附件
     *
     * @param taskId 任务ID（不能为空）
     * @return 附件列表
     */
    List<Attachment> getTaskAttachments(String taskId);

    /**
     * 获取流程实例的所有附件
     *
     * @param processInstanceId 流程实例ID（不能为空）
     * @return 附件列表
     */
    List<Attachment> getProcessInstanceAttachments(String processInstanceId);

    /**
     * 删除附件
     *
     * @param attachmentId 附件ID（不能为空）
     */
    void deleteAttachment(String attachmentId);

    /**
     * 删除任务的指定附件
     *
     * @param taskId       任务ID（不能为空）
     * @param attachmentId 附件ID（不能为空）
     */
    void deleteTaskAttachment(String taskId, String attachmentId);

    // ========================================================================
    // 十一、BPMN错误和Escalation处理
    // ========================================================================

    /**
     * 处理BPMN错误
     * <p>
     * 向流程引擎发送业务错误信号，触发对应的错误边界事件
     *
     * @param taskId    任务ID（不能为空）
     * @param errorCode 错误码（不能为空）
     */
    void handleBpmnError(String taskId, String errorCode);

    /**
     * 处理BPMN错误，带错误消息
     *
     * @param taskId       任务ID（不能为空）
     * @param errorCode    错误码（不能为空）
     * @param errorMessage 错误消息
     */
    void handleBpmnError(String taskId, String errorCode, String errorMessage);

    /**
     * 处理BPMN错误，带错误消息和变量
     *
     * @param taskId       任务ID（不能为空）
     * @param errorCode    错误码（不能为空）
     * @param errorMessage 错误消息
     * @param variables    传递给流程的变量
     */
    void handleBpmnError(String taskId, String errorCode, String errorMessage, Map<String, Object> variables);

    /**
     * 处理Escalation事件
     * <p>
     * 向流程引擎发送 escalation 信号，触发对应的 escalation 边界事件
     *
     * @param taskId          任务ID（不能为空）
     * @param escalationCode  escalation码（不能为空）
     */
    void handleEscalation(String taskId, String escalationCode);

    /**
     * 处理Escalation事件，携带变量
     *
     * @param taskId          任务ID（不能为空）
     * @param escalationCode  escalation码（不能为空）
     * @param variables       传递给流程的变量
     */
    void handleEscalation(String taskId, String escalationCode, Map<String, Object> variables);
}