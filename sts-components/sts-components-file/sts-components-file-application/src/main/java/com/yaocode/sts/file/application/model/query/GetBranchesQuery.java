package com.yaocode.sts.file.application.model.query;

import lombok.Builder;
import lombok.Data;

/**
 * 获取分支列表查询
 */
@Data
@Builder
public class GetBranchesQuery {
    private String fileId;
    private Boolean includeInactive;
}
