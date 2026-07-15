package com.yaocode.sts.components.file.runtime.model.result;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 批量更新结果
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class BatchUpdateResult {
    /** 总数 */
    private Integer total;

    /** 成功数 */
    private Integer success;

    /** 失败数 */
    private Integer failed;

    /** 失败的ID列表 */
    private List<String> failedIds;

    /** 错误信息 */
    private Map<String, String> errors;

    /** 更新时间戳 */
    private Long updateTime;
}
