package com.yaocode.sts.file.application.service.impl;

import com.yaocode.sts.common.tools.id.IdFactory;
import com.yaocode.sts.common.tools.id.IdGeneratorType;
import com.yaocode.sts.common.tools.messages.MessageUtils;
import com.yaocode.sts.file.application.converter.FileUploadApplicationConverter;
import com.yaocode.sts.file.application.model.command.FastUploadCommand;
import com.yaocode.sts.file.application.model.command.UploadBatchCommand;
import com.yaocode.sts.file.application.model.command.UploadFileCommand;
import com.yaocode.sts.file.application.model.dto.FileObjectDto;
import com.yaocode.sts.file.application.model.dto.FileUploadDto;
import com.yaocode.sts.file.application.model.query.FileExistenceQuery;
import com.yaocode.sts.file.application.model.result.FileExistenceResult;
import com.yaocode.sts.file.application.model.result.UploadResult;
import com.yaocode.sts.file.application.service.FileUploadService;
import com.yaocode.sts.file.core.constants.FileI18nKeyConstants;
import com.yaocode.sts.file.core.enums.FileErrorCodeEnums;
import com.yaocode.sts.file.core.enums.UploadStatusEnums;
import com.yaocode.sts.file.core.exception.FileUploadException;
import com.yaocode.sts.file.application.service.handler.FileDeduplicationHandler;
import com.yaocode.sts.file.application.service.handler.FilePersistenceHandler;
import com.yaocode.sts.file.application.service.handler.FileStorageSelectionHandler;
import com.yaocode.sts.file.application.service.handler.FileUploadCleanupHandler;
import com.yaocode.sts.file.application.service.handler.FileUploadExecutionHandler;
import com.yaocode.sts.file.application.service.handler.FileUploadPreparationHandler;
import com.yaocode.sts.file.application.service.handler.FileUploadValidationHandler;
import com.yaocode.sts.file.infrastructure.dao.FileBaseInfoDao;
import com.yaocode.sts.file.infrastructure.dao.FileDeduplicationDao;
import com.yaocode.sts.file.infrastructure.entity.FileBasicInfoEntity;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件上传服务实现（完整优化版）
 * <p>
 * 优化点：
 * 1. 流式处理 + 临时文件（解决内存问题）
 * 2. 短事务（解决事务范围过大）
 * 3. 职责清晰（策略执行委托给 Executor）
 * 4. 统一配置管理
 * 5. 统一异常处理
 * </p>
 *
 * @author yaocode
 * @since 1.0.0
 */
@Slf4j
@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Resource
    private FileUploadValidationHandler validationHandler;
    @Resource
    private FileUploadPreparationHandler preparationHandler;
    @Resource
    private FileStorageSelectionHandler storageSelectionHandler;
    @Resource
    private FileDeduplicationHandler deduplicationHandler;
    @Resource
    private FileUploadExecutionHandler executionHandler;
    @Resource
    private FilePersistenceHandler persistenceHandler;
    @Resource
    private FileUploadCleanupHandler cleanupHandler;
    // 仅用于 checkFileExists 接口查询
    @Resource
    private FileDeduplicationDao fileDeduplicationDao;
    @Resource
    private FileBaseInfoDao fileBaseInfoDao;
    @Resource
    private FileUploadApplicationConverter fileUploadApplicationConverter;

    @Resource
    private MessageUtils messageUtils;

    // ==================== 1. 主入口 ====================

    @Override
    public UploadResult upload(UploadFileCommand command) {
        FileUploadDto fileUploadDto = FileUploadDto.builder()
                .command(command)
                .startTime(System.currentTimeMillis())
                .build();
        try {
            validationHandler.handle(fileUploadDto);
            preparationHandler.handle(fileUploadDto);
            storageSelectionHandler.handle(fileUploadDto);
            deduplicationHandler.handle(fileUploadDto);
            executionHandler.handle(fileUploadDto);
            persistenceHandler.handle(fileUploadDto);
            return fileUploadDto.getResult();
        } catch (Exception e) {
            cleanupHandler.cleanupOnFailure(fileUploadDto);
            throw e;
        }
    }

    @Override
    public List<UploadResult> uploadBatch(UploadBatchCommand command) {
        List<UploadResult> results = new ArrayList<>();
        if (command.getFiles() == null || command.getFiles().isEmpty()) {
            return results;
        }
        for (FileObjectDto file : command.getFiles()) {
            try {
                UploadFileCommand fileCommand = fileUploadApplicationConverter.buildUploadFileCommand(command, file);
                UploadResult result = this.upload(fileCommand);
                results.add(result);
            } catch (Exception e) {
                log.warn("批量上传文件失败: {}", file.getFileName(), e);
                results.add(UploadResult.builder()
                        .fileName(file.getFileName())
                        .fileSize(file.getFileSize())
                        .fileMd5(file.getMd5())
                        .uploadStatus(UploadStatusEnums.FAILED.getCode())
                        .uploadStatusDesc(UploadStatusEnums.FAILED.getDesc())
                        .message(messageUtils.resolveExceptionMessage(e))
                        .build());
            }
        }
        return results;
    }

    // ==================== 7. 对外接口 ====================

    @Override
    public FileExistenceResult checkFileExists(FileExistenceQuery query) {
        return deduplicationHandler.checkFileExists(query.getFileMd5(), query.getFileSize(), query.getStorageType(), query.getTenantId());
    }

    @Override
    public UploadResult fastUpload(FastUploadCommand command) {
        long startTime = System.currentTimeMillis();

        // 1. 检查文件是否已存在
        FileExistenceResult existenceResult = deduplicationHandler.checkFileExists(
                command.getFileMd5(),
                command.getFileSize(),
                command.getStorageType(),
                command.getTenantId()
        );

        // 2. 文件不存在，无法秒传
        if (existenceResult == null || !existenceResult.getExists()) {
            throw new FileUploadException(FileErrorCodeEnums.FILE_NOT_FOUND);
        }

        // 3. 查询原文件实体（获取完整存储信息）
        FileBasicInfoEntity originalEntity = fileBaseInfoDao.selectByFileIdAndTenant(
                existenceResult.getFileId(), command.getTenantId()
        );
        if (originalEntity == null) {
            throw new FileUploadException(FileErrorCodeEnums.FILE_NOT_FOUND);
        }

        // 4. 创建引用记录（新 fileId，复用物理文件）
        String newFileId = IdFactory.generate(IdGeneratorType.UUID);
        FileBasicInfoEntity refEntity = fileUploadApplicationConverter.toFastUploadEntity(
                command, originalEntity, newFileId
        );
        fileBaseInfoDao.save(refEntity);

        // 5. 构建返回结果
        long processingTime = System.currentTimeMillis() - startTime;
        String message = messageUtils.getMessage(FileI18nKeyConstants.STRATEGY_REUSE_SUCCESS);
        UploadResult uploadResult = fileUploadApplicationConverter.toFastUploadResult(
                newFileId, command, originalEntity, message, processingTime
        );

        log.info("秒传成功: newFileId={}, originalFileId={}, 耗时={}ms",
                newFileId, originalEntity.getFileId(), processingTime);
        return uploadResult;
    }
}