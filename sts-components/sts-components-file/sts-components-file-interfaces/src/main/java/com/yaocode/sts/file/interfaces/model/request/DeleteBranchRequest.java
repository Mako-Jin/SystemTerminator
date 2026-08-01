package com.yaocode.sts.file.interfaces.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 删除分支请求
 */
@Data
public class DeleteBranchRequest {

    @NotBlank(message = "分支ID不能为空")
    private String branchId;

    private String tenantId;
    private String userId;
}
