package com.yaocode.sts.components.file.interfaces.api;

import com.yaocode.sts.common.web.model.PageResultModel;
import com.yaocode.sts.common.web.model.ResultModel;
import com.yaocode.sts.components.file.interfaces.model.request.BatchUpdateRequest;
import com.yaocode.sts.components.file.interfaces.model.request.FileSearchRequest;
import com.yaocode.sts.components.file.interfaces.model.request.UpdateDescriptionRequest;
import com.yaocode.sts.components.file.interfaces.model.request.UpdateFileNameRequest;
import com.yaocode.sts.components.file.interfaces.model.request.UpdatePermissionRequest;
import com.yaocode.sts.components.file.interfaces.model.request.UpdateTagsRequest;
import com.yaocode.sts.components.file.interfaces.model.response.AccessInfoResponse;
import com.yaocode.sts.components.file.interfaces.model.response.BatchUpdateResponse;
import com.yaocode.sts.components.file.interfaces.model.response.FileCompareResponse;
import com.yaocode.sts.components.file.interfaces.model.response.FileDetailInfoResponse;
import com.yaocode.sts.components.file.interfaces.model.response.FileExistenceResponse;
import com.yaocode.sts.components.file.interfaces.model.response.FileInfoResponse;
import com.yaocode.sts.components.file.interfaces.model.response.FileTypeResponse;
import com.yaocode.sts.components.file.interfaces.model.response.FileTypeStatisticsResponse;
import com.yaocode.sts.components.file.interfaces.model.response.IntegrityCheckResponse;
import com.yaocode.sts.components.file.interfaces.model.response.PermissionInfoResponse;
import com.yaocode.sts.components.file.interfaces.model.response.SizeDistributionResponse;
import com.yaocode.sts.components.file.interfaces.model.response.StorageInfoResponse;
import com.yaocode.sts.components.file.interfaces.model.response.StorageStatsResponse;
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
import java.util.Map;

/**
 * 文件元数据API
 * <p>
 * 提供文件元数据管理功能：
 * <ul>
 *   <li>文件信息查询 - 获取文件的基本信息、详细信息</li>
 *   <li>文件元数据更新 - 更新文件名、标签、描述等</li>
 *   <li>文件搜索 - 支持多条件搜索文件</li>
 *   <li>文件统计 - 获取文件统计信息</li>
 *   <li>批量操作 - 批量更新文件元数据</li>
 *   <li>文件类型管理 - 获取文件类型分类</li>
 *   <li>存储信息查询 - 获取文件的存储位置信息</li>
 * </ul>
 * </p>
 *
 * @author yaocode
 * @since 1.0.0
 */
@RequestMapping("/files")
public interface FileMetadataApi {

    // ==================== 1. 文件信息查询 ====================

    /**
     * 获取文件基本信息
     *
     * @param fileId 文件ID
     * @return 文件基本信息
     */
    @GetMapping("/{fileId}")
    ResultModel<FileInfoResponse> getFileInfo(@PathVariable String fileId);

    /**
     * 获取文件详细信息（包含元数据、访问记录等）
     *
     * @param fileId 文件ID
     * @return 文件详细信息
     */
    @GetMapping("/{fileId}/detail")
    ResultModel<FileDetailInfoResponse> getFileDetail(@PathVariable String fileId);

    /**
     * 批量获取文件信息
     *
     * @param fileIds 文件ID列表
     * @return 文件信息列表
     */
    @PostMapping("/batch/info")
    ResultModel<List<FileInfoResponse>> getFileInfoBatch(@RequestBody List<String> fileIds);

    /**
     * 获取文件的元数据（自定义属性）
     *
     * @param fileId 文件ID
     * @return 元数据键值对
     */
    @GetMapping("/{fileId}/metadata")
    ResultModel<Map<String, String>> getFileMetadata(@PathVariable String fileId);

    /**
     * 获取文件的存储信息
     *
     * @param fileId 文件ID
     * @return 存储信息
     */
    @GetMapping("/{fileId}/storage")
    ResultModel<StorageInfoResponse> getStorageInfo(@PathVariable String fileId);

    /**
     * 获取文件的访问信息（下载次数、查看次数等）
     *
     * @param fileId 文件ID
     * @return 访问信息
     */
    @GetMapping("/{fileId}/access")
    ResultModel<AccessInfoResponse> getAccessInfo(@PathVariable String fileId);

    // ==================== 2. 文件搜索 ====================

    /**
     * 搜索文件（多条件）
     *
     * @param request 搜索请求
     * @return 文件列表
     */
    @PostMapping("/search")
    PageResultModel<FileInfoResponse> searchFiles(@RequestBody FileSearchRequest request);

    /**
     * 简单搜索文件（关键词）
     *
     * @param keyword 关键词（文件名、标签）
     * @param page 页码
     * @param size 每页数量
     * @return 文件列表
     */
    @GetMapping("/search/simple")
    PageResultModel<FileInfoResponse> simpleSearch(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer size
    );

    /**
     * 获取文件列表（带筛选条件）
     *
     * @param fileType 文件类型（可选）
     * @param storageType 存储类型（可选）
     * @param uploadTimeStart 上传开始时间（可选）
     * @param uploadTimeEnd 上传结束时间（可选）
     * @param minSize 最小文件大小（可选）
     * @param maxSize 最大文件大小（可选）
     * @param status 文件状态（可选）
     * @param page 页码
     * @param size 每页数量
     * @return 文件列表
     */
    @GetMapping("/list")
    PageResultModel<FileInfoResponse> listFiles(
            @RequestParam(required = false) String fileType,
            @RequestParam(required = false) String storageType,
            @RequestParam(required = false) String uploadTimeStart,
            @RequestParam(required = false) String uploadTimeEnd,
            @RequestParam(required = false) Long minSize,
            @RequestParam(required = false) Long maxSize,
            @RequestParam(required = false) Integer status,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer size
    );

    /**
     * 获取当前用户上传的文件列表
     *
     * @param status 文件状态（可选）
     * @param page 页码
     * @param size 每页数量
     * @return 文件列表
     */
    @GetMapping("/my-files")
    PageResultModel<FileInfoResponse> getMyFiles(
            @RequestParam(required = false) Integer status,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer size
    );

    /**
     * 获取最近上传的文件
     *
     * @param limit 数量限制
     * @return 文件列表
     */
    @GetMapping("/recent")
    ResultModel<List<FileInfoResponse>> getRecentFiles(
            @RequestParam(defaultValue = "10") Integer limit
    );

    // ==================== 3. 文件元数据更新 ====================

    /**
     * 更新文件名
     *
     * @param fileId 文件ID
     * @param request 更新请求
     * @return 更新后的文件信息
     */
    @PutMapping("/{fileId}/name")
    ResultModel<FileInfoResponse> updateFileName(
            @PathVariable String fileId,
            @RequestBody UpdateFileNameRequest request
    );

    /**
     * 更新文件标签
     *
     * @param fileId 文件ID
     * @param request 更新请求
     * @return 更新后的文件信息
     */
    @PutMapping("/{fileId}/tags")
    ResultModel<FileInfoResponse> updateFileTags(
            @PathVariable String fileId,
            @RequestBody UpdateTagsRequest request
    );

    /**
     * 更新文件描述
     *
     * @param fileId 文件ID
     * @param request 更新请求
     * @return 更新后的文件信息
     */
    @PutMapping("/{fileId}/description")
    ResultModel<FileInfoResponse> updateDescription(
            @PathVariable String fileId,
            @RequestBody UpdateDescriptionRequest request
    );

    /**
     * 更新文件元数据（自定义属性）
     *
     * @param fileId 文件ID
     * @param metadata 元数据键值对
     * @return 更新后的元数据
     */
    @PutMapping("/{fileId}/metadata")
    ResultModel<Map<String, String>> updateMetadata(
            @PathVariable String fileId,
            @RequestBody Map<String, String> metadata
    );

    /**
     * 添加文件标签
     *
     * @param fileId 文件ID
     * @param tags 标签列表
     * @return 更新后的标签列表
     */
    @PostMapping("/{fileId}/tags")
    ResultModel<List<String>> addTags(
            @PathVariable String fileId,
            @RequestBody List<String> tags
    );

    /**
     * 删除文件标签
     *
     * @param fileId 文件ID
     * @param tag 标签
     * @return 更新后的标签列表
     */
    @DeleteMapping("/{fileId}/tags/{tag}")
    ResultModel<List<String>> removeTag(
            @PathVariable String fileId,
            @PathVariable String tag
    );

    /**
     * 批量更新文件元数据
     *
     * @param request 批量更新请求
     * @return 批量更新结果
     */
    @PostMapping("/batch/update")
    ResultModel<BatchUpdateResponse> batchUpdateMetadata(@RequestBody BatchUpdateRequest request);

    // ==================== 4. 文件类型和分类 ====================

    /**
     * 获取文件类型列表
     *
     * @return 文件类型列表
     */
    @GetMapping("/types")
    ResultModel<List<FileTypeResponse>> getFileTypes();

    /**
     * 按文件类型统计
     *
     * @param storageType 存储类型（可选）
     * @return 统计信息
     */
    @GetMapping("/statistics/types")
    ResultModel<List<FileTypeStatisticsResponse>> getFileTypeStatistics(
            @RequestParam(required = false) String storageType
    );

    /**
     * 按存储类型统计
     *
     * @return 统计信息
     */
    @GetMapping("/statistics/storages")
    ResultModel<List<StorageStatsResponse>> getStorageStatistics();

    /**
     * 获取文件大小分布
     *
     * @return 大小分布
     */
    @GetMapping("/statistics/size-distribution")
    ResultModel<SizeDistributionResponse> getSizeDistribution();

    /**
     * 获取上传时间趋势
     *
     * @param period 统计周期（day/week/month/year）
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 趋势数据
     */
    @GetMapping("/statistics/trend")
    ResultModel<List<TrendDataResponse>> getUploadTrend(
            @RequestParam(defaultValue = "day") String period,
            @RequestParam String startDate,
            @RequestParam String endDate
    );

    // ==================== 5. 文件验证 ====================

    /**
     * 验证文件是否存在
     *
     * @param fileMd5 文件MD5
     * @param fileSize 文件大小
     * @param storageType 存储类型（可选）
     * @return 验证结果
     */
    @PostMapping("/verify")
    ResultModel<FileExistenceResponse> verifyFileExists(
            @RequestParam String fileMd5,
            @RequestParam Long fileSize,
            @RequestParam(required = false) String storageType
    );

    /**
     * 验证文件完整性（通过MD5）
     *
     * @param fileId 文件ID
     * @return 验证结果
     */
    @PostMapping("/{fileId}/verify/integrity")
    ResultModel<IntegrityCheckResponse> verifyIntegrity(@PathVariable String fileId);

    // ==================== 6. 文件对比 ====================

    /**
     * 对比两个文件是否相同（基于MD5）
     *
     * @param fileId1 文件1ID
     * @param fileId2 文件2ID
     * @return 对比结果
     */
    @GetMapping("/compare")
    ResultModel<FileCompareResponse> compareFiles(
            @RequestParam String fileId1,
            @RequestParam String fileId2
    );

    // ==================== 7. 文件访问控制 ====================

    /**
     * 获取文件的访问权限信息
     *
     * @param fileId 文件ID
     * @return 权限信息
     */
    @GetMapping("/{fileId}/permission")
    ResultModel<PermissionInfoResponse> getFilePermission(@PathVariable String fileId);

    /**
     * 更新文件访问权限
     *
     * @param fileId 文件ID
     * @param request 权限请求
     * @return 更新后的权限信息
     */
    @PutMapping("/{fileId}/permission")
    ResultModel<PermissionInfoResponse> updateFilePermission(
            @PathVariable String fileId,
            @RequestBody UpdatePermissionRequest request
    );

    /**
     * 设置文件为公开/私有
     *
     * @param fileId 文件ID
     * @param publicAccess 是否公开
     * @return 操作结果
     */
    @PutMapping("/{fileId}/public")
    ResultModel<Void> setFilePublic(
            @PathVariable String fileId,
            @RequestParam Boolean publicAccess
    );
}