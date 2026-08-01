package com.yaocode.sts.file.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yaocode.sts.file.infrastructure.entity.FileBranchEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文件分支Mapper
 *
 * @author yaocode
 * @since 1.0.0
 */
@Mapper
public interface FileBranchMapper extends BaseMapper<FileBranchEntity> {

    /**
     * 插入分支记录
     */
    int insert(FileBranchEntity entity);

    /**
     * 根据分支ID查询
     */
    FileBranchEntity selectByBranchId(@Param("branchId") String branchId);

    /**
     * 根据文件ID和分支名称查询
     */
    FileBranchEntity selectByFileIdAndBranchName(
            @Param("fileId") String fileId,
            @Param("branchName") String branchName
    );

    /**
     * 根据文件ID查询默认分支
     */
    FileBranchEntity selectDefaultByFileId(@Param("fileId") String fileId);

    /**
     * 根据文件ID查询分支列表
     */
    List<FileBranchEntity> selectByFileId(@Param("fileId") String fileId);

    /**
     * 根据文件ID查询活跃分支列表
     */
    List<FileBranchEntity> selectActiveByFileId(@Param("fileId") String fileId);

    /**
     * 更新分支头版本
     */
    int updateHeadVersion(
            @Param("branchId") String branchId,
            @Param("headVersionId") String headVersionId
    );

    /**
     * 设置默认分支
     */
    int setDefaultBranch(@Param("branchId") String branchId);

    /**
     * 取消默认分支
     */
    int unsetDefaultBranch(@Param("fileId") String fileId);

    /**
     * 更新分支
     */
    int updateById(FileBranchEntity entity);

    /**
     * 删除分支
     */
    int deleteByBranchId(@Param("branchId") String branchId);

    /**
     * 根据文件ID删除所有分支
     */
    int deleteByFileId(@Param("fileId") String fileId);
}
