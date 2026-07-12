package com.yaocode.sts.components.file.interfaces.model.response;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 批量下载任务
 */
@Data
public class BatchDownloadTaskResponse {

    /**
     * 任务ID
     */
    private String taskId;
    /**
     * 任务状态 PENDING, PROCESSING, COMPLETED, FAILED
     */
    private String status;
    /**
     * 总文件数
     */
    private Integer totalFiles;
    /**
     * 已处理文件数
     */
    private Integer processedFiles;
    /**
     * 处理进度（0-100）
     */
    private Integer progress;
    /**
     * 总大小（字节）
     */
    private Long totalSize;
    /**
     * 已处理大小（字节）
     */
    private Long processedSize;
    /**
     * 压缩包文件名
     */
    private String zipFileName;
    /**
     * 压缩包下载URL
     */
    private String zipFileUrl;
    /**
     * 错误信息
     */
    private String errorMessage;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 完成时间
     */
    private LocalDateTime completeTime;

}