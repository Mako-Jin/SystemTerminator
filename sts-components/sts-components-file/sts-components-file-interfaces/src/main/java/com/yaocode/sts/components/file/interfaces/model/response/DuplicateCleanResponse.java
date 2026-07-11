package com.yaocode.sts.components.file.interfaces.model.response;

import lombok.Data;

/**
 * 重复文件清理结果
 */
@Data
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
     * 重复占用空间
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
}