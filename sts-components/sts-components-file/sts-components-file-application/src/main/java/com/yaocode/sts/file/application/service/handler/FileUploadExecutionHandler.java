package com.yaocode.sts.file.application.service.handler;

import com.yaocode.sts.file.application.model.command.UploadFileCommand;
import com.yaocode.sts.file.application.model.dto.FileUploadDto;
import com.yaocode.sts.file.core.enums.FileErrorCodeEnums;
import com.yaocode.sts.file.core.enums.StorageTypeEnums;
import com.yaocode.sts.file.core.exception.FileStorageException;
import com.yaocode.sts.file.core.spi.StoragePlugin;
import com.yaocode.sts.file.infrastructure.manager.StoragePluginManager;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

/**
 * 文件上传执行处理器
 * <p>
 * 职责：
 * <ul>
 *   <li>从 {@link StoragePluginManager} 获取选中存储类型对应的插件</li>
 *   <li>将临时文件流式上传到存储后端</li>
 *   <li>获取文件访问 URL</li>
 * </ul>
 * </p>
 *
 * <p>注意：当 {@link FileUploadDto#isCompleted()} 为 true 时（即去重策略短路），
 * 此 Handler 会被跳过。</p>
 *
 * @author yaocode
 * @since 1.0.0
 */
@Slf4j
@Component
@Order(5)
public class FileUploadExecutionHandler implements FileUploadHandler {

    @Resource
    private StoragePluginManager pluginManager;

    @Override
    public void handle(FileUploadDto fileUploadDto) {
        // 策略短路场景，跳过上传
        if (fileUploadDto.isCompleted()) {
            log.debug("上传已由去重策略完成，跳过执行: fileId={}", fileUploadDto.getFileId());
            return;
        }

        StorageTypeEnums storageType = fileUploadDto.getStorageType();
        StoragePlugin plugin = pluginManager.getPlugin(storageType);
        if (plugin == null) {
            throw new FileStorageException(FileErrorCodeEnums.STORAGE_TYPE_NOT_SUPPORTED, String.valueOf(storageType.getCode()));
        }

        UploadFileCommand command = fileUploadDto.getCommand();
        String bucket = command.getBusinessType() != null ? command.getBusinessType() : "default";

        try (InputStream is = Files.newInputStream(fileUploadDto.getTempFile())) {
            String filePath = plugin.upload(is, command.getFileName(), command.getFileSize(),
                    command.getTenantId(), bucket);
            String fileUrl = plugin.getFileUrl(filePath);

            fileUploadDto.setFilePath(filePath);
            fileUploadDto.setFileUrl(fileUrl);

            log.debug("文件上传成功: fileId={}, storageType={}, path={}",
                    fileUploadDto.getFileId(), storageType, filePath);

        } catch (IOException e) {
            throw new FileStorageException("STORAGE_UPLOAD_FAILED", e, storageType);
        }
    }
}
