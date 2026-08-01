package com.yaocode.sts.file.interfaces.api;

import com.yaocode.sts.common.web.model.ResultModel;
import com.yaocode.sts.file.interfaces.model.request.CreateBranchRequest;
import com.yaocode.sts.file.interfaces.model.request.CreateVersionRequest;
import com.yaocode.sts.file.interfaces.model.request.CreateVersionTagRequest;
import com.yaocode.sts.file.interfaces.model.request.DeleteBranchRequest;
import com.yaocode.sts.file.interfaces.model.request.DeleteVersionTagRequest;
import com.yaocode.sts.file.interfaces.model.request.MergeBranchRequest;
import com.yaocode.sts.file.interfaces.model.request.RollbackVersionRequest;
import com.yaocode.sts.file.interfaces.model.request.SwitchBranchRequest;
import com.yaocode.sts.file.interfaces.model.request.VersionCompareRequest;
import com.yaocode.sts.file.interfaces.model.request.VersionsBatchRequest;
import com.yaocode.sts.file.interfaces.model.response.BranchResponse;
import com.yaocode.sts.file.interfaces.model.response.MergeResponse;
import com.yaocode.sts.file.interfaces.model.response.SwitchBranchResponse;
import com.yaocode.sts.file.interfaces.model.response.VersionDetailResponse;
import com.yaocode.sts.file.interfaces.model.response.VersionDiffResponse;
import com.yaocode.sts.file.interfaces.model.response.VersionHistoryResponse;
import com.yaocode.sts.file.interfaces.model.response.VersionInfoResponse;
import com.yaocode.sts.file.interfaces.model.response.VersionRollbackResponse;
import com.yaocode.sts.file.interfaces.model.response.VersionTagResponse;
import com.yaocode.sts.file.interfaces.model.response.VersionTreeResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 文件版本管理API接口
 * <p>
 * 提供完整的文件版本控制功能，类似Git的版本管理：
 * <ul>
 *   <li>版本创建 - 为文件创建新版本</li>
 *   <li>版本历史 - 查看所有版本记录</li>
 *   <li>版本对比 - 比较两个版本的差异</li>
 *   <li>版本回滚 - 回滚到指定版本</li>
 *   <li>分支管理 - 创建、合并、切换分支</li>
 *   <li>版本标签 - 为重要版本打标签</li>
 * </ul>
 * </p>
 *
 * @author yaocode
 * @since 1.0.0
 */
@RequestMapping("/files/versions")
public interface FileVersionApi {

    // ==================== 版本管理 ====================

    /**
     * 创建新版本
     * <p>
     * 为已存在的文件创建新版本，支持：
     * <ul>
     *   <li>主要版本 (1.0.0 -> 2.0.0)</li>
     *   <li>次要版本 (1.0.0 -> 1.1.0)</li>
     *   <li>补丁版本 (1.0.0 -> 1.0.1)</li>
     * </ul>
     * </p>
     *
     * @param request 创建版本请求
     * @return 版本信息
     */
    @PostMapping("/create")
    ResultModel<VersionInfoResponse> createVersion(@RequestBody @Valid CreateVersionRequest request);

    /**
     * 获取版本历史
     * <p>
     * 返回文件的所有版本记录，按时间倒序排列
     * </p>
     *
     * @param fileId 文件ID
     * @param page   页码
     * @param size   每页数量
     * @return 版本历史
     */
    @GetMapping("/{fileId}/history")
    ResultModel<VersionHistoryResponse> getVersionHistory(
            @PathVariable String fileId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer size
    );

    /**
     * 版本对比
     * <p>
     * 比较两个版本之间的差异，返回：
     * <ul>
     *   <li>变更行数统计</li>
     *   <li>变更百分比</li>
     *   <li>具体差异内容</li>
     * </ul>
     * </p>
     *
     * @param request 版本对比请求
     * @return 差异信息
     */
    @PostMapping("/compare")
    ResultModel<VersionDiffResponse> compareVersions(@RequestBody @Valid VersionCompareRequest request);

    /**
     * 回滚到指定版本
     * <p>
     * 将文件回滚到指定的历史版本，会创建一个新的回滚版本
     * </p>
     *
     * @param request 回滚请求
     * @return 回滚后的版本信息
     */
    @PostMapping("/rollback")
    ResultModel<VersionRollbackResponse> rollbackVersion(@RequestBody @Valid RollbackVersionRequest request);

    /**
     * 获取版本详情
     *
     * @param versionId 版本ID
     * @return 版本详情
     */
    @GetMapping("/{versionId}/detail")
    ResultModel<VersionDetailResponse> getVersionDetail(@PathVariable String versionId);

    /**
     * 获取版本树（可视化版本分支图）
     *
     * @param fileId 文件ID
     * @return 版本树结构
     */
    @GetMapping("/{fileId}/tree")
    ResultModel<VersionTreeResponse> getVersionTree(@PathVariable String fileId);

    /**
     * 批量获取版本信息
     *
     * @param request 批量查询请求
     * @return 版本信息列表
     */
    @PostMapping("/batch")
    ResultModel<List<VersionInfoResponse>> getVersionsBatch(@RequestBody @Valid VersionsBatchRequest request);

    // ==================== 分支管理 ====================

    /**
     * 创建分支
     * <p>
     * 从当前分支或指定分支创建一个新分支
     * </p>
     *
     * @param request 创建分支请求
     * @return 分支信息
     */
    @PostMapping("/branch/create")
    ResultModel<BranchResponse> createBranch(@RequestBody @Valid CreateBranchRequest request);

    /**
     * 合并分支
     * <p>
     * 将源分支合并到目标分支，支持：
     * <ul>
     *   <li>快速合并（Fast-forward）</li>
     *   <li>三方合并（3-way merge）</li>
     * </ul>
     * </p>
     *
     * @param request 合并分支请求
     * @return 合并结果
     */
    @PostMapping("/branch/merge")
    ResultModel<MergeResponse> mergeBranch(@RequestBody @Valid MergeBranchRequest request);

    /**
     * 切换分支
     * <p>
     * 切换到指定分支，文件内容会切换到该分支的最新版本
     * </p>
     *
     * @param request 切换分支请求
     * @return 切换结果
     */
    @PostMapping("/branch/switch")
    ResultModel<SwitchBranchResponse> switchBranch(@RequestBody @Valid SwitchBranchRequest request);

    /**
     * 获取分支列表
     *
     * @param fileId 文件ID
     * @return 分支列表
     */
    @GetMapping("/{fileId}/branches")
    ResultModel<List<BranchResponse>> getBranches(@PathVariable String fileId);

    /**
     * 删除分支
     *
     * @param request 删除分支请求
     * @return 操作结果
     */
    @DeleteMapping("/branch/delete")
    ResultModel<String> deleteBranch(@RequestBody @Valid DeleteBranchRequest request);

    // ==================== 版本标签 ====================

    /**
     * 创建版本标签
     * <p>
     * 为指定版本打标签，便于识别重要版本（如 v1.0-release）
     * </p>
     *
     * @param request 创建标签请求
     * @return 标签信息
     */
    @PostMapping("/tag/create")
    ResultModel<VersionTagResponse> createTag(@RequestBody @Valid CreateVersionTagRequest request);

    /**
     * 获取版本标签列表
     *
     * @param fileId 文件ID
     * @return 标签列表
     */
    @GetMapping("/{fileId}/tags")
    ResultModel<List<VersionTagResponse>> getTags(@PathVariable String fileId);

    /**
     * 删除版本标签
     *
     * @param request 删除标签请求
     * @return 操作结果
     */
    @DeleteMapping("/tag/delete")
    ResultModel<String> deleteTag(@RequestBody @Valid DeleteVersionTagRequest request);
}
