package com.yaocode.sts.components.file.runtime.model.command;

import com.yaocode.sts.components.file.runtime.model.dto.FileObjectDto;
import lombok.Builder;
import lombok.Data;

/**
 * 可恢复上传命令
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class ResumableUploadCommand {
    /** 上传的文件 */
    private FileObjectDto file;

    /** 文件ID（用于断点续传） */
    private String fileId;

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

    /** 租户ID */
    private String tenantId;

    /** 用户ID */
    private String userId;
}
