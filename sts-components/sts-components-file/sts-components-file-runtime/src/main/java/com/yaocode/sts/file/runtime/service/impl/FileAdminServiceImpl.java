package com.yaocode.sts.file.runtime.service.impl;

import com.yaocode.sts.common.basic.exception.BusinessException;
import com.yaocode.sts.common.basic.model.PageResult;
import com.yaocode.sts.common.tools.id.IdFactory;
import com.yaocode.sts.file.core.enums.FileStatusEnums;
import com.yaocode.sts.file.runtime.entity.FileInfoEntity;
import com.yaocode.sts.file.runtime.entity.StorageNodeEntity;
import com.yaocode.sts.file.runtime.mapper.FileInfoMapper;
import com.yaocode.sts.file.runtime.mapper.StorageNodeMapper;
import com.yaocode.sts.file.runtime.model.command.AddStorageNodeCommand;
import com.yaocode.sts.file.runtime.model.command.AuditFileCommand;
import com.yaocode.sts.file.runtime.model.command.BatchDeleteCommand;
import com.yaocode.sts.file.runtime.model.command.BatchRestoreCommand;
import com.yaocode.sts.file.runtime.model.command.CancelMigrateCommand;
import com.yaocode.sts.file.runtime.model.command.CleanDuplicateCommand;
import com.yaocode.sts.file.runtime.model.command.CleanExpiredCommand;
import com.yaocode.sts.file.runtime.model.command.CleanTempCommand;
import com.yaocode.sts.file.runtime.model.command.DeleteFileCommand;
import com.yaocode.sts.file.runtime.model.command.DeleteStorageNodeCommand;
import com.yaocode.sts.file.runtime.model.command.MigrateFileCommand;
import com.yaocode.sts.file.runtime.model.command.PermanentDeleteCommand;
import com.yaocode.sts.file.runtime.model.command.RefreshStorageNodeCommand;
import com.yaocode.sts.file.runtime.model.command.RestoreFileCommand;
import com.yaocode.sts.file.runtime.model.command.TestStorageNodeCommand;
import com.yaocode.sts.file.runtime.model.command.UpdateStorageNodeCommand;
import com.yaocode.sts.file.runtime.model.query.AdminFileListQuery;
import com.yaocode.sts.file.runtime.model.query.AdminStatisticsQuery;
import com.yaocode.sts.file.runtime.model.query.AuditListQuery;
import com.yaocode.sts.file.runtime.model.query.AuditLogQuery;
import com.yaocode.sts.file.runtime.model.query.FileDetailQuery;
import com.yaocode.sts.file.runtime.model.query.FileVersionQuery;
import com.yaocode.sts.file.runtime.model.query.StorageNodeQuery;
import com.yaocode.sts.file.runtime.model.query.StorageNodeStatsQuery;
import com.yaocode.sts.file.runtime.model.query.TrendDataQuery;
import com.yaocode.sts.file.runtime.model.result.AdminStatisticsResult;
import com.yaocode.sts.file.runtime.model.result.BatchDeleteResult;
import com.yaocode.sts.file.runtime.model.result.BatchRestoreResult;
import com.yaocode.sts.file.runtime.model.result.CleanupDetailResult;
import com.yaocode.sts.file.runtime.model.result.CleanupResult;
import com.yaocode.sts.file.runtime.model.result.ConnectionTestResult;
import com.yaocode.sts.file.runtime.model.result.DuplicateCleanResult;
import com.yaocode.sts.file.runtime.model.result.DuplicateGroupResult;
import com.yaocode.sts.file.runtime.model.result.FileAuditInfoResult;
import com.yaocode.sts.file.runtime.model.result.FileAuditLogResult;
import com.yaocode.sts.file.runtime.model.result.FileDetailInfoResult;
import com.yaocode.sts.file.runtime.model.result.FileInfoResult;
import com.yaocode.sts.file.runtime.model.result.FileVersionInfoResult;
import com.yaocode.sts.file.runtime.model.result.MigrateTaskStatusResult;
import com.yaocode.sts.file.runtime.model.result.StorageNodeInfoResult;
import com.yaocode.sts.file.runtime.model.result.StorageNodeStatsResult;
import com.yaocode.sts.file.runtime.model.result.TrendDataResult;
import com.yaocode.sts.file.runtime.service.FileAdminService;
import com.yaocode.sts.file.runtime.service.FileStorageService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 文件管理后台服务实现
 *
 * @author yaocode
 * @since 1.0.0
 */
@Slf4j
@Service
public class FileAdminServiceImpl implements FileAdminService {

    @Resource
    private FileInfoMapper fileInfoMapper;

    @Resource
    private StorageNodeMapper storageNodeMapper;

    @Resource
    private FileStorageService fileStorageService;

    // ==================== 1. 文件删除 ====================

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteFile(DeleteFileCommand command) {
        // 1. 检查文件是否存在
        FileInfoEntity entity = fileInfoMapper.selectByFileIdAndTenant(
                command.getFileId(), command.getTenantId());
        if (entity == null) {
            throw new BusinessException("文件不存在");
        }

        // 2. 检查是否已删除
        if (FileStatusEnums.DELETED.getCode().equals(entity.getFileStatus())) {
            throw new BusinessException("文件已删除");
        }

        // 3. 检查是否已归档
        if (FileStatusEnums.ARCHIVED.getCode().equals(entity.getFileStatus())) {
            throw new BusinessException("文件已归档，请先取消归档后再删除");
        }

        // 4. 软删除
        entity.setFileStatus(FileStatusEnums.DELETED.getCode());
        entity.setDeleteTime(LocalDateTime.now());
        entity.setExpireTime(LocalDateTime.now().plusDays(30)); // 回收站保留30天
//        entity.setDeletedUserId(command.getUserId());
//        entity.setDeletedUserName(command.getUserName());
        entity.setUpdatedUserId(command.getUserId());
        entity.setUpdatedTime(LocalDateTime.now());
        fileInfoMapper.updateById(entity);
        log.info("文件删除成功: {}, 操作人: {}", command.getFileId(), command.getUserName());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BatchDeleteResult batchDeleteFiles(BatchDeleteCommand command) {
        List<String> successIds = new ArrayList<>();
        List<String> failedIds = new ArrayList<>();
        Map<String, String> errors = new HashMap<>();

        for (String fileId : command.getFileIds()) {
            try {
                DeleteFileCommand deleteCommand = DeleteFileCommand.builder()
                        .fileId(fileId)
                        .tenantId(command.getTenantId())
                        .userId(command.getUserId())
                        .userName(command.getUserName())
                        .build();
                deleteFile(deleteCommand);
                successIds.add(fileId);
            } catch (Exception e) {
                failedIds.add(fileId);
                errors.put(fileId, e.getMessage());
                log.warn("批量删除文件失败: {}, 原因: {}", fileId, e.getMessage());
            }
        }

        return BatchDeleteResult.builder()
                .total(command.getFileIds().size())
                .success(successIds.size())
                .failed(failedIds.size())
                .failedIds(failedIds)
                .errors(errors)
                .executionTime(System.currentTimeMillis())
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void permanentDeleteFile(PermanentDeleteCommand command) {
        // 1. 检查文件是否存在
        FileInfoEntity entity = fileInfoMapper.selectByFileIdAndTenant(
                command.getFileId(), command.getTenantId());
        if (entity == null) {
            throw new BusinessException("文件不存在");
        }

        // 2. 检查是否在回收站
        if (!FileStatusEnums.DELETED.getCode().equals(entity.getFileStatus())) {
            throw new BusinessException("文件不在回收站中，无法永久删除");
        }

        // 3. 删除存储文件
        try {
            fileStorageService.deleteFile(entity.getFilePath(), entity.getStorageType());
        } catch (Exception e) {
            log.error("删除存储文件失败: {}", e.getMessage());
            throw new BusinessException("删除存储文件失败: " + e.getMessage());
        }

        // 4. 物理删除数据库记录
        fileInfoMapper.deleteById(entity.getFileId());

        log.warn("文件永久删除: {}, 操作人: {}", command.getFileId(), command.getUserName());
    }

    // ==================== 2. 文件恢复 ====================

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void restoreFile(RestoreFileCommand command) {
        // 1. 检查文件是否存在
        FileInfoEntity entity = fileInfoMapper.selectByFileIdAndTenant(
                command.getFileId(), command.getTenantId());
        if (entity == null) {
            throw new BusinessException("文件不存在");
        }

        // 2. 检查是否在回收站
        if (!FileStatusEnums.DELETED.getCode().equals(entity.getFileStatus())) {
            throw new BusinessException("文件未被删除");
        }

        // 3. 检查文件是否过期（过期后可能已被自动清理）
        if (entity.getExpireTime() != null && entity.getExpireTime().isBefore(LocalDateTime.now())) {
            throw new BusinessException("文件已过期，无法恢复");
        }

        // 4. 恢复文件
        entity.setFileStatus(FileStatusEnums.NORMAL.getCode());
        entity.setDeleteTime(null);
        entity.setExpireTime(null);
//        entity.setDeletedUserId(null);
//        entity.setDeletedUserName(null);
        entity.setUpdatedUserId(command.getUserId());
        entity.setUpdatedTime(LocalDateTime.now());
        fileInfoMapper.updateById(entity);

        log.info("文件恢复成功: {}, 操作人: {}", command.getFileId(), command.getUserName());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BatchRestoreResult batchRestoreFiles(BatchRestoreCommand command) {
        List<String> successIds = new ArrayList<>();
        List<String> failedIds = new ArrayList<>();
        Map<String, String> errors = new HashMap<>();

        for (String fileId : command.getFileIds()) {
            try {
                RestoreFileCommand restoreCommand = RestoreFileCommand.builder()
                        .fileId(fileId)
                        .tenantId(command.getTenantId())
                        .userId(command.getUserId())
                        .userName(command.getUserName())
                        .build();
                restoreFile(restoreCommand);
                successIds.add(fileId);
            } catch (Exception e) {
                failedIds.add(fileId);
                errors.put(fileId, e.getMessage());
                log.warn("批量恢复文件失败: {}, 原因: {}", fileId, e.getMessage());
            }
        }

        return BatchRestoreResult.builder()
                .total(command.getFileIds().size())
                .success(successIds.size())
                .failed(failedIds.size())
                .failedIds(failedIds)
                .errors(errors)
                .executionTime(System.currentTimeMillis())
                .build();
    }

    // ==================== 3. 文件归档 ====================

//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void archiveFile(ArchiveFileCommand command) {
//        // 1. 检查文件是否存在
//        FileInfoEntity entity = fileInfoMapper.selectByFileIdAndTenant(
//                command.getFileId(), command.getTenantId());
//        if (entity == null) {
//            throw new BusinessException("文件不存在");
//        }
//
//        // 2. 检查文件状态
//        if (FileStatusEnums.DELETED.getCode().equals(entity.getFileStatus())) {
//            throw new BusinessException("文件已删除，无法归档");
//        }
//        if (FileStatusEnums.ARCHIVED.getCode().equals(entity.getFileStatus())) {
//            throw new BusinessException("文件已归档");
//        }
//
//        // 3. 执行归档
//        String archiveLocation = fileStorageService.archiveFile(
//                entity.getFilePath(),
//                entity.getStorageType(),
//                command.getArchiveType()
//        );
//
//        // 4. 更新文件状态
//        entity.setFileStatus(FileStatusEnums.ARCHIVED.getCode());
//        entity.setArchiveType(command.getArchiveType());
//        entity.setArchiveLocation(archiveLocation);
//        entity.setArchiveTime(LocalDateTime.now());
//        entity.setUpdatedUserId(command.getUserId());
//        entity.setUpdatedTime(LocalDateTime.now());
//        fileInfoMapper.updateById(entity);

//        log.info("文件归档成功: {}, 类型: {}", command.getFileId(), command.getArchiveType());
//    }

//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void unarchiveFile(UnarchiveFileCommand command) {
//        // 1. 检查文件是否存在
//        FileInfoEntity entity = fileInfoMapper.selectByFileIdAndTenant(
//                command.getFileId(), command.getTenantId());
//        if (entity == null) {
//            throw new BusinessException("文件不存在");
//        }
//
//        // 2. 检查是否已归档
//        if (!FileStatusEnums.ARCHIVED.getCode().equals(entity.getFileStatus())) {
//            throw new BusinessException("文件未归档");
//        }
//
//        // 3. 取消归档
//        String newPath = fileStorageService.unarchiveFile(
//                entity.getArchiveLocation(),
//                entity.getStorageType()
//        );
//
//        // 4. 恢复文件状态
//        entity.setFileStatus(FileStatusEnums.NORMAL.getCode());
//        entity.setFilePath(newPath);
//        entity.setArchiveType(null);
//        entity.setArchiveLocation(null);
//        entity.setArchiveTime(null);
//        entity.setUpdatedUserId(command.getUserId());
//        entity.setUpdatedTime(LocalDateTime.now());
//        fileInfoMapper.updateById(entity);
//
//        log.info("文件取消归档成功: {}", command.getFileId());
//    }
//
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public BatchArchiveResult batchArchiveFiles(BatchArchiveCommand command) {
//        List<String> successIds = new ArrayList<>();
//        List<String> failedIds = new ArrayList<>();
//        Map<String, String> errors = new HashMap<>();
//
//        for (String fileId : command.getFileIds()) {
//            try {
//                ArchiveFileCommand archiveCommand = ArchiveFileCommand.builder()
//                        .fileId(fileId)
//                        .archiveType(command.getArchiveType())
//                        .reason(command.getReason())
//                        .tenantId(command.getTenantId())
//                        .userId(command.getUserId())
//                        .userName(command.getUserName())
//                        .build();
//                archiveFile(archiveCommand);
//                successIds.add(fileId);
//            } catch (Exception e) {
//                failedIds.add(fileId);
//                errors.put(fileId, e.getMessage());
//                log.warn("批量归档文件失败: {}, 原因: {}", fileId, e.getMessage());
//            }
//        }
//
//        return BatchArchiveResult.builder()
//                .total(command.getFileIds().size())
//                .success(successIds.size())
//                .failed(failedIds.size())
//                .failedIds(failedIds)
//                .errors(errors)
//                .executionTime(System.currentTimeMillis())
//                .build();
//    }

    // ==================== 4. 文件迁移 ====================

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String migrateFile(MigrateFileCommand command) {
        // 1. 检查文件是否存在
        FileInfoEntity entity = fileInfoMapper.selectByFileIdAndTenant(
                command.getFileId(), command.getTenantId());
        if (entity == null) {
            throw new BusinessException("文件不存在");
        }

        // 2. 检查目标存储是否与当前相同
        if (command.getTargetStorageType().equals(entity.getStorageType())) {
            throw new BusinessException("目标存储类型与当前相同，无需迁移");
        }

        // 3. 生成任务ID
        String taskId = IdFactory.generate();

        // 4. 执行迁移
        if (command.getAsync() != null && command.getAsync()) {
            // 异步执行
            fileStorageService.migrateFileAsync(
                    entity.getFilePath(),
                    entity.getStorageType(),
                    command.getTargetStorageType(),
                    taskId,
                    command.getKeepSource() != null ? command.getKeepSource() : false
            );
            log.info("文件迁移任务已提交（异步）: {}, 任务ID: {}", command.getFileId(), taskId);
        } else {
            // 同步执行
            String newPath = fileStorageService.migrateFile(
                    entity.getFilePath(),
                    entity.getStorageType(),
                    command.getTargetStorageType(),
                    command.getKeepSource() != null ? command.getKeepSource() : false
            );
            entity.setFilePath(newPath);
            entity.setStorageType(command.getTargetStorageType());
            entity.setUpdatedUserId(command.getUserId());
            entity.setUpdatedTime(LocalDateTime.now());
            fileInfoMapper.updateById(entity);
            log.info("文件迁移完成（同步）: {}", command.getFileId());
        }

        return taskId;
    }

    @Override
    public MigrateTaskStatusResult getMigrateTaskStatus(String taskId) {
        return fileStorageService.getMigrateTaskStatus(taskId);
    }

    @Override
    public void cancelMigrateTask(CancelMigrateCommand command) {
        fileStorageService.cancelMigrateTask(command.getTaskId());
        log.info("迁移任务取消: {}", command.getTaskId());
    }

    // ==================== 5. 文件清理 ====================

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CleanupResult cleanExpiredFiles(CleanExpiredCommand command) {
        LocalDateTime expireTime = LocalDateTime.now().minusDays(command.getDays());
        List<FileInfoEntity> expiredFiles = fileInfoMapper.selectExpiredFiles(
                expireTime, command.getStorageType(), command.getTenantId());

        int deleted = 0;
        long freedSpace = 0;
        List<CleanupDetailResult> details = new ArrayList<>();

        for (FileInfoEntity entity : expiredFiles) {
            if (command.getDryRun() != null && command.getDryRun()) {
                // 试运行，只统计
                details.add(CleanupDetailResult.builder()
                        .fileId(entity.getFileId())
                        .fileName(entity.getFileName())
                        .fileSize(entity.getFileSize())
                        .reason("文件已过期（试运行）")
                        .deleted(false)
                        .build());
            } else {
                // 实际删除
                try {
                    fileStorageService.deleteFile(entity.getFilePath(), entity.getStorageType());
                    fileInfoMapper.deleteById(entity.getFileId());
                    deleted++;
                    freedSpace += entity.getFileSize();
                    details.add(CleanupDetailResult.builder()
                            .fileId(entity.getFileId())
                            .fileName(entity.getFileName())
                            .fileSize(entity.getFileSize())
                            .reason("文件已过期")
                            .deleted(true)
                            .build());
                } catch (Exception e) {
                    log.error("清理过期文件失败: {}, 原因: {}", entity.getFileId(), e.getMessage());
                    details.add(CleanupDetailResult.builder()
                            .fileId(entity.getFileId())
                            .fileName(entity.getFileName())
                            .fileSize(entity.getFileSize())
                            .reason("删除失败: " + e.getMessage())
                            .deleted(false)
                            .build());
                }
            }
        }

        String message = command.getDryRun() != null && command.getDryRun()
                ? "试运行完成，共扫描 " + expiredFiles.size() + " 个过期文件"
                : "清理完成，共删除 " + deleted + " 个文件，释放 " + freedSpace + " 字节";

        return CleanupResult.builder()
                .totalScanned(expiredFiles.size())
                .totalDeleted(deleted)
                .totalFreedSpace(freedSpace)
                .executionTime(System.currentTimeMillis())
                .message(message)
                .details(details)
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CleanupResult cleanTempFiles(CleanTempCommand command) {
        LocalDateTime expireTime = LocalDateTime.now().minusHours(command.getHours());
        List<FileInfoEntity> tempFiles = fileInfoMapper.selectTempFiles(expireTime, command.getTenantId());

        int deleted = 0;
        long freedSpace = 0;
        List<CleanupDetailResult> details = new ArrayList<>();

        for (FileInfoEntity entity : tempFiles) {
            try {
                // 删除临时文件
                fileStorageService.deleteFile(entity.getFilePath(), entity.getStorageType());
                fileInfoMapper.deleteById(entity.getFileId());
                deleted++;
                freedSpace += entity.getFileSize();
                details.add(CleanupDetailResult.builder()
                        .fileId(entity.getFileId())
                        .fileName(entity.getFileName())
                        .fileSize(entity.getFileSize())
                        .reason("临时文件超时（" + command.getHours() + "小时）")
                        .deleted(true)
                        .build());
            } catch (Exception e) {
                log.error("清理临时文件失败: {}, 原因: {}", entity.getFileId(), e.getMessage());
                details.add(CleanupDetailResult.builder()
                        .fileId(entity.getFileId())
                        .fileName(entity.getFileName())
                        .fileSize(entity.getFileSize())
                        .reason("删除失败: " + e.getMessage())
                        .deleted(false)
                        .build());
            }
        }

        return CleanupResult.builder()
                .totalScanned(tempFiles.size())
                .totalDeleted(deleted)
                .totalFreedSpace(freedSpace)
                .executionTime(System.currentTimeMillis())
                .message("清理完成，共删除 " + deleted + " 个临时文件")
                .details(details)
                .build();
    }

    @Override
    public DuplicateCleanResult cleanDuplicateFiles(CleanDuplicateCommand command) {
        // 查找重复文件（相同MD5和大小）
        List<DuplicateGroupResult> groups = fileInfoMapper.findDuplicateFiles(
                command.getStorageType(), command.getTenantId());

        int totalDuplicateFiles = 0;
        int deleted = 0;
        long freedSpace = 0;
        List<DuplicateGroupResult> processedGroups = new ArrayList<>();

        for (DuplicateGroupResult group : groups) {
            if (group.getFileCount() <= 1) continue;

            totalDuplicateFiles += group.getFileCount() - 1;

            // 保留第一个文件，删除其他
            List<FileInfoResult> files = group.getFiles();

            for (int i = 1; i < files.size(); i++) {
                FileInfoResult deleteFile = files.get(i);
                if (command.getAutoDelete() != null && command.getAutoDelete()) {
                    try {
                        fileStorageService.deleteFile(deleteFile.getFilePath(), deleteFile.getStorageType());
                        fileInfoMapper.deleteById(deleteFile.getFileId());
                        deleted++;
                        freedSpace += deleteFile.getFileSize();
                    } catch (Exception e) {
                        log.error("删除重复文件失败: {}, 原因: {}", deleteFile.getFileId(), e.getMessage());
                    }
                }
            }
            processedGroups.add(group);
        }

        return DuplicateCleanResult.builder()
                .duplicateGroups(groups.size())
                .duplicateFiles(totalDuplicateFiles)
                .duplicateSpace(freedSpace)
                .keptFiles(groups.size())
                .deletedFiles(deleted)
                .groups(processedGroups)
                .executionTime(System.currentTimeMillis())
                .build();
    }

    // ==================== 6. 文件统计 ====================

    @Override
    public AdminStatisticsResult getStatistics(AdminStatisticsQuery query) {
        return fileInfoMapper.getAdminStatistics(query.getStorageType(), query.getTenantId());
    }

    @Override
    public List<TrendDataResult> getTrendData(TrendDataQuery query) {
        return fileInfoMapper.getTrendData(
                query.getPeriod(),
                query.getStartDate(),
                query.getEndDate(),
                query.getStorageType(),
                query.getTenantId()
        );
    }

    @Override
    public List<StorageNodeStatsResult> getStorageNodeStats(StorageNodeStatsQuery query) {
        return fileStorageService.getStorageNodeStats(query.getTenantId());
    }

    // ==================== 7. 存储节点管理 ====================

    @Override
    public List<StorageNodeInfoResult> getStorageNodes(StorageNodeQuery query) {
        List<StorageNodeEntity> entities = storageNodeMapper.selectByTenantAndStatus(
                query.getTenantId(),
                query.getEnabledOnly()
        );

        if (CollectionUtils.isEmpty(entities)) {
            return Collections.emptyList();
        }

        return entities.stream()
                .map(this::convertToStorageNodeInfoResult)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public StorageNodeInfoResult addStorageNode(AddStorageNodeCommand command) {
        // 1. 检查节点代码是否已存在
        StorageNodeEntity existing = storageNodeMapper.selectByNodeCode(
                command.getTenantId(), command.getNodeCode()
        );
        if (existing != null) {
            throw new BusinessException("节点代码已存在: " + command.getNodeCode());
        }

        // 2. 创建存储节点
        StorageNodeEntity entity = new StorageNodeEntity();
        entity.setNodeCode(command.getNodeCode());
        entity.setNodeName(command.getNodeName());
        entity.setStorageType(command.getStorageType());
        entity.setEndpoint(command.getEndpoint());
        entity.setBucketName(command.getBucketName());
        entity.setAccessKey(command.getAccessKey());
        entity.setSecretKey(command.getSecretKey());
        entity.setRegion(command.getRegion());
        entity.setMaxCapacity(command.getMaxCapacity());
        entity.setUsedCapacity(0L);
        entity.setWeight(command.getWeight() != null ? command.getWeight() : 1);
        entity.setPriority(command.getPriority() != null ? command.getPriority() : 5);
        entity.setEnabled(command.getEnabled() != null ? command.getEnabled() : 1);
        entity.setNodeStatus(1); // 正常
        entity.setHealthStatus(1); // 健康
        entity.setLastHealthCheck(LocalDateTime.now());
//        entity.setTenantId(command.getTenantId());
        entity.setCreatedUserId(command.getUserId());
        entity.setCreatedTime(LocalDateTime.now());
        entity.setUpdatedTime(LocalDateTime.now());

        storageNodeMapper.insert(entity);
        log.info("添加存储节点成功: {}", command.getNodeName());

        return convertToStorageNodeInfoResult(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public StorageNodeInfoResult updateStorageNode(UpdateStorageNodeCommand command) {
        // 1. 检查节点是否存在
        StorageNodeEntity entity = storageNodeMapper.selectById(command.getNodeId());
        if (entity == null) {
            throw new BusinessException("存储节点不存在: " + command.getNodeId());
        }

        // 2. 更新节点信息
        if (StringUtils.hasText(command.getNodeCode())) {
            entity.setNodeCode(command.getNodeCode());
        }
        if (StringUtils.hasText(command.getNodeName())) {
            entity.setNodeName(command.getNodeName());
        }
        if (StringUtils.hasText(command.getEndpoint())) {
            entity.setEndpoint(command.getEndpoint());
        }
        if (StringUtils.hasText(command.getBucketName())) {
            entity.setBucketName(command.getBucketName());
        }
        if (StringUtils.hasText(command.getAccessKey())) {
            entity.setAccessKey(command.getAccessKey());
        }
        if (StringUtils.hasText(command.getSecretKey())) {
            entity.setSecretKey(command.getSecretKey());
        }
        if (StringUtils.hasText(command.getRegion())) {
            entity.setRegion(command.getRegion());
        }
        if (command.getMaxCapacity() != null) {
            entity.setMaxCapacity(command.getMaxCapacity());
        }
        if (command.getWeight() != null) {
            entity.setWeight(command.getWeight());
        }
        if (command.getPriority() != null) {
            entity.setPriority(command.getPriority());
        }
        if (command.getEnabled() != null) {
            entity.setEnabled(command.getEnabled());
        }

        entity.setUpdatedUserId(command.getUserId());
        entity.setUpdatedTime(LocalDateTime.now());

        storageNodeMapper.updateById(entity);
        log.info("更新存储节点成功: {}", command.getNodeId());

        return convertToStorageNodeInfoResult(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteStorageNode(DeleteStorageNodeCommand command) {
        // 1. 检查节点是否存在
        StorageNodeEntity entity = storageNodeMapper.selectById(command.getNodeId());
        if (entity == null) {
            throw new BusinessException("存储节点不存在: " + command.getNodeId());
        }

        // 2. 检查是否有文件存储在该节点（如果有文件，需要先迁移或删除文件）
        if (!command.getForce()) {
            long fileCount = fileInfoMapper.countByStorageTypeAndTenant(
                    entity.getStorageType(), entity.getTenantId()
            );
            if (fileCount > 0) {
                throw new BusinessException("该节点下存在 " + fileCount + " 个文件，请先迁移或删除文件");
            }
        }

        // 3. 删除节点
        storageNodeMapper.deleteById(command.getNodeId());
        log.info("删除存储节点成功: {}", command.getNodeId());
    }

    @Override
    public ConnectionTestResult testStorageNode(TestStorageNodeCommand command) {
        StorageNodeEntity entity = storageNodeMapper.selectById(command.getNodeId());
        if (entity == null) {
            throw new BusinessException("存储节点不存在: " + command.getNodeId());
        }

        long startTime = System.currentTimeMillis();
        try {
            boolean connected = fileStorageService.testConnection(
                    entity.getStorageType(),
                    entity.getEndpoint(),
                    entity.getAccessKey(),
                    entity.getSecretKey()
            );

            long latency = System.currentTimeMillis() - startTime;

            // 更新健康状态
            entity.setHealthStatus(connected ? 1 : 0);
            entity.setLastHealthCheck(LocalDateTime.now());
            storageNodeMapper.updateById(entity);

            return ConnectionTestResult.builder()
                    .success(connected)
                    .latency(latency)
                    .message(connected ? "连接成功" : "连接失败")
                    .testTime(LocalDateTime.now())
                    .build();

        } catch (Exception e) {
            long latency = System.currentTimeMillis() - startTime;
            log.error("测试存储节点连接失败: {}", command.getNodeId(), e);

            // 更新健康状态
            entity.setHealthStatus(0);
            entity.setLastHealthCheck(LocalDateTime.now());
            storageNodeMapper.updateById(entity);

            return ConnectionTestResult.builder()
                    .success(false)
                    .latency(latency)
                    .message("连接失败: " + e.getMessage())
                    .testTime(LocalDateTime.now())
                    .build();
        }
    }

    @Override
    public StorageNodeInfoResult refreshStorageNode(RefreshStorageNodeCommand command) {
        // 1. 检查节点是否存在
        StorageNodeEntity entity = storageNodeMapper.selectById(command.getNodeId());
        if (entity == null) {
            throw new BusinessException("存储节点不存在: " + command.getNodeId());
        }

        // 2. 获取最新状态
        long usedCapacity = fileInfoMapper.sumFileSizeByStorageTypeAndTenant(
                entity.getStorageType(), entity.getTenantId());

        // 3. 测试连接
        boolean connected = fileStorageService.testConnection(
                entity.getStorageType(),
                entity.getEndpoint(),
                entity.getAccessKey(),
                entity.getSecretKey()
        );

        // 4. 更新节点信息
        entity.setUsedCapacity(usedCapacity);
        entity.setHealthStatus(connected ? 1 : 0);
        entity.setLastHealthCheck(LocalDateTime.now());
        storageNodeMapper.updateById(entity);

        log.info("刷新存储节点状态成功: {}", command.getNodeId());

        return convertToStorageNodeInfoResult(entity);
    }

    // ==================== 8. 文件审核与审计 ====================

    @Override
    public PageResult<FileAuditInfoResult> getAuditList(AuditListQuery query) {
        return fileInfoMapper.getAuditList(query);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void auditFile(AuditFileCommand command) {
        // 1. 检查文件是否存在
//        FileInfoEntity entity = fileInfoMapper.selectByFileIdAndTenant(
//                command.getFileId(), command.getTenantId());
//        if (entity == null) {
//            throw new BusinessException("文件不存在");
//        }
//
//        // 2. 检查是否已审核
//        if (entity.getAuditStatus() != null && entity.getAuditStatus() != 0) {
//            throw new BusinessException("文件已审核");
//        }

        // 3. 更新审核状态
//        entity.setAuditStatus(command.getApproved() ? 1 : 2); // 1-通过, 2-拒绝
//        entity.setAuditComment(command.getComment());
//        entity.setAuditUserId(command.getUserId());
//        entity.setAuditUserName(command.getUserName());
//        entity.setAuditTime(LocalDateTime.now());
//        entity.setUpdatedUserId(command.getUserId());
//        entity.setUpdatedTime(LocalDateTime.now());
//        fileInfoMapper.updateById(entity);

        log.info("文件审核完成: {}, 结果: {}", command.getFileId(),
                command.getApproved() ? "通过" : "拒绝");
    }

    @Override
    public PageResult<FileAuditLogResult> getAuditLogs(AuditLogQuery query) {
//        return auditLogUtil.getAuditLogs(query);
        return null;
    }

    // ==================== 9. 文件信息查询 ====================

    @Override
    public FileDetailInfoResult getFileDetail(FileDetailQuery query) {
        return fileInfoMapper.getFileDetail(query.getFileId(), query.getTenantId());
    }

    @Override
    public PageResult<FileInfoResult> listFiles(AdminFileListQuery query) {
        return fileInfoMapper.listFiles(query);
    }

    @Override
    public List<FileVersionInfoResult> getFileVersions(FileVersionQuery query) {
        return fileInfoMapper.getFileVersions(query.getFileId(), query.getTenantId());
    }

    // ==================== 私有辅助方法 ====================

    /**
     * 转换存储节点实体为结果对象
     */
    private StorageNodeInfoResult convertToStorageNodeInfoResult(StorageNodeEntity entity) {
        if (entity == null) return null;

        double usageRate = 0.0;
        if (entity.getMaxCapacity() != null && entity.getMaxCapacity() > 0) {
            usageRate = (double) entity.getUsedCapacity() / entity.getMaxCapacity() * 100;
        }

        return StorageNodeInfoResult.builder()
                .nodeId(entity.getNodeId())
                .nodeCode(entity.getNodeCode())
                .nodeName(entity.getNodeName())
                .storageType(entity.getStorageType())
                .endpoint(entity.getEndpoint())
                .bucketName(entity.getBucketName())
                .maxCapacity(entity.getMaxCapacity())
                .usedCapacity(entity.getUsedCapacity())
                .usageRate(usageRate)
                .nodeStatus(entity.getNodeStatus())
                .healthStatus(entity.getHealthStatus())
                .enabled(entity.getEnabled())
                .weight(entity.getWeight())
                .priority(entity.getPriority())
                .lastHealthCheck(entity.getLastHealthCheck())
                .createdTime(entity.getCreatedTime())
                .updatedTime(entity.getUpdatedTime())
                .build();
    }
}