package com.yaocode.sts.components.file.runtime.model.result;

import lombok.Builder;
import lombok.Data;

/**
 * 分片上传结果
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class UploadPartResult {
    /** 上传ID */
    private String uploadId;

    /** 文件ID */
    private String fileId;

    /** 分片序号 */
    private Integer chunkNumber;

    /** 总分片数 */
    private Integer totalChunks;

    /** 是否成功 */
    private Boolean success;

    /** 已上传分片数 */
    private Integer uploadedChunks;

    /** 进度百分比 */
    private Integer progress;
}
