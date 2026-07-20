package com.yaocode.sts.file.interfaces.model.request;

import lombok.Data;

/**
 * 迁移选项
 */
@Data
public class MigrateOptionsRequest {
    /**
     * 是否异步执行
     */
    private Boolean async = true;
    /**
     * 是否保留源文件
     */
    private Boolean keepSource = false;
    /**
     * 超时时间（秒）
     */
    private Integer timeout;

}
