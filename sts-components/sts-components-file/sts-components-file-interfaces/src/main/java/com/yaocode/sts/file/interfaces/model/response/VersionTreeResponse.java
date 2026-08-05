package com.yaocode.sts.file.interfaces.model.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 版本树响应
 * <p>
 * 以树形结构展示所有版本的分支关系，类似 Git 的版本树
 * </p>
 */
@Data
@Builder
public class VersionTreeResponse {

    private String fileId;
    private String fileName;
    private List<BranchInfo> branches;
    private List<VersionTreeNode> nodes;
    private List<VersionTreeEdge> edges;

    /**
     * 版本树节点
     */
    @Data
    @Builder
    public static class VersionTreeNode {
        private String versionId;
        private String fileId;
        private Integer versionNumber;
        private String versionTag;
        private String versionName;
        private String versionRemark;
        private Long fileSize;
        private String fileMd5;
        private String branchId;
        private String branchName;
        private String parentVersionId;
        private List<String> childrenVersionIds;
        private Boolean isCurrent;
        private Boolean isLatest;
        private Boolean isMergeCommit;
        private Integer depth;
        private Integer position;
        private String createUserId;
        private String createUsername;
        private LocalDateTime createTime;
    }

    /**
     * 版本树边（连接关系）
     */
    @Data
    @Builder
    public static class VersionTreeEdge {
        private String fromVersionId;
        private String toVersionId;
        private String edgeType;  // PARENT, MERGE, BRANCH
        private String branchId;
        private String branchName;
    }

    @Data
    @Builder
    public static class BranchInfo {
        private String branchId;
        private String branchName;
        private Integer branchType;
        private String branchDescription;
        private String headVersionId;
        private Integer headVersionNumber;
        private Boolean isDefault;
        private Boolean isActive;
        private LocalDateTime createTime;
    }
}