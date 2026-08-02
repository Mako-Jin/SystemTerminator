package com.yaocode.sts.scheduler.common.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 任务统计实体
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("scheduler_job_statistics")
public class JobStatisticsEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 任务ID */
    private Long jobId;

    /** 统计日期 */
    private LocalDate statDate;

    /** 执行次数 */
    private Integer executeCount;

    /** 成功次数 */
    private Integer successCount;

    /** 失败次数 */
    private Integer failCount;

    /** 超时次数 */
    private Integer timeoutCount;

    /** 平均耗时(毫秒) */
    private Long avgDuration;

    /** 最大耗时(毫秒) */
    private Long maxDuration;

    /** 最小耗时(毫秒) */
    private Long minDuration;

    /** 成功率(%) */
    private BigDecimal successRate;

    // ========== 审计字段 ==========
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
