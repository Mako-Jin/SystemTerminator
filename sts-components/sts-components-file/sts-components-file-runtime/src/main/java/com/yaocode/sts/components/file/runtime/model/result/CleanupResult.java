package com.yaocode.sts.components.file.runtime.model.result;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 清理结果
 */
@Data
@Builder
public class CleanupResult {

    /**
     * 扫描的文件总数
     */
    private Integer totalScanned;

    /**
     * 删除的文件总数
     */
    private Integer totalDeleted;

    /**
     * 释放的空间大小（字节）
     */
    private Long totalFreedSpace;

    /**
     * 执行时间（毫秒）
     */
    private Long executionTime;

    /**
     * 结果消息
     */
    private String message;

    /**
     * 清理详情列表
     */
    private List<CleanupDetailResult> details;
}