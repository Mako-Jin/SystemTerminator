package com.yaocode.sts.file.application.service.handler;

import com.yaocode.sts.common.crypto.enums.AlgorithmTypeEnums;
import com.yaocode.sts.common.crypto.utils.HexUtils;
import com.yaocode.sts.common.tools.id.IdFactory;
import com.yaocode.sts.common.tools.id.IdGeneratorType;
import com.yaocode.sts.file.core.constants.FileConstants;
import com.yaocode.sts.file.application.model.command.UploadFileCommand;
import com.yaocode.sts.file.application.model.dto.FileUploadDto;
import com.yaocode.sts.file.core.enums.FileErrorCodeEnums;
import com.yaocode.sts.file.core.exception.FileValidationException;
import com.yaocode.sts.file.infrastructure.config.FileStorageConfig;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * 文件上传准备处理器
 * <p>
 * 职责：
 * <ul>
 *   <li>生成文件唯一 ID</li>
 *   <li>创建临时文件（流式写入）</li>
 *   <li>同时计算 MD5 和 SHA-256 哈希</li>
 *   <li>记录开始时间戳</li>
 * </ul>
 * </p>
 *
 * <p>优化：使用单次流式处理同时完成文件落盘和哈希计算，
 * 避免二次读取文件带来的 I/O 开销。</p>
 *
 * @author yaocode
 * @since 1.0.0
 */
@Slf4j
@Component
@Order(2)
public class FileUploadPreparationHandler implements FileUploadHandler {

    @Resource
    private FileStorageConfig fileStorageConfig;

    @Override
    public void handle(FileUploadDto fileUploadDto) {
        UploadFileCommand command = fileUploadDto.getCommand();

        // 1. 生成文件 ID
        fileUploadDto.setFileId(IdFactory.generate(IdGeneratorType.UUID));
        fileUploadDto.setStartTime(System.currentTimeMillis());

        // 2. 创建临时文件并计算哈希
        Path tempFile = createTempFileWithHash(command, fileUploadDto);
        fileUploadDto.setTempFile(tempFile);

        log.debug("临时文件创建完成: fileId={}, path={}, size={} bytes",
                fileUploadDto.getFileId(), tempFile, fileUploadDto.getActualFileSize());
    }

    /**
     * 创建临时文件并同时计算 MD5 + SHA-256
     */
    private Path createTempFileWithHash(UploadFileCommand command, FileUploadDto fileUploadDto) {
        try {
            String tempDir = fileStorageConfig.getUpload().getTempDir();
            Path tempDirPath = Path.of(tempDir);
            if (!Files.exists(tempDirPath)) {
                Files.createDirectories(tempDirPath);
            }

            Path tempFile = tempDirPath.resolve(UUID.randomUUID() + FileConstants.TEMP_FILE_EXTENSION);

            int bufferSize = fileStorageConfig.getUpload().getStreamBufferSize();
            MessageDigest md5Digest = MessageDigest.getInstance(AlgorithmTypeEnums.MD5.getDisplayName());
            MessageDigest sha256Digest = MessageDigest.getInstance(AlgorithmTypeEnums.SHA_256.getDisplayName());

            long totalBytes = 0;
            try (InputStream inputStream = command.getFile().getInputStream();
                 OutputStream outputStream = Files.newOutputStream(tempFile)) {

                byte[] buffer = new byte[bufferSize];
                int bytesRead;

                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                    md5Digest.update(buffer, 0, bytesRead);
                    sha256Digest.update(buffer, 0, bytesRead);
                    totalBytes += bytesRead;
                }

                outputStream.flush();
            }

            // 3. 回填哈希和实际大小到 command 和 fileUploadDto
            String fileMd5 = HexUtils.bytesToHex(md5Digest.digest());
            String fileSha256 = HexUtils.bytesToHex(sha256Digest.digest());

            command.setFileMd5(fileMd5);
            command.setFileSha256(fileSha256);
            command.setFileSize(totalBytes);

            fileUploadDto.setFileMd5(fileMd5);
            fileUploadDto.setFileSha256(fileSha256);
            fileUploadDto.setActualFileSize(totalBytes);

            return tempFile;

        } catch (NoSuchAlgorithmException | IOException e) {
            throw new FileValidationException(FileErrorCodeEnums.FILE_PREPARE_FAILED, e, command.getFileName());
        }
    }

}