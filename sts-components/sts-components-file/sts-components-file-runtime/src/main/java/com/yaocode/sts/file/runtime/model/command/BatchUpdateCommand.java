package com.yaocode.sts.file.runtime.model.command;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 批量更新命令
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class BatchUpdateCommand {
    /** 文件ID列表 */
    private List<String> fileIds;

    /** 更新字段和值 */
    private Map<String, Object> updates;

    /** 是否忽略不存在的文件 */
    @Builder.Default
    private Boolean ignoreNotFound = false;

    /** 租户ID */
    private String tenantId;

    /** 用户ID */
    private String userId;

    /** 用户名 */
    private String userName;
}
