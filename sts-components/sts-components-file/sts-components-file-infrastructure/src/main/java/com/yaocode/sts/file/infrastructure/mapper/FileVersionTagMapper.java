package com.yaocode.sts.file.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yaocode.sts.file.infrastructure.entity.FileVersionTagEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文件版本标签Mapper
 *
 * @author yaocode
 * @since 1.0.0
 */
@Mapper
public interface FileVersionTagMapper extends BaseMapper<FileVersionTagEntity> {

    /**
     * 插入标签记录
     */
    int insert(FileVersionTagEntity entity);

    /**
     * 根据标签ID查询
     */
    FileVersionTagEntity selectByTagId(@Param("tagId") String tagId);

    /**
     * 根据文件ID和标签名称查询
     */
    FileVersionTagEntity selectByFileIdAndTagName(
            @Param("fileId") String fileId,
            @Param("tagName") String tagName
    );

    /**
     * 根据版本ID查询标签列表
     */
    List<FileVersionTagEntity> selectByVersionId(@Param("versionId") String versionId);

    /**
     * 根据文件ID查询标签列表
     */
    List<FileVersionTagEntity> selectByFileId(@Param("fileId") String fileId);

    /**
     * 根据文件ID和标签类型查询
     */
    List<FileVersionTagEntity> selectByFileIdAndType(
            @Param("fileId") String fileId,
            @Param("tagType") Integer tagType
    );

    /**
     * 删除标签
     */
    int deleteByTagId(@Param("tagId") String tagId);

    /**
     * 根据版本ID删除所有标签
     */
    int deleteByVersionId(@Param("versionId") String versionId);

    /**
     * 根据文件ID删除所有标签
     */
    int deleteByFileId(@Param("fileId") String fileId);
}
