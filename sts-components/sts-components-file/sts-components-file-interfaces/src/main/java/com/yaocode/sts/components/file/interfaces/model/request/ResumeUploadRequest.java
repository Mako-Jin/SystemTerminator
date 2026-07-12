package com.yaocode.sts.components.file.interfaces.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

/**
 * 恢复上传请求
 */
@Data
public class ResumeUploadRequest {
    /**
     * 文件ID
     */
    @NotBlank(message = "文件ID不能为空")
    private String fileId;

    /**
     * 上传ID
     */
    @NotBlank(message = "上传ID不能为空")
    private String uploadId;

    /**
     * 文件MD5值
     */
    private String fileMd5;
    /**
     * 文件大小（字节）
     */
    private Long fileSize;
    /**
     * 需要重传的分片序号列表
     */
    private List<Integer> missingChunks;
}