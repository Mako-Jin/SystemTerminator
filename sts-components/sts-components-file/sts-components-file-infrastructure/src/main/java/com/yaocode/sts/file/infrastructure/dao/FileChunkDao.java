package com.yaocode.sts.file.infrastructure.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yaocode.sts.file.infrastructure.entity.FileChunkEntity;

import java.util.List;

public interface FileChunkDao extends IService<FileChunkEntity> {

    /**
     * 根据上传ID和分片序号查询
     */
    FileChunkEntity selectByUploadIdAndChunkNumber(String uploadId, Integer chunkNumber);

    /**
     * 更新分片状态和路径
     */
    void updateStatus(String uploadId, Integer chunkNumber, Integer chunkStatus, String chunkPath);

    /**
     * 统计已完成的分片数
     */
    int countCompletedByUploadId(String uploadId);

    /**
     * 查询所有分片列表
     */
    List<FileChunkEntity> selectByUploadIdAndTenantId(String uploadId, String tenantId);

    /**
     * 根据上传ID删除所有分片记录（逻辑删除）
     */
    void deleteByUploadIdAndTenantId(String uploadId, String tenantId);

    /**
     * 查询已完成的分片列表
     */
    List<FileChunkEntity> selectCompletedByUploadIdAndTenantId(String uploadId, String tenantId);

    /**
     * 批量更新指定上传会话中未完成的分片状态
     *
     * @param uploadId      上传ID
     * @param tenantId      租户ID
     * @param targetStatus  目标状态
     * @param excludeStatus 排除的状态（不更新该状态的分片）
     * @param errorMessage  错误信息（可选）
     * @return 更新的行数
     */
    int batchUpdateStatusByUploadId(
            String uploadId, String tenantId,
            Integer targetStatus, Integer excludeStatus,
            String errorMessage
    );
}