package com.yaocode.sts.components.file.interfaces.model.response;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 批量删除结果
 */
@Data
public class BatchDeleteResponse {
    /**
     * 需要删除的文件数
     */
    private Integer total;
    /**
     * 成功删除的文件数
     */
    private Integer success;
    /**
     * 失败删除的文件数
     */
    private Integer failed;
    /**
     * 失败删除的文件ID列表
     */
    private List<String> failedIds;
    /**
     * 失败删除的文件ID到错误信息的映射
     */
    private Map<String, String> errors;
}
