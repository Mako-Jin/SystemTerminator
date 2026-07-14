package com.yaocode.sts.components.file.runtime.model.command;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * 初始化分片上传命令
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class InitMultipartCommand {
    /** 文件名 */
    private String fileName;

    /** 文件大小（字节） */
    private Long fileSize;

    /** 分片大小（字节），默认10MB */
    private Long chunkSize;

    /** 文件MD5 */
    private String fileMd5;

    /** 文件类型 */
    private String fileType;

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
