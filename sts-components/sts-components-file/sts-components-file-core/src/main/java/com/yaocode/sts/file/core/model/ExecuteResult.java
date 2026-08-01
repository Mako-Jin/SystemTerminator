package com.yaocode.sts.file.core.model;

import lombok.Builder;
import lombok.Data;

/**
 * 策略执行结果
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class ExecuteResult {

    /** 文件ID */
    private String fileId;

    /** 文件名 */
    private String fileName;

    /** 文件大小 */
    private Long fileSize;

    /** 文件MD5 */
    private String fileMd5;

    /** 文件SHA-256 */
    private String fileSha256;

    /** 文件URL */
    private String fileUrl;

    /** 存储类型 */
    private Integer storageType;

    /** 租户ID */
    private String tenantId;

    /** 上传状态（0-上传中, 1-已完成, 2-失败） */
    private Integer uploadStatus;

    /** 是否为重复文件 */
    private Boolean isDuplicate;

    /** 源文件ID（如果是重复文件） */
    private String sourceFileId;

    /** 版本号 */
    private Integer versionNumber;

    /** 消息 */
    private String message;

}
