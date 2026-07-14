package com.yaocode.sts.components.file.runtime.model.command;

import lombok.Builder;
import lombok.Data;

import java.io.InputStream;

/**
 * 流式上传命令
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class StreamUploadCommand {
    /** 文件名 */
    private String fileName;

    /** 文件大小（字节） */
    private Long fileSize;

    /** 输入流 */
    private InputStream inputStream;

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

    /** 是否分块传输 */
    private Boolean chunked;

    /** 租户ID */
    private String tenantId;

    /** 用户ID */
    private String userId;
}
