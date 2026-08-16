package com.yaocode.sts.file.infrastructure.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yaocode.sts.common.basic.model.PageResult;
import com.yaocode.sts.file.infrastructure.entity.UploadSessionEntity;

public interface UploadSessionDao extends IService<UploadSessionEntity> {

    /**
     * 根据上传会话ID和租户ID查询
     */
    UploadSessionEntity selectByUploadIdAndTenant(String uploadId, String tenantId);

    /**
     * 更新会话状态和已完成分片数
     */
    void updateStatus(String uploadId, Integer uploadStatus, Integer completedChunks);

    /**
     * 分页查询租户的上传会话
     */
    PageResult<UploadSessionEntity> pageByTenant(String tenantId, Integer status, int page, int size);

    /**
     * 根据文件MD5+文件大小+存储类型查询活动(上传中)的会话
     * 用于续传检测：同一文件断点续传时复用已有会话
     */
    UploadSessionEntity selectActiveSession(String fileMd5, Long fileSize, Integer storageType, String tenantId);

}