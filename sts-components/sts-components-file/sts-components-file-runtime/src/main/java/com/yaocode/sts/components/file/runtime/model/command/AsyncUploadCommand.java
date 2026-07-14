package com.yaocode.sts.components.file.runtime.model.command;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * 异步上传命令
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class AsyncUploadCommand {
    /** 文件名 */
    private String fileName;

    /** 文件大小（字节） */
    private Long fileSize;

    /** 文件MD5 */
    private String fileMd5;

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

    /** 自定义元数据 */
    private Map<String, String> metadata;

    /** Base64编码的文件内容 */
    private String fileContentBase64;

    /** 文件URL（从URL拉取） */
    private String fileUrl;

    /** 回调URL */
    private String callbackUrl;

    /** 回调方法（GET/POST） */
    private String callbackMethod;

    /** 回调请求头 */
    private Map<String, String> callbackHeaders;

    /** 任务优先级（1-10） */
    private Integer priority;

    /** 超时时间（秒） */
    private Integer timeoutSeconds;

    /** 完成后是否删除 */
    private Boolean deleteAfterComplete;

    /** 租户ID */
    private String tenantId;

    /** 用户ID */
    private String userId;
}
