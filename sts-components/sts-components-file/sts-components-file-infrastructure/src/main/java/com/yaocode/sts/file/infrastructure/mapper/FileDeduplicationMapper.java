package com.yaocode.sts.file.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yaocode.sts.file.infrastructure.entity.FileDeduplicationEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileDeduplicationMapper extends BaseMapper<FileDeduplicationEntity> {

}
