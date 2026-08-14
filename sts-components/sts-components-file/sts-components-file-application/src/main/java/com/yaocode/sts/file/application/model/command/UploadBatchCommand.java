package com.yaocode.sts.file.application.model.command;

import com.yaocode.sts.file.application.model.dto.FileObjectDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

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
    private Integer storageType;

    /** 文件标签 */
    private String tags;

    /** 文件描述 */
    private String description;

    /** 是否公开 */
    private Integer isPublic;

    /** 租户ID */
    private String tenantId;

    /** 用户ID */
    private String userId;

    /** 存储桶 */
    private String bucket;

    /** 是否启用去重 */
    private Integer enableDeduplication;

    /** 自定义元数据 */
    private Map<String, String> metadata;
}
