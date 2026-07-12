package com.yaocode.sts.components.file.interfaces.model.request;

import lombok.Data;

import java.util.List;

/**
 * 更新标签请求
 */
@Data
public class UpdateTagsRequest {

    /**
     * 标签列表
     */
    private List<String> tags;
    /**
     * 是否追加（false则覆盖）
     */
    private Boolean append = false;

}