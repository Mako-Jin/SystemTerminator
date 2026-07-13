package com.yaocode.sts.components.file.runtime.model.result;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 批量恢复结果
 */
@Data
@Builder
public class BatchRestoreResult {

    /**
     * 总文件数
     */
    private Integer total;

    /**
     * 成功恢复文件数
     */
    private Integer success;

    /**
     * 恢复失败文件数
     */
    private Integer failed;

    /**
     * 恢复失败文件ID列表
     */
    private List<String> failedIds;

    /**
     * 错误信息映射
     */
    private Map<String, String> errors;

    /**
     * 执行时间（毫秒）
     */
    private Long executionTime;
}