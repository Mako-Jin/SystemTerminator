package com.yaocode.sts.file.application.model.query;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 批量获取版本信息查询
 */
@Data
@Builder
public class VersionsBatchQuery {
    private List<String> versionIds;
}
