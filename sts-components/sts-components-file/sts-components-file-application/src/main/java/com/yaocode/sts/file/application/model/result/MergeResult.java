package com.yaocode.sts.file.application.model.result;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 合并结果
 */
@Data
@Builder
public class MergeResult {
    private String mergeType;
    private String toBranchId;
    private String toBranchName;
    private String headVersionId;
    private Integer headVersionNumber;
    private String headVersionTag;
    private Boolean hasConflict;
    private List<ConflictInfo> conflicts;
    private String message;
    private LocalDateTime mergeTime;

    @Data
    @Builder
    public static class ConflictInfo {
        private String versionId;
        private Integer versionNumber;
        private String branchId;
        private String branchName;
        private String conflictType;
        private String description;
    }
}
