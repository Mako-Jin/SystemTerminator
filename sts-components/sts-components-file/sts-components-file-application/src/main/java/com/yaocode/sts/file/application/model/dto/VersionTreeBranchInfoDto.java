package com.yaocode.sts.file.application.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime; /**
 * 版本树分支信息
 */
@Data
@Builder
public class VersionTreeBranchInfoDto {
    private String branchId;
    private String branchName;
    private Integer branchType;
    private String branchDescription;
    private String headVersionId;
    private Integer headVersionNumber;
    private Boolean isDefault;
    private Boolean isActive;
    private LocalDateTime createdTime;
}
