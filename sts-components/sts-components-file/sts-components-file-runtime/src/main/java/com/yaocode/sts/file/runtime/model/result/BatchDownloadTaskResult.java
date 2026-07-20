package com.yaocode.sts.file.runtime.model.result;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 批量下载任务结果
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class BatchDownloadTaskResult {
    /** 任务ID */
    private String taskId;

    /** 任务状态（PENDING/PROCESSING/COMPLETED/FAILED） */
    private String status;

    /** 总文件数 */
    private Integer totalFiles;

    /** 已处理文件数 */
    private Integer processedFiles;

    /** 进度百分比 */
    private Integer progress;

    /** 总大小（字节） */
    private Long totalSize;

    /** 已处理大小（字节） */
    private Long processedSize;

    /** ZIP文件名 */
    private String zipFileName;

    /** ZIP文件URL */
    private String zipFileUrl;

    /** 错误信息 */
    private String errorMessage;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 完成时间 */
    private LocalDateTime completeTime;

    /** 租户ID */
    private String tenantId;

    /** 用户ID */
    private String userId;
}
