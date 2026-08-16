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

}
