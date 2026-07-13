package com.yaocode.sts.components.file.interfaces.api;

import com.yaocode.sts.common.web.model.PageResultModel;
import com.yaocode.sts.common.web.model.ResultModel;
import com.yaocode.sts.components.file.interfaces.model.request.BatchArchiveRequest;
import com.yaocode.sts.components.file.interfaces.model.request.FileListQueryRequest;
import com.yaocode.sts.components.file.interfaces.model.request.MigrateOptionsRequest;
import com.yaocode.sts.components.file.interfaces.model.request.StorageNodeInfoRequest;
import com.yaocode.sts.components.file.interfaces.model.response.AdminStatisticsResponse;
import com.yaocode.sts.components.file.interfaces.model.response.BatchArchiveResponse;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 文件管理后台API
 * <p>
 * 提供文件管理相关功能，主要用于管理员操作：
 * <ul>
 *   <li>文件删除 - 删除指定文件（支持批量）</li>
 *   <li>文件恢复 - 恢复已删除的文件</li>
 *   <li>文件归档 - 将文件归档到冷存储</li>
 *   <li>文件迁移 - 在不同存储间迁移文件</li>
 *   <li>文件清理 - 清理过期/临时文件</li>
 *   <li>文件统计 - 获取系统文件统计信息</li>
 *   <li>存储节点管理 - 管理存储节点</li>
 * </ul>
 * </p>
 *
 * @author yaocode
 * @since 1.0.0
 */
@RequestMapping("/files/admin")
public interface FileAdminApi {

    // ==================== 1. 文件删除 ====================

    /**
     * 删除文件（软删除）
     *
     * @param fileId 文件ID
     * @return 操作结果
     */
    @DeleteMapping("/{fileId}")
    ResultModel<String> deleteFile(@PathVariable String fileId);

    /**
     * 批量删除文件
     *
     * @param fileIds 文件ID列表（最多100个）
     * @return 操作结果
     */
    @DeleteMapping("/batch")
    ResultModel<BatchDeleteResponse> batchDeleteFiles(@RequestBody List<String> fileIds);

    /**
     * 永久删除文件（物理删除，不可恢复）
     *
     * @param fileId 文件ID
     * @return 操作结果
     */
    @DeleteMapping("/{fileId}/permanent")
    ResultModel<String> permanentDeleteFile(@PathVariable String fileId);

    // ==================== 2. 文件恢复 ====================

    /**
     * 恢复已删除的文件
     *
     * @param fileId 文件ID
     * @return 操作结果
     */
    @PostMapping("/{fileId}/restore")
    ResultModel<String> restoreFile(@PathVariable String fileId);

    /**
     * 批量恢复已删除的文件
     *
     * @param fileIds 文件ID列表
     * @return 操作结果
     */
    @PostMapping("/batch/restore")
    ResultModel<BatchRestoreResponse> batchRestoreFiles(@RequestBody List<String> fileIds);

    // ==================== 3. 文件归档 ====================

    /**
     * 归档文件（迁移到冷存储）
     *
     * @param fileId 文件ID
     * @param archiveType 归档类型（如：GLACIER, DEEP_ARCHIVE）
     * @return 操作结果
     */
    @PostMapping("/{fileId}/archive")
    ResultModel<String> archiveFile(
        @PathVariable String fileId,
        @RequestParam(defaultValue = "GLACIER") String archiveType
    );

    /**
     * 取消归档（从冷存储恢复到热存储）
     *
     * @param fileId 文件ID
     * @return 操作结果
     */
    @PostMapping("/{fileId}/unarchive")
    ResultModel<String> unarchiveFile(@PathVariable String fileId);

    /**
     * 批量归档文件
     *
     * @param request 批量归档请求
     * @return 操作结果
     */
    @PostMapping("/batch/archive")
    ResultModel<BatchArchiveResponse> batchArchiveFiles(@RequestBody BatchArchiveRequest request);

    // ==================== 4. 文件迁移 ====================

    /**
     * 迁移文件到另一个存储节点
     *
     * @param fileId 文件ID
     * @param targetStorageType 目标存储类型
     * @param options 迁移选项（如：同步/异步、是否保留源文件）
     * @return 迁移任务ID
     */
    @PostMapping("/{fileId}/migrate")
    ResultModel<String> migrateFile(
            @PathVariable String fileId,
            @RequestParam String targetStorageType,
            @RequestBody(required = false) MigrateOptionsRequest options
    );

    /**
     * 获取迁移任务状态
     *
     * @param taskId 迁移任务ID
     * @return 迁移任务状态
     */
    @GetMapping("/migrate/task/{taskId}")
    ResultModel<MigrateTaskStatusResponse> getMigrateTaskStatus(@PathVariable String taskId);

    /**
     * 取消迁移任务
     *
     * @param taskId 迁移任务ID
     * @return 操作结果
     */
    @DeleteMapping("/migrate/task/{taskId}")
    ResultModel<String> cancelMigrateTask(@PathVariable String taskId);

    // ==================== 5. 文件清理 ====================

    /**
     * 清理过期文件（根据过期时间自动删除）
     *
     * @param days 保留天数（超过此天数的文件将被清理）
     * @param dryRun 是否为试运行（true: 只统计，不实际删除）
     * @return 清理结果
     */
    @PostMapping("/cleanup/expired")
    ResultModel<CleanupResponse> cleanExpiredFiles(
            @RequestParam Integer days,
            @RequestParam(defaultValue = "true") Boolean dryRun
    );

    /**
     * 清理临时文件（未完成上传的文件）
     *
     * @param hours 保留小时数（超过此时间的临时文件将被清理）
     * @return 清理结果
     */
    @PostMapping("/cleanup/temp")
    ResultModel<CleanupResponse> cleanTempFiles(
            @RequestParam(defaultValue = "24") Integer hours
    );

    /**
     * 清理重复文件（基于MD5去重）
     *
     * @param storageType 存储类型（可选）
     * @return 清理结果
     */
    @PostMapping("/cleanup/duplicates")
    ResultModel<DuplicateCleanResponse> cleanDuplicateFiles(
            @RequestParam(required = false) String storageType
    );

    // ==================== 6. 文件统计 ====================

    /**
     * 获取系统文件统计信息
     *
     * @param storageType 存储类型（可选）
     * @return 统计信息
     */
    @GetMapping("/statistics")
    ResultModel<AdminStatisticsResponse> getStatistics(
            @RequestParam(required = false) String storageType
    );

    /**
     * 获取文件趋势数据（按天/周/月）
     *
     * @param period 统计周期（day/week/month）
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 趋势数据
     */
    @GetMapping("/statistics/trend")
    ResultModel<List<TrendDataResponse>> getTrendData(
            @RequestParam(defaultValue = "day") String period,
            @RequestParam String startDate,
            @RequestParam String endDate
    );

    /**
     * 获取存储节点容量统计
     *
     * @return 各存储节点容量信息
     */
    @GetMapping("/statistics/storage-nodes")
    ResultModel<List<StorageNodeStatsResponse>> getStorageNodeStats();

    // ==================== 7. 存储节点管理 ====================

    /**
     * 获取所有存储节点
     *
     * @param enabledOnly 是否只获取启用的节点
     * @return 存储节点列表
     */
    @GetMapping("/storage-nodes")
    ResultModel<List<StorageNodeInfoResponse>> getStorageNodes(
            @RequestParam(defaultValue = "true") Boolean enabledOnly
    );

    /**
     * 添加存储节点
     *
     * @param request 存储节点配置
     * @return 创建结果
     */
    @PostMapping("/storage-nodes")
    ResultModel<StorageNodeInfoResponse> addStorageNode(@RequestBody StorageNodeInfoRequest request);

    /**
     * 更新存储节点配置
     *
     * @param nodeId 节点ID
     * @param request 更新配置
     * @return 操作结果
     */
    @PutMapping("/storage-nodes/{nodeId}")
    ResultModel<StorageNodeInfoResponse> updateStorageNode(
            @PathVariable Long nodeId,
            @RequestBody StorageNodeInfoRequest request
    );

    /**
     * 删除存储节点
     *
     * @param nodeId 节点ID
     * @return 操作结果
     */
    @DeleteMapping("/storage-nodes/{nodeId}")
    ResultModel<String> deleteStorageNode(@PathVariable Long nodeId);

    /**
     * 测试存储节点连接
     *
     * @param nodeId 节点ID
     * @return 连接测试结果
     */
    @PostMapping("/storage-nodes/{nodeId}/test")
    ResultModel<ConnectionTestResponse> testStorageNode(@PathVariable Long nodeId);

    /**
     * 刷新存储节点状态
     *
     * @param nodeId 节点ID
     * @return 最新状态
     */
    @PostMapping("/storage-nodes/{nodeId}/refresh")
    ResultModel<StorageNodeInfoResponse> refreshStorageNode(@PathVariable Long nodeId);

    // ==================== 8. 文件审核与审计 ====================

    /**
     * 获取文件审核列表（待审核的文件）
     *
     * @param page 页码
     * @param size 每页数量
     * @param status 审核状态（可选）
     * @return 审核列表
     */
    @GetMapping("/audit/list")
    PageResultModel<FileAuditInfoResponse> getAuditList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer size,
            @RequestParam(required = false) Integer status
    );

    /**
     * 审核文件（通过/拒绝）
     *
     * @param fileId 文件ID
     * @param approved 是否通过审核
     * @param comment 审核意见
     * @return 操作结果
     */
    @PostMapping("/audit/{fileId}")
    ResultModel<String> auditFile(
            @PathVariable String fileId,
            @RequestParam Boolean approved,
            @RequestParam(required = false) String comment
    );

    /**
     * 获取文件操作审计日志
     *
     * @param fileId 文件ID（可选）
     * @param operationType 操作类型（可选）
     * @param startTime 开始时间（可选）
     * @param endTime 结束时间（可选）
     * @param page 页码
     * @param size 每页数量
     * @return 审计日志
     */
    @GetMapping("/audit/logs")
    PageResultModel<FileAuditLogResponse> getAuditLogs(
            @RequestParam(required = false) String fileId,
            @RequestParam(required = false) Integer operationType,
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer size
    );

    // ==================== 9. 文件信息查询 ====================

    /**
     * 获取文件详细信息（包含审计信息）
     *
     * @param fileId 文件ID
     * @return 文件详细信息
     */
    @GetMapping("/{fileId}/detail")
    ResultModel<FileDetailInfoResponse> getFileDetail(@PathVariable String fileId);

    /**
     * 查询文件列表（支持高级筛选）
     *
     * @param request 查询请求
     * @return 文件列表
     */
    @PostMapping("/list")
    PageResultModel<FileInfoResponse> listFiles(@RequestBody FileListQueryRequest request);

    /**
     * 获取文件版本列表
     *
     * @param fileId 文件ID
     * @return 版本列表
     */
    @GetMapping("/{fileId}/versions")
    ResultModel<List<FileVersionInfoResponse>> getFileVersions(@PathVariable String fileId);
}
