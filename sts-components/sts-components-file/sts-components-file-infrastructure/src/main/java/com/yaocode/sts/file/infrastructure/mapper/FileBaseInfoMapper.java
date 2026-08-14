package com.yaocode.sts.file.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yaocode.sts.file.infrastructure.entity.FileBasicInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 文件信息Mapper
 *
 * @author yaocode
 * @since 1.0.0
 */
@Mapper
public interface FileBaseInfoMapper extends BaseMapper<FileBasicInfoEntity> {

    /**
     * 根据文件ID列表和租户ID批量查询
     */
    List<FileBasicInfoEntity> selectByFileIdsAndTenant(
            @Param("fileIds") List<String> fileIds,
            @Param("tenantId") String tenantId
    );

    /**
     * 检查文件名是否已存在（租户内）
     */
    int existsByFileNameAndTenant(
            @Param("fileName") String fileName,
            @Param("tenantId") String tenantId
    );

    /**
     * 按存储类型和租户统计文件数
     */
    long countByStorageTypeAndTenant(
            @Param("storageType") String storageType,
            @Param("tenantId") String tenantId
    );

    /**
     * 按存储类型和租户统计文件总大小
     */
    long sumFileSizeByStorageTypeAndTenant(
            @Param("storageType") String storageType,
            @Param("tenantId") String tenantId
    );

    // ==================== 回收站相关 ====================

    /**
     * 查询过期文件（回收站）
     */
    List<FileBasicInfoEntity> selectExpiredFiles(
            @Param("expireTime") LocalDateTime expireTime,
            @Param("storageType") String storageType,
            @Param("tenantId") String tenantId
    );

    /**
     * 查询临时文件（未完成上传）
     */
    List<FileBasicInfoEntity> selectTempFiles(
            @Param("expireTime") LocalDateTime expireTime,
            @Param("tenantId") String tenantId
    );

    // ==================== 文件详情查询 ====================

    /**
     * 获取文件详细信息
     */
    FileBasicInfoEntity getFileDetail(
            @Param("fileId") String fileId,
            @Param("tenantId") String tenantId
    );

    /**
     * 获取文件版本列表
     */
    List<FileBasicInfoEntity> getFileVersions(
            @Param("fileId") String fileId,
            @Param("tenantId") String tenantId
    );

    // ==================== 文件列表查询 ====================

//    /**
//     * 管理员文件列表查询
//     */
//    PageResult<FileInfoEntity> listFiles(AdminFileListQuery query);
//
//    /**
//     * 用户文件列表查询
//     */
//    PageResult<FileInfoEntity> selectUserFiles(UploadFileListQuery query);
//
//    // ==================== 重复文件查询 ====================

    /**
     * 查找重复文件
     */
    List<FileBasicInfoEntity> findDuplicateFiles(
            @Param("storageType") String storageType,
            @Param("tenantId") String tenantId
    );

//    // ==================== 统计查询 ====================

//    /**
//     * 获取管理员统计信息
//     */
//    AdminStatisticsResult getAdminStatistics(
//            @Param("storageType") String storageType,
//            @Param("tenantId") String tenantId
//    );
//
//    /**
//     * 获取趋势数据
//     */
//    List<TrendDataResult> getTrendData(
//            @Param("period") String period,
//            @Param("startDate") String startDate,
//            @Param("endDate") String endDate,
//            @Param("storageType") String storageType,
//            @Param("tenantId") String tenantId
//    );
//
//    // ==================== 审核相关 ====================
//
//    /**
//     * 获取审核列表
//     */
//    PageResult<FileAuditInfoResult> getAuditList(AuditListQuery query);

//    /**
//     * 更新审核状态
//     */
//    int updateAuditStatus(
//            @Param("fileId") String fileId,
//            @Param("auditStatus") Integer auditStatus,
//            @Param("auditComment") String auditComment,
//            @Param("auditUserId") String auditUserId,
//            @Param("auditUserName") String auditUserName,
//            @Param("auditTime") LocalDateTime auditTime,
//            @Param("tenantId") String tenantId
//    );
//
//    // ==================== 文件更新操作 ====================
//
//    /**
//     * 增加下载次数
//     */
//    int incrementDownloadCount(@Param("fileId") String fileId, @Param("tenantId") String tenantId);
//
//    /**
//     * 增加查看次数
//     */
//    int incrementViewCount(@Param("fileId") String fileId, @Param("tenantId") String tenantId);
//
//    /**
//     * 更新文件元数据
//     */
//    int updateMetadata(
//            @Param("fileId") String fileId,
//            @Param("metadata") String metadata
//    );
//
//    /**
//     * 批量更新文件状态
//     */
//    int batchUpdateStatus(
//            @Param("fileIds") List<String> fileIds,
//            @Param("status") Integer status,
//            @Param("updateUserId") String updateUserId
//    );

//    /**
//     * 更新文件版本号
//     */
//    int updateFileVersion(
//            @Param("fileId") String fileId,
//            @Param("versionNumber") Integer versionNumber,
//            @Param("tenantId") String tenantId
//    );
}