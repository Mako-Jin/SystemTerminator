package com.yaocode.sts.components.file.interfaces.model.request;

import lombok.Data;

/**
 * 更新文件名请求
 */
@Data
public class UpdateFileNameRequest {

    /**
     * 新文件名
     */
    private String newFileName;
    /**
     * 修改原因（可选）
     */
    private String reason;

}