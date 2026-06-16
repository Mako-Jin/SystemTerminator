package com.yaocode.sts.components.flow.infrastructure.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 流程定义实体
 * 对应表: flow_tbl_process_definition
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("flow_tbl_process_definition")
public class ProcessDefinitionEntity extends BaseEntity {

    /**
     * 流程定义ID（主键）
     */
    @TableId
    private String processId;

    /**
     * 流程定义KEY（业务标识）
     */
    private String processKey;

    /**
     * 版本号
     */
    private Integer processVersion;

    /**
     * 是否最新版本（1=是，0=否）
     */
    private Integer isLatest;

    /**
     * 部署ID
     */
    private String deploymentId;

    /**
     * 资源文件名
     */
    private String resourceName;

    /**
     * 流程图资源文件名
     */
    private String diagramResourceName;

    /**
     * 流程名称
     */
    private String processName;

    /**
     * 流程描述
     */
    private String processDesc;

    /**
     * 分类
     */
    private String category;

    /**
     * 版本标签
     */
    private String versionTag;

    /**
     * 状态（1=启用，0=禁用，2=挂起）
     */
    private Integer processStatus;

    /**
     * 是否可启动新实例（1=是，0=否）
     */
    private Integer startable;

    /**
     * 是否有启动表单（1=是，0=否）
     */
    private Integer hasStartFormKey;

    /**
     * 部署时间
     */
    private LocalDateTime deployTime;

    /**
     * 部署人ID
     */
    private String deployUserId;

    /**
     * 部署人名称
     */
    private String deployUserName;

    /**
     * 来源类型（1=BPMN XML，2=JSON）
     */
    private Integer sourceType;

    /**
     * 源文件对象存储URL
     */
    private String sourceFileId;

    /**
     * 流程图对象存储URL
     */
    private String diagramFileId;

    /**
     * 历史数据保留天数
     */
    private Integer historyTtl;

    /**
     * 乐观锁版本号
     */
    @Version
    private Integer rev;
}
