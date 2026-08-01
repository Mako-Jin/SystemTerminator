package com.yaocode.sts.file.application.model.query;

import lombok.Builder;
import lombok.Data;

/**
 * 版本详情查询
 */
@Data
@Builder
public class VersionDetailQuery {
    private String versionId;
}