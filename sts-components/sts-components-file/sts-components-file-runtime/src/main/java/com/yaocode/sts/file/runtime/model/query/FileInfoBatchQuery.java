package com.yaocode.sts.file.runtime.model.query;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 批量文件信息查询
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class FileInfoBatchQuery {
    /** 文件ID列表 */
    private List<String> fileIds;

    /** 租户ID */
    private String tenantId;

    /** 用户ID */
    private String userId;
}
