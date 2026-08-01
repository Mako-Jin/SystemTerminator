package com.yaocode.sts.file.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yaocode.sts.file.infrastructure.entity.FileVersionDiffEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文件版本差异Mapper
 *
 * @author yaocode
 * @since 1.0.0
 */
@Mapper
public interface FileVersionDiffMapper extends BaseMapper<FileVersionDiffEntity> {

    /**
     * 插入差异记录
     */
    int insert(FileVersionDiffEntity entity);

    /**
     * 根据差异ID查询
     */
    FileVersionDiffEntity selectByDiffId(@Param("diffId") String diffId);

    /**
     * 根据版本ID查询差异
     */
    FileVersionDiffEntity selectByVersionId(@Param("versionId") String versionId);

    /**
     * 根据源版本和目标版本查询差异
     */
    FileVersionDiffEntity selectByFromAndTo(
            @Param("fromVersionId") String fromVersionId,
            @Param("toVersionId") String toVersionId
    );

    /**
     * 根据文件ID查询差异列表
     */
    List<FileVersionDiffEntity> selectByFileId(@Param("fileId") String fileId);

    /**
     * 获取从源版本到目标版本的差异路径
     */
    List<FileVersionDiffEntity> selectPath(
            @Param("fromVersionId") String fromVersionId,
            @Param("toVersionId") String toVersionId
    );

    /**
     * 删除差异
     */
    int deleteByDiffId(@Param("diffId") String diffId);

    /**
     * 根据版本ID删除差异
     */
    int deleteByVersionId(@Param("versionId") String versionId);

    /**
     * 根据文件ID删除所有差异
     */
    int deleteByFileId(@Param("fileId") String fileId);
}
