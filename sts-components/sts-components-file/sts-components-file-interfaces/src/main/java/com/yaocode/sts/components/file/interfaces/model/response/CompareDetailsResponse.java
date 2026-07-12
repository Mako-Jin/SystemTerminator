package com.yaocode.sts.components.file.interfaces.model.response;

import lombok.Data;

import java.util.List;

/**
 * 对比详情
 */
@Data
public class CompareDetailsResponse {

    /**
     * 文件名是否相同
     */
    private Boolean nameSame;
    /**
     * 文件大小是否相同
     */
    private Boolean sizeSame;
    /**
     * MD5值是否相同
     */
    private Boolean md5Same;
    /**
     * 文件类型是否相同
     */
    private Boolean typeSame;
    /**
     * 差异列表
     */
    private List<String> differences;
    /**
     * 相似度（百分比）
     */
    private Double similarity;

}