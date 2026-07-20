package com.yaocode.sts.file.interfaces.model.response;

import lombok.Builder;
import lombok.Data;

/**
 * 清理结果
 */
@Data
@Builder
public class CleanupResponse {

    /**
     * 扫描总数
     */
    private Integer totalScanned;
    /**
     * 删除总数
     */
    private Integer totalDeleted;
    /**
     * 释放空间（字节）
     */
    private Long totalFreedSpace;
    /**
     * 执行时间（毫秒）
     */
    private Long executionTime;
    /**
     * 操作结果消息
     */
    private String message;

}
