package com.yaocode.sts.file.application.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List; /**
 * 版本树节点
 */
@Data
@Builder
public class VersionTreeNodeDto {
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
    private String createdUserId;
    private String createdUserName;
    private LocalDateTime createdTime;
}
