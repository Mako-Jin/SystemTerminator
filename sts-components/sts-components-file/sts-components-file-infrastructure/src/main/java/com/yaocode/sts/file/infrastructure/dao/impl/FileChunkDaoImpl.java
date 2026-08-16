package com.yaocode.sts.file.infrastructure.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaocode.sts.file.core.enums.ChunkStatusEnums;
import com.yaocode.sts.file.infrastructure.dao.FileChunkDao;
import com.yaocode.sts.file.infrastructure.entity.FileChunkEntity;
import com.yaocode.sts.file.infrastructure.mapper.FileChunkMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class FileChunkDaoImpl extends ServiceImpl<FileChunkMapper, FileChunkEntity> implements FileChunkDao {

    @Resource
    private FileChunkMapper fileChunkMapper;

    @Override
    public FileChunkEntity selectByUploadIdAndChunkNumber(String uploadId, Integer chunkNumber) {
        LambdaQueryWrapper<FileChunkEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FileChunkEntity::getUploadId, uploadId);
        queryWrapper.eq(FileChunkEntity::getChunkNumber, chunkNumber);
        queryWrapper.last("LIMIT 1");
        return this.getOne(queryWrapper);
    }

    @Override
    public void updateStatus(String uploadId, Integer chunkNumber, Integer chunkStatus, String chunkPath) {
        LambdaUpdateWrapper<FileChunkEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(FileChunkEntity::getUploadId, uploadId);
        updateWrapper.eq(FileChunkEntity::getChunkNumber, chunkNumber);
        updateWrapper.set(FileChunkEntity::getChunkStatus, chunkStatus);
        updateWrapper.set(FileChunkEntity::getChunkPath, chunkPath);
        updateWrapper.set(FileChunkEntity::getUploadEndTime, LocalDateTime.now());
        this.update(updateWrapper);
    }

    @Override
    public int countCompletedByUploadId(String uploadId) {
        LambdaQueryWrapper<FileChunkEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FileChunkEntity::getUploadId, uploadId);
        queryWrapper.eq(FileChunkEntity::getChunkStatus, ChunkStatusEnums.COMPLETED.getCode());
        return (int) this.count(queryWrapper);
    }

    @Override
    public List<FileChunkEntity> selectByUploadIdAndTenantId(String uploadId, String tenantId) {
        LambdaQueryWrapper<FileChunkEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FileChunkEntity::getUploadId, uploadId);
        queryWrapper.eq(FileChunkEntity::getTenantId, tenantId);
        queryWrapper.orderByAsc(FileChunkEntity::getChunkNumber);
        return this.list(queryWrapper);
    }

    @Override
    public void deleteByUploadIdAndTenantId(String uploadId, String tenantId) {
        LambdaUpdateWrapper<FileChunkEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(FileChunkEntity::getUploadId, uploadId);
        updateWrapper.set(FileChunkEntity::getIsDeleted, 1);
        this.update(updateWrapper);
    }

    @Override
    public List<FileChunkEntity> selectCompletedByUploadIdAndTenantId(String uploadId, String tenantId) {
        LambdaQueryWrapper<FileChunkEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FileChunkEntity::getUploadId, uploadId);
        queryWrapper.eq(FileChunkEntity::getTenantId, tenantId);
        queryWrapper.eq(FileChunkEntity::getChunkStatus, ChunkStatusEnums.COMPLETED.getCode());
        queryWrapper.orderByAsc(FileChunkEntity::getChunkNumber);
        return this.list(queryWrapper);
    }
}
