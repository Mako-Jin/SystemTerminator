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
 * 消息队列实体
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("scheduler_message_queue")
public class MessageQueueEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 主题 */
    private String topic;

    /** 标签 */
    private String tag;

    /** 消息键 */
    private String messageKey;

    /** 消息体 */
    private String messageBody;

    /** 消息状态: 0-待处理 1-处理中 2-已处理 3-失败 */
    private Integer messageStatus;

    /** 重试次数 */
    private Integer retryTimes;

    /** 最大重试次数 */
    private Integer maxRetry;

    /** 延迟时间(毫秒) */
    private Long delayTime;

    /** 生产者 */
    private String producer;

    /** 消费者 */
    private String consumer;

    /** 错误信息 */
    private String errorMsg;

    // ========== 审计字段 ==========
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
