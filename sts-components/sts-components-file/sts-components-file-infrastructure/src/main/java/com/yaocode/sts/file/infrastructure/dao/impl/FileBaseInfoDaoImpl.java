package com.yaocode.sts.file.infrastructure.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaocode.sts.file.core.enums.FileStatusEnums;
import com.yaocode.sts.file.infrastructure.dao.FileBaseInfoDao;
import com.yaocode.sts.file.infrastructure.entity.FileInfoEntity;
import com.yaocode.sts.file.infrastructure.mapper.FileBaseInfoMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FileBaseInfoDaoImpl extends ServiceImpl<FileBaseInfoMapper, FileInfoEntity> implements FileBaseInfoDao {

    @Override
    public FileInfoEntity selectByFileIdAndTenant(String fileId, String tenantId) {
        LambdaQueryWrapper<FileInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FileInfoEntity::getFileId, fileId);
        queryWrapper.eq(FileInfoEntity::getTenantId, tenantId);
        return this.getOne(queryWrapper);
    }

    @Override
    public List<FileInfoEntity> selectByMd5AndTenant(String fileMd5, String tenantId) {
        LambdaQueryWrapper<FileInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FileInfoEntity::getFileMd5, fileMd5);
        queryWrapper.eq(FileInfoEntity::getTenantId, tenantId);
        queryWrapper.eq(FileInfoEntity::getFileStatus, FileStatusEnums.NORMAL.getCode());
        return this.list(queryWrapper);
    }

}
