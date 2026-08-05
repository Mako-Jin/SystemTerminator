package com.yaocode.sts.file.application.model.result;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 版本标签结果
 */
@Data
@Builder
public class VersionTagResult {
    private String tagId;
    private String tagName;
    private Integer tagType;
    private String tagDescription;
    private String versionId;
    private Integer versionNumber;
    private String versionTag;
    private String fileId;
    private String createUserId;
    private String createUsername;
    private LocalDateTime createTime;
}
