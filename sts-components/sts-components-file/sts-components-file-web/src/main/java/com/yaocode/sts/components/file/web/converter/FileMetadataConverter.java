package com.yaocode.sts.components.file.web.converter;

import com.yaocode.sts.components.file.interfaces.model.request.BatchUpdateRequest;
import com.yaocode.sts.components.file.interfaces.model.request.FileSearchRequest;
import com.yaocode.sts.components.file.interfaces.model.request.UpdateDescriptionRequest;
import com.yaocode.sts.components.file.interfaces.model.request.UpdateFileNameRequest;
import com.yaocode.sts.components.file.interfaces.model.request.UpdatePermissionRequest;
import com.yaocode.sts.components.file.interfaces.model.request.UpdateTagsRequest;
import com.yaocode.sts.components.file.interfaces.model.response.AccessInfoResponse;
import com.yaocode.sts.components.file.interfaces.model.response.AccessRecordResponse;
import com.yaocode.sts.components.file.interfaces.model.response.BatchUpdateResponse;
import com.yaocode.sts.components.file.interfaces.model.response.CompareDetailsResponse;
import com.yaocode.sts.components.file.interfaces.model.response.FileAuditLogResponse;
import com.yaocode.sts.components.file.interfaces.model.response.FileCompareResponse;
import com.yaocode.sts.components.file.interfaces.model.response.FileDetailInfoResponse;
import com.yaocode.sts.components.file.interfaces.model.response.FileExistenceResponse;
import com.yaocode.sts.components.file.interfaces.model.response.FileInfoResponse;
import com.yaocode.sts.components.file.interfaces.model.response.FileTypeResponse;
import com.yaocode.sts.components.file.interfaces.model.response.FileTypeStatisticsResponse;
import com.yaocode.sts.components.file.interfaces.model.response.FileVersionInfoResponse;
import com.yaocode.sts.components.file.interfaces.model.response.IntegrityCheckResponse;
import com.yaocode.sts.components.file.interfaces.model.response.PermissionInfoResponse;
import com.yaocode.sts.components.file.interfaces.model.response.PermissionItemResponse;
import com.yaocode.sts.components.file.interfaces.model.response.SizeDistributionResponse;
import com.yaocode.sts.components.file.interfaces.model.response.SizeRangeResponse;
import com.yaocode.sts.components.file.interfaces.model.response.StorageInfoResponse;
import com.yaocode.sts.components.file.interfaces.model.response.StorageStatsResponse;
import com.yaocode.sts.components.file.interfaces.model.response.StorageTypeStatsResponse;
import com.yaocode.sts.components.file.interfaces.model.response.TrendDataResponse;
import com.yaocode.sts.components.file.runtime.model.command.AddTagsCommand;
import com.yaocode.sts.components.file.runtime.model.command.BatchUpdateCommand;
import com.yaocode.sts.components.file.runtime.model.command.RemoveTagCommand;
import com.yaocode.sts.components.file.runtime.model.command.SetFilePublicCommand;
import com.yaocode.sts.components.file.runtime.model.command.UpdateDescriptionCommand;
import com.yaocode.sts.components.file.runtime.model.command.UpdateFileNameCommand;
import com.yaocode.sts.components.file.runtime.model.command.UpdateMetadataCommand;
import com.yaocode.sts.components.file.runtime.model.command.UpdatePermissionCommand;
import com.yaocode.sts.components.file.runtime.model.command.UpdateTagsCommand;
import com.yaocode.sts.components.file.runtime.model.dto.PermissionItemDto;
import com.yaocode.sts.components.file.runtime.model.query.AccessInfoQuery;
import com.yaocode.sts.components.file.runtime.model.query.FileCompareQuery;
import com.yaocode.sts.components.file.runtime.model.query.FileDetailQuery;
import com.yaocode.sts.components.file.runtime.model.query.FileExistenceQuery;
import com.yaocode.sts.components.file.runtime.model.query.FileInfoBatchQuery;
import com.yaocode.sts.components.file.runtime.model.query.FileInfoQuery;
import com.yaocode.sts.components.file.runtime.model.query.FileListQuery;
import com.yaocode.sts.components.file.runtime.model.query.FileMetadataQuery;
import com.yaocode.sts.components.file.runtime.model.query.FileSearchQuery;
import com.yaocode.sts.components.file.runtime.model.query.FileTypeStatisticsQuery;
import com.yaocode.sts.components.file.runtime.model.query.IntegrityCheckQuery;
import com.yaocode.sts.components.file.runtime.model.query.MyFileListQuery;
import com.yaocode.sts.components.file.runtime.model.query.PermissionInfoQuery;
import com.yaocode.sts.components.file.runtime.model.query.RecentFileQuery;
import com.yaocode.sts.components.file.runtime.model.query.SimpleSearchQuery;
import com.yaocode.sts.components.file.runtime.model.query.StorageInfoQuery;
import com.yaocode.sts.components.file.runtime.model.query.TrendDataQuery;
import com.yaocode.sts.components.file.runtime.model.result.AccessInfoResult;
import com.yaocode.sts.components.file.runtime.model.result.AccessRecordResult;
import com.yaocode.sts.components.file.runtime.model.result.BatchUpdateResult;
import com.yaocode.sts.components.file.runtime.model.result.CompareDetailsResult;
import com.yaocode.sts.components.file.runtime.model.result.FileAuditLogResult;
import com.yaocode.sts.components.file.runtime.model.result.FileCompareResult;
import com.yaocode.sts.components.file.runtime.model.result.FileDetailInfoResult;
import com.yaocode.sts.components.file.runtime.model.result.FileExistenceResult;
import com.yaocode.sts.components.file.runtime.model.result.FileInfoResult;
import com.yaocode.sts.components.file.runtime.model.result.FileTypeResult;
import com.yaocode.sts.components.file.runtime.model.result.FileTypeStatisticsResult;
import com.yaocode.sts.components.file.runtime.model.result.FileVersionInfoResult;
import com.yaocode.sts.components.file.runtime.model.result.IntegrityCheckResult;
import com.yaocode.sts.components.file.runtime.model.result.PermissionInfoResult;
import com.yaocode.sts.components.file.runtime.model.result.SizeDistributionResult;
import com.yaocode.sts.components.file.runtime.model.result.SizeRangeResult;
import com.yaocode.sts.components.file.runtime.model.result.StorageInfoResult;
import com.yaocode.sts.components.file.runtime.model.result.StorageStatsResult;
import com.yaocode.sts.components.file.runtime.model.result.StorageTypeStatsResult;
import com.yaocode.sts.components.file.runtime.model.result.TrendDataResult;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 文件元数据转换器
 *
 * @author yaocode
 * @since 1.0.0
 */
@Component
public class FileMetadataConverter {

    // ==================== 上下文信息获取 ====================

    private String getCurrentTenantId() {
        // TODO: 从 SecurityContext 获取
        return "default";
    }

    private String getCurrentUserId() {
        // TODO: 从 SecurityContext 获取
        return "system";
    }

    private String getCurrentUserName() {
        // TODO: 从 SecurityContext 获取
        return "system";
    }

    // ==================== Query 转换 ====================

    /**
     * 文件信息查询
     */
    public FileInfoQuery toFileInfoQuery(String fileId) {
        return FileInfoQuery.builder()
                .fileId(fileId)
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .build();
    }

    /**
     * 文件详情查询
     */
    public FileDetailQuery toFileDetailQuery(String fileId) {
        return FileDetailQuery.builder()
                .fileId(fileId)
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .build();
    }

    /**
     * 批量文件信息查询
     */
    public FileInfoBatchQuery toFileInfoBatchQuery(List<String> fileIds) {
        return FileInfoBatchQuery.builder()
                .fileIds(fileIds)
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .build();
    }

    /**
     * 文件元数据查询
     */
    public FileMetadataQuery toFileMetadataQuery(String fileId) {
        return FileMetadataQuery.builder()
                .fileId(fileId)
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .build();
    }

    /**
     * 存储信息查询
     */
    public StorageInfoQuery toStorageInfoQuery(String fileId) {
        return StorageInfoQuery.builder()
                .fileId(fileId)
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .build();
    }

    /**
     * 访问信息查询
     */
    public AccessInfoQuery toAccessInfoQuery(String fileId) {
        return AccessInfoQuery.builder()
                .fileId(fileId)
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .build();
    }

    /**
     * 文件搜索查询
     */
    public FileSearchQuery toFileSearchQuery(FileSearchRequest request) {
        return FileSearchQuery.builder()
                .keyword(request.getKeyword())
                .fileName(request.getFileName())
                .fileNameLike(request.getFileNameLike())
                .fileTypes(request.getFileTypes())
                .storageTypes(request.getStorageTypes())
                .statuses(request.getStatuses())
                .tags(request.getTags())
                .uploadTimeStart(request.getUploadTimeStart())
                .uploadTimeEnd(request.getUploadTimeEnd())
                .lastAccessStart(request.getLastAccessStart())
                .lastAccessEnd(request.getLastAccessEnd())
                .createdTimeStart(request.getCreatedTimeStart())
                .createdTimeEnd(request.getCreatedTimeEnd())
                .minSize(request.getMinSize())
                .maxSize(request.getMaxSize())
                .uploadUserId(request.getUploadUserId())
                .uploadUserName(request.getUploadUserName())
                .businessId(request.getBusinessId())
                .businessType(request.getBusinessType())
                .orderBy(request.getOrderBy())
                .orderDirection(request.getOrderDirection())
                .page(request.getPage() != null ? request.getPage() : 1)
                .size(request.getSize() != null ? request.getSize() : 20)
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .build();
    }

    /**
     * 简单搜索查询
     */
    public SimpleSearchQuery toSimpleSearchQuery(String keyword, Integer page, Integer size) {
        return SimpleSearchQuery.builder()
                .keyword(keyword)
                .page(page != null ? page : 1)
                .size(size != null ? size : 20)
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .build();
    }

    /**
     * 文件列表查询
     */
    public FileListQuery toFileListQuery(String fileType, String storageType,
                                         String uploadTimeStart, String uploadTimeEnd,
                                         Long minSize, Long maxSize, Integer status,
                                         Integer page, Integer size) {
        return FileListQuery.builder()
                .fileType(fileType)
                .storageType(storageType)
                .uploadTimeStart(uploadTimeStart)
                .uploadTimeEnd(uploadTimeEnd)
                .minSize(minSize)
                .maxSize(maxSize)
                .status(status)
                .page(page != null ? page : 1)
                .size(size != null ? size : 20)
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .build();
    }

    /**
     * 我的文件列表查询
     */
    public MyFileListQuery toMyFileListQuery(Integer status, Integer page, Integer size) {
        return MyFileListQuery.builder()
                .status(status)
                .page(page != null ? page : 1)
                .size(size != null ? size : 20)
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .build();
    }

    /**
     * 最近文件查询
     */
    public RecentFileQuery toRecentFileQuery(Integer limit) {
        return RecentFileQuery.builder()
                .limit(limit != null ? limit : 10)
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .build();
    }

    /**
     * 文件类型统计查询
     */
    public FileTypeStatisticsQuery toFileTypeStatisticsQuery(String storageType) {
        return FileTypeStatisticsQuery.builder()
                .storageType(storageType)
                .tenantId(getCurrentTenantId())
                .build();
    }

    /**
     * 趋势数据查询
     */
    public TrendDataQuery toTrendDataQuery(String period, String startDate, String endDate) {
        return TrendDataQuery.builder()
                .period(period != null ? period : "day")
                .startDate(startDate)
                .endDate(endDate)
                .tenantId(getCurrentTenantId())
                .build();
    }

    /**
     * 文件存在性查询
     */
    public FileExistenceQuery toFileExistenceQuery(String fileMd5, Long fileSize, String storageType) {
        return FileExistenceQuery.builder()
                .fileMd5(fileMd5)
                .fileSize(fileSize)
                .storageType(storageType)
                .tenantId(getCurrentTenantId())
                .build();
    }

    /**
     * 完整性检查查询
     */
    public IntegrityCheckQuery toIntegrityCheckQuery(String fileId) {
        return IntegrityCheckQuery.builder()
                .fileId(fileId)
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .build();
    }

    /**
     * 文件对比查询
     */
    public FileCompareQuery toFileCompareQuery(String fileId1, String fileId2) {
        return FileCompareQuery.builder()
                .fileId1(fileId1)
                .fileId2(fileId2)
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .build();
    }

    /**
     * 权限信息查询
     */
    public PermissionInfoQuery toPermissionInfoQuery(String fileId) {
        return PermissionInfoQuery.builder()
                .fileId(fileId)
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .build();
    }

    // ==================== Command 转换 ====================

    /**
     * 更新文件名命令
     */
    public UpdateFileNameCommand toUpdateFileNameCommand(String fileId, UpdateFileNameRequest request) {
        return UpdateFileNameCommand.builder()
                .fileId(fileId)
                .newFileName(request.getNewFileName())
                .reason(request.getReason())
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .userName(getCurrentUserName())
                .build();
    }

    /**
     * 更新标签命令
     */
    public UpdateTagsCommand toUpdateTagsCommand(String fileId, UpdateTagsRequest request) {
        return UpdateTagsCommand.builder()
                .fileId(fileId)
                .tags(request.getTags())
                .append(request.getAppend() != null ? request.getAppend() : false)
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .userName(getCurrentUserName())
                .build();
    }

    /**
     * 更新描述命令
     */
    public UpdateDescriptionCommand toUpdateDescriptionCommand(String fileId, UpdateDescriptionRequest request) {
        return UpdateDescriptionCommand.builder()
                .fileId(fileId)
                .description(request.getDescription())
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .userName(getCurrentUserName())
                .build();
    }

    /**
     * 更新元数据命令
     */
    public UpdateMetadataCommand toUpdateMetadataCommand(String fileId, Map<String, String> metadata) {
        return UpdateMetadataCommand.builder()
                .fileId(fileId)
                .metadata(metadata)
                .merge(true)
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .userName(getCurrentUserName())
                .build();
    }

    /**
     * 添加标签命令
     */
    public AddTagsCommand toAddTagsCommand(String fileId, List<String> tags) {
        return AddTagsCommand.builder()
                .fileId(fileId)
                .tags(tags)
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .userName(getCurrentUserName())
                .build();
    }

    /**
     * 删除标签命令
     */
    public RemoveTagCommand toRemoveTagCommand(String fileId, String tag) {
        return RemoveTagCommand.builder()
                .fileId(fileId)
                .tag(tag)
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .userName(getCurrentUserName())
                .build();
    }

    /**
     * 批量更新命令
     */
    public BatchUpdateCommand toBatchUpdateCommand(BatchUpdateRequest request) {
        return BatchUpdateCommand.builder()
                .fileIds(request.getFileIds())
                .updates(request.getUpdates())
                .ignoreNotFound(request.getIgnoreNotFound() != null ? request.getIgnoreNotFound() : false)
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .userName(getCurrentUserName())
                .build();
    }

    /**
     * 更新权限命令
     */
    public UpdatePermissionCommand toUpdatePermissionCommand(String fileId, UpdatePermissionRequest request) {
        return UpdatePermissionCommand.builder()
                .fileId(fileId)
                .isPublic(request.getIsPublic())
                .allowedUsers(toPermissionItemDtoList(request.getAllowedUsers()))
                .allowedRoles(toPermissionItemDtoList(request.getAllowedRoles()))
                .allowedGroups(toPermissionItemDtoList(request.getAllowedGroups()))
                .permissionLevel(request.getPermissionLevel())
                .comment(request.getComment())
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .userName(getCurrentUserName())
                .build();
    }

    public List<PermissionItemDto> toPermissionItemDtoList(List<PermissionItemResponse> permissionItemResponseList) {
        if (permissionItemResponseList.isEmpty()) {
            return Collections.emptyList();
        }
        return permissionItemResponseList.stream().map(this::toPermissionItemDto).toList();
    }

    public PermissionItemDto toPermissionItemDto(PermissionItemResponse permissionItemResponse) {
        return PermissionItemDto.builder()
                .id(permissionItemResponse.getId())
                .name(permissionItemResponse.getName())
                .type(permissionItemResponse.getType())
                .actions(permissionItemResponse.getActions())
                .build();
    }

    /**
     * 设置公开命令
     */
    public SetFilePublicCommand toSetFilePublicCommand(String fileId, Boolean publicAccess) {
        return SetFilePublicCommand.builder()
                .fileId(fileId)
                .publicAccess(publicAccess != null ? publicAccess : false)
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .userName(getCurrentUserName())
                .build();
    }

    // ==================== Result → Response 转换 ====================

    /**
     * 文件信息结果 → 文件信息响应
     */
    public FileInfoResponse toFileInfoResponse(FileInfoResult result) {
        if (result == null) return null;
        return FileInfoResponse.builder()
                .fileId(result.getFileId())
                .fileName(result.getFileName())
                .filePath(result.getFilePath())
                .fileSize(result.getFileSize())
                .fileMd5(result.getFileMd5())
                .fileType(result.getFileType())
                .fileExtension(result.getFileExtension())
                .storageType(result.getStorageType())
                .storageUrl(result.getStorageUrl())
                .fileStatus(result.getFileStatus())
                .fileStatusDesc(result.getFileStatusDesc())
                .uploadStatus(result.getUploadStatus())
                .uploadProgress(result.getUploadProgress())
                .downloadCount(result.getDownloadCount())
                .viewCount(result.getViewCount())
                .tags(result.getTags())
                .description(result.getDescription())
                .createdUserId(result.getCreatedUserId())
                .createdUserName(result.getCreatedUserName())
                .createdTime(result.getCreatedTime())
                .uploadTime(result.getUploadTime())
                .lastAccessTime(result.getLastAccessTime())
                .lastModifiedTime(result.getLastModifiedTime())
                .build();
    }

    /**
     * 文件信息结果列表 → 文件信息响应列表
     */
    public List<FileInfoResponse> toFileInfoResponseList(List<FileInfoResult> results) {
        if (CollectionUtils.isEmpty(results)) {
            return Collections.emptyList();
        }
        return results.stream()
                .map(this::toFileInfoResponse)
                .collect(Collectors.toList());
    }

    /**
     * 文件详情结果 → 文件详情响应
     */
    public FileDetailInfoResponse toFileDetailInfoResponse(FileDetailInfoResult result) {
        if (result == null) {
            return null;
        }
        return FileDetailInfoResponse.builder()
                .fileId(result.getFileId())
                .fileName(result.getFileName())
                .filePath(result.getFilePath())
                .fileSize(result.getFileSize())
                .fileMd5(result.getFileMd5())
                .fileSha256(result.getFileSha256())
                .fileType(result.getFileType())
                .fileExtension(result.getFileExtension())
                .storageType(result.getStorageType())
                .storageBucket(result.getStorageBucket())
                .storageRegion(result.getStorageRegion())
                .storageUrl(result.getStorageUrl())
                .storageMetadata(result.getStorageMetadata())
                .fileStatus(result.getFileStatus())
                .fileStatusDesc(result.getFileStatusDesc())
                .uploadStatus(result.getUploadStatus())
                .uploadStatusDesc(result.getUploadStatusDesc())
                .uploadProgress(result.getUploadProgress())
                .downloadCount(result.getDownloadCount())
                .viewCount(result.getViewCount())
                .tags(result.getTags())
                .description(result.getDescription())
                .businessId(result.getBusinessId())
                .businessType(result.getBusinessType())
                .createdUserId(result.getCreatedUserId())
                .createdUserName(result.getCreatedUserName())
                .createdTime(result.getCreatedTime())
                .uploadTime(result.getUploadTime())
                .lastAccessTime(result.getLastAccessTime())
                .lastModifiedTime(result.getLastModifiedTime())
                .expireTime(result.getExpireTime())
                .updatedUserId(result.getUpdatedUserId())
                .updatedUserName(result.getUpdatedUserName())
                .updatedTime(result.getUpdatedTime())
                .isEncrypted(result.getIsEncrypted())
                .isCompressed(result.getIsCompressed())
                .isPublic(result.getIsPublic())
                .versionCount(result.getVersionCount())
                .currentVersion(result.getCurrentVersion())
                .versions(toFileVersionInfoResponseList(result.getVersions()))
                .recentLogs(toFileAuditLogResponseList(result.getRecentLogs()))
                .build();
    }

    private FileAuditLogResponse toFileAuditLogResponse(FileAuditLogResult result) {
        if (result == null) {
            return null;
        }
        return FileAuditLogResponse.builder()
                .logId(result.getLogId())
                .fileId(result.getFileId())
                .fileName(result.getFileName())
                .userId(result.getUserId())
                .userName(result.getUserName())
                .operationType(result.getOperationType())
                .operationTypeDesc(result.getOperationTypeDesc())
                .operationDesc(result.getOperationDesc())
                .ipAddress(result.getIpAddress())
                .userAgent(result.getUserAgent())
                .success(result.getSuccess())
                .errorMessage(result.getErrorMessage())
                .costTime(result.getCostTime())
                .createdTime(result.getCreatedTime())
                .extraData(result.getExtraData())
                .build();
    }

    private List<FileAuditLogResponse> toFileAuditLogResponseList(List<FileAuditLogResult> results) {
        if (CollectionUtils.isEmpty(results)) {
            return Collections.emptyList();
        }
        return results.stream()
                .map(this::toFileAuditLogResponse)
                .collect(Collectors.toList());
    }


    private FileVersionInfoResponse toFileVersionInfoResponse(FileVersionInfoResult result) {
        if (result == null) {
            return null;
        }
        return FileVersionInfoResponse.builder()
                .versionId(result.getVersionId())
                .fileId(result.getFileId())
                .version(result.getVersion())
                .versionName(result.getVersionName())
                .changeLog(result.getChangeLog())
                .fileSize(result.getFileSize())
                .fileMd5(result.getFileMd5())
                .createdUserId(result.getCreatedUserId())
                .createdUserName(result.getCreatedUserName())
                .createdTime(result.getCreatedTime())
                .build();
    }

    private List<FileVersionInfoResponse> toFileVersionInfoResponseList(List<FileVersionInfoResult> results) {
        if (CollectionUtils.isEmpty(results)) {
            return Collections.emptyList();
        }
        return results.stream()
                .map(this::toFileVersionInfoResponse)
                .collect(Collectors.toList());
    }

    /**
     * 存储信息结果 → 存储信息响应
     */
    public StorageInfoResponse toStorageInfoResponse(StorageInfoResult result) {
        if (result == null) return null;
        return StorageInfoResponse.builder()
                .fileId(result.getFileId())
                .fileName(result.getFileName())
                .storageType(result.getStorageType())
                .storageTypeDesc(result.getStorageTypeDesc())
                .storageBucket(result.getStorageBucket())
                .storageRegion(result.getStorageRegion())
                .storagePath(result.getStoragePath())
                .storageUrl(result.getStorageUrl())
                .storageCost(result.getStorageCost())
                .storageClass(result.getStorageClass())
                .storageTime(result.getStorageTime())
                .expireTime(result.getExpireTime())
                .build();
    }

    /**
     * 访问信息结果 → 访问信息响应
     */
    public AccessInfoResponse toAccessInfoResponse(AccessInfoResult result) {
        if (result == null) return null;
        return AccessInfoResponse.builder()
                .fileId(result.getFileId())
                .fileName(result.getFileName())
                .totalDownloads(result.getTotalDownloads())
                .totalViews(result.getTotalViews())
                .uniqueUsers(result.getUniqueUsers())
                .todayDownloads(result.getTodayDownloads())
                .weekDownloads(result.getWeekDownloads())
                .monthDownloads(result.getMonthDownloads())
                .avgDownloadSpeed(result.getAvgDownloadSpeed())
                .downloadTrend(toTrendDataResponseList(result.getDownloadTrend()))
                .viewTrend(toTrendDataResponseList(result.getViewTrend()))
                .recentAccesses(toAccessRecordResponseList(result.getRecentAccesses()))
                .build();
    }

    public List<AccessRecordResponse> toAccessRecordResponseList(List<AccessRecordResult> results) {
        if (CollectionUtils.isEmpty(results)) {
            return Collections.emptyList();
        }
        return results.stream()
                .map(this::toAccessRecordResponse)
                .collect(Collectors.toList());
    }

    public AccessRecordResponse toAccessRecordResponse(AccessRecordResult result) {
        if (result == null) return null;
        return AccessRecordResponse.builder()
                .userId(result.getUserId())
                .userName(result.getUserName())
                .operationType(result.getOperationType())
                .ipAddress(result.getIpAddress())
                .userAgent(result.getUserAgent())
                .accessTime(result.getAccessTime())
                .duration(result.getDuration())
                .success(result.getSuccess())
                .build();
    }

    /**
     * 文件类型结果 → 文件类型响应
     */
    public FileTypeResponse toFileTypeResponse(FileTypeResult result) {
        if (result == null) return null;
        return FileTypeResponse.builder()
                .typeCode(result.getTypeCode())
                .typeName(result.getTypeName())
                .description(result.getDescription())
                .extensions(result.getExtensions())
                .icon(result.getIcon())
                .color(result.getColor())
                .isEnabled(result.getIsEnabled())
                .build();
    }

    /**
     * 文件类型结果列表 → 文件类型响应列表
     */
    public List<FileTypeResponse> toFileTypeResponseList(List<FileTypeResult> results) {
        if (CollectionUtils.isEmpty(results)) {
            return Collections.emptyList();
        }
        return results.stream()
                .map(this::toFileTypeResponse)
                .collect(Collectors.toList());
    }

    /**
     * 文件类型统计结果 → 文件类型统计响应
     */
    public FileTypeStatisticsResponse toFileTypeStatisticsResponse(FileTypeStatisticsResult result) {
        if (result == null) return null;
        return FileTypeStatisticsResponse.builder()
                .fileType(result.getFileType())
                .fileTypeDesc(result.getFileTypeDesc())
                .fileCount(result.getFileCount())
                .totalSize(result.getTotalSize())
                .percentage(result.getPercentage())
                .storageStats(toStorageTypeStatsResponseList(result.getStorageStats()))
                .build();
    }

    public List<StorageTypeStatsResponse> toStorageTypeStatsResponseList(List<StorageTypeStatsResult> results) {
        if (CollectionUtils.isEmpty(results)) {
            return Collections.emptyList();
        }
        return results.stream()
                .map(this::toStorageTypeStatsResponse)
                .collect(Collectors.toList());
    }

    public StorageTypeStatsResponse toStorageTypeStatsResponse(StorageTypeStatsResult result) {
        if (result == null) return null;
        return StorageTypeStatsResponse.builder()
                .storageType(result.getStorageType())
                .storageTypeDesc(result.getStorageTypeDesc())
                .fileCount(result.getFileCount())
                .totalSize(result.getTotalSize())
                .usedCapacity(result.getUsedCapacity())
                .maxCapacity(result.getMaxCapacity())
                .usageRate(result.getUsageRate())
                .build();
    }

    /**
     * 文件类型统计结果列表 → 文件类型统计响应列表
     */
    public List<FileTypeStatisticsResponse> toFileTypeStatisticsResponseList(List<FileTypeStatisticsResult> results) {
        if (CollectionUtils.isEmpty(results)) {
            return Collections.emptyList();
        }
        return results.stream()
                .map(this::toFileTypeStatisticsResponse)
                .collect(Collectors.toList());
    }

    /**
     * 存储统计结果 → 存储统计响应
     */
    public StorageStatsResponse toStorageStatsResponse(StorageStatsResult result) {
        if (result == null) return null;
        return StorageStatsResponse.builder()
                .storageType(result.getStorageType())
                .storageTypeDesc(result.getStorageTypeDesc())
                .fileCount(result.getFileCount())
                .totalSize(result.getTotalSize())
                .availableSpace(result.getAvailableSpace())
                .usedSpace(result.getUsedSpace())
                .usageRate(result.getUsageRate())
                .nodeCount(result.getNodeCount())
                .healthNodes(result.getHealthNodes())
                .build();
    }

    /**
     * 存储统计结果列表 → 存储统计响应列表
     */
    public List<StorageStatsResponse> toStorageStatsResponseList(List<StorageStatsResult> results) {
        if (CollectionUtils.isEmpty(results)) {
            return Collections.emptyList();
        }
        return results.stream()
                .map(this::toStorageStatsResponse)
                .collect(Collectors.toList());
    }

    /**
     * 大小分布结果 → 大小分布响应
     */
    public SizeDistributionResponse toSizeDistributionResponse(SizeDistributionResult result) {
        if (result == null) return null;
        return SizeDistributionResponse.builder()
                .ranges(toSizeRangeResponseList(result.getRanges()))
                .totalFiles(result.getTotalFiles())
                .totalSize(result.getTotalSize())
                .avgSize(result.getAvgSize())
                .minSize(result.getMinSize())
                .maxSize(result.getMaxSize())
                .build();
    }

    public List<SizeRangeResponse> toSizeRangeResponseList(List<SizeRangeResult> results) {
        if (CollectionUtils.isEmpty(results)) {
            return Collections.emptyList();
        }
        return results.stream()
                .map(this::toSizeRangeResponse)
                .collect(Collectors.toList());
    }

    public SizeRangeResponse toSizeRangeResponse(SizeRangeResult result) {
        if (result == null) return null;
        return SizeRangeResponse.builder()
                .minSize(result.getMinSize())
                .maxSize(result.getMaxSize())
                .fileCount(result.getFileCount())
                .totalSize(result.getTotalSize())
                .percentage(result.getPercentage())
                .range(result.getRange())
                .build();
    }

    /**
     * 趋势数据结果列表 → 趋势数据响应列表
     */
    public List<TrendDataResponse> toTrendDataResponseList(List<TrendDataResult> results) {
        if (CollectionUtils.isEmpty(results)) {
            return Collections.emptyList();
        }
        return results.stream()
                .map(this::toTrendDataResponse)
                .collect(Collectors.toList());
    }

    /**
     * 趋势数据结果 → 趋势数据响应
     */
    public TrendDataResponse toTrendDataResponse(TrendDataResult result) {
        if (result == null) return null;
        return TrendDataResponse.builder()
                .date(result.getDate())
                .uploadCount(result.getUploadCount())
                .downloadCount(result.getDownloadCount())
                .totalSize(result.getTotalSize())
                .uniqueUsers(result.getUniqueUsers())
                .build();
    }

    /**
     * 文件存在性结果 → 文件存在性响应
     */
    public FileExistenceResponse toFileExistenceResponse(FileExistenceResult result) {
        if (result == null) return null;
        return FileExistenceResponse.builder()
                .exists(result.getExists())
                .fileId(result.getFileId())
                .fileName(result.getFileName())
                .fileSize(result.getFileSize())
                .fileMd5(result.getFileMd5())
                .fileUrl(result.getFileUrl())
                .isDuplicate(result.getIsDuplicate())
                .duplicateFiles(toFileInfoResponseList(result.getDuplicateFiles()))
                .build();
    }

    /**
     * 完整性检查结果 → 完整性检查响应
     */
    public IntegrityCheckResponse toIntegrityCheckResponse(IntegrityCheckResult result) {
        if (result == null) return null;
        return IntegrityCheckResponse.builder()
                .fileId(result.getFileId())
                .fileName(result.getFileName())
                .isIntegrity(result.getIsIntegrity())
                .storedMd5(result.getStoredMd5())
                .computedMd5(result.getComputedMd5())
                .fileSize(result.getFileSize())
                .isCorrupted(result.getIsCorrupted())
                .checkMethod(result.getCheckMethod())
                .checkTime(result.getCheckTime())
                .message(result.getMessage())
                .build();
    }

    /**
     * 文件对比结果 → 文件对比响应
     */
    public FileCompareResponse toFileCompareResponse(FileCompareResult result) {
        if (result == null) return null;
        return FileCompareResponse.builder()
                .isSame(result.getIsSame())
                .file1(toFileInfoResponse(result.getFile1()))
                .file2(toFileInfoResponse(result.getFile2()))
                .details(toCompareDetailsResponse(result.getDetails()))
                .build();
    }

    public CompareDetailsResponse toCompareDetailsResponse(CompareDetailsResult result) {
        if (result == null) return null;
        return CompareDetailsResponse.builder()
                .nameSame(result.getNameSame())
                .sizeSame(result.getSizeSame())
                .md5Same(result.getMd5Same())
                .typeSame(result.getTypeSame())
                .differences(result.getDifferences())
                .similarity(result.getSimilarity())
                .build();
    }

    /**
     * 权限信息结果 → 权限信息响应
     */
    public PermissionInfoResponse toPermissionInfoResponse(PermissionInfoResult result) {
        if (result == null) return null;
        return PermissionInfoResponse.builder()
                .fileId(result.getFileId())
                .fileName(result.getFileName())
                .isPublic(result.getIsPublic())
                .allowedUsers(toPermissionItemResponseList(result.getAllowedUsers()))
                .allowedRoles(toPermissionItemResponseList(result.getAllowedRoles()))
                .allowedGroups(toPermissionItemResponseList(result.getAllowedGroups()))
                .permissionLevel(result.getPermissionLevel())
                .permissionTime(result.getPermissionTime())
                .build();
    }

    public List<PermissionItemResponse> toPermissionItemResponseList(List<PermissionItemDto> results) {
        if (CollectionUtils.isEmpty(results)) {
            return Collections.emptyList();
        }
        return results.stream()
                .map(this::toPermissionItemResponse)
                .collect(Collectors.toList());
    }

    public PermissionItemResponse toPermissionItemResponse(PermissionItemDto result) {
        if (result == null) return null;
        return PermissionItemResponse.builder()
                .id(result.getId())
                .name(result.getName())
                .type(result.getType())
                .actions(result.getActions())
                .build();
    }

    /**
     * 批量更新结果 → 批量更新响应
     */
    public BatchUpdateResponse toBatchUpdateResponse(BatchUpdateResult result) {
        if (result == null) return null;
        return BatchUpdateResponse.builder()
                .total(result.getTotal())
                .success(result.getSuccess())
                .failed(result.getFailed())
                .failedIds(result.getFailedIds())
                .errors(result.getErrors())
                .updateTime(result.getUpdateTime())
                .build();
    }

}
