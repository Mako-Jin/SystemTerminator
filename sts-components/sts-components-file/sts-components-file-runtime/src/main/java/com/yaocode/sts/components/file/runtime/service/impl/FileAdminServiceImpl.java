package com.yaocode.sts.components.file.runtime.service.impl;

import com.yaocode.sts.common.basic.model.PageResult;
import com.yaocode.sts.components.file.runtime.model.command.AddStorageNodeCommand;
import com.yaocode.sts.components.file.runtime.model.command.ArchiveFileCommand;
import com.yaocode.sts.components.file.runtime.model.command.AuditFileCommand;
import com.yaocode.sts.components.file.runtime.model.command.BatchArchiveCommand;
import com.yaocode.sts.components.file.runtime.model.command.BatchDeleteCommand;
import com.yaocode.sts.components.file.runtime.model.command.BatchRestoreCommand;
import com.yaocode.sts.components.file.runtime.model.command.CancelMigrateCommand;
import com.yaocode.sts.components.file.runtime.model.command.CleanDuplicateCommand;
import com.yaocode.sts.components.file.runtime.model.command.CleanExpiredCommand;
import com.yaocode.sts.components.file.runtime.model.command.CleanTempCommand;
import com.yaocode.sts.components.file.runtime.model.command.DeleteFileCommand;
import com.yaocode.sts.components.file.runtime.model.command.DeleteStorageNodeCommand;
import com.yaocode.sts.components.file.runtime.model.command.MigrateFileCommand;
import com.yaocode.sts.components.file.runtime.model.command.PermanentDeleteCommand;
import com.yaocode.sts.components.file.runtime.model.command.RefreshStorageNodeCommand;
import com.yaocode.sts.components.file.runtime.model.command.RestoreFileCommand;
import com.yaocode.sts.components.file.runtime.model.command.TestStorageNodeCommand;
import com.yaocode.sts.components.file.runtime.model.command.UnarchiveFileCommand;
import com.yaocode.sts.components.file.runtime.model.command.UpdateStorageNodeCommand;
import com.yaocode.sts.components.file.runtime.model.query.AdminFileListQuery;
import com.yaocode.sts.components.file.runtime.model.query.AdminStatisticsQuery;
import com.yaocode.sts.components.file.runtime.model.query.AuditListQuery;
import com.yaocode.sts.components.file.runtime.model.query.AuditLogQuery;
import com.yaocode.sts.components.file.runtime.model.query.FileDetailQuery;
import com.yaocode.sts.components.file.runtime.model.query.FileVersionQuery;
import com.yaocode.sts.components.file.runtime.model.query.StorageNodeQuery;
import com.yaocode.sts.components.file.runtime.model.query.StorageNodeStatsQuery;
import com.yaocode.sts.components.file.runtime.model.query.TrendDataQuery;
import com.yaocode.sts.components.file.runtime.model.result.AdminStatisticsResult;
import com.yaocode.sts.components.file.runtime.model.result.BatchArchiveResult;
import com.yaocode.sts.components.file.runtime.model.result.BatchDeleteResult;
import com.yaocode.sts.components.file.runtime.model.result.BatchRestoreResult;
import com.yaocode.sts.components.file.runtime.model.result.CleanupResult;
import com.yaocode.sts.components.file.runtime.model.result.ConnectionTestResult;
import com.yaocode.sts.components.file.runtime.model.result.DuplicateCleanResult;
import com.yaocode.sts.components.file.runtime.model.result.FileAuditInfoResult;
import com.yaocode.sts.components.file.runtime.model.result.FileAuditLogResult;
import com.yaocode.sts.components.file.runtime.model.result.FileDetailInfoResult;
import com.yaocode.sts.components.file.runtime.model.result.FileInfoResult;
import com.yaocode.sts.components.file.runtime.model.result.FileVersionInfoResult;
import com.yaocode.sts.components.file.runtime.model.result.MigrateTaskStatusResult;
import com.yaocode.sts.components.file.runtime.model.result.StorageNodeInfoResult;
import com.yaocode.sts.components.file.runtime.model.result.StorageNodeStatsResult;
import com.yaocode.sts.components.file.runtime.model.result.TrendDataResult;
import com.yaocode.sts.components.file.runtime.service.FileAdminService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileAdminServiceImpl implements FileAdminService {
    @Override
    public String deleteFile(DeleteFileCommand command) {
        return "";
    }

    @Override
    public BatchDeleteResult batchDeleteFiles(BatchDeleteCommand command) {
        return null;
    }

    @Override
    public void permanentDeleteFile(PermanentDeleteCommand command) {

    }

    @Override
    public String restoreFile(RestoreFileCommand command) {
        return "";
    }

    @Override
    public BatchRestoreResult batchRestoreFiles(BatchRestoreCommand command) {
        return null;
    }

    @Override
    public void archiveFile(ArchiveFileCommand command) {

    }

    @Override
    public void unarchiveFile(UnarchiveFileCommand command) {

    }

    @Override
    public BatchArchiveResult batchArchiveFiles(BatchArchiveCommand command) {
        return null;
    }

    @Override
    public String migrateFile(MigrateFileCommand command) {
        return "";
    }

    @Override
    public MigrateTaskStatusResult getMigrateTaskStatus(String taskId) {
        return null;
    }

    @Override
    public void cancelMigrateTask(CancelMigrateCommand command) {

    }

    @Override
    public CleanupResult cleanExpiredFiles(CleanExpiredCommand command) {
        return null;
    }

    @Override
    public CleanupResult cleanTempFiles(CleanTempCommand command) {
        return null;
    }

    @Override
    public DuplicateCleanResult cleanDuplicateFiles(CleanDuplicateCommand command) {
        return null;
    }

    @Override
    public AdminStatisticsResult getStatistics(AdminStatisticsQuery query) {
        return null;
    }

    @Override
    public List<TrendDataResult> getTrendData(TrendDataQuery query) {
        return List.of();
    }

    @Override
    public List<StorageNodeStatsResult> getStorageNodeStats(StorageNodeStatsQuery query) {
        return List.of();
    }

    @Override
    public List<StorageNodeInfoResult> getStorageNodes(StorageNodeQuery query) {
        return List.of();
    }

    @Override
    public StorageNodeInfoResult addStorageNode(AddStorageNodeCommand command) {
        return null;
    }

    @Override
    public StorageNodeInfoResult updateStorageNode(UpdateStorageNodeCommand command) {
        return null;
    }

    @Override
    public void deleteStorageNode(DeleteStorageNodeCommand command) {

    }

    @Override
    public ConnectionTestResult testStorageNode(TestStorageNodeCommand command) {
        return null;
    }

    @Override
    public StorageNodeInfoResult refreshStorageNode(RefreshStorageNodeCommand command) {
        return null;
    }

    @Override
    public PageResult<FileAuditInfoResult> getAuditList(AuditListQuery query) {
        return null;
    }

    @Override
    public void auditFile(AuditFileCommand command) {

    }

    @Override
    public PageResult<FileAuditLogResult> getAuditLogs(AuditLogQuery query) {
        return null;
    }

    @Override
    public FileDetailInfoResult getFileDetail(FileDetailQuery query) {
        return null;
    }

    @Override
    public PageResult<FileInfoResult> listFiles(AdminFileListQuery query) {
        return null;
    }

    @Override
    public List<FileVersionInfoResult> getFileVersions(FileVersionQuery query) {
        return List.of();
    }
}
