package com.yaocode.sts.file.runtime.model.result;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 重复文件组
 */
@Data
@Builder
public class DuplicateGroupResult {

    /**
     * 文件MD5值
     */
    private String fileMd5;

    /**
     * 文件大小（字节）
     */
    private Long fileSize;

    /**
     * 文件数量
     */
    private Integer fileCount;

    /**
     * 文件列表
     */
    private List<FileInfoResult> files;

    /**
     * 保留的文件ID
     */
    private String keptFileId;
}