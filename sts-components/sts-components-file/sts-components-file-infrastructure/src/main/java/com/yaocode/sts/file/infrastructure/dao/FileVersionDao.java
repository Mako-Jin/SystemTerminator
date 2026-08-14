package com.yaocode.sts.file.infrastructure.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yaocode.sts.file.infrastructure.entity.FileVersionEntity;

public interface FileVersionDao extends IService<FileVersionEntity> {

    /**
     * 根据文件ID和租户ID查询最大版本号
     */
    int getMaxVersionByFileId(String fileId, String tenantId);

}
