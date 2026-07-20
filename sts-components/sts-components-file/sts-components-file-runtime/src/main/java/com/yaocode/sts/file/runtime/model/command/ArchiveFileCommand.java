package com.yaocode.sts.file.runtime.model.command;

import lombok.Builder;
import lombok.Data;

/**
 * 归档文件命令
 */
@Data
@Builder
public class ArchiveFileCommand {

    /**
     * 文件ID
     */
    private String fileId;

    /**
     * 归档类型
     */
    private String archiveType;

    /**
     * 归档原因
     */
    private String reason;

    /**
     * 归档后是否删除原文件
     */
    private Boolean deleteAfterArchive;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户名称
     */
    private String userName;
}