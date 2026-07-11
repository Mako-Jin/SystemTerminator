package com.yaocode.sts.components.file.interfaces.model.response;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 批量归档结果
 */
@Data
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
     * 失败归档文件数
     */
    private Integer failed;
    /**
     * 失败归档文件ID列表
     */
    private List<String> failedIds;
    /**
     * 归档任务ID
     */
    private String archiveTaskId;
    /**
     * 失败归档文件ID到错误信息的映射
     */
    private Map<String, String> errors;

}
