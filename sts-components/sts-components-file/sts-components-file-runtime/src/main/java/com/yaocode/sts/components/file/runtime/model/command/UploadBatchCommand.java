package com.yaocode.sts.components.file.runtime.model.command;

import com.yaocode.sts.components.file.runtime.model.dto.FileObjectDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 批量上传命令
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class UploadBatchCommand {
    /** 上传的文件列表 */
    private List<FileObjectDto> files;

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

    /** 租户ID */
    private String tenantId;

    /** 用户ID */
    private String userId;
}
