package com.yaocode.sts.file.interfaces.model.request;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 批量更新请求
 */
@Data
public class BatchUpdateRequest {

    /**
     * 文件ID列表
     */
    private List<String> fileIds;
    /**
     * 更新的字段和值
     */
    private Map<String, Object> updates;
    /**
     * 是否忽略不存在的文件
     */
    private Boolean ignoreNotFound = false;

}