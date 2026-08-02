package com.yaocode.sts.file.core.model;

import com.yaocode.sts.file.core.enums.DuplicateFileStrategyEnums;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * 文件上传上下文
 * 包含上传请求的所有参数
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class FileUploadContext {

    /**
     * 文件ID
     */
    private String fileId;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件大小
     */
    private Long fileSize;

    /**
     * 文件MD5
     */
    private String fileMd5;

    /**
     * 文件SHA-256
     */
    private String fileSha256;

    /**
     * 存储类型
     */
    private Integer storageType;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 是否启用去重
     */
    private Integer enableDeduplication;

    /**
     * 用户指定的策略
     */
    private DuplicateFileStrategyEnums specifiedStrategy;

    /**
     * 文件扩展名
     */
    private String fileExtension;

    /**
     * 版本备注
     */
    private String versionRemark;

    /**
     * 桶
     */
    private String bucket;

    /**
     * 扩展属性
     */
    private Map<String, Object> attributes;

}
