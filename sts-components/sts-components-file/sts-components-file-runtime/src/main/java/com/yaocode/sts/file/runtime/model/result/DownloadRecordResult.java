package com.yaocode.sts.file.runtime.model.result;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 下载记录结果
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class DownloadRecordResult {
    /** 文件ID */
    private String fileId;

    /** 文件名 */
    private String fileName;

    /** 文件大小（字节） */
    private Long fileSize;

    /** 存储类型 */
    private String storageType;

    /** 下载类型（NORMAL/RANGE/PREVIEW/BATCH） */
    private String downloadType;

    /** IP地址 */
    private String ipAddress;

    /** 用户代理 */
    private String userAgent;

    /** 下载耗时（毫秒） */
    private Long downloadTime;

    /** 实际下载大小（字节） */
    private Long downloadSize;

    /** 是否成功 */
    private Boolean success;

    /** 创建时间 */
    private LocalDateTime createdTime;

    /** 租户ID */
    private String tenantId;

    /** 用户ID */
    private String userId;
}
