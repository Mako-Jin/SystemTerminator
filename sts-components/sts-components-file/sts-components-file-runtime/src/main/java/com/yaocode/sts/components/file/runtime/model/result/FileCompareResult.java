package com.yaocode.sts.components.file.runtime.model.result;

import lombok.Builder;
import lombok.Data;

/**
 * 文件对比结果
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class FileCompareResult {
    /** 是否相同 */
    private Boolean isSame;

    /** 文件1信息 */
    private FileInfoResult file1;

    /** 文件2信息 */
    private FileInfoResult file2;

    /** 对比详情 */
    private CompareDetailsResult details;
}
