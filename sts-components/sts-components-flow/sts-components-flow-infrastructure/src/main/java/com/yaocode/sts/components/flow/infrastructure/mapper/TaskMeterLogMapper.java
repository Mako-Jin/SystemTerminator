package com.yaocode.sts.components.flow.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yaocode.sts.components.flow.infrastructure.entity.TaskMeterLogEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 任务计量日志 Mapper
 * 对应表: flow_tbl_task_meter_log
 */
@Mapper
public interface TaskMeterLogMapper extends BaseMapper<TaskMeterLogEntity> {

    /**
     * 批量插入任务计量日志
     * XML: TaskMeterLogMapper.xml
     */
    int batchInsert(@Param("list") List<TaskMeterLogEntity> taskMeterLogs);

    /**
     * 清理过期数据
     * XML: TaskMeterLogMapper.xml
     */
    int cleanup(@Param("beforeTime") LocalDateTime beforeTime,
                @Param("limit") Integer limit);

}
