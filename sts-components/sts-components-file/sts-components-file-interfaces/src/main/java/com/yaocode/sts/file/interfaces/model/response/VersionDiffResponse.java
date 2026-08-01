package com.yaocode.sts.file.interfaces.model.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 版本差异响应
 */
@Data
@Builder
public class VersionDiffResponse {

    private Boolean isSameVersion;
    private String message;
    private VersionRef fromVersion;
    private VersionRef toVersion;
    private String diffType;
    private Double diffPercentage;
    private Integer addedLines;
    private Integer deletedLines;
    private Integer modifiedLines;
    private String diffContent;

    @Data
    @Builder
    public static class VersionRef {
        private String versionId;
        private Integer versionNumber;
        private String versionTag;
        private LocalDateTime createdTime;
    }
}
