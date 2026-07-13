package com.yaocode.sts.components.file.runtime.model.result;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 重复文件清理结果
 */
@Data
@Builder
public class DuplicateCleanResult {

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
     * 保留文件数
     */
    private Integer keptFiles;

    /**
     * 删除文件数
     */
    private Integer deletedFiles;

    /**
     * 重复文件组列表
     */
    private List<DuplicateGroup> groups;

    /**
     * 执行时间（毫秒）
     */
    private Long executionTime;
}