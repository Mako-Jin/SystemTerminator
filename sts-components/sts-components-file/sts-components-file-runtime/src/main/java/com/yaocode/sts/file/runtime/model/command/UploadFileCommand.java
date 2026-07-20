package com.yaocode.sts.file.runtime.model.command;

import com.yaocode.sts.file.runtime.model.dto.FileObjectDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 上传文件命令
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class UploadFileCommand {
    /** 上传的文件 */
    private FileObjectDto file;

    /** 文件名 */
    private String fileName;

    /** 文件大小（字节） */
    private Long fileSize;

    /** 文件MD5 */
    private String fileMd5;

    /** 存储类型 */
    private Integer storageType;

    /** 业务ID */
    private String businessId;

    /** 业务类型 */
    private String businessType;

    /** 是否启用去重 */
    private Boolean enableDeduplication;

    /** 文件标签（逗号分隔） */
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

    /** 优先存储列表 */
    private List<String> preferredStorages;

    /** 策略名称 */
    private String strategy;
}