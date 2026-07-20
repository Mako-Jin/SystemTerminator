package com.yaocode.sts.file.runtime.service;

import com.yaocode.sts.common.basic.model.PageResult;
import com.yaocode.sts.file.runtime.model.command.AddTagsCommand;
import com.yaocode.sts.file.runtime.model.command.BatchUpdateCommand;
import com.yaocode.sts.file.runtime.model.command.RemoveTagCommand;
import com.yaocode.sts.file.runtime.model.command.SetFilePublicCommand;
import com.yaocode.sts.file.runtime.model.command.UpdateDescriptionCommand;
import com.yaocode.sts.file.runtime.model.command.UpdateFileNameCommand;
import com.yaocode.sts.file.runtime.model.command.UpdateMetadataCommand;
import com.yaocode.sts.file.runtime.model.command.UpdatePermissionCommand;
import com.yaocode.sts.file.runtime.model.command.UpdateTagsCommand;
import com.yaocode.sts.file.runtime.model.query.AccessInfoQuery;
import com.yaocode.sts.file.runtime.model.query.FileCompareQuery;
import com.yaocode.sts.file.runtime.model.query.FileDetailQuery;
import com.yaocode.sts.file.runtime.model.query.FileExistenceQuery;
import com.yaocode.sts.file.runtime.model.query.FileInfoBatchQuery;
import com.yaocode.sts.file.runtime.model.query.FileInfoQuery;
import com.yaocode.sts.file.runtime.model.query.FileListQuery;
import com.yaocode.sts.file.runtime.model.query.FileMetadataQuery;
import com.yaocode.sts.file.runtime.model.query.FileSearchQuery;
import com.yaocode.sts.file.runtime.model.query.FileTypeStatisticsQuery;
import com.yaocode.sts.file.runtime.model.query.IntegrityCheckQuery;
import com.yaocode.sts.file.runtime.model.query.MyFileListQuery;
import com.yaocode.sts.file.runtime.model.query.PermissionInfoQuery;
import com.yaocode.sts.file.runtime.model.query.RecentFileQuery;
import com.yaocode.sts.file.runtime.model.query.SimpleSearchQuery;
import com.yaocode.sts.file.runtime.model.query.StorageInfoQuery;
import com.yaocode.sts.file.runtime.model.query.TrendDataQuery;
import com.yaocode.sts.file.runtime.model.result.AccessInfoResult;
import com.yaocode.sts.file.runtime.model.result.BatchUpdateResult;
import com.yaocode.sts.file.runtime.model.result.FileCompareResult;
import com.yaocode.sts.file.runtime.model.result.FileDetailInfoResult;
import com.yaocode.sts.file.runtime.model.result.FileExistenceResult;
import com.yaocode.sts.file.runtime.model.result.FileInfoResult;
import com.yaocode.sts.file.runtime.model.result.FileTypeResult;
import com.yaocode.sts.file.runtime.model.result.FileTypeStatisticsResult;
import com.yaocode.sts.file.runtime.model.result.IntegrityCheckResult;
import com.yaocode.sts.file.runtime.model.result.PermissionInfoResult;
import com.yaocode.sts.file.runtime.model.result.SizeDistributionResult;
import com.yaocode.sts.file.runtime.model.result.StorageInfoResult;
import com.yaocode.sts.file.runtime.model.result.StorageStatsResult;
import com.yaocode.sts.file.runtime.model.result.TrendDataResult;

import java.util.List;
import java.util.Map;

/**
 * 文件元数据服务接口
 * <p>
 * 使用 CQRS 模式，命令和查询分离
 * </p>
 *
 * @author yaocode
 * @since 1.0.0
 */
public interface FileMetadataService {

    // ==================== 1. 文件信息查询（查询） ====================

    /**
     * 获取文件基本信息
     */
    FileInfoResult getFileInfo(FileInfoQuery query);

    /**
     * 获取文件详细信息
     */
    FileDetailInfoResult getFileDetail(FileDetailQuery query);

    /**
     * 批量获取文件信息
     */
    List<FileInfoResult> getFileInfoBatch(FileInfoBatchQuery query);

    /**
     * 获取文件元数据
     */
    Map<String, String> getFileMetadata(FileMetadataQuery query);

    /**
     * 获取文件存储信息
     */
    StorageInfoResult getStorageInfo(StorageInfoQuery query);

    /**
     * 获取文件访问信息
     */
    AccessInfoResult getAccessInfo(AccessInfoQuery query);

    // ==================== 2. 文件搜索（查询） ====================

    /**
     * 搜索文件
     */
    PageResult<FileInfoResult> searchFiles(FileSearchQuery query);

    /**
     * 简单搜索
     */
    PageResult<FileInfoResult> simpleSearch(SimpleSearchQuery query);

    /**
     * 获取文件列表
     */
    PageResult<FileInfoResult> listFiles(FileListQuery query);

    /**
     * 获取当前用户文件列表
     */
    PageResult<FileInfoResult> getMyFiles(MyFileListQuery query);

    /**
     * 获取最近上传的文件
     */
    List<FileInfoResult> getRecentFiles(RecentFileQuery query);

    // ==================== 3. 文件元数据更新（命令） ====================

    /**
     * 更新文件名
     */
    FileInfoResult updateFileName(UpdateFileNameCommand command);

    /**
     * 更新文件标签
     */
    FileInfoResult updateFileTags(UpdateTagsCommand command);

    /**
     * 更新文件描述
     */
    FileInfoResult updateDescription(UpdateDescriptionCommand command);

    /**
     * 更新文件元数据
     */
    Map<String, String> updateMetadata(UpdateMetadataCommand command);

    /**
     * 添加文件标签
     */
    List<String> addTags(AddTagsCommand command);

    /**
     * 删除文件标签
     */
    List<String> removeTag(RemoveTagCommand command);

    /**
     * 批量更新文件元数据
     */
    BatchUpdateResult batchUpdateMetadata(BatchUpdateCommand command);

    // ==================== 4. 文件类型和分类（查询） ====================

    /**
     * 获取文件类型列表
     */
    List<FileTypeResult> getFileTypes();

    /**
     * 获取文件类型统计
     */
    List<FileTypeStatisticsResult> getFileTypeStatistics(FileTypeStatisticsQuery query);

    /**
     * 获取存储统计
     */
    List<StorageStatsResult> getStorageStatistics();

    /**
     * 获取大小分布
     */
    SizeDistributionResult getSizeDistribution();

    /**
     * 获取上传趋势
     */
    List<TrendDataResult> getUploadTrend(TrendDataQuery query);

    // ==================== 5. 文件验证（查询） ====================

    /**
     * 验证文件是否存在
     */
    FileExistenceResult verifyFileExists(FileExistenceQuery query);

    /**
     * 验证文件完整性
     */
    IntegrityCheckResult verifyIntegrity(IntegrityCheckQuery query);

    // ==================== 6. 文件对比（查询） ====================

    /**
     * 对比两个文件
     */
    FileCompareResult compareFiles(FileCompareQuery query);

    // ==================== 7. 文件访问控制（查询 + 命令） ====================

    /**
     * 获取文件权限信息（查询）
     */
    PermissionInfoResult getFilePermission(PermissionInfoQuery query);

    /**
     * 更新文件权限（命令）
     */
    PermissionInfoResult updateFilePermission(UpdatePermissionCommand command);

    /**
     * 设置文件公开/私有（命令）
     */
    void setFilePublic(SetFilePublicCommand command);
}
