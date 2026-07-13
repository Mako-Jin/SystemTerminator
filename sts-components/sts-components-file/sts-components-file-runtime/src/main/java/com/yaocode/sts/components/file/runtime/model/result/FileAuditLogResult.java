package com.yaocode.sts.components.file.runtime.model.result;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 文件审计日志结果
 */
@Data
@Builder
public class FileAuditLogResult {

    /**
     * 日志ID
     */
    private Long logId;

    /**
     * 文件ID
     */
    private String fileId;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 操作类型
     */
    private Integer operationType;

    /**
     * 操作类型描述
     */
    private String operationTypeDesc;

    /**
     * 操作描述
     */
    private String operationDesc;

    /**
     * IP地址
     */
    private String ipAddress;

    /**
     * 用户代理
     */
    private String userAgent;

    /**
     * 是否成功
     */
    private Boolean success;

    /**
     * 错误信息
     */
    private String errorMessage;

    /**
     * 耗时（毫秒）
     */
    private Long costTime;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 扩展数据
     */
    private Map<String, Object> extraData;
}