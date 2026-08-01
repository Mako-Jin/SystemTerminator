package com.yaocode.sts.file.application.model.result;

import com.yaocode.sts.file.application.model.dto.VersionRefDto;
import lombok.Builder;
import lombok.Data;

/**
 * 版本差异结果
 */
@Data
@Builder
public class VersionDiffResult {
    private Boolean isSameVersion;
    private String message;
    private VersionRefDto fromVersion;
    private VersionRefDto toVersion;
    private String diffType;
    private Double diffPercentage;
    private Integer addedLines;
    private Integer deletedLines;
    private Integer modifiedLines;
    private String diffContent;
}

