package com.yaocode.sts.file.application.model.result;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 版本详情结果
 */
@Data
@Builder
public class VersionDetailResult {
    private String versionId;
    private String fileId;
    private Integer versionNumber;
    private Integer versionType;
    private String versionTag;
    private String versionName;
    private String versionRemark;
    private String changeSummary;
    private Long fileSize;
    private String fileMd5;
    private String fileSha256;
    private String fileUrl;
    private String filePath;
    private String branchId;
    private String branchName;
    private String parentVersionId;
    private Boolean isCurrent;
    private Boolean isLatest;
    private String createdUserId;
    private String createdUserName;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
