package com.yaocode.sts.file.application.model.query;

import lombok.Builder;
import lombok.Data;

/**
 * 版本历史查询
 */
@Data
@Builder
public class VersionHistoryQuery {
    private String fileId;
    private Integer limit;
    private Integer offset;
    private String branchId;
    private Integer versionType;
    private String tenantId;
}
