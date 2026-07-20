package com.yaocode.sts.file.runtime.model.command;

import com.yaocode.sts.file.runtime.model.dto.FileObjectDto;
import lombok.Builder;
import lombok.Data;

/**
 * 自动上传命令
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class AutoUploadCommand {
    /** 上传的文件 */
    private FileObjectDto file;

    /** 存储类型 */
    private String storageType;

    /** 业务ID */
    private String businessId;

    /** 业务类型 */
    private String businessType;

    /** 分片阈值（字节） */
    private Long chunkThreshold;

    /** 文件标签 */
    private String tags;

    /** 文件描述 */
    private String description;

    /** 是否公开 */
    private Boolean isPublic;

    /** 租户ID */
    private String tenantId;

    /** 用户ID */
    private String userId;
}