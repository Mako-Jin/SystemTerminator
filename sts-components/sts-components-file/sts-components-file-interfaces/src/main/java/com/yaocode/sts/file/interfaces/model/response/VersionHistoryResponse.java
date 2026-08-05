package com.yaocode.sts.file.interfaces.model.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 版本历史响应
 * <p>
 * 以线性列表形式展示版本历史，按时间倒序排列
 * </p>
 */
@Data
@Builder
public class VersionHistoryResponse {

    private String fileId;
    private String fileName;
    private Long total;
    private Integer currentPage;
    private Integer pageSize;
    private List<VersionHistoryItem> items;
    private List<BranchInfo> branches;

    @Data
    @Builder
    public static class VersionHistoryItem {
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
        private String createUserId;
        private String createUsername;
        private LocalDateTime createTime;

        /**
         * 版本图显示信息（用于git log --graph风格）
         */
        private String graphInfo;
        private Integer depth;
        private List<String> parents;
    }

    @Data
    @Builder
    public static class BranchInfo {
        private String branchId;
        private String branchName;
        private Integer branchType;
        private String headVersionId;
        private Integer headVersionNumber;
        private Boolean isDefault;
        private Boolean isActive;
    }
}
