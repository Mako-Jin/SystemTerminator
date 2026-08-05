package com.yaocode.sts.file.interfaces.model.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 分支响应
 */
@Data
@Builder
public class BranchResponse {

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
    private String createUserId;
    private String createUsername;
    private LocalDateTime createTime;
}
