package com.yaocode.sts.components.file.web.controller;

import com.yaocode.sts.common.basic.model.PageResult;
import com.yaocode.sts.common.web.model.PageResultModel;
import com.yaocode.sts.common.web.model.ResultModel;
import com.yaocode.sts.common.web.utils.PageResultUtils;
import com.yaocode.sts.common.web.utils.ResultUtils;
import com.yaocode.sts.components.file.interfaces.api.FileAdminApi;
import com.yaocode.sts.components.file.interfaces.model.request.FileListQueryRequest;
import com.yaocode.sts.components.file.interfaces.model.request.MigrateOptionsRequest;
import com.yaocode.sts.components.file.interfaces.model.request.StorageNodeInfoRequest;
import com.yaocode.sts.components.file.interfaces.model.response.AdminStatisticsResponse;
import com.yaocode.sts.components.file.interfaces.model.response.BatchDeleteResponse;
import com.yaocode.sts.components.file.interfaces.model.response.BatchRestoreResponse;
import com.yaocode.sts.components.file.interfaces.model.response.CleanupResponse;
import com.yaocode.sts.components.file.interfaces.model.response.ConnectionTestResponse;
import com.yaocode.sts.components.file.interfaces.model.response.DuplicateCleanResponse;
import com.yaocode.sts.components.file.interfaces.model.response.FileAuditInfoResponse;
import com.yaocode.sts.components.file.interfaces.model.response.FileAuditLogResponse;
import com.yaocode.sts.components.file.interfaces.model.response.FileDetailInfoResponse;
import com.yaocode.sts.components.file.interfaces.model.response.FileInfoResponse;
import com.yaocode.sts.components.file.interfaces.model.response.FileVersionInfoResponse;
import com.yaocode.sts.components.file.interfaces.model.response.MigrateTaskStatusResponse;
import com.yaocode.sts.components.file.interfaces.model.response.StorageNodeInfoResponse;
import com.yaocode.sts.components.file.interfaces.model.response.StorageNodeStatsResponse;
import com.yaocode.sts.components.file.interfaces.model.response.TrendDataResponse;
import com.yaocode.sts.components.file.runtime.model.result.FileAuditInfoResult;
import com.yaocode.sts.components.file.runtime.model.result.FileAuditLogResult;
import com.yaocode.sts.components.file.runtime.model.result.FileInfoResult;
import com.yaocode.sts.components.file.runtime.service.FileAdminService;
import com.yaocode.sts.components.file.web.converter.FileAdminConverter;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 文件管理后台控制器
 * <p>
 * 实现文件管理后台API，提供管理员操作功能
 * </p>
 *
 * @author yaocode
 * @since 1.0.0
 */
@Slf4j
@RestController
public class FileAdminController implements FileAdminApi {

    @Resource
    private FileAdminService fileAdminService;

    @Resource
    private FileAdminConverter converter;

    // ==================== 1. 文件删除 ====================

    @Override
    public ResultModel<String> deleteFile(String fileId) {
        log.info("管理员删除文件: {}", fileId);
        fileAdminService.deleteFile(converter.toDeleteFileCommand(fileId));
        return ResultUtils.ok();
    }

    @Override
    public ResultModel<BatchDeleteResponse> batchDeleteFiles(List<String> fileIds) {
        log.info("管理员批量删除文件: {}", fileIds);
        BatchDeleteResponse response = converter.toBatchDeleteResponse(
                fileAdminService.batchDeleteFiles(
                        converter.toBatchDeleteCommand(fileIds)
                )
        );
        return ResultUtils.ok(response);
    }

    @Override
    public ResultModel<String> permanentDeleteFile(String fileId) {
        log.warn("管理员永久删除文件: {}", fileId);
        fileAdminService.permanentDeleteFile(
                converter.toPermanentDeleteCommand(fileId)
        );
        return ResultUtils.ok();
    }

    // ==================== 2. 文件恢复 ====================

    @Override
    public ResultModel<String> restoreFile(String fileId) {
        log.info("管理员恢复文件: {}", fileId);
        fileAdminService.restoreFile(converter.toRestoreFileCommand(fileId));
        return ResultUtils.ok();
    }

    @Override
    public ResultModel<BatchRestoreResponse> batchRestoreFiles(List<String> fileIds) {
        log.info("管理员批量恢复文件: {}", fileIds);
        BatchRestoreResponse response = converter.toBatchRestoreResponse(
                fileAdminService.batchRestoreFiles(
                        converter.toBatchRestoreCommand(fileIds)
                )
        );
        return ResultUtils.ok(response);
    }

    // ==================== 3. 文件归档 ====================

//    @Override
//    public ResultModel<String> archiveFile(String fileId, String archiveType) {
//        log.info("管理员归档文件: {}, 类型: {}", fileId, archiveType);
//        fileAdminService.archiveFile(
//                converter.toArchiveFileCommand(fileId, archiveType)
//        );
//        return ResultUtils.ok();
//    }
//
//    @Override
//    public ResultModel<String> unarchiveFile(String fileId) {
//        log.info("管理员取消归档文件: {}", fileId);
//        fileAdminService.unarchiveFile(
//                converter.toUnarchiveFileCommand(fileId)
//        );
//        return ResultUtils.ok();
//    }
//
//    @Override
//    public ResultModel<BatchArchiveResponse> batchArchiveFiles(@Valid BatchArchiveRequest request) {
//        log.info("管理员批量归档文件: {}", request.getFileIds());
//        BatchArchiveResponse response = converter.toBatchArchiveResponse(
//                fileAdminService.batchArchiveFiles(
//                        converter.toBatchArchiveCommand(request)
//                )
//        );
//        return ResultUtils.ok(response);
//    }

    // ==================== 4. 文件迁移 ====================

    @Override
    public ResultModel<String> migrateFile(String fileId, Integer targetStorageType,
                                           MigrateOptionsRequest options) {
        log.info("管理员迁移文件: {} -> {}", fileId, targetStorageType);
        String taskId = fileAdminService.migrateFile(
                converter.toMigrateFileCommand(fileId, targetStorageType, options)
        );
        return ResultUtils.ok(taskId);
    }

    @Override
    public ResultModel<MigrateTaskStatusResponse> getMigrateTaskStatus(String taskId) {
        MigrateTaskStatusResponse response = converter.toMigrateTaskStatusResponse(
                fileAdminService.getMigrateTaskStatus(taskId)
        );
        return ResultUtils.ok(response);
    }

    @Override
    public ResultModel<String> cancelMigrateTask(String taskId) {
        log.info("管理员取消迁移任务: {}", taskId);
        fileAdminService.cancelMigrateTask(
                converter.toCancelMigrateCommand(taskId)
        );
        return ResultUtils.ok();
    }

    // ==================== 5. 文件清理 ====================

    @Override
    public ResultModel<CleanupResponse> cleanExpiredFiles(Integer days, Boolean dryRun) {
        log.info("管理员清理过期文件: {}天, 试运行: {}", days, dryRun);
        CleanupResponse response = converter.toCleanupResponse(
                fileAdminService.cleanExpiredFiles(
                        converter.toCleanExpiredCommand(days, dryRun)
                )
        );
        return ResultUtils.ok(response);
    }

    @Override
    public ResultModel<CleanupResponse> cleanTempFiles(Integer hours) {
        log.info("管理员清理临时文件: {}小时", hours);
        CleanupResponse response = converter.toCleanupResponse(
                fileAdminService.cleanTempFiles(
                        converter.toCleanTempCommand(hours)
                )
        );
        return ResultUtils.ok(response);
    }

    @Override
    public ResultModel<DuplicateCleanResponse> cleanDuplicateFiles(String storageType) {
        log.info("管理员清理重复文件: {}", storageType);
        DuplicateCleanResponse response = converter.toDuplicateCleanResponse(
                fileAdminService.cleanDuplicateFiles(
                        converter.toCleanDuplicateCommand(storageType)
                )
        );
        return ResultUtils.ok(response);
    }

    // ==================== 6. 文件统计 ====================

    @Override
    public ResultModel<AdminStatisticsResponse> getStatistics(String storageType) {
        AdminStatisticsResponse response = converter.toAdminStatisticsResponse(
                fileAdminService.getStatistics(
                        converter.toAdminStatisticsQuery(storageType)
                )
        );
        return ResultUtils.ok(response);
    }

    @Override
    public ResultModel<List<TrendDataResponse>> getTrendData(String period, String startDate,
                                                             String endDate) {
        List<TrendDataResponse> response = converter.toTrendDataResponseList(
                fileAdminService.getTrendData(
                        converter.toTrendDataQuery(period, startDate, endDate)
                )
        );
        return ResultUtils.ok(response);
    }

    @Override
    public ResultModel<List<StorageNodeStatsResponse>> getStorageNodeStats() {
        List<StorageNodeStatsResponse> response = converter.toStorageNodeStatsResponseList(
                fileAdminService.getStorageNodeStats(
                        converter.toStorageNodeStatsQuery()
                )
        );
        return ResultUtils.ok(response);
    }

    // ==================== 7. 存储节点管理 ====================

    @Override
    public ResultModel<List<StorageNodeInfoResponse>> getStorageNodes(Boolean enabledOnly) {
        List<StorageNodeInfoResponse> response = converter.toStorageNodeInfoResponseList(
                fileAdminService.getStorageNodes(
                        converter.toStorageNodeQuery(enabledOnly)
                )
        );
        return ResultUtils.ok(response);
    }

    @Override
    public ResultModel<StorageNodeInfoResponse> addStorageNode(@Valid StorageNodeInfoRequest request) {
        log.info("管理员添加存储节点: {}", request.getNodeName());
        StorageNodeInfoResponse response = converter.toStorageNodeInfoResponse(
                fileAdminService.addStorageNode(
                        converter.toAddStorageNodeCommand(request)
                )
        );
        return ResultUtils.ok(response);
    }

    @Override
    public ResultModel<StorageNodeInfoResponse> updateStorageNode(Long nodeId,
                                                                  @Valid StorageNodeInfoRequest request) {
        log.info("管理员更新存储节点: {}", nodeId);
        StorageNodeInfoResponse response = converter.toStorageNodeInfoResponse(
                fileAdminService.updateStorageNode(
                        converter.toUpdateStorageNodeCommand(nodeId, request)
                )
        );
        return ResultUtils.ok(response);
    }

    @Override
    public ResultModel<String> deleteStorageNode(Long nodeId) {
        log.info("管理员删除存储节点: {}", nodeId);
        fileAdminService.deleteStorageNode(
                converter.toDeleteStorageNodeCommand(nodeId)
        );
        return ResultUtils.ok();
    }

    @Override
    public ResultModel<ConnectionTestResponse> testStorageNode(Long nodeId) {
        ConnectionTestResponse response = converter.toConnectionTestResponse(
                fileAdminService.testStorageNode(
                        converter.toTestStorageNodeCommand(nodeId)
                )
        );
        return ResultUtils.ok(response);
    }

    @Override
    public ResultModel<StorageNodeInfoResponse> refreshStorageNode(Long nodeId) {
        StorageNodeInfoResponse response = converter.toStorageNodeInfoResponse(
                fileAdminService.refreshStorageNode(
                        converter.toRefreshStorageNodeCommand(nodeId)
                )
        );
        return ResultUtils.ok(response);
    }

    // ==================== 8. 文件审核与审计 ====================

    @Override
    public PageResultModel<FileAuditInfoResponse> getAuditList(Integer page, Integer size, Integer status) {
        PageResult<FileAuditInfoResult> result = fileAdminService.getAuditList(
                converter.toAuditListQuery(page, size, status)
        );
        return PageResultUtils.ok(result.getTotal(), converter.toFileAuditInfoResponseList(result.getRecords()));
    }

    @Override
    public ResultModel<String> auditFile(String fileId, Boolean approved, String comment) {
        log.info("管理员审核文件: {}, 结果: {}", fileId, approved ? "通过" : "拒绝");
        fileAdminService.auditFile(
                converter.toAuditFileCommand(fileId, approved, comment)
        );
        return ResultUtils.ok();
    }

    @Override
    public PageResultModel<FileAuditLogResponse> getAuditLogs(
            String fileId, Integer operationType, String startTime,
            String endTime, Integer page, Integer size
    ) {
        PageResult<FileAuditLogResult> result = fileAdminService.getAuditLogs(
                converter.toAuditLogQuery(fileId, operationType, startTime, endTime, page, size)
        );
        return PageResultUtils.ok(result.getTotal(), converter.toFileAuditLogResponseList(result.getRecords()));
    }

    // ==================== 9. 文件信息查询 ====================

    @Override
    public ResultModel<FileDetailInfoResponse> getFileDetail(String fileId) {
        FileDetailInfoResponse response = converter.toFileDetailInfoResponse(
                fileAdminService.getFileDetail(
                        converter.toFileDetailQuery(fileId)
                )
        );
        return ResultUtils.ok(response);
    }

    @Override
    public PageResultModel<FileInfoResponse> listFiles(@Valid FileListQueryRequest request) {
        PageResult<FileInfoResult> result = fileAdminService.listFiles(
                converter.toAdminFileListQuery(request)
        );
        return PageResultUtils.ok(result.getTotal(), converter.toFileInfoResponseList(result.getRecords()));
    }

    @Override
    public ResultModel<List<FileVersionInfoResponse>> getFileVersions(String fileId) {
        List<FileVersionInfoResponse> response = converter.toFileVersionInfoResponseList(
                fileAdminService.getFileVersions(
                        converter.toFileVersionQuery(fileId)
                )
        );
        return ResultUtils.ok(response);
    }
}