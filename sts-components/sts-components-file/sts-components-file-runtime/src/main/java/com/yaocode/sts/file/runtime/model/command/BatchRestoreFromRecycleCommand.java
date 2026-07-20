package com.yaocode.sts.file.runtime.model.command;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 批量从回收站恢复文件命令
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class BatchRestoreFromRecycleCommand {
    /** 文件ID列表 */
    private List<String> fileIds;

    /** 租户ID */
    private String tenantId;

    /** 用户ID */
    private String userId;

    /** 用户名 */
    private String userName;
}
