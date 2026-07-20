package com.yaocode.sts.file.runtime.model.result;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 异步任务状态结果
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class AsyncTaskResult {
    /** 任务ID */
    private String taskId;

    /** 文件ID */
    private String fileId;

    /** 文件名 */
    private String fileName;

    /** 文件大小（字节） */
    private Long fileSize;

    /** 文件MD5 */
    private String fileMd5;

    /** 任务状态 */
    private String status;

    /** 任务状态描述 */
    private String statusDesc;

    /** 进度百分比 */
    private Integer progress;

    /** 错误信息 */
    private String errorMessage;

    /** 结果数据 */
    private String resultData;

    /** 重试次数 */
    private Integer retryCount;

    /** 开始时间戳 */
    private Long startTime;

    /** 结束时间戳 */
    private Long endTime;

    /** 耗时（毫秒） */
    private Long elapsedTime;

    /** 回调URL */
    private String callbackUrl;

    /** 回调是否成功 */
    private Boolean callbackSuccess;

    /** 创建时间 */
    private LocalDateTime createdTime;
}
