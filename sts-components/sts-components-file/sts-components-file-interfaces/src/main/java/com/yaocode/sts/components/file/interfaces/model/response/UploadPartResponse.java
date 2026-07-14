package com.yaocode.sts.components.file.interfaces.model.response;

import lombok.Builder;
import lombok.Data;

/**
 * 分片上传响应
 */
@Data
@Builder
public class UploadPartResponse {
    /**
     * 上传ID
     */
    private String uploadId;
    /**
     * 文件ID
     */
    private String fileId;
    /**
     * 当前分片序号
     */
    private Integer chunkNumber;
    /**
     * 总分片数
     */
    private Integer totalChunks;
    /**
     * 是否成功
     */
    private Boolean success;
    /**
     * 已上传分片数
     */
    private Integer uploadedChunks;
    /**
     * 进度（0-100）
     */
    private Integer progress;
}