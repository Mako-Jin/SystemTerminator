package com.yaocode.sts.file.application.model.dto;

import lombok.Builder;
import lombok.Data; /**
 * 分支信息
 */
@Data
@Builder
public class BranchInfoDto {
    private String branchId;
    private String branchName;
    private Integer branchType;
    private String headVersionId;
    private Integer headVersionNumber;
    private Boolean isDefault;
    private Boolean isActive;
}
