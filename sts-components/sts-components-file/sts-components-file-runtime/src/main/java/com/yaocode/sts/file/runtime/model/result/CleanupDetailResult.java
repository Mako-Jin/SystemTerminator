package com.yaocode.sts.file.runtime.model.result;

import lombok.Builder;
import lombok.Data;

/**
 * 清理详情
 */
@Data
@Builder
public class CleanupDetailResult {

    /**
     * 文件ID
     */
    private String fileId;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件大小（字节）
     */
    private Long fileSize;

    /**
     * 清理原因
     */
    private String reason;

    /**
     * 是否已删除
     */
    private Boolean deleted;
}