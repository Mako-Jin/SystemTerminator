package com.yaocode.sts.components.file.runtime.model.command;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 恢复上传命令
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class ResumeUploadCommand {
    /** 文件ID */
    private String fileId;

    /** 上传ID */
    private String uploadId;

    /** 文件MD5 */
    private String fileMd5;

    /** 文件大小（字节） */
    private Long fileSize;

    /** 缺失的分片序号列表 */
    private List<Integer> missingChunks;

    /** 租户ID */
    private String tenantId;

    /** 用户ID */
    private String userId;
}
