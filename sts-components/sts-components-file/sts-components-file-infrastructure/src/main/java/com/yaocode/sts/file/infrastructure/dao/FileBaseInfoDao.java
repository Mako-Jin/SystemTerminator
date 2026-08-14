package com.yaocode.sts.file.infrastructure.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yaocode.sts.file.infrastructure.entity.FileBasicInfoEntity;

import java.util.List;

public interface FileBaseInfoDao extends IService<FileBasicInfoEntity> {

    /**
     * 根据文件ID和租户ID查询
     */
    FileBasicInfoEntity selectByFileIdAndTenant(String fileId, String tenantId);

    /**
     * 根据MD5和租户ID查询
     */
    List<FileBasicInfoEntity> selectByMd5AndTenant(String fileMd5, String tenantId);

    /**
     * 根据文件ID更新文件内容元数据（覆盖场景）
     *
     * @param fileId     文件ID
     * @param tenantId   租户ID
     * @param filePath   新的存储路径
     * @param fileUrl    新的访问URL
     * @param fileSize   新的文件大小
     * @param fileMd5    新的MD5
     * @param fileSha256 新的SHA256
     */
    void updateFileContent(
            String fileId, String tenantId, String filePath, String fileUrl,
            Long fileSize, String fileMd5, String fileSha256
    );

}