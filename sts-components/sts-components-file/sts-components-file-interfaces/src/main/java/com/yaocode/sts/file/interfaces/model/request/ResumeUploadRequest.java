package com.yaocode.sts.file.interfaces.model.request;

import com.yaocode.sts.file.core.constants.FileI18nKeyConstants;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

/**
 * 断点续传请求
 * <p>
 * 用于恢复中断的上传任务，支持指定需要重传的分片
 * </p>
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
public class ResumeUploadRequest {
    /**
     * 文件ID
     */
    @NotBlank(message = FileI18nKeyConstants.FILE_ID_EMPTY)
    private String fileId;

    /**
     * 上传ID
     */
    @NotBlank(message = FileI18nKeyConstants.UPLOAD_ID_EMPTY)
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