package com.yaocode.sts.file.application.service.impl;

import com.yaocode.sts.file.application.converter.FileUploadApplicationConverter;
import com.yaocode.sts.file.application.model.command.UploadFileCommand;
import com.yaocode.sts.file.application.model.dto.FileUploadDto;
import com.yaocode.sts.file.application.model.query.FileExistenceQuery;
import com.yaocode.sts.file.application.model.result.FileExistenceResult;
import com.yaocode.sts.file.application.model.result.UploadResult;
import com.yaocode.sts.file.application.service.FileUploadService;
import com.yaocode.sts.file.application.service.handler.FileDeduplicationHandler;
import com.yaocode.sts.file.application.service.handler.FilePersistenceHandler;
import com.yaocode.sts.file.application.service.handler.FileStorageSelectionHandler;
import com.yaocode.sts.file.application.service.handler.FileUploadCleanupHandler;
import com.yaocode.sts.file.application.service.handler.FileUploadExecutionHandler;
import com.yaocode.sts.file.application.service.handler.FileUploadPreparationHandler;
import com.yaocode.sts.file.application.service.handler.FileUploadValidationHandler;
import com.yaocode.sts.file.infrastructure.dao.FileBaseInfoDao;
import com.yaocode.sts.file.infrastructure.dao.FileDeduplicationDao;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    // ==================== 7. 对外接口 ====================

    @Override
    public FileExistenceResult checkFileExists(FileExistenceQuery query) {
        return deduplicationHandler.checkFileExists(query.getFileMd5(), query.getFileSize(), query.getStorageType(), query.getTenantId());
    }
}