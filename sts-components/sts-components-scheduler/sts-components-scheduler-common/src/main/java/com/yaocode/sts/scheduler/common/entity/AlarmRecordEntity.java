package com.yaocode.sts.scheduler.common.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 告警记录实体
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("scheduler_tbl_alarm_record")
public class AlarmRecordEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 任务ID */
    private Long jobId;

    /** 任务编码 */
    private String jobCode;

    /** 任务名称 */
    private String jobName;

    /** 关联的日志ID */
    private Long logId;

    /** 告警配置ID */
    private Long alarmConfigId;

    /** 告警方式 */
    private String alarmType;

    /** 告警标题 */
    private String alarmTitle;

    /** 告警内容 */
    private String alarmContent;

    /** 告警级别: INFO/WARN/ERROR/CRITICAL */
    private String alarmLevel;

    /** 告警状态: 0-待发送 1-发送中 2-发送成功 3-发送失败 */
    private Integer alarmStatus;

    /** 告警时间 */
    private LocalDateTime alarmTime;

    /** 发送结果 */
    private String sendResult;

    /** 重试次数 */
    private Integer retryTimes;

    // ========== 审计字段 ==========
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
