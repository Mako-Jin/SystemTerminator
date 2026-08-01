package com.yaocode.sts.file.interfaces.model.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 版本标签响应
 */
@Data
@Builder
public class VersionTagResponse {

    private String tagId;
    private String tagName;
    private Integer tagType;
    private String tagDescription;
    private String versionId;
    private Integer versionNumber;
    private String versionTag;
    private String fileId;
    private String createdUserId;
    private String createdUserName;
    private LocalDateTime createdTime;
}
