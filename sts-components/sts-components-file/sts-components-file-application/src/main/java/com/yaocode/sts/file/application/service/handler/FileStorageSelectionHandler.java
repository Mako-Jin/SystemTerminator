package com.yaocode.sts.file.application.service.handler;

import com.yaocode.sts.file.application.model.command.UploadFileCommand;
import com.yaocode.sts.file.application.model.dto.FileUploadDto;
import com.yaocode.sts.file.application.selector.StorageSelector;
import com.yaocode.sts.file.core.enums.StorageTypeEnums;
import com.yaocode.sts.file.core.model.StorageSelectionContext;
import com.yaocode.sts.file.core.utils.FileUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 存储选择处理器
 * <p>
 * 职责：
 * <ul>
 *   <li>根据文件大小、扩展名、用户偏好等因素选择最优存储类型</li>
 *   <li>将选中的存储类型写回 command 供后续使用</li>
 * </ul>
 * </p>
 *
 * @author yaocode
 * @since 1.0.0
 */
@Slf4j
@Component
@Order(3)
public class FileStorageSelectionHandler implements FileUploadHandler {

    @Resource
    private StorageSelector storageSelector;

    @Override
    public void handle(FileUploadDto fileUploadDto) {
        UploadFileCommand command = fileUploadDto.getCommand();

        String fileExtension = FileUtils.getFileExtension(command.getFileName());

        StorageSelectionContext selectionContext = StorageSelectionContext.builder()
                .specifiedStorage(command.getStorageType())
                .fileSize(command.getFileSize())
                .fileExtension(fileExtension)
                .preferredStorages(command.getPreferredStorages())
                .strategy(command.getStrategy())
                .tenantId(command.getTenantId())
                .userId(command.getUserId())
                .build();

        StorageTypeEnums storageType = storageSelector.selectStorage(selectionContext);

        // 回填到 command，后续流程依赖此字段
        command.setStorageType(storageType.getCode());
        fileUploadDto.setStorageType(storageType);

        log.debug("存储类型选择完成: fileId={}, storageType={}", fileUploadDto.getFileId(), storageType);
    }
}
