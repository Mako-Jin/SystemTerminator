package com.yaocode.sts.file.infrastructure.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaocode.sts.common.basic.model.PageResult;
import com.yaocode.sts.file.core.enums.UploadStatusEnums;
import com.yaocode.sts.file.infrastructure.dao.UploadSessionDao;
import com.yaocode.sts.file.infrastructure.entity.UploadSessionEntity;
import com.yaocode.sts.file.infrastructure.mapper.UploadSessionMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Objects;

@Repository
public class UploadSessionDaoImpl extends ServiceImpl<UploadSessionMapper, UploadSessionEntity> implements UploadSessionDao {

    @Resource
    private UploadSessionMapper uploadSessionMapper;

    @Override
    public UploadSessionEntity selectByUploadIdAndTenant(String uploadId, String tenantId) {
        LambdaQueryWrapper<UploadSessionEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UploadSessionEntity::getUploadId, uploadId);
        queryWrapper.eq(UploadSessionEntity::getTenantId, tenantId);
        queryWrapper.last("LIMIT 1");
        return this.getOne(queryWrapper);
    }

    @Override
    public void updateStatus(String uploadId, Integer uploadStatus, Integer completedChunks) {
        LambdaUpdateWrapper<UploadSessionEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(UploadSessionEntity::getUploadId, uploadId);
        updateWrapper.set(UploadSessionEntity::getUploadStatus, uploadStatus);
        updateWrapper.set(UploadSessionEntity::getCompletedChunks, completedChunks);
        updateWrapper.set(UploadSessionEntity::getLastActiveTime, LocalDateTime.now());
        this.update(updateWrapper);
    }

    @Override
    public PageResult<UploadSessionEntity> pageByTenant(String tenantId, Integer status, int page, int size) {
        Page<UploadSessionEntity> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<UploadSessionEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UploadSessionEntity::getTenantId, tenantId);
        if (Objects.nonNull(status)) {
            UploadStatusEnums statusEnum = UploadStatusEnums.fromCode(status);
            if (statusEnum != null) {
                queryWrapper.eq(UploadSessionEntity::getUploadStatus, statusEnum.getCode());
            }
        }
        queryWrapper.orderByDesc(UploadSessionEntity::getCreateTime);
        Page<UploadSessionEntity> pageResult = this.page(pageParam, queryWrapper);
        return PageResult.<UploadSessionEntity>builder()
                .total(pageResult.getTotal())
                .records(pageResult.getRecords())
                .page(pageParam.getCurrent())
                .size(pageResult.getSize())
                .build();
    }

    @Override
    public UploadSessionEntity selectActiveSession(String fileMd5, Long fileSize, Integer storageType, String tenantId) {
        LambdaQueryWrapper<UploadSessionEntity> queryWrapper = new LambdaQueryWrapper<>();
        if (fileMd5 != null && !fileMd5.isEmpty()) {
            queryWrapper.eq(UploadSessionEntity::getFileMd5, fileMd5);
        }
        if (fileSize != null) {
            queryWrapper.eq(UploadSessionEntity::getFileSize, fileSize);
        }
        if (storageType != null) {
            queryWrapper.eq(UploadSessionEntity::getStorageType, storageType);
        }
        queryWrapper.eq(UploadSessionEntity::getTenantId, tenantId);
        queryWrapper.eq(UploadSessionEntity::getUploadStatus, UploadStatusEnums.UPLOADING.getCode());
        queryWrapper.last("LIMIT 1");
        return this.getOne(queryWrapper);
    }
}