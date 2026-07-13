package com.yaocode.sts.components.file.interfaces.model.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 批量删除结果
 */
@Data
@Builder
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
     * 删除失败的文件数
     */
    private Integer failed;

    /**
     * 删除失败的文件ID列表
     */
    private List<String> failedIds;

    /**
     * 错误信息映射，key为文件ID，value为错误描述
     */
    private Map<String, String> errors;

    /**
     * 执行时间（毫秒）
     */
    private Long executionTime;
}