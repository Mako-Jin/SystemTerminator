package com.yaocode.sts.file.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yaocode.sts.file.infrastructure.entity.FileDeduplicationEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FileDeduplicationMapper extends BaseMapper<FileDeduplicationEntity> {

    FileDeduplicationEntity selectByFingerprint(String fingerprint);

    /**
     * 更新文件去重记录
     */
    int updateByFileId(
            @Param("fileId") String fileId,
            @Param("fingerprint") String fingerprint,
            @Param("fileMd5") String fileMd5,
            @Param("fileSha256") String fileSha256,
            @Param("fileSize") Long fileSize,
            @Param("tenantId") String tenantId
    );
}
