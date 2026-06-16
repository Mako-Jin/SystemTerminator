package com.yaocode.sts.components.flow.infrastructure.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

/**
 * 事件订阅定义实体
 * 对应表: flow_tbl_event_subscription_definition
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "flow_tbl_event_subscription_definition", autoResultMap = true)
public class EventSubscriptionDefinitionEntity extends BaseEntity {

    @TableId
    private String subscriptionId;

    private String processId;

    private String processKey;

    private Integer eventType;

    private String eventName;

    private String eventDesc;

    private Integer targetType;

    private String targetNodeId;

    private String targetNodeKey;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> configuration;

    private Integer version;

    @Version
    private Integer rev;
}
