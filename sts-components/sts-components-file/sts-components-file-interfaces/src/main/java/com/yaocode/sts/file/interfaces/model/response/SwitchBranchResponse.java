package com.yaocode.sts.file.interfaces.model.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 切换分支响应
 */
@Data
@Builder
public class SwitchBranchResponse {

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
