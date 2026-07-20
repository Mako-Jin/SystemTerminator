package com.yaocode.sts.file.web.converter;

import com.yaocode.sts.file.interfaces.model.response.BatchRestoreResponse;
import com.yaocode.sts.file.interfaces.model.response.CleanupResponse;
import com.yaocode.sts.file.interfaces.model.response.RecycleFileInfoResponse;
import com.yaocode.sts.file.interfaces.model.response.RecycleStatisticsResponse;
import com.yaocode.sts.file.interfaces.model.response.TrendDataResponse;
import com.yaocode.sts.file.runtime.model.command.BatchRestoreFromRecycleCommand;
import com.yaocode.sts.file.runtime.model.command.EmptyRecycleBinByConditionsCommand;
import com.yaocode.sts.file.runtime.model.command.EmptyRecycleBinCommand;
import com.yaocode.sts.file.runtime.model.command.PermanentDeleteCommand;
import com.yaocode.sts.file.runtime.model.command.RestoreFromRecycleCommand;
import com.yaocode.sts.file.runtime.model.query.RecycleBinQuery;
import com.yaocode.sts.file.runtime.model.result.BatchRestoreResult;
import com.yaocode.sts.file.runtime.model.result.CleanupResult;
import com.yaocode.sts.file.runtime.model.result.RecycleFileInfoResult;
import com.yaocode.sts.file.runtime.model.result.RecycleStatisticsResult;
import com.yaocode.sts.file.runtime.model.result.TrendDataResult;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 文件回收站转换器
 *
 * @author yaocode
 * @since 1.0.0
 */
@Component
public class FileRecycleConverter {

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
     * 回收站列表查询
     */
    public RecycleBinQuery toRecycleBinQuery(Integer page, Integer size,
                                             String fileName, String startTime, String endTime) {
        return RecycleBinQuery.builder()
                .page(page != null ? page : 1)
                .size(size != null ? size : 20)
                .fileName(fileName)
                .startTime(startTime)
                .endTime(endTime)
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .build();
    }

    // ==================== Command 转换 ====================

    /**
     * 恢复文件命令
     */
    public RestoreFromRecycleCommand toRestoreFromRecycleCommand(String fileId) {
        return RestoreFromRecycleCommand.builder()
                .fileId(fileId)
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .userName(getCurrentUserName())
                .build();
    }

    /**
     * 批量恢复文件命令
     */
    public BatchRestoreFromRecycleCommand toBatchRestoreFromRecycleCommand(List<String> fileIds) {
        return BatchRestoreFromRecycleCommand.builder()
                .fileIds(fileIds)
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .userName(getCurrentUserName())
                .build();
    }

    /**
     * 永久删除命令
     */
    public PermanentDeleteCommand toPermanentDeleteCommand(String fileId) {
        return PermanentDeleteCommand.builder()
                .fileId(fileId)
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .userName(getCurrentUserName())
                .reason("从回收站永久删除")
                .build();
    }

    /**
     * 清空回收站命令
     */
    public EmptyRecycleBinCommand toEmptyRecycleBinCommand() {
        return EmptyRecycleBinCommand.builder()
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .userName(getCurrentUserName())
                .build();
    }

    /**
     * 按条件清空回收站命令
     */
    public EmptyRecycleBinByConditionsCommand toEmptyRecycleBinByConditionsCommand(
            Integer days, String storageType, Boolean confirm) {
        return EmptyRecycleBinByConditionsCommand.builder()
                .days(days)
                .storageType(storageType)
                .confirm(confirm != null ? confirm : true)
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .userName(getCurrentUserName())
                .build();
    }

    // ==================== Result → Response 转换 ====================

    /**
     * 回收站文件信息结果 → 回收站文件信息响应
     */
    public RecycleFileInfoResponse toRecycleFileInfoResponse(RecycleFileInfoResult result) {
        if (result == null) return null;
        return RecycleFileInfoResponse.builder()
                .fileId(result.getFileId())
                .fileName(result.getFileName())
                .fileSize(result.getFileSize())
                .fileMd5(result.getFileMd5())
                .storageType(result.getStorageType())
                .originalPath(result.getOriginalPath())
                .deletedUserId(result.getDeletedUserId())
                .deletedUserName(result.getDeletedUserName())
                .deletedTime(result.getDeletedTime())
                .expireTime(result.getExpireTime())
                .remainingDays(result.getRemainingDays())
                .deleteReason(result.getDeleteReason())
                .canRestore(result.getCanRestore())
                .build();
    }

    /**
     * 回收站文件信息结果列表 → 回收站文件信息响应列表
     */
    public List<RecycleFileInfoResponse> toRecycleFileInfoResponseList(List<RecycleFileInfoResult> results) {
        if (CollectionUtils.isEmpty(results)) {
            return Collections.emptyList();
        }
        return results.stream()
                .map(this::toRecycleFileInfoResponse)
                .collect(Collectors.toList());
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
     * 回收站统计结果 → 回收站统计响应
     */
    public RecycleStatisticsResponse toRecycleStatisticsResponse(RecycleStatisticsResult result) {
        if (result == null) return null;
        return RecycleStatisticsResponse.builder()
                .totalFiles(result.getTotalFiles())
                .totalSize(result.getTotalSize())
                .todayDeleted(result.getTodayDeleted())
                .todayRestored(result.getTodayRestored())
                .expireSoon(result.getExpireSoon())
                .storageTypeStats(result.getStorageTypeStats())
                .deleteTrend(toTrendDataResponseList(result.getDeleteTrend()))
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

}
