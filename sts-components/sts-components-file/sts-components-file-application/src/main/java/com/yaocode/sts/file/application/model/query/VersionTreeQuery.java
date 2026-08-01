package com.yaocode.sts.file.application.model.query;

import lombok.Builder;
import lombok.Data;

/**
 * 版本树查询
 */
@Data
@Builder
public class VersionTreeQuery {
    private String fileId;
    private Boolean simple;
    private Integer maxDepth;
}
