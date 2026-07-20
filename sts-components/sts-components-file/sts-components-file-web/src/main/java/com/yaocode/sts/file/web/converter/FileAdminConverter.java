package com.yaocode.sts.file.web.converter;

import com.yaocode.sts.file.interfaces.model.request.BatchArchiveRequest;
import com.yaocode.sts.file.interfaces.model.request.FileListQueryRequest;
import com.yaocode.sts.file.interfaces.model.request.MigrateOptionsRequest;
import com.yaocode.sts.file.interfaces.model.request.StorageNodeInfoRequest;
import com.yaocode.sts.file.interfaces.model.response.AdminStatisticsResponse;
import com.yaocode.sts.file.interfaces.model.response.AuditHistoryResponse;
import com.yaocode.sts.file.interfaces.model.response.BatchArchiveResponse;
import com.yaocode.sts.file.interfaces.model.response.BatchDeleteResponse;
import com.yaocode.sts.file.interfaces.model.response.BatchRestoreResponse;
import com.yaocode.sts.file.interfaces.model.response.CleanupResponse;
import com.yaocode.sts.file.interfaces.model.response.ConnectionTestResponse;
import com.yaocode.sts.file.interfaces.model.response.DuplicateCleanResponse;
import com.yaocode.sts.file.interfaces.model.response.FileAuditInfoResponse;
import com.yaocode.sts.file.interfaces.model.response.FileAuditLogResponse;
import com.yaocode.sts.file.interfaces.model.response.FileDetailInfoResponse;
import com.yaocode.sts.file.interfaces.model.response.FileInfoResponse;
import com.yaocode.sts.file.interfaces.model.response.FileVersionInfoResponse;
import com.yaocode.sts.file.interfaces.model.response.MigrateTaskStatusResponse;
import com.yaocode.sts.file.interfaces.model.response.StorageNodeInfoResponse;
import com.yaocode.sts.file.interfaces.model.response.StorageNodeStatsResponse;
import com.yaocode.sts.file.interfaces.model.response.StorageStatsResponse;
import com.yaocode.sts.file.interfaces.model.response.TrendDataResponse;
import com.yaocode.sts.file.interfaces.model.response.TypeStatsResponse;
import com.yaocode.sts.file.runtime.model.command.AddStorageNodeCommand;
import com.yaocode.sts.file.runtime.model.command.ArchiveFileCommand;
import com.yaocode.sts.file.runtime.model.command.AuditFileCommand;
import com.yaocode.sts.file.runtime.model.command.BatchArchiveCommand;
import com.yaocode.sts.file.runtime.model.command.BatchDeleteCommand;
import com.yaocode.sts.file.runtime.model.command.BatchRestoreCommand;
import com.yaocode.sts.file.runtime.model.command.CancelMigrateCommand;
import com.yaocode.sts.file.runtime.model.command.CleanDuplicateCommand;
import com.yaocode.sts.file.runtime.model.command.CleanExpiredCommand;
import com.yaocode.sts.file.runtime.model.command.CleanTempCommand;
import com.yaocode.sts.file.runtime.model.command.DeleteFileCommand;
import com.yaocode.sts.file.runtime.model.command.DeleteStorageNodeCommand;
import com.yaocode.sts.file.runtime.model.command.MigrateFileCommand;
import com.yaocode.sts.file.runtime.model.command.PermanentDeleteCommand;
import com.yaocode.sts.file.runtime.model.command.RefreshStorageNodeCommand;
import com.yaocode.sts.file.runtime.model.command.RestoreFileCommand;
import com.yaocode.sts.file.runtime.model.command.TestStorageNodeCommand;
import com.yaocode.sts.file.runtime.model.command.UnarchiveFileCommand;
import com.yaocode.sts.file.runtime.model.command.UpdateStorageNodeCommand;
import com.yaocode.sts.file.runtime.model.query.AdminFileListQuery;
import com.yaocode.sts.file.runtime.model.query.AdminStatisticsQuery;
import com.yaocode.sts.file.runtime.model.query.AuditListQuery;
import com.yaocode.sts.file.runtime.model.query.AuditLogQuery;
import com.yaocode.sts.file.runtime.model.query.FileDetailQuery;
import com.yaocode.sts.file.runtime.model.query.FileVersionQuery;
import com.yaocode.sts.file.runtime.model.query.StorageNodeQuery;
import com.yaocode.sts.file.runtime.model.query.StorageNodeStatsQuery;
import com.yaocode.sts.file.runtime.model.query.TrendDataQuery;
import com.yaocode.sts.file.runtime.model.result.AdminStatisticsResult;
import com.yaocode.sts.file.runtime.model.result.AuditHistoryResult;
import com.yaocode.sts.file.runtime.model.result.BatchArchiveResult;
import com.yaocode.sts.file.runtime.model.result.BatchDeleteResult;
import com.yaocode.sts.file.runtime.model.result.BatchRestoreResult;
import com.yaocode.sts.file.runtime.model.result.CleanupResult;
import com.yaocode.sts.file.runtime.model.result.ConnectionTestResult;
import com.yaocode.sts.file.runtime.model.result.DuplicateCleanResult;
import com.yaocode.sts.file.runtime.model.result.FileAuditInfoResult;
import com.yaocode.sts.file.runtime.model.result.FileAuditLogResult;
import com.yaocode.sts.file.runtime.model.result.FileDetailInfoResult;
import com.yaocode.sts.file.runtime.model.result.FileInfoResult;
import com.yaocode.sts.file.runtime.model.result.FileVersionInfoResult;
import com.yaocode.sts.file.runtime.model.result.MigrateTaskStatusResult;
import com.yaocode.sts.file.runtime.model.result.StorageNodeInfoResult;
import com.yaocode.sts.file.runtime.model.result.StorageNodeStatsResult;
import com.yaocode.sts.file.runtime.model.result.StorageStatsResult;
import com.yaocode.sts.file.runtime.model.result.TrendDataResult;
import com.yaocode.sts.file.runtime.model.result.TypeStatsResult;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 文件管理后台转换器
 * <p>
 * 负责 HTTP Request/Response 与 Service Command/Query/Result 之间的转换
 * </p>
 *
 * @author yaocode
 * @since 1.0.0
 */
@Component
public class FileAdminConverter {

    // ==================== 获取上下文信息 ====================

    /**
     * 获取当前租户ID（从SecurityContext或ThreadLocal）
     */
    private String getCurrentTenantId() {
        // 实际实现从上下文获取
        return "default";
    }

    /**
     * 获取当前用户ID
     */
    private String getCurrentUserId() {
        // 实际实现从上下文获取
        return "system";
    }

    /**
     * 获取当前用户名
     */
    private String getCurrentUserName() {
        // 实际实现从上下文获取
        return "admin";
    }

    // ==================== 1. 文件删除转换 ====================

    /**
     * 转换为删除命令
     */
    public DeleteFileCommand toDeleteFileCommand(String fileId) {
        return DeleteFileCommand.builder()
                .fileId(fileId)
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .userName(getCurrentUserName())
                .build();
    }

    /**
     * 转换为批量删除命令
     */
    public BatchDeleteCommand toBatchDeleteCommand(List<String> fileIds) {
        return BatchDeleteCommand.builder()
                .fileIds(fileIds)
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .userName(getCurrentUserName())
                .build();
    }

    /**
     * 转换为永久删除命令
     */
    public PermanentDeleteCommand toPermanentDeleteCommand(String fileId) {
        return PermanentDeleteCommand.builder()
                .fileId(fileId)
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .userName(getCurrentUserName())
                .build();
    }

    // ==================== 2. 文件恢复转换 ====================

    /**
     * 转换为恢复命令
     */
    public RestoreFileCommand toRestoreFileCommand(String fileId) {
        return RestoreFileCommand.builder()
                .fileId(fileId)
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .userName(getCurrentUserName())
                .build();
    }

    /**
     * 转换为批量恢复命令
     */
    public BatchRestoreCommand toBatchRestoreCommand(List<String> fileIds) {
        return BatchRestoreCommand.builder()
                .fileIds(fileIds)
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .userName(getCurrentUserName())
                .build();
    }

    // ==================== 3. 文件归档转换 ====================

    /**
     * 转换为归档命令
     */
    public ArchiveFileCommand toArchiveFileCommand(String fileId, String archiveType) {
        return ArchiveFileCommand.builder()
                .fileId(fileId)
                .archiveType(archiveType)
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .userName(getCurrentUserName())
                .build();
    }

    /**
     * 转换为取消归档命令
     */
    public UnarchiveFileCommand toUnarchiveFileCommand(String fileId) {
        return UnarchiveFileCommand.builder()
                .fileId(fileId)
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .userName(getCurrentUserName())
                .build();
    }

    /**
     * 转换为批量归档命令
     */
    public BatchArchiveCommand toBatchArchiveCommand(BatchArchiveRequest request) {
        return BatchArchiveCommand.builder()
                .fileIds(request.getFileIds())
                .archiveType(request.getArchiveType())
                .reason(request.getReason())
                .deleteAfterArchive(request.getDeleteAfterArchive())
                .immediate(request.getImmediate())
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .userName(getCurrentUserName())
                .build();
    }

    // ==================== 4. 文件迁移转换 ====================

    /**
     * 转换为迁移命令
     */
    public MigrateFileCommand toMigrateFileCommand(String fileId, Integer targetStorageType,
                                                   MigrateOptionsRequest options) {
        return MigrateFileCommand.builder()
                .fileId(fileId)
                .targetStorageType(targetStorageType)
                .async(options != null ? options.getAsync() : true)
                .keepSource(options != null ? options.getKeepSource() : false)
                .timeout(options != null ? options.getTimeout() : 300)
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .userName(getCurrentUserName())
                .build();
    }

    /**
     * 转换为取消迁移命令
     */
    public CancelMigrateCommand toCancelMigrateCommand(String taskId) {
        return CancelMigrateCommand.builder()
                .taskId(taskId)
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .build();
    }

    // ==================== 5. 文件清理转换 ====================

    /**
     * 转换为清理过期文件命令
     */
    public CleanExpiredCommand toCleanExpiredCommand(Integer days, Boolean dryRun) {
        return CleanExpiredCommand.builder()
                .days(days)
                .dryRun(dryRun)
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .build();
    }

    /**
     * 转换为清理临时文件命令
     */
    public CleanTempCommand toCleanTempCommand(Integer hours) {
        return CleanTempCommand.builder()
                .hours(hours)
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .build();
    }

    /**
     * 转换为清理重复文件命令
     */
    public CleanDuplicateCommand toCleanDuplicateCommand(String storageType) {
        return CleanDuplicateCommand.builder()
                .storageType(storageType)
                .autoDelete(true)
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .build();
    }

    // ==================== 6. 文件统计转换 ====================

    /**
     * 转换为统计查询
     */
    public AdminStatisticsQuery toAdminStatisticsQuery(String storageType) {
        return AdminStatisticsQuery.builder()
                .storageType(storageType)
                .tenantId(getCurrentTenantId())
                .build();
    }

    /**
     * 转换为趋势查询
     */
    public TrendDataQuery toTrendDataQuery(String period, String startDate, String endDate) {
        return TrendDataQuery.builder()
                .period(period)
                .startDate(startDate)
                .endDate(endDate)
                .tenantId(getCurrentTenantId())
                .build();
    }

    /**
     * 转换为存储节点统计查询
     */
    public StorageNodeStatsQuery toStorageNodeStatsQuery() {
        return StorageNodeStatsQuery.builder()
                .tenantId(getCurrentTenantId())
                .build();
    }

    // ==================== 7. 存储节点管理转换 ====================

    /**
     * 转换为存储节点查询
     */
    public StorageNodeQuery toStorageNodeQuery(Boolean enabledOnly) {
        return StorageNodeQuery.builder()
                .enabledOnly(enabledOnly)
                .tenantId(getCurrentTenantId())
                .build();
    }

    /**
     * 转换为添加存储节点命令
     */
    public AddStorageNodeCommand toAddStorageNodeCommand(StorageNodeInfoRequest request) {
        return AddStorageNodeCommand.builder()
                .nodeCode(request.getNodeCode())
                .nodeName(request.getNodeName())
                .storageType(request.getStorageType())
                .endpoint(request.getEndpoint())
                .bucketName(request.getBucketName())
                .accessKey(request.getAccessKey())
                .secretKey(request.getSecretKey())
                .region(request.getRegion())
                .maxCapacity(request.getMaxCapacity())
                .weight(request.getWeight())
                .priority(request.getPriority())
                .enabled(request.getEnabled())
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .build();
    }

    /**
     * 转换为更新存储节点命令
     */
    public UpdateStorageNodeCommand toUpdateStorageNodeCommand(Long nodeId,
                                                               StorageNodeInfoRequest request) {
        return UpdateStorageNodeCommand.builder()
                .nodeId(nodeId)
                .nodeCode(request.getNodeCode())
                .nodeName(request.getNodeName())
                .endpoint(request.getEndpoint())
                .bucketName(request.getBucketName())
                .accessKey(request.getAccessKey())
                .secretKey(request.getSecretKey())
                .region(request.getRegion())
                .maxCapacity(request.getMaxCapacity())
                .weight(request.getWeight())
                .priority(request.getPriority())
                .enabled(request.getEnabled())
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .build();
    }

    /**
     * 转换为删除存储节点命令
     */
    public DeleteStorageNodeCommand toDeleteStorageNodeCommand(Long nodeId) {
        return DeleteStorageNodeCommand.builder()
                .nodeId(nodeId)
                .force(false)
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .build();
    }

    /**
     * 转换为测试存储节点命令
     */
    public TestStorageNodeCommand toTestStorageNodeCommand(Long nodeId) {
        return TestStorageNodeCommand.builder()
                .nodeId(nodeId)
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .build();
    }

    /**
     * 转换为刷新存储节点命令
     */
    public RefreshStorageNodeCommand toRefreshStorageNodeCommand(Long nodeId) {
        return RefreshStorageNodeCommand.builder()
                .nodeId(nodeId)
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .build();
    }

    // ==================== 8. 文件审核与审计转换 ====================

    /**
     * 转换为审核列表查询
     */
    public AuditListQuery toAuditListQuery(Integer page, Integer size, Integer status) {
        return AuditListQuery.builder()
                .page(page)
                .size(size)
                .status(status)
                .tenantId(getCurrentTenantId())
                .build();
    }

    /**
     * 转换为审核命令
     */
    public AuditFileCommand toAuditFileCommand(String fileId, Boolean approved, String comment) {
        return AuditFileCommand.builder()
                .fileId(fileId)
                .approved(approved)
                .comment(comment)
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .userName(getCurrentUserName())
                .build();
    }

    /**
     * 转换为审计日志查询
     */
    public AuditLogQuery toAuditLogQuery(String fileId, Integer operationType,
                                         String startTime, String endTime,
                                         Integer page, Integer size) {
        return AuditLogQuery.builder()
                .fileId(fileId)
                .operationType(operationType)
                .startTime(startTime)
                .endTime(endTime)
                .page(page)
                .size(size)
                .tenantId(getCurrentTenantId())
                .build();
    }

    // ==================== 9. 文件信息查询转换 ====================

    /**
     * 转换为文件详情查询
     */
    public FileDetailQuery toFileDetailQuery(String fileId) {
        return FileDetailQuery.builder()
                .fileId(fileId)
                .tenantId(getCurrentTenantId())
                .build();
    }

    /**
     * 转换为管理端文件列表查询
     */
    public AdminFileListQuery toAdminFileListQuery(FileListQueryRequest request) {
        return AdminFileListQuery.builder()
                .fileName(request.getFileName())
                .fileStatus(request.getFileStatus())
                .storageType(request.getStorageType())
                .businessId(request.getBusinessId())
                .businessType(request.getBusinessType())
                .uploadUserId(request.getUploadUserId())
                .uploadUserName(request.getUploadUserName())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .minSize(request.getMinSize())
                .maxSize(request.getMaxSize())
                .tags(request.getTags())
                .page(request.getPage())
                .size(request.getSize())
                .orderBy(request.getOrderBy())
                .orderDirection(request.getOrderDirection())
                .tenantId(getCurrentTenantId())
                .build();
    }

    /**
     * 转换为文件版本查询
     */
    public FileVersionQuery toFileVersionQuery(String fileId) {
        return FileVersionQuery.builder()
                .fileId(fileId)
                .tenantId(getCurrentTenantId())
                .build();
    }

    // ==================== Result → Response 转换 ====================

    /**
     * 批量删除结果 → 批量删除响应
     */
    public BatchDeleteResponse toBatchDeleteResponse(BatchDeleteResult result) {
        if (result == null) return null;
        return BatchDeleteResponse.builder()
                .total(result.getTotal())
                .success(result.getSuccess())
                .failed(result.getFailed())
                .failedIds(result.getFailedIds())
                .errors(result.getErrors())
                .executionTime(result.getExecutionTime())
                .build();
    }

    /**
     * 批量恢复结果 → 批量恢复响应
     */
    public BatchRestoreResponse toBatchRestoreResponse(BatchRestoreResult result) {
        if (result == null) return null;
        return BatchRestoreResponse.builder()
                .total(result.getTotal())
                .success(result.getSuccess())
                .failed(result.getFailed())
                .failedIds(result.getFailedIds())
                .errors(result.getErrors())
                .executionTime(result.getExecutionTime())
                .build();
    }

    /**
     * 批量归档结果 → 批量归档响应
     */
    public BatchArchiveResponse toBatchArchiveResponse(BatchArchiveResult result) {
        if (result == null) return null;
        return BatchArchiveResponse.builder()
                .total(result.getTotal())
                .success(result.getSuccess())
                .failed(result.getFailed())
                .failedIds(result.getFailedIds())
                .archiveTaskId(result.getArchiveTaskId())
                .errors(result.getErrors())
                .executionTime(result.getExecutionTime())
                .build();
    }

    /**
     * 清理结果 → 清理响应
     */
    public CleanupResponse toCleanupResponse(CleanupResult result) {
        if (result == null) return null;
        return CleanupResponse.builder()
                .totalScanned(result.getTotalScanned())
                .totalDeleted(result.getTotalDeleted())
                .totalFreedSpace(result.getTotalFreedSpace())
                .executionTime(result.getExecutionTime())
                .message(result.getMessage())
                .build();
    }

    /**
     * 重复文件清理结果 → 重复文件清理响应
     */
    public DuplicateCleanResponse toDuplicateCleanResponse(DuplicateCleanResult result) {
        if (result == null) return null;
        return DuplicateCleanResponse.builder()
                .duplicateGroups(result.getDuplicateGroups())
                .duplicateFiles(result.getDuplicateFiles())
                .duplicateSpace(result.getDuplicateSpace())
                .keptFiles(result.getKeptFiles())
                .deletedFiles(result.getDeletedFiles())
                .executionTime(result.getExecutionTime())
                .build();
    }

    /**
     * 统计结果 → 统计响应
     */
    public AdminStatisticsResponse toAdminStatisticsResponse(AdminStatisticsResult result) {
        if (result == null) return null;
        return AdminStatisticsResponse.builder()
                .totalFiles(result.getTotalFiles())
                .totalSize(result.getTotalSize())
                .totalUsers(result.getTotalUsers())
                .todayUploads(result.getTodayUploads())
                .todayDownloads(result.getTodayDownloads())
                .todaySize(result.getTodaySize())
                .storageStats(toStorageStatsResponse(result.getStorageStats()))
                .statusDistribution(result.getStatusDistribution())
                .typeStats(toTypeStatsResponse(result.getTypeStats()))
                .uploadTrend(toTrendDataResponseList(result.getUploadTrend()))
                .downloadTrend(toTrendDataResponseList(result.getDownloadTrend()))
                .statisticsTime(result.getStatisticsTime())
                .build();
    }

    public Map<String, TypeStatsResponse> toTypeStatsResponse(Map<String, TypeStatsResult> result) {
        if (result == null) return null;
        Map<String, TypeStatsResponse> storageStats = new HashMap<>();
        result.forEach((key, value) -> storageStats.put(key, toTypeStatsResponse(value)));
        return storageStats;
    }

    public TypeStatsResponse toTypeStatsResponse(TypeStatsResult result) {
        if (result == null) return null;
        return TypeStatsResponse.builder()
                .fileType(result.getFileType())
                .fileTypeDesc(result.getFileTypeDesc())
                .fileCount(result.getFileCount())
                .totalSize(result.getTotalSize())
                .percentage(result.getPercentage())
                .build();
    }

    public Map<String, StorageStatsResponse> toStorageStatsResponse(Map<String, StorageStatsResult> result) {
        if (result == null) return null;
        Map<String, StorageStatsResponse> storageStats = new HashMap<>();
        result.forEach((key, value) -> storageStats.put(key, toStorageStatsResponse(value)));
        return storageStats;
    }

    public StorageStatsResponse toStorageStatsResponse(StorageStatsResult result) {
        if (result == null) return null;
        return StorageStatsResponse.builder()
                .storageType(result.getStorageType())
                .fileCount(result.getFileCount())
                .usedSpace(result.getUsedSpace())
                .maxCapacity(result.getMaxCapacity())
                .usageRate(result.getUsageRate())
                .nodeCount(result.getNodeCount())
                .healthNodes(result.getHealthNodes())
                .storageTypeDesc(result.getStorageTypeDesc())
                .availableSpace(result.getAvailableSpace())
                .build();
    }

    /**
     * 存储节点信息结果 → 存储节点信息响应
     */
    public StorageNodeInfoResponse toStorageNodeInfoResponse(StorageNodeInfoResult result) {
        if (result == null) return null;
        return StorageNodeInfoResponse.builder()
                .nodeId(result.getNodeId())
                .nodeCode(result.getNodeCode())
                .nodeName(result.getNodeName())
                .storageType(result.getStorageType())
                .storageTypeDesc(result.getStorageTypeDesc())
                .endpoint(result.getEndpoint())
                .bucketName(result.getBucketName())
                .maxCapacity(result.getMaxCapacity())
                .usedCapacity(result.getUsedCapacity())
                .usageRate(result.getUsageRate())
                .nodeStatus(result.getNodeStatus())
                .nodeStatusDesc(result.getNodeStatusDesc())
                .healthStatus(result.getHealthStatus())
                .healthStatusDesc(result.getHealthStatusDesc())
                .enabled(result.getEnabled())
                .weight(result.getWeight())
                .priority(result.getPriority())
                .lastHealthCheck(result.getLastHealthCheck())
                .createdTime(result.getCreatedTime())
                .updatedTime(result.getUpdatedTime())
                .build();
    }

    /**
     * 存储节点统计结果列表 → 存储节点统计响应列表
     */
    public List<StorageNodeStatsResponse> toStorageNodeStatsResponseList(
            List<StorageNodeStatsResult> results) {
        if (results == null) return null;
        return results.stream()
                .map(this::toStorageNodeStatsResponse)
                .collect(Collectors.toList());
    }

    /**
     * 存储节点统计结果 → 存储节点统计响应
     */
    public StorageNodeStatsResponse toStorageNodeStatsResponse(StorageNodeStatsResult result) {
        if (result == null) return null;
        return StorageNodeStatsResponse.builder()
                .nodeId(result.getNodeId())
                .nodeName(result.getNodeName())
                .storageType(result.getStorageType())
                .fileCount(result.getFileCount())
                .usedSpace(result.getUsedSpace())
                .maxCapacity(result.getMaxCapacity())
                .usageRate(result.getUsageRate())
                .healthStatus(result.getHealthStatus())
                .healthStatusDesc(result.getHealthStatusDesc())
                .activeConnections(result.getActiveConnections())
                .totalRequests(result.getTotalRequests())
                .avgResponseTime(result.getAvgResponseTime())
                .build();
    }

    /**
     * 连接测试结果 → 连接测试响应
     */
    public ConnectionTestResponse toConnectionTestResponse(ConnectionTestResult result) {
        if (result == null) return null;
        return ConnectionTestResponse.builder()
                .success(result.getSuccess())
                .latency(result.getLatency())
                .message(result.getMessage())
                .serverVersion(result.getServerVersion())
                .serverInfo(result.getServerInfo())
                .testTime(result.getTestTime())
                .statusCode(result.getStatusCode())
                .build();
    }

    /**
     * 迁移任务状态结果 → 迁移任务状态响应
     */
    public MigrateTaskStatusResponse toMigrateTaskStatusResponse(MigrateTaskStatusResult result) {
        if (result == null) return null;
        return MigrateTaskStatusResponse.builder()
                .taskId(result.getTaskId())
                .fileId(result.getFileId())
                .fileName(result.getFileName())
                .sourceStorage(result.getSourceStorage())
                .targetStorage(result.getTargetStorage())
                .progress(result.getProgress())
                .status(result.getStatus())
                .statusDesc(result.getStatusDesc())
                .transferredSize(result.getTransferredSize())
                .totalSize(result.getTotalSize())
                .startTime(result.getStartTime())
                .endTime(result.getEndTime())
                .elapsedTime(result.getElapsedTime())
                .errorMessage(result.getErrorMessage())
                .build();
    }

    /**
     * 趋势数据结果列表 → 趋势数据响应列表
     */
    public List<TrendDataResponse> toTrendDataResponseList(List<TrendDataResult> results) {
        if (results == null) return null;
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
                .avgUploadSize(result.getAvgUploadSize())
                .uploadSize(result.getUploadSize())
                .downloadSize(result.getDownloadSize())
                .build();
    }

    /**
     * 文件审计信息结果列表 → 文件审计信息响应列表
     */
    public List<FileAuditInfoResponse> toFileAuditInfoResponseList(
            List<FileAuditInfoResult> results) {
        if (results == null) return null;
        return results.stream()
                .map(this::toFileAuditInfoResponse)
                .collect(Collectors.toList());
    }

    /**
     * 文件审计信息结果 → 文件审计信息响应
     */
    public FileAuditInfoResponse toFileAuditInfoResponse(FileAuditInfoResult result) {
        if (result == null) return null;
        return FileAuditInfoResponse.builder()
                .fileId(result.getFileId())
                .fileName(result.getFileName())
                .fileSize(result.getFileSize())
                .fileType(result.getFileType())
                .uploadUserId(result.getUploadUserId())
                .uploadUserName(result.getUploadUserName())
                .uploadTime(result.getUploadTime())
                .auditStatus(result.getAuditStatus())
                .auditStatusDesc(result.getAuditStatusDesc())
                .auditComment(result.getAuditComment())
                .auditUserId(result.getAuditUserId())
                .auditUserName(result.getAuditUserName())
                .auditTime(result.getAuditTime())
                .auditHistory(toAuditHistoryResponseList(result.getAuditHistory()))
                .build();
    }

    public List<AuditHistoryResponse> toAuditHistoryResponseList(List<AuditHistoryResult> results) {
        if (results == null) return null;
        return results.stream()
                .map(this::toAuditHistoryResponse)
                .collect(Collectors.toList());
    }

    public AuditHistoryResponse toAuditHistoryResponse(AuditHistoryResult result) {
        if (result == null) return null;
        return AuditHistoryResponse.builder()
                .action(result.getAction())
                .comment(result.getComment())
                .userId(result.getUserId())
                .userName(result.getUserName())
                .actionTime(result.getActionTime())
                .build();
    }

    /**
     * 文件审计日志结果列表 → 文件审计日志响应列表
     */
    public List<FileAuditLogResponse> toFileAuditLogResponseList(
            List<FileAuditLogResult> results) {
        if (results == null) return null;
        return results.stream()
                .map(this::toFileAuditLogResponse)
                .collect(Collectors.toList());
    }

    /**
     * 文件审计日志结果 → 文件审计日志响应
     */
    public FileAuditLogResponse toFileAuditLogResponse(FileAuditLogResult result) {
        if (result == null) return null;
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

    /**
     * 文件详情结果 → 文件详情响应
     */
    public FileDetailInfoResponse toFileDetailInfoResponse(FileDetailInfoResult result) {
        if (result == null) return null;
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

    /**
     * 文件信息结果列表 → 文件信息响应列表
     */
    public List<FileInfoResponse> toFileInfoResponseList(List<FileInfoResult> results) {
        if (results == null) return null;
        return results.stream()
                .map(this::toFileInfoResponse)
                .collect(Collectors.toList());
    }

    /**
     * 文件信息结果 → 文件信息响应
     */
    public FileInfoResponse toFileInfoResponse(FileInfoResult result) {
        if (result == null) return null;
        return FileInfoResponse.builder()
                .fileId(result.getFileId())
                .fileName(result.getFileName())
                .fileSize(result.getFileSize())
                .fileMd5(result.getFileMd5())
                .fileType(result.getFileType())
                .storageType(result.getStorageType())
                .storageUrl(result.getStorageUrl())
                .fileStatus(result.getFileStatus())
                .fileStatusDesc(result.getFileStatusDesc())
                .downloadCount(result.getDownloadCount())
                .tags(result.getTags())
                .description(result.getDescription())
                .createdUserId(result.getCreatedUserId())
                .createdUserName(result.getCreatedUserName())
                .createdTime(result.getCreatedTime())
                .build();
    }

    /**
     * 文件版本信息结果列表 → 文件版本信息响应列表
     */
    public List<FileVersionInfoResponse> toFileVersionInfoResponseList(
            List<FileVersionInfoResult> results) {
        if (results == null) return null;
        return results.stream()
                .map(this::toFileVersionInfoResponse)
                .collect(Collectors.toList());
    }

    /**
     * 文件版本信息结果 → 文件版本信息响应
     */
    public FileVersionInfoResponse toFileVersionInfoResponse(FileVersionInfoResult result) {
        if (result == null) return null;
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

    /**
     * 存储节点信息结果列表 → 存储节点信息响应列表
     */
    public List<StorageNodeInfoResponse> toStorageNodeInfoResponseList(
            List<StorageNodeInfoResult> results) {
        if (results == null) return null;
        return results.stream()
                .map(this::toStorageNodeInfoResponse)
                .collect(Collectors.toList());
    }
}
