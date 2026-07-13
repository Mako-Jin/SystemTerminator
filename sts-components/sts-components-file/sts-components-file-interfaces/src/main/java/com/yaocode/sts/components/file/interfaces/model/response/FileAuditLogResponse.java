package com.yaocode.sts.components.file.interfaces.model.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 文件审计日志
 */
@Data
@Builder
public class FileAuditLogResponse {

    /**
     * 日志ID
     */
    private Long logId;
    /**
     * 文件ID
     */
    private String fileId;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 操作类型
     * 0-上传, 1-下载, 2-审核
     */
    private Integer operationType;
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
     * 错误消息
     */
    private String errorMessage;
    /**
     * 消耗时间（毫秒）
     * 仅对成功操作有效
     */
    private Long costTime;
    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    private String operationTypeDesc;

    private Map<String, Object> extraData;

}
