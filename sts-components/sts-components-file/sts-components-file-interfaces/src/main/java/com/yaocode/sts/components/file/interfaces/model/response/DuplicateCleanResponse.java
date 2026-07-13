package com.yaocode.sts.components.file.interfaces.model.response;

import lombok.Builder;
import lombok.Data;

/**
 * 重复文件清理结果
 */
@Data
@Builder
public class DuplicateCleanResponse {

    /**
     * 重复组数
     */
    private Integer duplicateGroups;

    /**
     * 重复文件数
     */
    private Integer duplicateFiles;

    /**
     * 重复占用空间（字节）
     */
    private Long duplicateSpace;

    /**
     * 保留的文件数
     */
    private Integer keptFiles;

    /**
     * 删除的文件数
     */
    private Integer deletedFiles;

    /**
     * 执行时间（毫秒）
     */
    private Long executionTime;
}