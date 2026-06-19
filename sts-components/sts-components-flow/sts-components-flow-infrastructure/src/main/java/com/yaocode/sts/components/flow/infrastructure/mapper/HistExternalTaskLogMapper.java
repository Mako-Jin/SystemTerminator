package com.yaocode.sts.components.flow.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yaocode.sts.components.flow.infrastructure.entity.HistExternalTaskLogEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 历史外部任务日志 Mapper
 * 对应表: flow_tbl_hist_external_task_log
 */
@Mapper
public interface HistExternalTaskLogMapper extends BaseMapper<HistExternalTaskLogEntity> {

    /**
     * 根据外部任务ID查询历史日志
     * XML: HistExternalTaskLogMapper.xml
     */
    List<HistExternalTaskLogEntity> selectByExternalTaskId(@Param("externalTaskId") String externalTaskId);

    /**
     * 根据流程实例ID查询历史外部任务日志
     * XML: HistExternalTaskLogMapper.xml
     */
    List<HistExternalTaskLogEntity> selectByProcessInstanceId(@Param("processInstanceId") String processInstanceId);

    /**
     * 批量清理历史外部任务日志
     * XML: HistExternalTaskLogMapper.xml
     */
    int batchCleanup(
            @Param("removalTime") LocalDateTime removalTime,
            @Param("limit") Integer limit
    );

}
