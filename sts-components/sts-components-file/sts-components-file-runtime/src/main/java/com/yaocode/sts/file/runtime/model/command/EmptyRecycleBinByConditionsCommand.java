package com.yaocode.sts.file.runtime.model.command;

import lombok.Builder;
import lombok.Data;

/**
 * 按条件清空回收站命令
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class EmptyRecycleBinByConditionsCommand {
    /** 保留天数（超过此天数的文件将被清理） */
    private Integer days;

    /** 存储类型 */
    private String storageType;

    /** 是否二次确认 */
    @Builder.Default
    private Boolean confirm = true;

    /** 租户ID */
    private String tenantId;

    /** 用户ID */
    private String userId;

    /** 用户名 */
    private String userName;
}
