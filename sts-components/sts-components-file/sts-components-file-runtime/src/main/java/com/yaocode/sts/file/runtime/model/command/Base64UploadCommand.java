package com.yaocode.sts.file.runtime.model.command;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * Base64上传命令
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class Base64UploadCommand {
    /** 文件名 */
    private String fileName;

    /** Base64编码的文件内容 */
    private String base64Content;

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

    /** 租户ID */
    private String tenantId;

    /** 用户ID */
    private String userId;
}
