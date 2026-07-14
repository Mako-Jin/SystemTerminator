package com.yaocode.sts.components.file.runtime.model.command;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * 完成分片上传命令
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class CompleteMultipartCommand {
    /** 上传ID */
    private String uploadId;

    /** 文件ID */
    private String fileId;

    /** 文件名（可覆盖） */
    private String fileName;

    /** 文件描述 */
    private String description;

    /** 文件标签 */
    private String tags;

    /** 是否公开 */
    private Boolean isPublic;

    /** 自定义元数据 */
    private Map<String, String> metadata;

    /** 租户ID */
    private String tenantId;

    /** 用户ID */
    private String userId;
}
