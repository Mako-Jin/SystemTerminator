package com.yaocode.sts.file.web.converter;

import com.yaocode.sts.common.domain.context.RequestContextHolder;
import com.yaocode.sts.file.application.model.command.CreateBranchCommand;
import com.yaocode.sts.file.application.model.command.CreateVersionCommand;
import com.yaocode.sts.file.application.model.command.CreateVersionTagCommand;
import com.yaocode.sts.file.application.model.command.DeleteBranchCommand;
import com.yaocode.sts.file.application.model.command.DeleteVersionTagCommand;
import com.yaocode.sts.file.application.model.command.MergeBranchCommand;
import com.yaocode.sts.file.application.model.command.RollbackVersionCommand;
import com.yaocode.sts.file.application.model.command.SwitchBranchCommand;
import com.yaocode.sts.file.application.model.command.VersionCompareCommand;
import com.yaocode.sts.file.application.model.dto.BranchInfoDto;
import com.yaocode.sts.file.application.model.dto.VersionHistoryItemDto;
import com.yaocode.sts.file.application.model.dto.VersionRefDto;
import com.yaocode.sts.file.application.model.dto.VersionTreeBranchInfoDto;
import com.yaocode.sts.file.application.model.dto.VersionTreeEdgeDto;
import com.yaocode.sts.file.application.model.dto.VersionTreeNodeDto;
import com.yaocode.sts.file.application.model.query.VersionsBatchQuery;
import com.yaocode.sts.file.application.model.result.BranchResult;
import com.yaocode.sts.file.application.model.result.MergeResult;
import com.yaocode.sts.file.application.model.result.SwitchBranchResult;
import com.yaocode.sts.file.application.model.result.VersionDetailResult;
import com.yaocode.sts.file.application.model.result.VersionDiffResult;
import com.yaocode.sts.file.application.model.result.VersionHistoryResult;
import com.yaocode.sts.file.application.model.result.VersionInfoResult;
import com.yaocode.sts.file.application.model.result.VersionRollbackResult;
import com.yaocode.sts.file.application.model.result.VersionTagResult;
import com.yaocode.sts.file.application.model.result.VersionTreeResult;
import com.yaocode.sts.file.interfaces.model.request.CreateBranchRequest;
import com.yaocode.sts.file.interfaces.model.request.CreateVersionRequest;
import com.yaocode.sts.file.interfaces.model.request.CreateVersionTagRequest;
import com.yaocode.sts.file.interfaces.model.request.DeleteBranchRequest;
import com.yaocode.sts.file.interfaces.model.request.DeleteVersionTagRequest;
import com.yaocode.sts.file.interfaces.model.request.MergeBranchRequest;
import com.yaocode.sts.file.interfaces.model.request.RollbackVersionRequest;
import com.yaocode.sts.file.interfaces.model.request.SwitchBranchRequest;
import com.yaocode.sts.file.interfaces.model.request.VersionCompareRequest;
import com.yaocode.sts.file.interfaces.model.request.VersionsBatchRequest;
import com.yaocode.sts.file.interfaces.model.response.BranchResponse;
import com.yaocode.sts.file.interfaces.model.response.MergeResponse;
import com.yaocode.sts.file.interfaces.model.response.SwitchBranchResponse;
import com.yaocode.sts.file.interfaces.model.response.VersionDetailResponse;
import com.yaocode.sts.file.interfaces.model.response.VersionDiffResponse;
import com.yaocode.sts.file.interfaces.model.response.VersionHistoryResponse;
import com.yaocode.sts.file.interfaces.model.response.VersionInfoResponse;
import com.yaocode.sts.file.interfaces.model.response.VersionRollbackResponse;
import com.yaocode.sts.file.interfaces.model.response.VersionTagResponse;
import com.yaocode.sts.file.interfaces.model.response.VersionTreeResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 文件版本转换器
 * <p>
 * 负责 Request/Response 与 Command/Query/Result 之间的转换
 * </p>
 *
 * @author yaocode
 * @since 1.0.0
 */
@Component
public class FileVersionConverter {

    // ==================== 上下文信息获取 ====================

    private String getCurrentTenantId() {
        var tenantId = RequestContextHolder.getTenantId();
        return tenantId != null ? tenantId.getValue() : null;
    }

    private String getCurrentUserId() {
        var userId = RequestContextHolder.getUserId();
        return userId != null ? userId.getValue() : null;
    }

    private String getCurrentUserName() {
        var username = RequestContextHolder.getUsername();
        return username != null ? username.getValue() : null;
    }

    // ==================== Request -> Command 转换 ====================

    /**
     * 转换创建版本请求
     */
    public CreateVersionCommand toCreateVersionCommand(CreateVersionRequest request) {
        if (request == null) {
            return null;
        }
        return CreateVersionCommand.builder()
                .fileId(request.getFileId())
                .fileName(request.getFileName())
                .fileSize(request.getFileSize())
                .fileMd5(request.getFileMd5())
                .fileSha256(request.getFileSha256())
                .versionType(request.getVersionType())
                .versionName(request.getVersionName())
                .versionRemark(request.getVersionRemark())
                .changeSummary(request.getChangeSummary())
                .branchId(request.getBranchId())
                .setAsCurrent(request.getSetAsCurrent())
                .tenantId(request.getTenantId() != null ? request.getTenantId() : getCurrentTenantId())
                .userId(request.getUserId() != null ? request.getUserId() : getCurrentUserId())
                .userName(request.getUserName() != null ? request.getUserName() : getCurrentUserName())
                .build();
    }

    /**
     * 转换版本对比请求
     */
    public VersionCompareCommand toVersionCompareCommand(VersionCompareRequest request) {
        if (request == null) {
            return null;
        }
        return VersionCompareCommand.builder()
                .versionId1(request.getVersionId1())
                .versionId2(request.getVersionId2())
                .build();
    }

    /**
     * 转换回滚版本请求
     */
    public RollbackVersionCommand toRollbackVersionCommand(RollbackVersionRequest request) {
        if (request == null) {
            return null;
        }
        return RollbackVersionCommand.builder()
                .fileId(request.getFileId())
                .targetVersionId(request.getTargetVersionId())
                .rollbackReason(request.getRollbackReason())
                .tenantId(request.getTenantId() != null ? request.getTenantId() : getCurrentTenantId())
                .userId(request.getUserId() != null ? request.getUserId() : getCurrentUserId())
                .userName(request.getUserName() != null ? request.getUserName() : getCurrentUserName())
                .build();
    }

    /**
     * 转换批量查询请求
     */
    public VersionsBatchQuery toVersionsBatchQuery(VersionsBatchRequest request) {
        if (request == null) {
            return null;
        }
        return VersionsBatchQuery.builder()
                .versionIds(request.getVersionIds())
                .build();
    }

    /**
     * 转换创建分支请求
     */
    public CreateBranchCommand toCreateBranchCommand(CreateBranchRequest request) {
        if (request == null) {
            return null;
        }
        return CreateBranchCommand.builder()
                .fileId(request.getFileId())
                .branchName(request.getBranchName())
                .branchType(request.getBranchType())
                .branchDescription(request.getBranchDescription())
                .sourceBranchId(request.getSourceBranchId())
                .tenantId(request.getTenantId() != null ? request.getTenantId() : getCurrentTenantId())
                .userId(request.getUserId() != null ? request.getUserId() : getCurrentUserId())
                .userName(request.getUserName() != null ? request.getUserName() : getCurrentUserName())
                .build();
    }

    /**
     * 转换合并分支请求
     */
    public MergeBranchCommand toMergeBranchCommand(MergeBranchRequest request) {
        if (request == null) {
            return null;
        }
        return MergeBranchCommand.builder()
                .fileId(request.getFileId())
                .fromBranchId(request.getFromBranchId())
                .toBranchId(request.getToBranchId())
                .mergeMessage(request.getMergeMessage())
                .autoResolve(request.getAutoResolve())
                .tenantId(request.getTenantId() != null ? request.getTenantId() : getCurrentTenantId())
                .userId(request.getUserId() != null ? request.getUserId() : getCurrentUserId())
                .userName(request.getUserName() != null ? request.getUserName() : getCurrentUserName())
                .build();
    }

    /**
     * 转换切换分支请求
     */
    public SwitchBranchCommand toSwitchBranchCommand(SwitchBranchRequest request) {
        if (request == null) {
            return null;
        }
        return SwitchBranchCommand.builder()
                .fileId(request.getFileId())
                .targetBranchId(request.getTargetBranchId())
                .tenantId(request.getTenantId() != null ? request.getTenantId() : getCurrentTenantId())
                .userId(request.getUserId() != null ? request.getUserId() : getCurrentUserId())
                .build();
    }

    /**
     * 转换删除分支请求
     */
    public DeleteBranchCommand toDeleteBranchCommand(DeleteBranchRequest request) {
        if (request == null) {
            return null;
        }
        return DeleteBranchCommand.builder()
                .branchId(request.getBranchId())
                .tenantId(request.getTenantId() != null ? request.getTenantId() : getCurrentTenantId())
                .userId(request.getUserId() != null ? request.getUserId() : getCurrentUserId())
                .build();
    }

    /**
     * 转换创建标签请求
     */
    public CreateVersionTagCommand toCreateVersionTagCommand(CreateVersionTagRequest request) {
        if (request == null) {
            return null;
        }
        return CreateVersionTagCommand.builder()
                .fileId(request.getFileId())
                .versionId(request.getVersionId())
                .tagName(request.getTagName())
                .tagType(request.getTagType())
                .tagDescription(request.getTagDescription())
                .tenantId(request.getTenantId() != null ? request.getTenantId() : getCurrentTenantId())
                .userId(request.getUserId() != null ? request.getUserId() : getCurrentUserId())
                .userName(request.getUserName() != null ? request.getUserName() : getCurrentUserName())
                .build();
    }

    /**
     * 转换删除标签请求
     */
    public DeleteVersionTagCommand toDeleteVersionTagCommand(DeleteVersionTagRequest request) {
        if (request == null) {
            return null;
        }
        return DeleteVersionTagCommand.builder()
                .tagId(request.getTagId())
                .tenantId(request.getTenantId() != null ? request.getTenantId() : getCurrentTenantId())
                .userId(request.getUserId() != null ? request.getUserId() : getCurrentUserId())
                .build();
    }

    // ==================== Result -> Response 转换 ====================

    /**
     * 转换版本信息结果
     */
    public VersionInfoResponse toVersionInfoResponse(VersionInfoResult result) {
        if (result == null) {
            return null;
        }
        return VersionInfoResponse.builder()
                .versionId(result.getVersionId())
                .fileId(result.getFileId())
                .versionNumber(result.getVersionNumber())
                .versionTag(result.getVersionTag())
                .versionRemark(result.getVersionRemark())
                .fileSize(result.getFileSize())
                .fileMd5(result.getFileMd5())
                .fileSha256(result.getFileSha256())
                .fileUrl(result.getFileUrl())
                .branchId(result.getBranchId())
                .branchName(result.getBranchName())
                .isCurrent(result.getIsCurrent())
                .isLatest(result.getIsLatest())
                .createUserId(result.getCreateUserId())
                .createUserName(result.getCreateUserName())
                .createTime(result.getCreateTime())
                .build();
    }

    /**
     * 转换版本信息结果列表
     */
    public List<VersionInfoResponse> toVersionInfoResponseList(List<VersionInfoResult> results) {
        if (results == null) {
            return null;
        }
        return results.stream()
                .map(this::toVersionInfoResponse)
                .collect(Collectors.toList());
    }

    /**
     * 转换版本历史结果
     */
    public VersionHistoryResponse toVersionHistoryResponse(VersionHistoryResult result) {
        if (result == null) {
            return null;
        }
        return VersionHistoryResponse.builder()
                .fileId(result.getFileId())
                .fileName(result.getFileName())
                .total(result.getTotal())
                .items(toVersionHistoryItems(result.getItems()))
                .branches(toBranchInfos(result.getBranches()))
                .build();
    }

    /**
     * 转换版本历史项列表
     */
    private List<VersionHistoryResponse.VersionHistoryItem> toVersionHistoryItems(
            List<VersionHistoryItemDto> items
    ) {
        if (items == null) {
            return null;
        }
        return items.stream()
                .map(item -> VersionHistoryResponse.VersionHistoryItem.builder()
                        .versionId(item.getVersionId())
                        .versionNumber(item.getVersionNumber())
                        .versionTag(item.getVersionTag())
                        .versionName(item.getVersionName())
                        .versionRemark(item.getVersionRemark())
                        .changeSummary(item.getChangeSummary())
                        .fileSize(item.getFileSize())
                        .fileMd5(item.getFileMd5())
                        .fileSha256(item.getFileSha256())
                        .fileUrl(item.getFileUrl())
                        .branchId(item.getBranchId())
                        .branchName(item.getBranchName())
                        .isCurrent(item.getIsCurrent())
                        .isLatest(item.getIsLatest())
                        .tags(item.getTags())
                        .graphInfo(item.getGraphInfo())
                        .depth(item.getDepth())
                        .parents(item.getParents())
                        .createUserId(item.getCreateUserId())
                        .createUsername(item.getCreateUsername())
                        .createTime(item.getCreateTime())
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * 转换分支信息列表
     */
    private List<VersionHistoryResponse.BranchInfo> toBranchInfos(
            List<BranchInfoDto> branches
    ) {
        if (branches == null) {
            return null;
        }
        return branches.stream()
                .map(branch -> VersionHistoryResponse.BranchInfo.builder()
                        .branchId(branch.getBranchId())
                        .branchName(branch.getBranchName())
                        .branchType(branch.getBranchType())
                        .headVersionId(branch.getHeadVersionId())
                        .headVersionNumber(branch.getHeadVersionNumber())
                        .isDefault(branch.getIsDefault())
                        .isActive(branch.getIsActive())
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * 转换版本差异结果
     */
    public VersionDiffResponse toVersionDiffResponse(VersionDiffResult result) {
        if (result == null) {
            return null;
        }
        return VersionDiffResponse.builder()
                .isSameVersion(result.getIsSameVersion())
                .message(result.getMessage())
                .fromVersion(toVersionRef(result.getFromVersion()))
                .toVersion(toVersionRef(result.getToVersion()))
                .diffType(result.getDiffType())
                .diffPercentage(result.getDiffPercentage())
                .addedLines(result.getAddedLines())
                .deletedLines(result.getDeletedLines())
                .modifiedLines(result.getModifiedLines())
                .diffContent(result.getDiffContent())
                .build();
    }

    /**
     * 转换版本引用
     */
    private VersionDiffResponse.VersionRef toVersionRef(VersionRefDto versionRef) {
        if (versionRef == null) {
            return null;
        }
        return VersionDiffResponse.VersionRef.builder()
                .versionId(versionRef.getVersionId())
                .versionNumber(versionRef.getVersionNumber())
                .versionTag(versionRef.getVersionTag())
                .createTime(versionRef.getCreateTime())
                .build();
    }

    /**
     * 转换版本回滚结果
     */
    public VersionRollbackResponse toVersionRollbackResponse(VersionRollbackResult result) {
        if (result == null) {
            return null;
        }
        VersionRollbackResponse.VersionRollbackResponseBuilder builder =
                VersionRollbackResponse.builder()
                        // 回滚后的版本
                        .newVersionId(result.getNewVersionId())
                        .newVersionNumber(result.getNewVersionNumber())
                        .newVersionTag(result.getNewVersionTag())
                        .newVersionRemark(result.getNewVersionRemark())
                        .fileSize(result.getFileSize())
                        .fileMd5(result.getFileMd5())
                        .fileSha256(result.getFileSha256())
                        .fileUrl(result.getFileUrl())
                        .branchId(result.getBranchId())
                        .branchName(result.getBranchName())
                        // 回滚前的版本
                        .oldVersionId(result.getOldVersionId())
                        .oldVersionNumber(result.getOldVersionNumber())
                        .oldVersionTag(result.getOldVersionTag())
                        .oldVersionRemark(result.getOldVersionRemark())
                        .oldFileSize(result.getOldFileSize())
                        .oldFileMd5(result.getOldFileMd5())
                        // 目标版本
                        .targetVersionId(result.getTargetVersionId())
                        .targetVersionNumber(result.getTargetVersionNumber())
                        .targetVersionTag(result.getTargetVersionTag())
                        .targetVersionRemark(result.getTargetVersionRemark())
                        // 统计信息
                        .rollbackReason(result.getRollbackReason())
                        .operatorUserName(result.getOperatorUserName())
                        .rollbackTime(result.getRollbackTime())
                        .success(result.getSuccess())
                        .message(result.getMessage());

        if (result.getDiffStats() != null) {
            builder.diffStats(VersionRollbackResponse.RollbackDiffStats.builder()
                    .rollbackType(result.getDiffStats().getRollbackType())
                    .changedLines(result.getDiffStats().getChangedLines())
                    .changePercentage(result.getDiffStats().getChangePercentage())
                    .addedLines(result.getDiffStats().getAddedLines())
                    .deletedLines(result.getDiffStats().getDeletedLines())
                    .modifiedLines(result.getDiffStats().getModifiedLines())
                    .build());
        }

        return builder.build();
    }

    /**
     * 转换版本详情结果
     */
    public VersionDetailResponse toVersionDetailResponse(VersionDetailResult result) {
        if (result == null) {
            return null;
        }
        return VersionDetailResponse.builder()
                .versionId(result.getVersionId())
                .fileId(result.getFileId())
                .versionNumber(result.getVersionNumber())
                .versionType(result.getVersionType())
                .versionTag(result.getVersionTag())
                .versionName(result.getVersionName())
                .versionRemark(result.getVersionRemark())
                .changeSummary(result.getChangeSummary())
                .fileSize(result.getFileSize())
                .fileMd5(result.getFileMd5())
                .fileSha256(result.getFileSha256())
                .fileUrl(result.getFileUrl())
                .filePath(result.getFilePath())
                .branchId(result.getBranchId())
                .branchName(result.getBranchName())
                .parentVersionId(result.getParentVersionId())
                .isCurrent(result.getIsCurrent())
                .isLatest(result.getIsLatest())
                .createUserId(result.getCreateUserId())
                .createUsername(result.getCreateUsername())
                .createTime(result.getCreateTime())
                .updateTime(result.getUpdateTime())
                .build();
    }

    /**
     * 转换版本树结果
     */
    public VersionTreeResponse toVersionTreeResponse(VersionTreeResult result) {
        if (result == null) {
            return null;
        }
        return VersionTreeResponse.builder()
                .fileId(result.getFileId())
                .fileName(result.getFileName())
                .nodes(toVersionTreeNodes(result.getNodes()))
                .edges(toVersionTreeEdges(result.getEdges()))
                .branches(toVersionTreeBranchInfos(result.getBranches()))
                .build();
    }

    /**
     * 转换版本树节点列表
     */
    private List<VersionTreeResponse.VersionTreeNode> toVersionTreeNodes(
            List<VersionTreeNodeDto> nodes) {
        if (nodes == null) {
            return null;
        }
        return nodes.stream()
                .map(node -> VersionTreeResponse.VersionTreeNode.builder()
                        .versionId(node.getVersionId())
                        .fileId(node.getFileId())
                        .versionNumber(node.getVersionNumber())
                        .versionTag(node.getVersionTag())
                        .versionName(node.getVersionName())
                        .versionRemark(node.getVersionRemark())
                        .fileSize(node.getFileSize())
                        .fileMd5(node.getFileMd5())
                        .branchId(node.getBranchId())
                        .branchName(node.getBranchName())
                        .parentVersionId(node.getParentVersionId())
                        .childrenVersionIds(node.getChildrenVersionIds())
                        .isCurrent(node.getIsCurrent())
                        .isLatest(node.getIsLatest())
                        .isMergeCommit(node.getIsMergeCommit())
                        .depth(node.getDepth())
                        .position(node.getPosition())
                        .createUserId(node.getCreateUserId())
                        .createUsername(node.getCreateUsername())
                        .createTime(node.getCreateTime())
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * 转换版本树边列表
     */
    private List<VersionTreeResponse.VersionTreeEdge> toVersionTreeEdges(
            List<VersionTreeEdgeDto> edges) {
        if (edges == null) {
            return null;
        }
        return edges.stream()
                .map(edge -> VersionTreeResponse.VersionTreeEdge.builder()
                        .fromVersionId(edge.getFromVersionId())
                        .toVersionId(edge.getToVersionId())
                        .edgeType(edge.getEdgeType())
                        .branchId(edge.getBranchId())
                        .branchName(edge.getBranchName())
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * 转换版本树分支信息列表
     */
    private List<VersionTreeResponse.BranchInfo> toVersionTreeBranchInfos(
            List<VersionTreeBranchInfoDto> branches) {
        if (branches == null) {
            return null;
        }
        return branches.stream()
                .map(branch -> VersionTreeResponse.BranchInfo.builder()
                        .branchId(branch.getBranchId())
                        .branchName(branch.getBranchName())
                        .branchType(branch.getBranchType())
                        .branchDescription(branch.getBranchDescription())
                        .headVersionId(branch.getHeadVersionId())
                        .headVersionNumber(branch.getHeadVersionNumber())
                        .isDefault(branch.getIsDefault())
                        .isActive(branch.getIsActive())
                        .createTime(branch.getCreateTime())
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * 转换分支结果
     */
    public BranchResponse toBranchResponse(BranchResult result) {
        if (result == null) {
            return null;
        }
        return BranchResponse.builder()
                .branchId(result.getBranchId())
                .branchName(result.getBranchName())
                .branchType(result.getBranchType())
                .branchDescription(result.getBranchDescription())
                .headVersionId(result.getHeadVersionId())
                .headVersionNumber(result.getHeadVersionNumber())
                .headVersionTag(result.getHeadVersionTag())
                .sourceBranchId(result.getSourceBranchId())
                .sourceBranchName(result.getSourceBranchName())
                .isDefault(result.getIsDefault())
                .isActive(result.getIsActive())
                .createUserId(result.getCreateUserId())
                .createUsername(result.getCreateUsername())
                .createTime(result.getCreateTime())
                .build();
    }

    /**
     * 转换分支结果列表
     */
    public List<BranchResponse> toBranchResponseList(List<BranchResult> results) {
        if (results == null) {
            return null;
        }
        return results.stream()
                .map(this::toBranchResponse)
                .collect(Collectors.toList());
    }

    /**
     * 转换合并结果
     */
    public MergeResponse toMergeResponse(MergeResult result) {
        if (result == null) {
            return null;
        }
        MergeResponse.MergeResponseBuilder builder = MergeResponse.builder()
                .mergeType(result.getMergeType())
                .toBranchId(result.getToBranchId())
                .toBranchName(result.getToBranchName())
                .headVersionId(result.getHeadVersionId())
                .headVersionNumber(result.getHeadVersionNumber())
                .headVersionTag(result.getHeadVersionTag())
                .hasConflict(result.getHasConflict())
                .message(result.getMessage())
                .mergeTime(result.getMergeTime());

        if (result.getConflicts() != null) {
            builder.conflicts(result.getConflicts().stream()
                    .map(conflict -> MergeResponse.ConflictInfo.builder()
                            .versionId(conflict.getVersionId())
                            .versionNumber(conflict.getVersionNumber())
                            .branchId(conflict.getBranchId())
                            .branchName(conflict.getBranchName())
                            .conflictType(conflict.getConflictType())
                            .description(conflict.getDescription())
                            .build())
                    .collect(Collectors.toList()));
        }

        return builder.build();
    }

    /**
     * 转换切换分支结果
     */
    public SwitchBranchResponse toSwitchBranchResponse(SwitchBranchResult result) {
        if (result == null) {
            return null;
        }
        return SwitchBranchResponse.builder()
                .branchId(result.getBranchId())
                .branchName(result.getBranchName())
                .versionId(result.getVersionId())
                .versionNumber(result.getVersionNumber())
                .versionTag(result.getVersionTag())
                .fileUrl(result.getFileUrl())
                .fileMd5(result.getFileMd5())
                .fileSha256(result.getFileSha256())
                .fileSize(result.getFileSize())
                .message(result.getMessage())
                .switchTime(result.getSwitchTime())
                .build();
    }

    /**
     * 转换版本标签结果
     */
    public VersionTagResponse toVersionTagResponse(VersionTagResult result) {
        if (result == null) {
            return null;
        }
        return VersionTagResponse.builder()
                .tagId(result.getTagId())
                .tagName(result.getTagName())
                .tagType(result.getTagType())
                .tagDescription(result.getTagDescription())
                .versionId(result.getVersionId())
                .versionNumber(result.getVersionNumber())
                .versionTag(result.getVersionTag())
                .fileId(result.getFileId())
                .createUserId(result.getCreateUserId())
                .createUsername(result.getCreateUsername())
                .createTime(result.getCreateTime())
                .build();
    }

    /**
     * 转换版本标签结果列表
     */
    public List<VersionTagResponse> toVersionTagResponseList(List<VersionTagResult> results) {
        if (results == null) {
            return null;
        }
        return results.stream()
                .map(this::toVersionTagResponse)
                .collect(Collectors.toList());
    }
}