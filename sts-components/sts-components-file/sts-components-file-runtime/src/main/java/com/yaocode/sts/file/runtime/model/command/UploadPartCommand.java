package com.yaocode.sts.file.runtime.model.command;

import com.yaocode.sts.file.runtime.model.dto.FileObjectDto;
import lombok.Builder;
import lombok.Data;

/**
 * 上传分片命令
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class UploadPartCommand {
    /** 上传ID */
    private String uploadId;

    /** 文件ID */
    private String fileId;

    /** 分片序号（从1开始） */
    private Integer chunkNumber;

    /** 总分片数 */
    private Integer totalChunks;

    /** 分片文件 */
    private FileObjectDto file;

    /** 分片MD5 */
    private String chunkMd5;

    /** 租户ID */
    private String tenantId;

    /** 用户ID */
    private String userId;
}
