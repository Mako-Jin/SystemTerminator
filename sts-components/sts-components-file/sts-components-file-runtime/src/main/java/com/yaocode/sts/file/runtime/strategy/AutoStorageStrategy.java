package com.yaocode.sts.file.runtime.strategy;

import com.yaocode.sts.file.core.enums.StorageTypeEnums;
import com.yaocode.sts.file.core.enums.StrategyTypeEnums;
import com.yaocode.sts.file.core.model.StorageSelectionContext;
import com.yaocode.sts.file.core.strategy.StorageStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

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
        if (fileSize < 10 * 1024 * 1024) {
            log.debug("小文件 ({} < 10MB)，选择: LOCAL", fileSize);
            return StorageTypeEnums.LOCAL;
        }

        // 大文件（> 100MB）→ 对象存储
        if (fileSize > 100 * 1024 * 1024) {
            log.debug("大文件 ({} > 100MB)，选择: MINIO", fileSize);
            return StorageTypeEnums.MINIO;
        }

        // 2. 中等文件根据扩展名选择
        if (fileExtension != null) {
            // 图片文件 → OSS
            if (List.of("jpg", "jpeg", "png", "gif", "webp", "bmp", "svg").contains(fileExtension.toLowerCase())) {
                log.debug("图片文件 (扩展名: {})，选择: OSS", fileExtension);
                return StorageTypeEnums.OSS;
            }
            // 视频文件 → MINIO
            if (List.of("mp4", "avi", "mkv", "mov", "wmv", "flv").contains(fileExtension.toLowerCase())) {
                log.debug("视频文件 (扩展名: {})，选择: MINIO", fileExtension);
                return StorageTypeEnums.MINIO;
            }
            // 文档文件 → LOCAL
            if (List.of("pdf", "doc", "docx", "xls", "xlsx", "ppt", "pptx").contains(fileExtension.toLowerCase())) {
                log.debug("文档文件 (扩展名: {})，选择: LOCAL", fileExtension);
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
