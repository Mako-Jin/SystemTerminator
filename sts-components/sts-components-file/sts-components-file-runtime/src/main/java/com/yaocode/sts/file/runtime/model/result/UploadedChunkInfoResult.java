package com.yaocode.sts.file.runtime.model.result;

import lombok.Builder;
import lombok.Data;

/**
 * 已上传分片信息
 */
@Data
@Builder
public class UploadedChunkInfoResult {
    /** 分片序号 */
    private Integer chunkNumber;

    /** 分片MD5 */
    private String chunkMd5;

    /** 分片大小（字节） */
    private Long chunkSize;

    /** 上传时间戳 */
    private Long uploadTime;
}
