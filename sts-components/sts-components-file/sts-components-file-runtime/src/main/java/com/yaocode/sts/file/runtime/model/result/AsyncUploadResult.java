package com.yaocode.sts.file.runtime.model.result;

import lombok.Builder;
import lombok.Data;

/**
 * 异步上传结果
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class AsyncUploadResult {
    /** 任务ID */
    private String taskId;

    /** 文件ID（如果秒传成功） */
    private String fileId;

    /** 文件名 */
    private String fileName;

    /** 文件大小（字节） */
    private Long fileSize;

    /** 状态 */
    private String status;

    /** 进度百分比 */
    private Integer progress;

    /** 消息 */
    private String message;

    /** 预计完成时间（秒） */
    private Long estimatedTime;

    /** 状态查询URL */
    private String statusUrl;
}
