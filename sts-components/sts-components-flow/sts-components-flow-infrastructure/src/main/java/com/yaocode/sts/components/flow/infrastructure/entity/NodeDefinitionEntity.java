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
 * 节点定义实体
 * 对应表: flow_tbl_node_definition
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "flow_tbl_node_definition", autoResultMap = true)
public class NodeDefinitionEntity extends BaseEntity {

    @TableId
    private String nodeId;

    private String processId;

    private String processKey;

    private String nodeKey;

    private String nodeName;

    private Integer nodeType;

    private Integer nodeOrder;

    private Integer positionX;

    private Integer positionY;

    private Integer width;

    private Integer height;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> configuration;

    private Integer timeLimit;

    private Integer timeLimitAction;

    private String escalateUserId;

    private String escalateUserName;

    private Integer isMultiInstance;

    private Integer multiInstanceType;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> multiInstanceConfig;

    private Integer asyncBefore;

    private Integer asyncAfter;

    private Integer exclusive;

    private String eventSubType;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> eventConfig;

    private Integer nodeVersion;

    @Version
    private Integer rev;
}
