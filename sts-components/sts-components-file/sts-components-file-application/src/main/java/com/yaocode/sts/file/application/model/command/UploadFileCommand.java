package com.yaocode.sts.file.application.model.command;

import com.yaocode.sts.file.application.model.dto.FileObjectDto;
import com.yaocode.sts.file.core.enums.DuplicateFileStrategyEnums;
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

    /** 存储桶 */
    private String bucket;

    /** 是否启用去重 */
    private Integer enableDeduplication;

    /** 文件标签（逗号分隔） */
    private String tags;

    /** 文件描述 */
    private String description;

    /** 是否公开 */
    private Integer isPublic;

    /** 自定义元数据 */
    private Map<String, String> metadata;

    /** 租户ID */
    private String tenantId;

    /** 用户ID */
    private String userId;
    /** 用户名 */
    private String username;

    /** 优先存储列表 */
    private List<String> preferredStorages;

    /** 策略名称 */
    private String strategy;

    /**
     * 文件SHA-256（用于安全校验）
     */
    private String fileSha256;

    /**
     * 重复文件处理策略
     */
    private DuplicateFileStrategyEnums duplicateStrategy;

    /**
     * 是否启用版本控制（默认false）
     */
    private Boolean enableVersionControl = false;

    /**
     * 创建版本时的版本备注
     */
    private String versionRemark;

    /**
     * 是否跨租户去重
     */
    private Boolean crossTenantDeduplication = false;

}