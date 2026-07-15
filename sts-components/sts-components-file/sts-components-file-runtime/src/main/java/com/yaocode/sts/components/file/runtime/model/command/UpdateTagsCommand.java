package com.yaocode.sts.components.file.runtime.model.command;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 更新标签命令
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class UpdateTagsCommand {
    /** 文件ID */
    private String fileId;

    /** 标签列表 */
    private List<String> tags;

    /** 是否追加（false则覆盖） */
    @Builder.Default
    private Boolean append = false;

    /** 租户ID */
    private String tenantId;

    /** 用户ID */
    private String userId;

    /** 用户名 */
    private String userName;
}
