package com.yaocode.sts.components.flow.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yaocode.sts.components.flow.infrastructure.entity.MeterLogEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 计量日志 Mapper
 * 对应表: flow_tbl_meter_log
 */
@Mapper
public interface MeterLogMapper extends BaseMapper<MeterLogEntity> {

    /**
     * 批量插入计量日志
     * XML: MeterLogMapper.xml
     */
    int batchInsert(@Param("list") List<MeterLogEntity> meterLogs);

    /**
     * 清理过期数据
     * XML: MeterLogMapper.xml
     */
    int cleanup(
            @Param("beforeTime") LocalDateTime beforeTime,
            @Param("limit") Integer limit
    );

}
