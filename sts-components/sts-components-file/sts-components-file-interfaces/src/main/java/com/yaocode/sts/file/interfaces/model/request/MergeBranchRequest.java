package com.yaocode.sts.file.interfaces.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 合并分支请求
 */
@Data
public class MergeBranchRequest {

    @NotBlank(message = "文件ID不能为空")
    private String fileId;

    @NotBlank(message = "源分支ID不能为空")
    private String fromBranchId;

    @NotBlank(message = "目标分支ID不能为空")
    private String toBranchId;

    private String mergeMessage;

    private Boolean autoResolve = false;

    private String tenantId;
    private String userId;
    private String userName;
}
