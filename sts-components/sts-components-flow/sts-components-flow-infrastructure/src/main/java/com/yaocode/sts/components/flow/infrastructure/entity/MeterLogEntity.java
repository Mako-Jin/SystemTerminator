package com.yaocode.sts.components.flow.infrastructure.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 计量日志实体
 * 对应表: flow_tbl_meter_log
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "flow_tbl_meter_log", autoResultMap = true)
public class MeterLogEntity extends BaseEntity {

    @TableId
    private String meterLogId;

    private String meterName;

    private Integer meterType;

    private Long meterValue;

    private String meterUnit;

    private Integer aggregationType;

    private LocalDateTime recordTime;

    private Long recordMilliseconds;

    private String groupByKey;

    private String groupByValue;

    private String reporter;

    private String reporterHost;

    private String processInstanceId;

    private String processId;

    private String processKey;

    private String nodeId;

    private String nodeKey;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, String> tags;
}
