package com.yaocode.sts.file.application.model.result;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 分支结果
 */
@Data
@Builder
public class BranchResult {
    private String branchId;
    private String branchName;
    private Integer branchType;
    private String branchDescription;
    private String headVersionId;
    private Integer headVersionNumber;
    private String headVersionTag;
    private String sourceBranchId;
    private String sourceBranchName;
    private Boolean isDefault;
    private Boolean isActive;
    private String createdUserId;
    private String createdUserName;
    private LocalDateTime createdTime;
}
