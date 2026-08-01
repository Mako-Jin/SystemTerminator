package com.yaocode.sts.file.interfaces.model.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 合并分支响应
 */
@Data
@Builder
public class MergeResponse {

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
