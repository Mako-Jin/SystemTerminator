package com.yaocode.sts.components.file.interfaces.model.response;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 批量恢复结果
 */
@Data
public class BatchRestoreResponse {
    /**
     * 需要恢复的文件数
     */
    private Integer total;
    /**
     * 成功恢复的文件数
     */
    private Integer success;
    /**
     * 失败恢复的文件数
     */
    private Integer failed;
    /**
     * 恢复失败的文件ID列表
     */
    private List<String> failedIds;
    /**
     * 恢复失败的文件ID到错误信息的映射
     */
    private Map<String, String> errors;
}
