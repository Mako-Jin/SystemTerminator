package com.yaocode.sts.file.interfaces.model.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 版本信息响应（批量查询用）
 */
@Data
@Builder
public class VersionInfoResponse {

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
    private Boolean isRollback;
    private Integer rollbackFromVersion;
    private String createUserId;
    private String createUserName;
    private LocalDateTime createTime;

}
