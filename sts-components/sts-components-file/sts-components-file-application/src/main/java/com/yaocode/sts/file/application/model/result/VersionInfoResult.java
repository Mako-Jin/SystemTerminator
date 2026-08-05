package com.yaocode.sts.file.application.model.result;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 版本信息结果
 */
@Data
@Builder
public class VersionInfoResult {
    private String versionId;
    private String fileId;
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
    private String createUserId;
    private String createUserName;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
