package com.yaocode.sts.components.file.interfaces.model.response;

import lombok.Builder;
import lombok.Data;

/**
 * 文件对比VO
 */
@Data
@Builder
public class FileCompareResponse {

    /**
     * 是否相同
     */
    private Boolean isSame;
    /**
     * 文件1信息
     */
    private FileInfoResponse file1;
    /**
     * 文件2信息
     */
    private FileInfoResponse file2;
    /**
     * 对比详情
     */
    private CompareDetailsResponse details;

}