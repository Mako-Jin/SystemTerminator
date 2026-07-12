package com.yaocode.sts.components.file.interfaces.model.request;

import lombok.Data;

/**
 * 更新描述请求
 */
@Data
public class UpdateDescriptionRequest {

    /**
     * 文件描述
     */
    private String description;

}