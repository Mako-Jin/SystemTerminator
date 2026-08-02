package com.yaocode.sts.scheduler.common.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 任务分组实体
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("scheduler_tbl_job_group")
public class JobGroupEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 分组编码 */
    private String groupCode;

    /** 分组名称 */
    private String groupName;

    /** 分组描述 */
    private String groupDesc;

    /** 父分组ID(用于树形结构) */
    private Long parentId;

    /** 排序号 */
    private Integer sortOrder;

    /** 图标 */
    private String icon;

    /** 颜色标识 */
    private String color;

    /** 状态: 0-禁用 1-启用 */
    private Integer status;

    /** 负责人 */
    private String owner;

    // ========== 审计字段 ==========
    private String createBy;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    // ========== 非表字段 ==========
    @TableField(exist = false)
    private List<JobGroupEntity> children;
}
