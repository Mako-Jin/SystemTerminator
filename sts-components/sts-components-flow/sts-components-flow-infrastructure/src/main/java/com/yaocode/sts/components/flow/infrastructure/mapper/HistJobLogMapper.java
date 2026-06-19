package com.yaocode.sts.components.flow.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yaocode.sts.components.flow.infrastructure.entity.HistJobLogEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 历史作业日志 Mapper
 * 对应表: flow_tbl_hist_job_log
 */
@Mapper
public interface HistJobLogMapper extends BaseMapper<HistJobLogEntity> {

    /**
     * 根据作业ID查询历史日志
     * XML: HistJobLogMapper.xml
     */
    List<HistJobLogEntity> selectByJobId(@Param("jobId") String jobId);

    /**
     * 根据流程实例ID查询历史作业日志
     * XML: HistJobLogMapper.xml
     */
    List<HistJobLogEntity> selectByProcessInstanceId(@Param("processInstanceId") String processInstanceId);

    /**
     * 批量清理历史作业日志
     * XML: HistJobLogMapper.xml
     */
    int batchCleanup(
            @Param("removalTime") LocalDateTime removalTime,
            @Param("limit") Integer limit
    );

}
