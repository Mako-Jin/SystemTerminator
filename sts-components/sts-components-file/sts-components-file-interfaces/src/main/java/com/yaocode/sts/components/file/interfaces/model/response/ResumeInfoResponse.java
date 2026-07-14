package com.yaocode.sts.components.file.interfaces.model.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 断点续传信息响应
 */
@Data
@Builder
public class ResumeInfoResponse {
    /**
     * 文件ID
     */
    private String fileId;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件大小（字节）
     */
    private Long fileSize;
    /**
     * 上传ID
     */
    private String uploadId;
    /**
     * 总分片数
     */
    private Integer totalChunks;
    /**
     * 已上传分片数
     */
    private Integer uploadedChunks;
    /**
     * 进度（0-100）
     */
    private Integer progress;
    /**
     * 已上传分片信息列表
     */
    private List<UploadedChunkInfoResponse> uploadedChunkInfos;
    /**
     * 缺失的分片序号列表
     */
    private List<Integer> missingChunks;
    /**
     * 是否可以继续上传
     */
    private Boolean canResume;
    /**
     * 状态
     */
    private String status;
}