package com.yaocode.sts.file.application.model.dto;

import lombok.Builder;
import lombok.Data; /**
 * 版本树边
 */
@Data
@Builder
public class VersionTreeEdgeDto {
    private String fromVersionId;
    private String toVersionId;
    private String edgeType;
    private String branchId;
    private String branchName;
}
