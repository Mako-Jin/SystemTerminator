package com.yaocode.sts.components.flow.infrastructure.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

/**
 * 边界事件定义实体
 * 对应表: flow_tbl_boundary_event_definition
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "flow_tbl_boundary_event_definition", autoResultMap = true)
public class BoundaryEventDefinitionEntity extends BaseEntity {

    @TableId
    private String eventId;

    private String processId;

    private String eventKey;

    private String attachedNodeId;

    private String attachedNodeKey;

    private Integer eventType;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> configuration;

    private Integer cancelActivity;

    private Integer version;
}