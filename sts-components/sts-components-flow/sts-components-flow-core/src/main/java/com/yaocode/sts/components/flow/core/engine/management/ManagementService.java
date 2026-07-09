package com.yaocode.sts.components.flow.core.engine.management;

import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 管理服务接口
 * <p>
 * 提供流程引擎的管理和维护操作，包括：
 * <ul>
 *   <li>引擎信息查询（版本、配置等）</li>
 *   <li>流程应用注册与注销</li>
 *   <li>作业（Job）管理：查询、执行、删除、挂起、激活、重试次数设置</li>
 *   <li>作业定义（JobDefinition）管理：挂起、激活</li>
 *   <li>数据库表信息查询和原生SQL执行</li>
 *   <li>缓存管理</li>
 *   <li>监控指标查询</li>
 *   <li>批次（Batch）管理：挂起、激活、删除、统计</li>
 *   <li>统计查询（流程定义、部署、活动）</li>
 *   <li>属性管理</li>
 *   <li>许可管理</li>
 * </ul>
 *
 * @author Process Engine Team
 * @version 1.0.0
 */
public interface ManagementService {

    // ========================================================================
    // 一、引擎信息
    // ========================================================================

    /**
     * 获取引擎信息
     *
     * @return 引擎信息对象
     */
    EngineInfo getEngineInfo();

    /**
     * 获取引擎配置
     *
     * @return 配置键值对
     */
    Map<String, Object> getEngineConfiguration();

    /**
     * 获取引擎版本
     *
     * @return 引擎版本号
     */
    String getEngineVersion();

    /**
     * 执行引擎命令
     *
     * @param command 命令对象
     * @param <T>     返回类型
     * @return 命令执行结果
     */
    <T> T executeCommand(Command<T> command);

    /**
     * 获取配置的历史级别
     *
     * @return 历史级别
     */
    int getHistoryLevel();

    // ========================================================================
    // 二、流程应用管理
    // ========================================================================

    /**
     * 为指定的部署注册流程应用
     * <p>
     * 激活后，流程引擎将在该流程应用上下文中执行原子操作，
     * 作业执行器将开始获取该部署的作业
     *
     * @param deploymentId 部署ID
     * @param reference    流程应用引用
     * @return 流程应用注册信息
     */
    ProcessApplicationRegistration registerProcessApplication(String deploymentId, ProcessApplicationReference reference);

    /**
     * 注销流程应用
     * <p>
     * 移除流程引擎与流程应用之间的关联
     *
     * @param deploymentId                        部署ID
     * @param removeProcessDefinitionsFromCache   是否从缓存中移除流程定义
     */
    void unregisterProcessApplication(String deploymentId, boolean removeProcessDefinitionsFromCache);

    /**
     * 批量注销流程应用
     *
     * @param deploymentIds                       部署ID集合
     * @param removeProcessDefinitionsFromCache   是否从缓存中移除流程定义
     */
    void unregisterProcessApplication(Set<String> deploymentIds, boolean removeProcessDefinitionsFromCache);

    /**
     * 获取指定部署注册的流程应用名称
     *
     * @param deploymentId 部署ID
     * @return 流程应用名称，未注册时返回null
     */
    String getProcessApplicationForDeployment(String deploymentId);

    // ========================================================================
    // 三、作业查询
    // ========================================================================

    /**
     * 查询所有作业
     *
     * @return 作业列表
     */
    List<Job> getAllJobs();

    /**
     * 查询等待执行的作业
     *
     * @return 等待中的作业列表
     */
    List<Job> getWaitingJobs();

    /**
     * 查询执行中的作业
     *
     * @return 执行中的作业列表
     */
    List<Job> getRunningJobs();

    /**
     * 查询失败的作业
     *
     * @return 失败的作业列表
     */
    List<Job> getFailedJobs();

    /**
     * 获取作业详情
     *
     * @param jobId 作业ID
     * @return 作业对象，不存在时返回null
     */
    Job getJob(String jobId);

    /**
     * 创建作业查询构建器
     *
     * @return 作业查询构建器
     */
    JobQuery createJobQuery();

    /**
     * 创建作业定义查询构建器
     *
     * @return 作业定义查询构建器
     */
    JobDefinitionQuery createJobDefinitionQuery();

    // ========================================================================
    // 四、作业执行控制
    // ========================================================================

    /**
     * 强制同步执行作业（用于管理或测试）
     * <p>
     * 即使流程定义或流程实例处于挂起状态，作业也会被执行
     *
     * @param jobId 作业ID（不能为空）
     */
    void executeJob(String jobId);

    /**
     * 删除作业
     *
     * @param jobId 作业ID（不能为空）
     */
    void deleteJob(String jobId);

    /**
     * 取消作业
     *
     * @param jobId 作业ID（不能为空）
     */
    void cancelJob(String jobId);

    /**
     * 重试失败的作业
     * <p>
     * 增加作业的重试次数，使其能够再次执行
     *
     * @param jobId 作业ID（不能为空）
     */
    void retryJob(String jobId);

    /**
     * 设置作业的重试次数
     *
     * @param jobId   作业ID（不能为空）
     * @param retries 重试次数
     */
    void setJobRetries(String jobId, int retries);

    /**
     * 批量设置作业的重试次数
     *
     * @param jobIds  作业ID列表（不能为空）
     * @param retries 重试次数
     */
    void setJobRetries(List<String> jobIds, int retries);

    /**
     * 创建作业重试次数设置构建器（同步）
     *
     * @param retries 重试次数
     * @return 构建器实例
     */
    SetJobRetriesBuilder setJobRetries(int retries);

    /**
     * 异步批量设置作业重试次数（通过作业ID列表）
     *
     * @param jobIds  作业ID列表
     * @param retries 重试次数
     * @return 批次操作对象
     */
    Batch setJobRetriesAsync(List<String> jobIds, int retries);

    /**
     * 异步批量设置作业重试次数（通过查询条件）
     *
     * @param jobQuery 作业查询条件
     * @param retries  重试次数
     * @return 批次操作对象
     */
    Batch setJobRetriesAsync(JobQuery jobQuery, int retries);

    /**
     * 异步批量设置作业重试次数（合并ID列表和查询条件）
     *
     * @param jobIds   作业ID列表
     * @param jobQuery 作业查询条件
     * @param retries  重试次数
     * @return 批次操作对象
     */
    Batch setJobRetriesAsync(List<String> jobIds, JobQuery jobQuery, int retries);

    /**
     * 异步批量设置作业重试次数（通过流程实例）
     *
     * @param processInstanceIds 流程实例ID列表
     * @param query              流程实例查询条件
     * @param retries            重试次数
     * @return 批次操作对象
     */
    Batch setJobRetriesAsync(List<String> processInstanceIds, ProcessInstanceQuery query, int retries);

    /**
     * 异步批量设置作业重试次数（完整参数）
     *
     * @param processInstanceIds           流程实例ID列表
     * @param processInstanceQuery         流程实例查询条件
     * @param historicProcessInstanceQuery 历史流程实例查询条件
     * @param retries                      重试次数
     * @return 批次操作对象
     */
    Batch setJobRetriesAsync(
            List<String> processInstanceIds,
            ProcessInstanceQuery processInstanceQuery,
            HistoricProcessInstanceQuery historicProcessInstanceQuery,
            int retries
    );

    /**
     * 创建异步作业重试次数设置构建器（基于作业）
     *
     * @param retries 重试次数
     * @return 构建器实例
     */
    SetJobRetriesByJobsAsyncBuilder setJobRetriesByJobsAsync(int retries);

    /**
     * 创建异步作业重试次数设置构建器（基于流程）
     *
     * @param retries 重试次数
     * @return 构建器实例
     */
    SetJobRetriesByProcessAsyncBuilder setJobRetriesByProcessAsync(int retries);

    /**
     * 按作业定义ID设置失败作业的重试次数
     *
     * @param jobDefinitionId 作业定义ID（不能为空）
     * @param retries         重试次数
     */
    void setJobRetriesByJobDefinitionId(String jobDefinitionId, int retries);

    /**
     * 设置作业的截止日期
     *
     * @param jobId      作业ID（不能为空）
     * @param newDuedate 新的截止日期（为null时立即执行）
     */
    void setJobDuedate(String jobId, Date newDuedate);

    /**
     * 设置作业的截止日期，可级联更新后续作业
     *
     * @param jobId      作业ID（不能为空）
     * @param newDuedate 新的截止日期
     * @param cascade    是否级联更新后续作业（仅对定时作业有效）
     */
    void setJobDuedate(String jobId, Date newDuedate, boolean cascade);

    /**
     * 重新计算作业的截止日期
     *
     * @param jobId             作业ID（不能为空）
     * @param creationDateBased 是否基于创建日期计算（false则基于当前日期）
     */
    void recalculateJobDuedate(String jobId, boolean creationDateBased);

    /**
     * 设置作业优先级
     *
     * @param jobId    作业ID（不能为空）
     * @param priority 优先级值
     */
    void setJobPriority(String jobId, long priority);

    /**
     * 获取作业的异常堆栈信息
     *
     * @param jobId 作业ID（不能为空）
     * @return 异常堆栈信息，无异常时返回null
     */
    String getJobExceptionStacktrace(String jobId);

    // ========================================================================
    // 五、作业定义挂起/激活
    // ========================================================================

    /**
     * 激活作业定义（通过ID）
     *
     * @param jobDefinitionId 作业定义ID
     */
    void activateJobDefinitionById(String jobDefinitionId);

    /**
     * 激活作业定义（通过ID），可选择是否激活关联作业
     *
     * @param jobDefinitionId 作业定义ID
     * @param activateJobs    是否同时激活关联的作业
     */
    void activateJobDefinitionById(String jobDefinitionId, boolean activateJobs);

    /**
     * 激活作业定义（通过ID），可指定激活时间
     *
     * @param jobDefinitionId 作业定义ID
     * @param activateJobs    是否同时激活关联的作业
     * @param activationDate  激活生效时间（为null时立即生效）
     */
    void activateJobDefinitionById(String jobDefinitionId, boolean activateJobs, Date activationDate);

    /**
     * 按流程定义ID激活作业定义
     *
     * @param processDefinitionId 流程定义ID
     */
    void activateJobDefinitionByProcessDefinitionId(String processDefinitionId);

    /**
     * 按流程定义ID激活作业定义，可选择是否激活关联作业
     *
     * @param processDefinitionId 流程定义ID
     * @param activateJobs        是否同时激活关联的作业
     */
    void activateJobDefinitionByProcessDefinitionId(String processDefinitionId, boolean activateJobs);

    /**
     * 按流程定义ID激活作业定义，可指定激活时间
     *
     * @param processDefinitionId 流程定义ID
     * @param activateJobs        是否同时激活关联的作业
     * @param activationDate      激活生效时间
     */
    void activateJobDefinitionByProcessDefinitionId(String processDefinitionId, boolean activateJobs, Date activationDate);

    /**
     * 按流程定义Key激活作业定义
     *
     * @param processDefinitionKey 流程定义Key
     */
    void activateJobDefinitionByProcessDefinitionKey(String processDefinitionKey);

    /**
     * 按流程定义Key激活作业定义，可选择是否激活关联作业
     *
     * @param processDefinitionKey 流程定义Key
     * @param activateJobs         是否同时激活关联的作业
     */
    void activateJobDefinitionByProcessDefinitionKey(String processDefinitionKey, boolean activateJobs);

    /**
     * 按流程定义Key激活作业定义，可指定激活时间
     *
     * @param processDefinitionKey 流程定义Key
     * @param activateJobs         是否同时激活关联的作业
     * @param activationDate       激活生效时间
     */
    void activateJobDefinitionByProcessDefinitionKey(String processDefinitionKey, boolean activateJobs, Date activationDate);

    /**
     * 挂起作业定义（通过ID）
     *
     * @param jobDefinitionId 作业定义ID
     */
    void suspendJobDefinitionById(String jobDefinitionId);

    /**
     * 挂起作业定义（通过ID），可选择是否挂起关联作业
     *
     * @param jobDefinitionId 作业定义ID
     * @param suspendJobs     是否同时挂起关联的作业
     */
    void suspendJobDefinitionById(String jobDefinitionId, boolean suspendJobs);

    /**
     * 挂起作业定义（通过ID），可指定挂起时间
     *
     * @param jobDefinitionId 作业定义ID
     * @param suspendJobs     是否同时挂起关联的作业
     * @param suspensionDate  挂起生效时间（为null时立即生效）
     */
    void suspendJobDefinitionById(String jobDefinitionId, boolean suspendJobs, Date suspensionDate);

    /**
     * 按流程定义ID挂起作业定义
     *
     * @param processDefinitionId 流程定义ID
     */
    void suspendJobDefinitionByProcessDefinitionId(String processDefinitionId);

    /**
     * 按流程定义ID挂起作业定义，可选择是否挂起关联作业
     *
     * @param processDefinitionId 流程定义ID
     * @param suspendJobs         是否同时挂起关联的作业
     */
    void suspendJobDefinitionByProcessDefinitionId(String processDefinitionId, boolean suspendJobs);

    /**
     * 按流程定义ID挂起作业定义，可指定挂起时间
     *
     * @param processDefinitionId 流程定义ID
     * @param suspendJobs         是否同时挂起关联的作业
     * @param suspensionDate      挂起生效时间
     */
    void suspendJobDefinitionByProcessDefinitionId(String processDefinitionId, boolean suspendJobs, Date suspensionDate);

    /**
     * 按流程定义Key挂起作业定义
     *
     * @param processDefinitionKey 流程定义Key
     */
    void suspendJobDefinitionByProcessDefinitionKey(String processDefinitionKey);

    /**
     * 按流程定义Key挂起作业定义，可选择是否挂起关联作业
     *
     * @param processDefinitionKey 流程定义Key
     * @param suspendJobs          是否同时挂起关联的作业
     */
    void suspendJobDefinitionByProcessDefinitionKey(String processDefinitionKey, boolean suspendJobs);

    /**
     * 按流程定义Key挂起作业定义，可指定挂起时间
     *
     * @param processDefinitionKey 流程定义Key
     * @param suspendJobs          是否同时挂起关联的作业
     * @param suspensionDate       挂起生效时间
     */
    void suspendJobDefinitionByProcessDefinitionKey(String processDefinitionKey, boolean suspendJobs, Date suspensionDate);

    /**
     * 创建作业定义挂起状态更新构建器
     *
     * @return 构建器实例
     */
    UpdateJobDefinitionSuspensionStateSelectBuilder updateJobDefinitionSuspensionState();

    // ========================================================================
    // 六、作业挂起/激活
    // ========================================================================

    /**
     * 激活作业（通过ID）
     *
     * @param jobId 作业ID
     */
    void activateJobById(String jobId);

    /**
     * 按作业定义ID激活所有作业
     *
     * @param jobDefinitionId 作业定义ID
     */
    void activateJobByJobDefinitionId(String jobDefinitionId);

    /**
     * 按流程实例ID激活所有作业
     *
     * @param processInstanceId 流程实例ID
     */
    void activateJobByProcessInstanceId(String processInstanceId);

    /**
     * 按流程定义ID激活所有作业
     *
     * @param processDefinitionId 流程定义ID
     */
    void activateJobByProcessDefinitionId(String processDefinitionId);

    /**
     * 按流程定义Key激活所有作业
     *
     * @param processDefinitionKey 流程定义Key
     */
    void activateJobByProcessDefinitionKey(String processDefinitionKey);

    /**
     * 挂起作业（通过ID）
     *
     * @param jobId 作业ID
     */
    void suspendJobById(String jobId);

    /**
     * 按作业定义ID挂起所有作业
     *
     * @param jobDefinitionId 作业定义ID
     */
    void suspendJobByJobDefinitionId(String jobDefinitionId);

    /**
     * 按流程实例ID挂起所有作业
     *
     * @param processInstanceId 流程实例ID
     */
    void suspendJobByProcessInstanceId(String processInstanceId);

    /**
     * 按流程定义ID挂起所有作业
     *
     * @param processDefinitionId 流程定义ID
     */
    void suspendJobByProcessDefinitionId(String processDefinitionId);

    /**
     * 按流程定义Key挂起所有作业
     *
     * @param processDefinitionKey 流程定义Key
     */
    void suspendJobByProcessDefinitionKey(String processDefinitionKey);

    /**
     * 创建作业挂起状态更新构建器
     *
     * @return 构建器实例
     */
    UpdateJobSuspensionStateSelectBuilder updateJobSuspensionState();

    // ========================================================================
    // 七、作业定义优先级管理
    // ========================================================================

    /**
     * 设置作业定义的覆盖优先级
     * <p>
     * 此后创建的作业将使用指定的优先级，覆盖BPMN XML中的设置
     *
     * @param jobDefinitionId 作业定义ID
     * @param priority        优先级值
     */
    void setOverridingJobPriorityForJobDefinition(String jobDefinitionId, long priority);

    /**
     * 设置作业定义的覆盖优先级，可选择是否级联更新已有作业
     *
     * @param jobDefinitionId 作业定义ID
     * @param priority        优先级值
     * @param cascade         是否级联更新已有作业的优先级
     */
    void setOverridingJobPriorityForJobDefinition(String jobDefinitionId, long priority, boolean cascade);

    /**
     * 清除作业定义的覆盖优先级
     * <p>
     * 此后新作业将使用BPMN XML中指定的优先级或全局默认优先级
     *
     * @param jobDefinitionId 作业定义ID
     */
    void clearOverridingJobPriorityForJobDefinition(String jobDefinitionId);

    // ========================================================================
    // 八、数据库管理
    // ========================================================================

    /**
     * 获取数据库表行数统计
     *
     * @return 表名 -> 行数
     */
    Map<String, Long> getTableCount();

    /**
     * 获取实体类对应的数据库表名
     *
     * @param entityClass 实体类
     * @return 表名
     */
    String getTableName(Class<?> entityClass);

    /**
     * 获取数据库表元数据（列名、列类型等）
     *
     * @param tableName 表名
     * @return 表元数据，不存在时返回null
     */
    TableMetaData getTableMetaData(String tableName);

    /**
     * 创建表分页查询构建器
     *
     * @return 表分页查询构建器
     */
    TablePageQuery createTablePageQuery();

    /**
     * 获取数据库表信息
     *
     * @return 表信息列表
     */
    List<TableInfo> getTableInfo();

    /**
     * 获取表总行数
     *
     * @param tableName 表名
     * @return 行数
     */
    long getTableRowCount(String tableName);

    /**
     * 执行原生SQL查询
     *
     * @param sql SQL查询语句
     * @return 查询结果列表
     */
    List<Map<String, Object>> executeNativeQuery(String sql);

    /**
     * 执行原生SQL更新
     *
     * @param sql SQL更新语句
     * @return 影响行数
     */
    int executeNativeUpdate(String sql);

    /**
     * 数据库Schema升级
     *
     * @param connection 数据库连接
     * @param catalog    目录
     * @param schema     模式
     * @return 升级反馈信息
     */
    String databaseSchemaUpgrade(Connection connection, String catalog, String schema);

    // ========================================================================
    // 九、缓存管理
    // ========================================================================

    /**
     * 清理所有缓存
     */
    void clearCache();

    /**
     * 清理指定缓存
     *
     * @param cacheName 缓存名称
     */
    void clearCache(String cacheName);

    /**
     * 获取缓存统计信息
     *
     * @return 缓存名称 -> 统计信息
     */
    Map<String, CacheStatistics> getCacheStatistics();

    // ========================================================================
    // 十、监控指标
    // ========================================================================

    /**
     * 获取实时指标
     *
     * @return 指标对象
     */
    Metrics getMetrics();

    /**
     * 获取历史指标（按时间区间）
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 指标列表
     */
    List<Metrics> getMetricsHistory(long startTime, long endTime);

    /**
     * 获取性能统计
     *
     * @return 性能统计对象
     */
    PerformanceStats getPerformanceStats();

    /**
     * 重置指标
     */
    void resetMetrics();

    /**
     * 创建指标查询构建器
     *
     * @return 指标查询构建器
     */
    MetricsQuery createMetricsQuery();

    /**
     * 删除指定时间之前的指标数据
     *
     * @param timestamp 时间戳（为null时删除所有）
     */
    void deleteMetrics(Date timestamp);

    /**
     * 删除指定时间之前且由特定报告者报告的指标数据
     *
     * @param timestamp 时间戳（为null时匹配所有）
     * @param reporter  报告者（为null时匹配所有）
     */
    void deleteMetrics(Date timestamp, String reporter);

    /**
     * 强制提交当前收集的指标到数据库
     */
    void reportDbMetricsNow();

    /**
     * 计算唯一任务工作者数量（基于用户任务办理人）
     *
     * @param startTime 开始时间（包含）
     * @param endTime   结束时间（不包含）
     * @return 唯一任务工作者数量
     */
    long getUniqueTaskWorkerCount(Date startTime, Date endTime);

    /**
     * 删除指定时间之前的任务指标
     *
     * @param timestamp 时间戳（为null时删除所有）
     */
    void deleteTaskMetrics(Date timestamp);

    // ========================================================================
    // 十一、统计查询
    // ========================================================================

    /**
     * 创建流程定义统计查询
     *
     * @return 流程定义统计查询构建器
     */
    ProcessDefinitionStatisticsQuery createProcessDefinitionStatisticsQuery();

    /**
     * 创建部署统计查询
     *
     * @return 部署统计查询构建器
     */
    DeploymentStatisticsQuery createDeploymentStatisticsQuery();

    /**
     * 创建活动统计查询
     *
     * @param processDefinitionId 流程定义ID
     * @return 活动统计查询构建器
     */
    ActivityStatisticsQuery createActivityStatisticsQuery(String processDefinitionId);

    // ========================================================================
    // 十二、批次管理
    // ========================================================================

    /**
     * 创建批次查询构建器
     *
     * @return 批次查询构建器
     */
    BatchQuery createBatchQuery();

    /**
     * 挂起批次
     * <p>
     * 同时挂起该批次相关的所有作业定义和作业
     *
     * @param batchId 批次ID
     */
    void suspendBatchById(String batchId);

    /**
     * 激活批次
     * <p>
     * 同时激活该批次相关的所有作业定义和作业
     *
     * @param batchId 批次ID
     */
    void activateBatchById(String batchId);

    /**
     * 删除批次
     *
     * @param batchId 批次ID
     * @param cascade 是否级联删除历史批次实例和历史作业日志
     */
    void deleteBatch(String batchId, boolean cascade);

    /**
     * 创建批次统计查询
     *
     * @return 批次统计查询构建器
     */
    BatchStatisticsQuery createBatchStatisticsQuery();

    // ========================================================================
    // 十三、属性管理
    // ========================================================================

    /**
     * 获取所有属性
     *
     * @return 属性键值对
     */
    Map<String, String> getProperties();

    /**
     * 设置属性值
     *
     * @param name  属性名
     * @param value 属性值
     */
    void setProperty(String name, String value);

    /**
     * 删除属性
     *
     * @param name 属性名
     */
    void deleteProperty(String name);

    // ========================================================================
    // 十四、许可管理
    // ========================================================================

    /**
     * 设置许可证密钥
     *
     * @param licenseKey 许可证密钥字符串
     */
    void setLicenseKey(String licenseKey);

    /**
     * 获取许可证密钥
     *
     * @return 许可证密钥，未设置时返回null
     */
    String getLicenseKey();

    /**
     * 删除许可证密钥
     */
    void deleteLicenseKey();

    // ========================================================================
    // 十五、作业执行器部署注册
    // ========================================================================

    /**
     * 获取已注册到作业执行器的部署集合
     *
     * @return 部署ID集合
     */
    Set<String> getRegisteredDeployments();

    /**
     * 为作业执行器注册部署
     *
     * @param deploymentId 部署ID
     */
    void registerDeploymentForJobExecutor(String deploymentId);

    /**
     * 为作业执行器注销部署
     *
     * @param deploymentId 部署ID
     */
    void unregisterDeploymentForJobExecutor(String deploymentId);

    // ========================================================================
    // 十六、Schema日志查询
    // ========================================================================

    /**
     * 创建Schema日志查询构建器
     *
     * @return Schema日志查询构建器
     */
    SchemaLogQuery createSchemaLogQuery();

    // ========================================================================
    // 十七、遥测数据
    // ========================================================================

    /**
     * 获取当前收集的遥测数据
     * <p>
     * 遥测数据包含关于安装环境的多方面信息
     *
     * @return 遥测数据对象
     */
    TelemetryData getTelemetryData();
}