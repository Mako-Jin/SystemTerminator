package com.yaocode.sts.file.runtime.model.command;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * 更新元数据命令
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class UpdateMetadataCommand {
    /** 文件ID */
    private String fileId;

    /** 元数据键值对 */
    private Map<String, String> metadata;

    /** 是否合并（true: 合并，false: 覆盖） */
    @Builder.Default
    private Boolean merge = true;

    /** 租户ID */
    private String tenantId;

    /** 用户ID */
    private String userId;

    /** 用户名 */
    private String userName;
}
