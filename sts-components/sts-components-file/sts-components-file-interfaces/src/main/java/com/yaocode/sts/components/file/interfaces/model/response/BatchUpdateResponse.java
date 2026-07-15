package com.yaocode.sts.components.file.interfaces.model.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 批量更新结果
 */
@Data
@Builder
public class BatchUpdateResponse {

    /**
     * 总文件数
     */
    private Integer total;
    /**
     * 成功更新数
     */
    private Integer success;
    /**
     * 失败数
     */
    private Integer failed;
    /**
     * 失败文件ID列表
     */
    private List<String> failedIds;
    /**
     * 错误信息映射
     */
    private Map<String, String> errors;
    /**
     * 更新耗时（毫秒）
     */
    private Long updateTime;

}