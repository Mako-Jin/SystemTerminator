package com.yaocode.sts.components.file.interfaces.model.request;

import lombok.Data;

import java.util.List;

/**
 * 批量归档请求
 */
@Data
public class BatchArchiveRequest {

    /**
     * 文件ID列表
     */
    private List<String> fileIds;

    /**
     * 归档类型 GLACIER-冰川归档, DEEP_ARCHIVE-深度归档
     */
    private String archiveType;

    /**
     * 是否立即执行
     */
    private Boolean immediate;

    /**
     * 归档原因
     */
    private String reason;

    /**
     * 归档后是否删除原文件
     */
    private Boolean deleteAfterArchive;
}