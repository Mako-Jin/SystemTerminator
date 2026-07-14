package com.yaocode.sts.components.file.runtime.model.result;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 分片会话结果
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class MultipartSessionResult {
    /** 上传ID */
    private String uploadId;

    /** 文件ID */
    private String fileId;

    /** 文件名 */
    private String fileName;

    /** 文件大小（字节） */
    private Long fileSize;

    /** 分片大小（字节） */
    private Long chunkSize;

    /** 总分片数 */
    private Integer totalChunks;

    /** 已上传分片数 */
    private Integer uploadedChunks;

    /** 进度百分比 */
    private Integer progress;

    /** 状态 */
    private String status;

    /** 过期时间戳 */
    private Long expireTime;

    /** 最后活跃时间戳 */
    private Long lastActiveTime;

    /** 创建时间 */
    private LocalDateTime createdTime;
}