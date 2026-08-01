package com.yaocode.sts.file.interfaces.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 回滚版本请求
 */
@Data
public class RollbackVersionRequest {

    @NotBlank(message = "文件ID不能为空")
    private String fileId;

    @NotBlank(message = "目标版本ID不能为空")
    private String targetVersionId;

    private String rollbackReason;

    private String tenantId;
    private String userId;
    private String userName;
}
