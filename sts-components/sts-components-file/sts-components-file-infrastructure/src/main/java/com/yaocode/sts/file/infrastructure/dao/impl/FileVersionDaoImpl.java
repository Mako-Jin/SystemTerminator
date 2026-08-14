package com.yaocode.sts.file.infrastructure.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaocode.sts.file.infrastructure.dao.FileVersionDao;
import com.yaocode.sts.file.infrastructure.entity.FileVersionEntity;
import com.yaocode.sts.file.infrastructure.mapper.FileVersionMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

@Repository
public class FileVersionDaoImpl extends ServiceImpl<FileVersionMapper, FileVersionEntity> implements FileVersionDao {

    @Resource
    private FileVersionMapper fileVersionMapper;

    @Override
    public int getMaxVersionByFileId(String fileId, String tenantId) {
        return fileVersionMapper.getMaxVersionByFileId(fileId, tenantId);
    }

}
