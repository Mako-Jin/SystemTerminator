package com.yaocode.sts.file.application.service.handler;

import com.yaocode.sts.file.application.model.dto.FileUploadDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * 文件上传清理处理器
 * <p>
 * 职责：
 * <ul>
 *   <li>成功场景：在 {@link FilePersistenceHandler} 中调用以清理临时文件</li>
 *   <li>失败场景：由编排器（FileUploadServiceImpl）在 catch 块中调用</li>
 * </ul>
 * </p>
 *
 * <p>设计为无状态 Bean，可被多处复用。</p>
 *
 * @author yaocode
 * @since 1.0.0
 */
@Slf4j
@Component
@Order(99)
public class FileUploadCleanupHandler implements FileUploadHandler {

    @Override
    public void handle(FileUploadDto fileUploadDto) {
        cleanupTempFile(fileUploadDto.getTempFile());
    }

    /**
     * 清理临时文件
     *
     * @param tempFile 临时文件路径，可为 null
     */
    public void cleanupTempFile(Path tempFile) {
        if (tempFile == null) {
            return;
        }
        if (!Files.exists(tempFile)) {
            return;
        }
        try {
            Files.deleteIfExists(tempFile);
            log.debug("临时文件已删除: {}", tempFile);
        } catch (IOException e) {
            log.warn("删除临时文件失败: {}", tempFile, e);
        }
    }

    /**
     * 失败时的清理（额外记录警告日志）
     */
    public void cleanupOnFailure(FileUploadDto fileUploadDto) {
        cleanupTempFile(fileUploadDto.getTempFile());
        log.warn("上传失败，已清理临时文件: fileId={}, path={}",
                fileUploadDto.getFileId(), fileUploadDto.getTempFile());
    }
}
