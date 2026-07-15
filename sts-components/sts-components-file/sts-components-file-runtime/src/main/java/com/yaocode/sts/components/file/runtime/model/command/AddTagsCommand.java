package com.yaocode.sts.components.file.runtime.model.command;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 添加标签命令
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class AddTagsCommand {
    /** 文件ID */
    private String fileId;

    /** 要添加的标签列表 */
    private List<String> tags;

    /** 租户ID */
    private String tenantId;

    /** 用户ID */
    private String userId;

    /** 用户名 */
    private String userName;
}
