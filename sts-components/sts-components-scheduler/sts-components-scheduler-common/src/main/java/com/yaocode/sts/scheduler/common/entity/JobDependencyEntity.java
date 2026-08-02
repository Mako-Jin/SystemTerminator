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
 * 任务依赖关系实体
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("scheduler_tbl_job_dependency")
public class JobDependencyEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 任务ID(依赖方) */
    private Long jobId;

    /** 被依赖任务ID */
    private Long dependJobId;

    /** 依赖类型: 1-强依赖 2-弱依赖 3-可选依赖 */
    private Integer dependType;

    /** 条件类型: 1-成功 2-完成 3-失败 4-任意 */
    private Integer conditionType;

    /** 依赖超时时间(秒) */
    private Integer timeout;

    /** 执行顺序 */
    private Integer sortOrder;

    /** 状态: 0-禁用 1-启用 */
    private Integer status;

    // ========== 审计字段 ==========
    private String createBy;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
