package com.yaocode.sts.file.web.controller;

import com.yaocode.sts.common.basic.model.PageResult;
import com.yaocode.sts.common.web.model.PageResultModel;
import com.yaocode.sts.common.web.model.ResultModel;
import com.yaocode.sts.common.web.utils.PageResultUtils;
import com.yaocode.sts.common.web.utils.ResultUtils;
import com.yaocode.sts.file.interfaces.api.FileMetadataApi;
import com.yaocode.sts.file.interfaces.model.request.BatchUpdateRequest;
import com.yaocode.sts.file.interfaces.model.request.FileSearchRequest;
import com.yaocode.sts.file.interfaces.model.request.UpdateDescriptionRequest;
import com.yaocode.sts.file.interfaces.model.request.UpdateFileNameRequest;
import com.yaocode.sts.file.interfaces.model.request.UpdatePermissionRequest;
import com.yaocode.sts.file.interfaces.model.request.UpdateTagsRequest;
import com.yaocode.sts.file.interfaces.model.response.AccessInfoResponse;
import com.yaocode.sts.file.interfaces.model.response.BatchUpdateResponse;
import com.yaocode.sts.file.interfaces.model.response.FileCompareResponse;
import com.yaocode.sts.file.interfaces.model.response.FileDetailInfoResponse;
import com.yaocode.sts.file.interfaces.model.response.FileExistenceResponse;
import com.yaocode.sts.file.interfaces.model.response.FileInfoResponse;
import com.yaocode.sts.file.interfaces.model.response.FileTypeResponse;
import com.yaocode.sts.file.interfaces.model.response.FileTypeStatisticsResponse;
import com.yaocode.sts.file.interfaces.model.response.IntegrityCheckResponse;
import com.yaocode.sts.file.interfaces.model.response.PermissionInfoResponse;
import com.yaocode.sts.file.interfaces.model.response.SizeDistributionResponse;
import com.yaocode.sts.file.interfaces.model.response.StorageInfoResponse;
import com.yaocode.sts.file.interfaces.model.response.StorageStatsResponse;
import com.yaocode.sts.file.interfaces.model.response.TrendDataResponse;
import com.yaocode.sts.file.runtime.model.result.FileInfoResult;
import com.yaocode.sts.file.runtime.service.FileMetadataService;
import com.yaocode.sts.file.web.converter.FileMetadataConverter;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 文件元数据控制器
 *
 * @author yaocode
 * @since 1.0.0
 */
@Slf4j
@RestController
public class FileMetadataController implements FileMetadataApi {

    @Resource
    private FileMetadataService fileMetadataService;

    @Resource
    private FileMetadataConverter converter;

    // ==================== 1. 文件信息查询 ====================

    @Override
    public ResultModel<FileInfoResponse> getFileInfo(String fileId) {
        log.info("获取文件信息: {}", fileId);
        FileInfoResponse response = converter.toFileInfoResponse(
                fileMetadataService.getFileInfo(
                        converter.toFileInfoQuery(fileId)
                )
        );
        return ResultUtils.ok(response);
    }

    @Override
    public ResultModel<FileDetailInfoResponse> getFileDetail(String fileId) {
        log.info("获取文件详情: {}", fileId);
        FileDetailInfoResponse response = converter.toFileDetailInfoResponse(
                fileMetadataService.getFileDetail(
                        converter.toFileDetailQuery(fileId)
                )
        );
        return ResultUtils.ok(response);
    }

    @Override
    public ResultModel<List<FileInfoResponse>> getFileInfoBatch(List<String> fileIds) {
        log.info("批量获取文件信息: {} 个文件", fileIds.size());
        List<FileInfoResponse> response = converter.toFileInfoResponseList(
                fileMetadataService.getFileInfoBatch(
                        converter.toFileInfoBatchQuery(fileIds)
                )
        );
        return ResultUtils.ok(response);
    }

    @Override
    public ResultModel<Map<String, String>> getFileMetadata(String fileId) {
        log.info("获取文件元数据: {}", fileId);
        Map<String, String> response = fileMetadataService.getFileMetadata(
                converter.toFileMetadataQuery(fileId)
        );
        return ResultUtils.ok(response);
    }

    @Override
    public ResultModel<StorageInfoResponse> getStorageInfo(String fileId) {
        log.info("获取文件存储信息: {}", fileId);
        StorageInfoResponse response = converter.toStorageInfoResponse(
                fileMetadataService.getStorageInfo(
                        converter.toStorageInfoQuery(fileId)
                )
        );
        return ResultUtils.ok(response);
    }

    @Override
    public ResultModel<AccessInfoResponse> getAccessInfo(String fileId) {
        log.info("获取文件访问信息: {}", fileId);
        AccessInfoResponse response = converter.toAccessInfoResponse(
                fileMetadataService.getAccessInfo(
                        converter.toAccessInfoQuery(fileId)
                )
        );
        return ResultUtils.ok(response);
    }

    // ==================== 2. 文件搜索 ====================

    @Override
    public PageResultModel<FileInfoResponse> searchFiles(@Valid FileSearchRequest request) {
        log.info("搜索文件: keyword={}", request.getKeyword());
        PageResult<FileInfoResult> result = fileMetadataService.searchFiles(
                converter.toFileSearchQuery(request)
        );
        return PageResultUtils.ok(result.getTotal(), converter.toFileInfoResponseList(result.getRecords()));
    }

    @Override
    public PageResultModel<FileInfoResponse> simpleSearch(String keyword, Integer page, Integer size) {
        log.info("简单搜索: keyword={}, page={}, size={}", keyword, page, size);
        PageResult<FileInfoResult> result = fileMetadataService.simpleSearch(
                converter.toSimpleSearchQuery(keyword, page, size)
        );
        return PageResultUtils.ok(result.getTotal(), converter.toFileInfoResponseList(result.getRecords()));
    }

    @Override
    public PageResultModel<FileInfoResponse> listFiles(String fileType, String storageType,
                                                       String uploadTimeStart, String uploadTimeEnd,
                                                       Long minSize, Long maxSize, Integer status,
                                                       Integer page, Integer size) {
        log.info("获取文件列表: page={}, size={}", page, size);
        PageResult<FileInfoResult> result = fileMetadataService.listFiles(
                converter.toFileListQuery(fileType, storageType, uploadTimeStart, uploadTimeEnd,
                        minSize, maxSize, status, page, size)
        );
        return PageResultUtils.ok(result.getTotal(), converter.toFileInfoResponseList(result.getRecords()));
    }

    @Override
    public PageResultModel<FileInfoResponse> getMyFiles(Integer status, Integer page, Integer size) {
        log.info("获取我的文件: status={}, page={}, size={}", status, page, size);
        PageResult<FileInfoResult> result = fileMetadataService.getMyFiles(
                converter.toMyFileListQuery(status, page, size)
        );
        return PageResultUtils.ok(result.getTotal(), converter.toFileInfoResponseList(result.getRecords()));
    }

    @Override
    public ResultModel<List<FileInfoResponse>> getRecentFiles(Integer limit) {
        log.info("获取最近文件: limit={}", limit);
        List<FileInfoResponse> response = converter.toFileInfoResponseList(
                fileMetadataService.getRecentFiles(
                        converter.toRecentFileQuery(limit)
                )
        );
        return ResultUtils.ok(response);
    }

    // ==================== 3. 文件元数据更新 ====================

    @Override
    public ResultModel<FileInfoResponse> updateFileName(String fileId,
                                                        @Valid UpdateFileNameRequest request) {
        log.info("更新文件名: {}, newName={}", fileId, request.getNewFileName());
        FileInfoResponse response = converter.toFileInfoResponse(
                fileMetadataService.updateFileName(
                        converter.toUpdateFileNameCommand(fileId, request)
                )
        );
        return ResultUtils.ok(response);
    }

    @Override
    public ResultModel<FileInfoResponse> updateFileTags(String fileId,
                                                        @Valid UpdateTagsRequest request) {
        log.info("更新文件标签: {}, tags={}", fileId, request.getTags());
        FileInfoResponse response = converter.toFileInfoResponse(
                fileMetadataService.updateFileTags(
                        converter.toUpdateTagsCommand(fileId, request)
                )
        );
        return ResultUtils.ok(response);
    }

    @Override
    public ResultModel<FileInfoResponse> updateDescription(String fileId,
                                                           @Valid UpdateDescriptionRequest request) {
        log.info("更新文件描述: {}", fileId);
        FileInfoResponse response = converter.toFileInfoResponse(
                fileMetadataService.updateDescription(
                        converter.toUpdateDescriptionCommand(fileId, request)
                )
        );
        return ResultUtils.ok(response);
    }

    @Override
    public ResultModel<Map<String, String>> updateMetadata(String fileId,
                                                           Map<String, String> metadata) {
        log.info("更新文件元数据: {}", fileId);
        Map<String, String> response = fileMetadataService.updateMetadata(
                converter.toUpdateMetadataCommand(fileId, metadata)
        );
        return ResultUtils.ok(response);
    }

    @Override
    public ResultModel<List<String>> addTags(String fileId, List<String> tags) {
        log.info("添加文件标签: {}, tags={}", fileId, tags);
        List<String> response = fileMetadataService.addTags(
                converter.toAddTagsCommand(fileId, tags)
        );
        return ResultUtils.ok(response);
    }

    @Override
    public ResultModel<List<String>> removeTag(String fileId, String tag) {
        log.info("删除文件标签: {}, tag={}", fileId, tag);
        List<String> response = fileMetadataService.removeTag(
                converter.toRemoveTagCommand(fileId, tag)
        );
        return ResultUtils.ok(response);
    }

    @Override
    public ResultModel<BatchUpdateResponse> batchUpdateMetadata(@Valid BatchUpdateRequest request) {
        log.info("批量更新文件元数据: {} 个文件", request.getFileIds().size());
        BatchUpdateResponse response = converter.toBatchUpdateResponse(
                fileMetadataService.batchUpdateMetadata(
                        converter.toBatchUpdateCommand(request)
                )
        );
        return ResultUtils.ok(response);
    }

    // ==================== 4. 文件类型和分类 ====================

    @Override
    public ResultModel<List<FileTypeResponse>> getFileTypes() {
        log.info("获取文件类型列表");
        List<FileTypeResponse> response = converter.toFileTypeResponseList(
                fileMetadataService.getFileTypes()
        );
        return ResultUtils.ok(response);
    }

    @Override
    public ResultModel<List<FileTypeStatisticsResponse>> getFileTypeStatistics(String storageType) {
        log.info("获取文件类型统计: storageType={}", storageType);
        List<FileTypeStatisticsResponse> response = converter.toFileTypeStatisticsResponseList(
                fileMetadataService.getFileTypeStatistics(
                        converter.toFileTypeStatisticsQuery(storageType)
                )
        );
        return ResultUtils.ok(response);
    }

    @Override
    public ResultModel<List<StorageStatsResponse>> getStorageStatistics() {
        log.info("获取存储统计");
        List<StorageStatsResponse> response = converter.toStorageStatsResponseList(
                fileMetadataService.getStorageStatistics()
        );
        return ResultUtils.ok(response);
    }

    @Override
    public ResultModel<SizeDistributionResponse> getSizeDistribution() {
        log.info("获取文件大小分布");
        SizeDistributionResponse response = converter.toSizeDistributionResponse(
                fileMetadataService.getSizeDistribution()
        );
        return ResultUtils.ok(response);
    }

    @Override
    public ResultModel<List<TrendDataResponse>> getUploadTrend(String period, String startDate,
                                                               String endDate) {
        log.info("获取上传趋势: period={}, start={}, end={}", period, startDate, endDate);
        List<TrendDataResponse> response = converter.toTrendDataResponseList(
                fileMetadataService.getUploadTrend(
                        converter.toTrendDataQuery(period, startDate, endDate)
                )
        );
        return ResultUtils.ok(response);
    }

    // ==================== 5. 文件验证 ====================

    @Override
    public ResultModel<FileExistenceResponse> verifyFileExists(String fileMd5, Long fileSize,
                                                               Integer storageType) {
        log.info("验证文件是否存在: md5={}, size={}", fileMd5, fileSize);
        FileExistenceResponse response = converter.toFileExistenceResponse(
                fileMetadataService.verifyFileExists(
                        converter.toFileExistenceQuery(fileMd5, fileSize, storageType)
                )
        );
        return ResultUtils.ok(response);
    }

    @Override
    public ResultModel<IntegrityCheckResponse> verifyIntegrity(String fileId) {
        log.info("验证文件完整性: {}", fileId);
        IntegrityCheckResponse response = converter.toIntegrityCheckResponse(
                fileMetadataService.verifyIntegrity(
                        converter.toIntegrityCheckQuery(fileId)
                )
        );
        return ResultUtils.ok(response);
    }

    // ==================== 6. 文件对比 ====================

    @Override
    public ResultModel<FileCompareResponse> compareFiles(String fileId1, String fileId2) {
        log.info("对比文件: {} vs {}", fileId1, fileId2);
        FileCompareResponse response = converter.toFileCompareResponse(
                fileMetadataService.compareFiles(
                        converter.toFileCompareQuery(fileId1, fileId2)
                )
        );
        return ResultUtils.ok(response);
    }

    // ==================== 7. 文件访问控制 ====================

    @Override
    public ResultModel<PermissionInfoResponse> getFilePermission(String fileId) {
        log.info("获取文件权限: {}", fileId);
        PermissionInfoResponse response = converter.toPermissionInfoResponse(
                fileMetadataService.getFilePermission(
                        converter.toPermissionInfoQuery(fileId)
                )
        );
        return ResultUtils.ok(response);
    }

    @Override
    public ResultModel<PermissionInfoResponse> updateFilePermission(String fileId,
                                                                    @Valid UpdatePermissionRequest request) {
        log.info("更新文件权限: {}", fileId);
        PermissionInfoResponse response = converter.toPermissionInfoResponse(
                fileMetadataService.updateFilePermission(
                        converter.toUpdatePermissionCommand(fileId, request)
                )
        );
        return ResultUtils.ok(response);
    }

    @Override
    public ResultModel<String> setFilePublic(String fileId, Boolean publicAccess) {
        log.info("设置文件公开状态: {}, isPublic={}", fileId, publicAccess);
        fileMetadataService.setFilePublic(
                converter.toSetFilePublicCommand(fileId, publicAccess)
        );
        return ResultUtils.ok();
    }
}
