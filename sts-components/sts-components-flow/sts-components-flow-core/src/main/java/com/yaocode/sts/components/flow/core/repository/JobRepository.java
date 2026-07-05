package com.yaocode.sts.components.flow.core.repository;

import com.yaocode.sts.components.flow.core.model.Job;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 作业仓储接口
 */
public interface JobRepository {

    /**
     * 根据ID查询作业
     */
    Job findById(String jobId);

    /**
     * 查询待执行作业
     */
    List<Job> findDueJobs(LocalDateTime currentTime, int limit);

    /**
     * 根据流程实例ID查询作业
     */
    List<Job> findByProcessInstanceId(String processInstanceId);

    /**
     * 根据作业定义ID查询作业
     */
    List<Job> findByJobDefinitionId(String jobDefinitionId);

    /**
     * 保存作业
     */
    void save(Job job);

    /**
     * 锁定作业
     */
    boolean lockJob(String jobId, String lockOwner, LocalDateTime lockExpireTime);

    /**
     * 作业执行成功
     */
    boolean success(String jobId, LocalDateTime executedTime);

    /**
     * 作业执行失败（重试）
     */
    boolean fail(String jobId, LocalDateTime nextRetryTime, String errorMessage, String stackFileId);

    /**
     * 取消作业
     */
    boolean cancel(String jobId);

    /**
     * 清理过期锁
     */
    int cleanupExpiredLocks(LocalDateTime currentTime);

    /**
     * 分页查询作业
     */
    List<Job> findPage(int offset, int limit, Integer status, String processInstanceId);

    /**
     * 批量删除作业
     */
    int deleteByProcessInstanceId(String processInstanceId);
}
