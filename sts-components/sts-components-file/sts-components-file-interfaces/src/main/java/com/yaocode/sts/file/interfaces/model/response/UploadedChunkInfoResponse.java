package com.yaocode.sts.file.interfaces.model.response;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 已上传分片信息
 */
@Data
public class UploadedChunkInfoResponse {
    /**
     * 分片序号
     */
    private Integer chunkNumber;
    /**
     * 分片MD5值
     */
    private String chunkMd5;
    /**
     * 分片大小（字节）
     */
    private Long chunkSize;
    /**
     * 上传时间
     */
    private LocalDateTime uploadTime;
}