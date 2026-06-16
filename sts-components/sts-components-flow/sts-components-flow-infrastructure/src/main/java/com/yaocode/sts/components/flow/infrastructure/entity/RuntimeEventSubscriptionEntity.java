package com.yaocode.sts.components.flow.infrastructure.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 运行时事件订阅实体
 * 对应表: flow_tbl_runtime_event_subscription
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "flow_tbl_runtime_event_subscription", autoResultMap = true)
public class RuntimeEventSubscriptionEntity extends BaseEntity {

    @TableId
    private String subscriptionId;

    private String subscriptionDefinitionId;

    private String processInstanceId;

    private String executionId;

    private String processId;

    private String processKey;

    private Integer eventType;

    private String eventName;

    private String activityNodeId;

    private String activityNodeKey;

    private String activityName;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> configuration;

    private String correlationKey;

    private Integer isMqSent;

    private String mqMessageId;

    private Integer status;

    private LocalDateTime expireTime;

    private LocalDateTime triggeredTime;

    private String triggeredBy;

    @Version
    private Integer rev;
}
