package com.yaocode.sts.file.runtime.model.result;

import lombok.Builder;
import lombok.Data;

/**
 * 分片初始化结果
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class MultipartInitResult {
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

    /** 过期时间戳 */
    private Long expireTime;

    /** 存储类型 */
    private String storageType;
}