package com.yaocode.sts.file.application.model.result;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 切换分支结果
 */
@Data
@Builder
public class SwitchBranchResult {
    private String branchId;
    private String branchName;
    private String versionId;
    private Integer versionNumber;
    private String versionTag;
    private String fileUrl;
    private String fileMd5;
    private String fileSha256;
    private Long fileSize;
    private String message;
    private LocalDateTime switchTime;
}
