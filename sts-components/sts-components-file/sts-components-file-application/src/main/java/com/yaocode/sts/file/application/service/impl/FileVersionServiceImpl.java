package com.yaocode.sts.file.application.service.impl;

import com.yaocode.sts.common.basic.exception.BusinessException;
import com.yaocode.sts.common.tools.StringUtils;
import com.yaocode.sts.common.tools.id.IdFactory;
import com.yaocode.sts.common.tools.id.IdGeneratorType;
import com.yaocode.sts.file.application.converter.FileVersionApplicationConverter;
import com.yaocode.sts.file.core.spi.StoragePlugin;
import com.yaocode.sts.file.infrastructure.dao.FileBaseInfoDao;
import com.yaocode.sts.file.infrastructure.entity.FileBranchEntity;
import com.yaocode.sts.file.infrastructure.entity.FileInfoEntity;
import com.yaocode.sts.file.infrastructure.entity.FileVersionEntity;
import com.yaocode.sts.file.infrastructure.entity.FileVersionTagEntity;
import com.yaocode.sts.file.infrastructure.mapper.FileBranchMapper;
import com.yaocode.sts.file.infrastructure.mapper.FileVersionDiffMapper;
import com.yaocode.sts.file.infrastructure.mapper.FileVersionMapper;
import com.yaocode.sts.file.infrastructure.mapper.FileVersionTagMapper;
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
import com.yaocode.sts.file.core.utils.DiffUtils;
import jakarta.annotation.Resource;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * 文件版本管理服务实现（统一实现）
 *
 * @author yaocode
 * @since 1.0.0
 */
@Slf4j
@Service
public class FileVersionServiceImpl implements FileVersionService {

    @Resource
    private FileVersionMapper fileVersionMapper;
    @Resource
    private FileBaseInfoDao fileBaseInfoDao;
    @Resource
    private FileBranchMapper fileBranchMapper;
    @Resource
    private FileVersionTagMapper fileVersionTagMapper;
    @Resource
    private FileVersionDiffMapper fileVersionDiffMapper;
    @Resource
    private StoragePlugin storagePlugin;
    @Resource
    private FileVersionApplicationConverter fileVersionApplicationConverter;

    // ==================== 版本管理实现 ====================

    @Override
    @Transactional(rollbackFor = Exception.class)
    public VersionInfoResult createVersion(CreateVersionCommand command) {
        log.info("创建文件版本: fileId={}, remark={}", command.getFileId(), command.getVersionRemark());
//
//        // 1. 获取文件信息
//        FileInfoEntity fileInfo = fileInfoMapper.selectByFileIdAndTenant(
//                command.getFileId(), command.getTenantId()
//        );
//        if (fileInfo == null) {
//            throw new BusinessException("FILE_NOT_FOUND", "file.not.found");
//        }
//
//        // 2. 检查版本控制是否启用
//        if (fileInfo.getVersionControlEnabled() == null || !fileInfo.getVersionControlEnabled()) {
//            throw new BusinessException("VERSION_CONTROL_DISABLED", "file.version.control.disabled");
//        }
//
//        // 3. 获取当前最新版本
//        FileVersionEntity currentVersion = fileVersionMapper.selectLatestByFileId(command.getFileId());
//
//        // 4. 检查内容是否有变化
//        if (currentVersion != null && currentVersion.getFileMd5().equals(command.getFileMd5())) {
//            throw new BusinessException("FILE_CONTENT_UNCHANGED", "file.content.unchanged");
//        }
//
//        // 5. 确定版本号
//        int newVersionNumber = currentVersion == null ? 1 : currentVersion.getVersionNumber() + 1;
//        String versionTag = fileVersionApplicationConverter.generateVersionTag(newVersionNumber, command.getVersionType());
//
//        // 6. 确定分支
//        String branchId = command.getBranchId();
//        if (!StringUtils.hasText(branchId)) {
//            FileBranchEntity defaultBranch = fileBranchMapper.selectDefaultByFileId(command.getFileId());
//            if (defaultBranch == null) {
//                defaultBranch = createDefaultBranch(fileInfo, command.getUserId());
//            }
//            branchId = defaultBranch.getBranchId();
//        }
//
//        // 7. 存储文件
//        String filePath = storagePlugin.upload(
//                command.getFileContent(),
//                command.getFileName(),
//                command.getFileSize(),
//                command.getTenantId(),
//                "default"
//        );
//        String fileUrl = storagePlugin.getFileUrl(filePath);
//
//        // 8. 创建版本记录
//        String versionId = IdFactory.generate(IdGeneratorType.UUID);
//        String parentVersionId = currentVersion != null ? currentVersion.getVersionId() : null;
//
//        FileVersionEntity version = fileVersionApplicationConverter.buildVersionEntity(
//                command, versionId, filePath, fileUrl, versionTag, parentVersionId, branchId
//        );
//        version.setVersionNumber(newVersionNumber);
//        fileVersionMapper.insert(version);
//
//        // 9. 更新分支头
//        fileBranchMapper.updateHeadVersion(branchId, versionId);
//
//        // 10. 更新文件信息
//        fileInfo.setFileMd5(command.getFileMd5());
//        fileInfo.setFileSha256(command.getFileSha256());
//        fileInfo.setFileSize(command.getFileSize());
//        fileInfo.setFilePath(filePath);
//        fileInfo.setStorageUrl(fileUrl);
//        fileInfo.setUpdatedTime(LocalDateTime.now());
//        fileInfoMapper.updateById(fileInfo);
//
//        // 11. 标记之前的版本
//        if (currentVersion != null) {
//            fileVersionMapper.updateLatestFlag(currentVersion.getVersionId(), false);
//        }
//
//        // 12. 设置当前版本
//        if (command.getSetAsCurrent() != null && command.getSetAsCurrent()) {
//            fileVersionMapper.updateCurrentFlagByFileId(command.getFileId(), false);
//            fileVersionMapper.updateCurrentFlag(versionId, true);
//        }
//
//        // 13. 计算版本差异
//        if (currentVersion != null && command.getFileContent() != null) {
//            FileVersionDiffEntity diff = fileVersionApplicationConverter.toFileVersionDiffEntity(
//                    currentVersion.getVersionId(), versionId, 0.0
//            );
//            fileVersionDiffMapper.insert(diff);
//        }
//
//        // 14. 构建返回结果
//        VersionInfoResult result = fileVersionApplicationConverter.toVersionInfoResult(version);
//        result.setBranchName(getBranchName(branchId));
//        return result;
        return null;
    }

    @Override
    public VersionHistoryResult getVersionHistory(VersionHistoryQuery query) {
        log.info("获取版本历史: fileId={}, limit={}, offset={}",
                query.getFileId(), query.getLimit(), query.getOffset());

        // 1. 获取版本列表
//        List<FileVersionEntity> versions = fileVersionMapper.selectByFileIdOrderByTime(
//                query.getFileId(), query.getLimit(), query.getOffset()
//        );
//
//        // 2. 获取分支信息
//        List<FileBranchEntity> branches = fileBranchMapper.selectByFileId(query.getFileId());
//
//        // 3. 预加载分支名称映射
//        Map<String, String> branchNameMap = buildBranchNameMap(branches);
//
//        // 4. 构建版本历史项
//        List<VersionHistoryItemDto> items = versions.stream().map(v -> {
//            List<FileVersionTagEntity> tags = fileVersionTagMapper.selectByVersionId(v.getVersionId());
//            List<String> parents = buildParentList(v);
//            String branchName = branchNameMap.get(v.getBranchId());
//            return fileVersionApplicationConverter.toVersionHistoryItem(v, tags, branchName, parents);
//        }).collect(Collectors.toList());
//
//        // 5. 构建分支信息
//        Map<String, Integer> versionNumberMap = buildVersionNumberMap(versions);
//        List<BranchInfoDto> branchInfos = fileVersionApplicationConverter.toBranchInfoList(branches, versionNumberMap);
//
//        // 6. 计算总数量
//        Long total = fileVersionMapper.countByFileId(query.getFileId());
//
//        return VersionHistoryResult.builder()
//                .fileId(query.getFileId())
//                .fileName(getFileName(query.getTenantId(), query.getFileId()))
//                .total(total)
//                .items(items)
//                .branches(branchInfos)
//                .build();
        return null;
    }

    @Override
    public VersionDiffResult compareVersions(VersionCompareCommand command) {
        log.info("版本对比: versionId1={}, versionId2={}",
                command.getVersionId1(), command.getVersionId2());

//        FileVersionEntity version1 = fileVersionMapper.selectByVersionId(command.getVersionId1());
//        FileVersionEntity version2 = fileVersionMapper.selectByVersionId(command.getVersionId2());
//
//        if (version1 == null || version2 == null) {
//            throw new BusinessException("VERSION_NOT_FOUND", "file.version.not.found");
//        }
//
//        boolean isSameVersion = version1.getVersionId().equals(version2.getVersionId());
//
//        if (isSameVersion) {
//            return fileVersionApplicationConverter.toVersionDiffResult(version1, version2, null, true);
//        }
//
//        byte[] content1 = downloadFileContent(version1.getFilePath());
//        byte[] content2 = downloadFileContent(version2.getFilePath());
//        DiffResult diff = DiffUtils.calculateDiff(content1, content2);
//
//        return fileVersionApplicationConverter.toVersionDiffResult(version1, version2, diff, false);
        return null;
    }

    /**
     * 从存储下载文件内容
     */
    private byte[] downloadFileContent(String filePath) {
        try (InputStream inputStream = storagePlugin.download(filePath)) {
            return DiffUtils.readInputStream(inputStream);
        } catch (IOException e) {
            log.error("下载文件内容失败: filePath={}", filePath, e);
            throw new BusinessException("FILE_DOWNLOAD_ERROR", "file.download.error", e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public VersionRollbackResult rollbackToVersion(RollbackVersionCommand command) {
        log.info("回滚文件版本: fileId={}, targetVersionId={}", command.getFileId(), command.getTargetVersionId());

        // 1. 获取目标版本
//        FileVersionEntity targetVersion = fileVersionMapper.selectByVersionId(command.getTargetVersionId());
//        if (targetVersion == null) {
//            throw new BusinessException("VERSION_NOT_FOUND", "file.version.not.found");
//        }
//
//        // 2. 检查权限
//        if (!hasRollbackPermission(command)) {
//            throw new BusinessException("ROLLBACK_PERMISSION_DENIED", "file.rollback.permission.denied");
//        }
//
//        // 3. 检查是否已是最新版本
//        if (targetVersion.getIsLatest()) {
//            throw new BusinessException("ALREADY_LATEST_VERSION", "file.version.already.latest");
//        }
//
//        // 4. 获取当前版本
//        FileVersionEntity currentVersion = fileVersionMapper.selectLatestByFileId(command.getFileId());
//        if (currentVersion == null) {
//            throw new BusinessException("CURRENT_VERSION_NOT_FOUND", "file.current.version.not.found");
//        }
//
//        // 5. 获取目标版本文件内容
//        InputStream content = storagePlugin.download(targetVersion.getFilePath());
//
//        // 6. 创建回滚版本
//        String newVersionId = IdFactory.generate(IdGeneratorType.UUID);
//        String versionTag = fileVersionApplicationConverter.generateRollbackVersionTag(targetVersion.getVersionNumber());
//
//        String filePath = storagePlugin.upload(
//                content,
//                getFileName(command.getTenantId(), targetVersion.getFileId()),
//                targetVersion.getFileSize(),
//                command.getTenantId(),
//                "default"
//        );
//        String fileUrl = storagePlugin.getFileUrl(filePath);
//
//        String versionRemark = fileVersionApplicationConverter.generateRollbackRemark(
//                targetVersion.getVersionNumber(), command.getRollbackReason()
//        );
//
//        FileVersionEntity rollbackVersion = FileVersionEntity.builder()
//                .versionId(newVersionId)
//                .fileId(command.getFileId())
//                .versionNumber(currentVersion.getVersionNumber() + 1)
//                .versionType(3)
//                .versionTag(versionTag)
//                .versionRemark(versionRemark)
//                .changeSummary(String.format("回滚到版本 %s", targetVersion.getVersionTag()))
//                .filePath(filePath)
//                .fileSize(targetVersion.getFileSize())
//                .fileMd5(targetVersion.getFileMd5())
//                .fileSha256(targetVersion.getFileSha256())
//                .storageUrl(fileUrl)
//                .parentVersionId(currentVersion.getVersionId())
//                .branchId(targetVersion.getBranchId())
//                .isCurrent(true)
//                .isLatest(true)
//                .createdUserId(command.getUserId())
//                .createdUserName(command.getUserName())
//                .createdTime(LocalDateTime.now())
//                .updatedTime(LocalDateTime.now())
//                .build();
//
//        fileVersionMapper.insert(rollbackVersion);
//
//        // 7. 更新文件信息
//        FileInfoEntity fileInfo = fileInfoMapper.selectByFileIdAndTenant(
//                command.getFileId(), command.getTenantId()
//        );
//        if (fileInfo != null) {
//            fileInfo.setFileMd5(targetVersion.getFileMd5());
//            fileInfo.setFileSha256(targetVersion.getFileSha256());
//            fileInfo.setFileSize(targetVersion.getFileSize());
//            fileInfo.setFilePath(filePath);
//            fileInfo.setStorageUrl(fileUrl);
//            fileInfo.setUpdatedTime(LocalDateTime.now());
//            fileInfoMapper.updateById(fileInfo);
//        }
//
//        // 8. 更新分支头
//        fileBranchMapper.updateHeadVersion(targetVersion.getBranchId(), newVersionId);
//
//        // 9. 标记版本状态
//        fileVersionMapper.updateLatestFlag(currentVersion.getVersionId(), false);
//        fileVersionMapper.updateCurrentFlagByFileId(command.getFileId(), false);
//        fileVersionMapper.updateCurrentFlag(newVersionId, true);
//
//        // 10. 计算差异统计
//        VersionRollbackResult.RollbackDiffStats diffStats =
//                fileVersionApplicationConverter.calculateRollbackDiffStats(currentVersion, targetVersion);
//
//        // 11. 构建返回结果
//        return fileVersionApplicationConverter.toVersionRollbackResult(
//                rollbackVersion,
//                currentVersion,
//                targetVersion,
//                command.getRollbackReason(),
//                command.getUserName(),
//                getBranchName(targetVersion.getBranchId()),
//                diffStats
//        );
        return null;
    }

    @Override
    public VersionDetailResult getVersionDetail(VersionDetailQuery query) {
        log.info("获取版本详情: versionId={}", query.getVersionId());

//        FileVersionEntity version = fileVersionMapper.selectByVersionId(query.getVersionId());
//        if (version == null) {
//            throw new BusinessException("VERSION_NOT_FOUND", "file.version.not.found");
//        }
//
//        return fileVersionApplicationConverter.toVersionDetailResult(version, getBranchName(version.getBranchId()));
        return null;
    }

    @Override
    public VersionTreeResult getVersionTree(VersionTreeQuery query) {
        log.info("获取版本树: fileId={}", query.getFileId());

        // 1. 获取所有版本
        List<FileVersionEntity> versions = fileVersionMapper.selectByFileIdOrderByTime(
                query.getFileId(), Integer.MAX_VALUE, 0
        );

        // 2. 获取分支信息
        List<FileBranchEntity> branches = fileBranchMapper.selectByFileId(query.getFileId());

        // 3. 构建版本树
        return buildVersionTree(versions, branches);
    }

    @Override
    public List<VersionInfoResult> getVersionsBatch(VersionsBatchQuery query) {
        log.info("批量获取版本信息: size={}",
                query.getVersionIds() != null ? query.getVersionIds().size() : 0);

        if (query.getVersionIds() == null || query.getVersionIds().isEmpty()) {
            return Collections.emptyList();
        }

        List<FileVersionEntity> versions = fileVersionMapper.selectByVersionIds(query.getVersionIds());
        return fileVersionApplicationConverter.toVersionInfoResultList(versions);
    }

    // ==================== 分支管理实现 ====================

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BranchResult createBranch(CreateBranchCommand command) {
        log.info("创建文件分支: fileId={}, branchName={}",
                command.getFileId(), command.getBranchName());

//        // 1. 检查分支名是否已存在
//        FileBranchEntity existing = fileBranchMapper.selectByFileIdAndBranchName(
//                command.getFileId(), command.getBranchName()
//        );
//        if (existing != null) {
//            throw new BusinessException("BRANCH_ALREADY_EXISTS", "file.branch.already.exists");
//        }
//
//        // 2. 获取源分支
//        String sourceBranchId = command.getSourceBranchId();
//        FileBranchEntity sourceBranch;
//        if (StringUtils.hasText(sourceBranchId)) {
//            sourceBranch = fileBranchMapper.selectByBranchId(sourceBranchId);
//            if (sourceBranch == null) {
//                throw new BusinessException("SOURCE_BRANCH_NOT_FOUND", "file.source.branch.not.found");
//            }
//        } else {
//            sourceBranch = fileBranchMapper.selectDefaultByFileId(command.getFileId());
//            if (sourceBranch == null) {
//                throw new BusinessException("DEFAULT_BRANCH_NOT_FOUND", "file.default.branch.not.found");
//            }
//        }
//
//        // 3. 获取源分支头版本
//        FileVersionEntity headVersion = fileVersionMapper.selectByVersionId(sourceBranch.getHeadVersionId());
//
//        // 4. 创建新分支
//        String branchId = IdFactory.generate(IdGeneratorType.UUID);
//        FileBranchEntity branch = fileVersionApplicationConverter.buildBranchEntity(
//                command, branchId, sourceBranch.getHeadVersionId(), sourceBranch.getBranchId()
//        );
//        fileBranchMapper.insert(branch);
//
//        // 5. 构建返回结果
//        return fileVersionApplicationConverter.toCreateBranchResult(branch, headVersion, sourceBranch.getBranchName());
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MergeResult mergeBranch(MergeBranchCommand command) {
        log.info("合并文件分支: fromBranch={}, toBranch={}",
                command.getFromBranchId(), command.getToBranchId());

//        FileBranchEntity fromBranch = fileBranchMapper.selectByBranchId(command.getFromBranchId());
//        FileBranchEntity toBranch = fileBranchMapper.selectByBranchId(command.getToBranchId());
//
//        if (fromBranch == null || toBranch == null) {
//            throw new BusinessException("BRANCH_NOT_FOUND", "file.branch.not.found");
//        }
//
//        if (!fromBranch.getFileId().equals(toBranch.getFileId())) {
//            throw new BusinessException("BRANCH_NOT_SAME_FILE", "file.branch.not.same.file");
//        }
//
//        FileVersionEntity fromHead = fileVersionMapper.selectByVersionId(fromBranch.getHeadVersionId());
//        FileVersionEntity toHead = fileVersionMapper.selectByVersionId(toBranch.getHeadVersionId());
//
//        if (fromHead == null || toHead == null) {
//            throw new BusinessException("BRANCH_HEAD_NOT_FOUND", "file.branch.head.not.found");
//        }
//
//        // 快速合并
//        boolean isFastForward = isFastForward(fromHead, toHead);
//        if (isFastForward) {
//            fileBranchMapper.updateHeadVersion(toBranch.getBranchId(), fromHead.getVersionId());
//            fileVersionMapper.updateCurrentFlagByFileId(command.getFileId(), false);
//            fileVersionMapper.updateCurrentFlag(fromHead.getVersionId(), true);
//
//            // 更新文件信息
//            FileInfoEntity fileInfo = fileInfoMapper.selectByFileIdAndTenant(
//                    command.getFileId(), command.getTenantId()
//            );
//            if (fileInfo != null) {
//                fileInfo.setFilePath(fromHead.getFilePath());
//                fileInfo.setStorageUrl(fromHead.getStorageUrl());
//                fileInfo.setFileMd5(fromHead.getFileMd5());
//                fileInfo.setFileSha256(fromHead.getFileSha256());
//                fileInfo.setFileSize(fromHead.getFileSize());
//                fileInfo.setUpdatedTime(LocalDateTime.now());
//                fileInfoMapper.updateById(fileInfo);
//            }
//
//            return fileVersionApplicationConverter.toMergeResult(
//                    "FAST_FORWARD",
//                    toBranch.getBranchId(),
//                    toBranch.getBranchName(),
//                    fromHead.getVersionId(),
//                    fromHead.getVersionNumber(),
//                    fromHead.getVersionTag(),
//                    false,
//                    null,
//                    "快速合并成功"
//            );
//        }
//
//        // 三方合并
//        return performThreeWayMerge(fromBranch, toBranch, fromHead, toHead, command);
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SwitchBranchResult switchBranch(SwitchBranchCommand command) {
        log.info("切换分支: fileId={}, targetBranchId={}",
                command.getFileId(), command.getTargetBranchId());

//        FileBranchEntity targetBranch = fileBranchMapper.selectByBranchId(command.getTargetBranchId());
//        if (targetBranch == null) {
//            throw new BusinessException("BRANCH_NOT_FOUND", "file.branch.not.found");
//        }
//
//        FileVersionEntity headVersion = fileVersionMapper.selectByVersionId(targetBranch.getHeadVersionId());
//        if (headVersion == null) {
//            throw new BusinessException("BRANCH_HEAD_NOT_FOUND", "file.branch.head.not.found");
//        }
//
//        FileInfoEntity fileInfo = fileInfoMapper.selectByFileIdAndTenant(
//                command.getFileId(), command.getTenantId()
//        );
//        if (fileInfo == null) {
//            throw new BusinessException("FILE_NOT_FOUND", "file.not.found");
//        }
//
//        // 更新文件信息
//        fileInfo.setFilePath(headVersion.getFilePath());
//        fileInfo.setStorageUrl(headVersion.getStorageUrl());
//        fileInfo.setFileMd5(headVersion.getFileMd5());
//        fileInfo.setFileSha256(headVersion.getFileSha256());
//        fileInfo.setFileSize(headVersion.getFileSize());
//        fileInfo.setUpdatedTime(LocalDateTime.now());
//        fileInfoMapper.updateById(fileInfo);
//
//        // 更新当前版本标记
//        fileVersionMapper.updateCurrentFlagByFileId(command.getFileId(), false);
//        fileVersionMapper.updateCurrentFlag(headVersion.getVersionId(), true);
//
//        String message = fileVersionApplicationConverter.generateSwitchBranchMessage(targetBranch.getBranchName());
//        return fileVersionApplicationConverter.toSwitchBranchResult(targetBranch, headVersion, message);
        return null;
    }

    @Override
    public List<BranchResult> getBranches(GetBranchesQuery query) {
        log.info("获取分支列表: fileId={}", query.getFileId());

//        List<FileBranchEntity> branches = fileBranchMapper.selectByFileId(query.getFileId());
//
//        if (query.getIncludeInactive() == null || !query.getIncludeInactive()) {
//            branches = branches.stream()
//                    .filter(FileBranchEntity::getIsActive)
//                    .collect(Collectors.toList());
//        }
//
//        // 预加载版本和分支名称
//        Map<String, FileVersionEntity> versionMap = buildVersionMap(branches);
//        Map<String, String> branchNameMap = buildBranchNameMap(branches);
//
//        return fileVersionApplicationConverter.toBranchResultList(branches, versionMap, branchNameMap);
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBranch(DeleteBranchCommand command) {
        log.info("删除分支: branchId={}", command.getBranchId());

        FileBranchEntity branch = fileBranchMapper.selectByBranchId(command.getBranchId());
        if (branch == null) {
            throw new BusinessException("BRANCH_NOT_FOUND", "file.branch.not.found");
        }

        if (branch.getIsDefault()) {
            throw new BusinessException("CANNOT_DELETE_DEFAULT_BRANCH", "file.branch.cannot.delete.default");
        }

        branch.setIsActive(false);
        branch.setUpdatedTime(LocalDateTime.now());
        fileBranchMapper.updateById(branch);
    }

    // ==================== 版本标签实现 ====================

    @Override
    @Transactional(rollbackFor = Exception.class)
    public VersionTagResult createTag(CreateVersionTagCommand command) {
//        log.info("创建版本标签: fileId={}, tagName={}", command.getFileId(), command.getTagName());
//
//        FileVersionEntity version = fileVersionMapper.selectByVersionId(command.getVersionId());
//        if (version == null) {
//            throw new BusinessException("VERSION_NOT_FOUND", "file.version.not.found");
//        }
//
//        FileVersionTagEntity existing = fileVersionTagMapper.selectByFileIdAndTagName(
//                command.getFileId(), command.getTagName()
//        );
//        if (existing != null) {
//            throw new BusinessException("TAG_ALREADY_EXISTS", "file.tag.already.exists");
//        }
//
//        String tagId = IdFactory.generate(IdGeneratorType.UUID);
//        FileVersionTagEntity tag = fileVersionApplicationConverter.buildVersionTagEntity(command, tagId);
//        fileVersionTagMapper.insert(tag);
//
//        return fileVersionApplicationConverter.toVersionTagResult(tag, version);
        return null;
    }

    @Override
    public List<VersionTagResult> getVersionTags(GetVersionTagsQuery query) {
//        log.info("获取版本标签列表: fileId={}", query.getFileId());
//
//        List<FileVersionTagEntity> tags = fileVersionTagMapper.selectByFileId(query.getFileId());
//
//        if (query.getTagType() != null) {
//            tags = tags.stream()
//                    .filter(t -> t.getTagType().equals(query.getTagType()))
//                    .collect(Collectors.toList());
//        }
//
//        // 预加载版本信息
//        Map<String, FileVersionEntity> versionMap = buildVersionMapFromTags(tags);
//
//        return fileVersionApplicationConverter.toVersionTagResultList(tags, versionMap);
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteTag(DeleteVersionTagCommand command) {
        log.info("删除版本标签: tagId={}", command.getTagId());

        FileVersionTagEntity tag = fileVersionTagMapper.selectByTagId(command.getTagId());
        if (tag == null) {
            throw new BusinessException("TAG_NOT_FOUND", "file.tag.not.found");
        }

        fileVersionTagMapper.deleteByTagId(command.getTagId());
    }

    // ==================== 私有辅助方法 ====================

    /**
     * 创建默认分支
     */
    private FileBranchEntity createDefaultBranch(FileInfoEntity fileInfo, String userId) {
        String branchId = IdFactory.generate(IdGeneratorType.UUID);
        FileBranchEntity branch = FileBranchEntity.builder()
                .branchId(branchId)
                .fileId(fileInfo.getFileId())
                .branchName("main")
                .branchType(1)
                .branchDescription("主分支")
                .headVersionId(null)
                .sourceBranchId(null)
                .isDefault(true)
                .isActive(true)
                .createdUserId(userId)
                .createdTime(LocalDateTime.now())
                .updatedTime(LocalDateTime.now())
                .build();
        fileBranchMapper.insert(branch);
        return branch;
    }

    /**
     * 获取分支名称
     */
    private String getBranchName(String branchId) {
        if (!StringUtils.hasText(branchId)) return "main";
        FileBranchEntity branch = fileBranchMapper.selectByBranchId(branchId);
        return branch != null ? branch.getBranchName() : "unknown";
    }

    /**
     * 获取文件名
     */
    private String getFileName(String tenantId, String fileId) {
        if (!StringUtils.hasText(fileId)) {
            return null;
        }
        FileInfoEntity fileInfo = fileBaseInfoDao.selectByFileIdAndTenant(fileId, tenantId);
        return fileInfo != null ? fileInfo.getFileName() : null;
    }

    /**
     * 构建分支名称映射
     */
    private Map<String, String> buildBranchNameMap(List<FileBranchEntity> branches) {
        Map<String, String> map = new HashMap<>();
        for (FileBranchEntity branch : branches) {
            map.put(branch.getBranchId(), branch.getBranchName());
        }
        return map;
    }

    /**
     * 构建版本号映射
     */
    private Map<String, Integer> buildVersionNumberMap(List<FileVersionEntity> versions) {
        Map<String, Integer> map = new HashMap<>();
        for (FileVersionEntity version : versions) {
            map.put(version.getVersionId(), version.getVersionNumber());
        }
        return map;
    }

    /**
     * 构建版本映射
     */
    private Map<String, FileVersionEntity> buildVersionMap(List<FileBranchEntity> branches) {
        Set<String> versionIds = new HashSet<>();
        for (FileBranchEntity branch : branches) {
            if (branch.getHeadVersionId() != null) {
                versionIds.add(branch.getHeadVersionId());
            }
        }
        List<FileVersionEntity> versions = fileVersionMapper.selectByVersionIds(
                new ArrayList<>(versionIds)
        );
        Map<String, FileVersionEntity> map = new HashMap<>();
        for (FileVersionEntity version : versions) {
            map.put(version.getVersionId(), version);
        }
        return map;
    }

    /**
     * 从标签构建版本映射
     */
    private Map<String, FileVersionEntity> buildVersionMapFromTags(List<FileVersionTagEntity> tags) {
        Set<String> versionIds = new HashSet<>();
        for (FileVersionTagEntity tag : tags) {
            if (tag.getVersionId() != null) {
                versionIds.add(tag.getVersionId());
            }
        }
        List<FileVersionEntity> versions = fileVersionMapper.selectByVersionIds(
                new ArrayList<>(versionIds)
        );
        Map<String, FileVersionEntity> map = new HashMap<>();
        for (FileVersionEntity version : versions) {
            map.put(version.getVersionId(), version);
        }
        return map;
    }

    /**
     * 构建父版本列表
     */
    private List<String> buildParentList(FileVersionEntity version) {
        List<String> parents = new ArrayList<>();
        if (version.getParentVersionId() != null) {
            parents.add(version.getParentVersionId());
        }
        return parents;
    }

    /**
     * 检查是否为快速合并
     */
    private boolean isFastForward(FileVersionEntity fromHead, FileVersionEntity toHead) {
        String current = toHead.getParentVersionId();
        while (current != null) {
            if (current.equals(fromHead.getVersionId())) {
                return true;
            }
            FileVersionEntity parent = fileVersionMapper.selectByVersionId(current);
            current = parent != null ? parent.getParentVersionId() : null;
        }
        return false;
    }

    /**
     * 执行三方合并
     */
    private MergeResult performThreeWayMerge(
            FileBranchEntity fromBranch,
            FileBranchEntity toBranch,
            FileVersionEntity fromHead,
            FileVersionEntity toHead,
            MergeBranchCommand command
    ) {
//        FileVersionEntity commonAncestor = findCommonAncestor(fromHead, toHead);
//        if (commonAncestor == null) {
//            throw new BusinessException("NO_COMMON_ANCESTOR", "file.branch.no.common.ancestor");
//        }
//
//        byte[] ancestorContent = downloadFileContent(commonAncestor.getFilePath());
//        byte[] fromContent = downloadFileContent(fromHead.getFilePath());
//        byte[] toContent = downloadFileContent(toHead.getFilePath());
//
//        try {
//            byte[] mergedContent = performThreeWayMergeContent(ancestorContent, fromContent, toContent);
//
//            // 4. 检查是否有冲突（简化检测：如果合并结果与ancestor不同，但from和to都不同）
//            boolean hasConflict = !Arrays.equals(mergedContent, fromContent)
//                    && !Arrays.equals(mergedContent, toContent)
//                    && !Arrays.equals(mergedContent, ancestorContent);
//
//            if (hasConflict && !command.getAutoResolve()) {
//                // 存在冲突且未开启自动解决
//                List<MergeResult.ConflictInfo> conflicts = detectConflicts(
//                        ancestorContent, fromContent, toContent, fromBranch, toBranch
//                );
//                throw new MergeConflictException(conflicts);
//            }
//
//            // 5. 创建合并版本
//            String mergeVersionId = createMergeVersion(
//                    toBranch, fromHead, toHead, mergedContent, command
//            );
//
//            // 6. 更新分支头
//            fileBranchMapper.updateHeadVersion(toBranch.getBranchId(), mergeVersionId);
//
//            // 7. 更新当前版本标记
//            fileVersionMapper.updateCurrentFlagByFileId(command.getFileId(), false);
//            fileVersionMapper.updateCurrentFlag(mergeVersionId, true);
//
//            // 8. 更新文件信息
//            FileInfoEntity fileInfo = fileInfoMapper.selectByFileIdAndTenant(
//                    command.getFileId(), command.getTenantId()
//            );
//            if (fileInfo != null) {
//                FileVersionEntity mergeVersion = fileVersionMapper.selectByVersionId(mergeVersionId);
//                if (mergeVersion != null) {
//                    fileInfo.setFilePath(mergeVersion.getFilePath());
//                    fileInfo.setStorageUrl(mergeVersion.getStorageUrl());
//                    fileInfo.setFileMd5(mergeVersion.getFileMd5());
//                    fileInfo.setFileSha256(mergeVersion.getFileSha256());
//                    fileInfo.setFileSize(mergeVersion.getFileSize());
//                    fileInfo.setUpdatedTime(LocalDateTime.now());
//                    fileInfoMapper.updateById(fileInfo);
//                }
//            }
//
//            return fileVersionApplicationConverter.toMergeResult(
//                    "THREE_WAY",
//                    toBranch.getBranchId(),
//                    toBranch.getBranchName(),
//                    mergeVersionId,
//                    getVersionNumber(mergeVersionId),
//                    getVersionTag(mergeVersionId),
//                    false,
//                    null,
//                    "三方合并成功"
//            );
//
//        } catch (MergeConflictException e) {
//            log.warn("合并存在冲突: fromBranch={}, toBranch={}",
//                    fromBranch.getBranchId(), toBranch.getBranchId());
//            return fileVersionApplicationConverter.toMergeResult(
//                    "THREE_WAY",
//                    toBranch.getBranchId(),
//                    toBranch.getBranchName(),
//                    null,
//                    null,
//                    null,
//                    true,
//                    e.getConflicts(),
//                    "合并存在冲突，请手动解决"
//            );
//        }
        return null;
    }

    /**
     * 检测冲突
     */
    private List<MergeResult.ConflictInfo> detectConflicts(
            byte[] ancestor, byte[] from, byte[] to,
            FileBranchEntity fromBranch, FileBranchEntity toBranch
    ) {
        List<MergeResult.ConflictInfo> conflicts = new ArrayList<>();

        String ancestorStr = new String(ancestor, StandardCharsets.UTF_8);
        String fromStr = new String(from, StandardCharsets.UTF_8);
        String toStr = new String(to, StandardCharsets.UTF_8);

        String[] ancestorLines = ancestorStr.split("\n", -1);
        String[] fromLines = fromStr.split("\n", -1);
        String[] toLines = toStr.split("\n", -1);

        int maxLen = Math.max(Math.max(ancestorLines.length, fromLines.length), toLines.length);

        for (int i = 0; i < maxLen; i++) {
            String ancestorLine = i < ancestorLines.length ? ancestorLines[i] : null;
            String fromLine = i < fromLines.length ? fromLines[i] : null;
            String toLine = i < toLines.length ? toLines[i] : null;

            if (fromLine != null && toLine != null && ancestorLine != null) {
                if (!fromLine.equals(toLine) && !fromLine.equals(ancestorLine) && !toLine.equals(ancestorLine)) {
                    conflicts.add(MergeResult.ConflictInfo.builder()
                            .versionId(fromBranch.getHeadVersionId())
                            .versionNumber(getVersionNumber(fromBranch.getHeadVersionId()))
                            .branchId(fromBranch.getBranchId())
                            .branchName(fromBranch.getBranchName())
                            .conflictType("CONTENT_CONFLICT")
                            .description(String.format("第 %d 行存在冲突", i + 1))
                            .build());
                }
            }
        }

        return conflicts;
    }

    /**
     * 查找共同祖先
     */
    private FileVersionEntity findCommonAncestor(FileVersionEntity version1, FileVersionEntity version2) {
        Set<String> ancestors1 = new HashSet<>();
        String current = version1.getParentVersionId();
        while (current != null) {
            ancestors1.add(current);
            FileVersionEntity parent = fileVersionMapper.selectByVersionId(current);
            current = parent != null ? parent.getParentVersionId() : null;
        }

        current = version2.getParentVersionId();
        while (current != null) {
            if (ancestors1.contains(current)) {
                return fileVersionMapper.selectByVersionId(current);
            }
            FileVersionEntity parent = fileVersionMapper.selectByVersionId(current);
            current = parent != null ? parent.getParentVersionId() : null;
        }

        return null;
    }

    /**
     * 执行三方合并内容
     */
    private byte[] performThreeWayMergeContent(byte[] ancestor, byte[] from, byte[] to) {
        if (Arrays.equals(from, to)) {
            return from;
        }
        if (Arrays.equals(from, ancestor)) {
            return to;
        }
        // 存在冲突，简化为返回from
        return from;
    }

    /**
     * 创建合并版本
     */
    private String createMergeVersion(
            FileBranchEntity toBranch, FileVersionEntity fromHead,
            FileVersionEntity toHead, byte[] mergedContent, MergeBranchCommand command
    ) {
//        String mergeVersionId = IdFactory.generate(IdGeneratorType.UUID);
//        int mergeVersionNumber = fileVersionApplicationConverter.calculateMergeVersionNumber(toHead);
//
//        String filePath = storagePlugin.upload(
//                new ByteArrayInputStream(mergedContent),
//                getFileName(toHead.getTenantId(), toHead.getFileId()),
//                (long) mergedContent.length,
//                command.getTenantId(),
//                "default"
//        );
//        String fileUrl = storagePlugin.getFileUrl(filePath);
//
//        String versionTag = fileVersionApplicationConverter.generateMergeVersionTag(mergeVersionNumber);
//        String versionRemark = fileVersionApplicationConverter.generateMergeRemark(
//                fromHead.getBranchId(), toBranch.getBranchId(), command.getMergeMessage()
//        );
//        String changeSummary = fileVersionApplicationConverter.generateMergeChangeSummary(
//                fromHead.getBranchId(), toBranch.getBranchId()
//        );
//
//        FileVersionEntity mergeVersion = FileVersionEntity.builder()
//                .versionId(mergeVersionId)
//                .fileId(toBranch.getFileId())
//                .versionNumber(mergeVersionNumber)
//                .versionType(3)
//                .versionTag(versionTag)
//                .versionRemark(versionRemark)
//                .changeSummary(changeSummary)
//                .filePath(filePath)
//                .fileSize((long) mergedContent.length)
//                .fileMd5(calculateMd5(mergedContent))
//                .fileSha256(calculateSha256(mergedContent))
//                .storageUrl(fileUrl)
//                .parentVersionId(toHead.getVersionId())
//                .branchId(toBranch.getBranchId())
//                .isCurrent(true)
//                .isLatest(true)
//                .createdUserId(command.getUserId())
//                .createdUserName(command.getUserName())
//                .createdTime(LocalDateTime.now())
//                .updatedTime(LocalDateTime.now())
//                .build();
//
//        fileVersionMapper.insert(mergeVersion);
//        fileBranchMapper.updateHeadVersion(toBranch.getBranchId(), mergeVersionId);
//
//        return mergeVersionId;
        return null;
    }

    /**
     * 计算MD5
     */
    private String calculateMd5(byte[] content) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(content);
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 计算SHA-256
     */
    private String calculateSha256(byte[] content) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(content);
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取版本号
     */
    private Integer getVersionNumber(String versionId) {
        FileVersionEntity version = fileVersionMapper.selectByVersionId(versionId);
        return version != null ? version.getVersionNumber() : null;
    }

    /**
     * 获取版本标签
     */
    private String getVersionTag(String versionId) {
        FileVersionEntity version = fileVersionMapper.selectByVersionId(versionId);
        return version != null ? version.getVersionTag() : null;
    }

    /**
     * 检查回滚权限
     */
    private boolean hasRollbackPermission(RollbackVersionCommand command) {
        // 简化实现，实际需要检查权限
        return true;
    }

    /**
     * 构建版本树
     */
    private VersionTreeResult buildVersionTree(
            List<FileVersionEntity> versions, List<FileBranchEntity> branches
    ) {
//        if (versions.isEmpty()) {
//            return fileVersionApplicationConverter.toVersionTreeResult(null, null, null, null, null);
//        }
//
//        String fileId = versions.get(0).getFileId();
//        String fileName = getFileName(versions.get(0).getTenantId(), versions.get(0).getFileId());
//
//        // 构建分支名称映射
//        Map<String, String> branchNameMap = buildBranchNameMap(branches);
//
//        // 构建节点映射
//        Map<String, VersionTreeNodeDto> nodeMap = new LinkedHashMap<>();
//        for (FileVersionEntity version : versions) {
//            String branchName = branchNameMap.get(version.getBranchId());
//            VersionTreeNodeDto node = fileVersionApplicationConverter.toVersionTreeNode(version, branchName);
//            nodeMap.put(version.getVersionId(), node);
//        }
//
//        // 构建父子关系
//        List<VersionTreeEdgeDto> edges = new ArrayList<>();
//        for (FileVersionEntity version : versions) {
//            if (version.getParentVersionId() != null) {
//                VersionTreeNodeDto parent = nodeMap.get(version.getParentVersionId());
//                VersionTreeNodeDto child = nodeMap.get(version.getVersionId());
//                if (parent != null && child != null) {
//                    parent.getChildrenVersionIds().add(version.getVersionId());
//                    String branchName = branchNameMap.get(version.getBranchId());
//                    edges.add(fileVersionApplicationConverter.toVersionTreeEdge(
//                            version.getParentVersionId(),
//                            version.getVersionId(),
//                            version.getBranchId(),
//                            branchName,
//                            "PARENT"
//                    ));
//                }
//            }
//        }
//
//        // 构建分支信息
//        Map<String, Integer> versionNumberMap = buildVersionNumberMap(versions);
//        List<VersionTreeBranchInfoDto> branchInfos = branches.stream()
//                .map(b -> {
//                    Integer headVersionNumber = b.getHeadVersionId() != null ?
//                            versionNumberMap.get(b.getHeadVersionId()) : null;
//                    return fileVersionApplicationConverter.toVersionTreeBranchInfo(b, headVersionNumber);
//                })
//                .collect(Collectors.toList());
//
//        return fileVersionApplicationConverter.toVersionTreeResult(
//                fileId,
//                fileName,
//                new ArrayList<>(nodeMap.values()),
//                edges,
//                branchInfos
//        );
        return null;
    }

    /**
     * 合并冲突异常
     */
    @Getter
    private static class MergeConflictException extends RuntimeException {
        private final List<MergeResult.ConflictInfo> conflicts;

        public MergeConflictException(List<MergeResult.ConflictInfo> conflicts) {
            this.conflicts = conflicts;
        }

    }
}
