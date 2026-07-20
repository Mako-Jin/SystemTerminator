package com.yaocode.sts.components.file.runtime.service;

import com.yaocode.sts.common.basic.model.PageResult;
import com.yaocode.sts.components.file.runtime.model.command.AddStorageNodeCommand;
import com.yaocode.sts.components.file.runtime.model.command.AuditFileCommand;
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

import java.util.List;

/**
 * 文件管理后台服务接口
 * <p>
 * 使用 CQRS 模式，命令和查询分离
 * </p>
 *
 * @author yaocode
 * @since 1.0.0
 */
public interface FileAdminService {

    // ==================== 1. 文件删除（命令） ====================

    /**
     * 软删除文件
     *
     * @param command 删除命令
     */
    void deleteFile(DeleteFileCommand command);

    /**
     * 批量软删除文件
     *
     * @param command 批量删除命令
     * @return 批量删除结果
     */
    BatchDeleteResult batchDeleteFiles(BatchDeleteCommand command);

    /**
     * 永久删除文件
     *
     * @param command 永久删除命令
     */
    void permanentDeleteFile(PermanentDeleteCommand command);

    // ==================== 2. 文件恢复（命令） ====================

    /**
     * 恢复文件
     *
     * @param command 恢复命令
     */
    void restoreFile(RestoreFileCommand command);

    /**
     * 批量恢复文件
     *
     * @param command 批量恢复命令
     * @return 批量恢复结果
     */
    BatchRestoreResult batchRestoreFiles(BatchRestoreCommand command);

    // ==================== 3. 文件归档（命令） ====================

//    /**
//     * 归档文件
//     *
//     * @param command 归档命令
//     */
//    void archiveFile(ArchiveFileCommand command);
//
//    /**
//     * 取消归档
//     *
//     * @param command 取消归档命令
//     */
//    void unarchiveFile(UnarchiveFileCommand command);
//
//    /**
//     * 批量归档文件
//     *
//     * @param command 批量归档命令
//     * @return 批量归档结果
//     */
//    BatchArchiveResult batchArchiveFiles(BatchArchiveCommand command);

    // ==================== 4. 文件迁移（命令） ====================

    /**
     * 迁移文件
     *
     * @param command 迁移命令
     * @return 迁移任务ID
     */
    String migrateFile(MigrateFileCommand command);

    /**
     * 获取迁移任务状态（查询）
     */
    MigrateTaskStatusResult getMigrateTaskStatus(String taskId);

    /**
     * 取消迁移任务
     *
     * @param command 取消迁移命令
     */
    void cancelMigrateTask(CancelMigrateCommand command);

    // ==================== 5. 文件清理（命令） ====================

    /**
     * 清理过期文件
     *
     * @param command 清理命令
     * @return 清理结果
     */
    CleanupResult cleanExpiredFiles(CleanExpiredCommand command);

    /**
     * 清理临时文件
     *
     * @param command 清理命令
     * @return 清理结果
     */
    CleanupResult cleanTempFiles(CleanTempCommand command);

    /**
     * 清理重复文件
     *
     * @param command 清理命令
     * @return 清理结果
     */
    DuplicateCleanResult cleanDuplicateFiles(CleanDuplicateCommand command);

    // ==================== 6. 文件统计（查询） ====================

    /**
     * 获取系统统计信息
     *
     * @param query 统计查询
     * @return 统计信息
     */
    AdminStatisticsResult getStatistics(AdminStatisticsQuery query);

    /**
     * 获取趋势数据
     *
     * @param query 趋势查询
     * @return 趋势数据
     */
    List<TrendDataResult> getTrendData(TrendDataQuery query);

    /**
     * 获取存储节点统计
     *
     * @param query 存储节点统计查询
     * @return 存储节点统计
     */
    List<StorageNodeStatsResult> getStorageNodeStats(StorageNodeStatsQuery query);

    // ==================== 7. 存储节点管理（命令 + 查询） ====================

    /**
     * 获取存储节点列表（查询）
     *
     * @param query 存储节点查询
     * @return 存储节点列表
     */
    List<StorageNodeInfoResult> getStorageNodes(StorageNodeQuery query);

    /**
     * 添加存储节点（命令）
     *
     * @param command 添加命令
     * @return 创建结果
     */
    StorageNodeInfoResult addStorageNode(AddStorageNodeCommand command);

    /**
     * 更新存储节点（命令）
     *
     * @param command 更新命令
     * @return 更新结果
     */
    StorageNodeInfoResult updateStorageNode(UpdateStorageNodeCommand command);

    /**
     * 删除存储节点（命令）
     *
     * @param command 删除命令
     */
    void deleteStorageNode(DeleteStorageNodeCommand command);

    /**
     * 测试存储节点连接（命令）
     *
     * @param command 测试命令
     * @return 测试结果
     */
    ConnectionTestResult testStorageNode(TestStorageNodeCommand command);

    /**
     * 刷新存储节点状态（命令）
     *
     * @param command 刷新命令
     * @return 最新状态
     */
    StorageNodeInfoResult refreshStorageNode(RefreshStorageNodeCommand command);

    // ==================== 8. 文件审核与审计（命令 + 查询） ====================

    /**
     * 获取审核列表（查询）
     *
     * @param query 审核列表查询
     * @return 审核列表
     */
    PageResult<FileAuditInfoResult> getAuditList(AuditListQuery query);

    /**
     * 审核文件（命令）
     *
     * @param command 审核命令
     */
    void auditFile(AuditFileCommand command);

    /**
     * 获取审计日志（查询）
     *
     * @param query 审计日志查询
     * @return 审计日志
     */
    PageResult<FileAuditLogResult> getAuditLogs(AuditLogQuery query);

    // ==================== 9. 文件信息查询（查询） ====================

    /**
     * 获取文件详细信息
     *
     * @param query 文件详情查询
     * @return 文件详细信息
     */
    FileDetailInfoResult getFileDetail(FileDetailQuery query);

    /**
     * 查询文件列表
     *
     * @param query 文件列表查询
     * @return 文件列表
     */
    PageResult<FileInfoResult> listFiles(AdminFileListQuery query);

    /**
     * 获取文件版本列表
     *
     * @param query 文件版本查询
     * @return 版本列表
     */
    List<FileVersionInfoResult> getFileVersions(FileVersionQuery query);
}