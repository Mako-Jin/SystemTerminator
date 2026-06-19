package com.yaocode.sts.components.flow.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yaocode.sts.components.flow.infrastructure.entity.JobEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 异步作业 Mapper
 * 对应表: flow_tbl_job
 */
@Mapper
public interface JobMapper extends BaseMapper<JobEntity> {

    // ==================== XML 复杂查询 ====================

    /**
     * 查询待执行作业（调度器用）
     * XML: JobMapper.xml
     */
    List<JobEntity> selectDueJobs(
            @Param("currentTime") LocalDateTime currentTime,
            @Param("limit") Integer limit
    );

    /**
     * 锁定作业（带乐观锁）
     * XML: JobMapper.xml
     */
    int lockJob(
            @Param("jobId") String jobId,
            @Param("lockOwner") String lockOwner,
            @Param("lockExpireTime") LocalDateTime lockExpireTime,
            @Param("rev") Integer rev
    );

    /**
     * 分页查询作业
     * XML: JobMapper.xml
     */
    List<JobEntity> selectPageList(
            @Param("page") Page<JobEntity> page,
            @Param("status") Integer status,
            @Param("jobType") Integer jobType,
            @Param("processInstanceId") String processInstanceId,
            @Param("processKey") String processKey,
            @Param("dueDateFrom") LocalDateTime dueDateFrom,
            @Param("dueDateTo") LocalDateTime dueDateTo
    );

    /**
     * 清理过期锁
     * XML: JobMapper.xml
     */
    int cleanupExpiredLocks(@Param("currentTime") LocalDateTime currentTime);

}
