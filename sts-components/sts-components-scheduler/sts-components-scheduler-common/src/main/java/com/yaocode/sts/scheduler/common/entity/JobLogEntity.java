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
 * 任务执行日志实体
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("scheduler_tbl_job_log")
public class JobLogEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 任务ID */
    private Long jobId;

    /** 任务编码(冗余) */
    private String jobCode;

    /** 任务名称(冗余) */
    private String jobName;

    /** 执行器ID */
    private Long executorId;

    /** 执行器地址 */
    private String executorAddress;

    // ========== 调度信息 ==========
    /** 调度时间 */
    private LocalDateTime scheduleTime;

    /** 触发类型: 1-自动调度 2-手动触发 3-重试触发 4-依赖触发 */
    private Integer triggerType;

    /** 触发来源(用户/系统) */
    private String triggerSource;

    // ========== 执行信息 ==========
    /** 开始执行时间 */
    private LocalDateTime startTime;

    /** 结束执行时间 */
    private LocalDateTime endTime;

    /** 执行耗时(毫秒) */
    private Long executeDuration;

    /** 执行状态: 0-进行中 1-成功 2-失败 3-超时 4-取消 5-跳过 */
    private Integer executeStatus;

    /** 执行结果数据 */
    private String executeResult;

    /** 错误码 */
    private String errorCode;

    /** 错误信息 */
    private String errorMsg;

    /** 错误堆栈 */
    private String errorStack;

    // ========== 分片信息 ==========
    /** 分片索引 */
    private Integer shardIndex;

    /** 分片总数 */
    private Integer shardTotal;

    /** 分片参数 */
    private String shardParams;

    // ========== 重试信息 ==========
    /** 当前重试次数 */
    private Integer retryTimes;

    /** 最大重试次数 */
    private Integer maxRetryTimes;

    /** 是否重试: 0-否 1-是 */
    private Integer isRetry;

    // ========== 日志信息 ==========
    /** 日志级别 */
    private String logLevel;

    /** 日志内容 */
    private String logContent;

    /** 日志文件路径 */
    private String logFile;

    /** 标准输出 */
    private String stdOut;

    /** 错误输出 */
    private String stdErr;

    // ========== 任务参数 ==========
    /** 实际执行参数 */
    private String executeParams;

    /** 上下文数据(JSON) */
    private String contextData;

    // ========== 告警信息 ==========
    /** 是否发送告警: 0-否 1-是 */
    private Integer alarmSent;

    /** 告警时间 */
    private LocalDateTime alarmTime;

    /** 告警结果 */
    private String alarmResult;

    // ========== 审计字段 ==========
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
