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
 * 告警配置实体
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("scheduler_tbl_alarm_config")
public class AlarmConfigEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 告警编码 */
    private String alarmCode;

    /** 告警名称 */
    private String alarmName;

    /** 告警方式: email/dingtalk/wechat/sms/phone/webhook */
    private String alarmType;

    /** 告警配置(JSON) */
    private String alarmConfig;

    /** 告警模板 */
    private String alarmTemplate;

    /** 告警条件(表达式) */
    private String alarmCondition;

    /** 限流时间(秒) */
    private Integer throttleTime;

    /** 状态: 0-禁用 1-启用 */
    private Integer status;

    /** 优先级: 1-10 */
    private Integer priority;

    // ========== 审计字段 ==========
    private String createBy;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
