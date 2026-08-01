package com.yaocode.sts.file.application.service;

import com.yaocode.sts.file.application.model.command.CreateBranchCommand;
import com.yaocode.sts.file.application.model.command.CreateVersionCommand;
import com.yaocode.sts.file.application.model.command.CreateVersionTagCommand;
import com.yaocode.sts.file.application.model.command.DeleteBranchCommand;
import com.yaocode.sts.file.application.model.command.DeleteVersionTagCommand;
import com.yaocode.sts.file.application.model.command.MergeBranchCommand;
import com.yaocode.sts.file.application.model.command.RollbackVersionCommand;
import com.yaocode.sts.file.application.model.command.SwitchBranchCommand;
import com.yaocode.sts.file.application.model.command.VersionCompareCommand;
import com.yaocode.sts.file.application.model.query.GetBranchesQuery;
import com.yaocode.sts.file.application.model.query.GetVersionTagsQuery;
import com.yaocode.sts.file.application.model.query.VersionDetailQuery;
import com.yaocode.sts.file.application.model.query.VersionHistoryQuery;
import com.yaocode.sts.file.application.model.query.VersionTreeQuery;
import com.yaocode.sts.file.application.model.query.VersionsBatchQuery;
import com.yaocode.sts.file.application.model.result.BranchResult;
import com.yaocode.sts.file.application.model.result.MergeResult;
import com.yaocode.sts.file.application.model.result.SwitchBranchResult;
import com.yaocode.sts.file.application.model.result.VersionDetailResult;
import com.yaocode.sts.file.application.model.result.VersionDiffResult;
import com.yaocode.sts.file.application.model.result.VersionHistoryResult;
import com.yaocode.sts.file.application.model.result.VersionInfoResult;
import com.yaocode.sts.file.application.model.result.VersionRollbackResult;
import com.yaocode.sts.file.application.model.result.VersionTagResult;
import com.yaocode.sts.file.application.model.result.VersionTreeResult;

import java.util.List;

/**
 * 文件版本管理服务（统一接口）
 * <p>
 * 整合了版本创建、查询、回滚、分支管理、标签管理等所有版本相关功能
 * </p>
 *
 * @author yaocode
 * @since 1.0.0
 */
public interface FileVersionService {

    // ==================== 版本管理 ====================

    /**
     * 创建新版本
     */
    VersionInfoResult createVersion(CreateVersionCommand command);

    /**
     * 获取版本历史
     */
    VersionHistoryResult getVersionHistory(VersionHistoryQuery query);

    /**
     * 版本对比
     */
    VersionDiffResult compareVersions(VersionCompareCommand command);

    /**
     * 回滚到指定版本
     */
    VersionRollbackResult rollbackToVersion(RollbackVersionCommand command);

    /**
     * 获取版本详情
     */
    VersionDetailResult getVersionDetail(VersionDetailQuery query);

    /**
     * 获取版本树
     */
    VersionTreeResult getVersionTree(VersionTreeQuery query);

    /**
     * 批量获取版本信息
     */
    List<VersionInfoResult> getVersionsBatch(VersionsBatchQuery query);

    // ==================== 分支管理 ====================

    /**
     * 创建分支
     */
    BranchResult createBranch(CreateBranchCommand command);

    /**
     * 合并分支
     */
    MergeResult mergeBranch(MergeBranchCommand command);

    /**
     * 切换分支
     */
    SwitchBranchResult switchBranch(SwitchBranchCommand command);

    /**
     * 获取分支列表
     */
    List<BranchResult> getBranches(GetBranchesQuery query);

    /**
     * 删除分支
     */
    void deleteBranch(DeleteBranchCommand command);

    // ==================== 版本标签 ====================

    /**
     * 创建版本标签
     */
    VersionTagResult createTag(CreateVersionTagCommand command);

    /**
     * 获取版本标签列表
     */
    List<VersionTagResult> getVersionTags(GetVersionTagsQuery query);

    /**
     * 删除版本标签
     */
    void deleteTag(DeleteVersionTagCommand command);
}
