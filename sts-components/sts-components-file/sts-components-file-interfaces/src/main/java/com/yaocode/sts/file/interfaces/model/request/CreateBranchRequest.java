package com.yaocode.sts.file.interfaces.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 创建分支请求
 */
@Data
public class CreateBranchRequest {

    @NotBlank(message = "文件ID不能为空")
    private String fileId;

    @NotBlank(message = "分支名称不能为空")
    private String branchName;

    /**
     * 分支类型: 1-主分支 2-开发分支 3-功能分支 4-修复分支 5-发布分支
     */
    private Integer branchType = 2;

    private String branchDescription;

    private String sourceBranchId;

    private String tenantId;
    private String userId;
    private String userName;
}
