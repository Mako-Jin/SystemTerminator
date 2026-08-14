package com.yaocode.sts.file.infrastructure.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaocode.sts.file.core.enums.FileStatusEnums;
import com.yaocode.sts.file.infrastructure.dao.FileBaseInfoDao;
import com.yaocode.sts.file.infrastructure.entity.FileBasicInfoEntity;
import com.yaocode.sts.file.infrastructure.mapper.FileBaseInfoMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class FileBaseInfoDaoImpl extends ServiceImpl<FileBaseInfoMapper, FileBasicInfoEntity> implements FileBaseInfoDao {

    @Resource
    private FileBaseInfoMapper fileBaseInfoMapper;

    @Override
    public FileBasicInfoEntity selectByFileIdAndTenant(String fileId, String tenantId) {
        LambdaQueryWrapper<FileBasicInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FileBasicInfoEntity::getFileId, fileId);
        queryWrapper.eq(FileBasicInfoEntity::getTenantId, tenantId);
        return this.getOne(queryWrapper);
    }

    @Override
    public List<FileBasicInfoEntity> selectByMd5AndTenant(String fileMd5, String tenantId) {
        LambdaQueryWrapper<FileBasicInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FileBasicInfoEntity::getFileMd5, fileMd5);
        queryWrapper.eq(FileBasicInfoEntity::getTenantId, tenantId);
        queryWrapper.eq(FileBasicInfoEntity::getFileStatus, FileStatusEnums.NORMAL.getCode());
        return this.list(queryWrapper);
    }

    @Override
    public void updateFileContent(String fileId, String tenantId, String filePath, String fileUrl,
                                  Long fileSize, String fileMd5, String fileSha256) {
        LambdaUpdateWrapper<FileBasicInfoEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(FileBasicInfoEntity::getFileId, fileId);
        updateWrapper.eq(FileBasicInfoEntity::getTenantId, tenantId);
        updateWrapper.set(FileBasicInfoEntity::getFilePath, filePath);
        updateWrapper.set(FileBasicInfoEntity::getStorageUrl, fileUrl);
        updateWrapper.set(FileBasicInfoEntity::getFileSize, fileSize);
        updateWrapper.set(FileBasicInfoEntity::getFileMd5, fileMd5);
        updateWrapper.set(FileBasicInfoEntity::getFileSha256, fileSha256);
        updateWrapper.set(FileBasicInfoEntity::getUploadTime, LocalDateTime.now());
        this.update(updateWrapper);
    }

}