package com.yaocode.sts.file.application.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime; /**
 * 版本引用
 */
@Data
@Builder
public class VersionRefDto {
    private String versionId;
    private Integer versionNumber;
    private String versionTag;
    private LocalDateTime createdTime;
}
