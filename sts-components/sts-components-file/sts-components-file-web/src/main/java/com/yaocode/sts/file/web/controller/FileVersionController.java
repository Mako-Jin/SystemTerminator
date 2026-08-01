package com.yaocode.sts.file.web.controller;

import com.yaocode.sts.common.web.annotation.SubRequestMapping;
import com.yaocode.sts.common.web.model.ResultModel;
import com.yaocode.sts.common.web.utils.ResultUtils;
import com.yaocode.sts.file.interfaces.api.FileVersionApi;
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
import com.yaocode.sts.file.application.service.FileVersionService;
import com.yaocode.sts.file.web.converter.FileVersionConverter;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 文件版本控制器
 *
 * @author yaocode
 * @since 1.0.0
 */
@RestController
@SubRequestMapping("/v1")
public class FileVersionController implements FileVersionApi {

    @Resource
    private FileVersionService fileVersionService;

    @Resource
    private FileVersionConverter converter;

    // ==================== 版本管理 ====================

    @Override
    public ResultModel<VersionInfoResponse> createVersion(CreateVersionRequest request) {
        CreateVersionCommand command = converter.toCreateVersionCommand(request);
        VersionInfoResult result = fileVersionService.createVersion(command);
        return ResultUtils.ok(converter.toVersionInfoResponse(result));
    }

    @Override
    public ResultModel<VersionHistoryResponse> getVersionHistory(
            String fileId, Integer page, Integer size) {
        VersionHistoryQuery query = VersionHistoryQuery.builder()
                .fileId(fileId)
                .limit(size)
                .offset((page - 1) * size)
                .build();
        VersionHistoryResult result = fileVersionService.getVersionHistory(query);
        return ResultUtils.ok(converter.toVersionHistoryResponse(result));
    }

    @Override
    public ResultModel<VersionDiffResponse> compareVersions(VersionCompareRequest request) {
        VersionCompareCommand command = converter.toVersionCompareCommand(request);
        VersionDiffResult result = fileVersionService.compareVersions(command);
        return ResultUtils.ok(converter.toVersionDiffResponse(result));
    }

    @Override
    public ResultModel<VersionRollbackResponse> rollbackVersion(RollbackVersionRequest request) {
        RollbackVersionCommand command = converter.toRollbackVersionCommand(request);
        VersionRollbackResult result = fileVersionService.rollbackToVersion(command);
        return ResultUtils.ok(converter.toVersionRollbackResponse(result));
    }

    @Override
    public ResultModel<VersionDetailResponse> getVersionDetail(String versionId) {
        VersionDetailQuery query = VersionDetailQuery.builder()
                .versionId(versionId)
                .build();
        VersionDetailResult result = fileVersionService.getVersionDetail(query);
        return ResultUtils.ok(converter.toVersionDetailResponse(result));
    }

    @Override
    public ResultModel<VersionTreeResponse> getVersionTree(String fileId) {
        VersionTreeQuery query = VersionTreeQuery.builder()
                .fileId(fileId)
                .build();
        VersionTreeResult result = fileVersionService.getVersionTree(query);
        return ResultUtils.ok(converter.toVersionTreeResponse(result));
    }

    @Override
    public ResultModel<List<VersionInfoResponse>> getVersionsBatch(VersionsBatchRequest request) {
        VersionsBatchQuery query = converter.toVersionsBatchQuery(request);
        List<VersionInfoResult> results = fileVersionService.getVersionsBatch(query);
        return ResultUtils.ok(converter.toVersionInfoResponseList(results));
    }

    // ==================== 分支管理 ====================

    @Override
    public ResultModel<BranchResponse> createBranch(CreateBranchRequest request) {
        CreateBranchCommand command = converter.toCreateBranchCommand(request);
        BranchResult result = fileVersionService.createBranch(command);
        return ResultUtils.ok(converter.toBranchResponse(result));
    }

    @Override
    public ResultModel<MergeResponse> mergeBranch(MergeBranchRequest request) {
        MergeBranchCommand command = converter.toMergeBranchCommand(request);
        MergeResult result = fileVersionService.mergeBranch(command);
        return ResultUtils.ok(converter.toMergeResponse(result));
    }

    @Override
    public ResultModel<SwitchBranchResponse> switchBranch(SwitchBranchRequest request) {
        SwitchBranchCommand command = converter.toSwitchBranchCommand(request);
        SwitchBranchResult result = fileVersionService.switchBranch(command);
        return ResultUtils.ok(converter.toSwitchBranchResponse(result));
    }

    @Override
    public ResultModel<List<BranchResponse>> getBranches(String fileId) {
        GetBranchesQuery query = GetBranchesQuery.builder()
                .fileId(fileId)
                .build();
        List<BranchResult> results = fileVersionService.getBranches(query);
        return ResultUtils.ok(converter.toBranchResponseList(results));
    }

    @Override
    public ResultModel<String> deleteBranch(DeleteBranchRequest request) {
        DeleteBranchCommand command = converter.toDeleteBranchCommand(request);
        fileVersionService.deleteBranch(command);
        return ResultUtils.ok("删除成功");
    }

    // ==================== 版本标签 ====================

    @Override
    public ResultModel<VersionTagResponse> createTag(CreateVersionTagRequest request) {
        CreateVersionTagCommand command = converter.toCreateVersionTagCommand(request);
        VersionTagResult result = fileVersionService.createTag(command);
        return ResultUtils.ok(converter.toVersionTagResponse(result));
    }

    @Override
    public ResultModel<List<VersionTagResponse>> getTags(String fileId) {
        GetVersionTagsQuery query = GetVersionTagsQuery.builder()
                .fileId(fileId)
                .build();
        List<VersionTagResult> results = fileVersionService.getVersionTags(query);
        return ResultUtils.ok(converter.toVersionTagResponseList(results));
    }

    @Override
    public ResultModel<String> deleteTag(DeleteVersionTagRequest request) {
        DeleteVersionTagCommand command = converter.toDeleteVersionTagCommand(request);
        fileVersionService.deleteTag(command);
        return ResultUtils.ok();
    }
}