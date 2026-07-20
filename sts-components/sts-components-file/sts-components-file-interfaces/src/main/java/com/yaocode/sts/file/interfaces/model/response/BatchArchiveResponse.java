package com.yaocode.sts.file.interfaces.model.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 批量归档结果
 */
@Data
@Builder
public class BatchArchiveResponse {

    /**
     * 总文件数
     */
    private Integer total;

    /**
     * 成功归档文件数
     */
    private Integer success;

    /**
     * 归档失败文件数
     */
    private Integer failed;

    /**
     * 归档失败文件ID列表
     */
    private List<String> failedIds;

    /**
     * 归档任务ID
     */
    private String archiveTaskId;

    /**
     * 错误信息映射，key为文件ID，value为错误描述
     */
    private Map<String, String> errors;

    /**
     * 执行时间（毫秒）
     */
    private Long executionTime;

}