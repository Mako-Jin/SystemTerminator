package com.yaocode.sts.file.application.service.handler;

import com.yaocode.sts.file.application.model.command.UploadFileCommand;
import com.yaocode.sts.file.application.model.dto.FileUploadDto;
import com.yaocode.sts.file.core.enums.FileErrorCodeEnums;
import com.yaocode.sts.file.core.exception.FileValidationException;
import com.yaocode.sts.file.core.utils.FileNameUtils;
import com.yaocode.sts.file.infrastructure.config.FileStorageConfig;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 文件上传校验处理器
 * <p>
 * 职责：
 * <ul>
 *   <li>校验文件不为空</li>
 *   <li>校验文件大小（上限、下限）</li>
 *   <li>校验文件名（格式、是否为空）</li>
 *   <li>校验扩展名（黑名单 / 白名单）</li>
 * </ul>
 * </p>
 *
 * @author yaocode
 * @since 1.0.0
 */
@Slf4j
@Component
@Order(1)
public class FileUploadValidationHandler implements FileUploadHandler {

    @Resource
    private FileStorageConfig fileStorageConfig;

    @Override
    public void handle(FileUploadDto fileUploadDto) {
        UploadFileCommand command = fileUploadDto.getCommand();

        long maxFileSize = fileStorageConfig.getUpload().getMaxFileSize();

        // 1. 文件非空校验
        if (command.getFile() == null || command.getFile().getInputStream() == null) {
            throw new FileValidationException(FileErrorCodeEnums.FILE_NOT_FOUND);
        }

        // 2. 文件大小校验
        if (command.getFileSize() <= 0) {
            throw new FileValidationException(FileErrorCodeEnums.FILE_SIZE_INVALID);
        }
        if (command.getFileSize() > maxFileSize) {
            throw new FileValidationException(FileErrorCodeEnums.FILE_TOO_LARGE, maxFileSize, command.getFileSize());
        }

        // 3. 文件名校验
        if (!StringUtils.hasText(command.getFileName())) {
            throw new FileValidationException(FileErrorCodeEnums.FILE_NAME_EMPTY);
        }
        if (!FileNameUtils.isValidFileName(command.getFileName())) {
            throw new FileValidationException(FileErrorCodeEnums.FILE_NAME_INVALID, command.getFileName());
        }

        // 4. 扩展名校验
        String extension = FileNameUtils.getFileExtension(command.getFileName());
        List<String> blockedExtensions = fileStorageConfig.getUpload().getBlockedExtensions();
        if (!blockedExtensions.isEmpty() && blockedExtensions.contains(extension.toLowerCase())) {
            throw new FileValidationException(FileErrorCodeEnums.FILE_EXTENSION_NOT_ALLOWED, extension);
        }

        List<String> allowedExtensions = fileStorageConfig.getUpload().getAllowedExtensions();
        if (!allowedExtensions.isEmpty() && !allowedExtensions.contains(extension.toLowerCase())) {
            throw new FileValidationException(FileErrorCodeEnums.FILE_EXTENSION_NOT_ALLOWED, extension);
        }

        log.debug("文件上传参数校验通过: fileName={}, fileSize={}", command.getFileName(), command.getFileSize());
    }
}
