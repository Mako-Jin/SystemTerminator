package com.yaocode.sts.scheduler.common.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 任务信息实体
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("scheduler_tbl_job_info")
public class JobInfoEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 任务编码(唯一) */
    private String jobCode;

    /** 任务名称 */
    private String jobName;

    /** 任务描述 */
    private String jobDesc;

    /** 任务分组 */
    private String jobGroup;

    /** 任务类型: 1-BEAN 2-REST 3-RPC 4-SCRIPT 5-SQL 6-MQ */
    private Integer jobType;

    /** 任务分类: 1-定时任务 2-实时任务 3-依赖任务 4-流程任务 */
    private Integer jobCategory;

    /** 优先级: 1-10, 数字越大优先级越高 */
    private Integer priority;

    /** 执行器ID */
    private Long executorId;

    /** 执行器地址(冗余) */
    private String executorAddress;

    /** 执行处理器(Bean名称) */
    private String executorHandler;

    /** 执行方法名 */
    private String executorMethod;

    /** 执行参数(JSON格式) */
    private String executorParams;

    // ========== REST类型字段 ==========
    /** REST请求URL */
    private String restUrl;

    /** REST请求方式: GET/POST/PUT/DELETE */
    private String restMethod;

    /** REST请求头(JSON) */
    private String restHeaders;

    /** REST请求体 */
    private String restBody;

    /** REST超时时间(秒) */
    private Integer restTimeout;

    // ========== RPC类型字段 ==========
    /** RPC接口全类名 */
    private String rpcInterface;

    /** RPC方法名 */
    private String rpcMethod;

    /** RPC版本 */
    private String rpcVersion;

    /** RPC分组 */
    private String rpcGroup;

    /** RPC参数类型(逗号分隔) */
    private String rpcParamTypes;

    /** RPC参数值(JSON) */
    private String rpcParams;

    // ========== SCRIPT类型字段 ==========
    /** 脚本类型: shell/python/nodejs/groovy */
    private String scriptType;

    /** 脚本内容 */
    private String scriptContent;

    /** 脚本文件路径 */
    private String scriptFile;

    // ========== SQL类型字段 ==========
    /** 数据源名称 */
    private String sqlDatasource;

    /** SQL内容 */
    private String sqlContent;

    /** SQL类型: select/update/insert/delete */
    private String sqlType;

    // ========== MQ类型字段 ==========
    /** MQ主题/队列 */
    private String mqTopic;

    /** MQ标签 */
    private String mqTag;

    /** MQ消息内容 */
    private String mqMessage;

    // ========== 调度配置 ==========
    /** 调度类型: 1-CRON 2-固定间隔 3-固定延迟 4-日历 */
    private Integer scheduleType;

    /** Cron表达式 */
    private String cronExpression;

    /** 固定间隔(毫秒) */
    private Long fixedRate;

    /** 固定延迟(毫秒) */
    private Long fixedDelay;

    /** 初始延迟(毫秒) */
    private Long initialDelay;

    /** 调度额外配置(JSON) */
    private String scheduleConfig;

    // ========== 执行配置 ==========
    /** 超时时间(秒)，0表示不超时 */
    private Integer timeout;

    /** 失败重试次数 */
    private Integer retryCount;

    /** 重试间隔(秒) */
    private Integer retryInterval;

    /** 重试策略: 1-固定间隔 2-指数退避 3-自定义 */
    private Integer retryStrategy;

    /** 重试配置(JSON) */
    private String retryConfig;

    /** 分片总数 */
    private Integer shardingTotal;

    /** 路由策略: FIRST/LAST/ROUND/RANDOM/LFU/LRU */
    private String routeStrategy;

    /** 失败策略: 1-重试 2-忽略 3-告警 4-终止 */
    private Integer failStrategy;

    // ========== 状态控制 ==========
    /** 任务状态: 0-禁用 1-启用 2-暂停 3-废弃 */
    private Integer jobStatus;

    /** 执行状态: 0-空闲 1-执行中 2-阻塞 */
    private Integer executeStatus;

    /** 最后执行时间 */
    private LocalDateTime lastExecuteTime;

    /** 下次执行时间 */
    private LocalDateTime nextExecuteTime;

    /** 最后执行结果 */
    private String lastExecuteResult;

    // ========== 告警配置 ==========
    /** 是否启用告警: 0-否 1-是 */
    private Integer alarmEnabled;

    /** 告警方式: email,dingtalk,wechat,sms,phone */
    private String alarmType;

    /** 告警接收人(JSON) */
    private String alarmReceivers;

    /** 告警模板 */
    private String alarmTemplate;

    /** 告警条件(JSON) */
    private String alarmCondition;

    // ========== 依赖配置 ==========
    /** 依赖任务ID列表(JSON) */
    private String dependJobs;

    /** 依赖类型: 1-全部成功 2-任一成功 3-全部完成 */
    private Integer dependType;

    /** 超时策略: 1-停止 2-继续 3-告警 */
    private Integer timeoutStrategy;

    // ========== 扩展字段 ==========
    /** 扩展配置(JSON) */
    private String extConfig;

    /** 标签(逗号分隔) */
    private String tags;

    /** 负责人 */
    private String owner;

    /** 所属部门 */
    private String department;

    // ========== 审计字段 ==========
    private String createBy;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    private String updateBy;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @Version
    private Integer version;
}
