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
 * 系统配置实体
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("scheduler_config")
public class SystemConfigEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 配置键 */
    private String configKey;

    /** 配置值 */
    private String configValue;

    /** 配置描述 */
    private String configDesc;

    /** 配置类型: STRING/INT/BOOLEAN/JSON/ENUM */
    private String configType;

    /** 配置分组 */
    private String groupName;

    /** 状态: 0-禁用 1-启用 */
    private Integer status;

    /** 是否系统配置: 0-否 1-是 */
    private Integer isSystem;

    // ========== 审计字段 ==========
    private String createBy;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
