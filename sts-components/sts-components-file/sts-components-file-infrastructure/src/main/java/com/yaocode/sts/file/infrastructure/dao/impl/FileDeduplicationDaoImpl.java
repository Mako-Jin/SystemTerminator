package com.yaocode.sts.file.infrastructure.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaocode.sts.common.tools.StringUtils;
import com.yaocode.sts.file.infrastructure.dao.FileDeduplicationDao;
import com.yaocode.sts.file.infrastructure.entity.FileDeduplicationEntity;
import com.yaocode.sts.file.infrastructure.mapper.FileDeduplicationMapper;
import org.springframework.stereotype.Repository;

@Repository
public class FileDeduplicationDaoImpl extends ServiceImpl<FileDeduplicationMapper, FileDeduplicationEntity> implements FileDeduplicationDao {

    @Override
    public FileDeduplicationEntity selectByFingerprint(String fingerprint) {
        if (StringUtils.isBlank(fingerprint)) {
            return null;
        }
        LambdaQueryWrapper<FileDeduplicationEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FileDeduplicationEntity::getFingerprint, fingerprint);
        return this.getOne(wrapper);
    }

    @Override
    public boolean updateByFileId(String fileId, String fingerprint, String fileMd5, String fileSha256, Long fileSize, String tenantId) {
        LambdaUpdateWrapper<FileDeduplicationEntity> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(FileDeduplicationEntity::getFileId, fileId);
        wrapper.eq(FileDeduplicationEntity::getTenantId, tenantId);
        wrapper.set(FileDeduplicationEntity::getFingerprint, fingerprint);
        wrapper.set(FileDeduplicationEntity::getFileMd5, fileMd5);
        wrapper.set(FileDeduplicationEntity::getFileSha256, fileSha256);
        wrapper.set(FileDeduplicationEntity::getFileSize, fileSize);
        return this.update(wrapper);
    }

}
