package com.yaocode.sts.file.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yaocode.sts.file.infrastructure.entity.FileVersionEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文件版本Mapper
 *
 * @author yaocode
 * @since 1.0.0
 */
@Mapper
public interface FileVersionMapper extends BaseMapper<FileVersionEntity> {

    /**
     * 插入版本记录
     */
    int insert(FileVersionEntity entity);

    /**
     * 根据版本ID查询
     */
    FileVersionEntity selectByVersionId(@Param("versionId") String versionId);

    /**
     * 根据文件ID查询最新版本
     */
    FileVersionEntity selectLatestByFileId(@Param("fileId") String fileId);

    /**
     * 根据文件ID查询当前版本
     */
    FileVersionEntity selectCurrentByFileId(@Param("fileId") String fileId);

    /**
     * 根据文件ID查询版本列表（按时间倒序）
     */
    List<FileVersionEntity> selectByFileIdOrderByTime(
            @Param("fileId") String fileId,
            @Param("limit") Integer limit,
            @Param("offset") Integer offset
    );

    /**
     * 根据文件ID查询所有版本（按时间倒序）
     */
    List<FileVersionEntity> selectAllByFileId(@Param("fileId") String fileId);

    /**
     * 根据版本ID列表批量查询
     */
    List<FileVersionEntity> selectByVersionIds(@Param("versionIds") List<String> versionIds);

    /**
     * 根据文件ID和分支ID查询版本列表
     */
    List<FileVersionEntity> selectByFileIdAndBranch(
            @Param("fileId") String fileId,
            @Param("branchId") String branchId
    );

    /**
     * 统计文件版本数量
     */
    Long countByFileId(@Param("fileId") String fileId);

    /**
     * 更新最新版本标记
     */
    int updateLatestFlag(
            @Param("versionId") String versionId,
            @Param("isLatest") Boolean isLatest
    );

    /**
     * 更新当前版本标记
     */
    int updateCurrentFlag(
            @Param("versionId") String versionId,
            @Param("isCurrent") Boolean isCurrent
    );

    /**
     * 根据文件ID更新当前版本标记
     */
    int updateCurrentFlagByFileId(
            @Param("fileId") String fileId,
            @Param("isCurrent") Boolean isCurrent
    );

    /**
     * 根据文件ID获取最大版本号
     */
    Integer getMaxVersionByFileId(@Param("fileId") String fileId, @Param("tenantId") String tenantId);

    /**
     * 根据分支ID获取最大版本号
     */
    Integer getMaxVersionByBranchId(@Param("branchId") String branchId);

    /**
     * 获取版本链（从指定版本到根）
     */
    List<FileVersionEntity> selectVersionChain(@Param("versionId") String versionId);

    /**
     * 获取两个版本之间的差异路径
     */
    List<FileVersionEntity> selectPathBetweenVersions(
            @Param("fromVersionId") String fromVersionId,
            @Param("toVersionId") String toVersionId
    );

    /**
     * 查询指定时间之前的版本（用于归档）
     */
    List<FileVersionEntity> selectOlderThan(@Param("dateTime") java.time.LocalDateTime dateTime);

    /**
     * 软删除版本
     */
    int softDelete(@Param("versionId") String versionId);

    /**
     * 物理删除版本
     */
    int deleteByVersionId(@Param("versionId") String versionId);

    /**
     * 根据文件ID物理删除所有版本
     */
    int deleteByFileId(@Param("fileId") String fileId);
}
