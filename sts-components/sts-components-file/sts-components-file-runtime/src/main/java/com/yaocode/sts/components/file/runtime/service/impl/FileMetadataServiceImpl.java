package com.yaocode.sts.components.file.runtime.service.impl;

import com.yaocode.sts.common.basic.model.PageResult;
import com.yaocode.sts.components.file.runtime.model.command.AddTagsCommand;
import com.yaocode.sts.components.file.runtime.model.command.BatchUpdateCommand;
import com.yaocode.sts.components.file.runtime.model.command.RemoveTagCommand;
import com.yaocode.sts.components.file.runtime.model.command.SetFilePublicCommand;
import com.yaocode.sts.components.file.runtime.model.command.UpdateDescriptionCommand;
import com.yaocode.sts.components.file.runtime.model.command.UpdateFileNameCommand;
import com.yaocode.sts.components.file.runtime.model.command.UpdateMetadataCommand;
import com.yaocode.sts.components.file.runtime.model.command.UpdatePermissionCommand;
import com.yaocode.sts.components.file.runtime.model.command.UpdateTagsCommand;
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
import com.yaocode.sts.components.file.runtime.model.result.BatchUpdateResult;
import com.yaocode.sts.components.file.runtime.model.result.FileCompareResult;
import com.yaocode.sts.components.file.runtime.model.result.FileDetailInfoResult;
import com.yaocode.sts.components.file.runtime.model.result.FileExistenceResult;
import com.yaocode.sts.components.file.runtime.model.result.FileInfoResult;
import com.yaocode.sts.components.file.runtime.model.result.FileTypeResult;
import com.yaocode.sts.components.file.runtime.model.result.FileTypeStatisticsResult;
import com.yaocode.sts.components.file.runtime.model.result.IntegrityCheckResult;
import com.yaocode.sts.components.file.runtime.model.result.PermissionInfoResult;
import com.yaocode.sts.components.file.runtime.model.result.SizeDistributionResult;
import com.yaocode.sts.components.file.runtime.model.result.StorageInfoResult;
import com.yaocode.sts.components.file.runtime.model.result.StorageStatsResult;
import com.yaocode.sts.components.file.runtime.model.result.TrendDataResult;
import com.yaocode.sts.components.file.runtime.service.FileMetadataService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class FileMetadataServiceImpl implements FileMetadataService {
    @Override
    public FileInfoResult getFileInfo(FileInfoQuery query) {
        return null;
    }

    @Override
    public FileDetailInfoResult getFileDetail(FileDetailQuery query) {
        return null;
    }

    @Override
    public List<FileInfoResult> getFileInfoBatch(FileInfoBatchQuery query) {
        return List.of();
    }

    @Override
    public Map<String, String> getFileMetadata(FileMetadataQuery query) {
        return Map.of();
    }

    @Override
    public StorageInfoResult getStorageInfo(StorageInfoQuery query) {
        return null;
    }

    @Override
    public AccessInfoResult getAccessInfo(AccessInfoQuery query) {
        return null;
    }

    @Override
    public PageResult<FileInfoResult> searchFiles(FileSearchQuery query) {
        return null;
    }

    @Override
    public PageResult<FileInfoResult> simpleSearch(SimpleSearchQuery query) {
        return null;
    }

    @Override
    public PageResult<FileInfoResult> listFiles(FileListQuery query) {
        return null;
    }

    @Override
    public PageResult<FileInfoResult> getMyFiles(MyFileListQuery query) {
        return null;
    }

    @Override
    public List<FileInfoResult> getRecentFiles(RecentFileQuery query) {
        return List.of();
    }

    @Override
    public FileInfoResult updateFileName(UpdateFileNameCommand command) {
        return null;
    }

    @Override
    public FileInfoResult updateFileTags(UpdateTagsCommand command) {
        return null;
    }

    @Override
    public FileInfoResult updateDescription(UpdateDescriptionCommand command) {
        return null;
    }

    @Override
    public Map<String, String> updateMetadata(UpdateMetadataCommand command) {
        return Map.of();
    }

    @Override
    public List<String> addTags(AddTagsCommand command) {
        return List.of();
    }

    @Override
    public List<String> removeTag(RemoveTagCommand command) {
        return List.of();
    }

    @Override
    public BatchUpdateResult batchUpdateMetadata(BatchUpdateCommand command) {
        return null;
    }

    @Override
    public List<FileTypeResult> getFileTypes() {
        return List.of();
    }

    @Override
    public List<FileTypeStatisticsResult> getFileTypeStatistics(FileTypeStatisticsQuery query) {
        return List.of();
    }

    @Override
    public List<StorageStatsResult> getStorageStatistics() {
        return List.of();
    }

    @Override
    public SizeDistributionResult getSizeDistribution() {
        return null;
    }

    @Override
    public List<TrendDataResult> getUploadTrend(TrendDataQuery query) {
        return List.of();
    }

    @Override
    public FileExistenceResult verifyFileExists(FileExistenceQuery query) {
        return null;
    }

    @Override
    public IntegrityCheckResult verifyIntegrity(IntegrityCheckQuery query) {
        return null;
    }

    @Override
    public FileCompareResult compareFiles(FileCompareQuery query) {
        return null;
    }

    @Override
    public PermissionInfoResult getFilePermission(PermissionInfoQuery query) {
        return null;
    }

    @Override
    public PermissionInfoResult updateFilePermission(UpdatePermissionCommand command) {
        return null;
    }

    @Override
    public void setFilePublic(SetFilePublicCommand command) {

    }
}
