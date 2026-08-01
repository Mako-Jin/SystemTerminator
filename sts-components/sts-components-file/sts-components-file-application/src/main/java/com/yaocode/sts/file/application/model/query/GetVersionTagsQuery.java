package com.yaocode.sts.file.application.model.query;

import lombok.Builder;
import lombok.Data;

/**
 * 获取版本标签列表查询
 */
@Data
@Builder
public class GetVersionTagsQuery {
    private String fileId;
    private Integer tagType;
}
