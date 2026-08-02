package com.yaocode.sts.scheduler.common.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 执行器信息实体
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("scheduler_tbl_executor_info")
public class ExecutorInfoEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 执行器名称 */
    private String executorName;

    /** 执行器编码(唯一) */
    private String executorCode;

    /** 执行器描述 */
    private String executorDesc;

    /** 执行器类型: 1-SpringBoot 2-普通Java 3-Go 4-Python */
    private Integer executorType;

    /** 主机IP */
    private String host;

    /** 端口 */
    private Integer port;

    /** 完整地址(host:port) */
    private String address;

    /** HTTP端口(用于管理) */
    private Integer httpPort;

    /** RPC端口 */
    private Integer rpcPort;

    // ========== 注册信息 ==========
    /** 注册时间 */
    private LocalDateTime registerTime;

    /** 最后心跳时间 */
    private LocalDateTime lastHeartbeat;

    /** 心跳间隔(秒) */
    private Integer heartbeatInterval;

    /** 状态: 0-离线 1-在线 2-繁忙 3-维护中 */
    private Integer status;

    // ========== 系统资源 ==========
    /** CPU使用率(%) */
    private BigDecimal cpuUsage;

    /** 内存使用率(%) */
    private BigDecimal memoryUsage;

    /** 磁盘使用率(%) */
    private BigDecimal diskUsage;

    /** 负载平均值 */
    private BigDecimal loadAverage;

    /** JVM内存使用(MB) */
    private Long jvmMemory;

    /** 线程数 */
    private Integer threadCount;

    /** 可用处理器数 */
    private Integer availableProcessors;

    // ========== 执行器能力 ==========
    /** 最大并发任务数 */
    private Integer maxJobs;

    /** 当前运行任务数 */
    private Integer runningJobs;

    /** 任务队列大小 */
    private Integer queueSize;

    /** 支持的任务类型(逗号分隔) */
    private String supportJobTypes;

    /** 支持的路由策略(逗号分隔) */
    private String supportRoutes;

    // ========== 版本信息 ==========
    /** 执行器版本 */
    private String versionTags;

    /** 调度器版本 */
    private String schedulerVersion;

    /** JDK版本 */
    private String jdkVersion;

    /** 操作系统信息 */
    private String osInfo;

    // ========== 扩展信息 ==========
    /** 标签(逗号分隔) */
    private String tags;

    /** 可用区 */
    private String zone;

    /** 环境: dev/test/prod */
    private String environment;

    /** 扩展配置(JSON) */
    private String extConfig;

    /** 备注 */
    private String remark;

    // ========== 审计字段 ==========
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @Version
    private Integer version;
}
