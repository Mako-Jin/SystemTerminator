package com.yaocode.sts.components.flow.core.engine.management;


import java.util.List;
import java.util.Map;

/**
 * 管理服务接口
 * 引擎管理、作业调度、监控指标
 *
 * @author Process Engine Team
 * @version 1.0.0
 */
public interface ManagementService {

    // ========== 引擎管理 ==========

    /**
     * 获取引擎信息
     */
    EngineInfo getEngineInfo();

    /**
     * 获取引擎配置
     */
    Map<String, Object> getEngineConfiguration();

    /**
     * 获取引擎版本
     */
    String getEngineVersion();

    /**
     * 执行引擎命令
     */
    <T> T executeCommand(Command<T> command);

    // ========== 作业管理 ==========

    /**
     * 查询所有作业
     */
    List<Job> getAllJobs();

    /**
     * 查询等待执行的作业
     */
    List<Job> getWaitingJobs();

    /**
     * 查询执行中的作业
     */
    List<Job> getRunningJobs();

    /**
     * 查询失败的作业
     */
    List<Job> getFailedJobs();

    /**
     * 获取作业详情
     */
    Job getJob(String jobId);

    /**
     * 执行作业（手动触发）
     */
    void executeJob(String jobId);

    /**
     * 取消作业
     */
    void cancelJob(String jobId);

    /**
     * 重试失败的作业
     */
    void retryJob(String jobId);

    /**
     * 设置作业的重试次数
     */
    void setJobRetries(String jobId, int retries);

    // ========== 数据库管理 ==========

    /**
     * 获取数据库表信息
     */
    List<TableInfo> getTableInfo();

    /**
     * 获取表总行数
     */
    long getTableRowCount(String tableName);

    /**
     * 执行原生SQL查询
     */
    List<Map<String, Object>> executeNativeQuery(String sql);

    /**
     * 执行原生SQL更新
     */
    int executeNativeUpdate(String sql);

    // ========== 缓存管理 ==========

    /**
     * 清理所有缓存
     */
    void clearCache();

    /**
     * 清理指定缓存
     */
    void clearCache(String cacheName);

    /**
     * 获取缓存统计信息
     */
    Map<String, CacheStatistics> getCacheStatistics();

    // ========== 监控指标 ==========

    /**
     * 获取实时指标
     */
    Metrics getMetrics();

    /**
     * 获取历史指标（按时间区间）
     */
    List<Metrics> getMetricsHistory(long startTime, long endTime);

    /**
     * 获取性能统计
     */
    PerformanceStats getPerformanceStats();

    /**
     * 重置指标
     */
    void resetMetrics();

}
