package com.yaocode.sts.file.application.model.result;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 版本回滚结果
 */
@Data
@Builder
public class VersionRollbackResult {
    // 回滚后的版本
    private String newVersionId;
    private Integer newVersionNumber;
    private String newVersionTag;
    private String newVersionRemark;
    private Long fileSize;
    private String fileMd5;
    private String fileSha256;
    private String fileUrl;
    private String branchId;
    private String branchName;

    // 回滚前的版本
    private String oldVersionId;
    private Integer oldVersionNumber;
    private String oldVersionTag;
    private String oldVersionRemark;
    private Long oldFileSize;
    private String oldFileMd5;

    // 目标版本
    private String targetVersionId;
    private Integer targetVersionNumber;
    private String targetVersionTag;
    private String targetVersionRemark;

    // 统计信息
    private String rollbackReason;
    private String operatorUserName;
    private LocalDateTime rollbackTime;
    private Boolean success;
    private String message;
    private RollbackDiffStats diffStats;
    private List<AffectedFileInfo> affectedFiles;

    @Data
    @Builder
    public static class RollbackDiffStats {
        private String rollbackType;
        private Integer changedLines;
        private Double changePercentage;
        private Integer addedLines;
        private Integer deletedLines;
        private Integer modifiedLines;
    }

    @Data
    @Builder
    public static class AffectedFileInfo {
        private String fileId;
        private String fileName;
        private String oldVersionId;
        private String newVersionId;
        private Integer oldVersionNumber;
        private Integer newVersionNumber;
        private String status;
        private String message;
    }
}
