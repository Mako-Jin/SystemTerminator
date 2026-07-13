package com.yaocode.sts.components.file.runtime.model.command;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 批量归档命令
 */
@Data
@Builder
public class BatchArchiveCommand {

    /**
     * 文件ID列表
     */
    private List<String> fileIds;

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
     * 是否立即执行
     */
    private Boolean immediate;

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