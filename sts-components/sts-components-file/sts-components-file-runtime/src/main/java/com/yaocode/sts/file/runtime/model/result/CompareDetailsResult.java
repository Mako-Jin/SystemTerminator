package com.yaocode.sts.file.runtime.model.result;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 对比详情
 */
@Data
@Builder
public class CompareDetailsResult {

    /** 文件名是否相同 */
    private Boolean nameSame;

    /** 文件大小是否相同 */
    private Boolean sizeSame;

    /** MD5是否相同 */
    private Boolean md5Same;

    /** 文件类型是否相同 */
    private Boolean typeSame;

    /** 差异列表 */
    private List<String> differences;

    /** 相似度（百分比） */
    private Double similarity;

}
