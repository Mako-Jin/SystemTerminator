package com.yaocode.sts.components.file.runtime.model.command;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * URL拉取命令
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class UrlFetchCommand {
    /** 文件URL */
    private String fileUrl;

    /** 文件名（可选） */
    private String fileName;

    /** 存储类型 */
    private String storageType;

    /** 业务ID */
    private String businessId;

    /** 业务类型 */
    private String businessType;

    /** 文件标签 */
    private String tags;

    /** 文件描述 */
    private String description;

    /** 是否公开 */
    private Boolean isPublic;

    /** 超时时间（秒） */
    private Integer timeout;

    /** 自定义请求头 */
    private Map<String, String> headers;

    /** 自定义元数据 */
    private Map<String, String> metadata;

    /** 租户ID */
    private String tenantId;

    /** 用户ID */
    private String userId;
}
