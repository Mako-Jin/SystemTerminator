package com.yaocode.sts.file.application.strategy;

import com.yaocode.sts.file.core.constants.FileConstants;
import com.yaocode.sts.file.core.enums.FileTypeEnums;
import com.yaocode.sts.file.core.enums.StorageTypeEnums;
import com.yaocode.sts.file.core.enums.StrategyTypeEnums;
import com.yaocode.sts.file.core.model.StorageSelectionContext;
import com.yaocode.sts.file.core.spi.StorageStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 自动策略：根据文件大小、类型自动选择最优存储
 *
 * @author yaocode
 * @since 1.0.0
 */
@Slf4j
@Component
public class AutoStorageStrategy implements StorageStrategy {

    @Override
    public StorageTypeEnums selectStorage(StorageSelectionContext context) {
        Long fileSize = context.getFileSize();
        String fileExtension = context.getFileExtension();

        // 1. 根据文件大小选择
        if (fileSize == null) {
            log.debug("文件大小为空，使用默认存储: LOCAL");
            return StorageTypeEnums.LOCAL;
        }

        // 小文件（< 10MB）→ 本地存储
        if (fileSize < FileConstants.SMALL_FILE_THRESHOLD) {
            log.debug("小文件 ({} < {} MB)，选择: LOCAL", fileSize, FileConstants.SMALL_FILE_THRESHOLD / 1024 / 1024);
            return StorageTypeEnums.LOCAL;
        }

        // 大文件（> 100MB）→ 对象存储
        if (fileSize > FileConstants.LARGE_FILE_THRESHOLD) {
            log.debug("大文件 ({} > {} MB)，选择: MINIO", fileSize, FileConstants.LARGE_FILE_THRESHOLD / 1024 / 1024);
            return StorageTypeEnums.MINIO;
        }

        // 2. 中等文件根据扩展名选择
        if (fileExtension != null) {
            String ext = fileExtension.toLowerCase().trim();
            // 图片文件 → OSS
            if (FileTypeEnums.IMAGE.containsExtension(ext)) {
                log.debug("图片文件 (扩展名: {})，选择: OSS", ext);
                return StorageTypeEnums.OSS;
            }
            // 视频文件 → MINIO
            if (FileTypeEnums.VIDEO.containsExtension(ext)) {
                log.debug("视频文件 (扩展名: {})，选择: MINIO", ext);
                return StorageTypeEnums.MINIO;
            }
            // 文档文件 → LOCAL
            if (FileTypeEnums.isDocument(FileTypeEnums.fromExtension(ext).getCode())) {
                log.debug("文档文件 (扩展名: {})，选择: LOCAL", ext);
                return StorageTypeEnums.LOCAL;
            }
        }

        // 3. 默认使用本地存储
        log.debug("未匹配到特殊规则，使用默认存储: LOCAL");
        return StorageTypeEnums.LOCAL;
    }

    @Override
    public StrategyTypeEnums getStrategy() {
        return StrategyTypeEnums.AUTO;
    }

    @Override
    public int getPriority() {
        return 1;
    }
}