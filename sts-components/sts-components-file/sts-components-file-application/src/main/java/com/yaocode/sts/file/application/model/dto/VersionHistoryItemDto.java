package com.yaocode.sts.file.application.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List; /**
 * 版本历史项
 */
@Data
@Builder
public class VersionHistoryItemDto {
    private String versionId;
    private Integer versionNumber;
    private String versionTag;
    private String versionName;
    private String versionRemark;
    private String changeSummary;
    private Long fileSize;
    private String fileMd5;
    private String fileSha256;
    private String fileUrl;
    private String branchId;
    private String branchName;
    private Boolean isCurrent;
    private Boolean isLatest;
    private List<String> tags;
    private String graphInfo;
    private Integer depth;
    private List<String> parents;
    private String createdUserId;
    private String createdUserName;
    private LocalDateTime createdTime;
}
