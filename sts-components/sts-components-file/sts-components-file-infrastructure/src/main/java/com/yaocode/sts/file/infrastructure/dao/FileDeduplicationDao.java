package com.yaocode.sts.file.infrastructure.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yaocode.sts.file.infrastructure.entity.FileDeduplicationEntity;
import org.apache.ibatis.annotations.Param;

public interface FileDeduplicationDao extends IService<FileDeduplicationEntity> {

    /**
     * 根据指纹查询去重记录
     * @param fingerprint 指纹（fingerprint = MD5 + Size + StorageType 等组合）
     * @return FileDeduplicationEntity
     */
    FileDeduplicationEntity selectByFingerprint(String fingerprint);

    /**
     * 更新文件去重记录
     */
    boolean updateByFileId(
            @Param("fileId") String fileId,
            @Param("fingerprint") String fingerprint,
            @Param("fileMd5") String fileMd5,
            @Param("fileSha256") String fileSha256,
            @Param("fileSize") Long fileSize,
            @Param("tenantId") String tenantId
    );

}
