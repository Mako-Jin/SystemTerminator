package com.yaocode.sts.file.interfaces.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 切换分支请求
 */
@Data
public class SwitchBranchRequest {

    @NotBlank(message = "文件ID不能为空")
    private String fileId;

    @NotBlank(message = "目标分支ID不能为空")
    private String targetBranchId;

    private String tenantId;
    private String userId;
}
