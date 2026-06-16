package com.yaocode.sts.components.flow.infrastructure.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 过滤器实体
 * 对应表: flow_tbl_filter
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "flow_tbl_filter", autoResultMap = true)
public class FilterEntity extends BaseEntity {

    @TableId
    private String filterId;

    private String filterKey;

    private Integer resourceType;

    private String filterName;

    private String description;

    private String owner;

    private String ownerName;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> queryCondition;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<Map<String, Object>> sortOrder;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> properties;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> filterCondition;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> visualizationConfig;

    private String cronExpression;

    private LocalDateTime lastExecutedTime;

    private Integer cacheTtlSeconds;

    private LocalDateTime lastCacheTime;

    private Integer useCount;

    private LocalDateTime lastUseTime;

    private Integer status;

    private Integer isPublic;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> grantedUsers;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> grantedGroups;

    private Integer version;

    @Version
    private Integer rev;
}
