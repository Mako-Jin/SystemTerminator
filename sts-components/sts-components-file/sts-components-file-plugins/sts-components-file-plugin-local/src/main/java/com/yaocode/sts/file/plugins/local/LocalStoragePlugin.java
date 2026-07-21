package com.yaocode.sts.file.plugins.local;

import com.yaocode.sts.file.core.enums.StorageTypeEnums;
import com.yaocode.sts.file.core.exception.FileNotFoundException;
import com.yaocode.sts.file.core.spi.StoragePlugin;
import com.yaocode.sts.file.core.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

/**
 * 本地存储插件
 * <p>
 * 作为默认的基础存储插件，提供本地文件系统存储能力
 * 支持租户隔离，按租户ID分目录存储
 * </p>
 *
 * @author yaocode
 * @since 1.0.0
 */
public class LocalStoragePlugin implements StoragePlugin {

    private static final Logger logger = LoggerFactory.getLogger(LocalStoragePlugin.class);

    private final LocalStorageProperties properties;
    private final Path storagePath;

    // 日期格式化器，用于按日期分目录
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy/MM/dd");

    /**
     * 无参构造方法 - SPI要求
     */
    public LocalStoragePlugin() {
        this.properties = new LocalStorageProperties();
        this.storagePath = Paths.get(properties.getBasePath()).toAbsolutePath().normalize();
        initStoragePath();
    }

    /**
     * 带参构造方法 - Spring注入
     */
    public LocalStoragePlugin(LocalStorageProperties properties) {
        this.properties = properties;
        this.storagePath = Paths.get(properties.getBasePath()).toAbsolutePath().normalize();
        initStoragePath();
    }

    private void initStoragePath() {
        try {
            Files.createDirectories(storagePath);
            logger.info("本地存储插件初始化成功，根目录: {}", storagePath);
        } catch (IOException e) {
            logger.error("初始化本地存储目录失败: {}", e.getMessage(), e);
            throw new RuntimeException("初始化本地存储目录失败", e);
        }
    }

    @Override
    public StorageTypeEnums getStorageType() {
        return StorageTypeEnums.LOCAL;
    }

    @Override
    public String getPluginName() {
        return StorageTypeEnums.LOCAL.getType();
    }

    /**
     * 上传文件（支持租户和业务分类）
     *
     * @param inputStream 文件输入流
     * @param fileName    文件名
     * @param fileSize    文件大小
     * @param tenantId    租户ID（可选）
     * @param bucket      业务分类（可选）
     * @return 文件存储路径
     */
    @Override
    public String upload(InputStream inputStream, String fileName, Long fileSize, String tenantId, String bucket) {
        if (inputStream == null) {
            throw new IllegalArgumentException("文件输入流不能为空");
        }
        if (fileName == null || fileName.trim().isEmpty()) {
            throw new IllegalArgumentException("文件名不能为空");
        }

        try {
            // 1. 校验文件大小
            if (fileSize != null && fileSize > properties.getMaxFileSize() * 1024 * 1024) {
                throw new IllegalArgumentException("文件大小超过限制: " + properties.getMaxFileSize() + "MB");
            }

            // 2. 构建存储路径：basePath/tenantId/bucket/yyyy/MM/dd/
            String datePath = LocalDateTime.now().format(DATE_FORMATTER);
            String tenantDir = tenantId != null && !tenantId.trim().isEmpty() ? tenantId : "default";
            String bucketDir = bucket != null && !bucket.trim().isEmpty() ? bucket : "default";

            Path targetDir = this.storagePath
                    .resolve(tenantDir)
                    .resolve(bucketDir)
                    .resolve(datePath);
            Files.createDirectories(targetDir);

            // 3. 生成存储文件名
            String extension = FileUtils.getFileExtension(fileName);
            String baseName = FileUtils.getBaseFileName(fileName);

            String storedFileName;
            if (properties.isKeepOriginalName()) {
                storedFileName = FileUtils.sanitizeFileName(fileName);
            } else {
                storedFileName = UUID.randomUUID().toString();
                if (!extension.isEmpty()) {
                    storedFileName += "." + extension;
                }
            }

            // 4. 处理重名文件
            Path targetPath = targetDir.resolve(storedFileName);
            if (Files.exists(targetPath)) {
                String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("_HHmmss"));
                String nameWithoutExt = storedFileName;
                String ext = "";
                if (storedFileName.contains(".")) {
                    int lastDot = storedFileName.lastIndexOf(".");
                    nameWithoutExt = storedFileName.substring(0, lastDot);
                    ext = storedFileName.substring(lastDot);
                }
                storedFileName = nameWithoutExt + timestamp + ext;
                targetPath = targetDir.resolve(storedFileName);
            }

            // 5. 保存文件
            try (InputStream is = inputStream) {
                Files.copy(is, targetPath);
            }

            // 6. 返回相对路径
            String relativePath = this.storagePath.relativize(targetPath).toString();
            logger.info("文件上传成功: tenant={}, bucket={}, path={}", tenantId, bucket, relativePath);
            return relativePath;

        } catch (IOException e) {
            logger.error("文件上传失败: {}", e.getMessage(), e);
            throw new RuntimeException("文件上传失败: " + e.getMessage(), e);
        }
    }

    @Override
    public String getFileUrl(String filePath) {
        if (filePath == null || filePath.trim().isEmpty()) {
            return null;
        }
        return filePath;
    }

    @Override
    public InputStream download(String filePath) {
        if (filePath == null || filePath.trim().isEmpty()) {
            throw new IllegalArgumentException("文件路径不能为空");
        }

        try {
            Path path = resolvePath(filePath);
            if (!Files.exists(path) || !Files.isRegularFile(path)) {
                throw new FileNotFoundException("文件不存在: " + filePath);
            }
            return Files.newInputStream(path);
        } catch (IOException e) {
            logger.error("文件下载失败: {}", e.getMessage(), e);
            throw new RuntimeException("文件下载失败", e);
        }
    }

    @Override
    public void delete(String filePath) {
        if (filePath == null || filePath.trim().isEmpty()) {
            return;
        }

        try {
            Path path = resolvePath(filePath);
            if (!Files.exists(path)) {
                return;
            }
            boolean deleted = Files.deleteIfExists(path);
            if (deleted) {
                logger.info("文件删除成功: {}", filePath);
                // 尝试删除空目录
                tryDeleteEmptyDirectories(path.getParent());
            }
        } catch (IOException e) {
            logger.error("文件删除失败: {}", e.getMessage(), e);
            throw new RuntimeException("文件删除失败: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean exists(String filePath) {
        if (filePath == null || filePath.trim().isEmpty()) {
            return false;
        }

        try {
            Path path = resolvePath(filePath);
            return Files.exists(path) && Files.isRegularFile(path);
        } catch (Exception e) {
            logger.error("检查文件是否存在失败: {}", e.getMessage(), e);
            return false;
        }
    }

    @Override
    public String uploadChunk(InputStream inputStream, String uploadId, int chunkNumber, long chunkSize) {
        // 本地存储分片上传实现
        try {
            // 构建分片存储目录：basePath/chunks/uploadId/
            Path chunkDir = this.storagePath.resolve("chunks").resolve(uploadId);
            Files.createDirectories(chunkDir);

            // 分片文件名：chunk_{chunkNumber}.part
            String chunkFileName = String.format("chunk_%d.part", chunkNumber);
            Path chunkPath = chunkDir.resolve(chunkFileName);

            try (InputStream is = inputStream) {
                Files.copy(is, chunkPath);
            }

            logger.debug("分片上传成功: uploadId={}, chunkNumber={}", uploadId, chunkNumber);
            return chunkPath.toString();

        } catch (IOException e) {
            logger.error("分片上传失败: {}", e.getMessage(), e);
            throw new RuntimeException("分片上传失败: " + e.getMessage(), e);
        }
    }

    @Override
    public String mergeChunks(String uploadId, String fileId) {
        try {
            // 分片目录
            Path chunkDir = this.storagePath.resolve("chunks").resolve(uploadId);
            if (!Files.exists(chunkDir)) {
                throw new RuntimeException("分片目录不存在: " + uploadId);
            }

            // 获取所有分片文件
            List<Path> chunks = Files.list(chunkDir)
                    .filter(Files::isRegularFile)
                    .sorted((p1, p2) -> {
                        String name1 = p1.getFileName().toString();
                        String name2 = p2.getFileName().toString();
                        int num1 = Integer.parseInt(name1.replaceAll("\\D", ""));
                        int num2 = Integer.parseInt(name2.replaceAll("\\D", ""));
                        return Integer.compare(num1, num2);
                    })
                    .toList();

            if (chunks.isEmpty()) {
                throw new RuntimeException("没有找到分片文件: " + uploadId);
            }

            // 生成目标文件名
            String targetFileName = fileId != null ? fileId : UUID.randomUUID().toString();
            // 根据第一个分片确定扩展名（如果有的话）
            Path firstChunk = chunks.get(0);
            String fileName = firstChunk.getFileName().toString();
            String extension = FileUtils.getFileExtension(fileName);
            if (!extension.isEmpty()) {
                targetFileName += "." + extension;
            }

            // 按日期分目录
            String datePath = LocalDateTime.now().format(DATE_FORMATTER);
            Path targetDir = this.storagePath
                    .resolve("default")
                    .resolve(datePath);
            Files.createDirectories(targetDir);

            Path targetPath = targetDir.resolve(targetFileName);

            // 合并分片
            try (OutputStream os = Files.newOutputStream(targetPath)) {
                byte[] buffer = new byte[8192];
                for (Path chunk : chunks) {
                    try (InputStream is = Files.newInputStream(chunk)) {
                        int bytesRead;
                        while ((bytesRead = is.read(buffer)) != -1) {
                            os.write(buffer, 0, bytesRead);
                        }
                    }
                }
                os.flush();
            }

            // 清理分片目录
            cleanupChunks(uploadId);

            String relativePath = this.storagePath.relativize(targetPath).toString();
            logger.info("分片合并成功: uploadId={}, path={}", uploadId, relativePath);
            return relativePath;

        } catch (IOException e) {
            logger.error("分片合并失败: {}", e.getMessage(), e);
            throw new RuntimeException("分片合并失败: " + e.getMessage(), e);
        }
    }

    @Override
    public void cleanupChunks(String uploadId) {
        try {
            Path chunkDir = this.storagePath.resolve("chunks").resolve(uploadId);
            if (Files.exists(chunkDir)) {
                Files.walk(chunkDir)
                        .filter(Files::isRegularFile)
                        .forEach(p -> {
                            try {
                                Files.deleteIfExists(p);
                            } catch (IOException e) {
                                logger.warn("删除分片文件失败: {}", p);
                            }
                        });
                Files.deleteIfExists(chunkDir);
                logger.debug("清理分片目录成功: {}", uploadId);
            }
        } catch (IOException e) {
            logger.warn("清理分片目录失败: {}", e.getMessage());
        }
    }

    /**
     * 解析文件路径
     */
    private Path resolvePath(String filePath) {
        Path path = Paths.get(filePath);
        if (path.isAbsolute()) {
            return path;
        }
        return this.storagePath.resolve(filePath);
    }

    /**
     * 尝试删除空目录（从叶子节点向上）
     */
    private void tryDeleteEmptyDirectories(Path dir) {
        try {
            if (dir != null && Files.isDirectory(dir) && isDirectoryEmpty(dir)) {
                // 不要删除根目录
                if (dir.equals(this.storagePath)) {
                    return;
                }
                Files.delete(dir);
                logger.debug("删除空目录: {}", dir);
                // 继续向上删除
                tryDeleteEmptyDirectories(dir.getParent());
            }
        } catch (IOException e) {
            logger.debug("删除空目录失败: {}", e.getMessage());
        }
    }

    /**
     * 判断目录是否为空
     */
    private boolean isDirectoryEmpty(Path dir) throws IOException {
        try (java.util.stream.Stream<Path> entries = Files.list(dir)) {
            return entries.findFirst().isEmpty();
        }
    }
}