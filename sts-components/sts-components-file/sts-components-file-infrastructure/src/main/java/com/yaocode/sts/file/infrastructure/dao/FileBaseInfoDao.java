package com.yaocode.sts.file.infrastructure.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yaocode.sts.file.infrastructure.entity.FileInfoEntity;

import java.util.List;

public interface FileBaseInfoDao extends IService<FileInfoEntity> {

    /**
     * 根据文件ID和租户ID查询
     */
    FileInfoEntity selectByFileIdAndTenant(String fileId, String tenantId);

    /**
     * 根据MD5和租户ID查询
     */
    List<FileInfoEntity> selectByMd5AndTenant(String fileMd5, String tenantId);

}
