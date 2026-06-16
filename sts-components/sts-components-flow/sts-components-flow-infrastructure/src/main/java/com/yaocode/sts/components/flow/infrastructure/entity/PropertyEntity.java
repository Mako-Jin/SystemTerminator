package com.yaocode.sts.components.flow.infrastructure.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统属性实体
 * 对应表: flow_tbl_property
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("flow_tbl_property")
public class PropertyEntity extends BaseEntity {

    /**
     * 属性名称（主键）
     */
    @TableId
    private String propertyName;

    /**
     * 属性值
     */
    private String propertyValue;

    /**
     * 乐观锁版本号
     */
    @Version
    private Integer rev;

    /**
     * 属性描述
     */
    private String description;
}

